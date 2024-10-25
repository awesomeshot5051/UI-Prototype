import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FirstPage implements ActionListener, Runnable {
    private final JFrame page1Frame = new JFrame("Personal information");
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
    private JTextField phoneNumber;
    private JLabel emailLabel;
    private JTextField email;
    private JRadioButton maleRadioButton;
    private JRadioButton femaleJRadioButton;
    private JRadioButton pntsJRadioButton;
    private ButtonGroup radioButtonGroup;
    private JFormattedTextField weightFormattedTextField;
    private JButton clearButton;
    private JButton submitButton;

    FirstPage() {
        setupUI();
    }

    private void setupUI() {
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
        middleNm.addActionListener(e -> {
            boolean isChecked = middleNm.isSelected();
            middleNameLabel.setVisible(isChecked);
            middleName.setVisible(isChecked);
        });

        middleNameLabel = new JLabel("Middle Name:");
        middleName = new JTextField(20);

        // Initially hide the middle name fields
        middleNameLabel.setVisible(false);
        middleName.setVisible(false);

        lastNameLabel = new JLabel("Last Name");
        lastName = new JTextField(20);
        phoneNumberLabel = new JLabel("Phone Number");
        phoneNumber = new JTextField(20);
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
        page1Frame.add(phoneNumber, gbc);

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
        gbc.gridy = 12;
        gbc.gridx = 0;
        page1Frame.add(currentAddressLabel, gbc);

        gbc.gridy = 13;
        gbc.gridx = 0;
        page1Frame.add(new JLabel("Number"), gbc);
        gbc.gridx = 1;
        page1Frame.add(numberField, gbc);

        gbc.gridx = 2;
        page1Frame.add(new JLabel("Street"), gbc);
        gbc.gridx = 3;
        page1Frame.add(streetField, gbc);

        gbc.gridy = 14;
        gbc.gridx = 0;
        page1Frame.add(new JLabel("City"), gbc);
        gbc.gridx = 1;
        page1Frame.add(cityField, gbc);

        gbc.gridx = 2;
        page1Frame.add(new JLabel("State"), gbc);
        gbc.gridx = 3;
        page1Frame.add(stateField, gbc);

        gbc.gridx = 4;
        page1Frame.add(new JLabel("ZIP"), gbc);
        gbc.gridx = 5;
        page1Frame.add(zipField, gbc);

        // Buttons (Clear and Submit)
        gbc.gridy = 11;
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

    @Override
    public void run() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
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

}
