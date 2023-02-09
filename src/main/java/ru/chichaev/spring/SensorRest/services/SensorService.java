package ru.chichaev.spring.SensorRest.services;

import org.springframework.stereotype.Service;
import ru.chichaev.spring.SensorRest.models.Measurement;
import ru.chichaev.spring.SensorRest.models.Sensor;
import ru.chichaev.spring.SensorRest.repositories.SensorRepository;

import java.util.Optional;

@Service
public class SensorService {

    private final SensorRepository sensorRepository;

    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public boolean isExists(Sensor sensor){
        return sensorRepository.findByName(sensor.getName()).isPresent();
    }

    public void save(Sensor sensor){
        sensorRepository.save(sensor);
    }

    public Optional<Sensor> getSensorFromDB(Measurement measurement){
        return sensorRepository.findByName(measurement.getSensor().getName());
    }
}
