package version.oop.classes.writers;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import version.oop.interfaces.Data;
import version.oop.interfaces.FileWriter;
import version.oop.interfaces.Formatter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class XlsFileWriter extends FileWriter {

    private static final String SHEET_NAME = "Missing Commits";
    private static final String XLS_EXT = ".xls";

    public XlsFileWriter(String fileName) {
        super(fileName);
        setFileName(fileName);

    }

    @Override
    public void setFileName(String fileName) {
        fileName += XLS_EXT;
        super.setFileName(fileName);
    }

    @Override
    public void write(List<Data> dataList, Formatter formatter) throws IOException {
        File excel = new File(getFileName());
        Formatter xlsFormatter = Objects.requireNonNull(getFormatter(formatter));

        try (Workbook book = new HSSFWorkbook();
             FileOutputStream os = new FileOutputStream(excel);) {

            Sheet sheet = book.createSheet(SHEET_NAME);

            for (int i = 0; i < dataList.size(); i++) {
                Row row = sheet.createRow(i);
                Data data = dataList.get(i);
                List<String> dataFormating = xlsFormatter.format(data);
                for (int j = 0; j < dataFormating.size(); j++) {
                    Cell cell = row.createCell(j);
                    cell.setCellValue(dataFormating.get(j));
                }
            }

            sheet.autoSizeColumn(1);
            book.write(os);

        } catch (IOException e) {
            throw e;
        }

    }
}
