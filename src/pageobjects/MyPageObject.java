package pageobjects;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.provar.core.testapi.annotations.LinkType;
import com.provar.core.testapi.annotations.Page;
import com.provar.core.testapi.annotations.TextType;

@Page( title="MyPageObject"                                
, summary=""
, relativeUrl=""
, connection="Presonnel"
		)   

public class MyPageObject {
	public WebDriver driver;
	public Logger testlogger;
	public MyPageObject(WebDriver driver){
		this.driver=driver;
	}
	public void uploadMultipleFiles(String file) throws InterruptedException {
		List<WebElement> fileInputs = driver.findElements(By.xpath("//input[@type='file']"));

		// Get all corresponding Done buttons
		List<WebElement> doneButtons = driver.findElements(By.xpath("//button[text()='Done']"));

		for (int i = 0; i < fileInputs.size(); i++) {
			WebElement fileInput = fileInputs.get(i);
			fileInput.sendKeys(file); // Upload the same file

			Thread.sleep(2000); // Simulate upload time

			WebElement doneBtn = doneButtons.get(i);
			doneBtn.click();
		}
	}




	public String getDate(String InputDate) {
		// Input date and format
		String inputDate ; // Example input date in M/d/YYYY format
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

		// Parse the input date
		LocalDate date = LocalDate.parse(InputDate, formatter);

		// Define the list of holidays
		List<LocalDate> holidays = Arrays.asList(
				LocalDate.of(2024, 11, 25), // Christmas
				LocalDate.of(2024, 11, 27), 
				LocalDate.of(2024, 11, 26) // Thanksgiving
				);

		// Perform the working day calculation
		int daysToAdd = 10;
		while (daysToAdd > 0) {
			date = date.plusDays(1);
			if (!(date.getDayOfWeek().getValue() == 6 || date.getDayOfWeek().getValue() == 7 || holidays.contains(date))) {
				daysToAdd--;
			}
		}

		// Format the resulting date
		String resultDate = date.format(formatter);
		return resultDate;	
	}
	public int RowCount() {
		List<WebElement> tableRows = driver.findElements(By.xpath("//td[normalize-space()='John Doe']"));
		int a= tableRows.size();
		return a;


	}

	public String getFutureDate(String currentDateStr, List<String> holidays) throws ParseException {
		// Define date format
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

		// Parse the current date
		Date currentDate = dateFormat.parse(currentDateStr);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);

		// Convert list of holiday strings to a set of Dates for faster lookup
		Set<Date> holidaySet = new HashSet<>();
		for (String holidayStr : holidays) {
			holidaySet.add(dateFormat.parse(holidayStr));
		}

		int daysAdded = 0;

		// Loop until we add 10 working days
		while (daysAdded < 10) {
			// Move to the next day
			calendar.add(Calendar.DAY_OF_MONTH, 1);

			// Check if it's a weekend
			int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
			if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
				continue; // Skip weekends
			}

			// Check if it's a holiday
			Date currentDay = calendar.getTime();
			if (holidaySet.contains(currentDay)) {
				continue; // Skip holidays
			}

			// It's a valid working day
			daysAdded++;
		}

		// Return the final date after adding 10 working days
		return dateFormat.format(calendar.getTime());
	}

	public LocalDate calculateFutureDate(LocalDate startDate, Set<LocalDate> usHolidays) {
		LocalDate futureDate = startDate;
		int daysAdded = 0;

		while (daysAdded < 10) {
			futureDate = futureDate.plusDays(1);

			// Check if the day is a weekend or a holiday
			if (!(futureDate.getDayOfWeek() == DayOfWeek.SATURDAY || 
					futureDate.getDayOfWeek() == DayOfWeek.SUNDAY || 
					usHolidays.contains(futureDate))) {
				daysAdded++;
			}
		}

		return futureDate;
	}

	public void randomClick() throws InterruptedException {
		WebElement button = driver.findElement(By.id("openPopupBtn"));
		button.click();

		// Handle the popup (alert)
		Alert alert = driver.switchTo().alert();
		Thread.sleep(3000);
		alert.accept(); // Accept the popup
		Thread.sleep(3000);

		// Store the current window handle for later use
		String originalWindow = driver.getWindowHandle();
		// testlogger.info(originalWindow);


		// After accepting the alert, a new window or tab should open
		Set<String> windowHandles = driver.getWindowHandles();       
		for (String windowHandle : windowHandles) {
			if (!windowHandle.equals(originalWindow)) {
				driver.switchTo().window(windowHandle);
				Thread.sleep(3000);

				//driver.findElement(By.xpath("//button[text()='Click Me in New Window']")).click();
				Thread.sleep(3000);
				driver.close();
			}
		}

		// Switch back to the original browser window
		driver.switchTo().window(originalWindow);
		Thread.sleep(3000);

		// Perform any other actions on the original window
		driver.findElement(By.id("openPopupBtn")).click();

	}


	@TextType()
	@FindBy(xpath = "//div[contains(@class,'active') and contains(@class,'oneWorkspace') and (ancestor::div[contains(@class,'active') and contains(@class,'main-content')]//div[contains(@class, 'oneGlobalNav') or contains(@class, 'tabBarContainer')]//div[contains(@class, 'tabContainer') and contains(@class, 'active')] )]//records-highlights-details-item[1]//p[2]/slot/lightning-formatted-text")
	public WebElement Type;
	@TextType()
	@FindBy(xpath = "//input[@name='SLAExpirationDate__c']")
	public WebElement sLAExpirationDateNew;
	@LinkType()
	@FindBy(xpath = "//div[contains(@class, 'active') and contains(@class, 'open') and (contains(@class, 'forceModal') or contains(@class, 'uiModal'))][last()]//a[normalize-space(.)='New']")
	public WebElement Status;

}
