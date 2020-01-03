package group11.leafalone;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class LeafAloneUtil {
    public static Date stripHoursAndMinutes(Date date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "dd/MM/yyyy");

        return formatter.parse(formatter.format(date));
    }

    public static Date getTodayAtMidnight() throws ParseException {
        return stripHoursAndMinutes(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
    }

    public static Date getTodayRightNow() {
        return Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
    }

}
