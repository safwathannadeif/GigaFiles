package com.shd.bigfile.wrgiga;

public interface   ReadRecordsIF<Record> {
 @SuppressWarnings("rawtypes")
public  ByteToRecordFuncIF  getByteToRecordFuncImpl() ;
 public void addRecord(Record record) ;
}
