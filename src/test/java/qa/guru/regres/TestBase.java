package qa.guru.regres;


import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.baseURI;

public class TestBase {

    @BeforeAll
    static void before() {
        baseURI = "https://reqres.in/api/";
    }
}
