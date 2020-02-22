package com.shd.bigfile.wrgiga;

public interface WriteRecordIF<Record> {
	public  RecordToByteFuncIF<Record>  getRecordToByteFuncImpl() ;
	public Record getRecord() ;
}

