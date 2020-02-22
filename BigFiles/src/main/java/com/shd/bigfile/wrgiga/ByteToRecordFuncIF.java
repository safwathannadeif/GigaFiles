package com.shd.bigfile.wrgiga;


public interface ByteToRecordFuncIF<Record> {

	Record makeRecord(byte[] byteAr );

}