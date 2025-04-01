package com.example.eventos_api.domain.coupon;

import com.example.eventos_api.domain.event.Event;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Table(name = "coupon")
@Entity
@Setter
@Getter
@AllArgsConstructor
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
}
