package steps;

import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.Assert.assertTrue;

public class DuckDuckGoSteps {

    WebDriver driver;

    @Given("the user is on the DuckDuckGo homepage")
    public void the_user_is_on_the_duckduckgo_homepage() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-gpu");
        driver = new ChromeDriver(options);
        driver.get("https://duckduckgo.com");
    }

    @When("the user searches for {string}")
    public void the_user_searches_for(String query) {
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys(query);
        searchBox.submit();
    }

    @Then("the page title should contain {string}")
    public void the_page_title_should_contain(String expectedTitle) {
        assertTrue(driver.getTitle().contains(expectedTitle));
        driver.quit();
    }
}
