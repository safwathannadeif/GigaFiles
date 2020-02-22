package com.shd.bigfile.wrgiga;

import java.io.IOException;
import java.nio.ByteBuffer;

import com.shd.commonref.ExtendedLevel;
import com.shd.commonref.LoggerRef;
// ByteBuffer Writer Controller
public class BBWriteThrdCtl extends Thread 

{          
private     GigaFileWrite fileWrite ;
public BBWriteThrdCtl(GigaFileWrite fileWritei)
	{	
		fileWrite =fileWritei ;
		
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
	public void writeSlicedToFile2(ByteBuffer wByteBuffer ) throws IOException
	{
		if (wByteBuffer.position() !=0 )wByteBuffer.flip();
		//showWhatWritten(wByteBuffer) ; //Debug***********Debug Only
		fileWrite.getfChannel().write(wByteBuffer);
		while (wByteBuffer.hasRemaining())
		{
			fileWrite.getfChannel().write(wByteBuffer); 
		}
		wByteBuffer.clear() ;
		wByteBuffer= null ;
	}


	public GigaFileWrite getFileWrite() {
		return fileWrite;
	}
}
