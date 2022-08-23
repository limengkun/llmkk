package cn.ofpp.core;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;

import java.util.Calendar;
import java.util.Date;

import static cn.hutool.core.date.DateUtil.age;

/**
 * @author DokiYolo
 * Date 2022-08-22
 */
public class Friend {

    private final String fullName;

    private final Integer howOld;

    private final Integer sonHowOld;

    private final String sonBirthDay;

    private final String userId;

    private final String birthday;

    private final String loveTime;

    private final String sex;

    private final String templateId;

    public Friend(String fullName, String sonBirthDay, String userId, String birthday, String loveTime, String sex) {
        this(fullName, sonBirthDay, userId, birthday, loveTime, sex, null);
    }

    public Friend(String fullName, String sonBirthDay, String userId, String birthday, String loveTime, String sex, String templateId) {
        this.fullName = fullName;
        this.howOld = age(DateUtil.parse(birthday), new Date());
        this.sonHowOld = differentDays(DateUtil.parse(sonBirthDay), new Date());
        this.sonBirthDay = sonBirthDay;
        this.userId = userId;
        this.birthday = birthday;
        this.loveTime = loveTime;
        this.sex = sex;
        this.templateId = templateId;
    }

    public String getFullName() {
        return fullName;
    }

    public Integer getHowOld() {
        return howOld;
    }

    public Integer getSonHowOld() {
        return sonHowOld;
    }

    public String getSonBirthDay() {
        return sonBirthDay;
    }

    public String getUserId() {
        return userId;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getLoveTime() {
        return loveTime;
    }

    public String getSex() {
        return sex;
    }

    public String getTemplateId() {
        return templateId;
    }

    public String getHowLongLived() {
        return String.valueOf(DateUtil.between(new Date(), DateUtil.parse(birthday), DateUnit.DAY));
    }

    public String getNextBirthdayDays() {
        return getNextDay(DateUtil.parse(birthday));
    }

    public String getNextSonBirthdayDays() {
        return getNextDay(DateUtil.parse(sonBirthDay));
    }

    public String getNextMemorialDay() {
        return getNextDay(DateUtil.parse(loveTime));
    }

    public static String getNextDay(DateTime dateTime) {
        dateTime = DateUtil.beginOfDay(dateTime);
        DateTime now = DateUtil.beginOfDay(new Date());
        dateTime.offset(DateField.YEAR, now.year() - dateTime.year());
        if (now.isAfter(dateTime)) {
            return String.valueOf(dateTime.offset(DateField.YEAR, 1).between(now, DateUnit.DAY));
        }
        return String.valueOf(dateTime.between(now, DateUnit.DAY));
    }


    private static int differentDays(Date date1,Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2) {//同一年
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)    //闰年
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2-day1) ;
        } else {// 不同年
            System.out.println("判断day2 - day1 : " + (day2-day1));
            return day2-day1;
        }
    }

}
