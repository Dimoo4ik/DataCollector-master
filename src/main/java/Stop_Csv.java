public record Stop_Csv(String name, String date) {
    @Override
    public String toString() {
        return "Stop_Csv\n{\n" +
                "\tname='" + name + '\'' + ",\n" +
                "\tdate=" + date +
                '}';
    }
}
