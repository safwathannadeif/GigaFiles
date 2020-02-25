package com.shd.bigfile.test.generators;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Employee {
	
	
	private transient List<String> pstr = Arrays.asList("A", "B", "C","V", "Y", "X", "Z","D", "K","M","L") ;
	
	private YearReport yR = new YearReport() ;
	private int employeeId; 
	private String firstName ;
    private String lastName ;
    private BigDecimal annualSalaryl ;
    private int yrsNoOfExperience ;  
    private String organisationName; 
    private transient  int  retPInx = -1 ; //Skip
    private transient int adjInt = -1 ;	//Skip
    private int recNum = 0 ;
    private String dateTimeISO = null ;
    private List<YearReport> lisOfReps = new ArrayList<YearReport>() ;
    private transient  int  noOfYearReports = 1 ; //Skip
    public Employee generateNewEmployee(int i)
    {
    	
    	Employee emp = new Employee() ;
    	emp.employeeId = i + 100 ;
    	emp.setRecNum(i) ;
    	emp.firstName="First-Name" + retNextPstr() ;
    	emp.lastName="Last-Name-Name" + retNextPstr() ;
    	emp.setSal(new BigDecimal( (1000*i*2)/(retPInx+1*3) + 800.5) );  
    	emp.retPInx = retPInx ;
    	emp.organisationName="Organisation" +pstr.get(retPInx) ;
    	emp.setYrsNoOfExperience((retPInx*5)+ adjInt);
    	adjInt = adjInt*(-1) ;
    	noOfYearReports++ ;
    	if ( noOfYearReports > 12) noOfYearReports=1 ;
    	//System.out.println("*** No Of Yr Report to Generate= " + noOfYearReports) ;
    	emp.lisOfReps= yR.generatNReport(noOfYearReports) ;
    	
    	return emp ;
    }
   
   
	private String retNextPstr()
    {
    	++retPInx ;
    	if ( retPInx == pstr.size() )retPInx= 0 ;
    	return pstr.get(retPInx) ;
    }
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public BigDecimal getSal() {
	    annualSalaryl.setScale(2, RoundingMode.HALF_UP);
	    return annualSalaryl ;
	}
	public void setSal(BigDecimal sali) {
		this.annualSalaryl = sali;
	}
	public int getYrsNoOfExperience() {
		return yrsNoOfExperience;
	}
	public void setYrsNoOfExperience(int yrsNoOfExperiencei) {
		this.yrsNoOfExperience = yrsNoOfExperiencei;
	}
	public String getOrganisationName() {
		return organisationName;
	}
	public void setOrganisationName(String organisationName) {
		this.organisationName = organisationName;
	}
	public int getRecNum() {
		return recNum;
	}
	public void setRecNum(int recNum) {
		this.recNum = recNum;
	}

    public String getDateateTimeISO() {
		return dateTimeISO;
	}

	public void setDateateTimeISO(String dateateTimeISO) {
		this.dateTimeISO = dateateTimeISO;
	}
	public List<YearReport> getLisOfReps() {
		return lisOfReps;
	}


	


	public void setLisOfReps(List<YearReport> lisOfReps) {
		this.lisOfReps = lisOfReps;
	}


//	@Override
//	public String toString() {
//		return "Employee [employeeId=" + employeeId + ", firstName=" + firstName + ", lastName=" + lastName + ", sal="
//				+ getSal() + ", yrsNoOfExperience=" + yrsNoOfExperience + ", organisationName=" + getOrganisationName() + 
//				", RecNum =" + getRecNum() +", GeneratedDateTimeISO=" + getDateateTimeISO() +"]\n";
//	}
	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", annualSalaryl=" + annualSalaryl + ", yrsNoOfExperience=" + yrsNoOfExperience
				+ ", organisationName=" + organisationName + ", recNum=" + recNum + ", dateTimeISO=" + dateTimeISO
				+ ", lisOfReps=" + lisOfReps.toString() + "]\n";
	}
	

	
}
