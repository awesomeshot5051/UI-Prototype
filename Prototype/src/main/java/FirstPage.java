import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FirstPage extends Application {
    /**
     * A JavaFX application class that implements a personal information form interface.
     * This class creates a detailed form for collecting user information including name,
     * contact details, address, and demographic information.
     *
     * <p>The form includes various input fields, radio buttons, and dropdown menus for
     * comprehensive data collection. It implements input validation and formatting for
     * specific fields like phone numbers and ZIP codes.</p>
     *
     * <p>Class Variables:</p>
     * <ul>
     *   <li>{@code WIDTH} - The default width of the application window (640 pixels)</li>
     *   <li>{@code HEIGHT} - The default height of the application window (480 pixels)</li>
     *   <li>{@code page1Frame} - The main JFrame container for the form</li>
     *   <li>{@code parent} - The root BorderPane layout container</li>
     *   <li>{@code numberField} - Text field for street number input</li>
     *   <li>{@code streetField} - Text field for street name input</li>
     *   <li>{@code cityField} - Text field for city name input</li>
     *   <li>{@code stateField} - Text field for state abbreviation input</li>
     *   <li>{@code zipField} - Text field for ZIP code input (limited to 5 digits)</li>
     *   <li>{@code currentAddressLabel} - Label for the address section</li>
     *   <li>{@code headingLabel} - Main heading label for the form</li>
     *   <li>{@code firstNameLabel} - Label for first name field</li>
     *   <li>{@code firstName} - Text field for first name input</li>
     *   <li>{@code lastNameLabel} - Label for last name field</li>
     *   <li>{@code lastName} - Text field for last name input</li>
     *   <li>{@code middleNm} - Checkbox to toggle middle name input visibility</li>
     *   <li>{@code middleNameLabel} - Label for middle name field</li>
     *   <li>{@code middleName} - Text field for middle name input</li>
     *   <li>{@code phoneNumberLabel} - Label for phone number field</li>
     *   <li>{@code emailLabel} - Label for email field</li>
     *   <li>{@code email} - Text field for email input</li>
     *   <li>{@code maleRadioButton} - Radio button for male gender selection</li>
     *   <li>{@code femaleJRadioButton} - Radio button for female gender selection</li>
     *   <li>{@code pntsJRadioButton} - Radio button for "prefer not to say" gender option</li>
     *   <li>{@code radioButtonGroup} - Button group for gender selection radio buttons</li>
     *   <li>{@code weightFormattedTextField} - Formatted text field for weight input</li>
     *   <li>{@code clearButton} - Button to clear all form fields</li>
     *   <li>{@code submitButton} - Button to submit form data</li>
     *   <li>{@code phoneNumberField} - Formatted text field for phone number input with validation</li>
     *   <li>{@code stateDropdown} - Dropdown menu for state selection</li>
     *   <li>{@code yearsAtAdd} - Button group for years at address selection</li>
     *   <li>{@code zeroToFive} - Radio button for 0-5 years at address</li>
     *   <li>{@code sixToTen} - Radio button for 6-10 years at address</li>
     *   <li>{@code elevenToTwenty} - Radio button for 11-20 years at address</li>
     *   <li>{@code twentyToThirty} - Radio button for 21-30 years at address</li>
     *   <li>{@code thirtyPlus} - Radio button for 30+ years at address</li>
     * </ul>
     */
    private static final double WIDTH = 640;
    private static final double HEIGHT = 480;
    private static final String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private final JFrame page1Frame = new JFrame("Personal information");
    private final BorderPane parent = new BorderPane();
    JTextField numberField, streetField, cityField, stateField, zipField;
    private JLabel currentAddressLabel;
    private JLabel headingLabel;
    private JLabel firstNameLabel;
    private JTextField firstName;
    private JLabel lastNameLabel;
    private JTextField lastName;
    private JCheckBox middleNm;
    private JLabel middleNameLabel;
    private JTextField middleName;
    private JLabel phoneNumberLabel;
    private JLabel emailLabel;
    private JTextField email;
    private JRadioButton maleRadioButton;
    private JRadioButton femaleJRadioButton;
    private JRadioButton pntsJRadioButton;
    private ButtonGroup radioButtonGroup;
    private JFormattedTextField weightFormattedTextField;
    private JButton clearButton;
    private JButton submitButton;
    private JFormattedTextField phoneNumberField;
    private JComboBox<String> stateDropdown;
    private ButtonGroup yearsAtAdd;
    private JRadioButton zeroToFive;
    private JRadioButton sixToTen;
    private JRadioButton elevenToTwenty;
    private JRadioButton twentyToThirty;
    private JRadioButton thirtyPlus;

    /**
     * Default constructor for FirstPage.
     * Initializes the UI components by calling setupUI().
     */
    FirstPage() {
        setupUI();
    }

    /**
     * JavaFX start method. Sets up the primary stage and displays the application window.
     *
     * @param stage The primary stage for this application
     * @throws Exception If there's an error during application startup
     */
    @Override
    public void start(Stage stage) throws Exception {

        Scene scene = new Scene(this.parent, WIDTH, HEIGHT);

        // Set the stage title
        stage.setTitle("JavaFX: Phone Number Input Field");

        // Sets the stage scene
        stage.setScene(scene);

        // Centers stage on screen
        stage.centerOnScreen();

        // Show stage on screen
        stage.show();

    }

    /**
     * JavaFX initialization method. Called before the start method.
     *
     * @throws Exception If there's an error during initialization
     */
    @Override
    public void init() throws Exception {
        super.init();
        this.setupUI();
    }

    /**
     * Sets up the user interface components and layout for the personal information form.
     * This method initializes all UI components and arranges them using GridBagLayout.
     *
     * <p><b>Component Groups and Their Purposes:</b></p>
     *
     * <h3>Form Header Components:</h3>
     * <ul>
     *   <li>headingLabel: Main form title displaying "Personal Information"</li>
     * </ul>
     *
     * <h3>Name Section Components:</h3>
     * <ul>
     *   <li>firstNameLabel/firstName: Required field for user's first name (20 chars max)</li>
     *   <li>middleNm: Optional checkbox to show/hide middle name field</li>
     *   <li>middleNameLabel/middleName: Optional field for user's middle name (20 chars max)</li>
     *   <li>lastNameLabel/lastName: Required field for user's last name (20 chars max)</li>
     * </ul>
     *
     * <h3>Contact Information Components:</h3>
     * <ul>
     *   <li>phoneNumberLabel/phoneNumberField: Field for phone number with automatic formatting</li>
     *   <li>emailLabel/email: Field for email address (20 chars max)</li>
     * </ul>
     *
     * <h3>Demographic Information Components:</h3>
     * <ul>
     *   <li>Gender selection radio buttons (maleRadioButton, femaleJRadioButton, pntsJRadioButton)</li>
     *   <li>Only one gender option can be selected at a time</li>
     * </ul>
     *
     * <h3>Address Components:</h3>
     * <ul>
     *   <li>numberField: House/building number (5 chars max)</li>
     *   <li>streetField: Street name (15 chars max)</li>
     *   <li>cityField: City name (10 chars max)</li>
     *   <li>stateDropdown: Dropdown with all US states</li>
     *   <li>zipField: 5-digit ZIP code with validation</li>
     * </ul>
     *
     * <h3>Residence Duration Components:</h3>
     * <ul>
     *   <li>Radio buttons for years at address (0-5, 6-10, 11-20, 21-30, 30+)</li>
     *   <li>Only one duration option can be selected at a time</li>
     * </ul>
     *
     * <h3>Action Components:</h3>
     * <ul>
     *   <li>clearButton: Resets all form fields to default values</li>
     *   <li>submitButton: Processes and submits form data</li>
     * </ul>
     *
     * <p><b>Validation Rules and Restrictions:</b></p>
     * <ul>
     *   <li>ZIP code must be exactly 5 digits</li>
     *   <li>Phone number must be valid Phone Number format</li>
     *   <li>Text fields have character limits as specified</li>
     *   <li>Middle name is only enabled when checkbox is selected</li>
     *   <li>No special characters allowed in name fields</li>
     *   <li>Only one gender option can be selected</li>
     *   <li>Only one residence duration can be selected</li>
     * </ul>
     */
    private void setupUI() {
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Adding components in a more organized manner
        gbc.gridx = 0;
        gbc.gridy = 0;
        // Initialize state selection array
        String[] states = {"State", "Alabama", "Alaska", "Arizona", "Arkansas", "California",
                "Colorado", "Connecticut", "Delaware", "Florida", "Georgia",
                "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas",
                "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts",
                "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana",
                "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico",
                "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma",
                "Oregon", "Pennsylvania", "Rhode Island", "South Carolina",
                "South Dakota", "Tennessee", "Texas", "Utah", "Vermont",
                "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"};

        // Initialize UI components in order of appearance
        // Header
        headingLabel = new JLabel("Personal Information");

        // Name section
        firstNameLabel = new JLabel("First Name");
        firstName = new JTextField(20);

        middleNm = new JCheckBox("Middle name");
        middleNameLabel = new JLabel("Middle Name:");
        middleName = new JTextField(20);
        middleNameLabel.setVisible(false);
        middleName.setVisible(false);

        lastNameLabel = new JLabel("Last Name");
        lastName = new JTextField(20);

        // Contact information
        phoneNumberLabel = new JLabel("Phone Number");
        phoneNumberField = new JFormattedTextField();
        phoneNumberField.setColumns(20);
        phoneNumberField.setValue("");
        JPanel phonePanel = new JPanel(new FlowLayout());
        phonePanel.add(phoneNumberField);
//        phonePanel.add(phoneTypeDropdown);
//        phonePanel.add(addPhoneButton);

        emailLabel = new JLabel("Email");
        email = new JTextField(20);

        // Gender selection
        maleRadioButton = new JRadioButton("Male");
        femaleJRadioButton = new JRadioButton("Female");
        pntsJRadioButton = new JRadioButton("Prefer not to say");
        radioButtonGroup = new ButtonGroup();
        radioButtonGroup.add(maleRadioButton);
        radioButtonGroup.add(femaleJRadioButton);
        radioButtonGroup.add(pntsJRadioButton);
        JPanel genderPanel = new JPanel(new GridLayout(1, 3));
        genderPanel.add(new JRadioButton("Male"));
        genderPanel.add(new JRadioButton("Female"));
        genderPanel.add(new JRadioButton("Prefer not to say"));


        // Address components
        currentAddressLabel = new JLabel("Current Address:");
        numberField = new JTextField(5);
        streetField = new JTextField(15);
        cityField = new JTextField(10);
        stateDropdown = new JComboBox<>(states);
        zipField = new JTextField(5);
        JPanel addressPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        addressPanel.add(new JLabel("Street Number:"));
        addressPanel.add(numberField);
        addressPanel.add(new JLabel("City:"));
        addressPanel.add(cityField);
        addressPanel.add(new JLabel("State:"));
        addressPanel.add(stateDropdown);
        addressPanel.add(new JLabel("ZIP:"));
        addressPanel.add(zipField);

        // Years at address
        JPanel yearsPanel = new JPanel(new GridLayout(1, 5));
        yearsPanel.add(new JRadioButton("0-5"));
        yearsPanel.add(new JRadioButton("6-10"));
        yearsPanel.add(new JRadioButton("11-20"));
        yearsPanel.add(new JRadioButton("21-30"));
        yearsPanel.add(new JRadioButton("30+"));

        // Action buttons
        clearButton = new JButton("Clear");
        submitButton = new JButton("Submit");

        // Set up frame
        ImageIcon icon = new ImageIcon("D:\\UI-Prototype\\Prototype\\Icon\\UNFinshedBusiness.png");
        page1Frame.setIconImage(icon.getImage());
//        page1Frame.setLayout(new GridBagLayout());
        page1Frame.setLocationRelativeTo(null);

        // Add event listeners
        middleNm.addActionListener(_ -> {
            boolean isChecked = middleNm.isSelected();
            middleNameLabel.setVisible(isChecked);
            middleName.setVisible(isChecked);
            page1Frame.pack();
        });

        phoneNumberField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (!phoneNumberField.getText().isBlank()) {
                    formatPhoneNumber();
                }
            }
        });

        submitButton.addActionListener(_ -> submitAndNextPage());
        clearButton.addActionListener(_ -> clearForm());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(clearButton);
        buttonPanel.add(submitButton);

        // Set up ZIP code validation
        ((AbstractDocument) zipField.getDocument()).setDocumentFilter(new DocumentFilter() {
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if ((fb.getDocument().getLength() + string.length()) <= 5 && string.matches("\\d*")) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attrs) throws BadLocationException {
                if ((fb.getDocument().getLength() + string.length() - length) <= 5 && string.matches("\\d*")) {
                    super.replace(fb, offset, length, string, attrs);
                }
            }
        });

        // Main panel and layout adjustments
        page1Frame.setLayout(new BorderLayout(10, 10));
        page1Frame.add(headingLabel, BorderLayout.NORTH);


        // Adding components in a more organized manner
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(firstNameLabel, gbc);
        gbc.gridx = 1;
        centerPanel.add(firstName, gbc);
        gbc.gridx = 2;
        centerPanel.add(middleNm, gbc);
        gbc.gridx = 1;
        gbc.gridy++;
        centerPanel.add(middleName, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        centerPanel.add(lastNameLabel, gbc);
        gbc.gridx = 1;
        centerPanel.add(lastName, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        centerPanel.add(emailLabel, gbc);
        gbc.gridx = 1;
        centerPanel.add(email, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        centerPanel.add(new JLabel("Gender:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        centerPanel.add(genderPanel, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy++;
        centerPanel.add(new JLabel("Phone Number:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        centerPanel.add(phonePanel, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy++;
        centerPanel.add(new JLabel("Address:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        centerPanel.add(addressPanel, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy++;
        centerPanel.add(new JLabel("Years at Address:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        centerPanel.add(yearsPanel, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy++;
        centerPanel.add(buttonPanel, gbc);

        page1Frame.add(centerPanel, BorderLayout.CENTER);
        page1Frame.pack();
        page1Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        page1Frame.setVisible(true);
    }

    /**
     * Validates form input fields for completeness and correctness, then proceeds to submit data if all requirements are met.
     * <p>
     * This method performs the following steps:
     * <ul>
     *     <li>Validates that all required text fields (first name, last name, phone number, email, address components) are filled.</li>
     *     <li>Ensures the email format is correct according to a specified regular expression pattern.</li>
     *     <li>Checks that at least one gender radio button is selected (male, female, or prefer not to say).</li>
     *     <li>Verifies that the state dropdown is not left at its default option ("State").</li>
     *     <li>Confirms that one of the "years at address" radio buttons is selected.</li>
     * </ul>
     * If any validation criteria are not met, an error message summarizing all detected issues is displayed to the user in a single popup.
     * If no issues are found, the form data is prepared for submission and printed to the console as a placeholder for further processing.
     * </p>
     *
     * <p>Note that middle name validation is conditional: if a "middle name" checkbox (middleNm) is selected, the middle name
     * text field value is retrieved; otherwise, the middle name is omitted from submission.</p>
     *
     * @throws IllegalArgumentException if an invalid regular expression pattern is provided for email validation.
     */
    private void submitAndNextPage() {
        StringBuilder errorMessages = new StringBuilder();

        // Check first name
        if (firstName.getText().isEmpty()) {
            errorMessages.append("First name is required.\n");
        }

        // Check last name
        if (lastName.getText().isEmpty()) {
            errorMessages.append("Last name is required.\n");
        }

        // Check phone number
        if (phoneNumberField.getText().isEmpty()) {
            errorMessages.append("Phone number is required.\n");
        }

        // Check email with regex pattern
        Pattern pattern = Pattern.compile(regex);
        String emailText = email.getText();
        Matcher matcher = pattern.matcher(emailText);
        if (emailText.isEmpty()) {
            errorMessages.append("Email is required.\n");
        } else if (!matcher.matches()) {
            errorMessages.append("Email format is invalid.\n");
        }

        // Check gender button group selection
        if (!maleRadioButton.isSelected() && !femaleJRadioButton.isSelected() && !pntsJRadioButton.isSelected()) {
            errorMessages.append("Please select a gender.\n");
        }

        // Check state dropdown selection
        if (Objects.equals(stateDropdown.getSelectedItem(), "State")) {
            errorMessages.append("Please select a state.\n");
        }

        // Check current address fields
        if (numberField.getText().isEmpty()) {
            errorMessages.append("House number is required.\n");
        }
        if (streetField.getText().isEmpty()) {
            errorMessages.append("Street is required.\n");
        }
        if (cityField.getText().isEmpty()) {
            errorMessages.append("City is required.\n");
        }
        if (zipField.getText().isEmpty()) {
            errorMessages.append("ZIP code is required.\n");
        }

        // Check years at address button group selection
        if (!zeroToFive.isSelected() && !sixToTen.isSelected() && !elevenToTwenty.isSelected()
                && !twentyToThirty.isSelected() && !thirtyPlus.isSelected()) {
            errorMessages.append("Please select years at address.\n");
        }

        // Show error messages if any issues were found
        if (errorMessages.length() > 0) {
            JOptionPane.showMessageDialog(page1Frame, errorMessages.toString(), "Input Errors", JOptionPane.ERROR_MESSAGE);
        } else {
            // Proceed if no errors
            String mName = middleNm.isSelected() ? middleName.getText() : null;
            String fName = firstName.getText();
            String lName = lastName.getText();
            String phoneNmber = phoneNumberField.getText();
            System.out.println(fName + " " + (mName != null ? mName + " " : "") + lName + " " + phoneNmber);
        }
    }


    /**
     * Clears all form fields and resets selections.
     * This includes:
     * - Clearing all text fields
     * - Resetting radio button selections
     * - Unchecking checkboxes
     * - Resetting the state dropdown to default
     * - Clearing the phone number field
     */
    private void clearForm() {
        // Iterate through each component in page1Frame
        for (Component component : page1Frame.getContentPane().getComponents()) {
            if (component instanceof JTextField) {
                ((JTextField) component).setText(""); // Clear text fields
            } else if (component instanceof JFormattedTextField) {
                ((JFormattedTextField) component).setText(""); // Clear formatted text fields
            } else if (component instanceof JRadioButton) {
                ((JRadioButton) component).setSelected(false); // Deselect radio buttons
            } else if (component instanceof JCheckBox) {
                ((JCheckBox) component).setSelected(false); // Deselect checkboxes
            }
        }
        // Clear button groups separately
        if (radioButtonGroup != null && yearsAtAdd != null) {
            radioButtonGroup.clearSelection();
            yearsAtAdd.clearSelection();// Clear radio button selection
        }
        if (!phoneNumberField.getText().isBlank() || !phoneNumberField.getText().isEmpty()) {
            phoneNumberField.setText("");
        }
        if (!Objects.equals(stateDropdown.getSelectedItem(), "State")) {
            stateDropdown.setSelectedItem("State");

        }
    }

    /**
     * Formats the phone number input using Google's libphonenumber library.
     * Validates and formats the phone number to international format.
     * Displays error messages if the number is invalid or cannot be parsed.
     */
    private void formatPhoneNumber() {
        String rawNumber = phoneNumberField.getText();
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

        try {
            // Parse number (assuming default country as 'US' for this example)
            Phonenumber.PhoneNumber numberProto = phoneUtil.parse(rawNumber, "US");

            if (phoneUtil.isValidNumber(numberProto)) {
                String formattedNumber = phoneUtil.format(numberProto, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
                phoneNumberField.setValue(formattedNumber);  // Sets formatted value
            } else {
                JOptionPane.showMessageDialog(page1Frame, "Invalid phone number!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberParseException e) {
            JOptionPane.showMessageDialog(page1Frame, "Error parsing phone number!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
