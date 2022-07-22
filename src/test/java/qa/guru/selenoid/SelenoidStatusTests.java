package qa.guru.selenoid;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SelenoidStatusTests {

    @Test
    void checkTotalTests() {
        get("https://selenoid.autotests.cloud/status")
                .then()
                .statusCode(200)
                .body("total", is(20));
    }

    @Test
    void checkTotalWithGivenTests() {
        given()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .statusCode(200)
                .body("total", is(20));
    }

    @Test
    void checkTotalWithLogsTests() {
        given()
                .log().all()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().all()
                .statusCode(200)
                .body("total", is(20));
    }

    @Test
    void checkChromeVersionTests() {
        given()
                .log().all()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().all()
                .statusCode(200)
                .body("browsers.chrome", hasKey("100.0"));
    }

    @Test
    void checkTotalWithSomeLogsTests() {
        given()
                .log().uri()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("total", is(20));
    }

    @Test
    void checkResponseBadPracticeTests() {
        String expectedResponseString = "{\"total\":20,\"used\":0,\"queued\":0,\"pending\":0,\"browsers\":" +
                "{\"android\":{\"8.1\":{}},\"chrome\":{\"100.0\":{},\"99.0\":{}}," +
                "\"chrome-mobile\":{\"86.0\":{}},\"firefox\":{\"97.0\":{},\"98.0\":{}}," +
                "\"opera\":{\"84.0\":{},\"85.0\":{}}}}\n";

        Response actualResponse = given()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .extract().response();

        System.out.println(actualResponse);

        String actualResponseString = actualResponse.asString();
        System.out.println(actualResponseString);

        assertEquals(expectedResponseString, actualResponseString);
    }

    @Test
    void checkTotalGoodPracticeTests() {
        Integer expectedTotal = 20;
        Integer actualTotal = given()
                .log().uri()
                .log().body()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract()
                .path("total");

        assertEquals(expectedTotal, actualTotal);
    }

    @Test
    void check401WdHubStatusTests() {
        given()
                .log().uri()
                .log().body()
                .when()
                .get("https://selenoid.autotests.cloud/wd/hub/status")
                .then()
                .log().status()
                .log().body()
                .statusCode(401);
    }

    @Test
    void checkWdHubStatusWithAuthInUrlTests() {
        given()
                .log().uri()
                .when()
                .get("https://user1:1234@selenoid.autotests.cloud/wd/hub/status")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("value.ready", is(true));
    }

    @Test
    void checkWdHubStatusTests() {
        given()
                .auth().basic("user1", "1234")
                .log().all()
                .when()
                .get("https://selenoid.autotests.cloud/wd/hub/status")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("value.ready", is(true));
    }
}
