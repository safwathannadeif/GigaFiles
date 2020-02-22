/*-1 cd C:\GigaFiles\GigaFile\Testing\target
 * C:\GigaFiles\GigaFile\Testing\target> 
 * -2java -Xss1M  -jar .\Testing-VT1-jar-with-dependencies.jar com.shd.bigfile.test.maintesting.MainTestReadWrite
 * Xss1M for stacksize --One Thread with ByteBufer for large iterations gives out of stack
 * -3 bufSize size for GigaFileWrite, range from 10M to 40M
 *  For Testing Avg  7G file output from 32,000,000 Employee records
 *  Large files can run command line NOT FROM ECLIPSE 
 */
package com.shd.bigfile.test.maintesting;
// C:\GigaFiles\GigaFile\Testing\target
// java -Xss1M -jar .\Testing-VT1-jar-with-dependencies.jar com.shd.bigfile.test.maintesting.MainTestReadWrite
//Large File bufSize 10M -- 40M Tested 
// -XX:MaxDirectMemorySize=655360 for bytebuffer size
// java -Xss1M -XX:MaxDirectMemorySize=655360 -jar .\Testing-VT1-jar-with-dependencies.jar com.shd.bigfile.test.maintesting.MainTestReadWrite
//-2  java -Xss1M -XX:MaxDirectMemorySize=900000 -jar .\Testing-VT1-jar-with-dependencies.jar com.shd.bigfile.test.maintesting.MainTestReadWrite
//-3 The Buf size parameter for GigaFileWritein constructor K. value should be between 10M to 60M
//         e.g new GigaFileWrite(wFilename, 10240); means 10M=1024*10240 

import java.io.IOException;
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
    	Thread.currentThread().setName("MainThrd");
    	LoggerRef.makeLogRefInit(ExtendedLevel.MSG) ;
    	//testWriteStr() ;
    	//testReadStr() ;
    	//testWriteEmp() ;
    	testReadEmp() ;
    }  
//
    public static void testWriteEmp() throws InterruptedException, IOException
    {
    int useBufSizeForWriting= 10240*6 ; //size in K 10240 means 10M Range from 10M to 60M
   // int useBufSizeForWriting= 1024 ; //size in K 10240 means 10M Range from 10M to 60M
    String wFilename = "C:/GigaFiles/DataFiles/wFileEmployee.binATextTry" ;
      	
      	//GigaFileWrite fileWrite = new GigaFileWrite(wFilename) ;
      	GigaFileWrite fileWrite = new GigaFileWrite(wFilename, useBufSizeForWriting) ; // 10240 size in KB
      	String tagStr = RecordGenerator.generate(2) ; //Byte Max int is 127 unsigned can be up to 255
      	GenerateLisOfEmployee genEmpi = new GenerateLisOfEmployee() ;
      	int is = 1 ;  int ie =1000 ;
      	for (int i=1; i < 64 ; i++) //10000
      //	for (int i=1; i <2 ; i++) //10000
      	{
      	  
    	List<Employee> lisOfEmp = genEmpi.generateEmpLis(is, ie) ;
    	//lisOfEmp.forEach( emp -> 
    	for (Employee emp:lisOfEmp)
    	{
    		WriteRecordEmpImpl emplRec = new WriteRecordEmpImpl(emp) ;
    		//tagStr = "ST"+tagStr +"ET" ;
    		tagStr = null ; 
    		fileWrite.addRec(emplRec, tagStr);
    		LoggerRef.makeLogRef().log(ExtendedLevel.MSG,"addEmplRecord:" + emp.toString() ) ;
    	}
    	is =ie+1 ;
    	ie = ie+10000 ;
      	}
    	fileWrite.doneWrite();
    }
//
    @SuppressWarnings("unchecked")
	public static void testReadEmp() throws InterruptedException, IOException
    {
    	 String rFilename = "C:/GigaFiles/DataFiles/Save_wFileEmployee.binAText3" ;
    	 GigaFileRead fileReadi = new GigaFileRead(rFilename) ; 
    	 RecordReadByteEmployeeImpl rEmpImp = new RecordReadByteEmployeeImpl() ;
    		while (fileReadi.readRecords(rEmpImp) )
    		{
    			@SuppressWarnings("rawtypes")
				List lisOut = rEmpImp.getListOfStrRecs() ;
    			Object allMgs= lisOut.stream().map(Object::toString).collect(Collectors.joining("")) ;
    			LoggerRef.makeLogRef().log(ExtendedLevel.MSG,"MainTestReadWrite:testReadEmp.LisOneBuksOfRecords Start"  ) ;
    			LoggerRef.makeLogRef().log(ExtendedLevel.MSG, "\n" +allMgs);	
    			LoggerRef.makeLogRef().log(ExtendedLevel.MSG,"MainTestReadWrite:testReadEmp.LisOneBuksOfRecords End"  ) ;	
    			rEmpImp = new RecordReadByteEmployeeImpl() ;
    		}
    		//Read will Close the file at this point
    
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