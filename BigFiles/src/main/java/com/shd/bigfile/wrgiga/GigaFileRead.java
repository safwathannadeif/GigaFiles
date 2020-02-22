package com.shd.bigfile.wrgiga;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.shd.commonref.ExtendedLevel;
import com.shd.commonref.LoggerRef;

public class GigaFileRead {
private FileChannel fileChannel = null ;
private BBRead bBReadAsRef ;
public GigaFileRead(String pathStr) throws IOException {
		Path path = Paths.get(pathStr);
		fileChannel =  FileChannel.open(path);
		fileChannel.force(true);
		bBReadAsRef = new BBRead() ;
	}

@SuppressWarnings("rawtypes")
public Boolean readRecords(ReadRecordsIF readRecordsi) throws IOException ///		Records 
{

	ByteBuffer nextBulkBuf  ;
	synchronized(this) {
	if ( (nextBulkBuf =readNextBulkRecrds()) != null  )
	{
		Exception exp =bBReadAsRef.getRefToRecordsFromBufi().fromBufToRecords(nextBulkBuf, readRecordsi) ;
		nextBulkBuf.clear() ;
		nextBulkBuf= null ;
		if ( null != exp)throw new RuntimeException(exp) ;
		return  true ;
	}
	fileChannel.close();
}
	return false ;
}
//
private ByteBuffer readNextBulkRecrds()  throws IOException {
		ByteBuffer byteBufferIntLen = ByteBuffer.allocate(4) ; 
		while (byteBufferIntLen.hasRemaining() ) {
			int iret = fileChannel.read(byteBufferIntLen);
			if ( iret == -1 ) {
				fileChannel.close();
				return null ;        //End Of File
			}
		}
		byteBufferIntLen.flip();
		int lenOfBufferReadnext  = byteBufferIntLen.getInt() ;	
		byteBufferIntLen.clear() ;
		byteBufferIntLen=null ;
		ByteBuffer bufferOfrecords = ByteBuffer.allocate(lenOfBufferReadnext);
		int noOfBytesRead = fileChannel.read(bufferOfrecords);
		if (noOfBytesRead < 0 )LoggerRef.makeLogRef().log(ExtendedLevel.ERR,"Error:readNextBulkRecrds retuns noOfBytesRead =[" + noOfBytesRead + "] The noOfBytesRead should'nt be nagtive be " ) ;
			while (bufferOfrecords.hasRemaining()) {
				bufferOfrecords.get() ;                 
			}
		return bufferOfrecords ;
	}
}