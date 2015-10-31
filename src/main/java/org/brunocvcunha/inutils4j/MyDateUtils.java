/**
 * Copyright (C) 2014 Bruno Candido Volpato da Cunha (brunocvcunha@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.brunocvcunha.inutils4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

public class MyDateUtils {

  private static Logger log = Logger.getLogger(MyDateUtils.class);

  public static Long SECOND_MILLIS = 1000L;
  public static Long MINUTE_MILLIS = 60 * SECOND_MILLIS;
  public static Long HOUR_MILLIS = 60 * MINUTE_MILLIS;
  public static Long DAY_MILLIS = 24 * HOUR_MILLIS;
  public static Long YEAR_MILLIS = 365 * DAY_MILLIS;
  public static Long WEEK_MILLIS = 7 * DAY_MILLIS;
  public static Long MONTH_MILLIS = 30 * DAY_MILLIS;

  public static Date dataStringToDate(String data) {
    Date date = null;
    String dia = data.substring(0, 2);
    String mes = data.substring(3, 5);
    String ano = data.substring(6, 10);
    GregorianCalendar calendar =
        new GregorianCalendar(Integer.parseInt(ano), Integer.parseInt(mes) - 1,
            Integer.parseInt(dia));
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

  public static Date calculateAgo(Date date, String agoString) {
    log.debug("Calculating ago for " + agoString);
    if (agoString.startsWith("<")) {
      agoString = agoString.substring(1).trim();
    }

    if (agoString.equalsIgnoreCase("today")) {
      return date;
    }

    if (agoString.equalsIgnoreCase("yesterday")) {
      return new Date(date.getTime() - DAY_MILLIS);
    }

    String[] parse = agoString.split("\\s+");
    int quantity = Integer.valueOf(parse[0]);

    if (parse[1].equalsIgnoreCase("second") || parse[1].equalsIgnoreCase("seconds")) {
      return new Date(date.getTime() - (quantity * SECOND_MILLIS));
    }

    if (parse[1].equalsIgnoreCase("minute") || parse[1].equalsIgnoreCase("minutes")) {
      return new Date(date.getTime() - (quantity * MINUTE_MILLIS));
    }

    if (parse[1].equalsIgnoreCase("hour") || parse[1].equalsIgnoreCase("hours")) {
      return new Date(date.getTime() - (quantity * HOUR_MILLIS));
    }

    if (parse[1].equalsIgnoreCase("day") || parse[1].equalsIgnoreCase("days")) {
      return new Date(date.getTime() - (quantity * DAY_MILLIS));
    }

    if (parse[1].equalsIgnoreCase("week") || parse[1].equalsIgnoreCase("weeks")) {
      return new Date(date.getTime() - (quantity * WEEK_MILLIS));
    }

    if (parse[1].equalsIgnoreCase("month") || parse[1].equalsIgnoreCase("months")) {
      return new Date(date.getTime() - (quantity * MONTH_MILLIS));
    }

    if (parse[1].equalsIgnoreCase("year") || parse[1].equalsIgnoreCase("years")) {
      return new Date(date.getTime() - (quantity * YEAR_MILLIS));
    }

    throw new IllegalArgumentException("Invalid ago term: " + agoString);
  }

  public static String getTimestamp() {
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    return "[".concat(sdf.format(new Date())).concat("]");
  }

  public static Date mergeDateAndTime(Date date, String time) throws ParseException {
    SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat sdfDateTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    String formattedDateTime = sdfDate.format(date) + " " + time;
    return sdfDateTime.parse(formattedDateTime);
  }
}
