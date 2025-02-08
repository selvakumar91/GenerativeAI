package automation.pipeline;

import automation.generator.LLMPWTestGenerator;
import automation.generator.TestCodeGenerator;
import automation.parser.JavaClassParser;
import automation.util.FileUtils;

public class TestAutomationToCreatePWFromSelnium {

    public static void main(String[] args){
        String seleniumpath = "src/main/java/automation/seleniumscripts/leafTapsCreateOptions.java";

        // 1. Parse the Java spec
        JavaClassParser parser = new JavaClassParser();
        String seleniumCodeDetails = parser.seleniumCodeParser(seleniumpath);

        // 2. Generate test case outline using LLM
        LLMPWTestGenerator llmPWGen = new LLMPWTestGenerator();
        String llmPWRawOutput = llmPWGen.generateTestCases(seleniumCodeDetails);

        // 3. Extract only the Playwright code from the LLM response
        TestCodeGenerator codeGen = new TestCodeGenerator();
        String finalPWTestCode = codeGen.extractPlaywrightCode(llmPWRawOutput);

        //5. Write generated code to a JavaScript (.js) file with a name matching the class name
        String outputFileName = "PlayWrightTests.js";
        FileUtils.writeToFile(finalPWTestCode, "src/main/java/automation/tests/"+outputFileName);

        System.out.println("Generated test code written to " + outputFileName);
    }
}
