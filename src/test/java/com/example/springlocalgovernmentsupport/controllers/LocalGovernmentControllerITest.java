package com.example.springlocalgovernmentsupport.controllers;

import static io.restassured.RestAssured.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import com.example.springlocalgovernmentsupport.commons.BaseTest;
import com.example.springlocalgovernmentsupport.dtos.AuthenticationRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.IOException;

@SpringBootTest(webEnvironment = RANDOM_PORT,
        properties = {"spring.mvc.throw-exception-if-no-handler-found=true", "spring.resources.add-mappings=false"})
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LocalGovernmentControllerITest extends BaseTest {

    @LocalServerPort
    private int port;

    private String token;

    @BeforeAll
    public void setup() {
        RestAssured.port = this.port;
        given()
                .contentType(ContentType.JSON)
                .body(AuthenticationRequest.builder().username("user").password("password").build())
                .when().post("/auth/signup")
                .then().assertThat().statusCode(200);

        token = given()
                .contentType(ContentType.JSON)
                .body(AuthenticationRequest.builder().username("user").password("password").build())
                .when().post("/auth/signin")
                .andReturn().jsonPath().getString("token");

        log.debug("Got token:" + token);
    }

    @Test
    public void whenFindLocalGovernmentsWithoutAuth_thenThrowsError() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/v1/local-governments")
                .then()
                .assertThat()
                .statusCode(403);
    }

    @Test
    public void whenFindLocalGovernmentsWithAuth_thenReturnsData() {
        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .when()
                .get("/v1/local-governments")
                .then()
                .statusCode(200);
    }

    @Test
    public void whenUploadFileWithoutAuth_thenThrowsError() throws IOException {
        given()
                .multiPart("file", readFile("data/temp.csv"))
                .when()
                .post("/v1/upload")
                .then()
                .assertThat()
                .statusCode(403);
    }

    @Test
    public void whenUploadFileWithInvalidToken_thenThrowsError() throws IOException {
        given()
                .header("Authorization", "Bearer Invalid Token")
                .multiPart("file", readFile("data/temp.csv"))
                .when()
                .post("/v1/upload")
                .then()
                .statusCode(400);
    }

    @Test
    public void whenUploadFileWithAuth_thenSuccess() throws IOException {
        given()
                .header("Authorization", "Bearer " + token)
                .multiPart("file", readFile("data/temp.csv"))
                .when()
                .post("/v1/upload")
                .then()
                .statusCode(200);
    }
}
