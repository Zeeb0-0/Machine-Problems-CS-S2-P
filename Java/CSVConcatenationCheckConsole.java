import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVConcatenationCheckConsole {
    public static void main(String[] args) {
        // Print the working directory to verify file location
        System.out.println("Working Directory: " + System.getProperty("user.dir"));
        
        String csvFile = "machine_problems_dataset.csv";
        String csvSplitBy = ",";

        // ANSI escape codes for colored console output
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_BLUE = "\u001B[34m";
        
        // Header output
        System.out.println(ANSI_BLUE + "CSV Concatenation Check" + ANSI_RESET);
        System.out.println("Concatenating a string with the sum of two numbers and checking if it exceeds 10 characters");
        System.out.println("------------------------------------------------------------------");
        
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Read and skip the header row (expected format: Number1,Number2,String,Boolean)
            String header = br.readLine();
            if (header == null) {
                System.out.println(ANSI_RED + "CSV file is empty." + ANSI_RESET);
                return;
            }
            
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;  // Skip empty lines
                }
                processLine(line, csvSplitBy, ANSI_GREEN, ANSI_RED);
            }
        } catch (IOException e) {
            System.out.println(ANSI_RED + "Error reading CSV file: " + e.getMessage() + ANSI_RESET);
        }
    }

    /**
     * Process a single CSV line: parse, compute sum and concatenate, then print result.
     */
    private static void processLine(String line, String delimiter, String ANSI_GREEN, String ANSI_RED) {
        // Split the line by the provided delimiter.
        String[] columns = line.split(delimiter);
        
        // Ensure there are at least three columns (Number1, Number2, String)
        if (columns.length < 3) {
            System.out.println(ANSI_RED + "Skipping line (insufficient columns): " + line + ANSI_RED);
            return;
        }
        
        try {
            // Parse the first two columns as numbers and the third column as a text string
            double number1 = Double.parseDouble(columns[0].trim());
            double number2 = Double.parseDouble(columns[1].trim());
            String text = columns[2].trim();
            
            // Compute the sum and build the result string (added a space for clarity)
            double sum = number1 + number2;
            String resultStr = text + " " + sum;
            
            // Check if the concatenated result's length exceeds 10 characters
            boolean exceeds = resultStr.length() > 10;
            String status = exceeds ? ANSI_GREEN + "exceeding" + "\u001B[0m" : ANSI_RED + "not exceeding" + "\u001B[0m";
            
            System.out.println("Result: '" + resultStr + "' is " + status + " 10 characters.");
        } catch (NumberFormatException e) {
            System.out.println(ANSI_RED + "Error parsing number in line: " + line + ANSI_RED);
        }
    }
}
