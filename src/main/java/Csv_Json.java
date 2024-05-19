import java.time.LocalDate;

public class Csv_Json {
    private String station_name;
    private String depth;
    private String name;
    private LocalDate date;


    public Csv_Json(String station_name, LocalDate date, String depth, String name) {
        this.station_name = station_name;
        this.depth = depth;
        this.name = name;
        this.date = date;
    }

    public String getStation_name() {
        return station_name;
    }

    public void setStation_name(String station_name) {
        this.station_name = station_name;
    }

    public String getDepth() {
        return depth;
    }

    public void setDepth(String depth) {
        this.depth = depth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Csv_Json{" +
                "station_name='" + station_name + '\'' +
                ", depth='" + depth + '\'' +
                ", name='" + name + '\'' +
                ", date=" + date +
                '}';
    }
}
