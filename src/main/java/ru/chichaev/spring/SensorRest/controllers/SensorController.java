package ru.chichaev.spring.SensorRest.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.chichaev.spring.SensorRest.DTO.SensorDTO;
import ru.chichaev.spring.SensorRest.Util.SensorErrorResponse;
import ru.chichaev.spring.SensorRest.Util.SensorNotCreatedException;
import ru.chichaev.spring.SensorRest.Validator.SensorValidator;
import ru.chichaev.spring.SensorRest.models.Sensor;
import ru.chichaev.spring.SensorRest.services.SensorService;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/sensors")
public class SensorController {

    private final SensorValidator sensorValidator;
    private final ModelMapper modelMapper;
    private final SensorService sensorService;

    public SensorController(SensorValidator sensorValidator, ModelMapper modelMapper, SensorService sensorService) {
        this.sensorValidator = sensorValidator;
        this.modelMapper = modelMapper;
        this.sensorService = sensorService;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid  SensorDTO sensorDTO, BindingResult errors){
        Sensor sensor = modelMapper.map(sensorDTO, Sensor.class);
        sensorValidator.validate(sensor, errors);
        if (errors.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errorsList = errors.getFieldErrors();
            for (FieldError e : errorsList){
                errorMsg.append(e.getField()).append(" - ").append(e.getDefaultMessage()).append("\n");
            }
            throw new SensorNotCreatedException(errorMsg.toString());
        }
        sensorService.save(sensor);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotCreatedException sensorNotCreatedException){
        SensorErrorResponse response = new SensorErrorResponse(sensorNotCreatedException.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    }
}
