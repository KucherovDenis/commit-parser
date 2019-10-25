package version.oop.classes;

import version.oop.interfaces.Data;
import version.oop.interfaces.Converter;
import version.oop.interfaces.Reader;
import version.oop.interfaces.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RepositoryImpl implements Repository {

    private List<List<String>> dataSet = null;

    @Override
    public void loadData(List<String> paths, Reader reader) throws IOException {

        if (reader == null)
            throw new NullPointerException("reader is null");

        if (paths == null)
            throw new NullPointerException("paths is null");

        dataSet = new ArrayList<>();
        for (String filePath : paths) {
            dataSet.addAll(reader.read(filePath));
        }
    }

    @Override
    public List<Data> getData(Converter converter) {
        if (dataSet == null) return null;

        if (converter == null)
            throw new NullPointerException("converter is null");

        List<Data> dataList = new ArrayList<>();
        for(List<String> data: dataSet) {
            dataList.add(converter.getAsData(data));
        }

        return dataList;
}
}
