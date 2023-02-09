package ru.chichaev.spring.SensorRest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.chichaev.spring.SensorRest.models.Measurement;

import java.util.List;

@Repository
public interface MeasurmentRepository extends JpaRepository<Measurement, Integer> {
    public List<Measurement> findAllByRainingIsTrue();
}
