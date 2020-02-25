/* *Note:  Producer Consumer with thread Control. The purpose to control the Memory consumption */
/*  While the Consumer evicted the memory the producer has to wait so the memory is growing up to certain size */
/*  from one actor only (either consumer or producer ) */
package com.shd.bigfile.wrgiga;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import com.shd.commonref.ExtendedLevel;
import com.shd.commonref.LoggerRef;
public class ProducerConsumerWriter {
private int bufSize;
private int size;
private Boolean endOfWrite = false ;
//private ByteBuffer bbAccumulated ;
private ByteBufMgr byteBufMgr ;
private static final int Tag_Max_length = 252 ;

public ProducerConsumerWriter(ByteBufMgr bbMgr) {
//bufSize=bufSizei ;
byteBufMgr=bbMgr ;
size = 0;
}

public synchronized int getSize() {
  return size;
}

public synchronized boolean isFull() {
  return (size >= bufSize);
}

public synchronized void recordProducer( @SuppressWarnings("rawtypes") WriteRecordIF WriteRecordsImpl,String tagStri) throws InterruptedException {
  if (endOfWrite) {
	  notify() ;		//**Notify to Write Last if any and and Stop wait for the consumer**
	  return ;   //no more add and let the client caller thread finalize it 
  }

     makeTheRecWithTag(WriteRecordsImpl,tagStri ) ;
  if (byteBufMgr.getBbRecord().capacity() >  byteBufMgr.getBbAccumulated().capacity()) {  //evict and resize
	  if ( size > 0) notifyConsumerAndWait() ;
	  byteBufMgr.reSizeBbAccumulated(byteBufMgr.getBbRecord().capacity()) ;
	  LoggerRef.makeLogRef().log(ExtendedLevel.MSG,"Evict and Resize Event. Accumulate bufSize changed from/to " + bufSize +"/" + byteBufMgr.getBbRecord().capacity() );
	  bufSize=byteBufMgr.getBbRecord().capacity() ;
  }
  
  if  ( byteBufMgr.getBbAccumulated().capacity() < size +  byteBufMgr.getBbRecord().limit()  ) {
	  notifyConsumerAndWait() ;
  }
  if (byteBufMgr.getBbRecord() != null )
  {
  byteBufMgr.getBbAccumulated().put(byteBufMgr.getBbRecord());
  size = byteBufMgr.getBbRecord().limit() +size;
  byteBufMgr.getBbRecord().clear() ;
 
  }
}
public synchronized void notifyConsumerAndWait() throws InterruptedException {
	notify() ;
	  wait () ;
	  
}
//
public synchronized void writerConsumer(GigaFileWrite fileWrite) throws InterruptedException, IOException {	
wait() ;
if (size > 0) fileWrite.getbBWrite().writeAccumulatedToFile() ; 
byteBufMgr.getBbAccumulated().clear();
	size= 0 ;
	if (endOfWrite) {
		fileWrite.endWrite() ;
		return ;
	}
	notify() ;
	writerConsumer(fileWrite) ; 
}
public synchronized void done() throws InterruptedException {
	endOfWrite= true ;
	recordProducer(null,null);
}

@SuppressWarnings({ "rawtypes", "unchecked" })
private synchronized void makeTheRecWithTag(WriteRecordIF WriteRecordsi,String tag) {
	//Check tag length to be max 252
	if (tag != null && tag.length() > Tag_Max_length) {
		tag = tag.substring(0,252) ;
		LoggerRef.makeLogRef().log(ExtendedLevel.MSG, "tagValue Size truncted to Max:=" + Tag_Max_length ) ;
	}
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
	LoggerRef.makeLogRef().log(ExtendedLevel.MSG, "tagValue/Size:" + tag +"["+tagLengthInOneByte +"]" ) ;
	byte[] recBytes =WriteRecordsi.getRecordToByteFuncImpl().makeRecordToByte(WriteRecordsi.getRecord()) ;
	int lengthOfRecord = recBytes.length ;
	int totLength = 1+unSignx+4+lengthOfRecord ;
	//
	ByteBuffer bbRecWithTag =byteBufMgr.getBbRecord(totLength);
	bbRecWithTag.put(tagLengthInOneByte).put(tagBytes).putInt(lengthOfRecord).put(recBytes) ;
	bbRecWithTag.flip(); //flip makes the limit == the written length
	
	
}
}