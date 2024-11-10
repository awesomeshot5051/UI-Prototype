import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.Objects;

public class educationForm extends JFrame {
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

    public educationForm() {
        // Set up main frame
        setTitle("Education Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);

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
        addExperienceButton = new JButton("Add Education");

        headerTitle = new JLabel("Education");
        headerTitle.setFont(new Font("Arial", Font.BOLD, 24));
        headerTitle.setHorizontalAlignment(JLabel.CENTER);
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
                addEducationFields();
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
        setLocationRelativeTo(null);
        setIconImage(UIPrototypeMainClass.getIcon());
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(educationForm::new);
//    }

    /* This function is used to populate the interface
     *  with the fields to enter information */
    private void addEducationFields() {

        entryNumber++;
        JTextField subjectField = new JTextField(10);
        JPanel educationPanel = new JPanel();
        educationPanel.setName("Education " + entryNumber);
        educationPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding between components

        educationPanel.setBorder(BorderFactory.createTitledBorder("Education " + entryNumber));

        // First Row: School Type and Name
        gbc.gridx = 0; // Column 0
        gbc.gridy = 0; // Row 0
        gbc.weightx = 1; // Expand horizontally
        gbc.fill = GridBagConstraints.HORIZONTAL;
        String[] schoolTypes = {
                "Education Type", "High School", "College", "Trade School", "Other"
        };

        JComboBox<String> educationTypeDropdown = new JComboBox<>(schoolTypes);
        educationTypeDropdown.setBorder(BorderFactory.createTitledBorder("Education Type"));
        educationPanel.add(educationTypeDropdown, gbc);
        educationTypeDropdown.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                subjectField.setVisible(!Objects.equals(educationTypeDropdown.getSelectedItem(), "High School"));
            }
        });
        educationTypeDropdown.setName("Education Type");
        gbc.gridx = 1; // Column 1
        JTextField schoolName = new JTextField(10);
        schoolName.setBorder(BorderFactory.createTitledBorder("School Name"));
        schoolName.setName("School Name");
        educationPanel.add(schoolName, gbc);

        // Second Row: City, State
        gbc.gridx = 0; // Column 0
        gbc.gridy = 1; // Row 1
        JTextField cityField = new JTextField(10);
        cityField.setBorder(BorderFactory.createTitledBorder("City"));
        educationPanel.add(cityField, gbc);
        cityField.setName("City");

        gbc.gridx = 1; // Column 1


        JComboBox<String> stateDropdown = UIPrototypeMainClass.getStates();
        stateDropdown.setBorder(BorderFactory.createTitledBorder("State"));
        educationPanel.add(stateDropdown, gbc);
        stateDropdown.setName("State");

        // Third Row: Subject, Grad Type
        gbc.gridx = 0; // Column 0
        gbc.gridy = 2; // Row 2

        subjectField.setBorder(BorderFactory.createTitledBorder("Subject"));
        subjectField.setName("Subject");
        educationPanel.add(subjectField, gbc);

        gbc.gridx = 1; // Column 1
        String[] gradType = {
                "Select Type", "Diploma", "Associates", "Bachelors", "Masters", "Certificate", "In Progress", "Other"
        };

        JComboBox<String> gradTypeBox = new JComboBox<>(gradType);
        gradTypeBox.setBorder(BorderFactory.createTitledBorder("Graduation Type"));
        educationPanel.add(gradTypeBox, gbc);
        gradTypeBox.setName("Graduation Type");
        gbc.gridx = 0; // Column 0
        gbc.gridy = 3; // Row 3


        experienceFieldsList.add(educationPanel);

        experiencePanel.add(educationPanel);

        experiencePanel.revalidate();
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


            experiencePanel.revalidate();
            experiencePanel.repaint();

            //decrementing entry number to let user keep track of number of fields added
            entryNumber--;
        }

    }

    public void nextPage() {
        // new referencesForm().setVisible(true);
        dispose();
        new workExperienceForm();
    }

    private void validateFields() {

        for (JPanel panel : experienceFieldsList) {
            String panelName = panel.getName();
            for (Component component : panel.getComponents()) {
                if (component instanceof JTextField textField) {
                    validateTextField(textField, panelName);
                } else if (component instanceof JComboBox<?> comboBox) {
                    validateDropdown(comboBox, panelName);
                }
            }
        }

        if (!errorMessages.toString().isEmpty()) {
            JOptionPane.showMessageDialog(this, errorMessages, "Error", JOptionPane.ERROR_MESSAGE);
            errorMessages.setLength(0);
        } else {
            nextPage(); // Proceed if all fields are filled
        }

    }

    private void validateDropdown(JComboBox<?> dropdown, String panelName) {
        if (dropdown.getSelectedIndex() == 0) { // Customize for your dropdown
            errorMessages.append(dropdown.getName()).append(" must be selected for ").append(panelName).append("\n");
        }
    }

    private void validateTextField(JTextField field, String panelName) {
        if (field.getName().equals("Subject") && !field.isVisible()) {
        } else {
            if (field.getText().isEmpty()) {
                errorMessages.append(field.getName()).append(" must be selected for ").append(panelName).append("\n");
            }
        }
    }
}
