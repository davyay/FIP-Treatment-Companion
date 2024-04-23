package com.github.davyay.fiptreatmentcompanion;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ExportManager {

    // Method to write cat details to a text file
    public void writeCatDetailsToFile(Cat cat, String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("Name of the Cat: " + cat.getName());
            writer.newLine();
            writer.write("Date of Birth: " + cat.getDateOfBirth());
            writer.newLine();
            writer.write("Weight Records:");
            writer.newLine();
            
            // Iterate over weight records
            for (WeightTracker.WeightRecord record : cat.getWeightTracker().getWeightRecords()) {
                writer.write("Date: " + record.getDate() + ", Weight: " + record.getWeight() + " kg");
                writer.newLine();
            }
            
            writer.write("Treatment Records:");
            writer.newLine();
            
            // Iterate over treatment records
            for (Treatment.TreatmentRecord record : cat.getTreatment().getTreatmentRecords()) {
                writer.write("Date/Time: " + record.getTime() +
                             ", Dose: " + record.getDosage() + "ml" +
                             ", Medication: " + record.getMedicationName());
                writer.newLine();
            }
        }
    }

}
