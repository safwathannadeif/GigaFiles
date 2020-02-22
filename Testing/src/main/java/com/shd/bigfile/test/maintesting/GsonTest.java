package com.shd.bigfile.test.maintesting;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import com.shd.bigfile.test.generators.Employee;
import com.shd.bigfile.test.generators.GenerateLisOfEmployee;
import com.shd.bigfile.wrgiga.ByteToRecordFuncIF;
import com.shd.commonref.ExtendedLevel;
import com.shd.commonref.LoggerRef;
public class GsonTest {
	
	    public static void main(String[] args) throws IOException, InterruptedException {
	    	LoggerRef.makeLogRefInit(ExtendedLevel.MSG) ;
	    	test() ;
	    } 

	public static void test() throws IOException {
	GenerateLisOfEmployee genEmpi = new GenerateLisOfEmployee() ;
	List<Employee> lisOfEmp = new ArrayList<>() ;
	lisOfEmp = genEmpi.generateEmpLis(1, 2) ;
	Employee emp1 = lisOfEmp.get(0) ;
	Gson gson = new Gson();
	String empStr= gson.toJson(emp1, Employee.class);
	byte[] byteAry = empStr.getBytes(StandardCharsets.UTF_8)  ;
	InputStream is = new ByteArrayInputStream(byteAry);
    Reader reader = new InputStreamReader(is, "UTF-8");
    Employee emp1Back = gson.fromJson(reader,Employee.class);
    test2(emp1Back) ;
	
}
	public static void test2(Employee empi) throws IOException
	{
	
			//ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			//write data to byteArrayOutputStream
			ByteArrayOutputStream osB =new ByteArrayOutputStream();
			//byte[] bytes = byteArrayOutputStream.toByteArray()
	        JsonWriter writer = new JsonWriter(new OutputStreamWriter(osB, "UTF-8"));
	        //writer.setIndent("  ");
	       // writer.beginArray();
	        //for (Message message : messages) {
	        Gson gson = new Gson() ;
	            gson.toJson(empi, Employee.class, writer);

	        //writer.endArray();
	        writer.close();
	        byte[] bytes = osB.toByteArray() ;
	        Employee empBack = byteToRecordEmplFuncImpl.makeRecord(bytes) ;
	        //use Lampda Function from WriteRecordEmpImpl to conv back bytes to EMP
	        System.out.println("**empBack**" + empBack.toString()) ;
	 }
	public static   ByteToRecordFuncIF<Employee> byteToRecordEmplFuncImpl = (byteAry -> {
		InputStream is = new ByteArrayInputStream(byteAry);
	    Reader reader;
		try {
			reader = new InputStreamReader(is, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException(e) ;
		}
	    Gson gson = new Gson() ;
	    Employee employee = gson.fromJson(reader,Employee.class);
	    return employee ;
	});         
	   
}