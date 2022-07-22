package qa.guru.regres;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;

public class RegresInTests extends TestBase{

    @Test
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
    @Tag("regres")
    void getListUsersTests() {
        given()
                .log().uri()
                .when()
                .get("/users?page=1")
                .then()
                .log().status()
                .log().body()
                .statusCode(200);
    }

    @Test
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
                .post("https://reqres.in/api/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("id", is(6))
                .body("token", is("QpwL5tke4Pnpja7X6"));
    }

    @Test
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
                .post("https://reqres.in/api/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Note: Only defined users succeed registration"));
    }

    @Test
    @Tag("regres")
    void getDelayedListUsersTests() {
        given()
                .log().uri()
                .when()
                .get("https://reqres.in/api/users?delay=3")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("total", is(12))
                .body("data.id", hasItems(1))
                .body("data.email", hasItems("george.bluth@reqres.in"));
    }
}
