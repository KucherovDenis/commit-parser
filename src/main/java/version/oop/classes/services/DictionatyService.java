package version.oop.classes.services;

import version.oop.interfaces.Data;
import version.oop.classes.Utils;
import version.oop.classes.converters.DictionaryConverter;
import version.oop.classes.readers.TxtFileReader;
import version.oop.classes.readers.XlsFileReader;
import version.oop.interfaces.Reader;
import version.oop.interfaces.Repository;
import version.oop.interfaces.Service;

import java.io.IOException;
import java.util.*;

public class DictionatyService extends Service {

    private String dictionaryFile;

    public DictionatyService(List<String> filePaths, Repository repository) {
        super(filePaths, repository);

        if (filePaths != null)
            dictionaryFile = filePaths.get(0);
    }

    @Override
    public List<Data> LoadData() throws IOException {
        if (repository == null || dictionaryFile == null) return null;

        List<Data> dataList = new ArrayList<>();

        List<String> files = new ArrayList<>();
        files.add(dictionaryFile);

        Reader reader = null;
        if (Utils.isXlsFile(dictionaryFile)) {
            reader = new XlsFileReader();
        }
        if (Utils.isTxtFile(dictionaryFile)) {
            reader = new TxtFileReader();
        }

        if (reader != null) {
            repository.loadData(files, reader);
            dataList.addAll(repository.getData(new DictionaryConverter()));
        }

        return Collections.unmodifiableList(dataList);
    }
}
