package version.oop.interfaces;

import java.io.IOException;
import java.util.List;

public abstract class Service {

    protected Repository repository;

    protected List<String> filePaths;

    public Service(List<String> filePaths, Repository repository) {
        this.repository = repository;
        this.filePaths = filePaths;
    }

    public abstract List<Data> LoadData() throws IOException;
}
