package com.shd.bigfile.wrgiga;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import com.shd.commonref.ExtendedLevel;
import com.shd.commonref.LoggerRef;

public class GigaFileWrite {
	private FileChannel  fChannel = null ;
	private BBWriteThrdCtl   bBWrite;
	private FileOutputStream wFouts = null ;
	private ProducerConsumerWriter synchInOut ;
	private  int  Byte_Buf_Size_In_M = 25 ;	//default 10M
	public GigaFileWrite(String filename, int bufSize) throws IOException
	{
		setMaxBufSize(bufSize);
		initGigaFileWrite(filename);
	}
	public GigaFileWrite(String filename) throws IOException
	{
		setMaxBufSize(Byte_Buf_Size_In_M) ; //Default 25 M
		initGigaFileWrite(filename);
	}
	private void initGigaFileWrite(String filename) throws IOException
	{
		wFouts = new FileOutputStream(filename);
		fChannel = wFouts.getChannel() ;
		fChannel.force(true);
		ByteBufMgr byteBufMgri = new ByteBufMgr(Byte_Buf_Size_In_M);
		synchInOut = new ProducerConsumerWriter(byteBufMgri) ;
		LoggerRef.makeLogRef().log(ExtendedLevel.MSG,"BufSize Used In Bytes=[" + Byte_Buf_Size_In_M +"]" ) ;
		bBWrite = new BBWriteThrdCtl (this,byteBufMgri) ;  
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
		return Byte_Buf_Size_In_M;
	}
	private void setMaxBufSize(int sizeInM) {
		
		if (sizeInM < 1) {
			LoggerRef.makeLogRef().log(ExtendedLevel.ERR,"MaxBufSize Should be > 1 Not " + sizeInM) ;
			throw new RuntimeException("MaxBufSize Should be > 1 M") ;
		}
		Byte_Buf_Size_In_M=1024*1024*sizeInM ;
		//Byte_Buf_Size_In_M=sizeInM ;
	}
	
}
