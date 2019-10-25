package version.oop.interfaces;

import java.util.ArrayList;
import java.util.List;

public class Data {

    public String getJiraKey() {
        return jiraKey;
    }

    public void setJiraKey(String jiraKey) {
        this.jiraKey = jiraKey;
    }

    public String getBbKey() {
        return bbKey;
    }

    public void setBbKey(String bbKey) {
        this.bbKey = bbKey;
    }

    public String getNameKey() {
        return nameKey;
    }

    public void setNameKey(String nameKey) {
        this.nameKey = nameKey;
    }

    private String jiraKey;

    private String bbKey;

    private String nameKey;

    public List<String> getOthers() {
        return others;
    }

    private List<String> others;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Data data = (Data) o;
        return getJiraKey().equals(data.getJiraKey()) &&
                getBbKey().equals(data.getBbKey()) &&
                getNameKey().equals(data.getNameKey());
    }

    @Override
    public int hashCode() {
        return getJiraKey().hashCode() + getBbKey().hashCode() + getNameKey().hashCode();
    }

    public Data() {
        others = new ArrayList<>();
    }

    public Data(String jiraKey, String bbKey, String nameKey) {
        this();
        this.jiraKey = jiraKey;
        this.bbKey = bbKey;
        this.nameKey = nameKey;
    }
}
