package com.shd.bigfile.wrgiga;

import java.io.IOException;

import com.shd.commonref.ExtendedLevel;
import com.shd.commonref.LoggerRef;
// ByteBuffer Writer Controller
public class BBWriteThrdCtl extends Thread 

{          
private     GigaFileWrite fileWrite ;
private     ByteBufMgr  bbMgr ;
public BBWriteThrdCtl(GigaFileWrite fileWritei, ByteBufMgr bbMgri)
	{	
		fileWrite =fileWritei ;
		bbMgr = bbMgri ;
		
	}
	
	 public void run(){
		 Thread.currentThread().setName("ProdConsumWThrd");
		 LoggerRef.makeLogRef().log(ExtendedLevel.MSG,"BBWriteThrdCtl Thread[" + Thread.currentThread().getName() +"] Start Running"); 
	       try {
	    	   fileWrite.getSynchInOut().writerConsumer(fileWrite);
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e) ;
		}
	    }
	
//	
	public void writeAccumulatedToFile() throws IOException
	{
		//wByteBuffer.flip() ;
		//lenOfBuf.putInt(wByteBuffer.limit()) ;
		bbMgr.getBbAccumulated().flip() ;
		bbMgr.getBbLengthAccumulated().putInt(bbMgr.getBbAccumulated().limit()) ;
		bbMgr.getBbLengthAccumulated().flip() ;
		//fileWrite.getfChannel().write(new ByteBuffer[] {lenOfBuf, wByteBuffer}) ;
		fileWrite.getfChannel().write(bbMgr.getByteBufferWriteArry()) ;
		bbMgr.getBbAccumulated().clear() ;
		//wBytBuffer.wByteBuffer.clear() ;
		bbMgr.getBbLengthAccumulated().clear();
	}


	public GigaFileWrite getFileWrite() {
		return fileWrite;
	}
}
