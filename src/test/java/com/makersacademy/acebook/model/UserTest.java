package com.makersacademy.acebook.model;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

    private User user = new User("username", "Sasha", "Parkes", "static/images/profile/default.jpeg");

    @Test
    public void userHasContent() {assertThat(user.getUsername(), containsString("username"));}

    @Test
    public void userIsEnabled() {assertEquals(true, user.isEnabled());}

    @Test
    public void userHasFirstName() {assertThat(user.getFirstName(), containsString("Sasha"));}

    @Test
    public void userHasLastName() {assertThat(user.getLastName(), containsString("Parkes"));}

    @Test
    public void userHasProfilePic() {assertThat(user.getProfilePic(), containsString("images/profile/default.jpeg"));}
}
