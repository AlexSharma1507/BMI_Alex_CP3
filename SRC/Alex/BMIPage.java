package Alex;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BMIPage extends JFrame {

    // Shared manager for all BMI records in this screen
    private static final BMIRecordManager manager = new BMIRecordManager();

    // GUI fields so we can read/update them inside listeners
    private JTextField heightField;
    private JTextField weightField;
    private JLabel resultLabel;

    public BMIPage() {
        setTitle("BMI Calculator");
        setSize(450, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        // 9 rows because we now have extra buttons
        card.setLayout(new GridLayout(9, 1, 10, 10));
        
// ---- IMAGE ROW ----
ImageIcon original = new ImageIcon(getClass().getResource("/Alex/BMIchart.jpg"));
Image scaled = original.getImage().getScaledInstance(150, 100, Image.SCALE_SMOOTH);
JLabel imgLabel = new JLabel(new ImageIcon(scaled), SwingConstants.CENTER);
card.add(imgLabel);

        // Title
        JLabel title = new JLabel("BMI Calculator", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        card.add(title);

        // Height row
        JPanel heightRow = new JPanel(new BorderLayout(10, 0));
        heightRow.setBackground(Color.WHITE);
        JLabel heightLabel = new JLabel("Height (cm): ");
        heightField = new JTextField();
        heightRow.add(heightLabel, BorderLayout.WEST);
        heightRow.add(heightField, BorderLayout.CENTER);
        card.add(heightRow);

        // Weight row
        JPanel weightRow = new JPanel(new BorderLayout(10, 0));
        weightRow.setBackground(Color.WHITE);
        JLabel weightLabel = new JLabel("Weight (kg): ");
        weightField = new JTextField();
        weightRow.add(weightLabel, BorderLayout.WEST);
        weightRow.add(weightField, BorderLayout.CENTER);
        card.add(weightRow);

        // Calculate button
        JButton calcButton = new JButton("Calculate BMI");
        calcButton.addActionListener(e -> calculateAndSaveBmi());
        card.add(calcButton);

        // Result label
        resultLabel = new JLabel("Enter your details and click Calculate.", SwingConstants.CENTER);
        resultLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        card.add(resultLabel);

        // --- New Buttons for Checkpoint 3 ---

        // View History
        JButton viewHistoryBtn = new JButton("View BMI History");
        viewHistoryBtn.addActionListener(e -> showHistory());
        card.add(viewHistoryBtn);

        // Search BMI
        JButton searchBtn = new JButton("Search BMI");
        searchBtn.addActionListener(e -> searchRecord());
        card.add(searchBtn);

        // Delete Record
        JButton deleteBtn = new JButton("Delete Record");
        deleteBtn.addActionListener(e -> deleteRecord());
        card.add(deleteBtn);

        add(card, BorderLayout.CENTER);
    }

    // ================== Logic Methods ==================

    private void calculateAndSaveBmi() {
        try {
            double hCm = Double.parseDouble(heightField.getText().trim());
            double wKg = Double.parseDouble(weightField.getText().trim());

            if (hCm <= 0 || wKg <= 0) {
                resultLabel.setText("Height and weight must be greater than 0.");
                return;
            }

            double hMeters = hCm / 100.0;
            double bmi = wKg / (hMeters * hMeters);

            resultLabel.setText(String.format("Your BMI: %.2f", bmi));

            // Save record in ArrayList via manager
            BMIRecord record = new BMIRecord(hCm, wKg, bmi);
            manager.addRecord(record);

        } catch (NumberFormatException ex) {
            resultLabel.setText("Please enter valid numbers for height and weight.");
        }
    }

    private void showHistory() {
        if (manager.size() == 0) {
            JOptionPane.showMessageDialog(this,
                    "No BMI records saved yet.",
                    "History",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder sb = new StringBuilder("Saved BMI Records:\n\n");
        List<BMIRecord> all = manager.getAllRecords();
        for (int i = 0; i < all.size(); i++) {
            sb.append(i)
              .append(": ")
              .append(all.get(i).toString())
              .append("\n");
        }

        JOptionPane.showMessageDialog(this,
                sb.toString(),
                "History",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void searchRecord() {
        if (manager.size() == 0) {
            JOptionPane.showMessageDialog(this,
                    "No records available to search.",
                    "Search BMI",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String input = JOptionPane.showInputDialog(this,
                "Enter BMI value to search (e.g. 22.5):",
                "Search BMI",
                JOptionPane.QUESTION_MESSAGE);

        if (input == null) {
            return; // user cancelled
        }

        try {
            double bmiValue = Double.parseDouble(input.trim());
            BMIRecord found = manager.findByExactBmi(bmiValue);

            if (found != null) {
                JOptionPane.showMessageDialog(this,
                        "Record found:\n" + found.toString(),
                        "Search Result",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "No record with that exact BMI.",
                        "Search Result",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a valid number, for example 23.4",
                    "Search BMI",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteRecord() {
        if (manager.size() == 0) {
            JOptionPane.showMessageDialog(this,
                    "No records to delete.",
                    "Delete Record",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String input = JOptionPane.showInputDialog(this,
                "Enter index to delete (0 to " + (manager.size() - 1) + "):",
                "Delete Record",
                JOptionPane.QUESTION_MESSAGE);

        if (input == null) {
            return;
        }

        try {
            int idx = Integer.parseInt(input.trim());
            boolean success = manager.deleteRecordAtIndex(idx);

            if (success) {
                JOptionPane.showMessageDialog(this,
                        "Record deleted.",
                        "Delete Record",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Invalid index.",
                        "Delete Record",
                        JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a whole number.",
                    "Delete Record",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
}
