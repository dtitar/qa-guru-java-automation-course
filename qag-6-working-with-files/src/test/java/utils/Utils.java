package utils;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.Files.readString;

/**
 * Class, containing utils methods for working with different types of files
 */
public class Utils {

    private static final String RESOURCES_DIR = System.getProperty("user.dir") + File.separator
            + "src" + File.separator
            + "test" + File.separator
            + "resources" + File.separator;


    public static String readTextFromDocFile(String fileName) throws IOException {
        File file = new File(RESOURCES_DIR + fileName);
        try (FileInputStream fis = new FileInputStream(file);
             HWPFDocument document = new HWPFDocument(fis);
             WordExtractor extractor = new WordExtractor(document)) {
            return extractor.getText();
        }
    }

    public static String readTextFromDocxFile(String fileName) {
        File file = new File(RESOURCES_DIR + fileName);
        try (FileInputStream fis = new FileInputStream(file);
             XWPFDocument document = new XWPFDocument(fis);
             XWPFWordExtractor extractor = new XWPFWordExtractor(document)) {
            return extractor.getText();
        } catch (FileNotFoundException e) {
            throw new Error("Файл не найден", e);
        } catch (IOException e) {
            throw new Error("Произошла ошибка при чтении файла", e);
        }
    }

    public static String readTextFromTxtFile(String fileName) {
        try {
            return readString(Path.of(RESOURCES_DIR + fileName));
        } catch (IOException e) {
            throw new Error("Произошла ошибка при чтении файла", e);
        }
    }

    public static PDF readTextFromPdfFile(String fileName) {
        try {
            return new PDF(new File(RESOURCES_DIR + fileName));
        } catch (IOException e) {
            throw new Error("Произошла ошибка при чтении пдф-файла", e);
        }
    }

    public static XLS readTextFromExcelFile(String fileName) {
        return new XLS(new File(RESOURCES_DIR + fileName));
    }

    public static String readTextFromFileInZipArchive(String zipArchiveName, String fileInZipArchiveName) {
        unzip(zipArchiveName);
        return readTextFromTxtFile(fileInZipArchiveName);
    }

    private static void unzip(String zipArchiveName) {
        try {
            new ZipFile(RESOURCES_DIR + zipArchiveName).extractAll(RESOURCES_DIR);
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }
}
