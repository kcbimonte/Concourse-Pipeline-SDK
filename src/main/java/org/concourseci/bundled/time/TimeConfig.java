package org.concourseci.bundled.time;

import com.google.gson.annotations.SerializedName;
import org.concourseci.sdk.resource.IResourceConfig;
import org.concourseci.sdk.util.Validator;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.*;

public class TimeConfig implements IResourceConfig {
    private String interval;
    private String location;

    private String start;
    private String end;

    private final List<String> days = new ArrayList<>();

    @SerializedName("initial_version")
    private Boolean initialVersion;

    public TimeConfig setInterval(String interval) {
        Validator.validateDuration(interval);
        this.interval = interval;

        return this;
    }

    public TimeConfig setLocation(String location) {
        if(!isValidTimezone(location)) throw new RuntimeException("Not a valid timezone");

        this.location = location;

        return this;
    }

    public TimeConfig setStartAndEnd(String start, String end) {
        this.start = start;
        this.end = end;

        return this;
    }

    public TimeConfig addDay(DayOfWeek dayOfWeek) {
        this.days.add(dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH));

        return this;
    }

    public TimeConfig emitInitialVersion() {
        this.initialVersion = true;

        return this;
    }

    private boolean isValidTimezone(String timezone) {
        return Set.of(TimeZone.getAvailableIDs()).contains(timezone);
    }
}
