package org.brunocunha.inutils4j;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MyDateUtils {

	public static Long SECOND_MILLIS = 1000L;
	public static Long MINUTE_MILLIS = 60 * SECOND_MILLIS;
	public static Long HOUR_MILLIS = 60 * MINUTE_MILLIS;
	public static Long DAY_MILLIS = 24 * HOUR_MILLIS;
	
	public static Date dataStringToDate(String data) {
        Date date = null;
        String dia = data.substring(0, 2);
        String mes = data.substring(3, 5);
        String ano = data.substring(6, 10);
        GregorianCalendar calendar = new GregorianCalendar(Integer.parseInt(ano), Integer.parseInt(mes) - 1, Integer.parseInt(dia));
        date = new Date(calendar.getTimeInMillis());
        return date;
    }

    public static String dataDateToString(Date date) {
        String data = "";
        String dateString = date.toString();
        String ano = dateString.substring(0, 4);
        String mes = dateString.substring(5, 7);
        String dia = dateString.substring(8, 10);
        data = dia + "/" + mes + "/" + ano;
        return data;
    }

    public static void zeroTime(Calendar cal) {
        cal.set(10, 0);
        cal.set(11, 0);
        cal.set(12, 0);
        cal.set(13, 0);
        cal.set(14, 0);
    }

    public static long getTimeBetween(long start, long end) {
        return Math.abs(start - end);
    }

    public static int getDaysBetween(Calendar d1, Calendar d2) {
        if (d1.after(d2)) {
            Calendar swap = d1;
            d1 = d2;
            d2 = swap;
        }

        int days = d2.get(6) - d1.get(6);
        int y2 = d2.get(1);
        if (d1.get(1) != y2) {
            d1 = (Calendar) d1.clone();
            do {
                days += d1.getActualMaximum(6);
                d1.add(1, 1);
            } while (d1.get(1) != y2);
        }
        return days;
    }

    public static int getMonthsBetween(Calendar start, Calendar end) {
        if (end.before(start)) {
            Calendar swap = start;
            start = end;
            end = swap;
        }

        end = (Calendar) end.clone();

        int months = -1;
        do {
            end.add(2, -1);
            ++months;
        } while (!(start.after(end)));

        return months;
    }

    public static String disposeSeconds(int seconds) {
        String ret = "";

        if (seconds >= 3600) {
            ret += (seconds / 3600) + "h";
            seconds = seconds % 3600;
        }

        if (seconds >= 60) {
            ret += (seconds / 60) + "m";
            seconds = seconds % 60;
        }

        if (seconds > 0) {
            ret += (seconds) + "s";
        }

        return ret;
    }
    
    public static String getTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return "[".concat(sdf.format(new Date())).concat("]");
    }
    
}