package com.shd.bigfile.wrgiga;

public interface   ReadRecordsIF<Record> {
public  ByteToRecordFuncIF<Record>  getByteToRecordFuncImpl() ;
 public void addRecord(Record record) ;
}
