package org.example.demo.repository;

import org.example.demo.entity.Tartalom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TartalomRepository extends JpaRepository<Tartalom, Integer> {
}
