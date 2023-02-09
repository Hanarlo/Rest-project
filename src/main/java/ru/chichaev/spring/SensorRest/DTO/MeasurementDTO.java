package ru.chichaev.spring.SensorRest.DTO;

import ru.chichaev.spring.SensorRest.models.Sensor;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class MeasurementDTO {

    private Sensor sensor;

    @NotNull
    @Min(value = -100, message = "Min temperature -100")
    @Max(value = 100, message = "Max temperature 100")
    private float value;

    @NotNull
    private Boolean raining;

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public Boolean getRaining() {
        return raining;
    }

    public void setRaining(Boolean raining) {
        this.raining = raining;
    }

    public MeasurementDTO(Sensor sensor, float value, Boolean raining) {
        this.sensor = sensor;
        this.value = value;
        this.raining = raining;
    }

    public MeasurementDTO() {
    }
}
