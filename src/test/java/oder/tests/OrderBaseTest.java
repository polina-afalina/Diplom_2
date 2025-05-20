package oder.tests;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import model.UserModel;
import org.junit.Before;
import org.junit.BeforeClass;
import steps.UserSteps;

import static java.net.HttpURLConnection.HTTP_OK;
import static test.data.TestValues.BASE_URL;

public class OrderBaseTest {

    protected static Faker faker;
    protected UserModel testUser;
    protected String accessToken;

    @BeforeClass
    public static void setUpClass() {
        faker = new Faker();
        RestAssured.baseURI = BASE_URL;
    }

    @Before
    public void setUpBase() {
        testUser = new UserModel(
                faker.internet().emailAddress(),
                faker.internet().password(6, 12),
                faker.name().firstName()
        );

        accessToken = UserSteps.createUser(testUser)
                .then()
                .statusCode(HTTP_OK)
                .extract()
                .path("accessToken");
    }
}