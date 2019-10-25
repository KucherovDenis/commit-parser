package version.oop.classes.services;

import version.oop.classes.Utils;
import version.oop.interfaces.Data;
import version.oop.classes.readers.TxtFileReader;
import version.oop.classes.readers.XlsFileReader;
import version.oop.classes.converters.SourceConverter;
import version.oop.interfaces.Repository;
import version.oop.interfaces.Service;

import java.io.IOException;
import java.util.*;

public class SourceService extends Service {
    private List<String> xlsFiles;
    private List<String> txtFiles;

    public SourceService(List<String> filePaths, Repository repository) {

        super(filePaths, repository);

        xlsFiles = new ArrayList<>();
        txtFiles = new ArrayList<>();

        if (filePaths != null) {
            for (String filePath : filePaths) {
                if (Utils.isXlsFile(filePath)) {
                    xlsFiles.add(filePath);
                }
                if (Utils.isTxtFile(filePath)) {
                    txtFiles.add(filePath);
                }
            }
        }

    }

    @Override
    public List<Data> LoadData() throws IOException {
        if (repository == null) return null;

        List<Data> dataList = new ArrayList<>();

        if (xlsFiles.size() > 0) {
            repository.loadData(xlsFiles, new XlsFileReader());
            dataList.addAll(repository.getData(new SourceConverter()));
        }

        if (txtFiles.size() > 0) {
            repository.loadData(txtFiles, new TxtFileReader());
            dataList.addAll(repository.getData(new SourceConverter()));
        }

        return Collections.unmodifiableList(dataList);
    }
}
