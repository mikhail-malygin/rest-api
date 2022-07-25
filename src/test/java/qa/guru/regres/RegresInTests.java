package qa.guru.regres;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;

public class RegresInTests extends TestBase{

    @Test
    @DisplayName("Login user test")
    void loginTests() {
        String body = "{ \"email\": \"eve.holt@reqres.in\", " +
                "\"password\": \"cityslicka\" }"; // todo bad practice

        given()
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
    @DisplayName("Login user test without password")
    void unsuccessfulLoginWithoutPasswordTests() {
        String body = "{ \"email\": \"eve.holt@reqres.in\"}"; // todo bad practice

        given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(body)
                .when()
                .post("/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    @DisplayName("Getting user list test")
    @Tag("regres")
    void getListUsersTests() {
        given()
                .log().uri()
                .when()
                .get("/users?page=1")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.id", hasItems(2))
                .body("data.email", hasItems("janet.weaver@reqres.in"));
    }

    @Test
    @DisplayName("Getting single user list test")
    @Tag("regres")
    void getSingleUserTests() {
        given()
                .log().uri()
                .when()
                .get("/users/7")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.id", is(7))
                .body("data.first_name", is("Michael"))
                .body("data.last_name", is("Lawson"));
    }

    @Test
    @DisplayName("Creation a new user test")
    @Tag("regres")
    void createUserTests() {
        String body = "{ \"name\": \"Guilo\", " +
                      "\"job\": \"QA engineer\" }";

        given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(body)
                .when()
                .post("/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("Guilo"))
                .body("job", is("QA engineer"));
    }

    @Test
    @DisplayName("Update of user data test")
    @Tag("regres")
    void updateUserTests() {
        String body = "{ \"name\": \"Guilo\", " +
                      "\"job\": \"QA engineer has reached a new level\" }";

        given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(body)
                .when()
                .put("/users/153")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("name", is("Guilo"))
                .body("job", is("QA engineer has reached a new level"));
    }

    @Test
    @DisplayName("Deletion user test")
    @Tag("regres")
    void deleteUserTests() {
        given()
                .log().uri()
                .when()
                .delete("/users/15300")
                .then()
                .log().status()
                .log().body()
                .statusCode(204);
    }

    @Test
    @DisplayName("Registration of a known user test")
    @Tag("regres")
    void registerKnownUserTests() {
        String body = "{ \"email\": \"tracey.ramos@reqres.in\", " +
                "\"password\": \"regres123\" }";

        given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(body)
                .when()
                .post("register")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("id", is(6))
                .body("token", is("QpwL5tke4Pnpja7X6"));
    }

    @Test
    @DisplayName("Registration of an unknown user test")
    @Tag("regres")
    void registerUnknownUserTests() {
        String body = "{ \"email\": \"testGulio@reqres.in\", " +
                      "\"password\": \"regres123\" }";

        given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(body)
                .when()
                .post("register")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Note: Only defined users succeed registration"));
    }

    @Test
    @DisplayName("Getting a delayed user list test")
    @Tag("regres")
    void getDelayedListUsersTests() {
        given()
                .log().uri()
                .when()
                .get("users?delay=3")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("total", is(12))
                .body("data.id", hasItems(1))
                .body("data.email", hasItems("george.bluth@reqres.in"));
    }
}
