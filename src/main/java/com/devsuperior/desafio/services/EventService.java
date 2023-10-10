package com.devsuperior.desafio.services;

import com.devsuperior.desafio.dto.EventDTO;
import com.devsuperior.desafio.entities.City;
import com.devsuperior.desafio.entities.Event;
import com.devsuperior.desafio.repositories.CityRepository;
import com.devsuperior.desafio.repositories.EventRepository;
import com.devsuperior.desafio.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private CityRepository cityRepository;

    public Page<EventDTO> findAll(Pageable pageable) {
        Page<Event> page = eventRepository.findAll(pageable);
        return page.map(EventDTO::new);
    }

    @Transactional
    public EventDTO insert(EventDTO dto) {
        Event entity = new Event();
        entity.setName(dto.getName());
        entity.setDate(dto.getDate());
        entity.setUrl(dto.getUrl());
        entity.setCity(cityRepository.getReferenceById(dto.getCityId()));
        entity = eventRepository.save(entity);
        return new EventDTO(entity);
    }

    public EventDTO update(Long id, EventDTO dto) {
        try {
            Event entity = eventRepository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = eventRepository.save(entity);
            return new EventDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado " + id);
        }
    }

    private void copyDtoToEntity(EventDTO dto, Event entity) {
        entity.setName(dto.getName());
        entity.setDate(dto.getDate());
        entity.setUrl(dto.getUrl());
        entity.setCity(new City(dto.getCityId(), null));
    }
}
