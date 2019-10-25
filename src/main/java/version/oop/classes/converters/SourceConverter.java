package version.oop.classes.converters;

import version.oop.interfaces.Data;
import version.oop.interfaces.Converter;

import java.util.List;

public class SourceConverter implements Converter {

    private static final int JIRA_CELL = 2;
    private static final int BB_CELL = 0;
    private static final int NAME_CELL = 0;

    private static final int BB_POS = 5;
    private static final int NAME_POS = 7;

    private static final String SEPARATOR = "/";

    private String getJiraKey(String contextCell) {
        int index = contextCell.indexOf('-');
        return contextCell.substring(0, index);
    }

    private String parseUrl(String url, int pos) {
        String[] split = url.split(SEPARATOR);
        return split[pos];
    }

    private String getBbKey(String contextCell) {
        return parseUrl(contextCell, BB_POS);
    }

    private String getNameKey(String contextCell) {
        return parseUrl(contextCell, NAME_POS);
    }

    @Override
    public Data getAsData(List<String> context) {
        Data userData = new Data();
        userData.setJiraKey(getJiraKey(context.get(JIRA_CELL)));
        userData.setBbKey(getBbKey(context.get(BB_CELL)));
        userData.setNameKey(getNameKey(context.get(NAME_CELL)));
        userData.getOthers().addAll(context);
        return userData;
    }
}
