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

public class LLMPWTestGenerator {

    private static final String LLM_API_URL = "https://api.groq.com/openai/v1/chat/completions";
    private static final String API_KEY = "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";

    public String generateTestCases(String seleniumFileContent) {
        if (seleniumFileContent == null || seleniumFileContent.isEmpty()) {
            return "No valid Selenium Code details to generate test cases.";
        }
        String userPrompt = "Convert Selenium Java test automation code to Playwright TypeScript while preserving the logic and functionality using the specified framework \n"
                            + seleniumFileContent;
        try {
            List<Map<String, String>> messages = new ArrayList<>();
            Map<String, String> systemMessage = new HashMap<>();
            systemMessage.put("role", "system");
            systemMessage.put("content", """
                             - Ensure that the converted code follows Playwright's best practices, including: \
                             -- Proper async/await usage for handling asynchronous operations.\
                             -- Selectors conversion (e.g., By.id() → page.locator() equivalent).\
                             -- Handling of waits (Implicit/Explicit waits should be replaced with Playwright’s auto-waiting).\
                             -- Assertions should be mapped to Playwright’s test assertions if applicable.\
                             -- Maintain proper TypeScript typings (Page, Browser, etc.) and use ES6+ features where appropriate.\
                             -- Optimize code structure, removing unnecessary waits or redundant calls.\
                             -- Ensure that logging/debugging mechanisms (if present in Selenium) are mapped correctly to Playwright equivalents.\
                             -- The output must be idiomatic Playwright TypeScript, not just a direct Java-to-TypeScript translation.\
                             -- Follow Playwright Official Documentation to ensure all functions are correctly\
                             -- DO NOT add any additional steps other than given input code.\
                             -- Disable strict mode violation when finding locators\
                             -- Ensure to use  { waitUntil: 'domcontentloaded',  timeout: 30000  }\
                             Context:\
                             I am building an AI-based prompt to convert Selenium Java code to Cypress JavaScript automatically.
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
                            Cypress JavaScrip (Expected Output)
                            
                            javascript
                            describe('Print Title Test', () => {\
                            
                             it('should have the correct title', () => {
                             cy.visit('https://playwright.dev', { ensure: 'domcontentloaded' });
                            
                              // Expect the title to contain 'Playwright'
                              cy.title().should('include', 'Playwright');
                            });
                            });
                             Persona:\
                            You are a Senior Test Automation Architect specializing in Selenium and Cypress migration.\s
                            Your responsibility is to ensure that the converted Cypress JavaScript code is accurate, maintainable, and follows industry best practices.
                            Output Format: \
                             - The output should be fully working Cypress JavaScrip code.\
                             - It should be structured as an executable script or within a test framework if required.\
                             - The code should be formatted properly and follow Cypress's official documentation. \
                             - Do NOT Provide anything other than Cypress Code Such as explanations, Key Points.\
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
