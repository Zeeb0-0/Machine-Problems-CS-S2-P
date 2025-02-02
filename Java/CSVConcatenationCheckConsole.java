import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVConcatenationCheckConsole {
    public static void main(String[] args) {
        String csvFile = "machine_problems_dataset.csv";
        String line;
        String csvSplitBy = ",";

        // ANSI escape codes for colored output
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_BLUE = "\u001B[34m";

        // Header output
        System.out.println(ANSI_BLUE + "CSV Concatenation Check" + ANSI_RESET);
        System.out.println("Concatenating a string with the sum of two numbers and checking if the result exceeds 10 characters");
        System.out.println("---------------------------------------------------------");

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Skip the header row (Columns: Number1, Number2, String, Boolean)
            String header = br.readLine();
            if (header == null) {
                System.out.println(ANSI_RED + "CSV file is empty." + ANSI_RESET);
                return;
            }
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;  // Skip empty lines

                // Simple CSV parsing: split by commas
                String[] columns = line.split(csvSplitBy);
                if (columns.length < 3) continue; // Ensure there are at least three columns

                // Parse numbers and the text
                double number1 = Double.parseDouble(columns[0].trim());
                double number2 = Double.parseDouble(columns[1].trim());
                String text = columns[2].trim();
                
                // Compute the sum and create the concatenated result
                double sum = number1 + number2;
                String resultStr = text + " " + sum;
                boolean exceeds = resultStr.length() > 10;
                
                // Build the output string with color coding for status
                String status = exceeds ? ANSI_GREEN + "exceeding" + ANSI_RESET : ANSI_RED + "not exceeding" + ANSI_RESET;
                System.out.println("Result: '" + resultStr + "' is " + status + " 10 characters.");
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println(ANSI_RED + "Error reading CSV file: " + e.getMessage() + ANSI_RESET);
        }
    }
}
