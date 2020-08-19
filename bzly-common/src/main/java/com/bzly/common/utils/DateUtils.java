package com.bzly.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class DateUtils {
    public static final String DATETIME_FORMAT = "yyyyMMddHHmmss";
    public static final String DATE_FORMAT = "yyyyMMdd";
    public static final String SIMPLE_DATE = "yyyy-MM-dd";
    public static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private StringBuffer buffer = new StringBuffer();
    private static String ZERO = "0";
    private static DateUtils date;

    public String getNowString() {
        Calendar calendar = getCalendar();
        buffer.delete(0, buffer.capacity());
        buffer.append(getYear(calendar));

        if (getMonth(calendar) < 10) {
            buffer.append(ZERO);
        }
        buffer.append(getMonth(calendar));

        if (getDate(calendar) < 10) {
            buffer.append(ZERO);
        }
        buffer.append(getDate(calendar));
        if (getHour(calendar) < 10) {
            buffer.append(ZERO);
        }
        buffer.append(getHour(calendar));
        if (getMinute(calendar) < 10) {
            buffer.append(ZERO);
        }
        buffer.append(getMinute(calendar));
        if (getSecond(calendar) < 10) {
            buffer.append(ZERO);
        }
        buffer.append(getSecond(calendar));
        return buffer.toString();
    }

    private static int getDateField(Date date, int field) {
        Calendar c = getCalendar();
        c.setTime(date);
        return c.get(field);
    }

	public static Date addDate(Date date, int day) {
		Calendar c = getCalendar();
		c.setTime(date);
		c.add(Calendar.DATE, day);
		return c.getTime();
	}
	public static Date addMonth(Date date, int month) {
		Calendar c = getCalendar();
		c.setTime(date);
		c.add(Calendar.MONTH, month);
		return c.getTime();
	}
	public static Date addHour(Date date, int hour) {
		Calendar c = getCalendar();
		c.setTime(date);
		c.add(Calendar.HOUR, hour);
    	c.set(Calendar.MINUTE, 0);
    	c.set(Calendar.SECOND, 0);
    	c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}
	public static Date addMinutes(Date date, int MINUTE) {
		Calendar c = getCalendar();
		c.setTime(date);
		c.add(Calendar.MINUTE, MINUTE);
		return c.getTime();
	}
	
    public static int getYearsBetweenDate(Date begin, Date end) {
        int bYear = getDateField(begin, Calendar.YEAR);
        int eYear = getDateField(end, Calendar.YEAR);
        return eYear - bYear;
    }

    public static int getMonthsBetweenDate(Date begin, Date end) {
        int bMonth = getDateField(begin, Calendar.MONTH);
        int eMonth = getDateField(end, Calendar.MONTH);
        return eMonth - bMonth;
    }

    public static int getWeeksBetweenDate(Date begin, Date end) {
        int bWeek = getDateField(begin, Calendar.WEEK_OF_YEAR);
        int eWeek = getDateField(end, Calendar.WEEK_OF_YEAR);
        return eWeek - bWeek;
    }

    public static int getDaysBetweenDate(Date begin, Date end) {
        int bDay = getDateField(begin, Calendar.DAY_OF_YEAR);
        int eDay = getDateField(end, Calendar.DAY_OF_YEAR);
        return eDay - bDay;
    }
 
    public static Date getSpecficYearStart(Date date, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, amount);
        cal.set(Calendar.DAY_OF_YEAR, 1);
        return getStartDate(cal.getTime());
    }

    public static Date getSpecficYearEnd(Date date, int amount) {
        Date temp = getStartDate(getSpecficYearStart(date, amount + 1));
        Calendar cal = Calendar.getInstance();
        cal.setTime(temp);
        cal.add(Calendar.DAY_OF_YEAR, -1);
        return getFinallyDate(cal.getTime());
    }

    public static Date getSpecficMonthStart(Date date, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, amount);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return getStartDate(cal.getTime());
    }

    public static Date getSpecficMonthEnd(Date date, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getSpecficMonthStart(date, amount + 1));
        cal.add(Calendar.DAY_OF_YEAR, -1);
        return getFinallyDate(cal.getTime());
    }

    public static Date getSpecficWeekStart(Date date, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.setFirstDayOfWeek(Calendar.MONDAY); /* 设置一周的第一天为星期一 */
        cal.add(Calendar.WEEK_OF_MONTH, amount);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return getStartDate(cal.getTime());
    }

    public static Date getSpecficWeekEnd(Date date, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY); /* 设置一周的第一天为星期一 */
        cal.add(Calendar.WEEK_OF_MONTH, amount);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return getFinallyDate(cal.getTime());
    }

    public static Date getSpecficDateStart(Date date, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, amount);
        return getStartDate(cal.getTime());
    }

    public static Date getSpecficDateEnd(Date date, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, amount);
        return getFinallyDate(cal.getTime());
    }

    public static Date getSpecficDate(Date date, String format, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, amount);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String temp = sdf.format(cal.getTime());
        try {
            return sdf.parse(temp);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date getSpecficMonth(Date date, String format, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, amount);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String temp = sdf.format(cal.getTime());
        try {
            return sdf.parse(temp);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date getSpecficWeek(Date date, String format, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.WEEK_OF_YEAR, amount);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String temp = sdf.format(cal.getTime());
        try {
            return sdf.parse(temp);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date getFinallyDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String temp = format.format(date);
        temp += " 23:59:59";
        try {
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return format1.parse(temp);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date getStartDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String temp = format.format(date);
        temp += " 00:00:00";
        try {
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return format1.parse(temp);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date getFinallyDate(String date) {
        String temp = date;
        temp += " 23:59:59";
        try {

            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return format1.parse(temp);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date getStartDate(String date) {
        String temp = date;
        temp += " 00:00:00";
        try {
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return format1.parse(temp);
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean isInDate(Date date, Date compareDate) {
        if (compareDate.after(getStartDate(date)) && compareDate.before(getFinallyDate(date))) {
            return true;
        } else {
            return false;
        }
    }

    public static Date getDate(Date date, String format) {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        String temp = sf.format(date);
        try {
            return sf.parse(temp);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date getStringToDate(String date, String format) {
        try {
            SimpleDateFormat format1 = new SimpleDateFormat(format);
            return format1.parse(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date parseDate(String datetime) {
        try {
            if (StringUtils.isBlank(datetime)) {
                return null;
            }
            if (datetime.length() < 8 || datetime.length() > 14) {
                return null;
            }
            DateFormat dateFormat = new SimpleDateFormat(DATETIME_FORMAT);
            if (datetime.length() < 14) {
                dateFormat = new SimpleDateFormat(DATETIME_FORMAT.substring(0, datetime.length()));
            }
            return dateFormat.parse(datetime);
        } catch (Exception e) {
            return null;
        }
    }

    public static String format(Date date, String format) {
        if (null == date) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static int getDaysBetweenTwoDate(Date begin, Date end) {
        int nDay = (int) ((end.getTime() - begin.getTime()) / (24 * 60 * 60 * 1000));
        return nDay;
    }

    public static int getMonthsBetweenTwoDate(Date begin, Date end) {
        int aYear = getYearsBetweenDate(begin, end);
        int bMonth = getMonthsBetweenDate(begin, end);
        int bYear = aYear * 12 > 0 ? aYear * 12 : aYear * 12 + 1;
        return bYear + bMonth;
    }

    private int getYear(Calendar calendar) {
        return calendar.get(Calendar.YEAR);
    }

    private int getMonth(Calendar calendar) {
        return calendar.get(Calendar.MONDAY) + 1;
    }

    private int getDate(Calendar calendar) {
        return calendar.get(Calendar.DATE);
    }

    private int getHour(Calendar calendar) {
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    private int getMinute(Calendar calendar) {
        return calendar.get(Calendar.MINUTE);
    }

    private int getSecond(Calendar calendar) {
        return calendar.get(Calendar.SECOND);
    }

    private static Calendar getCalendar() {
        return Calendar.getInstance();
    }

    public static DateUtils getDateInstance() {
        if (date == null) {
            date = new DateUtils();
        }
        return date;
    }

    public static String pad0(String date, int length) {
        return padChar(date, length, '0');
    }

    public static String pad9(String date, int length) {
        return padChar(date, length, '9');
    }

    private static String padChar(String date, int length, char theChar) {
        if (StringUtils.isEmpty(date)) {
            date = "";
        }
        return StringUtils.rightPad(date, length, theChar);
    }

    public static boolean lessThan(String time1, String time2) {
        if (StringUtils.isEmpty(time1)) {
            if (StringUtils.isEmpty(time2)) {
                return false;
            } else {
                return true;
            }
        } else {
            return time1.compareTo(time2) < 0;
        }
    }

    public static boolean greaterThan(String time1, String time2) {
        if (StringUtils.isEmpty(time1)) {
            if (StringUtils.isEmpty(time2)) {
                return false;
            } else {
                return true;
            }
        } else {
            return time1.compareTo(time2) > 0;
        }
    }

    public static long toMilliseconds(String datetime) {
        return parseDate(datetime).getTime();
    }

    public static String getHHmmssBetweenTwoDate(Date begin, Date end) {
        long l = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒

        long hour = l / (60 * 60);
        long minute = (l / 60 - hour * 60);
        long second = (l - hour * 60 * 60 - minute * 60);
        return hour + "小时" + minute + "分" + second + "秒";
    }

    public static List<String> getDatePeriod(Date date, int beforeDays) {
        List<String> datePeriodList = new ArrayList<String>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = getCalendar();
        cal.setTime(date);
        Calendar currentDay = cal;
        currentDay.add(Calendar.DATE, -(beforeDays - 1));
        for (int i = 0; i < beforeDays; i++) {
            datePeriodList.add(dateFormat.format(currentDay.getTime()));
            currentDay.add(Calendar.DATE, 1);
        }
        return datePeriodList;
    }

    public static List<String> getDatePeriod(Date beginDate, Date endDate) {
        if (beginDate.compareTo(endDate) >= 0) {
            return null;
        }
        List<String> datePeriodList = new ArrayList<String>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar begin = getCalendar();
        begin.setTime(beginDate);
        Calendar end = getCalendar();
        end.setTime(endDate);
        Calendar currentDay = begin;
        datePeriodList.add(dateFormat.format(currentDay.getTime()));
        while (true) {
            currentDay.add(Calendar.DATE, 1);
            datePeriodList.add(dateFormat.format(currentDay.getTime()));
            if (currentDay.compareTo(end) == 0) {
                break;
            }
        }
        return datePeriodList;
    }

    public static List<String> getDatePeriodOfMonth(Date date) {
        List<String> datePeriodList = new ArrayList<String>();
        Calendar cal = getCalendar();
        cal.setTime(date);
        cal.set(Calendar.DATE, 1);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int month = cal.get(Calendar.MONTH);
        while (cal.get(Calendar.MONTH) == month) {
            datePeriodList.add(dateFormat.format(cal.getTime()));
            cal.add(Calendar.DATE, 1);
        }
        return datePeriodList;
    }

    public static List<String> getMonthPeriod(Date date) {
        List<String> datePeriodList = new ArrayList<String>();
        Calendar cal = getCalendar();
        cal.setTime(date);
        cal.set(Calendar.MONTH, 0); 
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        int year = cal.get(Calendar.YEAR);
        while (cal.get(Calendar.YEAR) == year) {
            datePeriodList.add(dateFormat.format(cal.getTime()));
            cal.add(Calendar.MONTH, 1);
        }
        return datePeriodList;
    }

    public static List<String> getMonthPeriod(Date beginDate, Date endDate) {
        if (beginDate.compareTo(endDate) >= 0) {
            return null;
        }
        List<String> datePeriodList = new ArrayList<String>();
        SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM");
        int months = getMonthsBetweenTwoDate(beginDate, endDate);
        for (int i = 0; i <= months; i++) {
            Calendar cal = getCalendar();
            cal.setTime(beginDate);
            cal.add(Calendar.MONTH, i);
            datePeriodList.add(monthFormat.format(cal.getTime()));
        }
        return datePeriodList;
    }

    public static boolean isTheDay(final Date date) {
        Date day = new Date();
        return date.getTime() >= dayBegin(day).getTime() && date.getTime() <= dayEnd(day).getTime();
    }

    public static Date dayBegin(final Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public static Date dayEnd(final Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }

    public static int calRowDate(List<Date> list) {
        if (list == null || list.size() == 0)
            return 0;
        int num = 1;
        int size = 0; 
        if (list.size() == 0)
            return 1;
        long l1, l2;
        while (size + 1 < list.size()) {
            l1 = list.get(size).getTime();
            l2 = list.get(size + 1).getTime();
            if (l1 - l2 == 1000 * 60 * 60 * 24) {
                num++;
                size = size + 1;
            } else {
                break;
            }
        }
        return num;
    }
     
    public static boolean isInBetweenDate(String startTime, String endTime, String nowTime){
    	boolean result= false;
    	if(greaterThan(nowTime, startTime) && greaterThan(endTime, nowTime)){
    		result= true;
    	}
    	return result;
    }
    
    public static Date getBeforeDayDate(int day,Date date){
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - day);
    	return cal.getTime();
    }
    
	public static String getCurrentDateTimeStr() {
		SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String timeString = dataFormat.format(date);
		return timeString;
	}
	public static Date getNeedTime(int hour,int minute,int second,int addDay,int ...args){
	    Calendar calendar = Calendar.getInstance();
	    if(addDay != 0){
	        calendar.add(Calendar.DATE,addDay);
	    }
	    calendar.set(Calendar.HOUR_OF_DAY,hour);
	    calendar.set(Calendar.MINUTE,minute);
	    calendar.set(Calendar.SECOND,second);
	    if(args.length==1){
	        calendar.set(Calendar.MILLISECOND,args[0]);
	    }
	    return calendar.getTime();
	}
	public static String getMillisecondTime(Long time) {
		SimpleDateFormat dateformat = new SimpleDateFormat(DateUtils.SIMPLE_DATE);
		String dateStr = dateformat.format(time);
		return dateStr;
	}
	
	public static String getCurrentDate() {
		SimpleDateFormat sdf1 = new SimpleDateFormat(SIMPLE_DATE_FORMAT);
        return sdf1.format(new Date());
    }
	
	public static String dateToString(Date date, String format) {
        String result = "";
        SimpleDateFormat formater = new SimpleDateFormat(format);
        try {
            result = formater.format(date);
        } catch (Exception e) {
        }
        return result;
    }
	public static long getDaySub(String beginDateStr,String endDateStr){
	    long day=0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");    
        Date beginDate;
	    Date endDate;
        try{
	         beginDate = format.parse(beginDateStr);
	         endDate= format.parse(endDateStr);    
	         day=(endDate.getTime()-beginDate.getTime())/(24*60*60*1000);     
        } catch (ParseException e){
             // TODO 自动生成 catch 块
             e.printStackTrace();
        }   
        return day;
   }
}
