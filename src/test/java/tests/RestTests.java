package tests;

import models.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.ReqresTestSpec.*;


@Tag("apiTests")
public class RestTests extends TestBase {

    @DisplayName("Successful user authorization")
    @Test
    void successfulLoginTest() {
        final LoginModel login = new LoginModel();
        login.setEmail("eve.holt@reqres.in");
        login.setPassword("cityslicka");

        LoginModel login1 = step("Make Request for Authorization", () -> {
            return given()
                    .spec(loginTestReq)
                    .body(login)
                    .when()
                    .post()
                    .then()
                    .spec(responseWithStatusCode200)
                    .extract().as(LoginModel.class);
        });
        step("Check Results", () -> {
            Assertions.assertEquals("QpwL5tke4Pnpja7X4", login1.getToken(), login1.getId());
        });
    }

    @DisplayName("Incorrect user authorization")
    @Test
    void unsuccessfulLoginTest() {
        final LoginModel login = new LoginModel();
        login.setEmail("eve.hod@reqres.in");
        login.setPassword("cityslicka");

        MissingPasswordModel login2 = step("Make request", () -> {
            return given()
                    .spec(loginTestReq)
                    .body(login)
                    .when()
                    .post()
                    .then()
                    .spec(missingLoginResponseSpec)
                    .extract().as(MissingPasswordModel.class);
        });
        step("Check Results", () -> {
            Assertions.assertEquals("user not found", login2.getError());
        });
    }

    @DisplayName("Successful verification of the existence of an entry for an existing user")
    @Test
    void successfulGetLinkInfoTest() {
        SingleUserResponseModel response =
                step("Send a GET request to receive the material", () ->
        given(mainPageTestReq)
        .get("/api/unknown/2")
        .then()
                .spec(responseWithStatusCode200)
                .extract().as(SingleUserResponseModel.class));
        step("Check the id in the response", () ->
        assertThat(response.getData().getId()).isEqualTo(2));
    }

    @DisplayName("Negative verification of the presence of a non-existent user")
    @Test
    void unsuccessfulGetLinkInfoTest() {
        step("Send a GET request to receive the material", () ->
        given(mainPageTestReq)
                .get("/api/users/91")
                .then()
                .spec(responseWithStatusCode404));
    }


    @DisplayName("Successful update of user data")
    @Test
    void successfulCheckUserUpdatedInfoTest() {
        UpdateUserBodyModel userUpdate = new UpdateUserBodyModel();
        userUpdate.setName("morpheus");
        userUpdate.setJob("zion resident");
        UpdateUserResponseModel response =
                step("Send a PUT request to update the user", () ->
            given(mainPageTestReq)
                    .body(userUpdate)
                    .when()
                    .put("/api/users/2")
                    .then()
                    .spec(responseWithStatusCode200)
                    .extract().as(UpdateUserResponseModel.class));
        step("Check the name in the response", () ->
            assertThat(response.getName()).isEqualTo("morpheus"));
        step("Check the work in the response", () ->
            assertThat(response.getJob()).isEqualTo("zion resident"));
    }



    @DisplayName("Successful user deletion")
    @Test
    public void deleteUserTest() {
        step("Send a DELETE request to delete a user", () ->
        given(mainPageTestReq)
                .delete("api/users/5")
                .then()
                .spec(responseWithStatusCode204));
    }

}