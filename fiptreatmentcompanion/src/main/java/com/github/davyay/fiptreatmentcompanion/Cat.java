package com.github.davyay.fiptreatmentcompanion;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import com.fasterxml.jackson.annotation.JsonFormat;


public class Cat {
    // Fields
    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private LocalDate dateOfBirth;
    private WeightTracker weightTracker;  // Handles weight tracking
    private Treatment treatment;  // Handles treatment records
    
    public Cat() {
    }
    
    // Constructor
    public Cat(String name, String dob) {        this.name = name;
        this.dateOfBirth = LocalDate.parse(dob, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        this.weightTracker = new WeightTracker();  // Initialize the weight tracker
        this.treatment = new Treatment(this);  // Initialize the treatment object
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

    public WeightTracker getWeightTracker() {
        return weightTracker;
    }

    public Treatment getTreatment() {
        return treatment;
    }

    // Method to add a weight record through the Cat class
    public void addWeightRecord(double weight, LocalDate date) {
        this.weightTracker.addWeightRecord(weight, date);
    }

    // String Representation
    @Override
    public String toString() {
        return "Cat{" +
               "name='" + name + '\'' +
               ", dateOfBirth=" + dateOfBirth.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) +
               ", weightTracker=" + weightTracker +
               ", treatment=" + treatment +
               '}';
    }
}
