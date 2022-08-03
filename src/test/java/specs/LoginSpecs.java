package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomApiListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.notNullValue;

public class LoginSpecs {
    public static RequestSpecification loginRequestSpec = with()
            .basePath("/login")
            .filter(withCustomTemplates())
            .log().uri()
            .log().body()
            .contentType(JSON)
            .contentType(ContentType.JSON);

    public static ResponseSpecification LoginResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(LogDetail.STATUS)
            .log(LogDetail.BODY)
            .expectBody("token", notNullValue())
            .build();
}
