package com.example.eventos_api.domain.address;

import com.example.eventos_api.domain.event.Event;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Table(name = "address")
@Entity
@Setter
@Getter
@AllArgsConstructor
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
}
