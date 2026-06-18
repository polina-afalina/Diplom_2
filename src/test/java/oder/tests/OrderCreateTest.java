package oder.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.OrderModel;
import org.junit.Test;
import steps.OrderSteps;

import java.util.List;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.*;
import static test.data.TestValues.*;

public class OrderCreateTest extends OrderBaseTest {

    @Test
    @DisplayName("Создание заказа с авторизацией и валидными ингредиентами")
    @Description("Создание заказа с токеном и правильными ингредиентами. Ожидается success=true и номер заказа.")
    public void testCreateOrderWithAuth() {
        OrderModel order = new OrderModel(List.of(CRATER_BUN, IMMORTAL_MOLLUSK_MEAT));

        OrderSteps.createOrderWithAuth(order, accessToken)
                .then()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true))
                .body("order.number", notNullValue());
    }

    @Test
    @DisplayName("Создание заказа без авторизации и с валидными ингредиентами")
    @Description("Создание заказа без токена. Ожидается success=true и номер заказа.")
    public void testCreateOrderWithoutAuth() {
        OrderModel order = new OrderModel(List.of(CRATER_BUN, IMMORTAL_MOLLUSK_MEAT));

        OrderSteps.createOrderWithAuth(order,"")
                .then()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true))
                .body("order.number", notNullValue());
    }

    @Test
    @DisplayName("Создание заказа с пустым списком ингредиентов")
    @Description("Создание заказа без ингредиентов. Ожидается ошибка 400 и сообщение.")
    public void testCreateOrderNoIngredients() {
        OrderModel order = new OrderModel(List.of());

        OrderSteps.createOrderWithAuth(order, accessToken)
                .then()
                .statusCode(HTTP_BAD_REQUEST)
                .body("success", equalTo(false))
                .body("message", equalTo(MESSAGE_INGREDIENT_IDS));
    }

    @Test
    @DisplayName("Создание заказа с невалидными id ингредиентов")
    @Description("Создание заказа с несуществующими id. Ожидается 500 Internal Server Error.")
    public void testCreateOrderWithInvalidIngredientIds() {
        OrderModel order = new OrderModel(List.of(CRATER_BUN + "1"));

        OrderSteps.createOrderWithAuth(order, accessToken)
                .then()
                .statusCode(HTTP_INTERNAL_ERROR);
    }

    @Test
    @DisplayName("Создание заказа без авторизации и без ингредиентов")
    @Description("Ожидается ошибка 400 без токена и ингредиентов.")
    public void testCreateOrderNoAuthNoIngredients() {
        OrderModel order = new OrderModel(List.of());

        OrderSteps.createOrderWithAuth(order,"")
                .then()
                .statusCode(HTTP_BAD_REQUEST)
                .body("success", equalTo(false))
                .body("message", equalTo(MESSAGE_INGREDIENT_IDS));
    }
}