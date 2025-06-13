package com.makersacademy.acebook.feature;

import com.github.javafaker.Faker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PostFeatureTest {

    WebDriver driver;
    WebDriverWait wait;
    Faker faker;

    @Before
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        faker = new Faker();
    }

    @After
    public void tearDown() {
        driver.quit();
    }
    //-- login info copied from jordan's test -- required to sign up a new user in order to test
    private void login() {
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

        // need to wait for homepage to fully load
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1[contains(text(),'Posts')]")));
    }

    @Test
    public void testCreatePostCommentAndLike() {
        login();

        // test post and test comment content
        String postContent = "TEST_POST_CONTENT";
        String commentContent = "TEST_COMMENT_CONTENT";

        // -- when a user types a post, it should be appear correctly
        WebElement postTextArea = wait.until(ExpectedConditions.elementToBeClickable(By.name("content")));
        postTextArea.sendKeys(postContent);
        driver.findElement(By.xpath("//input[@value='Post Status']")).click();
        System.out.println("Post created successfully");
        // add a wait to make sure post appears
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(), '" + postContent + "')]")));
        System.out.println("Post appears successfully");
        // locate post container in html to make sure post content is there.
        WebElement postContainer = driver.findElement(By.xpath("//*[contains(text(), '" + postContent + "')]/ancestor::div[contains(@class, 'post')]"));
        System.out.println("Post container found successfully");


        // -- TEST when a user clicks on 'view post' it should take the user to the individual postId
        WebElement viewPostLink = postContainer.findElement(By.xpath(".//a[contains(@href, '/posts/')]"));
        viewPostLink.click();
        System.out.println("Post navigated successfully");

        // add wait for individual post page to fully load
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("form[action*='/posts/comments']")));
        System.out.println("Post navigated successfully");


        // -- TEST when a user leaves a comment, that comment should appear under the post
        WebElement commentBox = wait.until(ExpectedConditions.elementToBeClickable(By.name("content")));
        commentBox.sendKeys(commentContent);
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("form[action*='/posts/comments'] button[type='submit']")
        ));
        submitButton.click();
        System.out.println("comment submitted successfully");
        // add a wait and confirm comment appears
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(), '" + commentContent + "')]")));
        assertTrue(driver.getPageSource().contains(commentContent));
        System.out.println("comment appears successfully");

        // -- TEST when a user likes a post/comment, these should appear successfully
        WebElement likePostButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//form[contains(@action,'like') and not(contains(@action,'comments'))]//button")
        ));
        likePostButton.click();
        System.out.println("Post/comment liked successfully");


        // all elements of test completed successfully
        System.out.println("Post, comment, and like test successfully completed WOOHOO FINALLY BRUH.");
    }
}
