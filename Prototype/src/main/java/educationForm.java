import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class educationForm extends JFrame{
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
        
        experienceFields.setBorder(BorderFactory.createTitledBorder("Education " + entryNumber));

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
            experienceFields.add(educationTypeDropdown, gbc);
        
        gbc.gridx = 1; // Column 1
        JTextField jobTitleField = new JTextField(10);
        jobTitleField.setBorder(BorderFactory.createTitledBorder("School Name"));
        experienceFields.add(jobTitleField, gbc);

        // Second Row: City, State
        gbc.gridx = 0; // Column 0
        gbc.gridy = 1; // Row 1
        JTextField startDayField = new JTextField(10);
        startDayField.setBorder(BorderFactory.createTitledBorder("City"));
        experienceFields.add(startDayField, gbc);
        
        gbc.gridx = 1; // Column 1
        String[] stateAbbreviations = { 
                "Select State", "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA",
                "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH",
                "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX",
                "UT", "VT", "VA", "WA", "WV", "WI", "WY"
            };
            
            JComboBox<String> stateDropdown = new JComboBox<>(stateAbbreviations);
            stateDropdown.setBorder(BorderFactory.createTitledBorder("State"));
            experienceFields.add(stateDropdown, gbc);

        // Third Row: Subject, Grad Type
        gbc.gridx = 0; // Column 0
        gbc.gridy = 2; // Row 2
        JTextField addressField = new JTextField(10);
        addressField.setBorder(BorderFactory.createTitledBorder("Subject"));
        experienceFields.add(addressField, gbc);
        
        gbc.gridx = 1; // Column 1
        String[] gradType = { 
                "Select Type", "Diploma", "Associates", "Bachelors", "Masters", "Certificate", "In Progress", "Other"
            };
            
            JComboBox<String> gradTypeBox = new JComboBox<>(gradType);
            gradTypeBox.setBorder(BorderFactory.createTitledBorder("Graduation Type"));
            experienceFields.add(gradTypeBox, gbc);
        gbc.gridx = 0; // Column 0
        gbc.gridy = 3; // Row 3
        

        
        experienceFieldsList.add(experienceFields);
        
        experiencePanel.add(experienceFields);
        
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
            JPanel lastExperienceField = experienceFieldsList.remove(experienceFieldsList.size() - 1); 
            
            //removing panel from experience panel
            experiencePanel.remove(lastExperienceField); 
            
           
            experiencePanel.revalidate(); 
            experiencePanel.repaint(); 
            
            //decrementing entry number to let user keep track of number of fields added
            entryNumber--; 
        }
    	
    }
    
    public void nextPage() {
    	SwingUtilities.invokeLater(() -> {
            dispose(); 
           // new refrencesForm().setVisible(true); 
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
                        break; // Exit loop as soon as an empty field is found
                    }
                } else if (component instanceof JComboBox) {
                    JComboBox<?> comboBox = (JComboBox<?>) component;
                    if (comboBox.getSelectedIndex() == 0) { // Assuming index 0 is default
                        allFieldsFilled = false;
                        break;
                    }
                }
            }
            if (!allFieldsFilled) break; // Exit loop if any field is empty
        }

        if (!allFieldsFilled) {
            JOptionPane.showMessageDialog(this, "Please answer all questions before continuing.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            nextPage(); // Proceed if all fields are filled
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new educationForm());
    }
}
