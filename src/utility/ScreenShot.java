package utility;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import config.Constants;

public class ScreenShot {
	
	public static void takeScreenShot(WebDriver sDriver) throws IOException{
		/*try{
		File screen = ((TakesScreenshot)sDriver).getScreenshotAs(OutputType.FILE);
		File destination = new File(Constants.Screen_Path+System.currentTimeMillis()+".png");
		FileUtils.copyFile(screen, destination);
		}catch(Exception e){
			Log.info("Not able to capture screen shot : "+e.getMessage());
		}*/
		Log.info("Entering into takeScreenShot");
		try {
			Robot robot = new Robot();
			BufferedImage image = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
			ImageIO.write(image, "JPG",new File(Constants.Screen_Path + "\\" + System.currentTimeMillis() + ".jpg"));
		} catch (Exception e) {
			Log.error("Not able to capture screen : "+e.getMessage());
			Log.info("Getting full screen Image");
			File screenshot = ((TakesScreenshot)sDriver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screenshot, new File(Constants.Screen_Path+"\\"+System.currentTimeMillis()+".jpg"));
		}
	}
}
