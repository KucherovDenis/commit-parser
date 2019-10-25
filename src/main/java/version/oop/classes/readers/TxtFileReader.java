package version.oop.classes.readers;

import version.oop.interfaces.Reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TxtFileReader implements Reader {

    private static final String SEPARATOR = "\t";

    private List<String> parseLine(String line) {
        List<String> result = new ArrayList<>();
        String[] params = line.split(SEPARATOR);
        result.addAll(Arrays.asList(params));
        return result;
    }

    @Override
    public List<List<String>> read(String filePath) throws IOException {
        File file = new File(filePath);
        List<List<String>> table = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            String line;
            line = in.readLine();
            while ((line = in.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty())
                    table.add(parseLine(line));
            }
        }
        return table;
    }
}
