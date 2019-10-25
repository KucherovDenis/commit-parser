package version.oop.classes.writers;

import version.oop.interfaces.Data;
import version.oop.interfaces.BaseWriter;
import version.oop.interfaces.Formatter;

import java.util.List;

public class ConsoleWriter extends BaseWriter {
    @Override
    public void write(List<Data> dataList, Formatter formatter) {
        Formatter consoleFormatter = getFormatter(formatter);

        StringBuilder sb = new StringBuilder();

        for (Data data : dataList) {
            if (consoleFormatter == null) sb.append(data.toString());
            else sb.append(consoleFormatter.formatToStr(data));
            sb.append("\n");
        }

        System.out.println(sb.toString());
    }

    @Override
    public void addLog(String message) {
        System.out.println(message);
    }

}
