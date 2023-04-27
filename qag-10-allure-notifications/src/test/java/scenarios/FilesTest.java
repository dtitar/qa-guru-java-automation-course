package scenarios;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import org.junit.jupiter.api.Test;
import utils.Attachments;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static utils.Attachments.attachText;
import static utils.Utils.*;

public class FilesTest {

    private static final String EXPECTED_TEXT = "Ubuntu — это разрабатываемая сообществом, основанная на ядре Linux операционная система";

    @Test
    final void testTxtFileContainsText() {
        String fileContent = readTextFromTxtFile("about_ubuntu.txt");
        step("Проверяем, что содержимое файла содержит ожидаемый текст", () -> {
            assertThat(fileContent).contains(EXPECTED_TEXT);
            attachText("Ожидаемый текст", EXPECTED_TEXT);
        });
    }

    @Test
    final void testPdfFileContainsText() {
        PDF pdf = readTextFromPdfFile("about_ubuntu.pdf");
        step("Проверяем, что содержимое файла содержит ожидаемый текст", () -> {
            assertThat(pdf.text).contains(EXPECTED_TEXT);
            attachText("Ожидаемый текст", EXPECTED_TEXT);
        });
    }

    @Test
    final void testXlsxFileContainsText() {
        XLS xls = readTextFromExcelFile("top250.xlsx");
        step("Проверяем, что содержимое файла содержит ожидаемый текст", () -> {
            org.hamcrest.MatcherAssert.assertThat(xls, XLS.containsText("Остров проклятых"));
            attachText("Ожидаемый текст", "Остров проклятых");
        });
        step("Проверяем, что содержимое первой ячейки xls-файла содержит ожидаемый текст", () -> {
            org.hamcrest.MatcherAssert.assertThat(xls.excel.getSheetAt(0).getRow(0).getCell(0).toString(), containsString("Побег из Шоушенка"));
            attachText("Ожидаемый текст", "Побег из Шоушенка");
        });
    }

    @Test
    final void testZip() {
        String actualData = readTextFromFileInZipArchive("about_ubuntu.zip", "about_ubuntu_unzipped.txt");
        step("Проверяем, что содержимое файла содержит ожидаемый текст", () -> {
            assertThat(actualData, containsString(EXPECTED_TEXT));
            attachText("Ожидаемый текст", EXPECTED_TEXT);
        });
    }

    @Test
    final void testDocx() {
        String fileContent = readTextFromDocxFile("docx_file.docx");
        step("Проверяем, что содержимое файла содержит текст 'This is .docx file'", () -> {
            assertThat(fileContent).contains("This is .docx file");
            attachText("Ожидаемый текст", "This is .docx file");
        });
    }

    @Test
    final void testFail() {
        String fileContent = readTextFromTxtFile("about_ubuntu.txt");
        step("Проверяем, что содержимое файла содержит ожидаемый текст", () -> {
            assertThat(fileContent).contains("IncorrectText");
        });
    }
}
