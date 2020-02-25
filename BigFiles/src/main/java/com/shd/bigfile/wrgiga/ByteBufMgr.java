package com.shd.bigfile.wrgiga;

import java.nio.ByteBuffer;

import com.shd.commonref.ExtendedLevel;
import com.shd.commonref.LoggerRef;

public class ByteBufMgr {
	private ByteBuffer bbRecord ;
	private ByteBuffer[] byteBufferWriteArry = new ByteBuffer[2] ;
	private static final int MEGA = 1024*1024 ;
	private static final int MAX_BUF_SIZE = Integer.MAX_VALUE - MEGA*10 ;
	private static final int MIN_BUF_SIZE = 25*MEGA ;

public ByteBufMgr(int accMaxLenInki)
{
	byteBufferWriteArry[0] = ByteBuffer.allocate(4) ; // int Length
	int chkacc = ByteBufMgr.chkBufSize(accMaxLenInki);
	byteBufferWriteArry[1] = ByteBuffer.allocate(chkacc) ;
	bbRecord = ByteBuffer.allocate(accMaxLenInki/10) ;
}
public ByteBuffer[] getByteBufferWriteArry()
{
	return byteBufferWriteArry ;
}
public ByteBuffer getBbLengthAccumulated() // Length Accumulated
{
	return byteBufferWriteArry[0] ;
}
	
public ByteBuffer getBbAccumulated() // Accumulated
{
	return byteBufferWriteArry[1] ;
}
public ByteBuffer getBbRecord() 
{
	return bbRecord ;
}
public void reSizeBbAccumulated(int newSize)
{
    LoggerRef.makeLogRef().log(ExtendedLevel.MSG," Resize ByteBuffer Accumulated Event. bufSize changed from/to " + byteBufferWriteArry[1].capacity() +"/" + newSize ) ;
    byteBufferWriteArry[1].clear() ;
    byteBufferWriteArry[1] = null ;
	int chknew = ByteBufMgr.chkBufSize(newSize);
    byteBufferWriteArry[1] = ByteBuffer.allocate(chknew) ;	
}
public ByteBuffer getBbRecord(int totRecSize)
{	
	if (bbRecord.capacity() >= totRecSize) return bbRecord ;
	reSizeBbRecord(totRecSize) ;
	return bbRecord ;
}
public void reSizeBbRecord(int newSize)
{
    LoggerRef.makeLogRef().log(ExtendedLevel.MSG," Resize ByteBuffer Record Event. bufSize changed from/to " + bbRecord.capacity() +"/" + newSize ) ;
    bbRecord.clear() ;
	bbRecord= null ;
    bbRecord = ByteBuffer.allocate(newSize) ;	
}
//
public static int chkBufSize(int inpi)
{
	int output = inpi ;
	if (inpi > MAX_BUF_SIZE ) output = MAX_BUF_SIZE ;
	if (inpi < MIN_BUF_SIZE)  output = MIN_BUF_SIZE  ;
	return output ;
}

}