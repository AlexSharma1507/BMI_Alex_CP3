package Alex;

import java.util.ArrayList;
import java.util.List;

public class BMIRecordManager {

    private final ArrayList<BMIRecord> records;

    public BMIRecordManager() {
        records = new ArrayList<>();
    }

    public void addRecord(BMIRecord record) {
        records.add(record);
    }

    public List<BMIRecord> getAllRecords() {
        return records;
    }

    public BMIRecord findByExactBmi(double bmi) {
        for (BMIRecord r : records) {
            if (Math.abs(r.getBmiValue() - bmi) < 0.01) {
                return r;
            }
        }
        return null;
    }

    public boolean deleteRecordAtIndex(int index) {
        if (index >= 0 && index < records.size()) {
            records.remove(index);
            return true;
        }
        return false;
    }

    public int size() {
        return records.size();
    }
}
