package scenarios;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static utils.Utils.*;

public class FilesTest {

    private static final String EXPECTED_TEXT = "Ubuntu — это разрабатываемая сообществом, основанная на ядре Linux операционная система";

    @Test
    final void txtFileContainsTextTest() {
        String fileContent = readTextFromTxtFile("about_ubuntu.txt");
        assertThat(fileContent).contains(EXPECTED_TEXT);
    }

    @Test
    final void pdfFileContainsTextTest() {
        PDF pdf = readTextFromPdfFile("about_ubuntu.pdf");
        assertThat(pdf.text).contains(EXPECTED_TEXT);
    }

    @Test
    final void xlsxFileContainsTextTest() {
        XLS xls = readTextFromExcelFile("top250.xlsx");
        org.hamcrest.MatcherAssert.assertThat(xls, XLS.containsText("Остров проклятых"));
        org.hamcrest.MatcherAssert.assertThat(xls.excel.getSheetAt(0).getRow(0).getCell(0).toString(), containsString("Побег из Шоушенка"));
    }

    @Test
    final void zipTest() {
        String actualData = readTextFromFileInZipArchive("about_ubuntu.zip", "about_ubuntu_unzipped.txt");
        assertThat(actualData, containsString(EXPECTED_TEXT));
    }

    @Test
    final void docxTest() {
        String fileContent = readTextFromDocxFile("docx_file.docx");
        assertThat(fileContent).contains("This is .docx file");
    }
}
