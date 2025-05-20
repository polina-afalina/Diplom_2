package user.tests;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import model.UserModel;
import org.junit.After;
import org.junit.BeforeClass;
import test.data.TestValues;
import steps.UserSteps;

import static java.net.HttpURLConnection.HTTP_ACCEPTED;

public class UserBaseTest {

    protected static Faker faker;
    protected UserModel testUser;
    protected String accessToken;

    @BeforeClass
    public static void globalSetUp() {
        faker = new Faker();
        RestAssured.baseURI = TestValues.BASE_URL;
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            UserSteps.deleteUser(accessToken)
                    .then()
                    .statusCode(HTTP_ACCEPTED);
        }
    }
}