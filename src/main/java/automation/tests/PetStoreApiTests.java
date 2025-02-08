package automation.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class PetStoreApiTests {

    @BeforeMethod
    public void setUp() {
        // Setting the base URI for all tests
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    @Test
    public void updateExistingPet() {
        // Updating an existing pet with PUT request
        String requestBody = "{\n" +
                "  \"id\": 1,\n" +
                "  \"category\": {\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"Dogs\"\n" +
                "  },\n" +
                "  \"name\": \"Buddy\",\n" +
                "  \"photoUrls\": [\n" +
                "    \"string\"\n" +
                "  ],\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "      \"id\": 1,\n" +
                "      \"name\": \"Tag1\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \"available\"\n" +
                "}";
        
        given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when()
            .put("/pet")
            .then()
            .statusCode(200)
            .log()
            .all();
            
        System.out.println("Updated Pet Response: " + requestBody);
    }

    @Test
    public void addNewPet() {
        // Adding a new pet with POST request
        String requestBody = "{\n" +
                "  \"id\": 2,\n" +
                "  \"category\": {\n" +
                "    \"id\": 2,\n" +
                "    \"name\": \"Cats\"\n" +
                "  },\n" +
                "  \"name\": \"Whiskers\",\n" +
                "  \"photoUrls\": [\n" +
                "    \"string\"\n" +
                "  ],\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "      \"id\": 2,\n" +
                "      \"name\": \"Tag2\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \"pending\"\n" +
                "}";
        
        given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when()
            .post("/pet")
            .then()
            .statusCode(200)
            .log()
            .all();
            
        System.out.println("Added Pet Response: " + requestBody);
    }

    @Test
    public void findPetsByStatus() {
        // Finding pets by status with GET request
        given()
            .when()
            .get("/pet/findByStatus?status=available")
            .then()
            .statusCode(200)
            .log()
            .all();
            
        System.out.println("Pets by Status Response: ");
    }
}