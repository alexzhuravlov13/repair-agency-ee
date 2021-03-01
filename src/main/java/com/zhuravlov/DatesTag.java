package com.zhuravlov;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Class for formatting LocalDateTime in jsp
 * @author alexzhuravlov13
 */

public final class DatesTag {
    private DatesTag() {}

    /**
     * Format LocalDateTime
     * @param localDateTime date to format
     * @param pattern pattern of formatting
     * @return String in specified by parameter format
     */
    public static String formatLocalDateTime(LocalDateTime localDateTime, String pattern) {
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }
}