package is.stokkur.dateutils;

import android.content.Context;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Class description
 */

public class DateUtils {

    private static final String TAG = DateUtils.class.toString();

    public static final String PATTERN_hhmm = "hh:MM a";
    public static final String PATTERN_HHmm = "HH:mm";

    public static final String PATTERN_DOT_ddMM = "dd.MM.";
    public static final String PATTERN_DOT_ddMMyyyy = "dd.MM.yyyy";
    public static final String PATTERN_DOT_MMdd = "MM.dd.";
    public static final String PATTERN_DOT_MMddyyyy = "MM.dd.yyyy";

    public static final String PATTERN_dd_MMM = "dd MMM";
    public static final String PATTERN_dd_MMM_yyyy = "dd MMM yyyy";
    public static final String PATTERN_EEE_dd_MMM = "EEE, dd MMM";
    public static final String PATTERN_EEE_dd_MMM_yyyy = "EEE, dd MMM yyyy";

    public static final String PATTERN_EEEE_dd_MMM = "EEEE, dd MMM";
    public static final String PATTERN_EEEE_dd_MMM_yyyy = "EEEE, dd MMM yyyy";

    public static final String PATTERN_dd_MMMM = "dd MMMM";
    public static final String PATTERN_dd_MMMM_yyyy = "dd MMMM yyyy";
    public static final String PATTERN_EEE_dd_MMMM = "EEE, dd MMMM";
    public static final String PATTERN_EEE_dd_MMMM_yyyy = "EEE, dd MMMM yyyy";

    public static final String PATTERN_EEEE_dd_MMMM = "EEEE, dd MMMM";
    public static final String PATTERN_EEEE_dd_MMMM_yyyy = "EEEE, dd MMMM yyyy";

    public static final String PATTERN_DOT_hhmm_ddMM = "hh:mm a dd.MM.";
    public static final String PATTERN_DOT_hhmm_ddMMyyyy = "hh:mm a dd.MM.yyyy";
    public static final String PATTERN_DOT_hhmm_MMdd = "hh:mm a MM.dd.";
    public static final String PATTERN_DOT_hhmm_MMddyyyy = "hh:mm a MM.dd.yyyy";
    public static final String PATTERN_DOT_HHmm_ddMM = "HH:mm dd.MM.";
    public static final String PATTERN_DOT_HHmm_ddMMyyyy = "HH:mm dd.MM.yyyy";
    public static final String PATTERN_DOT_HHmm_MMdd = "HH:mm MM.dd.";
    public static final String PATTERN_DOT_HHmm_MMddyyyy = "HH:mm MM.dd.yyyy";

    public static final String PATTERN_SLASH_ddMM = "dd/MM";
    public static final String PATTERN_SLASH_ddMMyyyy = "dd/MM/yyyy";
    public static final String PATTERN_SLASH_MMdd = "MM/dd";
    public static final String PATTERN_SLASH_MMddyyyy = "MM/dd/yyyy";

    public static final String PATTERN_SLASH_hhmm_ddMM = "hh:mm a dd/MM";
    public static final String PATTERN_SLASH_hhmm_ddMMyyyy = "hh:mm a dd/MM/yyyy";
    public static final String PATTERN_SLASH_hhmm_MMdd = "hh:mm a MM/dd";
    public static final String PATTERN_SLASH_hhmm_MMddyyyy = "hh:mm a MM/dd/yyyy";
    public static final String PATTERN_SLASH_HHmm_ddMM = "HH:mm dd/MM";
    public static final String PATTERN_SLASH_HHmm_ddMMyyyy = "HH:mm dd/MM/yyyy";
    public static final String PATTERN_SLASH_HHmm_MMdd = "HH:mm MM/dd";
    public static final String PATTERN_SLASH_HHmm_MMddyyyy = "HH:mm MM/dd/yyyy";

    private static final String YEAR = "yyyy"; // 1997
    private static final String YEAR_MONTH = "yyyy-MM"; // 1997-07
    private static final String DATE = "yyyy-MM-dd"; // 1997-07-16
    private static final String DATE_TIME = "yyyy-MM-dd'T'hh:mm:ss"; // 1997-07-16T19:20+01:00
    private static final String DATE_TIME_SEC = "yyyy-MM-dd'T'HH:mm:ssZ"; // 1997-07-16T19:20:30+01:00
    private static final String DATE_TIME_MS = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"; // 1997-07-16T19:20:30.45+01:00

    private static final long MINUTE_IN_MS = 60000;
    private static final long HOUR_IN_MS = 3600000;
    private static final long DAY_IN_MS = 86400000;
    private static final long WEEK_IN_MS = 604800000;

    private static final long MINUTE_IN_SEC = 60;
    private static final long HOUR_IN_SEC = 3600;
    private static final long DAY_IN_SEC = 86400;
    private static final long WEEK_IN_SEC = 604800;

    private DateUtils() {
    }

    /**
     * Formats given timestamp to date as string without the time.
     *
     * @param timestamp  date
     * @param year       true if year should be added to the result, false otherwise
     * @param dateFormat of the date
     * @return date as String in format defined by the dateFormat parameter
     */
    public static String formatTimestampDate(long timestamp, boolean year, int dateFormat) {
        Date date = new Date(timestamp);
        DateFormat format = DateFormat.getDateInstance(dateFormat, Locale.getDefault());
        String out = format.format(date);
        if (!year) {
            out = out.substring(0, out.length() - 5);
        }
        return out;
    }

    /**
     * Formats given timestamp to time as string ignoring date.
     *
     * @param timestamp  time
     * @param timeFormat format of the time
     * @return time as String in format HH:MM
     */
    public static String formatTimestampTime(long timestamp, int timeFormat) {
        Date date = new Date(timestamp);
        DateFormat format = DateFormat.getTimeInstance(timeFormat, Locale.getDefault());
        return format.format(date);
    }

    /**
     * Formats given timestamp to date and time with long date and short time. Format depends on
     * Locale settings.
     *
     * @param timestamp  date and time in ms
     * @param dateFormat format of the date
     * @param timeFormat format of the time
     * @return date and time in format dd. month yyyy HH:MM:SS Z
     */
    public static String formatTimestampDateAndTime(long timestamp, int dateFormat, int timeFormat) {
        Date date = new Date(timestamp);
        DateFormat format = DateFormat.getDateTimeInstance(dateFormat, timeFormat, Locale.getDefault());
        return format.format(date);
    }

    /**
     * Formats given calendar to given pattern.
     *
     * @param calendar calendar instance of the date
     * @param pattern  pattern how the result should look like
     * @return formatted date as String
     */
    public static String formatDateByPattern(Calendar calendar, String pattern) {
        return android.text.format.DateFormat.format(pattern, calendar).toString();
    }

    /**
     * Formats given calendar to given pattern.
     *
     * @param timestamp date as timestamp
     * @param pattern  pattern how the result should look like
     * @return formatted date as String
     */
    public static String formatDateByPattern(long timestamp, String pattern) {
        return android.text.format.DateFormat.format(pattern, timestamp).toString();
    }

    /**
     * Formats given calendar to given pattern.
     *
     * @param date date instance of the date
     * @param pattern  pattern how the result should look like
     * @return formatted date as String
     */
    public static String formatDateByPattern(Date date, String pattern) {
        return android.text.format.DateFormat.format(pattern, date).toString();
    }

    /**
     * Gets Date from string
     *
     * @param date date as string, it can be in 6 formats - yyyy | yyyy-MM | yyyy-MM-dd |
     *             yyyy-MM-dd'T'hh:mm:ss | yyyy-MM-dd'T'HH:mm:ssZ | yyyy-MM-dd'T'HH:mm:ss.SSSZ
     * @return date instance
     */
    public static Date getDateFromString(String date) {
        int length = date.length();
        SimpleDateFormat dateFormat;
        switch (length) {
            case 4:
                dateFormat = new SimpleDateFormat(YEAR, Locale.getDefault());
                break;
            case 7:
                dateFormat = new SimpleDateFormat(YEAR_MONTH, Locale.getDefault());
                break;
            case 10:
                dateFormat = new SimpleDateFormat(DATE, Locale.getDefault());
                break;
            case 19:
                dateFormat = new SimpleDateFormat(DATE_TIME, Locale.getDefault());
                break;
            case 25:
                dateFormat = new SimpleDateFormat(DATE_TIME_SEC, Locale.getDefault());
                break;
            default:
                dateFormat = new SimpleDateFormat(DATE_TIME_MS, Locale.getDefault());
        }
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gets Calendar from string
     *
     * @param date date as string, it can be in 6 formats - yyyy | yyyy-MM | yyyy-MM-dd |
     *             yyyy-MM-dd'T'hh:mm:ss | yyyy-MM-dd'T'HH:mm:ssZ | yyyy-MM-dd'T'HH:mm:ss.SSSZ
     * @return Calendar instance
     */
    public static Calendar getCalendarFromString(String date) {
        Date d = getDateFromString(date);
        if (d == null) {
            return null;
        }
        return getCalendarFromDate(d);
    }

    /**
     * Gets if the given date is same as actual
     *
     * @param date
     * @return true if it the same date, false otherwise
     */
    public static boolean isEqualToActualDateIgnoringTime(Date date) {
        Calendar calendar = getCalendarFromDate(date);
        return isEqualToActualDateIgnoringTime(calendar);
    }

    /**
     * Gets if the given date is same as actual
     *
     * @param calendar
     * @return true if it the same date, false otherwise
     */
    public static boolean isEqualToActualDateIgnoringTime(Calendar calendar) {
        return ((calendar.get(Calendar.YEAR) == getActual(Calendar.YEAR)) &&
                (calendar.get(Calendar.MONTH) == getActual(Calendar.MONTH)) &&
                (calendar.get(Calendar.DAY_OF_MONTH) == getActual(Calendar.DAY_OF_MONTH)));
    }

    /**
     * Gets if the given date is today
     *
     * @param date
     * @return true if it is today, false otherwise
     */
    public static boolean isToday(Date date) {
        return isEqualToActualDateIgnoringTime(date);
    }

    /**
     * Gets if the given date is today
     *
     * @param calendar
     * @return true if it is today, false otherwise
     */
    public static boolean isToday(Calendar calendar) {
        return isEqualToActualDateIgnoringTime(calendar);
    }

    /**
     * Gets if the given date is tomorrow
     *
     * @param date
     * @return true if it is tomorrow, false otherwise
     */
    public static boolean isTomorrow(Date date) {
        Date temp = new Date(date.getTime());
        temp.setTime(date.getTime() - DAY_IN_MS);
        return isEqualToActualDateIgnoringTime(temp);
    }

    /**
     * Gets if the given date is tomorrow
     *
     * @param calendar
     * @return true if it is tomorrow, false otherwise
     */
    public static boolean isTomorrow(Calendar calendar) {
        Calendar temp = Calendar.getInstance();
        temp.setTimeInMillis(calendar.getTimeInMillis());
        temp.add(Calendar.DAY_OF_MONTH, -1);
        return isEqualToActualDateIgnoringTime(temp);
    }

    /**
     * Gets if the given date is yesterday
     *
     * @param date
     * @return true if it is yesterday, false otherwise
     */
    public static boolean isYesterday(Date date) {
        Date temp = new Date(date.getTime());
        temp.setTime(date.getTime() + DAY_IN_MS);
        return isEqualToActualDateIgnoringTime(temp);
    }

    /**
     * Gets if the given date is yesterday
     *
     * @param calendar
     * @return true if it is yesterday, false otherwise
     */
    public static boolean isYesterday(Calendar calendar) {
        Calendar temp = Calendar.getInstance();
        temp.setTimeInMillis(calendar.getTimeInMillis());
        temp.add(Calendar.DAY_OF_MONTH, 1);
        return isEqualToActualDateIgnoringTime(temp);
    }

    /**
     * Gets if the given date is in the same week as actual date
     *
     * @param date
     * @return true if it is the same week, false otherwise
     */
    public static boolean isSameWeek(Date date) {
        return isSameWeek(getCalendarFromDate(date));
    }

    /**
     * Gets if the given date is in the same week as actual date
     *
     * @param calendar
     * @return true if it is the same week, false otherwise
     */
    public static boolean isSameWeek(Calendar calendar) {
        Calendar comp1 = Calendar.getInstance();
        if (comp1.get(Calendar.WEEK_OF_YEAR) != calendar.get(Calendar.WEEK_OF_YEAR)) {
            return false;
        }
        return Math.abs(comp1.getTimeInMillis() - calendar.getTimeInMillis()) < WEEK_IN_MS;
    }

    /**
     * Gets if the given parameter of the date is the same as actual
     *
     * @param date  given date
     * @param field given field to be compared, like Calendar.Month, etc
     * @return true if it is the same
     */
    public static boolean isSame(Date date, int field) {
        return isSame(getCalendarFromDate(date), field);
    }

    /**
     * Gets if the given parameter of the date is the same as actual
     *
     * @param calendar given date as calendar
     * @param field    given field to be compared, like Calendar.Month, etc
     * @return true if it is the same
     */
    public static boolean isSame(Calendar calendar, int field) {
        return calendar.get(field) == Calendar.getInstance().get(field);
    }

    /**
     * Gets if the given parameter of the date is the next to actual, like next week, next year, etc
     *
     * @param date  given date
     * @param field given field to be compared, like Calendar.Month, etc
     * @return true if it is the next, false otherwise
     */
    public static boolean isNext(Date date, int field) {
        return isNext(getCalendarFromDate(date), field);
    }

    /**
     * Gets if the given parameter of the date is the next to actual, like next week, next year, etc
     *
     * @param calendar given date as calendar
     * @param field    given field to be compared, like Calendar.Month, etc
     * @return true if it is the next, false otherwise
     */
    public static boolean isNext(Calendar calendar, int field) {
        Calendar temp = Calendar.getInstance();
        temp.setTimeInMillis(calendar.getTimeInMillis());
        temp.add(field, -1);
        return temp.get(field) == Calendar.getInstance().get(field);
    }

    /**
     * Gets if the given parameter of the date is the last to actual, like last week, last year, etc
     *
     * @param date  given date
     * @param field given field to be compared, like Calendar.Month, etc
     * @return true if it is the last, false otherwise
     */
    public static boolean isLast(Date date, int field) {
        return isLast(getCalendarFromDate(date), field);
    }

    /**
     * Gets if the given parameter of the date is the last to actual, like last week, last year, etc
     *
     * @param calendar given date as calendar
     * @param field    given field to be compared, like Calendar.Month, etc
     * @return true if it is the last, false otherwise
     */
    public static boolean isLast(Calendar calendar, int field) {
        Calendar temp = Calendar.getInstance();
        temp.setTimeInMillis(calendar.getTimeInMillis());
        temp.add(field, 1);
        return temp.get(field) == Calendar.getInstance().get(field);
    }

    /**
     * Gets if the given date is earlier than actual date
     *
     * @param date
     * @return true if the given date is in the past, false otherwise
     */
    public static boolean isEarlierThanActual(Date date) {
        return date.getTime() < getActualTimeInMillis();
    }

    /**
     * Gets if the given date is earlier than actual date
     *
     * @param calendar
     * @return true if the given date is in the past, false otherwise
     */
    public static boolean isEarlierThanActual(Calendar calendar) {
        return calendar.getTimeInMillis() < getActualTimeInMillis();
    }

    /**
     * Gets if the given date is later than actual date
     *
     * @param date
     * @return true if the given date is in the future, false otherwise
     */
    public static boolean isLaterThanActual(Date date) {
        return date.getTime() > getActualTimeInMillis();
    }

    /**
     * Gets if the given date is later than actual date
     *
     * @param calendar
     * @return true if the given date is in the future, false otherwise
     */
    public static boolean isLaterThanActual(Calendar calendar) {
        return calendar.getTimeInMillis() > getActualTimeInMillis();
    }

    /**
     * Gets actual field of the Calendar. Like Calendar.MONTH, Calendar.YEAR, etc.
     *
     * @param field given field like YEAR, MONTH, DAY_OF_WEEK, etc.
     * @return
     */
    public static int getActual(int field) {
        return Calendar.getInstance().get(field);
    }

    /**
     * Gets actual field of the Date (it converts Date instance to Calendar). Like Calendar.MONTH,
     * Calendar.YEAR, etc.
     *
     * @param date
     * @param field given field like YEAR, MONTH, DAY_OF_WEEK, etc.
     * @return
     */
    public static int getField(Date date, int field) {
        return getCalendarFromDate(date).get(field);
    }

    /**
     * Gets seconds after actual date for the given date. If given time is in the future, it will
     * return + values, otherwise it will return - values.
     *
     * @param date date
     * @return seconds remaining from the actual time to the given time
     */
    public static long secondsAfter(Date date) {
        long out = (date.getTime() - getActualTimeInMillis()) / 1000;
        return out < 0 ? 0 : out;
    }

    /**
     * Gets seconds after actual date for the given date. If given time is in the future, it will
     * return + values, otherwise it will return 0.
     *
     * @param calendar date
     * @return seconds remaining from the actual time to the given time
     */
    public static long secondsAfter(Calendar calendar) {
        long out = (calendar.getTimeInMillis() - getActualTimeInMillis()) / 1000;
        return out < 0 ? 0 : out;
    }

    /**
     * Gets seconds after actual date for the given date. Like if given time is in the future, it will
     * return + values, otherwise it will return 0.
     *
     * @param date date
     * @return seconds remaining from the actual time to the given time modulo 60, so it returns only
     * values from 0 to 59.
     */
    public static long secondsAfter60Max(Date date) {
        return secondsAfter(date) % 60;
    }

    /**
     * Gets seconds after actual date for the given date. If given time is in the future, it will
     * return + values, otherwise it will return 0.
     *
     * @param calendar date
     * @return seconds remaining from the actual time to the given time modulo 60, so it returns only
     * values from 0 to 59.
     */
    public static long secondsAfter60Max(Calendar calendar) {
        return secondsAfter(calendar) % 60;
    }

    /**
     * Gets seconds before actual date for the given date. If given time is in the past, it will
     * return + values, otherwise it will return 0.
     *
     * @param date date
     * @return seconds remaining from the given time to the actual time
     */
    public static long secondsBefore(Date date) {
        long out = (getActualTimeInMillis() - date.getTime()) / 1000;
        return out < 0 ? 0 : out;
    }

    /**
     * Gets seconds before actual date for the given date. If given time is in the past, it will
     * return + values, otherwise it will return 0.
     *
     * @param calendar date
     * @return seconds remaining from the given time to the actual time
     */
    public static long secondsBefore(Calendar calendar) {
        long out = (getActualTimeInMillis() - calendar.getTimeInMillis()) / 1000;
        return out < 0 ? 0 : out;
    }

    /**
     * Gets seconds before actual date for the given date. If given time is in the past, it will
     * return + values, otherwise it will return 0.
     *
     * @param date date
     * @return seconds remaining from the actual time to the given time modulo 60, so it returns only
     * values from 0 to 59.
     */
    public static long secondsBefore60Max(Date date) {
        return secondsBefore(date) % 60;
    }

    /**
     * Gets seconds before actual date for the given date. If given time is in the past, it will
     * return + values, otherwise it will return 0.
     *
     * @param calendar
     * @return seconds remaining from the actual time to the given time modulo 60, so it returns only
     * values from 0 to 59.
     */
    public static long secondsBefore60Max(Calendar calendar) {
        return secondsBefore(calendar) % 60;
    }

    /**
     * Gets minutes after actual date for the given date. If given time is in the future, it will
     * return + values, otherwise it will return 0.
     *
     * @param date date
     * @return minutes remaining from the actual time to the given time
     */
    public static long minutesAfter(Date date) {
        return secondsAfter(date) / MINUTE_IN_SEC;
    }

    /**
     * Gets minutes after actual date for the given date. If given time is in the future, it will
     * return + values, otherwise it will return 0.
     *
     * @param calendar date
     * @return minutes remaining from the actual time to the given time
     */
    public static long minutesAfter(Calendar calendar) {
        return secondsAfter(calendar) / MINUTE_IN_SEC;
    }

    /**
     * Gets minutes after actual date for the given date. If given time is in the future, it will
     * return + values, otherwise it will return 0.
     *
     * @param date date
     * @return minutes remaining from the actual time to the given time modulo 60, so it returns
     * only values from 0 to 59
     */
    public static long minutesAfter60Max(Date date) {
        return minutesAfter(date) % 60;
    }

    /**
     * Gets minutes after actual date for the given date. If given time is in the future, it will
     * return + values, otherwise it will return 0.
     *
     * @param calendar date
     * @return minutes remaining from the actual time to the given time modulo 60, so it returns
     * only values from 0 to 59
     */
    public static long minutesAfter60Max(Calendar calendar) {
        return minutesAfter(calendar) % 60;
    }

    /**
     * Gets minutes before actual date for the given date. If given time is in the past, it will
     * return + values, otherwise it will return 0.
     *
     * @param date date
     * @return minutes remaining from the given time to the actual time
     */
    public static long minutesBefore(Date date) {
        return secondsBefore(date) / MINUTE_IN_SEC;
    }

    /**
     * Gets minutes before actual date for the given date. If given time is in the past, it will
     * return + values, otherwise it will return 0.
     *
     * @param calendar date
     * @return minutes remaining from the given time to the actual time
     */
    public static long minutesBefore(Calendar calendar) {
        return secondsBefore(calendar) / MINUTE_IN_SEC;
    }

    /**
     * Gets minutes before actual date for the given date. If given time is in the past, it will
     * return + values, otherwise it will return 0.
     *
     * @param date date
     * @return minutes remaining from the given time to the actual time modulo 60, so it returns
     * only values from 0 to 59.
     */
    public static long minutesBefore60Max(Date date) {
        return minutesBefore(date) % 60;
    }

    /**
     * Gets minutes before actual date for the given date. If given time is in the past, it will
     * return + values, otherwise it will return 0.
     *
     * @param calendar date
     * @return minutes remaining from the given time to the actual time modulo 60, so it returns
     * only values from 0 to 59.
     */
    public static long minutesBefore60Max(Calendar calendar) {
        return minutesBefore(calendar) % 60;
    }

    /**
     * Gets hours after actual date for the given date. If given time is in the future, it will
     * return + values, otherwise it will return 0.
     *
     * @param date date
     * @return hours remaining from the actual time to the given time
     */
    public static long hoursAfter(Date date) {
        return secondsAfter(date) / HOUR_IN_SEC;
    }

    /**
     * Gets hours after actual date for the given date. If given time is in the future, it will
     * return + values, otherwise it will return 0.
     *
     * @param calendar date
     * @return hours remaining from the actual time to the given time
     */
    public static long hoursAfter(Calendar calendar) {
        return secondsAfter(calendar) / HOUR_IN_SEC;
    }

    /**
     * Gets hours after actual date for the given date. If given time is in the future, it will
     * return + values, otherwise it will return 0.
     *
     * @param date date
     * @return hours remaining from the actual time to the given time modulo 24, so it returns
     * only values from 0 to 23
     */
    public static long hoursAfter24Max(Date date) {
        return hoursAfter(date) % 24;
    }

    /**
     * Gets hours after actual date for the given date. If given time is in the future, it will
     * return + values, otherwise it will return 0.
     *
     * @param calendar date
     * @return minutes remaining from the actual time to the given time modulo 24, so it returns
     * only values from 0 to 23
     */
    public static long hoursAfter24Max(Calendar calendar) {
        return hoursAfter(calendar) % 24;
    }

    /**
     * Gets hours before actual date for the given date. If given time is in the past, it will
     * return + values, otherwise it will return 0.
     *
     * @param date date
     * @return hours remaining from the given time to the actual time
     */
    public static long hoursBefore(Date date) {
        return secondsBefore(date) / HOUR_IN_SEC;
    }

    /**
     * Gets hours before actual date for the given date. If given time is in the past, it will
     * return + values, otherwise it will return 0.
     *
     * @param calendar date
     * @return hours remaining from the given time to the actual time
     */
    public static long hoursBefore(Calendar calendar) {
        return secondsBefore(calendar) / HOUR_IN_SEC;
    }

    /**
     * Gets hours before actual date for the given date. If given time is in the past, it will
     * return + values, otherwise it will return 0.
     *
     * @param date date
     * @return hours remaining from the given time to the actual time modulo 24, so it returns
     * only values from 0 to 23.
     */
    public static long hoursBefore24Max(Date date) {
        return hoursBefore(date) % 24;
    }

    /**
     * Gets hours before actual date for the given date. If given time is in the past, it will
     * return + values, otherwise it will return 0.
     *
     * @param calendar date
     * @return hours remaining from the given time to the actual time modulo 24, so it returns
     * only values from 0 to 23.
     */
    public static long hoursBefore24Max(Calendar calendar) {
        return hoursBefore(calendar) % 24;
    }

    /**
     * Gets days after actual date for the given date. If given time is in the future, it will
     * return + values, otherwise it will return 0.
     *
     * @param date date
     * @return days remaining from the actual time to the given time
     */
    public static long daysAfter(Date date) {
        return secondsAfter(date) / DAY_IN_SEC;
    }

    /**
     * Gets days after actual date for the given date. If given time is in the future, it will
     * return + values, otherwise it will return 0.
     *
     * @param calendar date
     * @return days remaining from the actual time to the given time
     */
    public static long daysAfter(Calendar calendar) {
        return secondsAfter(calendar) / DAY_IN_SEC;
    }

    /**
     * Gets days before actual date for the given date. If given time is in the past, it will
     * return + values, otherwise it will return 0.
     *
     * @param date date
     * @return days remaining from the given time to the actual time
     */
    public static long daysBefore(Date date) {
        return secondsBefore(date) / DAY_IN_SEC;
    }

    /**
     * Gets days before actual date for the given date. If given time is in the past, it will
     * return + values, otherwise it will return 0.
     *
     * @param calendar date
     * @return days remaining from the given time to the actual time
     */
    public static long daysBefore(Calendar calendar) {
        return secondsBefore(calendar) / DAY_IN_SEC;
    }

    /**
     * Gets if given date is a weekday or not
     *
     * @param date date
     * @return true if the date is mon-fri, false otherwise
     */
    public static boolean isWeekday(Date date) {
        return isWeekday(getCalendarFromDate(date));
    }

    /**
     * Gets if given date is a weekday or not
     *
     * @param calendar date
     * @return true if the date is mon-fri, false otherwise
     */
    public static boolean isWeekday(Calendar calendar) {
        return calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
                && calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY;
    }

    /**
     * Gets if given date is a weekend day or not
     *
     * @param date date
     * @return true if the date is sat or sun, false otherwise
     */
    public static boolean isWeekend(Date date) {
        return !isWeekday(date);
    }

    /**
     * Gets if given date is a weekend day or not
     *
     * @param calendar date
     * @return true if the date is sat or sun, false otherwise
     */
    public static boolean isWeekend(Calendar calendar) {
        return !isWeekday(calendar);
    }

    /**
     * Gets the date as general words. For example now, few minutes ago, in 54 minutes, today, etc.
     * It is localized in en, is, cs and sk.
     *
     * @param context
     * @param date    date instance of the date
     * @return Now if it is les than a minute, In few minutes or Few minutes ago if it is less than
     * 5 minutes, In x minutes or x minutes ago if it is les than an hour, In x hours or x hours ago
     * if it is less than 6 hours, today if it is today, yesterday and tomorrow is clear, otherwise
     * it will return date in medium format (it is based on locale, in en it is like 21 feb, in cs
     * it looks like 21.2.)
     */
    public static String getTimeAsWord(Context context, Date date) {
        return getTimeAsWord(context, getCalendarFromDate(date));
    }

    /**
     * Gets the date as general words. For example now, few minutes ago, in 54 minutes, today, etc.
     * It is localized in en, is, cs and sk.
     *
     * @param context
     * @param calendar calendar instance of the date
     * @return Now if it is les than a minute, In few minutes or Few minutes ago if it is less than
     * 5 minutes, In x minutes or x minutes ago if it is les than an hour, In x hours or x hours ago
     * if it is less than 6 hours, today if it is today, yesterday and tomorrow is clear, otherwise
     * it will return date in medium format (it is based on locale, in en it is like 21 feb, in cs
     * it looks like 21.2.)
     */
    public static String getTimeAsWord(Context context, Calendar calendar) {
        long actualTime = getActualTimeInMillis();
        long time = calendar.getTimeInMillis();
        long diff = actualTime - time;
        long diffAbs = Math.abs(diff);
        boolean minus = diff < 0;
        // If diff < 0 it is in the future
        if (diffAbs < MINUTE_IN_MS) {
            return context.getString(R.string.now);
        }
        if (diffAbs < 5 * MINUTE_IN_MS) {
            return minus ? context.getString(R.string.in_few_minutes) : context.getString(R.string.few_minutes_ago);
        }
        if (diffAbs < HOUR_IN_MS) {
            return minus ? context.getResources().getQuantityString(R.plurals.in_minutes, (int) minutesAfter(calendar), (int) minutesAfter(calendar)) :
                    context.getResources().getQuantityString(R.plurals.minutes_ago, (int) minutesBefore(calendar), (int) minutesBefore(calendar));
        }
        if (diffAbs < 6 * HOUR_IN_MS) {
            return minus ? context.getResources().getQuantityString(R.plurals.in_hours, (int) hoursAfter(calendar), (int) hoursAfter(calendar)) :
                    context.getResources().getQuantityString(R.plurals.hours_ago, (int) hoursBefore(calendar), (int) hoursBefore(calendar));
        }
        if (isToday(calendar)) {
            return context.getString(R.string.today);
        }
        if (isTomorrow(calendar)) {
            return context.getString(R.string.tomorrow);
        }
        if (isYesterday(calendar)) {
            return context.getString(R.string.yesterday);
        }
        return formatTimestampDate(time, false, DateFormat.MEDIUM);
    }

    /**
     * Gets actual time in milliseconds
     *
     * @return date in ms
     */
    private static long getActualTimeInMillis() {
        return Calendar.getInstance().getTimeInMillis();
    }

    /**
     * Gets actual date as Date instance
     *
     * @return
     */
    private static Date getActualDate() {
        return new Date(getActualTimeInMillis());
    }

    /**
     * Converts Date instance to Calendar instance
     *
     * @param date
     * @return
     */
    private static Calendar getCalendarFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

}
