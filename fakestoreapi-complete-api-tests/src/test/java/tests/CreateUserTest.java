package tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class CreateUserTest {

    @DataProvider(name = "userData")
    public Object[][] userData() {
        return new Object[][]{
                {"johnd", "m38rmF$"},
                {"alice123", "passWord!9"}
        };
    }

    @Test(groups = "users", dataProvider = "userData")
    public void createUser(String username, String password) {
        String body = String.format("{\n" +
                "  \"email\": \"%s@example.com\",\n" +
                "  \"username\": \"%s\",\n" +
                "  \"password\": \"%s\",\n" +
                "  \"name\": {\n" +
                "    \"firstname\": \"John\",\n" +
                "    \"lastname\": \"Doe\"\n" +
                "  },\n" +
                "  \"address\": {\n" +
                "    \"city\": \"San Jose\",\n" +
                "    \"street\": \"Main\",\n" +
                "    \"number\": 100,\n" +
                "    \"zipcode\": \"95126\",\n" +
                "    \"geolocation\": {\n" +
                "      \"lat\": \"37.3382\",\n" +
                "      \"long\": \"-121.8863\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"phone\": \"1-570-236-7033\"\n" +
                "}", username, username, password);

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("https://fakestoreapi.com/users")
                .then()
                .log().body()
                .statusCode(200)
                .body("id", notNullValue()); // Validate user was created
    }
}

