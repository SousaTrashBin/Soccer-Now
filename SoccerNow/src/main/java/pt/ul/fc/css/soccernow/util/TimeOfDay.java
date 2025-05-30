package pt.ul.fc.css.soccernow.util;

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

    public record TimeSpan(LocalTime start, LocalTime end) {
    }
}
