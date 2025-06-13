package com.makersacademy.acebook.feature;

import com.github.javafaker.Faker;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SettingsTest {

    WebDriver driver;
    Faker faker;

    @Before
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
        driver = new ChromeDriver();
        faker = new Faker();
    }

    @After
    public void tearDown() {
        driver.close();
    }

    @Test
    public void successfulGetRequestOnSettingsPage() {
        String email = faker.name().username() + "@email.com";
        String givenName = faker.name().firstName();
        String familyName = faker.name().lastName();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("http://localhost:8080/");
        driver.findElement(By.linkText("Login / Sign Up")).click();
        driver.findElement(By.xpath("(//a[text()='Sign Up'])[1]")).click();
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("given_name")).sendKeys(givenName);
        driver.findElement(By.name("family_name")).sendKeys(familyName);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement passwordField = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("1-password"))
        );
        passwordField.sendKeys("Password123");
        driver.findElement(By.className("auth0-label-submit")).click();
        driver.findElement(By.name("action")).click();
        WebElement dropdown = wait.until(
                ExpectedConditions.elementToBeClickable(By.cssSelector("a.nav-link.dropdown-toggle"))
        );
        dropdown.click();
        WebElement settingsLink = wait.until(
                ExpectedConditions.elementToBeClickable(By.linkText("Settings"))
        );
        settingsLink.click();
        String actualTitle = driver.getTitle();
        assertThat(actualTitle, containsString("Settings"));

    }

    @Test
    public void successfulImageUploadToSettingsPage() {
        String email = faker.name().username() + "@email.com";
        String givenName = faker.name().firstName();
        String familyName = faker.name().lastName();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("http://localhost:8080/");
        driver.findElement(By.linkText("Login / Sign Up")).click();
        driver.findElement(By.xpath("(//a[text()='Sign Up'])[1]")).click();
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("given_name")).sendKeys(givenName);
        driver.findElement(By.name("family_name")).sendKeys(familyName);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement passwordField = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("1-password"))
        );
        passwordField.sendKeys("Password123");
        driver.findElement(By.className("auth0-label-submit")).click();
        driver.findElement(By.name("action")).click();

        WebElement dropdown = wait.until(
                ExpectedConditions.elementToBeClickable(By.cssSelector("a.nav-link.dropdown-toggle"))
        );
        dropdown.click();
        WebElement settingsLink = wait.until(
                ExpectedConditions.elementToBeClickable(By.linkText("Settings"))
        );
        settingsLink.click();

        WebDriverWait waitForImageField = new WebDriverWait(driver, Duration.ofSeconds(10));
        waitForImageField.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div/div/div/form/div[2]/input")));

        Path imagePath = Paths.get("src/test/resources/Test_Profile.png");
        WebElement fileInput = driver.findElement(By.name("file"));
        fileInput.sendKeys(imagePath.toAbsolutePath().toString());

        driver.findElement(By.id("submit")).click();

        WebDriverWait waitForImage = new WebDriverWait(driver, Duration.ofSeconds(10));
        waitForImage.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div/div/div/form/div[2]/input")));

        WebElement image = driver.findElement(By.xpath("/html/body/div/div/div/form/div[1]/img"));
        String src = image.getAttribute("src");

        Assertions.assertFalse(src.contains("default.jpg"));
    }

    @Test
    public void successfulNameChangeToSettingsPage() {
        String email = faker.name().username() + "@email.com";
        String givenName = faker.name().firstName();
        String familyName = faker.name().lastName();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("http://localhost:8080/");
        driver.findElement(By.linkText("Login / Sign Up")).click();
        driver.findElement(By.xpath("(//a[text()='Sign Up'])[1]")).click();
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("given_name")).sendKeys(givenName);
        driver.findElement(By.name("family_name")).sendKeys(familyName);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement passwordField = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("1-password"))
        );
        passwordField.sendKeys("Password123");
        driver.findElement(By.className("auth0-label-submit")).click();
        driver.findElement(By.name("action")).click();

        WebElement dropdown = wait.until(
                ExpectedConditions.elementToBeClickable(By.cssSelector("a.nav-link.dropdown-toggle"))
        );
        dropdown.click();
        WebElement settingsLink = wait.until(
                ExpectedConditions.elementToBeClickable(By.linkText("Settings"))
        );
        settingsLink.click();

        WebDriverWait waitForImageField = new WebDriverWait(driver, Duration.ofSeconds(10));
        waitForImageField.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div/div/div/form/div[2]/input")));

        driver.findElement(By.id("lastName")).clear();
        driver.findElement(By.id("firstName")).clear();
        driver.findElement(By.id("firstName")).sendKeys("Harry");
        driver.findElement(By.id("lastName")).sendKeys("Parkes");
        driver.findElement(By.xpath("//input[@type='submit']")).click();

        waitForImageField.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div/div/div/form/div[2]/input")));
        WebElement firstName = driver.findElement(By.xpath("//*[@id=\"firstName\"]"));
        WebElement lastName = driver.findElement(By.xpath("//*[@id=\"lastName\"]"));
        String firstNameValue = firstName.getAttribute("value");
        assertThat(firstNameValue, equalTo("Harry"));
        String lastNameValue = lastName.getAttribute("value");
        assertThat(lastNameValue, equalTo("Parkes"));
        }

    }
