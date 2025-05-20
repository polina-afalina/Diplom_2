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

public class UserUpdateTest extends UserBaseTest {

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
    @DisplayName("Успешное обновление имени авторизованного")
    @Description("Успешное изменение поля name с токеном. Ожидается success=true и новое имя в ответе.")
    public void testUpdateUserNameWithAuth() {
        String newName = faker.name().firstName();
        UserModel update = new UserModel(null, null, newName);

        UserSteps.updateUser(update,  accessToken)
                .then()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true))
                .body("user.name", equalTo(newName));
    }

    @Test
    @DisplayName("Успешное обновление почты автовризованного пользователя")
    @Description("Успешное изменение поля email с токеном. Ожидается success=true и новый email в ответе.")
    public void testUpdateUserEmailWithAuth() {
        String newEmail = faker.internet().emailAddress();
        UserModel update = new UserModel(newEmail, null, null);

        UserSteps.updateUser(update, accessToken)
                .then()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true))
                .body("user.email", equalTo(newEmail.toLowerCase()));
    }

    @Test
    @DisplayName("Успешное обновление пароля авторизованного пользователя")
    @Description("Успешное изменение поля password с токеном. Ожидается success=true.")
    public void testUpdateUserPasswordWithAuth() {
        String newPassword = faker.internet().password(MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH);
        UserModel update = new UserModel(null, newPassword, null);

        UserSteps.updateUser(update,  accessToken)
                .then()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Ошибка при попытке изменить почту авторизованного пользователя на уже существующую")
    @Description("Попытка изменить поле email авторизованного пользователя на email другого пользователя. Ожидается 403 и сообщение об ошибке.")
    public void testUpdateEmailToExistingEmailWithAuth() {
        // Создаем второго пользователя, чтобы его email уже существовал
        UserModel secondUser = new UserModel(
                faker.internet().emailAddress(),
                faker.internet().password(MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH),
                faker.name().firstName()
        );

        // Регистрируем второго пользователя
        String secondAccessToken = UserSteps.createUser(secondUser)
                .then()
                .statusCode(HTTP_OK)
                .extract()
                .path("accessToken");

        // Попытка изменить email на уже существующий
        UserModel update = new UserModel(secondUser.getEmail(), null, null);

        UserSteps.updateUser(update, accessToken)
                .then()
                .statusCode(HTTP_FORBIDDEN)
                .body("message", equalTo(MESSAGE_EMAIL_EXISTS));

        // Чистим второго пользователя
        if (secondAccessToken != null) {
            UserSteps.deleteUser(secondAccessToken)
                    .then()
                    .statusCode(HTTP_ACCEPTED);
        }
    }

    @Test
    @DisplayName("Ошибка при обновлении имени неавторизованного пользователя")
    @Description("Попытка обновить поле name без токена. Ожидается 401 и сообщение об ошибке.")
    public void testUpdateNameWithoutAuth() {
        UserModel update = new UserModel(null, null, faker.name().firstName());

        UserSteps.updateUser(update, "")
                .then()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("message", equalTo(MESSAGE_UNAUTHORIZED));
    }

    @Test
    @DisplayName("Ошибка при обновлении почты неавторизованного пользователя")
    @Description("Попытка обновить поле email без токена. Ожидается 401 и сообщение об ошибке.")
    public void testUpdateEmailWithoutAuth() {
        UserModel update = new UserModel(faker.internet().emailAddress(), null, null);

        UserSteps.updateUser(update, "")
                .then()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("message", equalTo(MESSAGE_UNAUTHORIZED));
    }

    @Test
    @DisplayName("Ошибка при обновлении пароля неавторизованного пользователя")
    @Description("Попытка обновить поле password без токена. Ожидается 401 и сообщение об ошибке.")
    public void testUpdatePasswordWithoutAuth() {
        UserModel update = new UserModel(null, faker.internet().password(), null);

        UserSteps.updateUser(update, "")
                .then()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("message", equalTo(MESSAGE_UNAUTHORIZED));
    }

    @Test
    @DisplayName("Ошибка при попытке изменить почту неавторизованного пользователя на уже существующую")
    @Description("Попытка изменить поле email неавторизованного пользователя на email другого пользователя. Ожидается 401 и сообщение об ошибке.")
    public void testUpdateEmailToExistingEmailWithoutAuth() {
        // Создаем второго пользователя, чтобы его email уже существовал
        UserModel secondUser = new UserModel(
                faker.internet().emailAddress(),
                faker.internet().password(MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH),
                faker.name().firstName()
        );

        // Регистрируем второго пользователя
        String secondAccessToken = UserSteps.createUser(secondUser)
                .then()
                .statusCode(HTTP_OK)
                .extract()
                .path("accessToken");

        // Попытка изменить email на уже существующий
        UserModel update = new UserModel(secondUser.getEmail(), null, null);

        UserSteps.updateUser(update, "")
                .then()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("message", equalTo(MESSAGE_UNAUTHORIZED));

        // Чистим второго пользователя
        if (secondAccessToken != null) {
            UserSteps.deleteUser(secondAccessToken)
                    .then()
                    .statusCode(HTTP_ACCEPTED);
        }
    }
}