package version.oop.classes.writers;

import version.oop.interfaces.Data;
import version.oop.interfaces.FileWriter;
import version.oop.interfaces.Formatter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class TxtFileWriter extends FileWriter {

    private static final String TXT_EXT = ".txt";

    public TxtFileWriter(String fileName) {
        super(fileName);
        setFileName(fileName);

    }

    @Override
    public void setFileName(String fileName) {
        fileName += TXT_EXT;
        super.setFileName(fileName);
    }

    public void write(List<Data> dataList, Formatter formatter) throws IOException {
        File txt = new File(getFileName());
        Formatter txtFormatter = Objects.requireNonNull(getFormatter(formatter));

        try (BufferedWriter out = new BufferedWriter(new java.io.FileWriter(txt))) {
            for(Data row: dataList) {
                String line = txtFormatter.formatToStr(row);
                out.write(line);
            }
        }
    }
}
