package com.shd.bigfile.test.maintesting;
// 	C:\GigaFiles\GigaFile\Testing\target
//	java  -jar .\Testing-VT1-jar-with-dependencies.jar com.shd.bigfile.test.maintesting.MainTestReadWrite
//         new GigaFileWrite(wFilename, 10240); means 10M=1024*10240 
// for  Large files  run the command line BUT NOT FROM ECLIPSE 

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import com.shd.bigfile.test.generators.Employee;
import com.shd.bigfile.test.generators.GenerateLisOfEmployee;
import com.shd.bigfile.test.generators.RecordGenerator;
import com.shd.bigfile.test.recordsimpl.ReadRecordStrImpl;
import com.shd.bigfile.test.recordsimpl.RecordReadByteEmployeeImpl;
import com.shd.bigfile.test.recordsimpl.WriteRecordEmpImpl;
import com.shd.bigfile.test.recordsimpl.WriteRecordStrImpl;
import com.shd.bigfile.wrgiga.GigaFileRead;
import com.shd.bigfile.wrgiga.GigaFileWrite;
import com.shd.commonref.ExtendedLevel;
import com.shd.commonref.LoggerRef;

public class MainTestReadWrite {
    public static void main(String[] args) throws IOException, InterruptedException {
    	Instant start = Instant.now();
    	Thread.currentThread().setName("MainThrd");
    	LoggerRef.makeLogRefInit(ExtendedLevel.MSG) ;
    	//testWriteStr() ;
    	//testReadStr() ;
    	//testWriteEmp() ;
    	testReadEmp() ;
    	Instant finish = Instant.now();
    	long timeElapsed = Duration.between(start, finish).toMillis();
    	System.out.println("timeElapsed=[" + timeElapsed+"] Milli-Secs") ;
    }  
//
    public static void testWriteEmp() throws InterruptedException, IOException
    {
   
    int useBufSizeForWriting= 120; //size in M , Default 25M
    //String wFilename = "C:/GigaFiles/DataFiles/wFileEmployee.binATextTryG24-90M" ;
    String wFilename = "C:/GigaFiles/DataFiles/wFileEmployee.binATextTry120" ;
      	
      	//GigaFileWrite fileWrite = new GigaFileWrite(wFilename) ;
      	GigaFileWrite fileWrite = new GigaFileWrite(wFilename, useBufSizeForWriting) ; // 10240 size in KB
      	
      	GenerateLisOfEmployee genEmpi = new GenerateLisOfEmployee() ;
      	int noRecord =0 ;
      	int is = 1 ;  int ie =203 ;
      	for (int i=1; i < 51; i++) //10000  6001
      //	for (int i=1; i <2 ; i++) //10000
      	{  
    	List<Employee> lisOfEmp = genEmpi.generateEmpLis(is, ie) ;
    	for (Employee emp:lisOfEmp)
    	{
    		WriteRecordEmpImpl emplRec = new WriteRecordEmpImpl(emp) ;
    		String tagStr = RecordGenerator.generate(6) ; //Byte Max int is 127 unsigned can be up to 255
    		tagStr = "ST"+tagStr +"ET" ;
    		//String tagStr= null ;
    		fileWrite.addRec(emplRec, tagStr);
    	    ++noRecord ;
    		LoggerRef.makeLogRef().log(ExtendedLevel.MSG,"addEmplRecord:" + emp.toString() ) ;
    		tagStr = null ; 
    	}
    	is =ie ;
    	ie = ie+10000 ;
      	}
    	fileWrite.doneWrite();
    	LoggerRef.makeLogRef().log(ExtendedLevel.MSG,"Total No Of Record Written:[" + noRecord  +"]") ;
    }
//
    @SuppressWarnings("unchecked")
	public static void testReadEmp() throws InterruptedException, IOException
    {
    	int noRecord =0 ; 
    	String rFilename = "C:/GigaFiles/DataFiles/wFileEmployee.binATextTry120" ;
    	 GigaFileRead fileReadi = new GigaFileRead(rFilename) ; 
    	 RecordReadByteEmployeeImpl rEmpImp = new RecordReadByteEmployeeImpl() ;
    		while (fileReadi.readRecords(rEmpImp) )
    		{
    			@SuppressWarnings("rawtypes")
				List lisOut = rEmpImp.getListOfStrRecs() ;
    			 noRecord  =  noRecord +  lisOut.size() ;
    			Object allMgs= lisOut.stream().map(Object::toString).collect(Collectors.joining("")) ;
    			LoggerRef.makeLogRef().log(ExtendedLevel.MSG,"MainTestReadWrite:testReadEmp.LisOneBuksOfRecords Start"  ) ;
    			LoggerRef.makeLogRef().log(ExtendedLevel.MSG, "\n" +allMgs);	
    			LoggerRef.makeLogRef().log(ExtendedLevel.MSG,"MainTestReadWrite:testReadEmp.LisOneBuksOfRecords End"  ) ;	
    			rEmpImp = new RecordReadByteEmployeeImpl() ;
    		}
    		//Read will Close the file at this point
    		LoggerRef.makeLogRef().log(ExtendedLevel.MSG,"Total No Of Record Read:[" + noRecord  +"]") ;
    }
public static void testWriteStr() throws InterruptedException, IOException
{
String wFilename = "C:/GigaFiles/DataFiles/wFile1.binAText" ;
  	
  	GigaFileWrite fileWrite = new GigaFileWrite(wFilename) ;
  	int offest = 1 ;
  	//String tagStr= "" ;
  	String tagStr = RecordGenerator.generate(12) ; //Byte Max int is 127 unsigned can be up to 255
	for ( int i = 0 ; i < 5 ; i++)
	{
		int fixedlength = i + offest;
		String genStr = RecordGenerator.generate(fixedlength) ; 
		genStr = "*StartRec*" + genStr + "*EndRec*\n" ;
		WriteRecordStrImpl strRec = new WriteRecordStrImpl(genStr) ;
		String strTag = "ST"+tagStr +"ET" ;
		fileWrite.addRec(strRec,strTag);
		//fileWrite.addRec(genStr,null);
		LoggerRef.makeLogRef().log(ExtendedLevel.MSG,"MainTestReadWrite:testWrite.addRec" + genStr ) ;	 

}
	fileWrite.doneWrite();
}
@SuppressWarnings({ "rawtypes", "unchecked" })
public static void testReadStr() throws InterruptedException, IOException
{
	//String rFilename = "C:/JWT_Token/RgFiles/Wfile1.binATxt" ;
	String rFilename = "C:/GigaFiles/DataFiles/wFile1.binAText" ;
	GigaFileRead fileReadi = new GigaFileRead(rFilename) ; 
	ReadRecordStrImpl readRecordStri = new  ReadRecordStrImpl() ;
	while (fileReadi.readRecords(readRecordStri) )
	{
		List lisOut = readRecordStri.getListOfStrRecs() ;
		Object allMgs= lisOut.stream().map(Object::toString).collect(Collectors.joining("")) ;
		LoggerRef.makeLogRef().log(ExtendedLevel.MSG,"MainTestReadWrite:testRead.LisOneBuksOfRecords Start"  ) ;
		LoggerRef.makeLogRef().log(ExtendedLevel.MSG, "\n" +allMgs);	
		LoggerRef.makeLogRef().log(ExtendedLevel.MSG,"MainTestReadWrite:testRead.LisOneBuksOfRecords End"  ) ;	
		readRecordStri = new  ReadRecordStrImpl() ;
	}
	}
	
}