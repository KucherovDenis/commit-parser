package version.oop.classes.formatters;

import version.oop.interfaces.Data;
import version.oop.interfaces.BaseFormatter;

import java.util.ArrayList;
import java.util.List;

public class FileFormatter extends BaseFormatter {

    public FileFormatter() {
    }

    public FileFormatter(String separator) {
        super(separator);
    }

    @Override
    public List<String> format(Data data) {
        List<String> result = new ArrayList<>();
        result.addAll(data.getOthers());
        return result;
    }
}
