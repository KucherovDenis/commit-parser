package version.oop.classes.formatters;

import version.oop.interfaces.Data;
import version.oop.interfaces.BaseFormatter;

import java.util.ArrayList;
import java.util.List;

public class ConsoleFormatter extends BaseFormatter {

    public ConsoleFormatter(String separator) {
        super(separator);
    }

    @Override
    public List<String> format(Data data) {
        List<String> result = new ArrayList<>();
        result.add(data.getJiraKey());
        result.add(data.getBbKey());
        result.add(data.getNameKey());
        result.addAll(data.getOthers());
        return result;
    }
}