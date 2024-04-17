import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Cat {
    // Fields
    private String name;
    private LocalDate dateOfBirth;
    private double weight;
    private int totalDosesRequired;
    private String timeForDose;
    private Map<LocalDate, Boolean> doseStatus; // Map to track dose status for each day

    public Cat(String name, String dob, double weight, int totalDosesRequired, String timeForDose) {
        this.name = name;
        this.dateOfBirth = LocalDate.parse(dob, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        this.weight = weight;
        this.totalDosesRequired = totalDosesRequired;
        this.timeForDose = timeForDose;
        this.doseStatus = new HashMap<>();
    }


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

    public int getTotalDosesRequired() {
        return totalDosesRequired;
    }

    public void setTotalDosesRequired(int totalDosesRequired) {
        this.totalDosesRequired = totalDosesRequired;
    }

    public String getTimeForDose() {
        return timeForDose;
    }

    public void setTimeForDose(String timeForDose) {
        this.timeForDose = timeForDose;
    }

    public void markDoseGiven() {
        LocalDate today = LocalDate.now();
        doseStatus.put(today, true);
    }

    public boolean isDoseGivenToday() {
        LocalDate today = LocalDate.now();
        return doseStatus.getOrDefault(today, false);
    }

    public void updateTimeForDose(String newTime) {
        this.timeForDose = newTime;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) +
                ", weight=" + weight +
                ", totalDosesRequired=" + totalDosesRequired +
                ", timeForDose='" + timeForDose + '\'' +
                ", doseStatus=" + doseStatus +
                '}';
    }
}
