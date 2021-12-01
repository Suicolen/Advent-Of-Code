package suic.util;

import suic.AdventCalender;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public final class FileUtils {

    public static List<String> readResource(String path) {
        try {
            return Files.readAllLines(Path.of(FileUtils.class.getResource("/" + AdventCalender.YEAR + "/" + path).toURI()));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
