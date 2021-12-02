package suic.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import suic.AdventCalender;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@UtilityClass
public class FileUtils {

    @SneakyThrows(IOException.class)
    public static List<String> readResource(String path) {
        return Files.readAllLines(getPath(path));
    }

    @SneakyThrows(IOException.class)
    public static List<Integer> readResourceAsInt(String path) {
        return Files.lines(getPath(path)).mapToInt(Integer::parseInt).boxed().toList();
    }

    @SneakyThrows(IOException.class)
    public static List<Long> readResourceAsLong(String path) {
        return Files.lines(getPath(path)).mapToLong(Long::parseLong).boxed().toList();
    }

    @SneakyThrows(URISyntaxException.class)
    private static Path getPath(String path) {
        return Path.of(FileUtils.class.getResource("/" + AdventCalender.YEAR + "/" + path).toURI());
    }
}
