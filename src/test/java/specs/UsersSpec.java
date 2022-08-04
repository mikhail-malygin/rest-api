package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomApiListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.http.ContentType.JSON;

public class UsersSpec {

    public static RequestSpecification getUsersRequestSpec = with()
            .basePath("/users")
            .filter(withCustomTemplates())
            .log().uri()
            .contentType(JSON);

    public static ResponseSpecification getUsersResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(LogDetail.STATUS)
            .log(LogDetail.BODY)
            .build();

    public static RequestSpecification createUserRequestSpec = with()
            .basePath("/users")
            .filter(withCustomTemplates())
            .log().uri()
            .log().body()
            .contentType(JSON);

    public static ResponseSpecification createUserResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(201)
            .log(LogDetail.STATUS)
            .log(LogDetail.BODY)
            .build();

    public static RequestSpecification updateUserRequestSpec = with()
            .basePath("/users")
            .filter(withCustomTemplates())
            .log().uri()
            .log().body()
            .contentType(JSON);

    public static ResponseSpecification updateUserResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(LogDetail.STATUS)
            .log(LogDetail.BODY)
            .build();

    public static RequestSpecification deleteUserRequestSpec = with()
            .basePath("/users")
            .filter(withCustomTemplates())
            .log().uri()
            .contentType(JSON);

    public static ResponseSpecification deleteUserResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(204)
            .log(LogDetail.STATUS)
            .log(LogDetail.BODY)
            .build();

    public static RequestSpecification getDelayedUserRequestSpec = with()
            .basePath("/users")
            .filter(withCustomTemplates())
            .log().uri()
            .contentType(JSON);

    public static ResponseSpecification getDelayedUserResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(LogDetail.STATUS)
            .log(LogDetail.BODY)
            .build();
}
