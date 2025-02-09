import javax.swing.*;
import java.awt.*;
import java.io.*;

public class CSVConcatenationCheckGUI extends JFrame {
    private JTextArea outputTextArea;
    private JButton selectFileButton;

    public CSVConcatenationCheckGUI() {
        super("CSV Concatenation Check");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Create UI components
        selectFileButton = new JButton("Select CSV File");
        outputTextArea = new JTextArea();
        outputTextArea.setEditable(false);
        outputTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(outputTextArea);

        // When the button is clicked, open a file chooser
        selectFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showOpenDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                File csvFile = fileChooser.getSelectedFile();
                processCSVFile(csvFile);
            }
        });

        // Layout the components in the frame
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(selectFileButton, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        add(panel);
    }

    /**
     * Reads and processes the CSV file, displaying results in the text area.
     */
    private void processCSVFile(File csvFile) {
        // Clear previous output
        outputTextArea.setText("");
        appendOutput("CSV Concatenation Check\n");
        appendOutput("Concatenating a string with the sum of two numbers and checking if it exceeds 10 characters\n");
        appendOutput("------------------------------------------------------------------\n");

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
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

                // Process the CSV line and display the result
                processLine(line);
            }
        } catch (IOException e) {
            appendOutput("Error reading CSV file: " + e.getMessage() + "\n");
        }
    }

    /**
     * Processes a single CSV line: parses numbers, concatenates with text, and checks its length.
     */
    private void processLine(String line) {
        String[] columns = line.split(",");

        // Ensure there are at least three columns (Number1, Number2, String)
        if (columns.length < 3) {
            appendOutput("Skipping line (insufficient columns): " + line + "\n");
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

            appendOutput("Result: '" + resultStr + "' is " + status + " 10 characters.\n");
        } catch (NumberFormatException e) {
            appendOutput("Error parsing number in line: " + line + "\n");
        }
    }

    private void appendOutput(String text) {
        outputTextArea.append(text);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CSVConcatenationCheckGUI gui = new CSVConcatenationCheckGUI();
            gui.setVisible(true);
        });
    }
}
