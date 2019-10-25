package ru.sbrf.ufs.release.commit;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Hello!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {

        //в args[1] адрес папки, в которой лежит набор файлов
        final File folder = new File(args[1]);
        List<String> filesList = readFilesFromDirectory(folder);

        //мы заходим в каждый файл и считываем строки в список
        List<List<String>> sourceData = convertSourceDataToDictionaryFormat(getDataFromSourceFiles(filesList));

        //читаем файл справочника с которым будем сравнивать данные из sourceData
        final List<List<String>> dictionaryData = readXlsxFile(args[0]);

        //анализ sourceData по данным в dictionaryData и формирование списка коммитов
        List<List<String>> resultList = getResultList(sourceData, dictionaryData);
        writeXlsxFile(resultList);

        System.out.println(xlsListToRow(resultList));
    }

    private static List<List<String>> getResultList(List<List<String>> sourceData, List<List<String>> dictionaryData) {
        List<List<String>> resultList = new ArrayList<>(sourceData);
        for(int i = 0; i < sourceData.size(); ++i){
            List<String> currentSource = sourceData.get(i);
            for (int j = 0; j < dictionaryData.size(); ++j){
                List<String> currentDictionary = dictionaryData.get(j);
                String sourceJiraKey = currentSource.get(0);
                String dictionaryJiraKey = currentDictionary.get(0);

                String sourceBbKey = currentSource.get(1);
                String dictionaryBbKey = currentDictionary.get(1);

                String sourceNameKey = currentSource.get(2);
                String dictionaryKey = currentDictionary.get(2);

                if (sourceJiraKey.equalsIgnoreCase(dictionaryJiraKey)) {
                    if (sourceBbKey.equalsIgnoreCase(dictionaryBbKey)){
                        if (sourceNameKey.equalsIgnoreCase(dictionaryKey)){
                            int index = getIndexOfElement(resultList, sourceJiraKey, sourceBbKey, sourceNameKey);
                            resultList.remove(index);
                            continue;
                        }
                    }
                }
            }
        }
        return resultList;
    }

    private static Integer getIndexOfElement(List<List<String>> resultList,
                                             String jiraKey,
                                             String bbKey,
                                             String nameKey){
        for (int i = 0; i < resultList.size(); ++i){
            if (jiraKey.equalsIgnoreCase(resultList.get(i).get(0))) {
                if (bbKey.equalsIgnoreCase(resultList.get(i).get(1))) {
                    if (nameKey.equalsIgnoreCase(resultList.get(i).get(2))) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }

    private static List<List<String>> convertSourceDataToDictionaryFormat(List<List<String>> sourceData){
        List<List<String>> dictionaryDataFormant = new ArrayList<>();

        for (int i = 0; i < sourceData.size(); ++i){

            List<String> source = new ArrayList<>();
            source.add(getJiraKey(sourceData.get(i)));
            source.add(getBbKey(sourceData.get(i)));
            source.add(getNameKeyKey(sourceData.get(i)));
            source.addAll(sourceData.get(i));

            dictionaryDataFormant.add(source);
        }

        return dictionaryDataFormant;
    }

    private static String getNameKeyKey(List<String> row){
        String commotString = row.get(0);
        String[] split = commotString.split("/");
        return split[7];
    }
    private static String getBbKey(List<String> row){
        String commotString = row.get(0);
        String[] split = commotString.split("/");
        return split[5];
    }
    private static String getJiraKey(List<String> row){
        String jiraTask = row.get(2);
        int index = jiraTask.indexOf('-');
        return jiraTask.substring(0, index);
    }

    private static List<String> readFilesFromDirectory(File folder){
        List<String> list = new ArrayList<>();

        for (final File fileEntry : folder.listFiles()) {
            if (!fileEntry.isDirectory()) {
                list.add(folder.getPath() + "\\" + fileEntry.getName());
            }
        }
        return list;
    }
    public static List<List<String>> getDataFromSourceFiles(List<String> sourceFileName) throws IOException {

        List<List<String>> sourceFilesData = new ArrayList<>();
        for (int k = 0; k < sourceFileName.size(); ++k) {
            sourceFilesData.addAll(readXlsxFile(sourceFileName.get(k)));
        }
        return sourceFilesData;
    }

    public static List<List<String>> readXlsxFile(String filePath) throws IOException {

        List<List<String>> result = new ArrayList<>();

        InputStream stream = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(stream);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rows = sheet.rowIterator();
        rows.next();
        while (rows.hasNext()) {
            Row row = rows.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            List<String> line = new ArrayList<>();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                CellType cellType = cell.getCellTypeEnum();
                switch (cellType) {
                    case STRING:
                        line.add(cell.getStringCellValue());
                        break;
                    case NUMERIC:
                        line.add(new Integer((int) cell.getNumericCellValue()).toString());
                        break;
                }
            }
            if (line.size() != 0) {
                result.add(line);
            }
        }
        return result;
    }
    public static void writeXlsxFile(List<List<String>> resultList) throws IOException {
        File excel = new File("data/result.xls");
        Workbook book = new HSSFWorkbook();
        Sheet sheet = book.createSheet("Missing Commits");

        for (int i = 0; i < resultList.size(); ++i){
            Row row = sheet.createRow(i);
            for (int j = 3; j < resultList.get(i).size(); ++j){
                Cell cell = row.createCell(j - 3);
                cell.setCellValue(resultList.get(i).get(j));
            }
        }
        sheet.autoSizeColumn(1);
        FileOutputStream os = new FileOutputStream(excel);
        book.write(os);
        book.close();

    }

    //методы вывода результатов чтения файлов в консоль
    public static String xlsListToRow(List<List<String>> xlsList){
        String result = "";
        for (int i = 0; i < xlsList.size(); ++i){
            result += xlsRowToString(xlsList.get(i)) + "\n";
        }
        return result;
    }
    public static String xlsRowToString(List<String> xlsRow){
        String result = "";
        for (int i = 0; i < xlsRow.size(); ++i){
            result += xlsRow.get(i) + ";";
        }
        return result;
    }
}


