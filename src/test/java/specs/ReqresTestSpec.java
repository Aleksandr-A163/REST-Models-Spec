package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;

public class ReqresTestSpec {

    public static RequestSpecification mainPageTestReq = with()
            .filter(withCustomTemplates())
            .basePath("")
            .log().headers()
            .log().body()
            .contentType(JSON);

    public static RequestSpecification loginTestReq = with()
            .filter(withCustomTemplates())
            .basePath("api/login")
            .log().headers()
            .log().body()
            .contentType(JSON);
    public static ResponseSpecification responseWithStatusCode200 = new ResponseSpecBuilder()

            .expectStatusCode(200)
            .log(STATUS)
            .log(BODY)
            .build();

        public static ResponseSpecification missingLoginResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(400)
            .log(STATUS)
            .log(BODY)
            .build();

        public static ResponseSpecification responseWithStatusCode404 = new ResponseSpecBuilder()
            .expectStatusCode(404)
            .log(BODY)
            .build();

        public static ResponseSpecification responseWithStatusCode204 = new ResponseSpecBuilder()
            .expectStatusCode(204)
            .log(BODY)
            .build();
}

