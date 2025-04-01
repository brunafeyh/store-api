package com.example.eventos_api.domain.service;

import com.example.eventos_api.domain.event.Event;
import com.example.eventos_api.domain.event.EventRequestDTO;
import com.example.eventos_api.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EventService {
    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(UUID id) {
        return eventRepository.findById(id).orElse(null);
    }

    public Event createEvent(EventRequestDTO eventRequestDTO) {
        Date eventDate = new Date(eventRequestDTO.date());
        Event newEvent = new Event();
        newEvent.setTitle(eventRequestDTO.title());
        newEvent.setDescription(eventRequestDTO.description());
        newEvent.setImgUrl(eventRequestDTO.image());
        newEvent.setEventUrl(eventRequestDTO.eventUrl());
        newEvent.setRemote(eventRequestDTO.remote());
        newEvent.setDate(eventDate);

        return eventRepository.save(newEvent);
    }

    public Event updateEvent(UUID id, EventRequestDTO eventRequestDTO) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isPresent()) {
            Event eventToUpdate = optionalEvent.get();
            eventToUpdate.setTitle(eventRequestDTO.title());
            eventToUpdate.setDescription(eventRequestDTO.description());
            eventToUpdate.setImgUrl(eventRequestDTO.image());
            eventToUpdate.setEventUrl(eventRequestDTO.eventUrl());
            eventToUpdate.setRemote(eventRequestDTO.remote());
            eventToUpdate.setDate(new Date(eventRequestDTO.date()));
            return eventRepository.save(eventToUpdate);
        }
        return null;
    }

    public void deleteEvent(UUID id) {
        eventRepository.deleteById(id);
    }
}
