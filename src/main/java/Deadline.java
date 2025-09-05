import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {

    protected LocalDate by;

    public Deadline(String description, String byStr) {
        super(description);
        this.by = parseDateFlexible(byStr);
    }

    @Override
    public String getType() {
        return "D";
    }

    public String getBy() {
        return by.format(DateTimeFormatter.ofPattern("MMM d uuuu"));
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + getBy() + ")";
    }

    private static LocalDate parseDateFlexible(String s) {
        s = s.trim();

        try { return LocalDate.parse(s, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException ignored) {}

        try { return LocalDate.parse(s, DateTimeFormatter.ofPattern("d/M/uuuu"));
        } catch (DateTimeParseException ignored) {}

        try {
            LocalDateTime dt = LocalDateTime.parse(s, DateTimeFormatter.ofPattern("d/M/uuuu HHmm"));
            return dt.toLocalDate();
        } catch (DateTimeParseException ignored) {}

        throw new IllegalArgumentException("Unrecognized date: " + s);
    }
}
