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

public class UserCreateTest extends UserBaseTest {

    @Before
    public void setUpTest() {
        testUser = new UserModel(
                faker.internet().emailAddress(),
                faker.internet().password(MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH),
                faker.name().firstName()
        );
    }

    @Test
    @DisplayName("Успешное создание пользователя с валидными данными")
    @Description("Успешное создание пользователя с корректными email, паролем и именем. Ожидается код 200 и success=true.")
    public void testCreateUserSuccess() {
        accessToken = UserSteps.createUser(testUser)
                .then()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true))
                .extract()
                .path("accessToken");
    }

    @Test
    @DisplayName("Ошибка при создании пользователя с уже существующей почтой")
    @Description("Попытка создать пользователя с email, который уже зарегистрирован. Ожидается код 403 и сообщение об ошибке.")
    public void testCreateUserDuplicateEmail() {
        // Первый запрос — регистрация
        accessToken = UserSteps.createUser(testUser)
                .then()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true))
                .extract()
                .path("accessToken");

        // Второй запрос — ошибка
        UserSteps.createUser(testUser)
                .then()
                .statusCode(HTTP_FORBIDDEN)
                .body("message", equalTo(MESSAGE_USER_EXISTS));
    }

    @Test
    @DisplayName("Ошибка при создании пользователя без email")
    @Description("Попытка создать пользователя без обязательного поля email. Ожидается код 403 и сообщение об ошибке.")
    public void testCreateUserMissingEmail() {
        UserModel user = new UserModel(null, testUser.getPassword(), testUser.getName());

        UserSteps.createUser(user)
                .then()
                .statusCode(HTTP_FORBIDDEN)
                .body("message", equalTo(MESSAGE_REQUIRED_FIELDS));
    }

    @Test
    @DisplayName("Ошибка при создании пользователя без пароля")
    @Description("Попытка создать пользователя без обязательного поля password. Ожидается код 403 и сообщение об ошибке.")
    public void testCreateUserMissingPassword() {
        UserModel user = new UserModel(testUser.getEmail(), null, testUser.getName());

        UserSteps.createUser(user)
                .then()
                .statusCode(HTTP_FORBIDDEN)
                .body("message", equalTo(MESSAGE_REQUIRED_FIELDS));
    }

    @Test
    @DisplayName("Ошибка при создании пользователя без имени")
    @Description("Попытка создать пользователя без обязательного поля name. Ожидается код 403 и сообщение об ошибке.")
    public void testCreateUserMissingName() {
        UserModel user = new UserModel(testUser.getEmail(), testUser.getPassword(), null);

        UserSteps.createUser(user)
                .then()
                .statusCode(HTTP_FORBIDDEN)
                .body("message", equalTo(MESSAGE_REQUIRED_FIELDS));
    }
}