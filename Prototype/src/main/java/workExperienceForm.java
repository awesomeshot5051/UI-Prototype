import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;

public class workExperienceForm extends JFrame {
    /**
     * <ul>
     * <li>{@code mainPanel} the panel that holds everything</li>
     * <li>{@code experiencePanel} the panel that is duplicated</li>
     * <li>{@code headerButtonPanel} the panel that holds the buttons at the top of the panel(add experience field and delete the most recent experience</li>
     * <li>{@code footerButtonPanel} the panel that holds the buttons at the bottom of the panel(nextPage)</li>
     * <br>
     * <br>
     *
     * <li>{@code addExperienceButton} the button that duplicates the {@systemProperty experiencePanel}</li>
     * <li>{@code clearMostRecentButton} the button that clears the most recent {@systemProperty experiencePanel}. This deletes the entire panel.</li>
     * <li>{@code nextPage}the button that goes to the next page. Before this successfully moves to the next page, it first ensures that all fields are filled in. If a field is missing, it informs the user which field is missing and does not advance to the next page.</li>
     * <br>
     * <br>
     *
     * <li>{@code headerTitle} the title of the panel</li>
     * <li>{@code experienceFieldList} an array that holds all of the {@systemProperty experienceField} to make it easier to delete the latest one.</li>
     * <li>{@code errorMessages} a stringBuilder that is assembled when you click {@systemProperty nextPage}. It informs the user what is missing or improperly filled.</li>
     * <li>{@code entryNumber} the number entry you are on. This increments as you add {@systemProperty experienceField}(using the {@systemProperty addExperienceButton} and decrements as you remove {@systemProperty experienceField}(using the {@systemProperty clearMostRecentButton} Also used to label each entry panel sequentially.</li>
     *
     * </ul>
     **/
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

    /**
     * Constructor for the {@code workExperienceForm} class.
     * Initializes the JFrame, sets up all panels, buttons, and action listeners,
     * and displays the GUI.
     */
    public workExperienceForm() {
        // Set up the main frame (icon, title, size, etc.)
        setIconImage(UIPrototypeMainClass.getIcon());
        setTitle("Work Experience Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400); // Adjusted size to accommodate fields better

        JLabel headerLabel = new JLabel("Employment Information", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));

        // Create the main panel with a BorderLayout for structured layout
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Panel to hold dynamically added work experience fields
        experiencePanel = new JPanel();
        experiencePanel.setLayout(new BoxLayout(experiencePanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(experiencePanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Initialize header components
        clearMostRecentButton = new JButton("Clear Most Recent Entry");
        addExperienceButton = new JButton("Add Work Experience");
        headerTitle = new JLabel("Work Experience");
        headerTitle.setFont(new Font("Arial", Font.BOLD, 24));
        headerTitle.setHorizontalAlignment(JLabel.CENTER); // Center the text within the label
        headerTitle.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // Header panel to house the add/clear buttons and title
        headerButtonPanel = new JPanel(new BorderLayout());
        headerButtonPanel.add(addExperienceButton, BorderLayout.WEST);
        headerButtonPanel.add(headerTitle, BorderLayout.CENTER);
        headerButtonPanel.add(clearMostRecentButton, BorderLayout.EAST);
        mainPanel.add(headerButtonPanel, BorderLayout.NORTH);

        // Initialize footer components
        nextPage = new JButton("Next Page");
        footerButtonPanel = new JPanel(new BorderLayout());
        footerButtonPanel.add(nextPage, BorderLayout.EAST);
        mainPanel.add(footerButtonPanel, BorderLayout.SOUTH);


        // Attach button action listeners
        addExperienceButton.addActionListener(_ -> addExperienceFields());

        clearMostRecentButton.addActionListener(_ -> clearMostRecentExperienceField());

        nextPage.addActionListener(e -> validateFields());

        add(mainPanel);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    /**
     * Adds a new set of work experience fields to the form.
     * These fields include
     * <ul>
     * <li>{@code employerField} the field to put the name of your employer. This is the companies name, not your bosses name.</li>
     * <li>{@code jobTitle} official title of the position that you have or had</li>
     * <li>{@code start date}date that you started working the job</li>
     * <li>{@code end date}date that you were no longer employed at work. This can be removed using the {@code currentlyWorking} since not everyone is currently out of the job.</li>
     * <li>{@code address}address of the company that you worked for</li>
     * <li>{@code city}city of the company that you worked for</li>
     * <li>{@code zipCode}zipcode of the company that you worked for</i>
     * <li>{@code stateDropdown}state that the company is in that you worked for</li>
     * <li>{@code Currently Working}whether or not you are actively working in the job</li>
     * Each new set is wrapped in a titled border for clarity.
     */
    private void addExperienceFields() {
        entryNumber++; // Increment the entry number for labeling
        // Create a new panel for this entry
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
        DatePicker startDatePicker = new DatePicker(startDateSettings);

        // Set the veto policy to disable dates after today
        startDateSettings.setVetoPolicy(date -> !date.isAfter(LocalDate.now()));

        startDatePicker.setBorder(BorderFactory.createTitledBorder("Start Date"));
        startDatePicker.setName("Start");
        experienceFields.add(startDatePicker, gbc);

        // End Date with "Currently Working" Checkbox
        DatePickerSettings endDateSettings = new DatePickerSettings(Locale.ENGLISH);
        DatePicker endDatePicker = new DatePicker(endDateSettings);
        endDateSettings.setVetoPolicy(date -> !date.isAfter(LocalDate.now()));
        endDatePicker.setBorder(BorderFactory.createTitledBorder("End Date"));
        endDatePicker.setName("End");

        gbc.gridx = 1; // Column 1 for End Date
        experienceFields.add(endDatePicker, gbc);

        // "Currently Working" Checkbox
        JCheckBox currentlyWorkingCheckbox = new JCheckBox("Currently Working");
        gbc.gridx = 2; // Column 2 for Checkbox
        experienceFields.add(currentlyWorkingCheckbox, gbc);

        // Add ActionListener to Checkbox to hide/show the End Date field
        currentlyWorkingCheckbox.addActionListener(e -> {
            boolean isSelected = currentlyWorkingCheckbox.isSelected();
            endDatePicker.setVisible(!isSelected); // Hide endDatePicker if checkbox is selected
            experienceFields.revalidate(); // Refresh layout to update visibility
            experienceFields.repaint();
            experiencePanel.revalidate();
            experiencePanel.repaint();
        });

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
        // Create a JTextField for entering a ZIP code
        JTextField zipField = new JTextField(5);
        // Set a titled border to visually indicate the field's purpose
        zipField.setBorder(BorderFactory.createTitledBorder("Zip"));
        experienceFields.add(zipField, gbc);
        zipField.setName("Zip");
        // Add a DocumentFilter to the ZIP field to restrict input
        ((AbstractDocument) zipField.getDocument()).setDocumentFilter(new DocumentFilter() {
            /**
             * Called when text is inserted into the text field.
             * Ensures only numeric input and a maximum length of 5 characters.
             */
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                // Allow insertion only if input is numeric and length remains <= 5
                if ((fb.getDocument().getLength() + string.length()) <= 5 && string.matches("\\d*")) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            /**
             * Called when text in the field is replaced.
             * Ensures only numeric input and a maximum length of 5 characters.
             */
            @Override
            public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attrs) throws BadLocationException {
                // Allow replacement only if input is numeric and length remains <= 5
                if ((fb.getDocument().getLength() + string.length() - length) <= 5 && string.matches("\\d*")) {
                    super.replace(fb, offset, length, string, attrs);
                }
            }
        });
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

    /**
     * Transitions to the next page or completes the workflow by displaying a completion message
     * and exiting the application.
     */
    public void nextPage() {
        // Schedule the following task to run on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            // Dispose of the current frame to release resources and remove it from the screen
            dispose();

            //Goes to End User License Agreement page
            new EULA();
        });
    }


    /**
     * Validates all fields within the list of experience panels.
     * <p>
     * Each panel is iterated, and its components (text fields, combo boxes, date pickers) are checked for validity.
     * If any field is invalid, a descriptive error message is added to the errorMessages StringBuilder.
     * If errors are found, a JOptionPane displays the errors; otherwise, the workflow proceeds to the next page.
     * {@link #errorMessages}
     * </p>
     */
    private void validateFields() {
        // Clear previous error messages
        errorMessages.setLength(0);

        // Iterate through all experience panels
        for (JPanel panel : experienceFieldsList) {
            for (Component component : panel.getComponents()) {
                String panelName = panel.getName(); // Get the name of the current panel
                if (component instanceof JTextField textField) {
                    // Validate text fields
                    if (textField.getText().trim().isEmpty()) {
                        validateTextField(textField, panelName);
                    }
                } else if (component instanceof JComboBox<?> comboBox) {
                    // Validate dropdowns
                    validateDropdown(comboBox, panelName);
                } else if (component instanceof DatePicker datePicker) {
                    // Validate date pickers
                    validateDatePicker(datePicker, panelName);
                }
            }
        }

        // Display error messages if any exist; otherwise, proceed to the next page
        if (!errorMessages.toString().isEmpty()) {
            JOptionPane.showMessageDialog(this, errorMessages.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            nextPage(); // Proceed if all fields are valid
        }
    }

    /**
     * Validates a DatePicker field to ensure a date is selected.
     * <p>
     * For "End" date fields that are invisible, validation is skipped.
     * If the field is empty, an error message is added for the corresponding panel.
     * </p>
     *
     * @param datePicker The DatePicker to validate.
     * @param panelName  The name of the panel containing this field.
     */
    private void validateDatePicker(DatePicker datePicker, String panelName) {
        if (datePicker.getName().equals("End") && !datePicker.isVisible()) {
            // Skip validation for hidden "End" date fields
            return;
        }
        if (datePicker.getText().isEmpty()) {
            errorMessages.append(datePicker.getName())
                    .append(" Date must be selected for ")
                    .append(panelName)
                    .append("!\n");
        }
    }

    /**
     * Validates a JComboBox dropdown to ensure an item other than the default is selected.
     * <p>
     * If the selected index is 0, an error message is added for the corresponding panel.
     * </p>
     *
     * @param dropdown  The JComboBox to validate.
     * @param panelName The name of the panel containing this dropdown.
     */
    private void validateDropdown(JComboBox<?> dropdown, String panelName) {
        if (dropdown.getSelectedIndex() == 0) { // Check if the default item is selected
            errorMessages.append(dropdown.getName())
                    .append(" must be selected for ")
                    .append(panelName)
                    .append("!\n");
        }
    }

    /**
     * Validates a JTextField to ensure it is not empty.
     * <p>
     * If the field is empty, an error message is appended to the errorMessages StringBuilder
     * with a reference to the containing panel.
     * </p>
     *
     * @param field     The JTextField to validate.
     * @param panelName The name of the panel containing this field.
     */
    private void validateTextField(JTextField field, String panelName) {
        if (field.getText().isEmpty()) {
            errorMessages.append(field.getName())
                    .append(" must be entered for ")
                    .append(panelName)
                    .append("\n");
        }
    }

}
