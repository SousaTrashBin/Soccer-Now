package pt.ul.fc.css.soccernow.util;

import java.time.LocalDateTime;
import java.time.LocalTime;

public enum TimeOfDay {
    MORNING, AFTERNOON, EVENING;

    public static TimeSpan toTimeSpan(TimeOfDay timeOfDay) {
        return switch (timeOfDay) {
            case MORNING -> new TimeSpan(LocalTime.of(6, 0), LocalTime.of(12, 0));
            case AFTERNOON -> new TimeSpan(LocalTime.of(12, 0), LocalTime.of(18, 0));
            case EVENING -> new TimeSpan(LocalTime.of(18, 0), LocalTime.of(23, 59));
        };
    }

    public static TimeOfDay fromDateTime(LocalDateTime dateTime) {
        LocalTime time = dateTime.toLocalTime();

        if (!time.isBefore(LocalTime.of(6, 0)) && time.isBefore(LocalTime.of(12, 0))) {
            return MORNING;
        } else if (!time.isBefore(LocalTime.of(12, 0)) && time.isBefore(LocalTime.of(18, 0))) {
            return AFTERNOON;
        } else if (!time.isBefore(LocalTime.of(18, 0)) && !time.isAfter(LocalTime.of(23, 59))) {
            return EVENING;
        } else {
            throw new IllegalArgumentException("Unsupported time outside 06:00-23:59: " + time);
        }
    }

    public record TimeSpan(LocalTime start, LocalTime end) {
    }
}
