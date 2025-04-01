package com.example.eventos_api.domain.event;

public record EventRequestDTO (String title,
                               String description,
                               Long date,
                               String city,
                               String state,
                               Boolean remote,
                               String eventUrl,
                               String image ) {
}
