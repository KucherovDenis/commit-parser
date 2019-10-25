package version.oop.interfaces;

import java.util.Objects;

public abstract class BaseWriter implements Writer {

    private Formatter formatter = null;

    @Override
    public void addFormatter(Formatter formatter) {
        this.formatter = Objects.requireNonNull(formatter);
    }

    protected Formatter getFormatter(Formatter formatter) {
        if(formatter == null)
            return this.formatter;
        else
            return formatter;
    }
}
