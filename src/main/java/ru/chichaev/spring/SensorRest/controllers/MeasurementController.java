package ru.chichaev.spring.SensorRest.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.chichaev.spring.SensorRest.DTO.MeasurementDTO;
import ru.chichaev.spring.SensorRest.Util.MeasurementErrorResponse;
import ru.chichaev.spring.SensorRest.Util.MeasurementNotCreatedException;
import ru.chichaev.spring.SensorRest.models.Measurement;
import ru.chichaev.spring.SensorRest.models.Sensor;
import ru.chichaev.spring.SensorRest.services.MeasurementService;
import ru.chichaev.spring.SensorRest.services.SensorService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {

    private final ModelMapper modelMapper;
    private final MeasurementService measurementService;
    private final SensorService sensorService;
    private final String SENSOR_IS_NOT_EXISTS = "Sensor with this name is not exists!";


    public MeasurementController(ModelMapper modelMapper, MeasurementService measurementService, SensorService sensorService) {
        this.modelMapper = modelMapper;
        this.measurementService = measurementService;
        this.sensorService = sensorService;
    }


    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMeasure(@RequestBody @Valid MeasurementDTO measurementDTO,
                                                 BindingResult bindingResult){
            if (bindingResult.hasErrors()){
                StringBuilder errorMsg = new StringBuilder();
                List<FieldError> fieldErrorsList = bindingResult.getFieldErrors();
                for (FieldError f : fieldErrorsList){
                    errorMsg.append(f.getField()).append(" - ").append(f.getDefaultMessage());
                }
                throw new MeasurementNotCreatedException(errorMsg.toString());
            }
            Measurement measurement = modelMapper.map(measurementDTO, Measurement.class);
            Optional<Sensor> OptionalSensor = sensorService.getSensorFromDB(measurement);
            if (OptionalSensor.isPresent()){
                Sensor sensor = OptionalSensor.get();
                measurement.setSensor(sensor);
            } else {
                throw new MeasurementNotCreatedException(SENSOR_IS_NOT_EXISTS);
            }
            measurementService.save(measurement);
            return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public List<MeasurementDTO> getAllMeasurements(){
        return measurementService.getAllMeasurementsAsMeasurementsDTO();
    }

    @GetMapping("/rainyDaysCount")
    public int countRainyDays(){
        return measurementService.getAllMeasurementsWithRain().size();
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementNotCreatedException measurementNotCreatedException){
        MeasurementErrorResponse response = new MeasurementErrorResponse(measurementNotCreatedException.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    }



}
