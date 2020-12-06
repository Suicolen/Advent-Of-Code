package suic;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.Value;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataManager {
    public static final String USER_AGENT = "SizableShrimp-AOC-Data-Bot/2.0.2.0 (+http://github.com/SizableShrimp/AdventOfCode2020)";
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static String sessionCookie;

    @SneakyThrows
    private static void load() {
        if (sessionCookie != null)
            return;

        Path path = Path.of("session.txt");

        if (!Files.exists(path))
            throw new IllegalArgumentException("No AOC session cookie found! Please create session.txt");

        sessionCookie = Files.readString(path)
                .trim();
    }

    /**
     * The <code>read</code> method is used to locate input data for a specified {@link Puzzle}.
     * <br />
     * This function checks for the existence of a {@link Puzzle} input file first in the <code>days</code> directory included in the jar.
     * If a file is not found, it then checks if the file exists in the <code>aoc_input</code> directory inside of the working directory.
     * For example, class <code>Day01</code> would have a corresponding input text file in "days/day01.txt". If an input text file is found,
     * the data from that file is returned in an unmodifiable list.
     * <br />
     * If no input text file is found, this method then <u>tries to connect to the Advent Of Code servers for input
     * data</u>. If <code>session.txt</code> does not exist, this method will throw an {@link IllegalArgumentException}.
     * This session file should hold your session cookie for the <a href="http://adventofcode.com">Advent Of Code Website</a>.
     * This cookie can be found using browser inspection.
     * <br />
     * If a successful connection is made to the AOC servers, the input data is stored in a file that is located in the
     * working directory in the <code>aoc_input</code> directory in case of later usage. The data fetched from the server
     * originally is then returned in an unmodifiable list.
     *
     * @param day The integer day of which to read input data.
     * @return An unmodifiable {@link List} of strings representing each line of input data.
     * @throws IllegalArgumentException If an existing {@link Puzzle} input file cannot be found and <code>sessions.txt</code> does not exist.
     */
    public static List<String> read(int day) {
        Path path = getPathInput(day);
        if (path == null)
            return List.of();
        List<String> lines = getDataFromFile(path);

        if (!lines.isEmpty())
            return lines;

        return getDataFromServer(day, AdventCalender.YEAR, path);
    }


    /*public static Result upload(Day.Result result, int day) {
        return upload(result, day, AdventCalender.YEAR);
    }*/

    /*public static Result upload(Day.Result result, int day, int year) {
        load();
        if (Files.exists(getPathCompleted(day, Part.SECOND)))
            return new Result("Already completed", true, true);

        String part1 = result.getPart1();
        String part2 = result.getPart2();

        if (part1 == null)
            return new Result("", false, true);

        boolean part1Completed = Files.exists(getPathCompleted(day, Part.FIRST));
        Part part = part1Completed ? Part.SECOND : Part.FIRST;

        String selected = switch (part) {
            case FIRST -> part1;
            // In the heat of the moment, I might overwrite part 2 code in part 1 still!
            case SECOND -> part2 == null ? part1 : part2;
            case AUTO -> throw new IllegalArgumentException("Part cannot be AUTO here");
        };

        HttpResponse<String> response = uploadDataToServer(day, year, part, selected);
        System.out.println(response.body());
        System.out.println(response.headers());
        return null;
    }*/

    /**
     * Reads all input data for a given year from the server using the provided AOC session cookie
     * and saves it to running directory subfolder "aoc_input". See {@link #read} for more detail.
     *
     * @param year The Advent Of Code year to read input data for each day.
     */
    public static void writeAllDaysToFile(int year) {
        for (int i = 1; i <= 25; i++) {
            String filename = "day" + AdventCalender.pad(i) + ".txt";
            Path path = getBasePathInput(filename);
            getDataFromServer(i, year, path);
        }
    }

    /*private static HttpResponse<String> uploadDataToServer(int day, int year, Part part, String selected) {
        if (selected.contains("\n"))
            throw new IllegalArgumentException("WARNING: Selected string has \\n in it! See:\n" + selected);
        try {
            String requestBody = OBJECT_MAPPER
                    .writeValueAsString(Map.of("level", String.valueOf(part.num), "answer", selected));

            URI uri = new URI("https://adventofcode.com/" + year + "/day/" + day + "/answer");
            HttpRequest request = HttpRequest.newBuilder(uri)
                    .header("User-Agent", USER_AGENT)
                    .header("Content-Type", "application/json")
                    .header("Cookie", "session=" + sessionCookie)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();
            return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }*/

    private static List<String> getDataFromServer(int day, int year, Path path) {
        load();
        List<String> lines = new ArrayList<>();

        try {
            URI uri = new URI("https://adventofcode.com/" + year + "/day/" + day + "/input");

            HttpRequest request = HttpRequest.newBuilder(uri)
                    .header("User-Agent",
                            USER_AGENT)
                    .header("Cookie", "session=" + sessionCookie)
                    .build();
            HttpResponse<Stream<String>> response = HttpClient.newHttpClient()
                    .send(request,
                            HttpResponse.BodyHandlers.ofLines());

            lines = response.body()
                    .collect(Collectors.toList());
            if (path != null)
                writeFile(path, lines);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return List.copyOf(lines);
    }

    private static List<String> getDataFromFile(Path path) {
        try {
            if (path != null && Files.exists(path)) {
                return List.copyOf(Files.readAllLines(path));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return List.of();
    }

    private static Path getPathInput(int day) {
        String filename = "day" + AdventCalender.pad(day) + ".txt";

        URL url = AdventCalender.class.getResource("/days/" + filename);
        if (url == null) {
            return getBasePathInput(filename);
        }

        try {
            return Path.of(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*private static Path getPathGuesses(int day, Part part) {
        if (part == Part.AUTO)
            throw new IllegalArgumentException("Part cannot be AUTO here");

        return getBasePathOutput(day, part, "guesses");
    }*/

    /*private static Path getPathCompleted(int day, Part part) {
        if (part == Part.AUTO)
            throw new IllegalArgumentException("Part cannot be AUTO here");

        return getBasePathOutput(day, part, "completed");
    }*/

    public static void writeFile(Path path, List<String> lines) {
        Path parent = path.getParent();
        try {
            if (!Files.exists(parent))
                Files.createDirectory(parent);
            //remove empty last line of input files although this doesn't really matter? hmm
            Files.writeString(path, String.join(System.lineSeparator(), lines));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Path getBasePathInput(String filename) {
        return Path.of("aoc_input", filename);
    }

    /*public static Path getBasePathOutput(int day, Part part, String extension) {
        return Path.of("aoc_output", String.format("day%spart%s_%s.txt", AdventCalender.pad(day), part.num, extension));
    }*/

    @Value
    class Result {
        String message;
        boolean correct;
        boolean invalid;
    }

    @Value
    class Guess {
        String guess;
        String response;
    }

    @AllArgsConstructor
    enum Part {
        FIRST(1),
        SECOND(2),
        /**
         * Sends the second part if both parts are non-null, otherwise the first part
         */
        AUTO(-1);

        private final int num;
    }
}