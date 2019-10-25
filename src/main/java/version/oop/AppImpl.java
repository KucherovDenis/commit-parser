package version.oop;

import version.oop.interfaces.Data;
import version.oop.classes.RepositoryImpl;
import version.oop.classes.Utils;
import version.oop.classes.services.*;
import version.oop.interfaces.Service;
import version.oop.interfaces.Writer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class AppImpl {

    private Service dictionaryService;
    private Service sourceService;

    private List<Writer> writers;

    public AppImpl(String dictionaryPath, String folderPath) {
        if (dictionaryPath != null) {
            List<String> list = new ArrayList<>();
            list.add(dictionaryPath);

            dictionaryService = new DictionatyService(list, new RepositoryImpl());
        }

        if (folderPath != null) {
            List<String> list = Utils.readFilesFromDirectory(folderPath);
            sourceService = new SourceService(list, new RepositoryImpl());
        }

        writers = new ArrayList<>();
    }

    public void addWriter(Writer writer) {
        Objects.requireNonNull(writer);
        writers.add(writer);
    }

    private List<Data> compare(List<Data> dataSource, List<Data> dataDictionary) {
        List<Data> result = new ArrayList<>();
        if (dataSource != null) {
            result.addAll(dataSource);

            if (dataDictionary != null)
                for (Data source : dataSource)
                    for (Data data : dataDictionary)
                        if (data.equals(source)) {
                            result.remove(source);
                            break;
                        }
        }
        return result;
    }

    private void writeAll(List<Data> result) {
        for (Writer writer : writers)
            try {
                writer.write(Collections.unmodifiableList(result));
            } catch (IOException e) {
                writeLog(e.getMessage());
            }
    }

    private void writeLog(String msg) {
        for (Writer writer : writers)
            writer.addLog(msg);
    }

    private List<Data> LoadData(Service service) {
        List<Data> data = null;
        try {
            data = service.LoadData();
        } catch (IOException e) {
            writeLog(e.getMessage());
        }

        return data;
    }

    public void execute() {
        if (sourceService == null || dictionaryService == null) return;

        List<Data> dataSource = LoadData(sourceService);
        List<Data> dataDictionary = LoadData(dictionaryService);

        List<Data> result = compare(dataSource, dataDictionary);

        writeAll(result);
    }
}
