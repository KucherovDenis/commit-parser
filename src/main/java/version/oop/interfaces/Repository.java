package version.oop.interfaces;

import java.io.IOException;
import java.util.List;

public interface Repository {
    void loadData(List<String> paths, Reader reader) throws IOException;
    List<Data> getData(Converter converter);
}
