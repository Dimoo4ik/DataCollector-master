import java.time.LocalDate;

public record Stop_Csv(String name, LocalDate date, String depth) {
    @Override
    public String toString() {
        return "Stop_Csv\n{\n" +
                "\tname='" + name + '\'' + ",\n" +
                "\tdate=" + date +
                '}';
    }
}
