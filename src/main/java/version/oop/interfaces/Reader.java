package version.oop.interfaces;

import java.io.IOException;
import java.util.List;

public interface Reader {

    List<List<String>> read(String filePath) throws IOException;
}
