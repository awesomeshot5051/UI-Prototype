import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class referencesForm extends JFrame {
    /**
     * <ul>
     * <li>{@code regex} a regular expression used for email validation. It checks if the input follows a valid email format.</li>
     * <br>
     * <br>
     *
     * <li>{@code mainPanel} the panel that holds all the components and is the main container for the form.</li>
     * <li>{@code referencePanel} the panel that represents a reference section in the form, typically duplicated for each reference.</li>
     * <li>{@code headerButtonPanel} the panel containing buttons at the top of the form, such as adding or removing reference fields.</li>
     * <li>{@code footerButtonPanel} the panel containing buttons at the bottom of the form, including the button to go to the next page.</li>
     * <br>
     * <br>
     *
     * <li>{@code addReferenceButton} the button used to add a new {@code referencePanel}, effectively duplicating the reference section.</li>
     * <li>{@code clearMostRecentButton} the button used to remove the most recent {@code referencePanel}, deleting the entire reference entry.</li>
     * <li>{@code nextPage} the button that advances to the next page. It ensures all fields are properly filled before allowing the transition. If fields are missing or incorrect, an error message is shown.</li>
     * <br>
     * <br>
     *
     * <li>{@code headerTitle} the label displaying the title of the form at the top of the panel.</li>
     * <li>{@code referenceFieldsList} an {@link ArrayList} that holds all of the {@code referencePanel} instances, making it easy to manage them (e.g., deleting the most recent entry).</li>
     * <li>{@code errorMessages} a {@link StringBuilder} used to assemble error messages when the {@code nextPage} button is clicked. It informs the user about any missing or improperly filled fields.</li>
     * <li>{@code entryNumber} a counter used to track the number of fields created. This value increments as new reference fields are added and decrements when reference fields are removed. It is also used for labeling each entry panel sequentially.</li>
     * </ul>
     */

    private static final String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    //JPanels
    private final JPanel mainPanel;
    private final JPanel referencePanel;
    private final JPanel headerButtonPanel;
    private final JPanel footerButtonPanel;
    private final StringBuilder errorMessages = new StringBuilder();
    //JButtons
    private final JButton addRefrenceButton;
    private final JButton clearMostRecentButton;
    private final JButton nextPage;
    //JLabels
    private final JLabel headerTitle;
    private final ArrayList<JPanel> refrenceFieldsList = new ArrayList<>();
    private int entryNumber = 0; //used to keep track of fields created

    /**
     * Constructor for the {@code referencesForm} class, which initializes and sets up the GUI components for the references form.
     *
     * <p>
     * The constructor also sets up the following buttons with their corresponding action listeners:
     *
     * <p>
     * The constructor also:
     * <ul>
     *     <li>Sets the form's title to "References" and the size to 500x400 pixels.</li>
     *     <li>Sets the window to close when the user clicks the close button.</li>
     *     <li>Configures the panel layout using {@link BorderLayout} and {@link BoxLayout} for various sections of the form.</li>
     *     <li>Centers the window on the screen and sets an icon for the application.</li>
     * </ul>
     */

    public referencesForm() {
        // Set up the main frame of the application
        setTitle("References Form"); // Set the title of the window to "References Form"
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the application when the window is closed
        setSize(500, 400); // Set the window size to 500x400 pixels for better component accommodation

        // Create and configure the header label
        JLabel headerLabel = new JLabel("References", JLabel.CENTER); // Create a JLabel with text "References" and center-aligned
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Set the font to Arial, bold, size 24

        // Main panel to hold all components in the form
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout()); // Use BorderLayout for the main panel layout

        // Panel to hold dynamic reference fields
        referencePanel = new JPanel();
        referencePanel.setLayout(new BoxLayout(referencePanel, BoxLayout.Y_AXIS)); // Arrange components vertically

        // Create a scroll pane to hold the referencePanel, allowing scrolling if necessary
        JScrollPane scrollPane = new JScrollPane(referencePanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER); // Add the scroll pane to the center of the main panel

        // Initialize all components for the header section
        clearMostRecentButton = new JButton("Clear Most Recent Entry"); // Button to clear the most recent reference entry
        addRefrenceButton = new JButton("Add Reference"); // Button to add a new reference entry

        // Create and configure the header title label
        headerTitle = new JLabel("References");
        headerTitle.setFont(new Font("Arial", Font.BOLD, 24)); // Set the font to Arial, bold, size 24
        headerTitle.setHorizontalAlignment(JLabel.CENTER); // Center the text within the label
        headerTitle.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding around the header label

        // Panel to hold the buttons in the header section
        headerButtonPanel = new JPanel(new BorderLayout());
        headerButtonPanel.add(addRefrenceButton, BorderLayout.WEST); // Place the "Add Reference" button on the left
        headerButtonPanel.add(headerTitle, BorderLayout.CENTER); // Place the title in the center
        headerButtonPanel.add(clearMostRecentButton, BorderLayout.EAST); // Place the "Clear Most Recent Entry" button on the right
        mainPanel.add(headerButtonPanel, BorderLayout.NORTH); // Add the headerButtonPanel to the top (North) of the main panel

        // Create the "Next Page" button for navigation
        nextPage = new JButton("Next Page");

        // Panel to hold the "Next Page" button in the footer section
        footerButtonPanel = new JPanel(new BorderLayout());
        footerButtonPanel.add(nextPage, BorderLayout.EAST); // Place the "Next Page" button on the right
        mainPanel.add(footerButtonPanel, BorderLayout.SOUTH); // Add the footerButtonPanel to the bottom (South) of the main panel

        // Add action listeners to the buttons for handling user actions

        // Action listener for the "Add Reference" button
        addRefrenceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addReferenceFields(); // Call the method to add a new reference field
            }
        });

        // Action listener for the "Clear Most Recent Entry" button
        clearMostRecentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearMostRecentReferenceField(); // Call the method to clear the most recent reference field
            }
        });

        // Action listener for the "Next Page" button
        nextPage.addActionListener(_ -> validateFields()); // Validate all fields before proceeding to the next page

        // Add the main panel to the JFrame
        add(mainPanel);

        // Make the window visible
        setVisible(true);

        // Center the window on the screen
        setLocationRelativeTo(null);

        // Set the application icon
        setIconImage(UIPrototypeMainClass.getIcon());
    }

    /**
     * Formats a phone number and validates its correctness.
     * <p>
     * This method processes the user's phone number input, attempting to parse it using the
     * PhoneNumberUtil class to check if the number is valid. If the number is valid, it formats
     * the number in international format and updates the phone number field with the formatted number.
     * If the number is invalid or an error occurs during parsing, an appropriate error message is
     * appended to the {@code errorMessages} StringBuilder, and {@code false} is returned to indicate failure.
     * </p>
     *
     * @param phoneNumber   The {@link JFormattedTextField} containing the phone number input by the user.
     * @param errorMessages A {@link StringBuilder} that accumulates error messages to be displayed to the user.
     * @param panelName     The name of the panel (or field) where the phone number is entered, used for error message context.
     * @return {@code true} if the phone number is valid and successfully formatted, {@code false} otherwise.
     */
    public static boolean formatPhoneNumber(JFormattedTextField phoneNumber, StringBuilder errorMessages, String panelName) {
        // Retrieve the raw phone number text from the JFormattedTextField
        String rawNumber = phoneNumber.getText();

        // Create an instance of PhoneNumberUtil to process and validate the phone number
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

        try {
            // Attempt to parse the raw phone number assuming the default country as 'US'
            Phonenumber.PhoneNumber numberProto = phoneUtil.parse(rawNumber, "US");

            // Check if the parsed number is valid according to the phoneUtil
            boolean contains = errorMessages.toString().contains("Invalid phone number for " + panelName + "\n");
            if (phoneUtil.isValidNumber(numberProto)) {
                // Format the valid phone number in international format
                String formattedNumber = phoneUtil.format(numberProto, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);

                // Set the formatted number back to the phoneNumber field
                phoneNumber.setValue(formattedNumber);
                if (!errorMessages.isEmpty() && contains) {
                    // Create the exact message to remove
                    String messageToRemove = "Invalid phone number for " + panelName + "\n";

                    // Find the starting index of the message
                    int startIndex = errorMessages.indexOf(messageToRemove);

                    // If the message exists, remove it by replacing it with an empty string
                    if (startIndex != -1) {
                        errorMessages.replace(startIndex, startIndex + messageToRemove.length(), "");
                    }
                }

            } else {
                // If the phone number is invalid, add an error message to the errorMessages StringBuilder
                if (!contains) {
                    errorMessages.append("Invalid phone number for ").append(panelName).append("!\n");
                }
                // Return false indicating the phone number was invalid
                return false;
            }
        } catch (NumberParseException e) {
            // If an error occurs while parsing the phone number, add a parsing error message
            errorMessages.append("Error parsing phone number for ").append(panelName).append("!\n");

            // Return false indicating a parsing error occurred
            return false;
        }

        // Return true indicating the phone number was successfully validated and formatted
        return true;
    }

    /**
     * This method adds a new set of fields for entering reference information.
     * <p>
     * The method dynamically generates a new reference entry form with fields for
     * employer name, job title, company name, phone number, and email address.
     * Each entry is grouped in a panel and added to the main reference panel.
     * </p>
     * The fields are arranged in a grid layout with padding, and each field is
     * properly titled and prepared for user input.
     * <ul>
     * <li>{@code entryNumber} increments every time a new reference is added to ensure each reference has a unique identifier.</li>
     * <li>{@code referenceFields} is a {@link JPanel} used to group and organize the fields for a single reference entry.</li>
     * <li>{@code gbc} is a {@link GridBagConstraints} instance that defines the layout constraints for each component in the {@code referenceFields} panel.</li>
     * <li>{@code employerField} is a {@link JTextField} that allows the user to input the employer's name.</li>
     * <li>{@code jobTitleField} is a {@link JTextField} for the user to input the job title of the reference.</li>
     * <li>{@code startDayField} is a {@link JTextField} for entering the start date of employment.</li>
     * <li>{@code phoneNumber} is a {@link JFormattedTextField} used to enter and format the phone number for the reference.</li>
     * <li>{@code addressField} is a {@link JTextField} where the user enters the reference's email address.</li>
     * <li>{@code refrenceFieldsList} is a {@link List} that holds all the panels representing individual reference entries. It helps track the fields added.</li>
     * </ul>
     */
    private void addReferenceFields() {

        // Increment the entry number to ensure each reference has a unique identifier
        entryNumber++;

        // Create a new JPanel for the current reference entry
        JPanel referenceFields = new JPanel();
        referenceFields.setName("Company " + entryNumber);  // Set the panel name for identification
        referenceFields.setLayout(new GridBagLayout());  // Use GridBagLayout for positioning components
        GridBagConstraints gbc = new GridBagConstraints();  // GridBagConstraints to manage component positioning
        gbc.insets = new Insets(5, 5, 5, 5);  // Padding between components for better spacing

        // Add a border with the reference number for visual identification
        referenceFields.setBorder(BorderFactory.createTitledBorder("Reference " + entryNumber));

        // First Row: Employer Name and Job Title
        gbc.gridx = 0;  // Position in column 0
        gbc.gridy = 0;  // Position in row 0
        gbc.weightx = 1;  // Expand horizontally
        gbc.fill = GridBagConstraints.HORIZONTAL;  // Allow horizontal expansion
        JTextField employerField = new JTextField(10);  // Create a text field for employer name
        employerField.setBorder(BorderFactory.createTitledBorder("Name"));  // Set the field border title
        employerField.setName("Employer");  // Set the name attribute for easy identification
        referenceFields.add(employerField, gbc);  // Add the field to the panel

        gbc.gridx = 1;  // Position in column 1
        JTextField jobTitleField = new JTextField(10);  // Create a text field for job title
        jobTitleField.setBorder(BorderFactory.createTitledBorder("Position"));  // Set the field border title
        jobTitleField.setName("Position");  // Set the name attribute for easy identification
        referenceFields.add(jobTitleField, gbc);  // Add the field to the panel

        // Second Row: Start Date, End Date
        gbc.gridx = 0;  // Position in column 0
        gbc.gridy = 1;  // Position in row 1
        JTextField startDayField = new JTextField(10);  // Create a text field for the start date
        startDayField.setBorder(BorderFactory.createTitledBorder("Company"));  // Set the field border title
        referenceFields.add(startDayField, gbc);  // Add the field to the panel
        startDayField.setName("Company");  // Set the name attribute for easy identification

        // Phone Number Field
        gbc.gridx = 1;  // Position in column 1
        JFormattedTextField phoneNumber = new JFormattedTextField();  // Create a formatted text field for phone number
        phoneNumber.setBorder(BorderFactory.createTitledBorder("Phone Number"));  // Set the field border title
        referenceFields.add(phoneNumber, gbc);  // Add the field to the panel

        // Add a FocusListener to handle phone number formatting when focus is lost
        phoneNumber.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent evt) {
                // Only format if the phone number is not blank
                if (!phoneNumber.getText().isBlank()) {
                    // Call formatPhoneNumber method to validate and format the phone number
                    formatPhoneNumber(phoneNumber, errorMessages, referenceFields.getName());
                }
            }
        });
        phoneNumber.setName("Phone Number");  // Set the name attribute for easy identification

        // Third Row: Email Address
        gbc.gridx = 0;  // Position in column 0
        gbc.gridy = 2;  // Position in row 2
        JTextField addressField = new JTextField(10);  // Create a text field for email address
        addressField.setBorder(BorderFactory.createTitledBorder("Email"));  // Set the field border title
        addressField.setName("Email");  // Set the name attribute for easy identification
        referenceFields.add(addressField, gbc);  // Add the field to the panel

        // State Dropdown: A placeholder for additional fields (currently not implemented)
        gbc.gridx = 1;  // Position in column 1
        gbc.gridy = 3;  // Position in row 3 (same row as the state dropdown)
        // (Currently left unimplemented but can be added here in the future)

        // Add the reference fields panel to the list for possible future access
        refrenceFieldsList.add(referenceFields);

        // Add the new reference fields panel to the main reference panel
        referencePanel.add(referenceFields);

        // Revalidate and repaint the reference panel to update the UI with new fields
        referencePanel.revalidate();
        referencePanel.repaint();
    }

    /**
     * This method removes the most recent reference entry from the interface.
     * <p>
     * It identifies the last panel added to the form, removes it from both the display and
     * the internal list tracking the reference fields. The form is then refreshed to reflect
     * the changes, and the {@code entryNumber} is decremented to update the count of reference fields.
     * </p>
     * <ul>
     * <li>{@code lastExperienceField} is a {@link JPanel} that represents the most recent reference entry
     * and is removed from the main panel.</li>
     * </ul>
     */
    private void clearMostRecentReferenceField() {

        // Check if there are any reference fields to remove
        if (!refrenceFieldsList.isEmpty()) {

            // Grab the most recent panel (last one added) from the list of reference fields
            JPanel lastExperienceField = refrenceFieldsList.removeLast();

            // Remove the reference panel from the main panel
            referencePanel.remove(lastExperienceField);

            // Refresh the reference panel to reflect the removal of the last field
            referencePanel.revalidate();  // Ensures that the layout is recalculated
            referencePanel.repaint();  // Refreshes the UI to update the display

            // Decrement the entry number to track the number of references after removal
            entryNumber--;
        }
    }

    /**
     * Validates all fields within the reference form.
     * <p>
     * This method iterates through all the panels containing reference information (stored in {@code refrenceFieldsList}),
     * and validates each field within them (e.g., text fields, dropdowns). If any field is invalid, an error message is
     * shown to the user, listing the fields that need attention. If all fields are valid, it proceeds to the next page.
     * </p>
     * <ul>
     * <li>{@code allFieldsFilled} is a boolean that keeps track of whether all fields have been validated successfully.</li>
     * <li>{@code errorMessages} is a {@link StringBuilder} used to accumulate error messages, if any validation fails.</li>
     * </ul>
     */
    private void validateFields() {
        boolean allFieldsFilled = true; // Flag to track whether all fields are filled correctly

        // Loop through each reference panel to validate its components
        for (JPanel panel : refrenceFieldsList) {
            String panelName = panel.getName() != null ? panel.getName() : "Unnamed Reference"; // Get the panel name or set default

            // Loop through each component within the current panel
            for (Component component : panel.getComponents()) {
                if (component instanceof JTextField textField) {
                    // If the component is a text field, validate it
                    if (component instanceof JFormattedTextField) {
                        // Validate formatted text field (like phone number)
                        allFieldsFilled &= validateFormattedTextField((JFormattedTextField) component, panelName);
                    } else {
                        // Validate standard text field
                        allFieldsFilled &= validateTextField(textField, panelName);
                    }
                } else if (component instanceof JComboBox<?> comboBox) {
                    // Validate the dropdown field
                    allFieldsFilled &= validateDropdown(comboBox, panelName);
                }
            }
        }

        // If not all fields are filled correctly, show the error messages
        if (!allFieldsFilled) {
            JOptionPane.showMessageDialog(this, errorMessages.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            errorMessages.setLength(0); // Clear error messages after displaying
        } else {
            nextPage(); // Proceed to next page if all fields are valid
        }
    }

    /**
     * Validates a standard JTextField to ensure it is not empty.
     * <p>
     * If the field is empty and its name is not "Middle Name", an error message is appended to the {@code errorMessages} StringBuilder.
     * Additionally, if the field is an email field, it validates the format against a specified regex pattern.
     * </p>
     * <ul>
     * <li>{@code regex} is the regular expression pattern used for validating email format.</li>
     * </ul>
     *
     * @param field     The {@link JTextField} to validate.
     * @param panelName The name of the panel containing this field, used for error message clarity.
     * @return {@code true} if the field is valid, {@code false} otherwise.
     */
    private boolean validateTextField(JTextField field, String panelName) {
        if (field.getText().isEmpty()) {
            // If the text field is empty, append an error message
            errorMessages.append(field.getName()).append(" is required for ").append(panelName).append("\n");
            return false;
        } else {
            if (Objects.equals(field.getName(), "Email")) {
                // If it's an email field, validate its format using a regex
                Pattern pattern = Pattern.compile(regex); // regex pattern for email validation
                String emailText = field.getText();
                Matcher matcher = pattern.matcher(emailText);

                if (emailText.isEmpty()) {
                    errorMessages.append("Email is required for ").append(panelName).append("\n");
                    return false;
                } else if (!matcher.matches()) {
                    errorMessages.append("Email format is invalid for ").append(panelName).append("\n");
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Validates a {@link JFormattedTextField} (in this case, used for phone numbers) to ensure it is not empty.
     * <p>
     * If the field is filled, it formats the phone number.
     * </p>
     * <ul>
     * <li>{@code phoneNumber} is the JFormattedTextField that contains the phone number input by the user.</li>
     * </ul>
     *
     * @param field     The {@link JFormattedTextField} to validate.
     * @param panelName The name of the panel containing this field, used for error message clarity.
     * @return {@code true} if the formatted field is valid, {@code false} otherwise.
     */
    private boolean validateFormattedTextField(JFormattedTextField field, String panelName) {
        if (field.getText().isEmpty()) {
            // If the phone number field is empty, append an error message
            errorMessages.append("Phone number is required for ").append(panelName).append("\n");
            return false;
        } else {
            // If the phone number is filled, validate its format
            return formatPhoneNumber(field, errorMessages, panelName); // Format the phone number if valid
        }
    }

    /**
     * Validates a {@link JComboBox} to ensure a selection is made that is not the default option.
     * <p>
     * This method checks that the user has selected a value from the dropdown, and not left it on the default "State" option.
     * </p>
     *
     * @param dropdown  The {@link JComboBox} to validate.
     * @param panelName The name of the panel containing this dropdown, used for error message clarity.
     * @return {@code true} if the dropdown is correctly selected, {@code false} otherwise.
     */
    private boolean validateDropdown(JComboBox<?> dropdown, String panelName) {
        if (dropdown.getSelectedIndex() == 0) { // Index 0 is typically the default option, e.g., "State"
            // If the default option is selected, append an error message
            errorMessages.append(dropdown.getName()).append(" must be selected!\n");
            return false;
        }
        return true;
    }


    /**
     * Navigates to the next page by disposing of the current form and opening the education form.
     * This method is executed on the Swing event dispatch thread to ensure thread safety when updating the UI.
     */
    public void nextPage() {
        // Ensure that the UI updates happen on the Event Dispatch Thread (EDT) for thread safety
        SwingUtilities.invokeLater(() -> {
            // Close the current window (this form)
            dispose();

            // Create a new instance of the educationForm and make it visible
            new educationForm().setVisible(true);
        });
    }


}
