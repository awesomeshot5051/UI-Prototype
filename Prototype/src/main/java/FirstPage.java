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
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
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
    private final StringBuilder errorMessages = new StringBuilder();
    private final ArrayList<JPanel> phonePanels = new ArrayList<>();
    private final JButton addPhoneButton = new JButton("Add Phone Number");
    private final String[] states = {"State", "Alabama", "Alaska", "Arizona", "Arkansas", "California",
            "Colorado", "Connecticut", "Delaware", "Florida", "Georgia",
            "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas",
            "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts",
            "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana",
            "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico",
            "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma",
            "Oregon", "Pennsylvania", "Rhode Island", "South Carolina",
            "South Dakota", "Tennessee", "Texas", "Utah", "Vermont",
            "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"};
    private final ButtonGroup over18 = new ButtonGroup();
    JTextField numberField, streetField, cityField, stateField, zipField;
    private JLabel currentAddressLabel;
    private JTextField firstName;
    private JTextField lastName;
    private JCheckBox middleNm;
    private JLabel middleNameLabel;
    private JTextField middleName;
    private JTextField email;
    private JRadioButton maleRadioButton;
    private JRadioButton femaleJRadioButton;
    private JRadioButton pntsJRadioButton;
    private ButtonGroup radioButtonGroup;
    private JFormattedTextField weightFormattedTextField;
    private JFormattedTextField phoneNumberField;
    private JComboBox<String> stateDropdown;
    private ButtonGroup yearsAtAdd;
    private JRadioButton zeroToFive;
    private JRadioButton sixToTen;
    private JRadioButton elevenToTwenty;
    private JRadioButton twentyToThirty;
    private JRadioButton thirtyPlus;
    private JPanel contactInfoPanel;
    private GridBagConstraints gbc;
    //    private JTabbedPane tabbedPane;
    private JComboBox<String> phoneTypeDropdown;
    private int number = 8;
    private JPanel eciPanel;
    private JPanel addressPanel;
    private int contactCount = 0; // Counter to limit number of contacts
    private JButton addContactButton;
    private JTabbedPane tabbedPane;

    /**
     * Default constructor for FirstPage.
     * Initializes the UI components by calling setupUI().
     */
    FirstPage() {
        setupUI();
    }

    // Method to check if a button is part of a ButtonGroup
    private static boolean isButtonInGroup(JRadioButton button, ButtonGroup group) {
        var elements = group.getElements(); // Get enumeration of buttons in the group
        while (elements.hasMoreElements()) {
            if (elements.nextElement() == button) { // Directly compare JRadioButton instances
                return true; // Found the button in the group
            }
        }
        return false; // Button not found in the group
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
//        page1Frame = new JFrame("Personal Information");
        JPanel mainPanel = new JPanel(new BorderLayout());
        tabbedPane = new JTabbedPane(SwingConstants.TOP);
        JPanel namePanel = new JPanel(new GridBagLayout());
        contactInfoPanel = new JPanel(new GridBagLayout());
        JPanel combinedAddressPanel = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Initialize state selection array

        // Initialize UI components in order of appearance
        // Header
        JLabel headingLabel = new JLabel("Personal Information");

        // Name section
        JLabel firstNameLabel = new JLabel("First Name");
        firstName = new JTextField(20);

        middleNm = new JCheckBox("Middle name");
        middleNameLabel = new JLabel("Middle Name:");
        middleName = new JTextField(20);
        middleName.setName("Middle Name");
        middleNameLabel.setVisible(false);
        middleName.setVisible(false);
        // Add event listeners
        middleNm.addActionListener(_ -> {
            boolean isChecked = middleNm.isSelected();
            middleNameLabel.setVisible(isChecked);
            middleName.setVisible(isChecked);
            page1Frame.pack();
        });

        JLabel lastNameLabel = new JLabel("Last Name");
        lastName = new JTextField(20);
        lastName.setName("Last Name");
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        namePanel.add(firstNameLabel, gbc);
        gbc.gridx = 1;
        namePanel.add(firstName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        namePanel.add(middleNm, gbc);
        namePanel.add(middleNameLabel, gbc);
        gbc.gridx = 1;
        namePanel.add(middleName, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        namePanel.add(lastNameLabel, gbc);
        gbc.gridx = 1;
        namePanel.add(lastName, gbc);

        // Gender selection
        maleRadioButton = new JRadioButton("Male");
        femaleJRadioButton = new JRadioButton("Female");
        pntsJRadioButton = new JRadioButton("Prefer not to say");
        radioButtonGroup = new ButtonGroup();
        radioButtonGroup.add(maleRadioButton);
        radioButtonGroup.add(femaleJRadioButton);
        radioButtonGroup.add(pntsJRadioButton);
        gbc.gridx = 0;
        gbc.gridy = 4;
        namePanel.add(new JLabel("Gender"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        namePanel.add(maleRadioButton, gbc);
        gbc.gridy = 5;
        namePanel.add(femaleJRadioButton, gbc);
        gbc.gridy = 6;
        namePanel.add(pntsJRadioButton, gbc);
        JRadioButton over18True = new JRadioButton("Yes");
        JRadioButton over18False = new JRadioButton("No");
        over18.add(over18False);
        over18.add(over18True);
        gbc.gridx = 0;
        gbc.gridy++;
        namePanel.add(new JLabel("Are you over 18?"), gbc);
        gbc.gridx = 1;
//        gbc.gridy++;
        namePanel.add(over18True, gbc);
        gbc.gridy++;
        namePanel.add(over18False, gbc);


        contactInfoPanel = new JPanel(new GridBagLayout());
//        gbc = new GridBagConstraints();
//        gbc.insets = new Insets(5, 5, 5, 5);
//        gbc.anchor = GridBagConstraints.WEST;

        // Heading Label
        gbc.gridy = 0;
        gbc.gridx = 0;
        headingLabel = new JLabel("Contact Information");
        contactInfoPanel.add(headingLabel, gbc);


        // Add phone button

        // Email field
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel emailLabel = new JLabel("Email");
        contactInfoPanel.add(emailLabel, gbc);
        gbc.gridx = 1;
        email = new JTextField(20);
        email.setName("Email");
        contactInfoPanel.add(email, gbc);
        gbc.gridx = 1;
        gbc.gridy++;
        // Primary phone number field and label
        addPhoneField(false, contactInfoPanel, addPhoneButton);
        gbc.gridy++;
        addPhoneButton.setToolTipText("Max 8 additions allowed");
        addPhoneButton.addActionListener(_ -> {
            addPhoneField(true, contactInfoPanel, addPhoneButton);
            if (number <= 0) {
                addPhoneButton.setToolTipText("No more additions allowed!");
            } else if (number >= 8) {
                addPhoneButton.setToolTipText("Max " + number + " additions allowed");
            } else {
//                if (number >= 4) {
//                    addScrollPane(contactInfoPanel);
//                }
                addPhoneButton.setToolTipText("You have " + number + " additions left");
            }
            page1Frame.pack();
        });
        contactInfoPanel.add(addPhoneButton, gbc);

//        JScrollPane scrollPane = new JScrollPane(contactInfoPanel);
//        scrollPane.setPreferredSize(new Dimension((int) WIDTH, (int) HEIGHT));
//        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
//        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        // Address components
        currentAddressLabel = new JLabel("Current Address:");
        numberField = new JTextField(5);
        numberField.setName("Street Number");
        streetField = new JTextField(15);
        streetField.setName("Street Name");
        cityField = new JTextField(10);
        cityField.setName("City");
        stateDropdown = new JComboBox<>(states);
        zipField = new JTextField(5);
        zipField.setName("Zip Code");
        // Create a new panel for the address and years sections

//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

// Row 1: Street Number
        gbc.gridx = 0;
        gbc.gridy = 0;
        combinedAddressPanel.add(new JLabel("Street Number:"), gbc);
        gbc.gridx = 1;
        combinedAddressPanel.add(numberField, gbc);

// Row 2: Street Name
        gbc.gridx = 0;
        gbc.gridy = 1;
        combinedAddressPanel.add(new JLabel("Street Name:"), gbc);
        gbc.gridx = 1;
        combinedAddressPanel.add(streetField, gbc);

// Row 3: City
        gbc.gridx = 0;
        gbc.gridy = 2;
        combinedAddressPanel.add(new JLabel("City:"), gbc);
        gbc.gridx = 1;
        combinedAddressPanel.add(cityField, gbc);

        // Row 4: ZIP Code
        gbc.gridx = 0;
        gbc.gridy = 3;
        combinedAddressPanel.add(new JLabel("ZIP:"), gbc);
        gbc.gridx = 1;
        combinedAddressPanel.add(zipField, gbc);

        // Row 5: State Dropdown
        gbc.gridx = 0;
        gbc.gridy = 4;
        combinedAddressPanel.add(new JLabel("State:"), gbc);
        gbc.gridx = 1;
        stateDropdown.setName("State");
        combinedAddressPanel.add(stateDropdown, gbc);

// Row 6: Years at Address
        JPanel yearsPanel = new JPanel(new GridLayout(1, 5, 5, 5));
        zeroToFive = new JRadioButton("0-5");
        sixToTen = new JRadioButton("6-10");
        elevenToTwenty = new JRadioButton("11-20");
        twentyToThirty = new JRadioButton("21-30");
        thirtyPlus = new JRadioButton("30+");
        yearsPanel.add(zeroToFive);
        yearsPanel.add(sixToTen);
        yearsPanel.add(elevenToTwenty);
        yearsPanel.add(twentyToThirty);
        yearsPanel.add(thirtyPlus);

// Group radio buttons
        yearsAtAdd = new ButtonGroup();
        yearsAtAdd.add(zeroToFive);
        yearsAtAdd.add(sixToTen);
        yearsAtAdd.add(elevenToTwenty);
        yearsAtAdd.add(twentyToThirty);
        yearsAtAdd.add(thirtyPlus);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;  // Span across both label and field columns
        combinedAddressPanel.add(new JLabel("Years at Address:"), gbc);
        gbc.gridy = 6;
        combinedAddressPanel.add(yearsPanel, gbc);

//        yearsPanel.add(yearsAtAdd);

        // Action buttons
        JButton clearButton = new JButton("Clear");
        JButton submitButton = new JButton("Submit");

        // Set up frame
        ImageIcon icon = new ImageIcon("D:\\UI-Prototype\\Prototype\\Icon\\UNFinshedBusiness.png");
        page1Frame.setIconImage(icon.getImage());
//        page1Frame.setLayout(new GridBagLayout());
        page1Frame.setLocationRelativeTo(null);


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
        ((AbstractDocument) numberField.getDocument()).setDocumentFilter(new DocumentFilter() {
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if ((fb.getDocument().getLength() + string.length()) <= 7 && string.matches("\\d*")) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attrs) throws BadLocationException {
                if ((fb.getDocument().getLength() + string.length() - length) <= 7 && string.matches("\\d*")) {
                    super.replace(fb, offset, length, string, attrs);
                }
            }
        });

        // Main panel and layout adjustments
        page1Frame.setLayout(new BorderLayout(10, 10));
//        page1Frame.add(headingLabel, BorderLayout.NORTH);

//        gbc.gridx = 0;
//        gbc.gridy++;
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        eciPanel = new JPanel(new GridBagLayout());
        gbc.gridy = 0;
        gbc.gridx = 0;
        addContactButton = new JButton("Add contact");
        addContactButton.addActionListener(_ -> {
            if (++contactCount > 2) {
                addScrollPane(eciPanel);
            }
//            createEmergencyContactPanel(true, eciPanel);
        });

//        createEmergencyContactPanel(false, eciPanel);
        gbc.gridy++;
        eciPanel.add(addContactButton, gbc);

//        tabbedPane.addTab("Name and number", scrollPane);
        tabbedPane.addTab("General Information", namePanel);
        tabbedPane.addTab("Contact Information", contactInfoPanel);
        tabbedPane.addTab("Address", combinedAddressPanel);
//        tabbedPane.addTab("Emergency Contact Information", scrollPane);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        // Adding components in a more organized manner
//        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        page1Frame.add(tabbedPane, BorderLayout.NORTH);
        page1Frame.add(mainPanel, BorderLayout.CENTER);
        page1Frame.setSize((int) WIDTH, (int) HEIGHT);
        page1Frame.pack();
        page1Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        page1Frame.setLocationRelativeTo(null);
        page1Frame.setVisible(true);
//        tabbedPane.list();
        firstName.setName("First Name");
    }

    private void addScrollPane(JPanel namePanel) {
        JScrollPane scrollPane = new JScrollPane(namePanel);
        scrollPane.setPreferredSize(new Dimension((int) WIDTH, (int) HEIGHT));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    }

    /**
     * Adds a phone field to the specified JPanel, optionally allowing for
     * multiple entries. A dropdown for phone types is also included.
     *
     * @param isDuplicate Indicates if the phone field is a duplicate.
     * @param panel       The JPanel to which the phone field will be added.
     * @param button      The JButton used to trigger adding/removing phone fields.
     *                    This method also handles the display of the phone number label and
     *                    includes a remove button if the phone field is a duplicate.
     */
    private void addPhoneField(boolean isDuplicate, JPanel panel, JButton button) {
        // Create a new panel for the phone field and type dropdown
        JPanel phonePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Phone number label and field only for the first instance
        if (!isDuplicate) {
            JLabel phoneNumberLabel = new JLabel("Phone Number");
            gbc.gridx = 0;
            gbc.gridy++; // Increment row for each new panel
            panel.add(phoneNumberLabel, gbc);
        }

        // Create the phone number field
        JFormattedTextField phoneNumber = new JFormattedTextField();
        phoneNumber.setColumns(15);
        phoneNumber.setValue("");
        phoneNumber.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent evt) {
                if (!phoneNumber.getText().isBlank()) {
                    formatPhoneNumber(phoneNumber);
                }
            }
        });
        phonePanel.add(phoneNumber);

        // Phone type dropdown
        String[] phoneTypes = {"Home", "Mobile", "Other"};
        JComboBox<String> phoneTypeDropdown = new JComboBox<>(phoneTypes);
        phonePanel.add(phoneTypeDropdown);

        // Add remove button if it's a duplicate field
        if (isDuplicate) {
            if (number <= 0) {
                return;
            } else {
                JButton removeButton = new JButton("Remove");
                removeButton.addActionListener(e -> removePhoneField(phonePanel, button, panel));
                phonePanel.add(removeButton);
                gbc.gridy++;
                number--;
            }
        }

        // Add phone panel to the list and to the main contact info panel
        phonePanels.add(phonePanel);
        gbc.gridx = 1; // Set to the column for the new phone field
        // Use size for the correct y position (1 for the first field, 2 for the second, etc.)

        // Add the new phone panel to the contact information panel
        panel.add(phonePanel, gbc);
//        gbc.gridy++;
//        contactInfoPanel.add(addPhoneButton, gbc);

        // Refresh the layout
        panel.revalidate();
        panel.repaint();
        page1Frame.pack();
    }


    /**
     * Removes a specified phone field from the provided JPanel and updates
     * the associated button's tooltip to reflect the number of allowed additions.
     *
     * @param phonePanel The JPanel representing the phone field to be removed.
     * @param button     The JButton that controls the addition of phone fields.
     * @param panel      The JPanel containing the phone field to be removed.
     *                   This method also refreshes the layout of the panel and updates the
     *                   number of remaining additions allowed.
     */
    private void removePhoneField(JPanel phonePanel, JButton button, JPanel panel) {
        phonePanels.remove(phonePanel);
        panel.remove(phonePanel);
        panel.revalidate();
        panel.repaint();
        page1Frame.pack();
        number++;
        if (number >= 8) {
            button.setToolTipText("Max " + number + " additions allowed");
        } else {
            button.setToolTipText("You have " + number + " additions left");
        }
    }

    /**
     * Validates the input from all components in the tabbed pane and processes the form data.
     * This method iterates through each tab of the tabbed pane, checking for required fields,
     * and collects error messages if any validation fails.
     * <p>
     * If any errors are found, they are displayed in a dialog box. If the validation is successful,
     * the method processes the valid form data.
     */
    private void submitAndNextPage() {
        errorMessages.setLength(0); // Clear previous error messages
        Component component = tabbedPane.getComponentAt(1);

        // Iterate over all components in each tab
        for (Component tab : tabbedPane.getComponents()) {
            if (tab instanceof JPanel) {
                validateComponents((JPanel) tab);
            }
        }

        // Display error messages if any issues were found
        if (!errorMessages.isEmpty()) {
            JOptionPane.showMessageDialog(page1Frame, errorMessages.toString(), "Input Errors", JOptionPane.ERROR_MESSAGE);
        } else {
            processValidFormData(); // Process form data if no errors
        }
    }

    /**
     * Validates all components within the specified panel.
     * <p>
     * This method checks each component in the panel, verifying text fields for completeness,
     * formatted text fields (like phone numbers) for correctness, dropdown selections for validity,
     * and radio buttons for group selection.
     * </p>
     *
     * @param panel The JPanel containing the components to validate.
     */
    private void validateComponents(JPanel panel) {
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JTextField) {
                if (comp instanceof JFormattedTextField) {
                    validateFormattedTextField((JFormattedTextField) comp);
                } else {
                    validateTextField((JTextField) comp);
                }
            } else if (comp instanceof JComboBox) {
                validateDropdown((JComboBox<?>) comp);
            } else if (comp instanceof JPanel) {
                validateComponents((JPanel) comp); // Recursive call for nested panels
            } else if (comp instanceof JRadioButton) {
                if (isButtonInGroup((JRadioButton) comp, radioButtonGroup)) {
                    validateButtonGroup(radioButtonGroup);
                } else if (isButtonInGroup((JRadioButton) comp, yearsAtAdd)) {
                    validateButtonGroup(yearsAtAdd);
                } else if (isButtonInGroup((JRadioButton) comp, over18)) {
                    validateButtonGroup(over18);
                }
            }
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
    private void validateTextField(JTextField field) {
        if (field.getText().isEmpty() && !Objects.equals(field.getName(), "Middle Name")) {
            errorMessages.append(field.getName()).append(" is required!\n");
        } else {
            if (Objects.equals(field.getName(), "Email")) {
                Pattern pattern = Pattern.compile(regex);
                String emailText = email.getText();
                Matcher matcher = pattern.matcher(emailText);
                if (emailText.isEmpty()) {
                    errorMessages.append("Email is required!\n");
                } else if (!matcher.matches()) {
                    errorMessages.append("Email format is invalid!\n");
                }
            }
        }
    }

    /**
     * Validates a JFormattedTextField (in this case the only JFormattedTextField is the phone number field) to ensure it is not empty.
     * <p>
     * If the field is filled, it calls the method to format the phone number.
     * </p>
     *
     * @param field The JFormattedTextField to validate.
     */
    private void validateFormattedTextField(JFormattedTextField field) {
        if (field.getText().isEmpty()) {
            errorMessages.append("Phone number is required!\n");
        } else {
            formatPhoneNumber(field); // Formats if valid
        }
    }

    /**
     * Validates a JComboBox to ensure that a selection is made that is not the default option.
     * <p>
     * In this implementation, it checks that the dropdown is not left on the default item "State".
     * </p>
     *
     * @param dropdown The JComboBox to validate.
     */
    private void validateDropdown(JComboBox<?> dropdown) {
        if (Objects.equals(dropdown.getSelectedItem(), "State")) { // Customize for your dropdown
            errorMessages.append(dropdown.getName()).append(" must be selected!\n");
        }
    }

    /**
     * Validates a ButtonGroup to ensure at least one button is selected.
     * <p>
     * If no selection is made, it appends a relevant error message based on the specific button group being checked.
     * </p>
     *
     * @param group The ButtonGroup to validate.
     */
    private void validateButtonGroup(ButtonGroup group) {
        if (group.getSelection() == null) {
            String message = yearsAtAdd == group ? "Years at Address is required!\n" :
                    radioButtonGroup == group ? "Gender Selection Required!\n" :
                            over18 == group ? "You need to say whether you're over 18!\n" : "Unknown selection is required!\n";
            if (!errorMessages.toString().contains(message)) {
                errorMessages.append(message);
            }
        }

    }

    /**
     * Processes the form data when all inputs are valid.
     * <p>
     * This method retrieves the user's input data, including the first name, middle name (if applicable),
     * last name, and other information, then prints it to the console for further processing.
     * </p>
     */
    private void processValidFormData() {
        new refrencesForm();
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
    private void formatPhoneNumber(JFormattedTextField phoneNumber) {
        String rawNumber = phoneNumber.getText();
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

        try {
            // Parse number (assuming default country as 'US' for this example)
            Phonenumber.PhoneNumber numberProto = phoneUtil.parse(rawNumber, "US");

            if (phoneUtil.isValidNumber(numberProto)) {
                String formattedNumber = phoneUtil.format(numberProto, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
                phoneNumber.setValue(formattedNumber);  // Sets formatted value
            } else {
//                JOptionPane.showMessageDialog(page1Frame, "Invalid phone number!", "Error", JOptionPane.ERROR_MESSAGE);
                errorMessages.append("Invalid phone number!\n");

            }
        } catch (NumberParseException e) {
//            JOptionPane.showMessageDialog(page1Frame, "Error parsing phone number!", "Error", JOptionPane.ERROR_MESSAGE);
            errorMessages.append("Error parsing phone number!\n");
        }
    }
}
