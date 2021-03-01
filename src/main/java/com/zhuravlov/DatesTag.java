package com.zhuravlov;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DatesTag {
    private DatesTag() {}

    public static String formatLocalDateTime(LocalDateTime localDateTime, String pattern) {
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }
}