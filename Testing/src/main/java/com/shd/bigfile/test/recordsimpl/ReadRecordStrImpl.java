package com.shd.bigfile.test.recordsimpl;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.shd.bigfile.wrgiga.ByteToRecordFuncIF;
import com.shd.bigfile.wrgiga.ReadRecordsIF;

public class ReadRecordStrImpl implements ReadRecordsIF<String>
{
	List<String> listOfStrRecs  = new ArrayList<>();
	
	public ReadRecordStrImpl() {
		
	}
	public  ByteToRecordFuncIF<String>  getByteToRecordFuncImpl() {
		return byteToRecordFuncImpl ;
	}
	public void addRecord(String strRecord ) {
		listOfStrRecs.add(strRecord) ;
	}
	 
	public List<String> getListOfStrRecs() {
		return listOfStrRecs;
	}

	private  ByteToRecordFuncIF<String> byteToRecordFuncImpl = (byteAry -> {
		String strOut=  new String(byteAry, StandardCharsets.UTF_8);
		return strOut;
	}); 
	
}
 