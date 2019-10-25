package version.oop.interfaces;

import java.util.Objects;

public abstract class BaseFormatter implements Formatter {

    private String separator;

    public BaseFormatter(String separator) {
        this.separator = separator;
    }

    public BaseFormatter() {
        this.separator = "";
    }

    @Override
    public String getSeparator() {
        return separator;
    }

    @Override
    public void setSeparator(String separator) {
        this.separator = Objects.requireNonNull(separator);
    }
}
