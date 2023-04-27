package utils;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static utils.Utils.RESOURCES_DIR;


public class Attachments {


    @Attachment(value = "{attachName}", type = "text/plain")
    public static String attachText(String attachName, String message) {
        return message;
    }

    public static void attachFile(String fileName) {
        Path content = Paths.get(RESOURCES_DIR + fileName);
        try (InputStream is = Files.newInputStream(content)) {
            Allure.addAttachment(fileName, is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
