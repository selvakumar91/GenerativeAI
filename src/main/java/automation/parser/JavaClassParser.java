package automation.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JavaClassParser {

    public String seleniumCodeParser(String seleniumFilePath) {
       String seleniumFileContent = "";

        try {
            //Read the file content
            Path path = Paths.get(seleniumFilePath);
             seleniumFileContent = new String(Files.readString(path));
            
        } catch (IOException e) {
           System.err.println("Error reading from file: " +e.getMessage());
        }

        return seleniumFileContent;

       /* StringBuilder code = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(seleniumFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                code.append(line).append("\n");
            }
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }

        return code.toString();*/
    }
}
