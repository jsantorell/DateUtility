package Dates;

import java.text.*;
import java.time.*;
import java.util.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author jerem
 */
public class DateUtilities8 {

    private static DateUtilities8 instance;

    public enum TimeUnit {

        DAY(3600000 * 24),
        HOUR(3600000),
        MINUTE(3600000 / 60),
        SECOND(3600000 / 60 / 60);

        TimeUnit(long ms) {
            milliseconds = ms;
        }

        private final long milliseconds;

        public long getMilliseconds() {
            return milliseconds;
        }
    }

    public int differnceBetween2Dates(utility.DateUtilities.DateUnit dateUnit, Calendar firstDate, Calendar secondDate)
            throws IllegalArgumentException {

        // Convert Calendars to LocalDateTime objects
        LocalDateTime startDate = LocalDateTime.ofInstant(firstDate.toInstant(), ZoneId.systemDefault());
        LocalDateTime endDate = LocalDateTime.ofInstant(secondDate.toInstant(), ZoneId.systemDefault());
        Duration diff = Duration.between(startDate, endDate);
        int value;

        switch (dateUnit) {
            case DAY:
                value = (int) diff.toDays();
                break;

            case HOUR:
                value = (int) diff.toHours();
                break;

            case MINUTE:
                value = (int) diff.toMinutes();
                break;

            case SECOND:
                value = (int) (diff.toMillis() / 1000L);
                break;

            default:
                value = (int) diff.toHours();
        }

        return value;
    }

    public Date turnStringIntoDateObject(String dateString) throws IllegalArgumentException {
        Date date = null;
        DateFormat df;

        /*
        Attempt to get all possibilities
        
                        Input string                            Pattern
                ------------------------------------    ----------------------------
                2001.07.04 AD at 12:08:56 PDT           yyyy.MM.dd G 'at' HH:mm:ss z
                Wed, Jul 4, '01                         EEE, MMM d, ''yy
                12:08 PM                                h:mm a
                12 o'clock PM, Pacific Daylight Time    hh 'o''clock' a, zzzz
                0:08 PM, PDT                            K:mm a, z
                02001.July.04 AD 12:08 PM               yyyyy.MMMM.dd GGG hh:mm aaa
                Wed, 4 Jul 2001 12:08:56 -0700          EEE, d MMM yyyy HH:mm:ss Z
                010704120856-0700                       yyMMddHHmmssZ
                2001-07-04T12:08:56.235-0700            yyyy-MM-dd'T'HH:mm:ss.SSSZ
                2001-07-04T12:08:56.235-07:00           yyyy-MM-dd'T'HH:mm:ss.SSSXXX
                2001-W27-3                              YYYY-'W'ww-u
         */
        try {//example July 1, 2017
            df = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
            date = df.parse(dateString);
        } catch (ParseException e) {
            try {//example 2001.07.04 AD at 12:08:56 CDT
                df = new SimpleDateFormat("MMddyyyy");
                date = df.parse(dateString);
            } catch (ParseException e2) {
                try {
                    df = new SimpleDateFormat("yyyyMMdd");//How do you make this possible?
                    date = df.parse(dateString);
                } catch (ParseException e3) {
                    try {
                        df = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
                        date = df.parse(dateString);
                    } catch (ParseException e4) {
                        throw new IllegalArgumentException("The String inputted is not compatible with this method");
                    }
                }
            }
        }
        return date;
    }

    public String toStringMMddyyyy(Date date) throws IllegalArgumentException {
        if (date == null) {
            throw new IllegalArgumentException("Error: date argument cannot be null");
        }
        DateFormat df = new SimpleDateFormat("MMddyyyy");
        return df.format(date);
    }

    public String toString(Calendar date) throws IllegalArgumentException {
        DateFormat df = DateFormat.getDateInstance();
        return df.format(date.getTime());
    }

}
