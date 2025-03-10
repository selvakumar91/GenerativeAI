package automation.generator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestCodeGenerator {

    public String extractJavaCode(String llmResponse) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(llmResponse);
            JsonNode choicesNode = rootNode.path("choices");
            if (choicesNode.isArray() && !choicesNode.isEmpty()) {
                JsonNode messageNode = choicesNode.get(0).path("message");
                String content = messageNode.path("content").asText().trim();
                if (content.contains("```java")) {
                    int start = content.indexOf("```java");
                    int end = content.lastIndexOf("```");
                    if (start != -1 && end != -1 && end > start) {
                        content = content.substring(start + "```java".length(), end).trim();
                    }
                } else if (content.contains("package")) {
                    int index = content.indexOf("package");
                    content = content.substring(index).trim();
                }
                return addMissingImports(content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertToJavaCode(llmResponse);
    }

    public String convertToJavaCode(String extractedCode) {
        return addMissingImports(extractedCode);
    }

    /**
     * Ensures all required Rest Assured and Hamcrest imports are included in the generated code.
     */
    private String addMissingImports(String javaCode) {
        String requiredImports = """
                import io.restassured.RestAssured;
                import io.restassured.http.ContentType;
                import io.restassured.response.Response;
                import org.testng.annotations.BeforeMethod;
                import org.testng.annotations.Test;
                import static io.restassured.RestAssured.given;
                import static org.testng.Assert.assertEquals;
                import static org.hamcrest.Matchers.equalTo;
                import static org.hamcrest.Matchers.notNullValue;
                """;

        if (!javaCode.contains("import io.restassured.RestAssured;")) {
            javaCode = requiredImports + "\n" + javaCode;
        }
        return javaCode;
    }

    public String extractPlaywrightCode(String llmResponse) {
        String pwcontent = "";
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(llmResponse);
            JsonNode choicesNode = rootNode.path("choices");
            if (choicesNode.isArray() && !choicesNode.isEmpty()) {
                JsonNode messageNode = choicesNode.get(0).path("message");
                 pwcontent = messageNode.path("content").asText().trim();
            }

        }catch(Exception e){
                e.printStackTrace();
        }
        return pwcontent;
    }

    public String extractCypressCode(String llmResponse) {
        String cypcontent = "";
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(llmResponse);
            JsonNode choicesNode = rootNode.path("choices");
            if (choicesNode.isArray() && !choicesNode.isEmpty()) {
                JsonNode messageNode = choicesNode.get(0).path("message");
                cypcontent = messageNode.path("content").asText().trim();
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return cypcontent;
    }
}
