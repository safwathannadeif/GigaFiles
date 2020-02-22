package com.shd.bigfile.test.recordsimpl;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.shd.bigfile.test.generators.Employee;
import com.shd.bigfile.wrgiga.ByteToRecordFuncIF;
import com.shd.bigfile.wrgiga.ReadRecordsIF;

public class RecordReadByteEmployeeImpl implements ReadRecordsIF<Employee>
{
	private List<Employee> listOfEmployeeRecs  = new ArrayList<>();
	private static Gson gson = new Gson() ;
	public RecordReadByteEmployeeImpl() {
	}
	public  ByteToRecordFuncIF<Employee>  getByteToRecordFuncImpl() {
		return byteToRecordEmplFuncImpl ;
	}
	public void addRecord(Employee empRecord ) {
		listOfEmployeeRecs.add(empRecord) ;
	}
	 
	public List<Employee> getListOfStrRecs() {
		return listOfEmployeeRecs;
	}
	//
	private  ByteToRecordFuncIF<Employee> byteToRecordEmplFuncImpl = (byteAry -> {
		InputStream is = new ByteArrayInputStream(byteAry);
	    Reader reader;
		try {
			reader = new InputStreamReader(is, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException(e) ;
		}
	    Employee employee = gson.fromJson(reader,Employee.class);
	    return employee ;
	}); 
	
}
