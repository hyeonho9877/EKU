package com.eku.EKU.utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RelativeTimeConverter {
    public static String convertToRelativeTime(String writtenTime){
        LocalDateTime parse = LocalDateTime.parse(writtenTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(parse, now);
        if (duration.toDays() >= 3)
            return parse.getYear() + "/" + parse.getMonthValue() + "/" + parse.getDayOfMonth();
        else if (duration.toDays() == 2) return "이틀 전";
        else if (duration.toDays() == 1) return "하루 전";
        else if (duration.toMinutes() >= 60) return duration.toHours() + "시간 전";
        else if (duration.toSeconds() >= 60) return duration.toMinutes() + "분 전";
        else return duration.toSeconds() + "초 전";
    }
}
