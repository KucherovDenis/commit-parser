package version.oop.classes.readers;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import version.oop.interfaces.Reader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class XlsFileReader implements Reader {

    private void readSheet(Sheet sheet, List<List<String>> table) {
        Iterator<Row> rows = sheet.rowIterator();
        rows.next();
        while (rows.hasNext()) {
            Row row = rows.next();
            Iterator<Cell> cellIterator = row.cellIterator();

            List<String> line = readLine(cellIterator);

            if (line.size() != 0) {
                table.add(line);
            }

        }
    }

    private List<String> readLine(Iterator<Cell> cellIterator) {
        List<String> line = new ArrayList<>();

        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            CellType cellType = cell.getCellTypeEnum();
            switch (cellType) {
                case STRING:
                    line.add(cell.getStringCellValue());
                    break;
                case NUMERIC:
                    line.add(Integer.toString((int) cell.getNumericCellValue()));
                    break;
            }
        }

        return line;
    }

    @Override
    public List<List<String>> read(String filePath) throws IOException {
        List<List<String>> table = new ArrayList<>();
        InputStream stream = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(stream);
        Sheet sheet = workbook.getSheetAt(0);
        readSheet(sheet, table);
        return table;
    }
}
