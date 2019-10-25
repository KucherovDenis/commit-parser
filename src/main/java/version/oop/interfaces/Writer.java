package version.oop.interfaces;

import java.io.IOException;
import java.util.List;

public interface Writer {
    void write(List<Data> dataList, Formatter formatter) throws IOException;
    void addLog(String message);
    default void write(List<Data> dataList) throws IOException{
        write(dataList, null);
    }

    void addFormatter(Formatter formatter);
}
