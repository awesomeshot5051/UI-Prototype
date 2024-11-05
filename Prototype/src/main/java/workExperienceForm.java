
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class workExperienceForm extends JFrame {
	
	//JPanels
    private JPanel mainPanel;
    private JPanel experiencePanel;
    private JPanel headerButtonPanel;
    private JPanel footerButtonPanel;
    
    
    //JButtons
    private JButton addExperienceButton;
    private JButton clearMostRecentButton;
    private JButton nextPage;
    
    //JLabes
    private JLabel headerTitle;
    
    private int entryNumber = 0; //used to keep track of fields created
    
    private ArrayList<JPanel> experienceFieldsList = new ArrayList<>();

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
                if (component instanceof JTextField) {
                    JTextField textField = (JTextField) component;
                    if (textField.getText().trim().isEmpty()) {
                        allFieldsFilled = false;
                        break; // Exit inner loop as soon as an empty field is found
                    }
                } else if (component instanceof JComboBox) {
                    JComboBox<?> comboBox = (JComboBox<?>) component;
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new workExperienceForm());
    }
}