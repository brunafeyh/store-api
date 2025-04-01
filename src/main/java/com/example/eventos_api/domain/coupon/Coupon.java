package com.example.eventos_api.domain.coupon;

import com.example.eventos_api.domain.event.Event;
import jakarta.persistence.*;
import java.util.Date;
import java.util.UUID;

@Table(name = "coupon")
@Entity
public class Coupon {
    @Id
    @GeneratedValue
    private UUID uuid;
    private Integer discount;
    private Date valid;

    @ManyToOne
    @JoinColumn(name = "eventId")
    private Event event;

    public Coupon() {
    }

    public Coupon(UUID uuid, Integer discount, Date valid, Event event) {
        this.uuid = uuid;
        this.discount = discount;
        this.valid = valid;
        this.event = event;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Date getValid() {
        return valid;
    }

    public void setValid(Date valid) {
        this.valid = valid;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}