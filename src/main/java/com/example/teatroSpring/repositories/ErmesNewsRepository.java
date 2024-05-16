package com.example.teatroSpring.repositories;

import com.example.teatroSpring.entities.ErmesNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErmesNewsRepository extends JpaRepository<ErmesNews, Long> {
}
