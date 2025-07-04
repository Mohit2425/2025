package pageobjects;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import  org.openqa.selenium.WebElement;
import com.provar.core.model.base.api.IRuntimeConfiguration;
import com.provar.core.testapi.ILoginPage;
import com.provar.core.testapi.ILoginResult;
import com.provar.core.testapi.SfLoginResult;
import com.provar.core.testapi.annotations.ButtonType;
import com.provar.core.testapi.annotations.LinkType;
import com.provar.core.testapi.annotations.Page;
import com.provar.core.testapi.annotations.TextType;

@Page( title="Sso New"                                
     , summary=""
     , relativeUrl=""
     , connection="Presonnel"
     )             
public class SsoNew implements ILoginPage{
  @Override
  public ILoginResult doLogin(IRuntimeConfiguration runtimeConfiguration, WebDriver driver,
      Map<String, String> credentials) {
    // Get the user name and password.
    String userName = credentials.get(CREDENTIAL_USER);
    String password = credentials.get(CREDENTIAL_PASSWORD);
    // Submit the SSO form.
    try {
		Thread.sleep(3000);
	} catch (InterruptedException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
    driver.findElement(By.xpath("//input[@id='emailTextInput']")).clear();
    driver.findElement(By.xpath("//input[@id='emailTextInput']")).sendKeys(userName);
    try {
		Thread.sleep(3000);
	} catch (InterruptedException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
   driver.findElement(By.xpath("//input[@id='nextButton']")).click();
    //driver.findElement(By.xpath("//input[@id='Login']")).click();
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return new SfLoginResult(true, null, driver);
  }

@TextType()
@FindBy(xpath = "//input[@name='SLAExpirationDate__c']")
public WebElement sLAExpirationDate;
@TextType()
@FindBy(xpath = "//div[contains(@class,'active') and contains(@class,'oneWorkspace') and (ancestor::div[contains(@class,'active') and contains(@class,'main-content')]//div[contains(@class, 'oneGlobalNav') or contains(@class, 'tabBarContainer')]//div[contains(@class, 'tabContainer') and contains(@class, 'active')] )]//input[@id='input-433']")
public WebElement sLAExpirationDateRead;
@LinkType()
@FindBy(xpath = "//div[contains(@class,'split-left')]//tr[12]//a[normalize-space(.)='Adobe PDFclient']")
public WebElement Adobe_PDFclient;
@TextType()
@FindBy(xpath = "//div[contains(@class, 'active') and contains(@class, 'open') and (contains(@class, 'forceModal') or contains(@class, 'uiModal'))][last()]//div[1]/img")
public WebElement Pdf;
@ButtonType()
@FindBy(xpath = "//div[contains(@class, 'active') and contains(@class, 'open') and (contains(@class, 'forceModal') or contains(@class, 'uiModal'))][last()]//button[normalize-space(.)='Download']")
public WebElement download;
@TextType()
@FindBy(xpath = "//div[contains(@class,'active') and contains(@class,'oneContent')]//span/force-record-avatar/span/img")
public WebElement a;
}
		
