package com.bzly.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {

    private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmssS");

    private static SimpleDateFormat sdf3 = new SimpleDateFormat("yyyyMMdd");
    
    private static SimpleDateFormat sdf4 = new SimpleDateFormat("yyyyMMddHHmmss");
    
    private static SimpleDateFormat sdf5 = new SimpleDateFormat("yyyy-MM-dd");
    
	private static SimpleDateFormat sdf6 = new SimpleDateFormat("yyyy-M-d");
    
    private static String dateStr = "yyyy-MM-dd";
    
    public static String getCurrentDate() {

        return sdf1.format(new Date());
    }

    synchronized public static String getLongCurrentDate() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
        }
            
        return sdf2.format(new Date());
    }

    public static String getShortCurrentDate() {

        return sdf3.format(new Date());
    }

    public static String getShortTomorrowDate() {
    	long dateTime = new Date().getTime();
    	Date date = new Date(dateTime + (24*60*60*1000));
    	return sdf3.format(date);
    }
    
    public static Date praseStringDate(String dateStr){
        try {
             Date date = sdf1.parse(dateStr);
             return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
   
    public static String getCurrentDate2(){
        return sdf4.format(new Date());
    }
    
    public static String getCurrentDate5(){
        return sdf5.format(new Date());
    }
    
    public static String formatDateStr(String d) {
        Date date;
        try {
            date = new SimpleDateFormat(dateStr).parse(d);
            return sdf3.format(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        return null;
    }
    
    public static String formatDate2Str(String str){
    	  Date date;
          try {
              date = new SimpleDateFormat(dateStr).parse(str);
              return sdf5.format(date);
          } catch (ParseException e) {
              e.printStackTrace();
          } 
          return null;
    }

    public static String cutOutDate(String date){
    	return date.substring(0, 10);
    }
    
    public static int getDaysBetweenTwoDate(Date beginDate,Date endDate){
    	int days = 0;
    	long times = endDate.getTime() - beginDate.getTime();
    	days = (int) (times/86400000);
    	return days;
    }

    public static boolean isSflb(String endInvestDate){
         boolean flag = false;
         try {
            Date date = sdf1.parse(endInvestDate);
            long times = date.getTime() - new Date().getTime() ;
            if(times < 43200000){
                flag = true; 
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return flag ;
    }
    
    public static String getYesterday(){
        Date date = new Date(new Date().getTime()-86400000);
        
        return sdf3.format(date);
    }
    public static String getYesterday2(){
    	Date date = new Date(new Date().getTime()-86400000);
    	
    	return sdf5.format(date);
    }
    
    public static String formartStringtoDate(String date){
        
        try {
            Date formatDate = sdf3.parse(date);
            return sdf5.format(formatDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean dateGreaterOneDay (String date){ 
        boolean flag = false;
        try {
            Date formatDate = sdf1.parse(date);
            long times =  formatDate.getTime() - new Date().getTime();
            if(times < 86400000){
                flag = true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static boolean dateGreaterOneDay2 (String date){ 
        boolean flag = true;
        try {
            Date formatDate = sdf1.parse(date);
            long times =  new Date().getTime() - formatDate.getTime();
            if(times > 86400000){
                flag = false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return flag;
    }
    
	public static int differentDays(Date beginDate, Date endDate) {
		Calendar beforeCal = Calendar.getInstance();
		beforeCal.setTime(beginDate);
		Calendar afterCal = Calendar.getInstance();
		afterCal.setTime(endDate);
		int beforeYear = beforeCal.get(Calendar.YEAR);
		int afterYear = afterCal.get(Calendar.YEAR);
		int days = 0;
		if (beforeYear == afterYear) { 
			int beforeDay = beforeCal.get(Calendar.DAY_OF_YEAR);
			int afterDay = afterCal.get(Calendar.DAY_OF_YEAR);
			days = Math.abs(afterDay - beforeDay);
		} else { 
			while (beforeCal.before(afterCal)) {
				days++;
				beforeCal.add(Calendar.DAY_OF_YEAR, 1);
			}
		}
		return days;
	}
	  
	public static int differentDays(String beginDateStr, String endDateStr) {
		
		return differentDays(praseStringDate(beginDateStr), praseStringDate(endDateStr));
	}
	
	public static String getMonthEndTime(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, (month - 1));
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		Date time = calendar.getTime();
		return sdf1.format(time);
	}
	
	public static String formatDate(Date date) {
		try {
			return sdf1.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<String> getAllDayDateList(String startTime, String endTime) {
		List<String> resultList = new ArrayList<String>();
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(DateUtil.praseStringDate(endTime));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateUtil.praseStringDate(startTime));
		while (calendar.before(endCalendar)) {
			resultList.add(sdf6.format(calendar.getTime()));
			calendar.add(Calendar.DAY_OF_YEAR, 1);
		}
		return resultList;
	}
	public static String getAddDay(Date date,Long day,String format) {
		long endTime=date.getTime()+day*24*60*60*1000;
		SimpleDateFormat dateformat = new SimpleDateFormat(format);
		String time = dateformat.format(endTime);
		return time;
	}
	
	public static String getLongToDateDay(Long day,String format) {
		SimpleDateFormat dateformat = new SimpleDateFormat(format);
		String time = dateformat.format(day);
		return time;
	}
	
	/**
	 * 查询上个月份
	 * @return
	 * @throws ParseException
	 */
    public static String getLastMonth() throws ParseException{
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.MONTH, -1);
		Date m = c.getTime();
		String mon = format.format(m);
		return  mon.substring(0,7);
    }
    /**
     * 获取昨天0点的时间戳
     * @return
     */
    public static long getYesterDay() {
    	long now = new Date().getTime();
		long time= now-86400000;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String yesterDay=sdf.format(new Date(time))+" 00:00:00";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long yesterDayTime=0l;
		try {
			 Date date = simpleDateFormat.parse(yesterDay);
			 yesterDayTime = date.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return yesterDayTime;
    }
    
    /**
     * 获取本周的第一天
     * @return String
     * **/
    public static String getWeekStart(){
        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.WEEK_OF_MONTH, 0);
        cal.set(Calendar.DAY_OF_WEEK, 2);
        Date time=cal.getTime();
        Date date = new Date();
        long timeL = time.getTime();
        long dateL = date.getTime();
        if(dateL < timeL){
        	 cal.add(Calendar.WEEK_OF_MONTH, -1);
        	 cal.set(Calendar.DAY_OF_WEEK, 2);
        	 time=cal.getTime();
        }
        return new SimpleDateFormat("yyyy-MM-dd").format(time)+" 00:00:00";
    }
    /**
     * 获取今天某时刻的时间戳
     * @param hour
     * @param minute
     * @return
     * @throws ParseException
     */
    public static long timeStamp(int hour,int minute) throws ParseException {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String yesterDay=sdf.format(new Date())+" 00:00:00";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(yesterDay);
        long yesterDayTime = date.getTime()+hour*3600000+minute*60000;
		return yesterDayTime;
    }
}