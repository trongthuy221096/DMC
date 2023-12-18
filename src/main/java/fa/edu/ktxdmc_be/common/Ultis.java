package fa.edu.ktxdmc_be.common;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Ultis {
    public static LocalDate stringToLocalDate(String dateStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(dateStr, formatter);
        } catch (Exception e) {
            return null;
        }
    }
}
