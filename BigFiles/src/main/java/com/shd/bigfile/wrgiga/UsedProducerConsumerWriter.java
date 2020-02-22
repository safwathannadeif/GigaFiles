package com.shd.bigfile.wrgiga;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

import com.shd.commonref.LoggerRef;

public class UsedProducerConsumerWriter {
@SuppressWarnings({ "rawtypes", "unchecked" })
private	LinkedList<ByteBuffer> linkLis= new LinkedList();

private int maxBBLength;
private int size;
private String produceOrConsume = null ;
private Boolean produceWait = false ;
private Boolean endOfProduced = false ;
private Boolean consumeWait = false ;
private Boolean endOfWrite = false ;
public UsedProducerConsumerWriter(int cap) {
  maxBBLength = (cap > 0) ? cap : 1; // at least 1
  size = 0;
}

public synchronized int getSize() {
  return size;
}

public synchronized boolean isFull() {
  return (size >= maxBBLength);
}

public synchronized void recordProducer(String stri) throws InterruptedException {
  produceOrConsume ="Produce" ;
  if (endOfWrite) {
	  notify() ;
	  produceWait=false ;
	  return ;   //no more add and let the caller client caller thread finalize it 
  }
  produceWait=false ;
  ByteBuffer bbRec= ByteBuffer.wrap(stri.getBytes(StandardCharsets.UTF_8));
  int sizeRequred = 4 + bbRec.limit() ;
  if (sizeRequred >= size && maxBBLength == 0)
  {
	  produceWait=true ;
	  linkLis.add(bbRec);
	 // size = sizeRequred ;
  }
  sizeRequred = size + sizeRequred ;
  if  ( maxBBLength < sizeRequred  ) {
	  notify() ;
	  produceWait=true ;
	  sizeRequred =  sizeRequred - size ; //rollBack the size
	  wait () ;
  }
  size = sizeRequred ;
  linkLis.add(bbRec);
  produceWait=false ;
  
}
//
public synchronized void writerConsumer(GigaFileWrite fileWrite) throws InterruptedException, IOException {	
	produceOrConsume ="Consume" ;
	consumeWait=true ;
	printState() ;
	wait() ;
	consumeWait=false ;
	printState() ;
	ByteBuffer bbAccumulated= ByteBuffer.allocate(size + 4) ;
	bbAccumulated.putInt(size) ;
	linkLis.forEach((bbRec) -> {
		bbAccumulated.putInt(bbRec.limit()) ; //Length oF Record
		bbAccumulated.put(bbRec);
	});
	linkLis.clear();
	size=0 ;
	fileWrite.getbBWrite().writeSlicedToFile2(bbAccumulated) ;
	if (endOfWrite) {
		consumeWait=false ;
		fileWrite.endWrite() ;
		return ;
	}
	notify() ;
	consumeWait=true ;
	writerConsumer(fileWrite) ; 
}
public synchronized void done() throws InterruptedException {
	endOfWrite= true ;
	recordProducer(null);
}
//
public   synchronized void printState() {
	String priState= "PCW [linkLis=" + linkLis + ", capacity=" + maxBBLength + ", size=" + size + ", ProduceOrConsume="
			+ produceOrConsume + ", ProduceWait=" + produceWait + ", ConsumeWait=" + consumeWait+", EndOfProduced=" + endOfProduced 
			+ ", endOfWrite=" + endOfWrite + "]";
	LoggerRef.makeLogRef().info(priState);
}




}