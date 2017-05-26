package config;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import static executionEngine.DriverScript.OR;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import executionEngine.DriverScript;
import utility.ExcelUtils;
import utility.Log;
import utility.ScreenShot;

public class ActionKeywords {
	
	public static WebDriver driver;
	public static String sPageTitle;
	public static String sInstructionId;
	public static String sInstructionStat;
	public static String sInstructionIdDFP;
	public static String sInstructionIdRFP;
	public static int iLength;
	public static String sFirstLetter;
	public static String sSecondLetter;
	public static char cFirst;
	public static char cSecond;
	public static String sFirstLetterDrop;
	public static char cFirstDrop;
	public static String [] aSpliteDate;          
	public static String sExDate;
	public static String sExMonth;
	public static String sExYear;
	public static String sBDate;
	public static String sBusinessDate;
	public static String sExecutionDate;
	public static String sExecutionMonth;
	public static String sExecutionYear;
	public static String sBDateRemoveSpace;
	public static String sBDateWithoutColen;
	public static String [] aBDateRemoveColen;
	public static String [] aBDateRemoveHypen;
	public static String sSale = "OFF MARKET SALE";
	public static String sOther = "OTHERS";
	public static String [] aSplitReasonCodeConsideration;  // Added on 22-10-2016
	public static String sReason;							// Added on 22-10-2016
	public static String sConsideration;					// Added on 22-10-2016
	public static String sReasonDrop;						// Added on 22-10-2016
	public static String sAlertText;						// Added on 22-10-2016
	public static boolean bFoundAlert;						// Added on 22-10-2016
	public static String sPrefix;							// Added on 22-10-2016
	public static String sSuffix;							// Added on 22-10-2016
	public static String sPrefix2;							// Added on 22-10-2016
	public static String sSuffix2;							// Added on 22-10-2016
	public static WebElement webElement;
	public static WebElement element;
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
	public static String inputInstructionId;
	public static String expectedInstructionStatus;
	public static String actualInstructionStatus;
	
	public static void openBrowser(String object,String data){
		try{
			Log.info("Opening Browser");
			System.setProperty("webdriver.ie.driver",Constants.IEDriver_Path);
			driver = new InternetExplorerDriver();
			driver.manage().window().maximize();
			Thread.sleep(6000);
		}catch(Exception e){
			Log.info("Not able to Open Browser : " +e.getMessage());
			DriverScript.bResult=false;
		}
	}
	
	public static void navigate(String object,String data){
		try{
			Log.info("Navigating to URL");
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.get(data);
			Thread.sleep(6000);
			Runtime.getRuntime().exec("E:\\QK_ManojG Data\\New folder\\JavaWarning.exe");
			ScreenShot.takeScreenShot(driver);
			sPageTitle = driver.getTitle();
			if(sPageTitle.equalsIgnoreCase("Certificate Error: Navigation Blocked")){
				driver.navigate().to("javascript:document.getElementById('overridelink').click");
			}
		}catch(Exception e){
			Log.info("Not able to navigate to URL : " +e.getMessage());
			DriverScript.bResult=false;
		}
	}
	
	public static void input(String object,String data){
		try{
			Log.info("Entering Input Data : " +object);
			ActionKeywords.isElementLoaded(object);
			webElement.clear();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			webElement.sendKeys(data);
			ScreenShot.takeScreenShot(driver);
		}catch(Exception e){
			Log.error("Not able to input Input data : "+e.getMessage());
			DriverScript.bResult=false;
		}
	}
	
	public static void click(String object,String data){
		try{
			Log.info("Clicking on Button or link : " +object);
		if(data.equalsIgnoreCase("N") || data.equalsIgnoreCase("looseslip")){
			Log.info("+++++DIS Loose Slip selected+++++");
			ActionKeywords.isElementLoaded(object);
			webElement.click();
			//System.out.println("New DIS selected");
			ScreenShot.takeScreenShot(driver);
		}else if(data.equalsIgnoreCase("Y") || data.equalsIgnoreCase("booklet")){
			Log.info("++++DIS Booklet selected++++");
			ScreenShot.takeScreenShot(driver);
		}else if (object.contains("link") || object.contains("rdb")){
			ActionKeywords.isElementLoaded(object);
			webElement.click();
			ScreenShot.takeScreenShot(driver);
		}else{
			ActionKeywords.isElementLoaded(object);
			webElement.click();
			ScreenShot.takeScreenShot(driver);
			ActionKeywords.isAlertPresent(driver);			//Added on 22-10-2016 to click on alert
			while(bFoundAlert){								//Added on 22-10-2016 to click on alert
	 			ActionKeywords.isAlertPresent(driver);	
			}
			}if(object.equals("btn_LogIn")){													// Added on 22-10-2016 for switchWindow
				Log.info("----Switching driver to new window----");
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
					for (String winHandle : driver.getWindowHandles()) {
						driver.switchTo().window(winHandle);
			}
		}
		}catch(Exception e){
			Log.error("Element not clickable : "+e.getMessage());
			DriverScript.bResult=false;
		}
	}
	
	public static void input_InstructionId(String object,String data){
		try{
			Log.info("Entering DFP Instruction ID : " +object);
			//Log.info("Instructrion ID for Input : "+sInstructionIdDFP);
			ActionKeywords.isElementLoaded(object);
			//webElement.sendKeys(sInstructionIdDFP);
			
			int iTotalTestCases = ExcelUtils.getRowCount(Constants.Sheet_TestCases);
			for(int iTestcase=1;iTestcase<iTotalTestCases;iTestcase++){
				//bResult = true;
				sTestCaseID = ExcelUtils.getCellData(iTestcase, Constants.Col_TestCaseID, Constants.Sheet_TestCases); 
				//System.out.println("s Test Case ID = "+sTestCaseID);
				sRunMode = ExcelUtils.getCellData(iTestcase, Constants.Col_RunMode,Constants.Sheet_TestCases);
				if (sRunMode.equals("Yes")){
					Log.startTestCase(sTestCaseID);
					iTestStep = ExcelUtils.getRowContains(sTestCaseID, Constants.Col_TestCaseID, sTestCaseID);
					//System.out.println("iTest step = "+iTestStep);
					iTestLastStep = ExcelUtils.getTestStepsCount(sTestCaseID, sTestCaseID, iTestStep);
					//bResult=true;
					for (;iTestStep<iTestLastStep;iTestStep++){
						sActionKeyword = ExcelUtils.getCellData(iTestStep, Constants.Col_ActionKeyword,sTestCaseID);
			    		//System.out.println("sAction keyword = "+sActionKeyword);
			    		sPageObject = ExcelUtils.getCellData(iTestStep, Constants.Col_PageObject, sTestCaseID);
			    		sData = ExcelUtils.getCellData(iTestStep, Constants.Col_DataSet, sTestCaseID);
			    		//System.out.println("s Test Data = "+sData);
						if(sActionKeyword.equals("getInstructionId")){
							//ExcelUtils.setCellData(id,iTestStep,Constants.Col_instructionId,sTestCaseID);
							inputInstructionId = ExcelUtils.getCellData(iTestStep, Constants.Col_instructionId, sTestCaseID);
							Log.info("Instructrion ID for Input : "+inputInstructionId);
							webElement.sendKeys(inputInstructionId);
							//break;
						}						
					}
				}
			}
			DriverScript.bResult=true;
			ScreenShot.takeScreenShot(driver);
		}catch(Exception e){
			Log.error("Not able to input instruction ID : "+e.getMessage());
			DriverScript.bResult=false;
		}
	}
	
	public static void input_InstructionIdRfp(String object,String data){
		try{
			Log.info("Entering RFP Instruction ID : " +object);
			Log.info("Instructrion ID for Input : "+sInstructionIdRFP);
			ActionKeywords.isElementLoaded(object);
			
			int iTotalTestCases = ExcelUtils.getRowCount(Constants.Sheet_TestCases);
			for(int iTestcase=1;iTestcase<iTotalTestCases;iTestcase++){
				//bResult = true;
				sTestCaseID = ExcelUtils.getCellData(iTestcase, Constants.Col_TestCaseID, Constants.Sheet_TestCases); 
				//System.out.println("s Test Case ID = "+sTestCaseID);
				sRunMode = ExcelUtils.getCellData(iTestcase, Constants.Col_RunMode,Constants.Sheet_TestCases);
				if (sRunMode.equals("Yes")){
					Log.startTestCase(sTestCaseID);
					iTestStep = ExcelUtils.getRowContains(sTestCaseID, Constants.Col_TestCaseID, sTestCaseID);
					//System.out.println("iTest step = "+iTestStep);
					iTestLastStep = ExcelUtils.getTestStepsCount(sTestCaseID, sTestCaseID, iTestStep);
					//bResult=true;
					for (;iTestStep<iTestLastStep;iTestStep++){
						sActionKeyword = ExcelUtils.getCellData(iTestStep, Constants.Col_ActionKeyword,sTestCaseID);
			    		//System.out.println("sAction keyword = "+sActionKeyword);
			    		sPageObject = ExcelUtils.getCellData(iTestStep, Constants.Col_PageObject, sTestCaseID);
			    		sData = ExcelUtils.getCellData(iTestStep, Constants.Col_DataSet, sTestCaseID);
			    		//System.out.println("s Test Data = "+sData);
						if(sActionKeyword.equals("getInstructionIdRfp")){
							//ExcelUtils.setCellData(id,iTestStep,Constants.Col_instructionId,sTestCaseID);
							inputInstructionId = ExcelUtils.getCellData(iTestStep, Constants.Col_instructionId, sTestCaseID);
							Log.info("Instructrion ID for Input : "+inputInstructionId);
							webElement.sendKeys(inputInstructionId);
							//break;
						}						
					}
				}
			}
			
			//webElement.sendKeys(sInstructionIdRFP);
			ScreenShot.takeScreenShot(driver);
			DriverScript.bResult=true;
		}catch(Exception e){
			Log.error("Not able to input instruction ID : "+e.getMessage());
			DriverScript.bResult=false;
		}
	}

	public static void select_DropDown(String object, String data){ 							 // Added on 22-10-2016 Added validation for Reason Purpose Fields
		try{
			Log.info("Entered select_DropDown : " +object);
			sFirstLetterDrop = String.valueOf(data.charAt(0));
			cFirstDrop = sFirstLetterDrop.charAt(0);
			//System.out.println("First character is : "+cFirstDrop);
			if(object.contains(";")){
				aSplitReasonCodeConsideration = object.split(";");
				sReasonDrop = aSplitReasonCodeConsideration[0];
				sConsideration = aSplitReasonCodeConsideration[1];
				sReason = aSplitReasonCodeConsideration[2];
				ActionKeywords.isElementLoaded(sReasonDrop);
				Select selectReasonDropDown = new Select(webElement);
				if(Character.isDigit(cFirstDrop)){
					Log.info("selecting dropdown by Value: " +data);
					selectReasonDropDown.selectByValue(data);
					ScreenShot.takeScreenShot(driver);
					if(data.equalsIgnoreCase("1")){
						ActionKeywords.isElementLoaded(sConsideration);
						webElement.sendKeys(Constants.consideration);
						ScreenShot.takeScreenShot(driver);
					}else if(data.equalsIgnoreCase("99")){
						ActionKeywords.isElementLoaded(sReason);
						webElement.sendKeys(Constants.reason);
						ScreenShot.takeScreenShot(driver);
					}
				}else if(Character.isLetter(cFirstDrop)){
						Log.info("selecting dropdown by Visible Text: " +data);
						selectReasonDropDown.selectByVisibleText(data);
						ScreenShot.takeScreenShot(driver);
						if(data.equalsIgnoreCase(sSale)){
							ActionKeywords.isElementLoaded(sConsideration);
							webElement.sendKeys(Constants.consideration);
							ScreenShot.takeScreenShot(driver);
						}else if(data.equalsIgnoreCase(sOther)){
							ActionKeywords.isElementLoaded(sReason);
							webElement.sendKeys(Constants.reason);
							ScreenShot.takeScreenShot(driver);
						}else{
							Log.info("Entered data is not valid");
							DriverScript.bResult=false;
						}
				}
				}else{
					ActionKeywords.isElementLoaded(object);
					Select selectDropDown = new Select(webElement);
					if(Character.isDigit(cFirstDrop)){
						Log.info("selecting dropdown by Value: " +object);
						selectDropDown.selectByValue(data);
						ScreenShot.takeScreenShot(driver);
					}else if(Character.isLetter(cFirstDrop)){
						Log.info("selecting dropdown by Visible Text: " +object);
						selectDropDown.selectByVisibleText(data);
						ScreenShot.takeScreenShot(driver);
				}else{
					Log.info("Entered data is not valid");
					DriverScript.bResult=false;
				}
			}
		}catch(Exception e){
			Log.error("Unable to select dropdown : "+e.getMessage());
			DriverScript.bResult=false;
		}
	}
	
	public static void executionDate(String object, String data){
		try{
			Log.info("**** Entering Execution Date ****");
			aSpliteDate = object.split(";");
			sExDate = aSpliteDate[0];
			sExMonth= aSpliteDate[1];
			sExYear= aSpliteDate[2];
			sBDate = aSpliteDate[3];
			ActionKeywords.isElementLoaded(sBDate);
			sBusinessDate = webElement.getText();
			sBDateRemoveSpace = sBusinessDate.replaceAll("\\s+","");
			aBDateRemoveColen = sBDateRemoveSpace.split(":");
			sBDateWithoutColen = aBDateRemoveColen[1];
			aBDateRemoveHypen = sBDateWithoutColen.split("-");
			sExecutionDate = aBDateRemoveHypen[0];
			sExecutionMonth = aBDateRemoveHypen[1];
			sExecutionYear = aBDateRemoveHypen[2];
			ActionKeywords.isElementLoaded(sExDate);
			webElement.sendKeys(sExecutionDate);
			ActionKeywords.isElementLoaded(sExMonth);
			webElement.sendKeys(sExecutionMonth);
			ActionKeywords.isElementLoaded(sExYear);
			webElement.sendKeys(sExecutionYear);
		}catch(Exception e){
			Log.error("Unable to Enter Execution Date : "+e.getMessage());
			DriverScript.bResult=false;
		}
	}
	
	public static void getInstructionId(String object, String data){
		try{
			Log.info("---Getting DFP Instruction ID---");
			driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
			if(DriverScript.sTestCaseID.contains("IDT")){
				ActionKeywords.isElementLoaded(object);
				sInstructionIdDFP = webElement.getAttribute("value");
				//System.out.println("Instruction ID : " +sInstructionIdDFP);				
		
			}else{
			ActionKeywords.isElementLoaded(object);
			sInstructionIdDFP = webElement.getText();
			String id = sInstructionIdDFP.toString();
			int iTotalTestCases = ExcelUtils.getRowCount(Constants.Sheet_TestCases);
			for(int iTestcase=1;iTestcase<iTotalTestCases;iTestcase++){
				//bResult = true;
				sTestCaseID = ExcelUtils.getCellData(iTestcase, Constants.Col_TestCaseID, Constants.Sheet_TestCases); 
				//System.out.println("s Test Case ID = "+sTestCaseID);
				sRunMode = ExcelUtils.getCellData(iTestcase, Constants.Col_RunMode,Constants.Sheet_TestCases);
				if (sRunMode.equals("Yes")){
					Log.startTestCase(sTestCaseID);
					iTestStep = ExcelUtils.getRowContains(sTestCaseID, Constants.Col_TestCaseID, sTestCaseID);
					//System.out.println("iTest step = "+iTestStep);
					iTestLastStep = ExcelUtils.getTestStepsCount(sTestCaseID, sTestCaseID, iTestStep);
					//bResult=true;
					for (;iTestStep<iTestLastStep;iTestStep++){
						sActionKeyword = ExcelUtils.getCellData(iTestStep, Constants.Col_ActionKeyword,sTestCaseID);
			    		//System.out.println("sAction keyword = "+sActionKeyword);
			    		sPageObject = ExcelUtils.getCellData(iTestStep, Constants.Col_PageObject, sTestCaseID);
			    		sData = ExcelUtils.getCellData(iTestStep, Constants.Col_DataSet, sTestCaseID);
			    		//System.out.println("s Test Data = "+sData);
						if(sActionKeyword.equals("getInstructionId")){
							ExcelUtils.setCellData(id,iTestStep,Constants.Col_instructionId,sTestCaseID);
							//break;
						}						
					}
				}
			}
			Log.info("Instruction ID Stored : "+sInstructionIdDFP);
			DriverScript.bResult=true;
		}																											//Added on 23-10-2016 Log info
		}catch(Exception e){
			Log.error("Unable to get Instruction ID : "+e.getMessage());
			DriverScript.bResult=false;
		}
	}
	
	public static void getInstructionIdRfp(String object, String data){
		try{
			Log.info("---Getting RFP Instruction ID---");
			driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
			if(DriverScript.sTestCaseID.contains("IDT")){
				ActionKeywords.isElementLoaded(object);
				sInstructionIdRFP = webElement.getAttribute("value");
				//System.out.println("Instruction ID : " +sInstructionIdRFP);
			}else{
			ActionKeywords.isElementLoaded(object);
			sInstructionIdRFP = webElement.getText();
			String idRfp = sInstructionIdRFP.toString();
			int iTotalTestCases = ExcelUtils.getRowCount(Constants.Sheet_TestCases);
			for(int iTestcase=1;iTestcase<iTotalTestCases;iTestcase++){
				//bResult = true;
				sTestCaseID = ExcelUtils.getCellData(iTestcase, Constants.Col_TestCaseID, Constants.Sheet_TestCases); 
				//System.out.println("s Test Case ID = "+sTestCaseID);
				sRunMode = ExcelUtils.getCellData(iTestcase, Constants.Col_RunMode,Constants.Sheet_TestCases);
				if (sRunMode.equals("Yes")){
					Log.startTestCase(sTestCaseID);
					iTestStep = ExcelUtils.getRowContains(sTestCaseID, Constants.Col_TestCaseID, sTestCaseID);
					//System.out.println("iTest step = "+iTestStep);
					iTestLastStep = ExcelUtils.getTestStepsCount(sTestCaseID, sTestCaseID, iTestStep);
					//bResult=true;
					for (;iTestStep<iTestLastStep;iTestStep++){
						sActionKeyword = ExcelUtils.getCellData(iTestStep, Constants.Col_ActionKeyword,sTestCaseID);
			    		//System.out.println("sAction keyword = "+sActionKeyword);
			    		sPageObject = ExcelUtils.getCellData(iTestStep, Constants.Col_PageObject, sTestCaseID);
			    		sData = ExcelUtils.getCellData(iTestStep, Constants.Col_DataSet, sTestCaseID);
			    		//System.out.println("s Test Data = "+sData);
						if(sActionKeyword.equals("getInstructionIdRfp")){
							ExcelUtils.setCellData(idRfp,iTestStep,Constants.Col_instructionId,sTestCaseID);
							//break;
							}						
						}
				}
			}
			
			Log.info("Instruction ID Stored : "+sInstructionIdRFP);	
			DriverScript.bResult=true;
			}
		}catch(Exception e){
			Log.error("Unable to get Instruction ID : "+e.getMessage());
			DriverScript.bResult=false;
		}
	}
	
	public static void getInstructionStatus(String object, String data){
		try{
			Log.info("---Getting Instruction Status----");
			ActionKeywords.isElementLoaded(object);
			actualInstructionStatus = driver.findElement(By.xpath(OR.getProperty(object))).getText();
			
			int iTotalTestCases = ExcelUtils.getRowCount(Constants.Sheet_TestCases);
			for(int iTestcase=1;iTestcase<iTotalTestCases;iTestcase++){
				//bResult = true;
				sTestCaseID = ExcelUtils.getCellData(iTestcase, Constants.Col_TestCaseID, Constants.Sheet_TestCases); 
				//System.out.println("s Test Case ID = "+sTestCaseID);
				sRunMode = ExcelUtils.getCellData(iTestcase, Constants.Col_RunMode,Constants.Sheet_TestCases);
				if (sRunMode.equals("Yes")){
					Log.startTestCase(sTestCaseID);
					iTestStep = ExcelUtils.getRowContains(sTestCaseID, Constants.Col_TestCaseID, sTestCaseID);
					//System.out.println("iTest step = "+iTestStep);
					iTestLastStep = ExcelUtils.getTestStepsCount(sTestCaseID, sTestCaseID, iTestStep);
					//bResult=true;
					for (;iTestStep<iTestLastStep;iTestStep++){
						sActionKeyword = ExcelUtils.getCellData(iTestStep, Constants.Col_ActionKeyword,sTestCaseID);
			    		//System.out.println("sAction keyword = "+sActionKeyword);
			    		sPageObject = ExcelUtils.getCellData(iTestStep, Constants.Col_PageObject, sTestCaseID);
			    		sData = ExcelUtils.getCellData(iTestStep, Constants.Col_DataSet, sTestCaseID);
			    		//System.out.println("s Test Data = "+sData);
						if(sActionKeyword.equals("getInstructionStatus")){
							//ExcelUtils.setCellData(id,iTestStep,Constants.Col_instructionId,sTestCaseID);
							expectedInstructionStatus = ExcelUtils.getCellData(iTestStep, Constants.Col_ExpectedResult, sTestCaseID);
							ExcelUtils.setCellData(actualInstructionStatus,iTestStep,Constants.Col_ActualResult,sTestCaseID);
							Log.info("Instructrion ID for Input : "+expectedInstructionStatus);
							//webElement.sendKeys(inputInstructionId);
							}						
						}
				}
			}
				if(expectedInstructionStatus.equalsIgnoreCase(actualInstructionStatus)){
					Log.info("Instruction Status is : "+actualInstructionStatus);
					DriverScript.bResult=true;		
				}else{
					DriverScript.bResult=false;
					AlertWindow.infoBox(actualInstructionStatus, "Instruction status is : ");
				}
			}catch(Exception e){
				Log.error("Unable to get Instruction ID : "+e.getMessage());
				DriverScript.bResult=false;
			}
	}
	
	public static void logOut(String object, String data){
		try{
			Log.info("=== Logging Out ===");
			ActionKeywords.isElementLoaded(object);
			webElement.click();
			ActionKeywords.isAlertPresent(driver);				//Added on 22-10-2016 to click on alert
			/*Runtime.getRuntime().exec("taskkill /F /IM IEDriverServer.exe");
			Thread.sleep(10);*/
		}catch(Exception e){
			Log.info("Logout error : "+e.getMessage());
			DriverScript.bResult=false;
		}
	}
	
	public static void disHandle(String object, String data){
		try{
			Log.info("**Entering DIS serial number**");
			iLength = data.length();
		if(object.equalsIgnoreCase("Y") || object.equalsIgnoreCase("new")){
			sFirstLetter = String.valueOf(data.charAt(0));
			sSecondLetter = String.valueOf(data.charAt(1));
			cFirst = sFirstLetter.charAt(0);
			cSecond = sSecondLetter.charAt(0);
		if(Character.isLetter(cFirst)&& Character.isLetter(cSecond)){
			Log.info("Entering New DIS serial number");
			sPrefix = data.substring(0, 2);							// changed string name 23-10-2016
			sSuffix = data.substring(2, iLength);					// changed string name 23-10-2016
			ActionKeywords.isElementLoaded("prefixNew");
			webElement.sendKeys(sPrefix);
			ActionKeywords.isElementLoaded("suffixNew");
			webElement.sendKeys(sSuffix);
		}else if(Character.isLetter(cFirst) && Character.isDigit(cSecond)){
			Log.info("Entering New DIS serial number");
			//System.out.println(cFirst+"  ---  "+cSecond);
			sPrefix2 = data.substring(0, 1);						// changed string name 23-10-2016
			sSuffix2 = data.substring(1, iLength);					// changed string name 23-10-2016
			ActionKeywords.isElementLoaded("prefixNew");
			webElement.sendKeys(sPrefix2);
			ActionKeywords.isElementLoaded("suffixNew");
			webElement.sendKeys(sSuffix2);
		}else if(Character.isDigit(cFirst)){
			Log.info("Entering New DIS serial number ");
			ActionKeywords.isElementLoaded("suffixNew");
			webElement.sendKeys(data);
		}
		}if(object.equalsIgnoreCase("N") || object.equalsIgnoreCase("old")){
			Log.info("Entering Old DIS serial number");
			ActionKeywords.isElementLoaded("chk_oldDis");
			webElement.click();
			ActionKeywords.isElementLoaded("oldDisSerial");
			webElement.sendKeys(data);
		}else{
			Log.info("Entered DIS serial number is not in correct format");
			//System.out.println("Enter valid DIS serial number");
		}
		}catch(Exception e){
			Log.info("Not able to Input DIS serial number "+e.getMessage());
			DriverScript.bResult=false;
			}
	}
	
	public static void closeBrowser(String string, String string2) {
		try{
			driver.close();
			Runtime.getRuntime().exec("taskkill /F /IM IEDriverServer.exe");
			Thread.sleep(10);
		}catch(Exception e){
			Log.info("Not able to close browser "+e.getMessage());
			DriverScript.bResult=false;
		}
	}
	
	public static boolean isAlertPresent(WebDriver adriver){  // Added on 22-10-2016 for alert search
		  	//Log.info("++ Switching to Alert ++");
			bFoundAlert = false;
			WebDriverWait wait = new WebDriverWait(adriver, 4);
		try { 
			 	wait.until(ExpectedConditions.alertIsPresent());
			 	Alert alert = adriver.switchTo().alert();
	 			sAlertText = alert.getText();
	 			if(sAlertText.contains("Your Password will be Expired")){
	 				alert.dismiss();
	 				Log.info(sAlertText+" - Alert dismissed -");
	 				bFoundAlert = true; 
	 			}else{
	 				alert.accept();
	 				Log.info(sAlertText+" - Alert accepted -");
	 				Thread.sleep(10);
	 				bFoundAlert = true; 	
			 	}		
	        }catch (Exception e){
	        	bFoundAlert = false; 
	        }
		return bFoundAlert;
	}
	
	public static WebElement isElementLoaded(String obj) {
		webElement = driver.findElement(By.xpath(OR.getProperty(obj)));
	    WebDriverWait wait = new WebDriverWait(driver, 300);
	    element = wait.until(ExpectedConditions.visibilityOf(webElement));
	    return webElement;
	}
	
	
	/*public static  instructionIdReadWrite(){
		
		
		return ;
		
	}*/
	
}
