import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ParseWebPage {
    private List<String> jsonFilePaths = new ArrayList<>();
    private List<String> csvFilePaths = new ArrayList<>();
    private Document document;
    private Elements elements;

    //Method get html-code web-page
    public String getHtmlCodeWebPage() throws IOException {
        String url = "https://skillbox-java.github.io/";
        document = Jsoup.connect(url).get();
        String htmlCode = String.valueOf(document);
        FileWriter fileWriter = new FileWriter("data/codeHtmlMetro.html");
        fileWriter.write(htmlCode);
        return htmlCode;
    }

    //Method get names and number line station
    public void getNamesAndLineStation() {


        ObjectMapper objectMapper = new ObjectMapper();
        AllLinesAndStationsMetro allLinesAndStationsMetro = new AllLinesAndStationsMetro();

        elements = document.select(".js-metro-stations");
        elements.forEach(elem -> {
            String nameStation = elem.select(".name").text();
            String numberLine = elem.attr("data-line");

            LineAndHerNamesMetro lineAndHerNamesMetro = new LineAndHerNamesMetro();
            lineAndHerNamesMetro.setNames(nameStation);
            lineAndHerNamesMetro.setNumberLineMetro(numberLine);
            allLinesAndStationsMetro.setStations(lineAndHerNamesMetro);

            System.out.println("Number line \"" + numberLine + "\"\nName station \"" + nameStation + "\"");
        });

        try {
            String jsonStation = objectMapper.writeValueAsString(allLinesAndStationsMetro);
            FileWriter fileWriter = new FileWriter("data/stations.json");
            fileWriter.write(jsonStation);
            fileWriter.close();
        } catch (Exception ex) {
            ex.getMessage();
        }
    }


    public void fromJsonAsJavaStations() throws IOException {
        String json = Files.readString(Paths.get("data/stations.json"));
        ObjectMapper objectMapper = new ObjectMapper();
        AllLinesAndStationsMetro allLinesAndStationsMetro = objectMapper.readValue(json, AllLinesAndStationsMetro.class);
        System.out.println(allLinesAndStationsMetro.toString());
    }

    public void getFilesJsonAndCSV(File file) throws IOException {
        for (File currentFile : file.listFiles()) {
            if (currentFile.isDirectory()) {
                getFilesJsonAndCSV(currentFile);
            } else if (currentFile.getName().endsWith(".json")) {
                jsonFilePaths.add(currentFile.getPath());

                String json = Files.readString(currentFile.toPath());
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());
                List<Json> jsonObjects = objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, Json.class));
                jsonObjects.forEach(System.out::println);

            } else if (currentFile.getName().endsWith(".csv")) {
                csvFilePaths.add(currentFile.getPath());

                List<String> lines = Files.readAllLines(currentFile.toPath());

                for (int i = 1; i < lines.size(); i++) {
                    String line = lines.get(i);
                    String[] parts = line.split(",");

                    String nameStation = parts[0];
                    String numberLine = parts[1];

                    Stop_Csv stopCsv = new Stop_Csv(nameStation, numberLine);

                    System.out.println(stopCsv);

                }
            }
        }
    }

    /*public void jsonToJava(File file) throws IOException {
        String json = Files.readString(file.toPath());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        List<Json> jsonObjects = objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, Json.class));
        jsonObjects.forEach(System.out::println);

    }*/

    /*public void csvToJava(File file) throws IOException {

        List<String> lines = Files.readAllLines(file.toPath());

        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] parts = line.split(",");

            String nameStation = parts[0];
            String numberLine = parts[1];

            Stop_Csv stopCsv = new Stop_Csv(nameStation, numberLine);

            System.out.println(stopCsv);

        }
    }*/

    public void get() throws IOException {

        String pathJson = "";

        for (String l : jsonFilePaths) {
            pathJson = l;
        }
        String pathCsv = "";
        for (String l : csvFilePaths) {
            pathCsv = l;
        }

        String json = Files.readString(Paths.get(pathJson));
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        List<Json> jsonObjects = objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, Json.class));


        int size = 0;
        for (Json j : jsonObjects) {
            size++;
            System.out.println(size);
        }
        List<String> lines = Files.readAllLines(Paths.get(pathCsv));

        for (int i = 1; i < size; i++) {

            String line = lines.get(i);
            String[] parts = line.split(",");

            String nameStation = parts[0];
            DateTimeFormatter ff = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate date = LocalDate.parse(parts[1], ff);

            String depth = jsonObjects.get(i).getDepth();
            String name = jsonObjects.get(i).getStation_name();

            Csv_Json stopCsv = new Csv_Json(nameStation, date, depth, name);
            System.out.println(stopCsv);
            System.out.println(" \n");

        }
    }

 /*   public List<String> getJsonFilePaths() {
        return jsonFilePaths;
    }

    public List<String> getCsvFilePaths() {
        return csvFilePaths;
    }*/
}
