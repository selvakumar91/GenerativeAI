package automation.pipeline;

import automation.generator.LLMCypressTestGenerator;
import automation.generator.TestCodeGenerator;
import automation.parser.JavaClassParser;
import automation.util.FileUtils;

public class TestAutomationToCreateCypressFromSelnium {

    public static void main(String[] args){
       // String seleniumpath = "src/main/java/automation/seleniumscripts/leafTapsCreateOptions.java";
        //String seleniumpath = "src/main/java/automation/seleniumscripts/CreateLead.java";
        String seleniumpath = "src/main/java/automation/seleniumscripts/SalesforceLogin.java";

        // 1. Parse the Java spec
        JavaClassParser parser = new JavaClassParser();
        String seleniumCodeDetails = parser.seleniumCodeParser(seleniumpath);

        // 2. Generate test case outline using LLM
        LLMCypressTestGenerator llmCypressGen = new LLMCypressTestGenerator();
        String llmCypressRawOutput = llmCypressGen.generateTestCases(seleniumCodeDetails);

        // 3. Extract only the Playwright code from the LLM response
        TestCodeGenerator codeGen = new TestCodeGenerator();
        String finalPWTestCode = codeGen.extractCypressCode(llmCypressRawOutput);

        //5. Write generated code to a JavaScript (.js) file with a name matching the class name
        String outputFileName = "CypressTest.cy.js";
        FileUtils.writeToFile(finalPWTestCode, "src/main/java/automation/tests/"+outputFileName);

        System.out.println("Generated test code written to " + outputFileName);
    }
}
