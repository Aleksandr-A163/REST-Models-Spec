package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import io.restassured.filter.log.LogDetail;
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
            .basePath("/login")
            .contentType(JSON)
            .log().all();

    public static ResponseSpecification responseWithStatusCode200 = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(STATUS)
            .log(LogDetail.ALL)
            .build();

        public static ResponseSpecification missingLoginResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(400)
            .log(STATUS)
            .log(LogDetail.ALL)
            .build();

        public static ResponseSpecification responseWithStatusCode404 = new ResponseSpecBuilder()
            .expectStatusCode(404)
            .log(LogDetail.ALL)
            .build();

        public static ResponseSpecification responseWithStatusCode204 = new ResponseSpecBuilder()
            .expectStatusCode(204)
            .log(LogDetail.ALL)
            .build();
}

