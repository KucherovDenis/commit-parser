package version.oop;

import version.oop.classes.formatters.*;
import version.oop.classes.writers.*;
import version.oop.interfaces.Writer;

/**
 * data         - каталог с исходными данными. Данные в xlsx-файлах.
 * dataOne      - каталог с одной ошибкой. Данные в xlsx-файлах.
 * dataNull     - каталог с файлами без ошибок. Данные в xlsx-файлах.
 * dataOneTxt   - каталог с одной ошибкой. Данные в txt-файлах.
 * Результат сохраняется в файлы result.xls и result.txt
 */
public class App {

    private static final String CONSOLE_SEPARATOR = ";";
    private static final String TXT_SEPARATOR = "\t";

    /**
     * args[0] - путь к файлу со словарем dataOne\dictionary.xlsx
     * args[1] - директория обрабатываемых файлов dataOne\source
     * args[2] - путь к файлу с результатами без расширения (дописывается) dataOne\result
     */
    public static void main(String[] args) {
        if(args.length == 3) {
            AppImpl app = new AppImpl(args[0], args[1]);

            Writer consoleWriter = new ConsoleWriter();
            consoleWriter.addFormatter(new ConsoleFormatter(CONSOLE_SEPARATOR));
            app.addWriter(consoleWriter);

            Writer xlsWriter = new XlsFileWriter(args[2]);
            xlsWriter.addFormatter(new FileFormatter());
            app.addWriter(xlsWriter);

            Writer txtWriter = new TxtFileWriter(args[2]);
            txtWriter.addFormatter(new FileFormatter(TXT_SEPARATOR));
            app.addWriter(txtWriter);

            app.execute();
        }
    }
}
