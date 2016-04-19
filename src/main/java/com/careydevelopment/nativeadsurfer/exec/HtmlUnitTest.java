package com.careydevelopment.nativeadsurfer.exec;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;

public class HtmlUnitTest {

	public HtmlUnitTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
	    final WebClient webClient = new WebClient(BrowserVersion.CHROME);
	    webClient.getOptions().setThrowExceptionOnScriptError(false);
	    webClient.setAjaxController(new NicelyResynchronizingAjaxController()); 
	    webClient.waitForBackgroundJavaScript(30000); 
	    //System.err.println(webClient.getOptions().);
	    
	    try {
	        // Create a new instance of the Firefox driver
	        // Notice that the remainder of the code relies on the interface, 
	        // not the implementation.
	    	WebDriver driver = new FirefoxDriver();

	        // And now use this to visit Google
	    	driver.get("http://www.huffingtonpost.com/entry/trump-clinton-new-york-primary_us_57162031e4b0018f9cbb00a9");
	        // Alternatively the same thing can be done like this
	        // driver.navigate().to("http://www.google.com");

	        // Find the text input element by its name
	        /*WebElement element = driver.findElement(By.name("q"));

	        // Enter something to search for
	        element.sendKeys("Cheese!");
	        
	        

	        // Now submit the form. WebDriver will find the form for us from the element
	        element.submit();

	        // Check the title of the page
	        System.out.println("Page title is: " + driver.getTitle());
	        
	        // Google's search is rendered dynamically with JavaScript.
	        // Wait for the page to load, timeout after 10 seconds
	        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
	            public Boolean apply(WebDriver d) {
	                return d.getTitle().toLowerCase().startsWith("cheese!");
	            }
	        });
			*/
	        // Should see: "cheese! - Google Search"
	        //System.out.println("Page title is: " + driver.getTitle());
	        
	        /*List<WebElement> els = driver.findElements(By.className("ob-widget-section"));
	       
	        for (WebElement el : els) {
	        	System.err.println(el.getText());
	        }*/
	        
	        //List<WebElement> els = driver.findElements(By.className("heading"));
	        
	    	List<WebElement> els = driver.findElements(By.className("trc_rbox_outer"));
	        //List<WebElement> els = driver.findElements(By.className("zerglayoutcl"));
	        for (WebElement el : els) {
	        	System.err.println(el.getText());
	        }
	        
	        //driver.findElements(By.)
	        
	        File file = new File("/etc/tomcat8/resources/filename.txt");

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(driver.getPageSource());
			bw.close();
			
			driver.findElements(By.tagName("a"));
	        
	        //System.err.println(driver.getPageSource());
	        
	        //Close the browser
	        driver.quit();;
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	}

}
