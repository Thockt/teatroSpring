package com.example.teatroSpring.repositories;

import com.example.teatroSpring.entities.Biglietto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BigliettoRepository extends JpaRepository<Biglietto, Long> {
}
