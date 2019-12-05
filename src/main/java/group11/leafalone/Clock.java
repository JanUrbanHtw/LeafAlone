package group11.leafalone;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Clock {
    String date;
    String time;

    public Clock() {
        this.date = "Date: " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        this.time = "Time: " + LocalDateTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM));
    }

    public String getDate() {
        return this.date;
    }

    public String getTime() {
        return time;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
