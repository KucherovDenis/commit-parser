package version.oop.interfaces;

import java.util.List;

public interface Formatter {

    String getSeparator();

    void setSeparator(String separator);

    List<String> format(Data data);

    default String formatToStr(Data data) {
        List<String> params = format(data);
        StringBuilder sb = new StringBuilder();
        String separator = getSeparator();
        for (String param : params)
            sb.append(param).append(separator);
        return sb.toString();
    }
}
