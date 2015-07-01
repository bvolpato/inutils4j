package org.brunocvcunha.inutils4j;

import java.util.List;
import java.util.regex.Pattern;

public class MyTextRegex {

    public static List<String> getEmails(String body) {
        Pattern pattern =
                Pattern.compile("(([A-Za-z0-9]+_+)|([A-Za-z0-9]+\\-+)|([A-Za-z0-9]+\\.+)|([A-Za-z0-9]+\\++))*[A-Za-z0-9]+@((\\w+\\-+)|(\\w+\\.))*\\w{1,63}\\.[a-zA-Z]{2,6}");
        return MyStringUtils.regexTest(pattern, body);
    }

    public static List<String> getUrls(String body) {
        Pattern pattern =
                Pattern.compile("^(?:(?:ht|f)tp(?:s?)\\:\\/\\/|~/|/)?(?:\\w+:\\w+@)?(?:(?:[-\\w]+\\.)+(?:com|org|net|gov|mil|biz|info|mobi|name|aero|jobs|museum|travel|[a-z]{2}))(?::[\\d]{1,5})?(?:(?:(?:/(?:[-\\w~!$+|.,=]|%[a-f\\d]{2})+)+|/)+|\\?|#)?(?:(?:\\?(?:[-\\w~!$+|.,*:]|%[a-f\\d{2}])+=(?:[-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)(?:&(?:[-\\w~!$+|.,*:]|%[a-f\\d{2}])+=(?:[-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)*)*(?:#(?:[-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)?$");
        return MyStringUtils.regexTest(pattern, body);
    }

    public static String fixStringEmails(String str) {
        str = str.replaceAll("\\s?(([(\\]|<]at[)|\\]>]))\\s?", "@");
        str = str.replace(" at ", "@");

        return str;
    }
}