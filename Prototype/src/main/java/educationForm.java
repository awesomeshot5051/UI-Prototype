import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.Objects;

public class educationForm extends JFrame {
    /**
     * <ul>
     * <li>{@code mainPanel} the panel that holds everything</li>
     * <li>{@code educationPanel} the panel that is duplicated</li>
     * <li>{@code headerButtonPanel} the panel that holds the buttons at the top of the panel(add education field and delete the most recent education</li>
     * <li>{@code footerButtonPanel} the panel that holds the buttons at the bottom of the panel(nextPage)</li>
     * <br>
     * <br>
     *
     * <li>{@code addEducationButton} the button that duplicates the {@systemProperty educationPanel}</li>
     * <li>{@code clearMostRecentButton} the button that clears the most recent {@systemProperty educationPanel}. This deletes the entire panel.</li>
     * <li>{@code nextPage}the button that goes to the next page. Before this successfully moves to the next page, it first ensures that all fields are filled in. If a field is missing, it informs the user which field is missing and does not advance to the next page.</li>
     * <br>
     * <br>
     *
     * <li>{@code headerTitle} the title of the panel</li>
     * <li>{@code educationFieldList} an array that holds all of the {@systemProperty educationField} to make it easier to delete the latest one.</li>
     * <li>{@code errorMessages} a stringBuilder that is assembled when you click {@systemProperty nextPage}. It informs the user what is missing or improperly filled.</li>
     * <li>{@code entryNumber} the number entry you are on. This increments as you add {@systemProperty educationField}(using the {@systemProperty addEducationButton} and decrements as you remove {@systemProperty educationField}(using the {@systemProperty clearMostRecentButton} Also used to label each entry panel sequentially.</li>
     *
     * </ul>
     **/
    private final JPanel mainPanel;
    private final JPanel educationPanel;
    private final JPanel headerButtonPanel;
    private final JPanel footerButtonPanel;


    //JButtons
    private final JButton addEducationButton;
    private final JButton clearMostRecentButton;
    private final JButton nextPage;

    //JLabes
    private final JLabel headerTitle;
    private final ArrayList<JPanel> educationFieldsList = new ArrayList<>();
    private final StringBuilder errorMessages = new StringBuilder();
    private int entryNumber = 0; //used to keep track of fields created

    /**
     * Constructor for creating and initializing the Education Form frame.
     * <p>
     * This method sets up the main UI for the education form window, including all components such as labels, buttons, panels, and action listeners.
     * It configures the layout of the form and ensures that all components are added to the frame properly, allowing the user to interact with the form elements.
     * </p>
     */
    public educationForm() {
        // Set up the main frame for the application
        setTitle("Education Form"); // Set the title of the frame to "Education Form"
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set the default close operation to exit the application when the window is closed
        setSize(500, 400); // Set the size of the window to 500x400 pixels

        // Create a header label for the form title
        JLabel headerLabel = new JLabel("Education Information", JLabel.CENTER); // Create a label with text "Education Information" centered
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Set the font to Arial, bold, and size 24 for the header label

        // Main panel to hold all components of the frame (centered panel for form fields)
        mainPanel = new JPanel(); // Initialize the main panel that will contain all the components
        mainPanel.setLayout(new BorderLayout()); // Set the layout manager to BorderLayout for easy placement of components

        // Panel to hold dynamically added education fields (this panel will be populated with education fields)
        educationPanel = new JPanel(); // Create a new panel to hold education field components
        educationPanel.setLayout(new BoxLayout(educationPanel, BoxLayout.Y_AXIS)); // Set the layout manager to BoxLayout with vertical alignment to stack fields vertically

        // JScrollPane for the educationPanel (enables scrolling if the number of education fields exceeds the available space)
        JScrollPane scrollPane = new JScrollPane(educationPanel); // Wrap the educationPanel in a scroll pane to allow scrolling when needed
        mainPanel.add(scrollPane, BorderLayout.CENTER); // Add the scroll pane to the center of the main panel

        // Initializing components for the header section (buttons and titles)
        clearMostRecentButton = new JButton("Clear Most Recent Entry"); // Create a button to clear the most recent entry
        addEducationButton = new JButton("Add Education"); // Create a button to add new education fields to the form

        // Header title label for the form
        headerTitle = new JLabel("Education"); // Create a label for the header with text "Education"
        headerTitle.setFont(new Font("Arial", Font.BOLD, 24)); // Set the font to Arial, bold, and size 24 for the header title
        headerTitle.setHorizontalAlignment(JLabel.CENTER); // Center the text horizontally within the label
        headerTitle.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add empty borders around the header title for spacing

        // Panel to hold the header elements and buttons (including the add and clear buttons)
        headerButtonPanel = new JPanel(new BorderLayout()); // Create a panel with BorderLayout to manage the placement of the buttons and title
        headerButtonPanel.add(addEducationButton, BorderLayout.WEST); // Place the "Add Education" button on the left side of the panel
        headerButtonPanel.add(headerTitle, BorderLayout.CENTER); // Place the header title in the center of the panel
        headerButtonPanel.add(clearMostRecentButton, BorderLayout.EAST); // Place the "Clear Most Recent Entry" button on the right side of the panel
        mainPanel.add(headerButtonPanel, BorderLayout.NORTH); // Add the header button panel to the top (North) section of the main panel

        // Footer panel to hold the "Next Page" button
        nextPage = new JButton("Next Page"); // Create a button labeled "Next Page"
        footerButtonPanel = new JPanel(new BorderLayout()); // Create a footer panel with BorderLayout to manage button placement
        footerButtonPanel.add(nextPage, BorderLayout.EAST); // Place the "Next Page" button on the right side of the footer panel
        mainPanel.add(footerButtonPanel, BorderLayout.SOUTH); // Add the footer button panel to the bottom (South) section of the main panel

        // Action listeners for the buttons
        // Action listener for the "Add Education" button (adds new education fields when clicked)
        addEducationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEducationFields(); // Call the method to add new education fields to the form
            }
        });

        // Action listener for the "Clear Most Recent Entry" button (removes the most recent education field when clicked)
        clearMostRecentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearMostRecentEducationField(); // Call the method to clear the most recent education field
            }
        });

        // Action listener for the "Next Page" button (validates the form fields when clicked)
        nextPage.addActionListener(e -> validateFields()); // Call the validateFields method to check if all fields are correctly filled

        // Final setup for the main frame
        add(mainPanel); // Add the main panel to the frame (this contains all the components like the header, body, and footer)
        setVisible(true); // Set the frame visible to the user
        setLocationRelativeTo(null); // Center the window on the screen
        setIconImage(UIPrototypeMainClass.getIcon()); // Set the application icon (obtained from the UIPrototypeMainClass)
    }


    /**
     * Adds a new education field panel to the form.
     * <p>
     * This method dynamically generates and adds a new education field section to the form, which includes various
     * form elements such as dropdown menus, text fields, and labels. Each education field is wrapped in a titled panel and
     * placed in a grid layout. The method handles visibility of certain fields based on the user’s selection, such as
     * showing a subject field only when the education type is not "High School".
     * </p>
     * <p>
     * The following components are initialized within this method:
     * <ul>
     *     <li>{@link GridBagConstraints gbc} - Layout constraints for positioning components in the grid layout. It specifies
     *         the row and column positioning as well as horizontal and vertical expansion properties for components.</li>
     *     <li>{@link ArrayList schoolTypes} - An array containing different education types for the dropdown list (e.g., High School,
     *         College, Trade School, Other).</li>
     *     <li>{@link JComboBox<String> educationTypeDropdown} - A dropdown menu allowing the user to select an education type
     *         from the {@code schoolTypes} array. The visibility of the subject field is controlled based on the selected type.</li>
     *     <li>{@link JTextField schoolName} - A text field for the user to input the name of the school.</li>
     *     <li>{@link JTextField cityField} - A text field for the user to input the city of the school.</li>
     *     <li>{@link JComboBox<String> stateDropdown} - A dropdown menu populated with U.S. states, obtained from the
     *         {@code UIPrototypeMainClass.getStates()} method.</li>
     *     <li>{@link ArrayList gradType} - An array containing various graduation types for the dropdown list (e.g., Diploma, Bachelors,
     *         Masters, etc.).</li>
     *     <li>{@link JComboBox<String> gradTypeBox} - A dropdown menu allowing the user to select a graduation type from the
     *         {@code gradType} array.</li>
     * </ul>
     */
    private void addEducationFields() {

        entryNumber++; // Increment entry number to track the education section being added

        JTextField subjectField = new JTextField(10); // Text field for entering the subject
        JPanel educationPanel = new JPanel(); // Panel to hold all education fields for this entry
        educationPanel.setName("Education " + entryNumber); // Set a unique name for the panel
        educationPanel.setLayout(new GridBagLayout()); // Set the layout of the panel to GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints(); // Layout constraints for components in GridBagLayout
        gbc.insets = new Insets(5, 5, 5, 5); // Padding between components in the layout

        educationPanel.setBorder(BorderFactory.createTitledBorder("Education " + entryNumber)); // Set the border for the panel

        // First Row: School Type and Name
        gbc.gridx = 0; // Column 0
        gbc.gridy = 0; // Row 0
        gbc.weightx = 1; // Expand horizontally
        gbc.fill = GridBagConstraints.HORIZONTAL; // Fill horizontally

        String[] schoolTypes = {
                "Education Type", "High School", "College", "Trade School", "Other" // Array of education types
        };

        JComboBox<String> educationTypeDropdown = new JComboBox<>(schoolTypes); // Dropdown for selecting education type
        educationTypeDropdown.setBorder(BorderFactory.createTitledBorder("Education Type")); // Border for the dropdown
        educationPanel.add(educationTypeDropdown, gbc); // Add the dropdown to the panel

        // Add focus listener to show/hide subject field based on education type selection
        educationTypeDropdown.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                subjectField.setVisible(!Objects.equals(educationTypeDropdown.getSelectedItem(), "High School"));
            }
        });

        educationTypeDropdown.setName("Education Type"); // Set the name for the education type dropdown

        // Column 1: School Name
        gbc.gridx = 1; // Column 1
        JTextField schoolName = new JTextField(10); // Text field for entering the school name
        schoolName.setBorder(BorderFactory.createTitledBorder("School Name")); // Border for the text field
        schoolName.setName("School Name"); // Set the name for the school name field
        educationPanel.add(schoolName, gbc); // Add the school name field to the panel

        // Second Row: City, State
        gbc.gridx = 0; // Column 0
        gbc.gridy = 1; // Row 1
        JTextField cityField = new JTextField(10); // Text field for entering the city
        cityField.setBorder(BorderFactory.createTitledBorder("City")); // Border for the city field
        educationPanel.add(cityField, gbc); // Add the city field to the panel
        cityField.setName("City"); // Set the name for the city field

        gbc.gridx = 1; // Column 1
        JComboBox<String> stateDropdown = UIPrototypeMainClass.getStates(); // Dropdown for selecting state
        stateDropdown.setBorder(BorderFactory.createTitledBorder("State")); // Border for the state dropdown
        educationPanel.add(stateDropdown, gbc); // Add the state dropdown to the panel
        stateDropdown.setName("State"); // Set the name for the state dropdown

        // Third Row: Subject, Grad Type
        gbc.gridx = 0; // Column 0
        gbc.gridy = 2; // Row 2

        subjectField.setBorder(BorderFactory.createTitledBorder("Subject")); // Border for the subject field
        subjectField.setName("Subject"); // Set the name for the subject field
        educationPanel.add(subjectField, gbc); // Add the subject field to the panel

        gbc.gridx = 1; // Column 1
        String[] gradType = {
                "Select Type", "Diploma", "Associates", "Bachelors", "Masters", "Certificate", "In Progress", "Other" // Array of graduation types
        };

        JComboBox<String> gradTypeBox = new JComboBox<>(gradType); // Dropdown for selecting graduation type
        gradTypeBox.setBorder(BorderFactory.createTitledBorder("Graduation Type")); // Border for the graduation type dropdown
        educationPanel.add(gradTypeBox, gbc); // Add the graduation type dropdown to the panel
        gradTypeBox.setName("Graduation Type"); // Set the name for the graduation type dropdown

        gbc.gridx = 0; // Column 0
        gbc.gridy = 3; // Row 3

        // Add the education panel to the list and revalidate the UI
        educationFieldsList.add(educationPanel);
        this.educationPanel.add(educationPanel);
        this.educationPanel.revalidate();
        this.educationPanel.repaint();
    }


    /**
     * Clears the most recent education field panel from the user interface.
     * <p>
     * This method removes the last added education field panel from the list and updates the layout of the UI
     * accordingly. The method also keeps track of the number of fields added and decrements the entry counter.
     * </p>
     * <ul>
     * <li>
     * {@code lastExperienceField} the panel representing the most recent education field that will be removed from the UI</li>
     * </ul>
     *
     * @see #educationFieldsList
     * @see #educationPanel
     * @see #entryNumber
     */
    private void clearMostRecentEducationField() {

        // Check if there are any education fields in the list
        if (!educationFieldsList.isEmpty()) {

            // Remove the last education field panel from the list
            // This assigns the most recently added education field panel to the variable lastExperienceField
            JPanel lastExperienceField = educationFieldsList.removeLast(); // Remove the last panel from the list

            // Remove the last education field panel from the educationPanel (container for displaying education fields)
            educationPanel.remove(lastExperienceField); // Remove the most recent education field panel from the UI

            // Revalidate the educationPanel to ensure the layout is updated correctly after the removal
            educationPanel.revalidate(); // Informs the layout manager that the UI has been modified
            educationPanel.repaint(); // Redraws the panel to visually reflect the changes made

            // Decrement the entry number to keep track of the number of fields left
            entryNumber--; // Decrease the entry number, adjusting the total count of education fields
        }
    }


    /**
     * Navigates to the next page in the workflow by closing the current form and opening the work experience form.
     * <p>
     * This method first disposes of the current form, releasing any resources associated with it. It then creates and
     * displays the work experience form, allowing the user to proceed with the next step in the process.
     * </p>
     *
     * @see workExperienceForm
     */
    public void nextPage() {
        SwingUtilities.invokeLater(() -> {
            // Dispose of the current form to free resources and close the current window
            dispose();
            // Create and display the work experience form, allowing the user to proceed
            new workExperienceForm();
        });
    }


    /**
     * Validates the fields in all education panels to ensure all required fields are filled out.
     * <p>
     * This method iterates through each education field panel, checking all components (text fields and dropdowns).
     * It validates that all required text fields are filled and that dropdowns have a valid selection (not the default option).
     * If any fields are invalid, an error message is appended to the {@link StringBuilder} errorMessages.
     * Once validation is complete, if there are errors, they are displayed in a dialog box. If no errors are found,
     * the method proceeds to the next page by calling {@link #nextPage()}.
     * </p>
     *
     * @see #validateTextField
     * @see #validateDropdown
     * @see #nextPage
     */
    private void validateFields() {

        // Loop through each education panel to validate its components
        for (JPanel panel : educationFieldsList) {
            String panelName = panel.getName(); // Get the name of the current panel to include in error messages

            // Loop through each component in the panel
            for (Component component : panel.getComponents()) {
                // If the component is a text field, validate it
                if (component instanceof JTextField textField) {
                    validateTextField(textField, panelName);
                    // If the component is a dropdown, validate it
                } else if (component instanceof JComboBox<?> comboBox) {
                    validateDropdown(comboBox, panelName);
                }
            }
        }

        // After validating all fields, check if any errors were found
        if (!errorMessages.toString().isEmpty()) {
            // If errors were found, display them in a dialog box
            JOptionPane.showMessageDialog(this, errorMessages, "Error", JOptionPane.ERROR_MESSAGE);

            // Clear the errorMessages StringBuilder to prepare for the next validation cycle
            errorMessages.setLength(0);
        } else {
            // If no errors are found, proceed to the next page
            nextPage();
        }
    }


    /**
     * Validates that a dropdown (JComboBox) has a valid selection.
     * <p>
     * The method checks if the first item in the dropdown is selected (indicating that the user hasn't made a valid selection).
     * If so, an error message is added to the {@link StringBuilder} errorMessages.
     * </p>
     *
     * @param dropdown  The JComboBox to validate.
     * @param panelName The name of the panel where the dropdown is located, used for error reporting.
     */
    private void validateDropdown(JComboBox<?> dropdown, String panelName) {
        // Check if the first item in the dropdown is selected, indicating no valid selection
        if (dropdown.getSelectedIndex() == 0) {
            // Append an error message specifying the missing selection for the panel
            errorMessages.append(dropdown.getName()).append(" must be selected for ").append(panelName).append("\n");
        }
    }


    /**
     * Validates that a text field has been filled.
     * <p>
     * This method checks if the text field is empty. If the field is empty, an error message is appended to the
     * {@link StringBuilder} errorMessages. It also ensures that the "Subject" field is only validated when visible.
     * </p>
     *
     * @param field     The JTextField to validate.
     * @param panelName The name of the panel where the text field is located, used for error reporting.
     */
    private void validateTextField(JTextField field, String panelName) {
        // Check if the "Subject" field is not visible, and if it is, skip the validation
        if (field.getName().equals("Subject") && !field.isVisible()) {
            // Skip validation if the "Subject" field is not visible
        } else {
            // Check if the field's text is empty
            if (field.getText().isEmpty()) {
                // Append an error message specifying the missing text for the field
                errorMessages.append(field.getName()).append(" must be selected for ").append(panelName).append("\n");
            }
        }
    }
}
