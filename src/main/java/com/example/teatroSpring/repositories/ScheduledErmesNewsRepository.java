package com.example.teatroSpring.repositories;

import com.example.teatroSpring.entities.ScheduledErmesNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduledErmesNewsRepository  extends JpaRepository<ScheduledErmesNews, Long> {
}
