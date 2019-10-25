package version.oop.interfaces;

import java.util.Objects;

public abstract class FileWriter extends BaseWriter {

    public String getFileName() {
        return fileName;
    }

    private String fileName;

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public FileWriter(String fileName) {
        this.fileName = Objects.requireNonNull(fileName);
    }

    @Override
    public void addLog(String message) {

    }
}
