package executionEngine;

import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.Properties;

import org.apache.log4j.xml.DOMConfigurator;

import config.ActionKeywords;
import config.AlertWindow;
import config.Constants;
import utility.ExcelUtils;
import utility.Log;
 
public class DriverScript {
	
	public static Properties OR;
	public static ActionKeywords actionKeywords;
	public static String sActionKeyword;
	public static String sPageObject;
	public static Method method[];	
	public static int iTestStep;
	public static int iTestLastStep;
	public static String sTestCaseID;
	public static String sRunMode;
	public static String sData;
	public static boolean bResult;
	
	
	public DriverScript() throws NoSuchMethodException, SecurityException{
		actionKeywords = new ActionKeywords();
		method = actionKeywords.getClass().getMethods();	
	}
	
    public static void main(String[] args) throws Exception {
    	ExcelUtils.setExcelFile(Constants.Path_TestData);
    	DOMConfigurator.configure("log4j.xml");
    	
    	String Path_OR = Constants.Path_OR;
		FileInputStream fs = new FileInputStream(Path_OR);
		OR= new Properties(System.getProperties());
		OR.load(fs);
		
		DriverScript startEngine = new DriverScript();
		startEngine.execute_TestCase();
		
    }
		
    private void execute_TestCase() throws Exception {
	    	int iTotalTestCases = ExcelUtils.getRowCount(Constants.Sheet_TestCases);
			for(int iTestcase=1;iTestcase<iTotalTestCases;iTestcase++){
				bResult = true;
				sTestCaseID = ExcelUtils.getCellData(iTestcase, Constants.Col_TestCaseID, Constants.Sheet_TestCases); 
				//System.out.println("s Test Case ID = "+sTestCaseID);
				sRunMode = ExcelUtils.getCellData(iTestcase, Constants.Col_RunMode,Constants.Sheet_TestCases);
				if (sRunMode.equals("Yes")){
					Log.startTestCase(sTestCaseID);
					iTestStep = ExcelUtils.getRowContains(sTestCaseID, Constants.Col_TestCaseID, sTestCaseID);
					//System.out.println("iTest step = "+iTestStep);
					iTestLastStep = ExcelUtils.getTestStepsCount(sTestCaseID, sTestCaseID, iTestStep);
					bResult=true;
					for (;iTestStep<iTestLastStep;iTestStep++){
						sActionKeyword = ExcelUtils.getCellData(iTestStep, Constants.Col_ActionKeyword,sTestCaseID);
			    		//System.out.println("sAction keyword = "+sActionKeyword);
			    		sPageObject = ExcelUtils.getCellData(iTestStep, Constants.Col_PageObject, sTestCaseID);
			    		sData = ExcelUtils.getCellData(iTestStep, Constants.Col_DataSet, sTestCaseID);
			    		//System.out.println("s Test Data = "+sData);

			    		execute_Actions();
						if(bResult==false){
							ExcelUtils.setCellData(Constants.KEYWORD_FAIL,iTestcase,Constants.Col_Result,Constants.Sheet_TestCases);
							Log.endTestCase(sTestCaseID);
							AlertWindow.infoBox("Test Case Failed : "+sTestCaseID, "Execution Error");  // Added 22-10-2016
							break;
							}						
						}
					if(bResult==true){
					ExcelUtils.setCellData(Constants.KEYWORD_PASS,iTestcase,Constants.Col_Result,Constants.Sheet_TestCases);
					Log.endTestCase(sTestCaseID);
					AlertWindow.infoBox("Test Case Execution Completed ! "+sTestCaseID, "Execution Complete"); //  Added 22-10-2016
						}					
					}
				}
    		}	
     
     private static void execute_Actions() throws Exception {
	
		for(int i=0;i<method.length;i++){
			
			if(method[i].getName().equals(sActionKeyword)){
				method[i].invoke(actionKeywords,sPageObject, sData);
				if(bResult==true){
					ExcelUtils.setCellData(Constants.KEYWORD_PASS, iTestStep, Constants.Col_TestStepResult, sTestCaseID);
					break;
				}else{
					ExcelUtils.setCellData(Constants.KEYWORD_FAIL, iTestStep, Constants.Col_TestStepResult, sTestCaseID);
					ActionKeywords.closeBrowser("","");
					//AlertWindow.infoBox("Test Case Failed : "+sTestCaseID, "Execution Error"); //Removed 22-10-2016
					break;
					}
				}
			}
     }
     
}