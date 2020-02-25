package com.shd.bigfile.test.generators;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class YearReport {
private  final String[] COMMENTS = {
"Shows how innovation and creativity can significantly improve a company’s performance. Doug’s willingness to take chances was shown in the ad campaign he pitched which on the face looked like it would bomb, but proved to be a smash hit.",
"She has consistently gone above and beyond his job to satsify his customers. He demonstrates how excellent customer service can have a lasting effect on customer relationships.",
"Took many steps to improve processes for the team over the past year. These steps were innovative and resulted in a 30% productivity gain on the team.",
"She has consistently gone above and beyond his job to satsify his customers. He demonstrates how excellent customer service can have a lasting effect on customer relationships.",
"He has a knack for changing directions when presented with new information. ",
"A great attribute and  his ability to skillfully move around obstacles as they present themselves.", 
"Found a creative workaround to the customer billing issues which has improved revenues by 22%.",
"Over the past year, he has been a very consistent employee. Throughout this time, Ted has performed his work as instructed, but did not take any opportunities to think about new ways he could be working. Improving processes and procedures could be a significant benefit to the company, but Ted needs to work on his creativity and how to think about the steps he takes in his work and how he can reduce the time it takes. ", 
"She creates a stifling environment which is not conducive to creativity.",
"He  is not willing to take chances and step out.", 
"She is reluctant to explore new ideas, processes, or alternatives.",
"The sales team has an effective telephone script which is shown to work. he consistently tries to “reinvent the wheel” and not work within the system which is designed for success.", 
"Customer Satisfaction is not good.",
"She has consistently gone above and beyond his job to satsify his customers. She demonstrates how excellent customer service can have a lasting effect on customer relationships." ,
"Clearly a “people” person and shows his clients how much he likes working with them."
};
private  final Short[] Rates = {
		0,1,2,3,4,5,6,7,8,9,10
} ;
private  final Short[] Years = {
		2020,2019,2018,2017,2016,2015,2014,2013,2012,2011,2010
} ;
private  Short indxOfComment =0 ;
private  Short indxOfRates =0 ;
private  Short indxOfYears =0 ;
private  int noOfComments =COMMENTS.length ;
private Short year ;
private Short workProductworkProduct ;
private Short dependability ;
private Short cooperativeness;
private Short adaptability;
private Short communication;
private Short dailyDecisionMakingProblemSolving ;
private Short serviceToPublicClients ;
private Short useofEquipmentAndMaterials ;
private Short projectPlanningAndImplementation ;
private Short workGroupManagement ;
private Short performancePlanningAndReview ;

private  List<String> lisOfComments = new ArrayList<String>() ;

public Short getYear() {
	return year;
}
public void setYear(Short year) {
	this.year = year;
}
public Short getWorkProductworkProduct() {
	return workProductworkProduct;
}
public void setWorkProductworkProduct(Short workProductworkProduct) {
	this.workProductworkProduct = workProductworkProduct;
}
public Short getDependability() {
	return dependability;
}
public void setDependability(Short dependability) {
	this.dependability = dependability;
}
public Short getCooperativeness() {
	return cooperativeness;
}
public void setCooperativeness(Short cooperativeness) {
	this.cooperativeness = cooperativeness;
}
public Short getAdaptability() {
	return adaptability;
}
@Override
public String toString() {
	return "YearReport [year=" + year + ", workProductworkProduct=" + workProductworkProduct + ", dependability="
			+ dependability + ", cooperativeness=" + cooperativeness + ", adaptability=" + adaptability
			+ ", communication=" + communication + ", dailyDecisionMakingProblemSolving="
			+ dailyDecisionMakingProblemSolving + ", serviceToPublicClients=" + serviceToPublicClients
			+ ", useofEquipmentAndMaterials=" + useofEquipmentAndMaterials + ", projectPlanningAndImplementation="
			+ projectPlanningAndImplementation + ", workGroupManagement=" + workGroupManagement
			+ ", performancePlanningAndReview=" + performancePlanningAndReview + ", commments=" + lisOfComments + "]";
}
public void setAdaptability(Short adaptability) {
	this.adaptability = adaptability;
}
public Short getCommunication() {
	return communication;
}
public void setCommunication(Short communication) {
	this.communication = communication;
}
public Short getDailyDecisionMakingProblemSolving() {
	return dailyDecisionMakingProblemSolving;
}
public void setDailyDecisionMakingProblemSolving(Short dailyDecisionMakingProblemSolving) {
	this.dailyDecisionMakingProblemSolving = dailyDecisionMakingProblemSolving;
}
public Short getServiceToPublicClients() {
	return serviceToPublicClients;
}
public void setServiceToPublicClients(Short serviceToPublicClients) {
	this.serviceToPublicClients = serviceToPublicClients;
}
public Short getUseofEquipmentAndMaterials() {
	return useofEquipmentAndMaterials;
}
public void setUseofEquipmentAndMaterials(Short useofEquipmentAndMaterials) {
	this.useofEquipmentAndMaterials = useofEquipmentAndMaterials;
}
public Short getProjectPlanningAndImplementation() {
	return projectPlanningAndImplementation;
}
public void setProjectPlanningAndImplementation(Short projectPlanningAndImplementation) {
	this.projectPlanningAndImplementation = projectPlanningAndImplementation;
}
public Short getWorkGroupManagement() {
	return workGroupManagement;
}
public void setWorkGroupManagement(Short workGroupManagement) {
	this.workGroupManagement = workGroupManagement;
}
public Short getPerformancePlanningAndReview() {
	return performancePlanningAndReview;
}
public void setPerformancePlanningAndReview(Short performancePlanningAndReview) {
	this.performancePlanningAndReview = performancePlanningAndReview;
}

public  String[] getComments() {
	return COMMENTS;
}
private Short nextYear()
{
	++indxOfYears;
	if (indxOfYears == Years.length)indxOfYears=0 ;
	return Years[indxOfYears] ;
	
}
private Short nextRate()
{
	++indxOfRates;
	if (indxOfRates == Rates.length)indxOfRates=0 ;
	return Rates[indxOfRates] ;
	
}
//COMMENTS
private String nextComment()
{
	++indxOfComment;
	if (indxOfComment == COMMENTS.length)indxOfComment=0 ;
	return COMMENTS[indxOfComment] ;	
}

public List<String> getLisOfComments() {
	return lisOfComments;
}
public void setLisOfComments(List<String> lisOfComments) {
	this.lisOfComments = lisOfComments;
}
public YearReport generatOneReport()
{
	YearReport yearReport = new YearReport() ;
	yearReport.setYear(nextYear());
	yearReport.setWorkProductworkProduct(nextRate()) ;
	yearReport.setDependability(nextRate()) ;
	yearReport.setCooperativeness(nextRate());
	yearReport.setAdaptability(nextRate());
	yearReport.setCommunication(nextRate()) ;
	yearReport.setDailyDecisionMakingProblemSolving(nextRate()); 
	yearReport.setServiceToPublicClients(nextRate()); 
	yearReport.setUseofEquipmentAndMaterials(nextRate());
	yearReport.setProjectPlanningAndImplementation(nextRate());
	yearReport.setWorkGroupManagement(nextRate());
	yearReport.setPerformancePlanningAndReview(nextRate());
	noOfComments-- ;
	if (noOfComments==0)noOfComments=COMMENTS.length-1 ;
	List<String> nComments =generatNComments(noOfComments) ;
	yearReport.setLisOfComments(nComments) ;
	return yearReport ;	
}
public List<YearReport> generatNReport(int noOfReports)	{
	//List<YearReport> lisOfReps = new ArrayList() ;
	List<YearReport> lisOfRep =IntStream.range(0, noOfReports)
            .mapToObj(i -> generatOneReport())  
            .collect(Collectors.toList()); 
//	System.out.println(lisOfRep.get(0)) ;
	return lisOfRep ;
}
//
public List<String> generatNComments(int noOfComments)	{
	//List<YearReport> lisOfReps = new ArrayList() ;
	List<String> lisOfComments =IntStream.range(0, noOfComments)
            .mapToObj(i -> nextComment())  
            .collect(Collectors.toList()); 
//	System.out.println(lisOfRep.get(0)) ;
	return lisOfComments ;
	
}

}