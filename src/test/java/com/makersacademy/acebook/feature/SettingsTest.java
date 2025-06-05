package com.makersacademy.acebook.feature;

import com.github.javafaker.Faker;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.UserRepository;
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
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class SettingsTest {

    @Autowired
    UserRepository userRepository;

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
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("http://localhost:8080/");
        driver.findElement(By.linkText("Login / Sign Up")).click();
        driver.findElement(By.name("email")).sendKeys(
                "winona.anderson@email.com");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement passwordField = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("1-password"))
        );
        passwordField.sendKeys("Password123");
        driver.findElement(By.className("auth0-label-submit")).click();
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

        driver.navigate().to("http://localhost:8080/settings");

        Path imagePath = Paths.get("src/test/resources/Test_Profile.png");
        WebElement fileInput = driver.findElement(By.name("file"));
        fileInput.sendKeys(imagePath.toAbsolutePath().toString());

        driver.findElement(By.id("submit")).click();

        WebDriverWait waitForImage = new WebDriverWait(driver, Duration.ofSeconds(10));

        User userByEmail = userRepository.findUserByUsername(email).get();
        String userId = String.valueOf(userByEmail.getId());

        waitForImage.until(driver -> {
            WebElement img = driver.findElement(By.xpath("//img[@alt='User Profile Image']"));
            String src = img.getAttribute("src");
            return src != null && src.contains(userId);
        });

        WebElement updatedImage = driver.findElement(By.xpath("//img[@alt='User Profile Image']"));
        String updatedSrc = updatedImage.getAttribute("src");

        Assertions.assertTrue(updatedSrc.contains(userId));
//
//        WebDriverWait waitForImage = new WebDriverWait(driver, Duration.ofSeconds(10));
//        waitForImage.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//img[@alt='User Profile Image']")));
//
//        WebElement fileName = driver.findElement(By.xpath("//img[@alt='User Profile Image']"));
//        String src = fileName.getAttribute("src");
//
//        User userByEmail = userRepository.findUserByUsername(email).get();
//        String userId = String.valueOf(userByEmail.getId());
//
//        Assertions.assertTrue(src.contains(userId));
    }

}
