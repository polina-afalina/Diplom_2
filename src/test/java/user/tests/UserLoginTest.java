package user.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.UserModel;
import org.junit.Before;
import org.junit.Test;
import steps.UserSteps;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.equalTo;
import static test.data.TestValues.*;

public class UserLoginTest extends UserBaseTest {

    @Before
    public void setUp() {
        testUser = new UserModel(
                faker.internet().emailAddress(),
                faker.internet().password(MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH),
                faker.name().firstName()
        );

        accessToken = UserSteps.createUser(testUser)
                .then()
                .statusCode(HTTP_OK)
                .extract()
                .path("accessToken");
    }

    @Test
    @DisplayName("Успешный логин пользователя с корректными данными")
    @Description("Успешный логин по корректной паре полей: email и password. Ожидается код 200 и success=true.")
    public void testLoginUserSuccess() {
        UserSteps.loginUser(testUser)
                .then()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Ошибка логина с неверной почтой")
    @Description("Попытка логина с неверным полем email (добавлен 0 в начало). Ожидается 401 и сообщение об ошибке.")
    public void testLoginUserWrongEmail() {
        UserModel wrongEmailUser = new UserModel("0" + testUser.getEmail(), testUser.getPassword(), null);

        UserSteps.loginUser(wrongEmailUser)
                .then()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("message", equalTo(MESSAGE_LOGIN_FAILED));
    }

    @Test
    @DisplayName("Ошибка логина с неверным паролем")
    @Description("Попытка логина с неверным полем password (добавлен 0 в начало). Ожидается 401 и сообщение об ошибке.")
    public void testLoginUserWrongPassword() {
        UserModel wrongPasswordUser = new UserModel(testUser.getEmail(), "0" + testUser.getPassword(), null);

        UserSteps.loginUser(wrongPasswordUser)
                .then()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("message", equalTo(MESSAGE_LOGIN_FAILED));
    }
}