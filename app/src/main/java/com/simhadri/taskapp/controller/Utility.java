package com.simhadri.taskapp.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Calendar.DATE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

/**
 * Created by rajup on 18-01-2018.
 */

public class Utility {

    public static String capitalize(String capString){
        try {
            if(!capString.isEmpty()) {
                StringBuffer capBuffer = new StringBuffer();
                Matcher capMatcher = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(capString);
                while (capMatcher.find()) {
                    capMatcher.appendReplacement(capBuffer, capMatcher.group(1).toUpperCase() + capMatcher.group(2).toLowerCase());
                }

                return capMatcher.appendTail(capBuffer).toString();
            }
        }catch (Exception e){e.printStackTrace();}
        return capString;
    }


    // Comparator for Ascending Order
    public static Comparator<String> StringAscComparator = new Comparator<String>() {

        public int compare(String app1, String app2) {

            String stringName1 = app1;
            String stringName2 = app2;

            return stringName1.compareToIgnoreCase(stringName2);
        }
    };
    //Comparator for Descending Order
    public static Comparator<String> StringDescComparator = new Comparator<String>() {

        public int compare(String app1, String app2) {

            String stringName1 = app1;
            String stringName2 = app2;

            return stringName2.compareToIgnoreCase(stringName1);
        }
    };

    public static boolean isValidDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date testDate = null;
        try {
            testDate = sdf.parse(date);
        } catch (ParseException e) {
            return false;
        }

        return true;
    }

    public static String getDateReqdFormat(String pDate) {
        String appointmentDate = "";
        try {
            if(!pDate.isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date myDate = sdf.parse(pDate);
                sdf.applyPattern("EEEE dd MMMM, yyyy");
                appointmentDate = sdf.format(myDate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return appointmentDate;
    }



    public static String getDateReqddayFormat(String pDate) {
        String appointmentDate = "";
        try {
            if(!pDate.isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date myDate = sdf.parse(pDate);
                sdf.applyPattern("dd'th' MMMM, yyyy EEEE");
                appointmentDate = sdf.format(myDate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return appointmentDate;
    }


    public static String getDateReqdMonthFormat(String pDate) {
        String appointmentDate = "";
        try {
            if(!pDate.isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date myDate = sdf.parse(pDate);
                sdf.applyPattern("dd MMMM, yyyy");
                appointmentDate = sdf.format(myDate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return appointmentDate;
    }



    public static String getDateReqdMonthDayFormat(String pDate) {
        String appointmentDate = "";
        try {
            if(!pDate.isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                Date myDate = sdf.parse(pDate);
                sdf.applyPattern("EEEE d MMMM, yyyy");
                appointmentDate = sdf.format(myDate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return appointmentDate;
    }

    public static boolean isValidPostolCode(String phone) {
        boolean check = false;
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            if ((5 <= phone.length()) && (phone.length() <= 6)) {
                check = false;
            } else {
                check = true;
            }
        } else {
            check = false;
        }
        return check;
    }


    public static boolean isloginValidPhone(String phone) {
        boolean check = false;
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            if ((6 <= phone.length()) && (phone.length() <= 11)) {
                check = true;

            } else {
                check = false;
            }
        } else {
            check = false;
        }
        return check;
    }

    public static boolean isValidPhone(String phone) {
        boolean check = false;
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            if ((6 <= phone.length()) && (phone.length() <= 11)) {
                check = false;

            } else {
                check = true;
            }
        } else {
            check = false;
        }
        return check;
    }

    /*
        Resetting variables when we search with keyword and guided
     */


    public static String setEmiratesIdwithDelimeter(String pEmiratesId) {

        //String to char Array
        char emiratesList[] = pEmiratesId.toCharArray();
        //The string builder used to construct the string
        StringBuilder commaSepValueBuilder = new StringBuilder();
        //Looping through the list
        for (int i = 0; i < emiratesList.length; i++) {
            //append the value into the builder
            commaSepValueBuilder.append(emiratesList[i]);
            if (i == 2) {
                //append the value into the builder
                commaSepValueBuilder.append("-");
            }
            if (i == 6) {
                //append the value into the builder
                commaSepValueBuilder.append("-");
            }
            if (i == 13) {
                //append the value into the builder
                commaSepValueBuilder.append("-");
            }
        }
        return commaSepValueBuilder.toString();
    }

    public static String splitEmiratesIdwithoutDelimeter(String pEmiratesId) {
        StringBuilder strEmirate = new StringBuilder();
        StringTokenizer token = new StringTokenizer(pEmiratesId, "-");
        while (token.hasMoreTokens()) {
            strEmirate.append(token.nextToken());
        }
        return strEmirate.toString();
    }

    public static void weekDays() {
        String hcp_week_days = "";
        for (int w = 1; w <= 7; w++) {
            if (w == 1) {
                hcp_week_days = "1";
            } else {
                hcp_week_days += ",";
                hcp_week_days += w;
            }
        }

    }

    public static Date stringToDate(String aDate, String aFormat) {

        if (aDate == null)
            return null;
        ParsePosition pos = new ParsePosition(0);
        SimpleDateFormat simpledateformat = new SimpleDateFormat(aFormat);
        Date stringDate = simpledateformat.parse(aDate, pos);

        return stringDate;
    }

    public static long daysBetween(Calendar startDate, Calendar endDate) {
        long end = endDate.getTimeInMillis();
        long start = startDate.getTimeInMillis();
        return TimeUnit.MILLISECONDS.toDays(Math.abs(end - start));
    }

    public static Calendar DateToCalendar(Date date) {
        Calendar cal = null;
        try {
            cal = Calendar.getInstance();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            format.format(date);
            cal = format.getCalendar();
        } catch (Exception e) {
            System.out.println("Exception :" + e);
        }
        return cal;
    }

    public static String weeksBetween(Calendar startDate, Calendar endDate) {
        try {
            int Days = (int) daysBetween(startDate, endDate);
            if (daysBetween(startDate, endDate) < 6) {
                if (Days == 0) {
                    long seconds = (endDate.getTimeInMillis() - startDate.getTimeInMillis()) / 1000;
                    int hours = (int) (seconds / 3600);
                    if (hours == 0) {
                        int minutes = (int) (seconds / 60);
                        if (minutes == 0) {
                            return "In " + seconds + " Secs";
                        }
                        return "In " + minutes + " Mins";
                    }
                    return "In " + hours + " Hours";
                }
                return "In " + Days + " Days";
            } else {
                startDate.set(Calendar.HOUR_OF_DAY, 0);
                startDate.set(Calendar.MINUTE, 0);
                startDate.set(Calendar.SECOND, 0);
                int start = (int) TimeUnit.MILLISECONDS.toDays(
                        startDate.getTimeInMillis())
                        - startDate.get(Calendar.DAY_OF_WEEK);
                int end = (int) TimeUnit.MILLISECONDS.toDays(
                        endDate.getTimeInMillis());
                Days = (end - start) / 7;
                return "In " + Days + " Weeks";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0";
    }

    public static String setDateInDefinedformat(String pPreferredDate) {
        StringBuilder tempDob= null;
        if(pPreferredDate !=null && !pPreferredDate.equalsIgnoreCase("null")){
            Date selectedDate = stringToDate(pPreferredDate, "yyyy-MM-dd");
            String month = getMonthInName(selectedDate);
            String date = getDateInNumber(selectedDate);
            String year = getYearInNumber(selectedDate);
            tempDob = new StringBuilder().append(date)
                    .append("-").append(month).append("-").append(year)
                    .append(" ");
        }else {
            tempDob = new StringBuilder().append("");
        }

        return tempDob.toString();
    }

    public static String getDateforAPI(String pDate) {
//        "DOB": "5/5/1999 12:00:00 AM"
//        "hh.mm a, dd MMM yyyy"
        SimpleDateFormat inFormat = new SimpleDateFormat("mm/dd/yyyy hh:mm:ss a",Locale.getDefault());
        Date dateforAPI = null;
        try {
           // Date d1 = inFormat.parse(pDate);
            SimpleDateFormat outFormat = new SimpleDateFormat("MM/DD/YYYY",Locale.getDefault());
            dateforAPI = outFormat.parse(pDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateforAPI.toString();
    }

    public static String getDateforAPI1(String pDate) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        Date date = null;
        String dateforAPI = "";
        try {
            date = sdf.parse(pDate);
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            dateforAPI = sdf.format(date);
            //Log.d("Raju", dateforAPI);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateforAPI;
    }
    public static String getMonthInName(Date selectedDate) {

        String month = "";
        try {
            SimpleDateFormat monthFormat = new SimpleDateFormat("MMM");
            if (monthFormat != null)
                month = monthFormat.format(selectedDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return month;
    }


    public static String getdob(Date selectedDate) {

        String month = "";
        try {
            SimpleDateFormat monthFormat = new SimpleDateFormat("DD/MM/YYYY");
            if (monthFormat != null)
                month = monthFormat.format(selectedDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return month;
    }

    public static String getYearInNumber(Date selectedDate) {

        SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy");
        String Year = monthFormat.format(selectedDate);

        return Year;
    }

    public static String getDateInNumber(Date selectedDate) {

        SimpleDateFormat monthFormat = new SimpleDateFormat("dd");
        String date = monthFormat.format(selectedDate);

        return date;
    }

    public static String getFormattedDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //2nd of march 2015
        int day = cal.get(DATE);

        if (!((day > 10) && (day < 19)))
            switch (day % 10) {
                case 1:
                    return new SimpleDateFormat("d'st' 'of' MMMM yyyy").format(date);
                case 2:
                    return new SimpleDateFormat("d'nd' 'of' MMMM yyyy").format(date);
                case 3:
                    return new SimpleDateFormat("d'rd' 'of' MMMM yyyy").format(date);
                default:
                    return new SimpleDateFormat("d'th' 'of' MMMM yyyy").format(date);
            }
        return new SimpleDateFormat("d'th' 'of' MMMM yyyy").format(date);
    }

    public static String getSpecifiedChars(String pSelectedInfo) {

        String strSel = new String(pSelectedInfo);
        char[] chSpecChars = new char[18];
        try {
            strSel.getChars(0, 17, chSpecChars, 0);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        String tmpValue = new String(chSpecChars);
        tmpValue += "..";

        return tmpValue;
    }

    public static int getDiffYears(Calendar a, Calendar b) {
        int diff = b.get(YEAR) - a.get(YEAR);
        if (a.get(MONTH) > b.get(MONTH) ||
                (a.get(MONTH) == b.get(MONTH) && a.get(DATE) > b.get(DATE))) {
            diff--;
        }
        return diff;
    }


    public static void getTimeZone() {
        TimeZone tz = TimeZone.getDefault();
        //Log.d("TimeZone   ", tz.getDisplayName(false, TimeZone.SHORT)); //output:GMT+05:30
        //Log.d("Timezon id    ", tz.getID()); // output: Asia/Kolkata

    }



    public static double CalculationByDistance(double initialLat, double initialLong,
                                               double hospitalLat, double hospitalLong) {
        double R = 6371.8; // km (Earth radius)
        double dLat = toRadians(hospitalLat - initialLat);
        double dLon = toRadians(hospitalLong - initialLong);
        initialLat = toRadians(initialLat);
        hospitalLat = toRadians(hospitalLat);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(initialLat) * Math.cos(hospitalLat);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }

    public static double toRadians(double deg) {
        return deg * (Math.PI / 180);
    }


    public static String arraytoString(Object[] a) {
        if (a == null)
            return "null";

        int iMax = a.length - 1;
        if (iMax == -1)
            return "[]";

        StringBuilder b = new StringBuilder();
        b.append('[');
        for (int i = 0; ; i++) {
            b.append("\"");
            b.append(String.valueOf(a[i]));
            b.append("\"");
            if (i == iMax)
                return b.append(']').toString();
            b.append(",");
        }
    }
}
