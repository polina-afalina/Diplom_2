package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.UserModel;

import static io.restassured.RestAssured.given;

public class UserSteps {
    public static final String USER_REGISTER_ENDPOINT = "/api/auth/register";
    public static final String USER_INFO_ENDPOINT = "/api/auth/user";
    public static final String USER_LOGIN_ENDPOINT = "/api/auth/login";

    @Step("Создание пользователя: {user}")
    public static Response createUser(UserModel user) {
        return given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(USER_REGISTER_ENDPOINT)
                .then()
                .extract()
                .response();
    }

    @Step("Логин пользователя: {user}")
    public static Response loginUser(UserModel user) {
        return given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(USER_LOGIN_ENDPOINT)
                .then()
                .extract()
                .response();
    }

    @Step("Удаление пользователя с токеном: {token}")
    public static Response deleteUser(String token) {
        return given()
                .header("Authorization", token)
                .when()
                .delete(USER_INFO_ENDPOINT)
                .then()
                .extract()
                .response();
    }

    @Step("Обновление данных пользователя с токеном: {token}")
    public static Response updateUser(UserModel user, String token) {
        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body(user)
                .when()
                .patch(USER_INFO_ENDPOINT)
                .then()
                .extract()
                .response();
    }
}