import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVConcatenationCheck {
    public static void main(String[] args) {
        // Define the path to your CSV file
        String csvFilePath = "C:\\Users\\TestUser\\OneDrive\\Desktop\\School\\BSCS\\1st Year\\Machine-Problems-CS-S2-P\\Java\\machine_problems_dataset.csv";

        // Header output
        System.out.println("CSV Concatenation Check");
        System.out.println("Concatenating a string with the sum of two numbers and checking if it exceeds 10 characters");
        System.out.println("------------------------------------------------------------------");

        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                // Skip the header row
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                // Skip empty lines
                if (line.trim().isEmpty()) {
                    continue;
                }

                // Process the CSV line
                processLine(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
        }
    }

    /**
     * Processes a single CSV line: parses numbers, concatenates with text, and checks length.
     */
    private static void processLine(String line) {
        String[] columns = line.split(",");

        // Ensure there are at least three columns (Number1, Number2, String)
        if (columns.length < 3) {
            System.out.println("Skipping line (insufficient columns): " + line);
            return;
        }

        try {
            double number1 = Double.parseDouble(columns[0].trim());
            double number2 = Double.parseDouble(columns[1].trim());
            String text = columns[2].trim();

            double sum = number1 + number2;
            String resultStr = text + " " + sum;
            boolean exceeds = resultStr.length() > 10;

            String status = exceeds ? "exceeding" : "not exceeding";
            System.out.println("Result: '" + resultStr + "' is " + status + " 10 characters.");
        } catch (NumberFormatException e) {
            System.out.println("Error parsing number in line: " + line);
        }
    }
}
