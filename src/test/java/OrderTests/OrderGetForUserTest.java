package OrderTests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.OrderModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.OrderSteps;
import steps.UserSteps;

import java.util.List;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.*;
import static testData.TestValues.*;

public class OrderGetForUserTest extends OrderBaseTest {

    @Before
    public void setUpOrder() {
        super.setUpBase();

        // Создаём 1 заказ, чтобы он появился в истории
        OrderModel order = new OrderModel(List.of(CRATER_BUN, IMMORTAL_MOLLUSK_MEAT));
        OrderSteps.createOrderWithAuth(order, accessToken)
                .then()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true));
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            UserSteps.deleteUser(accessToken)
                    .then()
                    .statusCode(HTTP_ACCEPTED);
        }
    }

    @Test
    @DisplayName("Получение заказов авторизованным пользователем")
    @Description("Ожидается код 200, success=true, и непустой список заказов.")
    public void testGetOrdersWithAuth() {
        OrderSteps.getUserOrdersWithAuth(accessToken)
                .then()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true))
                .body("orders", not(empty()))
                .body("total", greaterThan(0))
                .body("totalToday", greaterThanOrEqualTo(0));
    }

    @Test
    @DisplayName("Получение заказов неавторизованным пользователем")
    @Description("Попытка получить заказы без токена. Ожидается 401 и сообщение об ошибке.")
    public void testGetOrdersWithoutAuth() {
        OrderSteps.getUserOrdersWithAuth("")
                .then()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo(MESSAGE_UNAUTHORIZED));
    }
}