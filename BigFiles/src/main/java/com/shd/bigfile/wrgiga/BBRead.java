package com.shd.bigfile.wrgiga;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import com.shd.commonref.ExtendedLevel;
import com.shd.commonref.LoggerRef;
public class BBRead {
//private	ByteBuffer byteBuffAccInput  ; 
public BBRead( )
{
	
}

@FunctionalInterface
interface RecordsFromBuf {
 @SuppressWarnings("rawtypes")
public Exception fromBufToRecords(ByteBuffer byteBuffAccInput,ReadRecordsIF readRecordsi);
}
@SuppressWarnings("unchecked")
RecordsFromBuf recordsFromBufi = (byteBuffAccInput,readRecordsi) ->
{ 
	Exception exp = null ;
	try {
		if (byteBuffAccInput.position() !=0 )byteBuffAccInput.flip();
		while (byteBuffAccInput.hasRemaining() )
		{
			byte iBytelen = byteBuffAccInput.get();
			int intUnSignx = Byte.toUnsignedInt(iBytelen);
			byte[] bytesTag = new byte[intUnSignx] ;
			//
			byteBuffAccInput.get(bytesTag);	
			String tagRecStr=  new String(bytesTag, StandardCharsets.UTF_8);
			LoggerRef.makeLogRef().log(ExtendedLevel.MSG,"TagRec-Str/Size[" + tagRecStr +"] " + intUnSignx ) ;	 
			//
			int lenOfRec = byteBuffAccInput.getInt();
			byte[] recBytes = new byte[lenOfRec] ;
			byteBuffAccInput.get(recBytes) ;
			Object objRec = readRecordsi.getByteToRecordFuncImpl().makeRecord(recBytes) ;
			readRecordsi.addRecord(objRec);	
		}
	}catch (Exception ex)
	{
		exp=ex;	
	}
	return exp ;
};

public RecordsFromBuf getRefToRecordsFromBufi()
{
	return recordsFromBufi ;
}

}



	