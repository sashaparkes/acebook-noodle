package com.makersacademy.acebook.model;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

    private User user = new User("username", "Sasha", "Parkes", "image/profpic");

    @Test
    public void userHasContent() {assertThat(user.getUsername(), containsString("username"));}

    @Test
    public void userIsEnabled() {assertEquals(true, user.isEnabled());}

    @Test
    public void userHasFirstName() {assertThat(user.getFirst_name(), containsString("Sasha"));}

    @Test
    public void userHasLastName() {assertThat(user.getLast_name(), containsString("Parkes"));}

    @Test
    public void userHasProfilePic() {assertThat(user.getProfile_pic(), containsString("image/profpic"));}
}
