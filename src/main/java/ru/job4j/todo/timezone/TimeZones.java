package ru.job4j.todo.timezone;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.*;

@Data
@Component
public class TimeZones {

    private List<String> zones;

    public TimeZones() {
        this.zones = new ArrayList<>();
        load();
    }

    public void load() {
        Arrays.stream(TimeZone.getAvailableIDs()).distinct()
                .forEach(id -> zones.add(id));
    }
}
