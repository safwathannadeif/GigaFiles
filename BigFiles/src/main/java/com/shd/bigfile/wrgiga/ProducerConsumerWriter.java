/* *Note:  Producer Consumer with thread Control. The purpose to control the Memory consumption */
/*  While the Consumer evicted the memory the producer has to wait so the memory is growing up to certain size */
/*  from one actor only (either consumer or producer ) */
package com.shd.bigfile.wrgiga;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

import com.shd.commonref.LoggerRef;

public class ProducerConsumerWriter {
private	LinkedList<ByteBuffer> linkLis= new LinkedList<ByteBuffer>();
private int bufSize;
private int size;
//private String produceOrConsume = null ;
//private Boolean produceWait = false ;
//private Boolean endOfProduced = false ;
//private Boolean consumeWait = false ;
private Boolean endOfWrite = false ;
private ByteBuffer bbAccumulated ;


public ProducerConsumerWriter(int bufSizei) {
 // maxBufSize = (cap > 0) ? cap : 1; // at least 1
bufSize=bufSizei ;
size = 0;
 bbAccumulated= ByteBuffer.allocate(bufSize+4) ;
}

public synchronized int getSize() {
  return size;
}

public synchronized boolean isFull() {
  return (size >= bufSize);
}

public synchronized void recordProducer( @SuppressWarnings("rawtypes") WriteRecordIF WriteRecordsImpl,String tagStri) throws InterruptedException {
  //produceOrConsume ="Produce" ;
  if (endOfWrite) {
	  notify() ;		//**Notify to Write Last if any and and Stop wait for the consumer**
	  //produceWait=false ;
	  return ;   //no more add and let the caller client caller thread finalize it 
  }

 // produceWait=false ;

  ByteBuffer bbRec =  makeTheRecWithTag(WriteRecordsImpl,tagStri ) ;
  int sizeRequest =  bbRec.limit();
  if (sizeRequest > bufSize &&  size== 0)
  {
	  //produceWait=true ;
	  linkLis.add(bbRec);	  
      size =sizeRequest +size;
      notifyConsumerAndWait() ;
      return ;
  }
  // int  testTotSizeRequested = size + sizeRequest ;
  if  ( bufSize < size + sizeRequest  ) {
	  notifyConsumerAndWait() ;
  }
  if (bbRec != null )
  {
  linkLis.add(bbRec);
  size = sizeRequest +size;
  }
  //produceWait=false ;
}
public synchronized void notifyConsumerAndWait() throws InterruptedException {
	notify() ;
	  //produceWait=true ;
	  wait () ;
	  size= 0 ;
}
//
public synchronized void writerConsumer(GigaFileWrite fileWrite) throws InterruptedException, IOException {	
	//produceOrConsume ="Consume" ;
	//consumeWait=true ;
	//printState() ;
	wait() ;
	//consumeWait=false ;
	//printState() ;
if (linkLis.isEmpty()) return ;
	
//ByteBuffer bbAccumulated= ByteBuffer.allocate(size+4) ;

	bbAccumulated.putInt(size) ;
	linkLis.forEach((bbRec) -> {
		//bbAccumulated.putInt(bbRec.limit()) ; //Length oF Record
		//printState() ;
		bbAccumulated.put(bbRec);
		bbRec.clear() ;
		bbRec = null ;
	});
	linkLis.clear();
	//LoggerRef.logInfo("**** Start Write to File size=" + size) ;
	
	//LoggerRef.logInfo("**** Start Write to File size Changed=" + size) ;
	fileWrite.getbBWrite().writeSlicedToFile2(bbAccumulated) ;
	size=0 ;
	bbAccumulated.clear();
	
	//LoggerRef.logInfo("**** End Write to File size =" + size) ;
	
	if (endOfWrite) {
		//consumeWait=false ;
		fileWrite.endWrite() ;
		return ;
	}
	notify() ;
	//consumeWait=true ;
	writerConsumer(fileWrite) ; 
}
public synchronized void done() throws InterruptedException {
	endOfWrite= true ;
	recordProducer(null,null);
}
//
//public   synchronized void printState() {
//	String priState= "PCW [linkLis=" + linkLis + ", capacity=" + bufSize + ", size=" + size + ", ProduceOrConsume="
//			+ produceOrConsume + ", ProduceWait=" + produceWait + ", ConsumeWait=" + consumeWait+", EndOfProduced=" + endOfProduced 
//			+ ", endOfWrite=" + endOfWrite + "]";
//	LoggerRef.makeLogRef().log(ExtendedLevel.DEBUG,priState);
//}
//
@SuppressWarnings({ "rawtypes", "unchecked" })
private synchronized ByteBuffer makeTheRecWithTag(WriteRecordIF WriteRecordsi,String tag) {
	byte[] tagBytes =String.valueOf("").getBytes() ;
	byte tagLengthInOneByte ;
	if (tag== null) {
		tagLengthInOneByte =0 ; 
	}
	else {
		 tagBytes = tag.getBytes(StandardCharsets.UTF_8)  ;
		 tagLengthInOneByte =(byte)tagBytes.length ;	
	}
	int unSignx = Byte.toUnsignedInt(tagLengthInOneByte);
	LoggerRef.makeLogRef().info("tagValue/Size:" + tag +"["+tagLengthInOneByte +"]" ) ;
	byte[] recBytes =WriteRecordsi.getRecordToByteFuncImpl().makeRecordToByte(WriteRecordsi.getRecord()) ;
	int lengthOfRecord = recBytes.length ;
	int totLength = 1+unSignx+4+lengthOfRecord ;
	ByteBuffer bbRecWithTag = ByteBuffer.allocate(totLength) ;
	bbRecWithTag.put(tagLengthInOneByte).put(tagBytes).putInt(lengthOfRecord).put(recBytes) ;
	bbRecWithTag.flip();
	return bbRecWithTag ;
}
}