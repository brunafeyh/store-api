package com.example.eventos_api.domain.address;

import com.example.eventos_api.domain.event.Event;
import jakarta.persistence.*;
import java.util.UUID;

@Table(name = "address")
@Entity
public class Address {
    @Id
    @GeneratedValue
    private UUID uuid;

    private String city;
    private String uf;

    @ManyToOne
    @JoinColumn(name = "eventId")
    private Event event;

    public Address() {
    }

    public Address(UUID uuid, String city, String uf, Event event) {
        this.uuid = uuid;
        this.city = city;
        this.uf = uf;
        this.event = event;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
