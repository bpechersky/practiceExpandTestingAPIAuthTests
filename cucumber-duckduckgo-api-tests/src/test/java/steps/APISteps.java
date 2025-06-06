package steps;

import io.cucumber.java.en.*;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.assertEquals;

public class APISteps {

    Response response;

    @Given("the Reqres API is available")
    public void the_reqres_api_is_available() {
        baseURI = "https://reqres.in";
    }

    @When("I request the user list with API key")
    public void i_request_the_user_list_with_api_key() {
        response = given()
            .header("x-api-key", "reqres-free-v1")
            .when().get("/api/users?page=2");
    }

    @Then("the response code should be {int}")
    public void the_response_code_should_be(Integer code) {
        assertEquals((int) code, response.getStatusCode());
    }
}
