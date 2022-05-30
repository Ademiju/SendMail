package com.sendMail.sendMail.services;

import com.sendMail.sendMail.datas.models.User;
import com.sendMail.sendMail.datas.repositories.UserRepository;
import com.sendMail.sendMail.dtos.requests.user.*;
import com.sendMail.sendMail.dtos.responses.user.UserResponse;
import com.sendMail.sendMail.exceptions.IncorrectLoginDetailsException;
import com.sendMail.sendMail.exceptions.SendEmailException;
import com.sendMail.sendMail.exceptions.UnMatchingDetailsException;
import com.sendMail.sendMail.exceptions.UserNotFoundException;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
@SpringBootTest
@ImportAutoConfiguration(exclude = EmbeddedMongoAutoConfiguration.class)

class UserServiceImplTest {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    UserRequest createUserAccount;
    UserRequest createUserAccount2;
    LoginRequest loginRequest;
    LoginRequest loginRequest2;

    @BeforeEach
    void setUp() {
    createUserAccount = UserRequest.builder().firstName("").lastName("").emailAddress("Ademiju@sendmail.com").password("Ademiju1#").build();
    loginRequest = LoginRequest.builder().emailAddress("Ademiju@sendmail.com").password("Ademiju1#").build();
    createUserAccount2 = UserRequest.builder().firstName("Increase").lastName("").emailAddress("Increase@sendmail.com").password("IncreaseLois").build();
    loginRequest2 = LoginRequest.builder().emailAddress("Increase@sendmail.com").password("IncreaseLois").build();

    }

    @Test
    void userCanCreateAccountTest(){
        UserResponse userResponse = userService.register(createUserAccount);
        assertThat(userResponse.getEmailAddress(),is(createUserAccount.getEmailAddress()));
        assertThat(userResponse.getMessage(),is("Account Successfully Created"));

    }
    @Test
    void userCanLoginTest(){
        userService.register(createUserAccount);
        String message = userService.login(loginRequest);
        assertThat(message,is("Login Successful"));
    }

    @Test
    public void createNewUser_WithExistingEmail_ThrowsExceptionTest() {
        userService.register(createUserAccount);
        assertThatThrownBy(() -> userService.register(createUserAccount)).isInstanceOf(SendEmailException.class).hasMessage("Email Already Exist");

    }
    @Test
    public void userLoginWithIncorrectUsernameOrPasswordThrowsExceptionTest(){
        userService.register(createUserAccount);
        userService.register(createUserAccount2);
        String message = userService.login(loginRequest);
        LoginRequest loginRequest = LoginRequest.builder().emailAddress("Increase@sendmail.com").password("increaseLois").build();
        assertThat(message,is("Login Successful"));
        assertThatThrownBy(()->userService.login(loginRequest)).isInstanceOf(IncorrectLoginDetailsException.class).hasMessage("Incorrect Login details");
    }
    @Test
    public void userCanUpdate_AccountDetailsTest(){
        userService.register(createUserAccount);
        userService.login(loginRequest);
        UpdateDetailsRequest accountUpdateRequest = UpdateDetailsRequest.builder().emailAddress("Ademijuwonlo@sendmail.com").firstName("Ademijuwonlo").lastName("").build();
        UserResponse updateResponse = userService.updateUserDetails(accountUpdateRequest, loginRequest.getEmailAddress());

        assertThat(updateResponse.getEmailAddress(), is(accountUpdateRequest.getEmailAddress()));
        assertThat(updateResponse.getFirstName(), is(accountUpdateRequest.getFirstName()));

    }
    @Test
    public void userCanLoginWithUpdatedDetailsTest(){
        userService.register(createUserAccount);
        userService.login(loginRequest);
        UpdateDetailsRequest accountUpdateRequest = UpdateDetailsRequest.builder().emailAddress("Ademijuwonlo@sendmail.com").firstName("Ademijuwonlo").lastName("").build();
        UserResponse updateResponse = userService.updateUserDetails(accountUpdateRequest, loginRequest.getEmailAddress());

        assertThat(updateResponse.getEmailAddress(), is(accountUpdateRequest.getEmailAddress()));
        assertThat(updateResponse.getFirstName(), is(accountUpdateRequest.getFirstName()));
        LoginRequest loginWithUpdatedUserDetails = LoginRequest.builder().emailAddress("Ademijuwonlo@sendmail.com").password("Ademiju1#").build();
        String message = userService.login(loginWithUpdatedUserDetails);
        assertThat(message, is("Login Successful"));
    }

    @Test
    public void updateEmailAddressToAlreadyExistingEmailAddress_ThrowsExceptionTest(){
        userService.register(createUserAccount);
        userService.register(createUserAccount2);
        userService.login(loginRequest);

        UpdateDetailsRequest accountUpdateRequest = UpdateDetailsRequest.builder().emailAddress("Increase@sendmail.com").firstName("Ademijuwonlo").lastName("").build();
        assertThatThrownBy(()-> userService.updateUserDetails(accountUpdateRequest,loginRequest.getEmailAddress())).isInstanceOf(SendEmailException.class).hasMessage("Email Already Exist");

    }
    @Test
    public void passwordCanBeUpdatedTest(){
        userService.register(createUserAccount);
        userService.login(loginRequest);

        UpdatePasswordRequest updatePasswordRequest = UpdatePasswordRequest.builder().oldPassword("Ademiju1#").newPassword("Ademijuwonlo#").confirmNewPassword("Ademijuwonlo#").build();
        String message = userService.updatePassword(updatePasswordRequest,loginRequest.getEmailAddress());
        assertThat(message, is("Password Successfully Updated"));

    }
    @Test
    public void passwordCanBeUpdatedOnlyWhenUserIsLoggedIn(){
        userService.register(createUserAccount);
        UpdatePasswordRequest updatePasswordRequest = UpdatePasswordRequest.builder().oldPassword("Ademiju1#").newPassword("Ademijuwonlo#").confirmNewPassword("Ademijuwonlo#").build();
        assertThatThrownBy(()-> userService.updatePassword(updatePasswordRequest,loginRequest.getEmailAddress())).isInstanceOf(SendEmailException.class).hasMessage("You must be logged in");
    }

    @Test
    public void updatePasswordWithIncorrectOldPasswordThrowExceptionTest(){
        userService.register(createUserAccount);
        UpdatePasswordRequest updatePasswordRequest = UpdatePasswordRequest.builder().oldPassword("Ademiju1").newPassword("Ademijuwonlo#").confirmNewPassword("Ademijuwonlo#").build();
        userService.login(loginRequest);
        assertThatThrownBy(()->userService.updatePassword(updatePasswordRequest,loginRequest.getEmailAddress())).isInstanceOf(UnMatchingDetailsException.class).hasMessage("Incorrect password");

    }
    @Test
    public void updatePasswordWithUnMatchingNewPasswordThrowExceptionTest() {
        userService.register(createUserAccount);
        UpdatePasswordRequest updatePasswordRequest = UpdatePasswordRequest.builder().oldPassword("Ademiju1#").newPassword("Ademijuwonlo").confirmNewPassword("Ademijuwonlo#").build();
        userService.login(loginRequest);

        assertThatThrownBy(() -> userService.updatePassword(updatePasswordRequest, loginRequest.getEmailAddress())).isInstanceOf(UnMatchingDetailsException.class).hasMessage("New Password Does Not Match");
    }

    @Test
    public void findUserByEmailTest(){
        userService.register(createUserAccount);
        UserResponse userResponse = userService.register(createUserAccount2);
        User userFromRepository = userService.findByEmailAddress("Increase@sendmail.com");
        assertThat(userFromRepository.getEmailAddress(), is(userResponse.getEmailAddress()));
    }

    @Test
    public void searchUserWithNonExistingEmailThrowsExceptionTest(){
        userService.register(createUserAccount);
        assertThatThrownBy(()->userService.findByEmailAddress("Ademi@sendmail.com")).isInstanceOf(UserNotFoundException.class).hasMessage("User does not exist");

    }
    @Test
    public void userCanBeDeletedByEmailTest(){
        userService.register(createUserAccount);
        userService.login(loginRequest);
        DeleteRequest deleteRequest = DeleteRequest.builder().emailAddress("Ademiju@sendmail.com").password("Ademiju1#").confirmPassword("Ademiju1#").build();
        String message = userService.deleteAccount(deleteRequest);
        assertThat(message, is( "User Account successfully Deleted"));
        assertThatThrownBy(()->userService.findByEmailAddress("Ademiju@sendmail.com")).isInstanceOf(UserNotFoundException.class).hasMessage("User does not exist");

    }
    @Test
    public void userCannotBeDeletedByEmailWhenNotLoggedInTest(){
        userService.register(createUserAccount);
        DeleteRequest deleteRequest = DeleteRequest.builder().emailAddress("Ademiju@sendmail.com").password("Ademiju1#").confirmPassword("Ademiju1#").build();
        String message = userService.deleteAccount(deleteRequest);
        assertThat(message, is( "You must be logged in"));
        User userFromRepository = userService.findByEmailAddress("Ademiju@sendmail.com");
        assertThat(userFromRepository.getEmailAddress(), is(createUserAccount.getEmailAddress()));

    }


    @Test
    public void deleteUserWithWrongPasswordDetailsThrowExceptionTest() {
        userService.register(createUserAccount);
        userService.login(loginRequest);
        DeleteRequest deleteRequest = DeleteRequest.builder().emailAddress("Ademiju@sendmail.com").password("Ademiju1").confirmPassword("Ademiju1#").build();
        assertThatThrownBy(() -> userService.deleteAccount(deleteRequest)).isInstanceOf(UnMatchingDetailsException.class).hasMessage("Password Mismatch!!! Failed To Delete");

    }
    @Test
    public void userCanLogoutTest(){
        userService.register(createUserAccount);
        userService.login(loginRequest);
        String message = userService.logout(loginRequest.getEmailAddress());
        assertThat(message,is("Logout Successful"));
    }
        @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }
}