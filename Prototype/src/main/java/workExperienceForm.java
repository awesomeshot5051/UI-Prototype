import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;

public class workExperienceForm extends JFrame {

    //JPanels
    private final JPanel mainPanel;
    private final JPanel experiencePanel;
    private final JPanel headerButtonPanel;
    private final JPanel footerButtonPanel;


    //JButtons
    private final JButton addExperienceButton;
    private final JButton clearMostRecentButton;
    private final JButton nextPage;

    //JLabes
    private final JLabel headerTitle;
    private final ArrayList<JPanel> experienceFieldsList = new ArrayList<>();
    private final StringBuilder errorMessages = new StringBuilder();
    private int entryNumber = 0; //used to keep track of fields created

    public workExperienceForm() {
        // Set up main frame
        setIconImage(UIPrototypeMainClass.getIcon());
        setTitle("Work Experience Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400); // Adjusted size to accommodate fields better

        JLabel headerLabel = new JLabel("Employment Information", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));

        // Main panel to hold all components
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Panel to hold dynamic experience fields
        experiencePanel = new JPanel();
        experiencePanel.setLayout(new BoxLayout(experiencePanel, BoxLayout.Y_AXIS));

        // Scroll pane for experience fields
        JScrollPane scrollPane = new JScrollPane(experiencePanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        //initializing all components for header
        clearMostRecentButton = new JButton("Clear Most Recent Entry");
        addExperienceButton = new JButton("Add Work Experience");

        headerTitle = new JLabel("Work Experience");
        headerTitle.setFont(new Font("Arial", Font.BOLD, 24));
        headerTitle.setHorizontalAlignment(JLabel.CENTER); // Center the text within the label
        headerTitle.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        headerButtonPanel = new JPanel(new BorderLayout());
        headerButtonPanel.add(addExperienceButton, BorderLayout.WEST);
        headerButtonPanel.add(headerTitle, BorderLayout.CENTER);
        headerButtonPanel.add(clearMostRecentButton, BorderLayout.EAST);
        mainPanel.add(headerButtonPanel, BorderLayout.NORTH);

        nextPage = new JButton("Next Page");
        footerButtonPanel = new JPanel(new BorderLayout());
        footerButtonPanel.add(nextPage, BorderLayout.EAST);
        mainPanel.add(footerButtonPanel, BorderLayout.SOUTH);


        // Action listeners for buttons
        addExperienceButton.addActionListener(_ -> addExperienceFields());

        clearMostRecentButton.addActionListener(_ -> clearMostRecentExperienceField());

        nextPage.addActionListener(e -> validateFields());

        add(mainPanel);
        setVisible(true);
        setLocationRelativeTo(null);
    }
    

    /* This function is used to populate the interface
     *  with the fields to enter information */
    private void addExperienceFields() {

        entryNumber++;

        JPanel experienceFields = new JPanel();
        experienceFields.setName("Job " + entryNumber);
        experienceFields.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding between components

        experienceFields.setBorder(BorderFactory.createTitledBorder("Work Experience " + entryNumber));

        // First Row: Employer Name and Job Title
        gbc.gridx = 0; // Column 0
        gbc.gridy = 0; // Row 0
        gbc.weightx = 1; // Expand horizontally
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JTextField employerField = new JTextField(10);
        employerField.setBorder(BorderFactory.createTitledBorder("Employer Name"));
        experienceFields.add(employerField, gbc);
        employerField.setName("Employer Name");

        gbc.gridx = 1; // Column 1
        JTextField jobTitleField = new JTextField(10);
        jobTitleField.setBorder(BorderFactory.createTitledBorder("Job Title"));
        experienceFields.add(jobTitleField, gbc);
        jobTitleField.setName("Job Title");

        // Second Row: Start Date, End Date
        gbc.gridx = 0; // Column 0
        gbc.gridy = 1; // Row 1
        DatePickerSettings startDateSettings = new DatePickerSettings(Locale.ENGLISH);

//        DatePicker datePicker = new DatePicker(startDateSettings);

        DatePicker startDatePicker = new DatePicker(startDateSettings);
        // Set the veto policy to disable dates after today
        startDateSettings.setVetoPolicy(date -> {
            // Allow only dates up to the current date (today or before)
            return !date.isAfter(LocalDate.now());
        });
//        startDatePicker.setTextEditable(true);
        startDatePicker.setBorder(BorderFactory.createTitledBorder("Start Date"));
        startDatePicker.setName("Start");
        experienceFields.add(startDatePicker, gbc);
        DatePickerSettings endDateSettings = new DatePickerSettings(Locale.ENGLISH);
        gbc.gridx = 1; // Column 1
        DatePicker endDatePicker = new DatePicker(endDateSettings);
        endDateSettings.setVetoPolicy(date -> {
            // Allow only dates up to the current date (today or before)
            return !date.isAfter(LocalDate.now());
        });
        endDatePicker.setBorder(BorderFactory.createTitledBorder("End Date"));
        endDatePicker.setName("End");
        experienceFields.add(endDatePicker, gbc);


        // Third Row: Address related fields
        gbc.gridx = 0; // Column 0
        gbc.gridy = 2; // Row 2
        JTextField addressField = new JTextField(10);
        addressField.setBorder(BorderFactory.createTitledBorder("Address"));
        experienceFields.add(addressField, gbc);
        addressField.setName("Address");

        gbc.gridx = 1; // Column 1
        JTextField cityField = new JTextField(10);
        cityField.setBorder(BorderFactory.createTitledBorder("City"));
        experienceFields.add(cityField, gbc);
        cityField.setName("City");

        gbc.gridx = 0; // Column 0
        gbc.gridy = 3; // Row 3
        JTextField zipField = new JTextField(5);
        zipField.setBorder(BorderFactory.createTitledBorder("Zip"));
        experienceFields.add(zipField, gbc);
        zipField.setName("Zip");

        // State Dropdown
        gbc.gridx = 1; // Column 1
        gbc.gridy = 3; // Same row as zip
        JComboBox<String> stateDropdown = UIPrototypeMainClass.getStates();
        stateDropdown.setBorder(BorderFactory.createTitledBorder("State"));
        experienceFields.add(stateDropdown, gbc);
        stateDropdown.setName("State");


        experienceFieldsList.add(experienceFields);

        experiencePanel.add(experienceFields);

        experiencePanel.revalidate(); // Refresh the panel to show new fields
        experiencePanel.repaint();
    }

    /**
     * clearMostRecent function
     * Grabs the most recent field added and removes it from the interface
     */
    private void clearMostRecentExperienceField() {

        if (!experienceFieldsList.isEmpty()) {

            //targets the last panel added and removes from arrayList assigns to JPanel
            JPanel lastExperienceField = experienceFieldsList.removeLast();

            //removing panel from experience panel
            experiencePanel.remove(lastExperienceField);

            //refresh page
            experiencePanel.revalidate();
            experiencePanel.repaint();

            //decrementing entry number to let user keep track of number of fields added
            entryNumber--;
        }

    }

    public void nextPage() {
        SwingUtilities.invokeLater(() -> {
            dispose();
            JOptionPane.showMessageDialog(null, "You finished!", "You Finished", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    private void validateFields() {
        errorMessages.setLength(0);

        for (JPanel panel : experienceFieldsList) {
            for (Component component : panel.getComponents()) {
                String panelName = panel.getName();
                if (component instanceof JTextField textField) {
                    if (textField.getText().trim().isEmpty()) {
                        validateTextField(textField, panelName);
                    }
                } else if (component instanceof JComboBox<?> comboBox) {
                    validateDropdown(comboBox, panelName);
                } else if (component instanceof DatePicker datePicker) {
                    validateDatePicker(datePicker, panelName);
                }
            }
        }

        if (!errorMessages.toString().isEmpty()) {
            JOptionPane.showMessageDialog(this, errorMessages, "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            nextPage(); // Proceed if all fields are filled
        }
    }

    private void validateDatePicker(DatePicker datePicker, String panelName) {
        if (datePicker.getText().isEmpty()) {
            errorMessages.append(datePicker.getName()).append(" Date must be selected for ").append(panelName).append("!\n");
        }
    }


    private void validateDropdown(JComboBox<?> dropdown, String panelName) {
        if (dropdown.getSelectedIndex() == 0) { // Customize for your dropdown
            errorMessages.append(dropdown.getName()).append(" must be selected for ").append(panelName).append("!\n");
        }
    }

    /**
     * Validates a standard JTextField to ensure it is not empty.
     * <p>
     * If the field is empty and its name is not "Middle Name," an error message is appended to the errorMessages StringBuilder.
     * Additionally, if the field is an email field, it validates the format against a specified regex pattern.
     * </p>
     *
     * @param field The JTextField to validate.
     */
    private void validateTextField(JTextField field, String panelName) {
        if (field.getText().isEmpty()) {
            errorMessages.append(field.getName()).append(" must be selected for ").append(panelName).append("\n");
        }
    }
}
