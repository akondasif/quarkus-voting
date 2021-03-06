package com.redhat.demo.voting;

import com.redhat.demo.voting.rest.Result;
import com.redhat.demo.voting.rest.Vote;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.wildfly.common.Assert;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class PollResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
                .when().get("/poll")
                .then()
                .statusCode(200)
                .body(is("hello voting app"));
    }

    @Test
    public void testResults() {
        Response response = given()
                .when().get("/poll/results")
                .then()
                .statusCode(200)
                .assertThat()
                .extract()
                .response();

        JsonPath jsonValidator = response.jsonPath();
        List<Result> results = jsonValidator.getObject("$", List.class);
        Assert.assertTrue(results.size() == 3);

    }

    @Test
    public void testResultsByPollId() {
        Response response = given()
                .when().get("/poll/results/1")
                .then()
                .statusCode(200)
                .assertThat()
                .extract()
                .response();

        JsonPath jsonValidator = response.jsonPath();
        List<Result> results = jsonValidator.getObject("$", List.class);
        Assert.assertTrue(results.size() == 3);

    }

    @Test
    public void testAddVote() {
        Vote vote = new Vote();
        vote.setPollId(1l);
        vote.setOption(1);
        vote.setId("1");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(vote)
                .post("/poll/vote");

        assertEquals(200, response.getStatusCode());

        JsonPath jsonValidator = response.jsonPath();
        Vote result = jsonValidator.getObject("$", Vote.class);
        Assert.assertTrue(result.equals(vote));
    }
}
