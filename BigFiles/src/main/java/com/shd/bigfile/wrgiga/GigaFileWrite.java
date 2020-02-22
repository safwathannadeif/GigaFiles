package com.shd.bigfile.wrgiga;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import com.shd.commonref.ExtendedLevel;
import com.shd.commonref.LoggerRef;

public class GigaFileWrite {
	private FileChannel fChannel = null ;
	private BBWriteThrdCtl   bBWrite;
	private FileOutputStream wFouts = null ;
	private ProducerConsumerWriter synchInOut ;
	private  int  Byte_Buf_Size = 10*1024 ;		//default 10K
	public GigaFileWrite(String filename, int bufSize) throws IOException
	{
		setMaxBufSize(bufSize);
		initGigaFileWrite(filename);
	}
	public GigaFileWrite(String filename) throws IOException
	{
		initGigaFileWrite(filename);
	}
	private void initGigaFileWrite(String filename) throws IOException
	{
		wFouts = new FileOutputStream(filename);
		fChannel = wFouts.getChannel() ;
		fChannel.force(true);
		synchInOut = new ProducerConsumerWriter(Byte_Buf_Size) ;
		LoggerRef.makeLogRef().log(ExtendedLevel.MSG,"MaxBufSize Used In K=[" + Byte_Buf_Size/1024 +"]" ) ;
		fChannel.force(true);
		bBWrite = new BBWriteThrdCtl (this) ;  
		bBWrite.start();
		
	}
	
    @SuppressWarnings("rawtypes")
	public void addRec(WriteRecordIF writeRecordi,String tagStri) throws InterruptedException
    {
    	synchInOut.recordProducer(writeRecordi,tagStri);
    }
    //
    public void doneWrite() throws InterruptedException
    {
    	synchInOut.done();
    }
	public void endWrite() throws IOException
	{

		wFouts.flush();
		fChannel.close() ;
		wFouts.close();	
	}

	public FileChannel getfChannel() {
		return fChannel;
	}

	public BBWriteThrdCtl getbBWrite() {
		return bBWrite;
	}

	

	public ProducerConsumerWriter getSynchInOut() {
		return synchInOut;
	}

	public int getMaxBufSize() {
		return Byte_Buf_Size;
	}
	private void setMaxBufSize(int sizeInK) {
		
		if (sizeInK < 1) {
			LoggerRef.makeLogRef().log(ExtendedLevel.ERR,"MaxBufSize Should be > 1 Not " + sizeInK) ;
			throw new RuntimeException("MaxBufSize Should be > 1") ;
		}
		 Byte_Buf_Size=1024*sizeInK ;
	}
	
}
