package ru.chichaev.spring.SensorRest.DTO;

import org.hibernate.validator.constraints.Length;

public class SensorDTO {
    @Length(min = 3, max = 30, message = "Length of the name should be 3-30 characters!")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SensorDTO(String name) {
        this.name = name;
    }

    public SensorDTO() {
    }
}
