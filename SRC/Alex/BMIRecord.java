package Alex;

public class BMIRecord {
    private double heightCm;
    private double weightKg;
    private double bmiValue;

    public BMIRecord(double heightCm, double weightKg, double bmiValue) {
        this.heightCm = heightCm;
        this.weightKg = weightKg;
        this.bmiValue = bmiValue;
    }

    public double getHeightCm() {
        return heightCm;
    }

    public double getWeightKg() {
        return weightKg;
    }

    public double getBmiValue() {
        return bmiValue;
    }

    @Override
    public String toString() {
        return String.format("Height: %.1f cm, Weight: %.1f kg, BMI: %.2f",
                heightCm, weightKg, bmiValue);
    }
}
