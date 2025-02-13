package automation.generator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LLMCypressTestGenerator {

    private static final String LLM_API_URL = "https://api.groq.com/openai/v1/chat/completions";
    private static final String API_KEY = "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";

    public String generateTestCases(String seleniumFileContent) {
        if (seleniumFileContent == null || seleniumFileContent.isEmpty()) {
            return "No valid Selenium Code details to generate test cases.";
        }
        String userPrompt = "Convert Selenium Java test automation code to Cypress JavaScript while preserving the logic and functionality using the specified framework \n"
                            + seleniumFileContent;
        try {
            List<Map<String, String>> messages = new ArrayList<>();
            Map<String, String> systemMessage = new HashMap<>();
            systemMessage.put("role", "system");
            systemMessage.put("content", """
                             - Ensure that the converted code follows Cypress's best practices, including: \
                             -- Proper cy commands for handling asynchronous operations.\
                             -- Selectors conversion (e.g., By.id() → cy.get() equivalent).\
                             -- Handling of waits (Implicit/Explicit waits should be replaced with Cypress's built-in automatic waiting).\
                             -- Assertions should be mapped to Cypress’s test assertions where applicable.\
                             -- Maintain proper JavaScript ES6+ features where appropriate.\
                             -- Optimize code structure, removing unnecessary waits or redundant calls.\
                             -- Ensure that logging/debugging mechanisms (if present in Selenium) are mapped correctly to Cypress equivalents.\
                             -- The output must be idiomatic Cypress JavaScript, not just a direct Java-to-JavaScript translation.\
                             -- Follow Cypress Official Documentation to ensure all functions are correctly implemented.\
                             -- DO NOT add any additional steps other than given input code.\
                             -- Ensure strict mode is not violated when finding locators.\
                             -- Make sure to use { ensure: 'domcontentloaded' } when navigating.\
                             Context:\
                             I am building an AI-based prompt to convert Selenium Java code to Playwright TypeScript automatically.
                            The converted code must be production-ready, as accuracy is crucial for my career growth.
                             \
                            Example:
                            
                            Selenium Java (Input)
                            
                            java
                            import org.openqa.selenium.WebDriver;
                            import org.openqa.selenium.chrome.ChromeDriver;
                            
                            public class PrintTitle {
                              public static void main(String[] args) {
                                WebDriver driver = new ChromeDriver();
                                driver.get("http://playwright.dev");
                                System.out.println(driver.getTitle());
                                driver.quit();
                              }
                            }
                            Playwright TypeScript (Expected Output)
                            
                            typescript
                            import { test, expect } from '@playwright/test';
                            
                            test('has title', async ({ page }) => {
                              await page.goto('https://playwright.dev/');
                            
                              // Expect a title "to contain" a substring.
                              await expect(page).toHaveTitle(/Playwright/);
                            });
                             Persona:\
                            You are a Senior Test Automation Architect specializing in Selenium and Playwright migration.\s
                            Your responsibility is to ensure that the converted Playwright TypeScript code is accurate, maintainable, and follows industry best practices.\
                            Output Format: \
                             - The output should be fully working Playwright TypeScript code.\
                             - It should be structured as an executable script or within a test framework if required.\
                             - The code should be formatted properly and follow Playwright’s official documentation. \
                             - Do NOT Provide anything other than Playwright Code Such as explanations, Key Points.\
                             - Make Sure the comments are staying as it is in the code."""

                    );
   
            messages.add(systemMessage);
            Map<String, String> userMessage = new HashMap<>();
            userMessage.put("role", "user");
            userMessage.put("content", userPrompt);
            messages.add(userMessage);
            Map<String, Object> payload = new HashMap<>();
            //payload.put("model", "deepseek-r1-distill-llama-70b");
            payload.put("model", "llama-3.3-70b-versatile");
            payload.put("messages", messages);
            payload.put("temperature", 0.2);
            payload.put("max_tokens", 1500);
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writeValueAsString(payload);
            return callLLMApi(requestBody);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error building JSON payload: " + e.getMessage();
        }
    }

    private String callLLMApi(String requestBody) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(LLM_API_URL);
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Authorization", "Bearer " + API_KEY);
            request.setEntity(new StringEntity(requestBody));
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                return EntityUtils.toString(response.getEntity());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error calling LLM API: " + e.getMessage();
        }
    }
}
