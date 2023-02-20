package usergenerator;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import userinfo.UserInfo;

import static io.restassured.RestAssured.given;


public class UserGenerator {

            public RequestSpecification requestSpec = new RequestSpecBuilder()
                    .setBaseUri("http://localhost")
                    .setPort(9999)
                    .setAccept(ContentType.JSON)
                    .setContentType(ContentType.JSON)
                    .log(LogDetail.ALL)
                    .build();

            public void authTest(String login, String password, String status) {
                UserInfo user = new UserInfo(login, password, status);
                given()
                        .spec(requestSpec)
                        .body(user)
                        .when()
                        .post("/api/system/users")
                        .then()
                        .statusCode(200);
            }
        }
