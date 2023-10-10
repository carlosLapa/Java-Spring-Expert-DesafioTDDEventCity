package com.devsuperior.desafio.repositories;

import com.devsuperior.desafio.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
