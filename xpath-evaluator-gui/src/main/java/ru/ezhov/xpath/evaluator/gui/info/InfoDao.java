package ru.ezhov.xpath.evaluator.gui.info;

import java.io.File;
import java.nio.file.Files;

public class InfoDao {
    private static final String NAME_FILE = "xpath-info.txt";

    public String getInfo() throws InfoException {
        try {
            return new String(Files.readAllBytes(new File(NAME_FILE).toPath()), "UTF-8");
        } catch (Exception e) {
            throw new InfoException(e);
        }
    }
}
