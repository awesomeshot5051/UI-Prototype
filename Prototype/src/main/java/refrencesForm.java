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

public class refrencesForm extends JFrame {

    private static final String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    //JPanels
    private final JPanel mainPanel;
    private final JPanel refrencePanel;
    private final JPanel headerButtonPanel;
    private final JPanel footerButtonPanel;
    private final StringBuilder errorMessages = new StringBuilder();
    //JButtons
    private final JButton addRefrenceButton;
    private final JButton clearMostRecentButton;
    private final JButton nextPage;
    //JLabes
    private final JLabel headerTitle;
    private final ArrayList<JPanel> refrenceFieldsList = new ArrayList<>();
    private int entryNumber = 0; //used to keep track of fields created

    public refrencesForm() {
        // Set up main frame
        setTitle("Refrences Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400); // Adjusted size to accommodate fields better

        JLabel headerLabel = new JLabel("Refrences", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));

        // Main panel to hold all components
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Panel to hold dynamic experience fields
        refrencePanel = new JPanel();
        refrencePanel.setLayout(new BoxLayout(refrencePanel, BoxLayout.Y_AXIS));

        // Scroll pane for experience fields
        JScrollPane scrollPane = new JScrollPane(refrencePanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        //initializing all components for header
        clearMostRecentButton = new JButton("Clear Most Recent Entry");
        addRefrenceButton = new JButton("Add Reference");

        headerTitle = new JLabel("References");
        headerTitle.setFont(new Font("Arial", Font.BOLD, 24));
        headerTitle.setHorizontalAlignment(JLabel.CENTER); // Center the text within the label
        headerTitle.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        headerButtonPanel = new JPanel(new BorderLayout());
        headerButtonPanel.add(addRefrenceButton, BorderLayout.WEST);
        headerButtonPanel.add(headerTitle, BorderLayout.CENTER);
        headerButtonPanel.add(clearMostRecentButton, BorderLayout.EAST);
        mainPanel.add(headerButtonPanel, BorderLayout.NORTH);

        nextPage = new JButton("Next Page");
        footerButtonPanel = new JPanel(new BorderLayout());
        footerButtonPanel.add(nextPage, BorderLayout.EAST);
        mainPanel.add(footerButtonPanel, BorderLayout.SOUTH);


        // Action listeners for buttons
        addRefrenceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addReferenceFields();
            }
        });

        clearMostRecentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearMostRecentReferenceField();
            }
        });

        nextPage.addActionListener(_ -> validateFields());

        add(mainPanel);
        setVisible(true);
    }

    /**
     * Processes the form data when all inputs are valid.
     * <p>
     * This method retrieves the user's input data, including the first name, middle name (if applicable),
     * last name, and other information, then prints it to the console for further processing.
     * </p>
     */
    public static boolean formatPhoneNumber(JFormattedTextField phoneNumber, StringBuilder errorMessages, String panelName) {
        String rawNumber = phoneNumber.getText();
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

        try {
            // Parse number (assuming default country as 'US' for this example)
            Phonenumber.PhoneNumber numberProto = phoneUtil.parse(rawNumber, "US");

            if (phoneUtil.isValidNumber(numberProto)) {
                String formattedNumber = phoneUtil.format(numberProto, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
                phoneNumber.setValue(formattedNumber);  // Sets formatted value
            } else {
                if (!errorMessages.toString().contains("Invalid phone number for " + panelName + "\n")) {
                    errorMessages.append("Invalid phone number for ").append(panelName).append("!\n");
                }
                return false;
            }
        } catch (NumberParseException e) {
//            JOptionPane.showMessageDialog(page1Frame, "Error parsing phone number!", "Error", JOptionPane.ERROR_MESSAGE);
            errorMessages.append("Error parsing phone number for ").append(panelName).append("!\n");
            return false;
        }
        return true;
    }

    /* This function is used to populate the interface
     *  with the fields to enter information */
    private void addReferenceFields() {

        entryNumber++;
        JPanel referenceFields = new JPanel();
        referenceFields.setName("Company " + entryNumber);
        referenceFields.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding between components

        referenceFields.setBorder(BorderFactory.createTitledBorder("Reference " + entryNumber));

        // First Row: Employer Name and Job Title
        gbc.gridx = 0; // Column 0
        gbc.gridy = 0; // Row 0
        gbc.weightx = 1; // Expand horizontally
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JTextField employerField = new JTextField(10);
        employerField.setBorder(BorderFactory.createTitledBorder("Name"));
        employerField.setName("Employer");
        referenceFields.add(employerField, gbc);

        gbc.gridx = 1; // Column 1
        JTextField jobTitleField = new JTextField(10);
        jobTitleField.setBorder(BorderFactory.createTitledBorder("Position"));
        jobTitleField.setName("Position");
        referenceFields.add(jobTitleField, gbc);

        // Second Row: Start Date, End Date
        gbc.gridx = 0; // Column 0
        gbc.gridy = 1; // Row 1
        JTextField startDayField = new JTextField(10);
        startDayField.setBorder(BorderFactory.createTitledBorder("Company"));
        referenceFields.add(startDayField, gbc);
        startDayField.setName("Company");

        gbc.gridx = 1; // Column 1
        JFormattedTextField phoneNumber = new JFormattedTextField();
        phoneNumber.setBorder(BorderFactory.createTitledBorder("Phone Number"));
        referenceFields.add(phoneNumber, gbc);
        phoneNumber.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent evt) {
                if (!phoneNumber.getText().isBlank()) {
                    formatPhoneNumber(phoneNumber, errorMessages, referenceFields.getName());
                }
            }
        });
        phoneNumber.setName("Phone Number");

        // Third Row: Address related fields
        gbc.gridx = 0; // Column 0
        gbc.gridy = 2; // Row 2
        JTextField addressField = new JTextField(10);
        addressField.setBorder(BorderFactory.createTitledBorder("Email"));
        addressField.setName("Email");
        referenceFields.add(addressField, gbc);


        // State Dropdown
        gbc.gridx = 1; // Column 1
        gbc.gridy = 3; // Same row as zip


        refrenceFieldsList.add(referenceFields);

        refrencePanel.add(referenceFields);

        refrencePanel.revalidate(); // Refresh the panel to show new fields
        refrencePanel.repaint();
    }

    /**
     * clearMostRecent function
     * Grabs the most recent field added and removes it from the interface
     */
    private void clearMostRecentReferenceField() {

        if (!refrenceFieldsList.isEmpty()) {

            //targets the last panel added and removes from arrayList assigns to JPanel
            JPanel lastExperienceField = refrenceFieldsList.removeLast();

            //removing panel from experience panel
            refrencePanel.remove(lastExperienceField);

            //refresh page
            refrencePanel.revalidate();
            refrencePanel.repaint();

            //decrementing entry number to let user keep track of number of fields added
            entryNumber--;
        }

    }

    private void validateFields() {
        boolean allFieldsFilled = true;

        for (JPanel panel : refrenceFieldsList) {
            String panelName = panel.getName() != null ? panel.getName() : "Unnamed Reference";
            for (Component component : panel.getComponents()) {
                if (component instanceof JTextField textField) {
                    if (component instanceof JFormattedTextField) {
                        allFieldsFilled &= validateFormattedTextField((JFormattedTextField) component, panelName);
                    } else {
                        allFieldsFilled &= validateTextField(textField, panelName);
                    }
                } else if (component instanceof JComboBox<?> comboBox) {
                    allFieldsFilled &= validateDropdown(comboBox, panelName);
                }
            }
//            if (!allFieldsFilled) break;
        }

        if (!allFieldsFilled) {
            JOptionPane.showMessageDialog(this, errorMessages.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            errorMessages.setLength(0);
        } else {
            nextPage();
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
    private boolean validateTextField(JTextField field, String panelName) {
        if (field.getText().isEmpty()) {
            errorMessages.append(field.getName()).append(" is required for ").append(panelName).append("\n");
            return false;
        } else {
            if (Objects.equals(field.getName(), "Email")) {
                Pattern pattern = Pattern.compile(regex);
                String emailText = field.getText();
                Matcher matcher = pattern.matcher(emailText);
                if (emailText.isEmpty()) {
                    errorMessages.append("Email is required for").append(panelName).append("\n");
                    return false;
                } else if (!matcher.matches()) {
                    errorMessages.append("Email format is invalid for ").append(panelName).append("\n");
                    return false;
                }
            }
        }
        return true;
    }

    //    /**
//     * Validates a JFormattedTextField (in this case the only JFormattedTextField is the phone number field) to ensure it is not empty.
//     * <p>
//     * If the field is filled, it calls the method to format the phone number.
//     * </p>
//     *
//     * @param field The JFormattedTextField to validate.
//     */
    private boolean validateFormattedTextField(JFormattedTextField field, String panelName) {
        if (field.getText().isEmpty()) {
            errorMessages.append("Phone number is required for ").append(panelName).append("\n");
            return false;
        } else {
            return formatPhoneNumber(field, errorMessages, panelName); // Formats if valid\
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
    private boolean validateDropdown(JComboBox<?> dropdown, String panelName) {
        if (dropdown.getSelectedIndex() == 0) { // Customize for your dropdown
            errorMessages.append(dropdown.getName()).append(" must be selected!\n");
            return false;
        }
        return true;
    }

    //    /**
//     * Validates a ButtonGroup to ensure at least one button is selected.
//     * <p>
//     * If no selection is made, it appends a relevant error message based on the specific button group being checked.
//     * </p>
//     *
//     * @param group The ButtonGroup to validate.
//     */
//    private void validateButtonGroup(ButtonGroup group) {
//        if (group.getSelection() == null) {
//            String message = yearsAtAdd == group ? "Years at Address is required!\n" :
//                    radioButtonGroup == group ? "Gender Selection Required!\n" :
//                            over18 == group ? "You need to say whether you're over 18!\n" : "Unknown selection is required!\n";
//            if (!errorMessages.toString().contains(message)) {
//                errorMessages.append(message);
//            }
//        }
//
//    }
    public void nextPage() {
        SwingUtilities.invokeLater(() -> {
            dispose();
            new educationForm().setVisible(true);
        });
    }

}
