package com.football.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

/**
 * 通用日期处理类
 * 
 */
public class DateUtils {

	private static DateUtils instance;



	// public static DateBuilder getInstance() {
	// return new DateBuilder();
	// }
	public static DateUtils getInstance() {
		if (instance == null) {
			instance = new DateUtils();
		}
		return instance;
	}

	/**
	 * 取得系统当前时间
	 * 
	 * @return String yyyy-mm-dd
	 */
	public static String getCurrentDate_Simple() {
		Calendar rightNow = Calendar.getInstance();
		int year = rightNow.get(Calendar.YEAR);
		int month = rightNow.get(Calendar.MONTH) + 1;
		int day = rightNow.get(Calendar.DATE);
		return convertDateToString(convertStringToDate(year + "-" + month + "-" + day));
	}
	
	public static String getDateToCH(String datestr){
		if(datestr!=""){
			String str = "";
			String[] strsplit = datestr.split("-");
			if(strsplit.length==3){
				str = Integer.parseInt(strsplit[0].toString())+"年";
				str += Integer.parseInt(strsplit[1].toString())+"月";
				str += Integer.parseInt(strsplit[2].toString())+"日";
			}
			return str;
		}
		return "";
	}

	/**
	 * 获取现在时间
	 * 
	 * @return 返回时间字符串 yyyy-MM-dd HH:mm:ss
	 */
	public static String getCurrentDate() {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}
	/**
	 * 日期格式化
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date getCurrentDate(Date date) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.parse(format.format(date));
	}

	/**
	 * 获取现在时间
	 * 
	 * @return 返回时间字符串 yyyy-MM-dd HH:mm:ss
	 */
	public static String getDateByLongTime(long time) {
		Date date = new Date(time);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}

	public static String getCurrentDateOrder() {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		return format.format(date);
	}

	public static String getCurrentDateTime() {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		return format.format(date);

	}

	/**
	 * 取得系统当前时间前n个月的相对应的一天
	 * 
	 * @param n
	 *            int
	 * @return String yyyy-mm-dd
	 */
	public static String getMonthOfDayBeforeCurrentDate(int month) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -month);
		return convertDateToString(convertStringToDate("" + c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-"
				+ c.get(Calendar.DATE)));
	}

	/**
	 * 取得系统当前时间后n个月的相对应的一天
	 * 
	 * @param n
	 *            int
	 * @return String yyyy-mm-dd
	 */
	public static String getMonthOfDayAfterCurrentDate(int month) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -month);
		return convertDateToString(convertStringToDate("" + c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-"
				+ c.get(Calendar.DATE)));
	}

	/**
	 * 获取本月第一天
	 * 
	 * @return
	 */
	public static String getFirstDayOfCurrentMonth() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DATE);
		return convertDateToString(convertStringToDate(year + "-" + month + "-" + day));
	}

	/**
	 * 获取本月最后一天
	 * 
	 * @return
	 */
	public static String getLastDayOfCurrentMonth() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DATE);
		return convertDateToString(convertStringToDate(year + "-" + month + "-" + day));
	}

	/**
	 * 获取某月的第一天
	 * 
	 * @return 返回时间长整型
	 */
	public static String getFirstDayOfMonthString(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DATE);
		return convertDateToString(convertStringToDate(year + "-" + month + "-" + day));
	}

	/**
	 * 获取某月的最后一天
	 * 
	 * @return 返回时间长整型
	 */
	public static String getLastDayOfMonthString(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DATE);
		return convertDateToString(convertStringToDate(year + "-" + month + "-" + day));
	}

	/**
	 * 得到指定时间小时
	 */
	public static int getHour(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		return hour;
	}

	/**
	 * 获取指定时间天
	 */
	public static int getDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day = c.get(Calendar.DAY_OF_MONTH);
		return day;
	}

	/**
	 * 得到现在分钟
	 * 
	 * @return
	 */
	public static int getMinute() {
		Calendar c = Calendar.getInstance();
		int min = c.get(Calendar.MINUTE);
		return min;
	}

	/**
	 * 取得系统当前时间的前n天
	 * 
	 * @param n
	 *            int
	 * @return String yyyy-mm-dd
	 */
	public static String getDayBeforeCurrentDate(int day) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -day);
		return convertDateToString(convertStringToDate("" + c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-"
				+ c.get(Calendar.DATE)));
	}

	/**
	 * 取得系统当前时间后n天
	 * 
	 * @param n
	 *            int
	 * @return String yyyy-mm-dd
	 */
	public static String getDayAfterCurrentDate(int day) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, day);
		return convertDateToString(convertStringToDate("" + c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-"
				+ c.get(Calendar.DATE)));
	}
	
	public static Date getDateTimeAfterCurrentDate(int day) throws ParseException{
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		String newDate = getDayAfterCurrentDate(day);
		String time = format.format(date);
		SimpleDateFormat formatTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatTime.parse(newDate+" "+time);
	}


	/**
	 * 获取日期的星期
	 * 
	 * @param strDate
	 *            日期
	 * @return 星期数字
	 */
	public int getWeek(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int week = c.get(Calendar.DAY_OF_WEEK) - 1;
		return week;

	}

	public String getWeekString(int week) {
		String strWeek = "";
		switch (week) {
		case 0:
			strWeek = "星期日";
			break;
		case 1:
			strWeek = "星期一";
			break;
		case 2:
			strWeek = "星期二";
			break;
		case 3:
			strWeek = "星期三";
			break;
		case 4:
			strWeek = "星期四";
			break;
		case 5:
			strWeek = "星期五";
			break;
		case 6:
			strWeek = "星期六";
			break;
		case 7:
			strWeek = "星期日";
			break;
		default:
			strWeek = "";
		}
		return strWeek;
	}

	/**
	 * 将一个日期字符串转化成日期
	 * 
	 * @param sDate
	 *            String
	 * @return Date yyyy-mm-dd
	 */
	public static Date convertStringToDate(String strDate) {
		Date date = null;
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			date = df.parse(strDate);

		} catch (Exception e) {
			System.out.println("日期转换失败:" + e.getMessage());
		}
		return date;
	}
	
	public static Date convertStringToDate2(String strDate){
		Date date = null;
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			date = df.parse(strDate);

		} catch (Exception e) {
			System.out.println("日期转换失败:" + e.getMessage());
		}
		return date;
	}
	public static Date convertStringToDate3(String strDate, String format){
		Date date = null;
		try {
			SimpleDateFormat df = new SimpleDateFormat(format);
			date = df.parse(strDate);
			
		} catch (Exception e) {
			System.out.println("日期转换失败:" + e.getMessage());
		}
		return date;
	}

	/**
	 * 输入两个字符串型的日期，比较两者的大小
	 * 
	 * @param fromDate
	 *            String
	 * @param toDate
	 *            String
	 * @return boolean before为true
	 */
	public static boolean compareTwoDateBigOrSmall(String fromDate, String toDate) {
		Date dateFrom = convertStringToDate(fromDate);
		Date dateTo = convertStringToDate(toDate);
		if (dateFrom.before(dateTo)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 将一个日期字符串转化成Calendar
	 * 
	 * @param sDate
	 *            String
	 * @return Calendar
	 */
	public static Calendar convertDateStringToCalendar(String strDate) {
		Date date = convertStringToDate(strDate);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c;
	}

	/**
	 * 将一个日期转化成Calendar
	 * 
	 * @param date
	 *            Date
	 * @return Calendar
	 */
	public static Calendar convertDateToCalendar(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c;
	}

	/**
	 * 取得某个特定时间前n个月相对应的一天
	 * 
	 * @param sDate
	 *            String
	 * @param n
	 *            int
	 * @return yyyy-mm-dd
	 */
	public static String getDayOfMonthBeforeSpecificDate(String strDate, int month) {
		Calendar c = convertDateStringToCalendar(strDate);
		c.add(Calendar.MONTH, -month);
		return convertDateToString(convertStringToDate("" + c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-"
				+ c.get(Calendar.DATE)));
	}

	/**
	 * 取得某个特定时间前N小时的时间
	 * 
	 * @param sDate
	 *            String
	 * @param n
	 *            int
	 * @return yyyy-mm-dd HH:mm:ss
	 */
	public static String getDayOfHourBefore(String strDate, int hour) {

		Date date = null;
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			date = df.parse(strDate);
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.HOUR, -hour);
			strDate = c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DATE) + " " + c.get(Calendar.HOUR_OF_DAY) + ":"
					+ c.get(Calendar.MILLISECOND) + ":" + c.get(Calendar.SECOND);
		} catch (Exception e) {
			System.out.println("日期转换失败:" + e.getMessage());
		}
		return strDate;
	}

	/**
	 * 取得某个特定时间后n个月相对应的一天
	 * 
	 * @param sDate
	 *            String
	 * @param n
	 *            int
	 * @return yyyy-mm-dd
	 */
	public static String getDayOfMonthAfterSpecificDate(String strDate, int month) {
		Calendar c = convertDateStringToCalendar(strDate);
		c.add(Calendar.MONTH, month);
		return convertDateToString(convertStringToDate("" + c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-"
				+ c.get(Calendar.DATE)));
	}

	/**
	 * 取得某个特定时间前n天,格式为yyyy-mm-dd
	 * 
	 * @param sDate
	 *            String
	 * @param day
	 *            int
	 * @return yyyy-mm-dd
	 */
	public static String getDayBeforeSpecificDate(String strDate, int day) {
		Calendar c = convertDateStringToCalendar(strDate);
		c.add(Calendar.DAY_OF_MONTH, -day);
		return convertDateToString(convertStringToDate("" + c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-"
				+ c.get(Calendar.DATE)));
	}

	/**
	 * 取得某个时间后n天,格式为yyyy-mm-dd
	 * 
	 * @param sDate
	 *            String
	 * @param day
	 *            int
	 * @return yyyy-mm-dd
	 */
	public static String getDayAfterSpecificDate(String strDate, int day) {
		Calendar c = convertDateStringToCalendar(strDate);
		c.add(Calendar.DAY_OF_MONTH, day);
		return convertDateToString(convertStringToDate("" + c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-"
				+ c.get(Calendar.DATE)));
	}
	
	public static String getDayAfterSpecificDate(String strDate, int day,String format) {
		Calendar c = convertDateStringToCalendar(strDate);
		c.add(Calendar.DAY_OF_MONTH, day);
		return convertDateToString(convertStringToDate("" + c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-"
				+ c.get(Calendar.DATE)),format);
	}

	/**
	 * 判断系统当前时间是否为闰年
	 * 
	 * @return
	 */
	public boolean isLeapYear() {
		Calendar rightNow = Calendar.getInstance();
		int year = rightNow.get(Calendar.YEAR);
		if (0 == year % 4 && (year % 100 != 0 || year % 400 == 0)) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 将一个字符串转化为标准日期
	 * 
	 * @param str
	 * @return
	 */
	public Date convertStringToStandardDate(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		Date dateTime = null;
		if (!"".equals(str)) {
			try {
				date = sdf.parse(str);
				dateTime = new Date(date.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}
		return dateTime;
	}

	/**
	 * 将日期转化为字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String convertDateToString(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(date);
	}
	
	public static String convertDateToString(Date date,String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}

	/**
	 * 将日期转化为字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String convertDateToString_str(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		return dateFormat.format(date);
	}
	/**
	 * 将日期转换为字符串
	 * @param date 日期
	 * @param pattern 格式字符串
	 * @return
	 */
	public static String formatDate(Date date,String pattern){
		if(date == null)
			return "";
		if(pattern ==null || "".equals(pattern)){
			pattern = "yyyy-MM-dd";
		}
		SimpleDateFormat dateFormat = null;
		try{
			dateFormat = new SimpleDateFormat(pattern);
			return dateFormat.format(date);
		}catch(Exception e){
			dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			return dateFormat.format(date);
		}
		
	}

	/**
	 * 获取指定时间月份
	 */
	public static int getMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int month = c.get(Calendar.MONTH) + 1;
		return month;
	}

	/**
	 * 获取指定时间年份
	 */
	public static int getYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int year = c.get(Calendar.YEAR);
		return year;
	}

	/**
	 * 取得上一个小时
	 */
	public static String getPreHour(String format, int step) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR, cal.get(Calendar.HOUR) - step);
		return sdf.format(cal.getTime());
	}

	/**
	 * 
	 * 取得某日期的上一个月时间
	 */
	public static String getPrevMonthOfDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(convertStringToDate(date));
		cal.add(Calendar.MONTH, -1); // 前个月
		return sdf.format(cal.getTime());
	}

	/**
	 * 
	 * 取得某日期的下一个月时间
	 */
	public static String getNextMonthOfDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(convertStringToDate(date));
		cal.add(Calendar.MONTH, 1); // 后个月
		return sdf.format(cal.getTime());
	}

	public static Hashtable<Integer, Integer> getWeekNumberHT(long beginDate, long endDate) {
		Hashtable<Integer, Integer> weeks = new Hashtable<Integer, Integer>();
		for (int i = 1; i <= 7; i++) {
			weeks.put(i, 0);
		}
		long q = beginDate;
		for (q = beginDate; q <= endDate; q = q + new Long(24 * 3600 * 1000).longValue()) {
			Calendar c = Calendar.getInstance();
			Date date = new Date(q);
			c.setTime(date);
			int dayofweek = c.get(Calendar.DAY_OF_WEEK) - 1;
			if (dayofweek == 0) {
				dayofweek = 7;
			}
			if (weeks.containsKey(dayofweek)) {
				weeks.put(dayofweek, weeks.get(dayofweek) + 1);
			} else {
				weeks.put(dayofweek, 1);
			}
		}
		return weeks;
	}

	/**
	 * 上一星期
	 * 
	 * @param date
	 * @return
	 */
	public static String getPrevWeekOfDate(String date) {
		return getDayBeforeSpecificDate(date, 7);
	}

	/**
	 * 获取某年某月的最后一天
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @return 最后一天
	 */
	public static int getLastDayOfMonth(int year, int month) {
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
			return 31;
		}
		if (month == 4 || month == 6 || month == 9 || month == 11) {
			return 30;
		}
		if (month == 2) {
			if (isLeapYear(year)) {
				return 29;
			} else {
				return 28;
			}
		}
		return 0;
	}

	/**
	 * 是否闰年
	 * 
	 * @param year
	 *            年
	 * @return
	 */
	public static boolean isLeapYear(int year) {
		return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
	}
	/**
	 * 返回某段时间内 每一天
	 * @param beginDateStr
	 * @param endDateStr
	 * @return
	 */
	public static List<String> getDaysByFromTo(String beginDateStr,String endDateStr){
		Date beiginDate = convertStringToDate(beginDateStr);
		endDateStr = getDayAfterSpecificDate(endDateStr, 1);
		Date endDate = convertStringToDate(endDateStr);
		List<String> dateList = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		if(currentDate.before(beiginDate)){
			currentDate = beiginDate;
		}
		String currentDateStr = sdf.format(currentDate);
		while(currentDate.before(endDate)){
			dateList.add(currentDateStr);
			currentDateStr = getDayAfterSpecificDate(currentDateStr, 1);
			currentDate = convertStringToDate(currentDateStr);
		}
		return dateList;
	}
	
	public static String getDateToCHYM(String datestr){
		String d = formatDate(convertStringToDate(datestr),"");
		if(d!=""){
			String str = "";
			String[] strsplit = d.split("-");
			if(strsplit.length==3){
				str = Integer.parseInt(strsplit[0].toString())+"年";
				str += Integer.parseInt(strsplit[1].toString())+"月";
			}
			return str;
		}
		return "";
	}
	
	public static String getDateToMD(String datestr){
		String d = formatDate(convertStringToDate(datestr),"");
		if(d!=""){
			String str = "";
			String[] strsplit = d.split("-");
			if(strsplit.length==3){
				str = Integer.parseInt(strsplit[0].toString())+"年";
				str += Integer.parseInt(strsplit[1].toString())+"月";
			}
			return str;
		}
		return "";
	}
	
	public static String getMMddHHmm(String strDate){
		return formatDate(convertStringToDate2(strDate),"MM-dd HH:mm");
	}
}
