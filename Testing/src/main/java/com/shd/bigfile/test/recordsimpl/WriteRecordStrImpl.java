package com.shd.bigfile.test.recordsimpl;

import java.nio.charset.StandardCharsets;

import com.shd.bigfile.wrgiga.RecordToByteFuncIF;
import com.shd.bigfile.wrgiga.WriteRecordIF;


public class WriteRecordStrImpl implements WriteRecordIF<String> {

	private String strRecord ;
	public WriteRecordStrImpl() {
		
	}
public WriteRecordStrImpl(String stri) {
	strRecord = stri ;
	}
	public String getRecord()
	{
		return strRecord ;
	}
	
	public RecordToByteFuncIF<String>  getRecordToByteFuncImpl() {
		return byteToRecordFuncImpl ;
	}
	
	private  RecordToByteFuncIF<String> byteToRecordFuncImpl = (str -> {
		byte[] byteAry = str.getBytes(StandardCharsets.UTF_8)  ;
		return byteAry;
	});
	@Override
	public String toString() {
		return "WriteRecordStrImpl [strRecord=" + strRecord + "]";
	} 
	
}