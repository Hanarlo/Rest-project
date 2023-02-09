package ru.chichaev.spring.SensorRest.Validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.chichaev.spring.SensorRest.models.Sensor;
import ru.chichaev.spring.SensorRest.services.SensorService;

@Component
public class SensorValidator implements Validator {

    private final SensorService sensorService;

    public SensorValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(Sensor.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        if (sensorService.isExists((Sensor) o)){
            errors.rejectValue("name", "", "Sensor with this name is already exists!");
        }
    }
}
