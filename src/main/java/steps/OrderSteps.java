package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.OrderModel;

import static io.restassured.RestAssured.given;

public class OrderSteps {
    public static final String ORDER_ENDPOINT = "/api/orders";

    @Step("Создание заказа с авторизацией: {order}")
    public static Response createOrderWithAuth(OrderModel order, String token) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body(order)
                .when()
                .post(ORDER_ENDPOINT)
                .then()
                .log().all()
                .extract().response();
    }

    @Step("Получение заказов пользователя с авторизацией: {token}")
    public static Response getUserOrdersWithAuth(String token) {
        return given()
                .log().all()
                .header("Authorization", token)
                .when()
                .get(ORDER_ENDPOINT)
                .then()
                .log().all()
                .extract()
                .response();
    }
}