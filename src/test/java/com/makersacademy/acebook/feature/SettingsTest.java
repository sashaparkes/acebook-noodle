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
        WebElement settingsLink = wait.until(
                ExpectedConditions.elementToBeClickable(By.linkText("| Settings |"))
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

        driver.findElement(By.linkText("| Settings |")).click();

        WebDriverWait waitForImageField = new WebDriverWait(driver, Duration.ofSeconds(10));
        waitForImageField.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//img[@alt='User Profile Image']")));

        Path imagePath = Paths.get("src/test/resources/Test_Profile.png");
        WebElement fileInput = driver.findElement(By.name("file"));
        fileInput.sendKeys(imagePath.toAbsolutePath().toString());

        driver.findElement(By.id("submit")).click();

        WebDriverWait waitForImage = new WebDriverWait(driver, Duration.ofSeconds(10));
        waitForImage.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//img[@alt='User Profile Image']")));

        WebElement fileName = driver.findElement(By.xpath("//img[@alt='User Profile Image']"));
        String src = fileName.getAttribute("src");

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

        driver.findElement(By.linkText("| Settings |")).click();

        WebDriverWait waitForImageField = new WebDriverWait(driver, Duration.ofSeconds(10));
        waitForImageField.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//img[@alt='User Profile Image']")));

        driver.findElement(By.id("last_name")).clear();
        driver.findElement(By.id("first_name")).clear();
        driver.findElement(By.id("first_name")).sendKeys("Harry");
        driver.findElement(By.id("last_name")).sendKeys("Parkes");
        driver.findElement(By.xpath("//input[@type='submit']")).click();

        waitForImageField.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//img[@alt='User Profile Image']")));
        List<WebElement> headers = driver.findElements(By.tagName("h2"));
        assertEquals(headers.get(0).getText(), "First name: Harry");
        assertEquals(headers.get(1).getText(), "Surname: Parkes");

        }

    }
