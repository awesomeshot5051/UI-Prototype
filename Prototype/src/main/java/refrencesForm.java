
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class refrencesForm extends JFrame{
	
	//JPanels
    private JPanel mainPanel;
    private JPanel refrencePanel;
    private JPanel headerButtonPanel;
    private JPanel footerButtonPanel;
    
    
    //JButtons
    private JButton addRefrenceButton;
    private JButton clearMostRecentButton;
    private JButton nextPage;
    
    //JLabes
    private JLabel headerTitle;
    
    private int entryNumber = 0; //used to keep track of fields created
    
    private ArrayList<JPanel> refrenceFieldsList = new ArrayList<>();

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
                addRefrenceFields();
            }
        });
        
        clearMostRecentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearMostRecentRefrenceField();
            }
        });
        
        nextPage.addActionListener(e -> validateFields());

        add(mainPanel);
        setVisible(true);
    }
    	

    /* This function is used to populate the interface
     *  with the fields to enter information */
    private void addRefrenceFields() {
    	
    	entryNumber++;
    	
        JPanel refrenceFields = new JPanel();
        refrenceFields.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding between components
        
        refrenceFields.setBorder(BorderFactory.createTitledBorder("Reference " + entryNumber));

        // First Row: Employer Name and Job Title
        gbc.gridx = 0; // Column 0
        gbc.gridy = 0; // Row 0
        gbc.weightx = 1; // Expand horizontally
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JTextField employerField = new JTextField(10);
        employerField.setBorder(BorderFactory.createTitledBorder("Name"));
        refrenceFields.add(employerField, gbc);
        
        gbc.gridx = 1; // Column 1
        JTextField jobTitleField = new JTextField(10);
        jobTitleField.setBorder(BorderFactory.createTitledBorder("Title"));
        refrenceFields.add(jobTitleField, gbc);

        // Second Row: Start Date, End Date
        gbc.gridx = 0; // Column 0
        gbc.gridy = 1; // Row 1
        JTextField startDayField = new JTextField(10);
        startDayField.setBorder(BorderFactory.createTitledBorder("Company"));
        refrenceFields.add(startDayField, gbc);
        
        gbc.gridx = 1; // Column 1
        JTextField endDayField = new JTextField(10);
        endDayField.setBorder(BorderFactory.createTitledBorder("Phone Number"));
        refrenceFields.add(endDayField, gbc);

        // Third Row: Address related fields
        gbc.gridx = 0; // Column 0
        gbc.gridy = 2; // Row 2
        JTextField addressField = new JTextField(10);
        addressField.setBorder(BorderFactory.createTitledBorder("Email"));
        refrenceFields.add(addressField, gbc);
        
        

        // State Dropdown
        gbc.gridx = 1; // Column 1
        gbc.gridy = 3; // Same row as zip
        

        
        refrenceFieldsList.add(refrenceFields);
        
        refrencePanel.add(refrenceFields);
        
        refrencePanel.revalidate(); // Refresh the panel to show new fields
        refrencePanel.repaint();
    }
    
    /**
     * clearMostRecent function
     * Grabs the most recent field added and removes it from the interface
     */
    private void clearMostRecentRefrenceField() {
    	
    	if (!refrenceFieldsList.isEmpty()) {
    		
    		//targets the last panel added and removes from arrayList assigns to JPanel
            JPanel lastExperienceField = refrenceFieldsList.remove(refrenceFieldsList.size() - 1); 
            
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
    public void nextPage() {
    	SwingUtilities.invokeLater(() -> {
            dispose(); 
            new educationForm().setVisible(true); 
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new refrencesForm());
    }
}
