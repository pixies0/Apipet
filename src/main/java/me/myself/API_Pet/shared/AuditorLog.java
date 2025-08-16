package me.myself.API_Pet.shared;

import java.time.Instant;
import java.util.concurrent.ConcurrentLinkedQueue;

public enum AuditorLog {
    INSTANCE;

    private final ConcurrentLinkedQueue<String> events = new ConcurrentLinkedQueue<>();

    public void log(String who, String action, String details) {
        events.add("[%s] %s - %s :: %s".formatted(Instant.now(), who, action, details));
    }

    public ConcurrentLinkedQueue<String> getEvents() {
        return events;
    }
}
