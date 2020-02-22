package com.shd.bigfile.test.recordsimpl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import com.shd.bigfile.test.generators.Employee;
import com.shd.bigfile.wrgiga.RecordToByteFuncIF;
import com.shd.bigfile.wrgiga.WriteRecordIF;

// Accepts Employee return byte[] Implementation 
public class WriteRecordEmpImpl implements WriteRecordIF<Employee> {

	private Employee empRecord ;
	private  static Gson gson = new Gson() ;
	public WriteRecordEmpImpl() {
		
	}
public WriteRecordEmpImpl(Employee empi) {
	empRecord = empi ;
	}
	public Employee getRecord()
	{
		return empRecord ;
	}
	
	public RecordToByteFuncIF<Employee>  getRecordToByteFuncImpl() {
		return recordToByteEmplFuncImpl ;
	}
	//
	@SuppressWarnings("unused")
	private  RecordToByteFuncIF<Employee> recordToByteEmplFuncImpl_2 = (emp -> {
	ByteArrayOutputStream osB =new ByteArrayOutputStream();
	try {
		JsonWriter writer = new JsonWriter(new OutputStreamWriter(osB, "UTF-8"));
		gson.toJson(emp, Employee.class, writer);
	    writer.close();
	} catch (UnsupportedEncodingException e) {
		e.printStackTrace();
		throw new RuntimeException(e) ;
	} catch (IOException e) {
		e.printStackTrace();
		throw new RuntimeException(e) ;
	}
    byte[] byte2Write = osB.toByteArray() ;
    return byte2Write ;
	}
	);
    //
	private  RecordToByteFuncIF<Employee> recordToByteEmplFuncImpl = (emp -> {
		String empStr= gson.toJson(emp, Employee.class);
		byte[] byteAry = empStr.getBytes(StandardCharsets.UTF_8)  ;
		return byteAry ;
	});
	@Override
	public String toString() {
		return "WriteRecordEmpImpl [empRecord=" + empRecord + "]";
	} 
	
}