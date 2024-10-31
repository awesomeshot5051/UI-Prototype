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

public class FirstPage extends Application {
    private static final double WIDTH = 640;
    private static final double HEIGHT = 480;
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
    private JSpinner yearsAtAdd;

    FirstPage() {
        setupUI();
    }

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

    @Override
    public void init() throws Exception {
        super.init();
        this.setupUI();
    }

    private void setupUI() {
        String[] states = {"Alabama", "Alaska", "Arizona", "Arkansas", "California",
                "Colorado", "Connecticut", "Delaware", "Florida", "Georgia",
                "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas",
                "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts",
                "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana",
                "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico",
                "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma",
                "Oregon", "Pennsylvania", "Rhode Island", "South Carolina",
                "South Dakota", "Tennessee", "Texas", "Utah", "Vermont",
                "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"};

        stateDropdown = new JComboBox<>(states);
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(5, 0, 95, 1);
        yearsAtAdd = new JSpinner(spinnerModel);

//        JTextField textfield = new JTextField("Something", 20);
//        BorderPane borderPane = new BorderPane();
        phoneNumberField = new JFormattedTextField();
        phoneNumberField.setColumns(20);
        phoneNumberField.setValue("");  // Start with empty value

        // Format the number on focus loss
        phoneNumberField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (phoneNumberField.getText().isBlank()) {
                } else {
                    formatPhoneNumber();
                }
            }
        });
        // Set the PhoneNumberField max width to 200.0px


        ImageIcon icon = new ImageIcon("D:\\UI-Prototype\\Prototype\\Icon\\UNFinshedBusiness.png");
        page1Frame.setIconImage(icon.getImage());
        page1Frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        page1Frame.setLocationRelativeTo(null);
        // Initializing components
        headingLabel = new JLabel("Personal Information");
        firstNameLabel = new JLabel("First Name");
        firstName = new JTextField(20);
        middleNm = new JCheckBox("Middle name");
        middleNm.addActionListener(_ -> {
            boolean isChecked = middleNm.isSelected();
            middleNameLabel.setVisible(isChecked);
            middleName.setVisible(isChecked);

            if (isChecked) {
                page1Frame.pack();  // Adjust the frame size to fit new content
            } else {
                page1Frame.pack();  // Adjust the frame size back to fit reduced content
            }
        });

        middleNameLabel = new JLabel("Middle Name:");
        middleName = new JTextField(20);
        // Initially hide the middle name fields
        middleNameLabel.setVisible(false);
        middleName.setVisible(false);
        lastNameLabel = new JLabel("Last Name");
        lastName = new JTextField(20);
        phoneNumberLabel = new JLabel("Phone Number");

//        // Set up the phone number field with a mask formatter
//        try {
//            MaskFormatter phoneFormatter = new MaskFormatter("(###) ###-####");
////            phoneFormatter.setPlaceholderCharacter('_');
////            phoneNumberField = new JFormattedTextField(phoneFormatter);
//            phoneNumber.setColumns(20);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        emailLabel = new JLabel("Email");
        email = new JTextField(20);
        maleRadioButton = new JRadioButton("Male");
        femaleJRadioButton = new JRadioButton("Female");
        pntsJRadioButton = new JRadioButton("Prefer not to say");
        radioButtonGroup = new ButtonGroup();
        radioButtonGroup.add(maleRadioButton);
        radioButtonGroup.add(femaleJRadioButton);
        radioButtonGroup.add(pntsJRadioButton);
        currentAddressLabel = new JLabel("Current Address:");
        numberField = new JTextField(5);
        streetField = new JTextField(15);
        cityField = new JTextField(10);
        stateField = new JTextField(2);
        zipField = new JTextField(5);
        weightFormattedTextField = new JFormattedTextField();
        clearButton = new JButton("Clear");
        submitButton = new JButton("Submit");
        submitButton.addActionListener(_ -> {
            submitAndNextPage();
        });

        // Limit ZIP field to accept only 5 digits
        ((AbstractDocument) zipField.getDocument()).setDocumentFilter(new DocumentFilter() {
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if ((fb.getDocument().getLength() + string.length()) <= 5 && string.matches("\\d*")) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String string, AttributeSet attrs) throws BadLocationException {
                if ((fb.getDocument().getLength() + string.length() - length) <= 5 && string.matches("\\d*")) {
                    super.replace(fb, offset, length, string, attrs);
                }
            }
        });

// Adding components with constraints
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;

// Heading
        page1Frame.add(headingLabel, gbc);

// First Name
        gbc.gridy = 1;
        page1Frame.add(firstNameLabel, gbc);
        gbc.gridx = 1;
        page1Frame.add(firstName, gbc);

// Middle Name Checkbox (in between First and Last Name)
        gbc.gridy = 2;
        gbc.gridx = 0;
        page1Frame.add(middleNm, gbc);

// Middle Name Label and Field (initially hidden)
        gbc.gridy = 3;
        gbc.gridx = 0;
        page1Frame.add(middleNameLabel, gbc);
        gbc.gridx = 1;
        page1Frame.add(middleName, gbc);

// Last Name
        gbc.gridy = 4;
        gbc.gridx = 0;
        page1Frame.add(lastNameLabel, gbc);
        gbc.gridx = 1;
        page1Frame.add(lastName, gbc);

// Phone Number
        gbc.gridy = 5;
        gbc.gridx = 0;
        page1Frame.add(phoneNumberLabel, gbc);
        gbc.gridx = 1;
        page1Frame.add(phoneNumberField, gbc);

// Email
        gbc.gridy = 6;
        gbc.gridx = 0;
        page1Frame.add(emailLabel, gbc);
        gbc.gridx = 1;
        page1Frame.add(email, gbc);

// Sex Radio Buttons
        gbc.gridy = 7;
        gbc.gridx = 0;
        page1Frame.add(new JLabel("Sex"), gbc);
        gbc.gridx = 1;
        page1Frame.add(maleRadioButton, gbc);
        gbc.gridy = 8;
        page1Frame.add(femaleJRadioButton, gbc);
        gbc.gridy = 9;
        page1Frame.add(pntsJRadioButton, gbc);

// Current Address
        gbc.gridy = 10;
        gbc.gridx = 0;
        page1Frame.add(currentAddressLabel, gbc);
        gbc.gridy = 11;
        gbc.gridx = 0;
        page1Frame.add(new JLabel("Number"), gbc);
        gbc.gridx = 1;
        page1Frame.add(numberField, gbc);
        gbc.gridx = 2;
        page1Frame.add(new JLabel("Street"), gbc);
        gbc.gridx = 3;
        page1Frame.add(streetField, gbc);

        gbc.gridy = 12;
        gbc.gridx = 0;
        page1Frame.add(new JLabel("City"), gbc);
        gbc.gridx = 1;
        page1Frame.add(cityField, gbc);
        gbc.gridx = 2;
        page1Frame.add(new JLabel("State"), gbc);
        gbc.gridx = 3;
        page1Frame.add(stateDropdown, gbc);
        gbc.gridx = 4;
        page1Frame.add(new JLabel("ZIP"), gbc);
        gbc.gridx = 5;
        page1Frame.add(zipField, gbc);

        gbc.gridy = 13;
        gbc.gridx = 0;
        page1Frame.add(new JLabel("How many years have you lived at this address?"), gbc);
        gbc.gridx = 1;
        page1Frame.add(yearsAtAdd, gbc);
        // Buttons (Clear and Submit)
        gbc.gridy = 25;
        gbc.gridx = 0;
        page1Frame.add(clearButton, gbc);
        gbc.gridx = 1;
        page1Frame.add(submitButton, gbc);
        clearButton.addActionListener(_ -> {
            clearForm();
        });

// Final frame setup
        page1Frame.pack();
        page1Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        page1Frame.setVisible(true);

    }

    private void submitAndNextPage() {
        String mName = null;
        String fName = firstName.getText();
        if (middleNm.isSelected()) {
            mName = middleName.getText();
        }

        String lName = lastName.getText();
        String phoneNmber = phoneNumberField.getText();
        System.out.println(fName + " " + (mName != null ? mName + " " : "") + lName + phoneNmber);
    }

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
        if (radioButtonGroup != null) {
            radioButtonGroup.clearSelection(); // Clear radio button selection
        }
    }

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
