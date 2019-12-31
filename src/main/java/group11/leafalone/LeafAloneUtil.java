package group11.leafalone;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LeafAloneUtil {
    public static Date stripHoursAndMinutes(Date date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "dd/MM/yyyy");

        return formatter.parse(formatter.format(date));
    }
}
