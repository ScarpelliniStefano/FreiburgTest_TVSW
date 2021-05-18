// Generated by Selenium IDE
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;
public class NewPatientCorrectTest {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  @Before
  public void setUp() {
    driver = new ChromeDriver();
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }
  @After
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void newPatientCorrect() {
    driver.get("http://localhost:8888/#!doctor");
    driver.findElement(By.cssSelector("#gwt-uid-3 > .v-captiontext")).click();
    driver.findElement(By.cssSelector(".v-textfield-focus")).click();
    driver.findElement(By.cssSelector(".v-textfield-focus")).sendKeys("Giovanni");
    driver.findElement(By.cssSelector(".v-textfield-focus")).sendKeys("Rossi");
    driver.findElement(By.cssSelector(".v-datefield-textfield")).click();
    driver.findElement(By.cssSelector(".v-datefield-button")).click();
    driver.findElement(By.cssSelector(".v-button-prevyear")).click();
    driver.findElement(By.id("PID_VAADIN_POPUPCAL-1-4")).click();
    driver.findElement(By.cssSelector(".v-gridlayout-slot > .v-button")).click();
    {
      List<WebElement> elements = driver.findElements(By.cssSelector(".v-grid-row-stripe > .v-grid-cell:nth-child(1)"));
      assert(elements.size() > 0);
    }
  }
}
