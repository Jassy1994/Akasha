package com.example.async;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jassy on 2017/3/1.
 * description:
 */
public class EventModel {
    private EventType eventType;
    private int actorId;
    private int entityOwnerId;
    private int entityType;
    private int entityId;

    private Map<String, String> exts = new HashMap<>();

    public int getActorId() {
        return actorId;
    }

    public EventModel setActorId(int actorId) {
        this.actorId = actorId;
        return this;
    }

    public EventType getEventType() {
        return eventType;
    }

    public EventModel setEventType(EventType eventType) {
        this.eventType = eventType;
        return this;
    }

    public int getEntityOwnerId() {
        return entityOwnerId;
    }

    public EventModel setEntityOwnerId(int entityOwnerId) {
        this.entityOwnerId = entityOwnerId;
        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public EventModel setEntityType(int entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public EventModel setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public Map<String, String> getExts() {
        return exts;
    }

    public String getExt(String key){
        return getExts().get(key);
    }

    public EventModel setExts(Map<String, String> exts) {
        this.exts = exts;
        return this;
    }

    public EventModel setExt(String key, String value) {
        exts.put(key, value);
        return this;
    }
}
