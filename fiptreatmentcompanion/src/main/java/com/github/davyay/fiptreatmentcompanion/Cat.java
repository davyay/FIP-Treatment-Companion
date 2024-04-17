package com.github.davyay.fiptreatmentcompanion;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Cat {
    // Fields
    private String name;
    private LocalDate dateOfBirth;
    private double weight;

    // Constructor
    public Cat(String name, String dob, double weight) {
        this.name = name;
        this.dateOfBirth = LocalDate.parse(dob, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        this.weight = weight;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    }

    public void setDateOfBirth(String dob) {
        this.dateOfBirth = LocalDate.parse(dob, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    // String Representation
    @Override
    public String toString() {
        return "Cat{" +
               "name='" + name + '\'' +
               ", dateOfBirth=" + dateOfBirth.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) +
               ", weight=" + weight +
               '}';
    }
}