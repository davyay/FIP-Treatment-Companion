package com.github.davyay.fiptreatmentcompanion;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WeightTracker {
    private List<WeightRecord> weightRecords;

    public WeightTracker() {
        this.weightRecords = new ArrayList<>();
    }

    public void addWeightRecord(double weight, LocalDate date) {
        weightRecords.add(new WeightRecord(date, weight));
    }

    public List<WeightRecord> getWeightRecords() {
        return new ArrayList<>(weightRecords); // Return a copy to avoid external modifications.
    }

    public double getMostRecentWeight() {
        if (!weightRecords.isEmpty()) {
            return weightRecords.get(weightRecords.size() - 1).getWeight();
        }
        throw new IllegalStateException("No weight records available.");
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (WeightRecord record : weightRecords) {
            sb.append(record).append("\n");
        }
        return sb.toString();
    }

    // Inner class to store individual weight records
    public static class WeightRecord {
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate date;

        private double weight;

        public WeightRecord() {
        }

        public WeightRecord(LocalDate date, double weight) {
            this.date = date;
            this.weight = weight;
        }

        @JsonProperty("weightdate")
        public LocalDate getDate() {
            return date;
        }

        @JsonProperty("weight")
        public double getWeight() {
            return weight;
        }

        @Override
        public String toString() {
            return "Date: " + date + ", Weight: " + weight + " kg";
        }
    }
}
