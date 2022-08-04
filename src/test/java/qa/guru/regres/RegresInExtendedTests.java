package qa.guru.regres;

import models.lombok.BodyLombokModel;
import models.lombok.ResponseLombokModel;
import models.pojo.BodyPojoModel;
import models.pojo.ResponsePojoModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static helpers.CustomApiListener.withCustomTemplates;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.LoginSpecs.*;
import static specs.RegisterSpec.*;
import static specs.UsersSpec.*;

public class RegresInExtendedTests extends TestBase{

    @Test
    @DisplayName("Login user test")
    void loginTests() {
        String body = "{ \"email\": \"eve.holt@reqres.in\", " +
                "\"password\": \"cityslicka\" }";

        given()
                .filter(withCustomTemplates())
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(body)
                .when()
                .post("/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    @DisplayName("Login user test")
    void loginWithPojoModelTests() {

        BodyPojoModel bodyPojoModel = new BodyPojoModel();
        bodyPojoModel.setEmail("eve.holt@reqres.in");
        bodyPojoModel.setPassword("cityslicka");

        ResponsePojoModel response = given()
                .filter(withCustomTemplates())
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(bodyPojoModel) // loginBodyModel.toString()
                .when()
                .post("/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(ResponsePojoModel.class);

        assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
    }

    @Test
    @DisplayName("Login user test")
    void loginWithLombokModelTests() {

        BodyLombokModel bodyLombokModel = new BodyLombokModel();
        bodyLombokModel.setEmail("eve.holt@reqres.in");
        bodyLombokModel.setPassword("cityslicka");

        ResponseLombokModel response = given()
                .filter(withCustomTemplates())
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(bodyLombokModel)
                .when()
                .post("/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract()
                .as(ResponseLombokModel.class);

        assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
    }

    @Test
    @DisplayName("Login user test")
    void loginWithSpecsTests() {

        BodyLombokModel bodyLombokModel = new BodyLombokModel();
        bodyLombokModel.setEmail("eve.holt@reqres.in");
        bodyLombokModel.setPassword("cityslicka");

        ResponseLombokModel response = given()
                .spec(loginRequestSpec)
                .body(bodyLombokModel)
                .when()
                .post()
                .then()
                .spec(LoginResponseSpec)
                .extract()
                .as(ResponseLombokModel.class);

        assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
    }

    @Test
    @DisplayName("Login user test without password")
    void unsuccessfulLoginWithoutPasswordTests() {
        BodyLombokModel bodyLombokModel = new BodyLombokModel();
        bodyLombokModel.setEmail("eve.holt@reqres.in");

        ResponseLombokModel response = given()
                .spec(loginRequestSpec)
                .body(bodyLombokModel)
                .when()
                .post()
                .then()
                .spec(LoginResponseWithoutPasswordSpec)
                .extract()
                .as(ResponseLombokModel.class);

        assertThat(response.getError()).isEqualTo("Missing password");

    }

    @Test
    @DisplayName("Getting user list test")
    @Tag("regres_models")
    void getListUsersTests() {
         given()
                .spec(getUsersRequestSpec)
                .when()
                .get("?page=1")
                .then()
                .spec(getUsersResponseSpec)
                .body("data.id", hasItems(2))
                .body("data.email", hasItems("janet.weaver@reqres.in"));
    }

    @Test
    @DisplayName("Getting single user list test")
    @Tag("regres_models")
    void getSingleUserTests() {
        given()
                .spec(getUsersRequestSpec)
                .when()
                .get("/7")
                .then()
                .spec(getUsersResponseSpec)
                .body("data.id", is(7))
                .body("data.first_name", is("Michael"))
                .body("data.last_name", is("Lawson"));
    }

    @Test
    @DisplayName("Creation a new user test")
    @Tag("regres_models")
    void createUserTests() {

        BodyPojoModel bodyPojoModel = new BodyPojoModel();
        bodyPojoModel.setUserName("Guilo");
        bodyPojoModel.setUserJob("QA engineer");

        ResponsePojoModel response =  given()
                .spec(createUserRequestSpec)
                .body(bodyPojoModel)
                .when()
                .post()
                .then()
                .spec(createUserResponseSpec)
                .extract()
                .as(ResponsePojoModel.class);

        assertThat(response.getUserName()).isEqualTo("Guilo");
        assertThat(response.getUserJob()).isEqualTo("QA engineer");
    }

    @Test
    @DisplayName("Update of user data test")
    @Tag("regres_models")
    void updateUserTests() {

        BodyPojoModel bodyPojoModel = new BodyPojoModel();
        bodyPojoModel.setUserName("Guilo");
        bodyPojoModel.setUserJob("QA engineer has reached a new level");

        ResponsePojoModel response = given()
                .spec(updateUserRequestSpec)
                .body(bodyPojoModel)
                .when()
                .put("/153")
                .then()
                .spec(updateUserResponseSpec)
                .extract()
                .as(ResponsePojoModel.class);

        assertThat(response.getUserName()).isEqualTo("Guilo");
        assertThat(response.getUserJob()).isEqualTo("QA engineer has reached a new level");

    }

    @Test
    @DisplayName("Deletion user test")
    @Tag("regres_models")
    void deleteUserTests() {
        given()
                .spec(deleteUserRequestSpec)
                .log().uri()
                .when()
                .delete("/15300")
                .then()
                .spec(deleteUserResponseSpec);
    }

    @Test
    @DisplayName("Registration of a known user test")
    @Tag("regres_models")
    void registerKnownUserTests() {

        BodyLombokModel bodyLombokModel = new BodyLombokModel();
        bodyLombokModel.setEmail("tracey.ramos@reqres.in");
        bodyLombokModel.setPassword("regres123");

        given()
                .spec(registerUserRequestSpec)
                .body(bodyLombokModel)
                .when()
                .post()
                .then()
                .spec(registerKnownUserResponseSpec)
                .body("id", is(6))
                .body("token", is("QpwL5tke4Pnpja7X6"));
    }

    @Test
    @DisplayName("Registration of an unknown user test")
    @Tag("regres_models")
    void registerUnknownUserTests() {

        BodyLombokModel bodyLombokModel = new BodyLombokModel();
        bodyLombokModel.setEmail("testGulio@reqres.in");
        bodyLombokModel.setPassword("regres123");

        given()
                .spec(registerUserRequestSpec)
                .body(bodyLombokModel)
                .when()
                .post()
                .then()
                .spec(registerUnknownUserResponseSpec)
                .body("error", is("Note: Only defined users succeed registration"));
    }

    @Test
    @DisplayName("Getting a delayed user list test")
    @Tag("regres_models")
    void getDelayedListUsersTests() {
        given()
                .spec(getDelayedUserRequestSpec)
                .when()
                .get("?delay=3")
                .then()
                .spec(getDelayedUserResponseSpec)
                .body("total", is(12))
                .body("data.id", hasItems(1))
                .body("data.email", hasItems("george.bluth@reqres.in"));
    }

}
