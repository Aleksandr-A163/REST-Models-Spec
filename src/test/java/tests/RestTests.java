package tests;

import models.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static io.qameta.allure.Allure.step;
import static helpers.CustomAllureListener.withCustomTemplates;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static io.restassured.http.ContentType.JSON;


import static specs.LoginTestSpec.loginTestReq;
import static specs.LoginTestSpec.loginTestRes;


@Tag("apiTests")
public class RestTests extends TestBase {

    @DisplayName("Успешная авторизация пользователя")
    @Test
    void successfulLoginTest() {
        final LoginModel login = new LoginModel();
        login.setEmail("eve.holt@reqres.in");
        login.setPassword("pistol");

        LoginModel login1 = step("Make Request for Authorization", () -> {
            return given()
                    .spec(loginTestReq)
                    .body(login)
                    .when()
                    .post()
                    .then()
                    .spec(loginTestRes)
                    .extract().as(LoginModel.class);
        });
        step("Check Results", () -> {
            Assertions.assertNotEquals(null, login1.getToken());
            Assertions.assertNotEquals(null, login1.getId());
        });
    }

    @DisplayName("Некорректная авторизация пользователя")
    @Test
    void unsuccessfulLoginTest() {
        String authData = "{\"email\": \"eve.dod@mail.ru\", \"password\": \"cityslicka\"}";

        given()
                .body(authData)
                .log().uri()

                .when()
                .post("https://reqres.in/api/login")

                .then()
                .log().status()
                .log().body()
                .statusCode(400);
    }

    @DisplayName("Успешная проверка наличия записи по существующему")
    @Test
    void successfulGetLinkInfoTest() {
        given()
                .log().uri()

                .when()
                .get("/unknown")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.name", hasItem("cerulean"));
    }

    @DisplayName("Негативная проверка наличия несуществующего пользователя")
    @Test
    void unsuccessfulGetLinkInfoTest() {
        given()
                .log().uri()

                .when()
                .get("/users/91")

                .then()
                .log().status()
                .log().body()
                .statusCode(404);
    }


    @DisplayName("Успешное обновление данных о пользоваателе")
    @Test
    void successfulCheckUserUpdatedInfoTest() {
        String requestBody = "{ \"name\": \"morpheus\", \"job\": \"zion resident\" }";
        given()
                .body(requestBody)
                .contentType(JSON)
                .log().uri()

                .when()
                .put("https://reqres.in/api/users/2")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("name", equalTo("morpheus"))
                .body("job", equalTo("zion resident"))
                .body("updatedAt", notNullValue());
    }

    @DisplayName("Успешное получение пользователя по id")
    @Test
    void getSingleUserTest() {

        given()
                .log().uri()

                .when()
                .get("/users/3")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.id", is(3));
    }

    @DisplayName("Успешное удаление пользователя")
    @Test
    public void deleteUserTest() {

        given()
                .log().uri()
                .when()
                .delete("api/users/5")
                .then()
                .log().status()
                .log().body()
                .statusCode(204);
    }

}