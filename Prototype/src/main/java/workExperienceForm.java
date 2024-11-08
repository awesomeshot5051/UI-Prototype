import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
    private int entryNumber = 0; //used to keep track of fields created

    public workExperienceForm() {
        // Set up main frame
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
        addExperienceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addExperienceFields();
            }
        });

        clearMostRecentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearMostRecentExperienceField();
            }
        });

        nextPage.addActionListener(e -> validateFields());

        add(mainPanel);
        setVisible(true);
    }


    /* This function is used to populate the interface
     *  with the fields to enter information */
    private void addExperienceFields() {

        entryNumber++;

        JPanel experienceFields = new JPanel();
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

        gbc.gridx = 1; // Column 1
        JTextField jobTitleField = new JTextField(10);
        jobTitleField.setBorder(BorderFactory.createTitledBorder("Job Title"));
        experienceFields.add(jobTitleField, gbc);

        // Second Row: Start Date, End Date
        gbc.gridx = 0; // Column 0
        gbc.gridy = 1; // Row 1
        JTextField startDayField = new JTextField(10);
        startDayField.setBorder(BorderFactory.createTitledBorder("Start Date"));
        experienceFields.add(startDayField, gbc);

        gbc.gridx = 1; // Column 1
        JTextField endDayField = new JTextField(10);
        endDayField.setBorder(BorderFactory.createTitledBorder("End Date"));
        experienceFields.add(endDayField, gbc);

        // Third Row: Address related fields
        gbc.gridx = 0; // Column 0
        gbc.gridy = 2; // Row 2
        JTextField addressField = new JTextField(10);
        addressField.setBorder(BorderFactory.createTitledBorder("Address"));
        experienceFields.add(addressField, gbc);

        gbc.gridx = 1; // Column 1
        JTextField cityField = new JTextField(10);
        cityField.setBorder(BorderFactory.createTitledBorder("City"));
        experienceFields.add(cityField, gbc);

        gbc.gridx = 0; // Column 0
        gbc.gridy = 3; // Row 3
        JTextField zipField = new JTextField(5);
        zipField.setBorder(BorderFactory.createTitledBorder("Zip"));
        experienceFields.add(zipField, gbc);

        // State Dropdown
        gbc.gridx = 1; // Column 1
        gbc.gridy = 3; // Same row as zip
        String[] stateAbbreviations = {
                "Select State", "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA",
                "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH",
                "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX",
                "UT", "VT", "VA", "WA", "WV", "WI", "WY"
        };

        JComboBox<String> stateDropdown = new JComboBox<>(stateAbbreviations);
        stateDropdown.setBorder(BorderFactory.createTitledBorder("State"));
        experienceFields.add(stateDropdown, gbc);


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
            JPanel lastExperienceField = experienceFieldsList.remove(experienceFieldsList.size() - 1);

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
            new refrencesForm().setVisible(true);
        });
    }

    private void validateFields() {
        boolean allFieldsFilled = true;

        for (JPanel panel : experienceFieldsList) {
            for (Component component : panel.getComponents()) {
                if (component instanceof JTextField textField) {
                    if (textField.getText().trim().isEmpty()) {
                        allFieldsFilled = false;
                        break; // Exit inner loop as soon as an empty field is found
                    }
                } else if (component instanceof JComboBox<?> comboBox) {
                    if (comboBox.getSelectedIndex() == 0) { // Assuming index 0 is "Select State"
                        allFieldsFilled = false;
                        break;
                    }
                }
            }
            if (!allFieldsFilled) break; // Exit outer loop if any field is empty
        }

        if (!allFieldsFilled) {
            JOptionPane.showMessageDialog(this, "Please answer all questions before continuing.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            nextPage(); // Proceed if all fields are filled
        }
    }

//    private void submitAndNextPage() {
//        errorMessages.setLength(0); // Clear previous error messages
//        Component component = tabbedPane.getComponentAt(1);
//
//        // Iterate over all components in each tab
//        for (Component tab : tabbedPane.getComponents()) {
//            if (tab instanceof JPanel) {
//                validateComponents((JPanel) tab);
//            }
//        }
//
//        // Display error messages if any issues were found
//        if (!errorMessages.isEmpty()) {
//            JOptionPane.showMessageDialog(page1Frame, errorMessages.toString(), "Input Errors", JOptionPane.ERROR_MESSAGE);
//        } else {
//            processValidFormData(); // Process form data if no errors
//        }
//    }
//
//    /**
//     * Validates all components within the specified panel.
//     * <p>
//     * This method checks each component in the panel, verifying text fields for completeness,
//     * formatted text fields (like phone numbers) for correctness, dropdown selections for validity,
//     * and radio buttons for group selection.
//     * </p>
//     *
//     * @param panel The JPanel containing the components to validate.
//     */
//    private void validateComponents(JPanel panel) {
//        for (Component comp : panel.getComponents()) {
//            if (comp instanceof JTextField) {
//                if (comp instanceof JFormattedTextField) {
//                    validateFormattedTextField((JFormattedTextField) comp);
//                } else {
//                    validateTextField((JTextField) comp);
//                }
//            } else if (comp instanceof JComboBox) {
//                validateDropdown((JComboBox<?>) comp);
//            } else if (comp instanceof JPanel) {
//                validateComponents((JPanel) comp); // Recursive call for nested panels
//            } else if (comp instanceof JRadioButton) {
//                if (isButtonInGroup((JRadioButton) comp, radioButtonGroup)) {
//                    validateButtonGroup(radioButtonGroup);
//                } else if (isButtonInGroup((JRadioButton) comp, yearsAtAdd)) {
//                    validateButtonGroup(yearsAtAdd);
//                } else if (isButtonInGroup((JRadioButton) comp, over18)) {
//                    validateButtonGroup(over18);
//                }
//            }
//        }
//    }
//
//
//    /**
//     * Validates a standard JTextField to ensure it is not empty.
//     * <p>
//     * If the field is empty and its name is not "Middle Name," an error message is appended to the errorMessages StringBuilder.
//     * Additionally, if the field is an email field, it validates the format against a specified regex pattern.
//     * </p>
//     *
//     * @param field The JTextField to validate.
//     */
//    private void validateTextField(JTextField field) {
//        if (field.getText().isEmpty() && !Objects.equals(field.getName(), "Middle Name")) {
//            errorMessages.append(field.getName()).append(" is required!\n");
//        } else {
//            if (Objects.equals(field.getName(), "Email")) {
//                Pattern pattern = Pattern.compile(regex);
//                String emailText = email.getText();
//                Matcher matcher = pattern.matcher(emailText);
//                if (emailText.isEmpty()) {
//                    errorMessages.append("Email is required!\n");
//                } else if (!matcher.matches()) {
//                    errorMessages.append("Email format is invalid!\n");
//                }
//            }
//        }
//    }
//
//    /**
//     * Validates a JFormattedTextField (in this case the only JFormattedTextField is the phone number field) to ensure it is not empty.
//     * <p>
//     * If the field is filled, it calls the method to format the phone number.
//     * </p>
//     *
//     * @param field The JFormattedTextField to validate.
//     */
//    private void validateFormattedTextField(JFormattedTextField field) {
//        if (field.getText().isEmpty()) {
//            errorMessages.append("Phone number is required!\n");
//        } else {
//            formatPhoneNumber(field); // Formats if valid
//        }
//    }
//
//    /**
//     * Validates a JComboBox to ensure that a selection is made that is not the default option.
//     * <p>
//     * In this implementation, it checks that the dropdown is not left on the default item "State".
//     * </p>
//     *
//     * @param dropdown The JComboBox to validate.
//     */
//    private void validateDropdown(JComboBox<?> dropdown) {
//        if (Objects.equals(dropdown.getSelectedItem(), "State")) { // Customize for your dropdown
//            errorMessages.append(dropdown.getName()).append(" must be selected!\n");
//        }
//    }
//
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
//
//    /**
//     * Processes the form data when all inputs are valid.
//     * <p>
//     * This method retrieves the user's input data, including the first name, middle name (if applicable),
//     * last name, and other information, then prints it to the console for further processing.
//     * </p>
//     */
//    private void processValidFormData() {
//        String mName = middleNm.isSelected() ? middleName.getText() : null;
//        String fName = firstName.getText();
//        String lName = lastName.getText();
////        String phoneNmber = phoneNumberField.getText();
//        System.out.println(fName + " " + (mName != null ? mName + " " : "") + lName + " ");
//    }
}
