package version.oop.classes.converters;

import version.oop.interfaces.Data;
import version.oop.interfaces.Converter;
import java.util.List;

public class DictionaryConverter implements Converter {

    private static final int JIRA_CELL = 0;
    private static final int BB_CELL = 1;
    private static final int NAME_CELL = 2;

    @Override
    public Data getAsData(List<String> context) {
        Data dictionaryData = new Data();
        dictionaryData.setJiraKey(context.get(JIRA_CELL));
        dictionaryData.setBbKey((context.get(BB_CELL)));
        dictionaryData.setNameKey(context.get(NAME_CELL));
        dictionaryData.getOthers().addAll(context);
        return dictionaryData;
    }
}
