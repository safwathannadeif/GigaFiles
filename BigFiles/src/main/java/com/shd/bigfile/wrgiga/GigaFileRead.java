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
private ByteBuffer byteBufferIntLen = ByteBuffer.allocate(4) ;
private ByteBuffer bufferOfrecords =  null ;
public GigaFileRead(String pathStr) throws IOException {
		Path path = Paths.get(pathStr);
		fileChannel =  FileChannel.open(path);
		bBReadAsRef = new BBRead() ;
	}

@SuppressWarnings("rawtypes")
public Boolean readRecords(ReadRecordsIF readRecordsi) throws IOException ///		Records 
{
    if (null == bufferOfrecords) {
    	readFirstBulkRecrds() ; // discover the length of record to allocate it once
    	Exception exp =bBReadAsRef.getRefToRecordsFromBufi().fromBufToRecords(bufferOfrecords, readRecordsi) ;
    	if ( null != exp)throw new RuntimeException(exp) ;
    	bufferOfrecords.clear() ;
    	if ( fileChannel.isOpen() ) return true ;
    	return  false ; 
    	}
	synchronized(this) {
	if ( (bufferOfrecords =readNextBulkRecrds()) != null  )
	{
		Exception exp =bBReadAsRef.getRefToRecordsFromBufi().fromBufToRecords(bufferOfrecords, readRecordsi) ;
		bufferOfrecords.clear() ;
		//nextBulkBuf= null ;
		if ( null != exp)throw new RuntimeException(exp) ;
		return  true ;
	}
	fileChannel.close();
}
	return false ;
}
private ByteBuffer readFirstBulkRecrds()  throws IOException {
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
	int chkrsize = ByteBufMgr.chkBufSize((lenOfBufferReadnext/2) +lenOfBufferReadnext);  //Increase Delta 50%
	
	bufferOfrecords = ByteBuffer.allocate(chkrsize); //Allocate once with large size
	LoggerRef.makeLogRef().log(ExtendedLevel.MSG,"readFirstBulkRecrds bufferOfrecords Allocated size=:[" + bufferOfrecords.capacity()  +"]") ;
	bufferOfrecords.limit(lenOfBufferReadnext) ;
	int noOfBytesRead = fileChannel.read(bufferOfrecords);
	if (noOfBytesRead < 0 )LoggerRef.makeLogRef().log(ExtendedLevel.ERR,"Error:readNextBulkRecrds retuns noOfBytesRead =[" + noOfBytesRead + "] The noOfBytesRead should'nt be nagtive be " ) ;
//		while (bufferOfrecords.hasRemaining()) {
//			bufferOfrecords.get() ;                 
//		}
	return bufferOfrecords ;
}

private ByteBuffer readNextBulkRecrds()  throws IOException { 
		while (byteBufferIntLen.hasRemaining() ) {
			int iret = fileChannel.read(byteBufferIntLen);
			if ( iret == -1 ) {
				fileChannel.close();
				return null ;        //End Of File
			}
		}
		byteBufferIntLen.flip();
		int lenOfBufferReadnext  = byteBufferIntLen.getInt() ;	
		if ( bufferOfrecords.capacity() < lenOfBufferReadnext) {
			bufferOfrecords.clear() ;
			bufferOfrecords=null ;
			int chkrsize = ByteBufMgr.chkBufSize((lenOfBufferReadnext/2) +lenOfBufferReadnext);  //Increase Delta 50%
			LoggerRef.makeLogRef().log(ExtendedLevel.MSG,"readNextBulkRecrds bufferOfrecords Re-Allocated size from/to=:[" + bufferOfrecords.capacity()+"/"+ chkrsize +"]") ;
			bufferOfrecords = ByteBuffer.allocate(chkrsize); //Resize for case of last record it should be also allocate once/last
		}
		byteBufferIntLen.clear() ;
		bufferOfrecords.limit(lenOfBufferReadnext) ;
		int noOfBytesRead = fileChannel.read(bufferOfrecords);
		if (noOfBytesRead < 0 )LoggerRef.makeLogRef().log(ExtendedLevel.ERR,"Error:readNextBulkRecrds retuns noOfBytesRead =[" + noOfBytesRead + "] The noOfBytesRead should'nt be nagtive be " ) ;
//			while (bufferOfrecords.hasRemaining()) {
//				bufferOfrecords.get() ;                 
//			}
		return bufferOfrecords ;
	}
}