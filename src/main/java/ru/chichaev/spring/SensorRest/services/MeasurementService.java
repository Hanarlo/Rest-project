package ru.chichaev.spring.SensorRest.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.chichaev.spring.SensorRest.DTO.MeasurementDTO;
import ru.chichaev.spring.SensorRest.models.Measurement;
import ru.chichaev.spring.SensorRest.repositories.MeasurmentRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class MeasurementService {

    private final MeasurmentRepository measurmentRepository;
    private final ModelMapper modelMapper;

    public MeasurementService(MeasurmentRepository measurmentRepository, ModelMapper modelMapper) {
        this.measurmentRepository = measurmentRepository;
        this.modelMapper = modelMapper;
    }

    public void save(Measurement measurement){
        measurmentRepository.save(measurement);
    }

    public List<MeasurementDTO> getAllMeasurementsAsMeasurementsDTO(){
        List<Measurement> measurements = measurmentRepository.findAll();
        ArrayList<MeasurementDTO> measurementDTOList = new ArrayList<>();
        for (Measurement m : measurements){
            measurementDTOList.add(modelMapper.map(m, MeasurementDTO.class));
        }
        return measurementDTOList;
    }

    public List<Measurement> getAllMeasurementsWithRain(){
        return measurmentRepository.findAllByRainingIsTrue();
    }


}
