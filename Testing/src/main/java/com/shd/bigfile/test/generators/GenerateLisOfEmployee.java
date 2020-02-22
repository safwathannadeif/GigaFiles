package com.shd.bigfile.test.generators;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.shd.commonref.ExtendedLevel;
import com.shd.commonref.LoggerRef;

public class GenerateLisOfEmployee {  /// Generate Lis Of Employee

	 Employee nEmp = new Employee() ;	
//
	 @FunctionalInterface 
	interface EmpFunction {
		Employee doIt(Employee emp, Integer ii ) ;
		}
	EmpFunction  empFunctioni = Employee::generateNewEmployee ; 
	 	
//
	  @FunctionalInterface 
		interface DateFormatISOForGsonFun {
			String doDateIso(GenerateLisOfEmployee gen) ;
			}
public	DateFormatISOForGsonFun  dateFormatISOForGsonFuni = GenerateLisOfEmployee::dateISO ; 
public  String dateISO()
{
	 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	 
	  String generatedDateTimeISO  = formatter.format(LocalDateTime.now());			//(LocalDate.now()) ;
	    return(generatedDateTimeISO) ; 
}

	  
    public  List<Employee> generateEmpLis(int start , int end)
    {
		List<Integer>  inArry =IntStream.range(start, end).boxed().collect(Collectors.toList()) ;
        GenerateLisOfEmployee generateLis = new GenerateLisOfEmployee() ;
        List<Employee> lisOfEmployee = inArry.stream().map( i-> generateLis.empFunctioni.doIt(generateLis.nEmp,i) )
        		                                      .peek(k -> k.setDateateTimeISO(generateLis.dateFormatISOForGsonFuni.doDateIso(generateLis)))
        		                                      .collect(Collectors.toList()) ;
        LoggerRef.makeLogRef().log(ExtendedLevel.MSG,"Start empListReport from GenerateLisOfEmployee:") ;
        LoggerRef.makeLogRef().log(ExtendedLevel.MSG,"End empListReport from GenerateLisOfEmployee:") ;
        return lisOfEmployee ;
    }
}
