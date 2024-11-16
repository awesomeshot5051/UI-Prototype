import javax.swing.*;
import java.awt.*;

/**
 * Main class for initializing the user interface of the UI Prototype.
 * This class handles the application icon, state selection combo box,
 * and launching the first page of the UI.
 */
public class UIPrototypeMainClass {

    // The icon used for the application window (path to the image file)
    static ImageIcon icon = new ImageIcon("D:\\UI-Prototype\\Prototype\\Icon\\UNFinshedBusiness.png");

    /**
     * The entry point of the application.
     * This method is called when the application is launched and initializes the first page of the UI.
     *
     * @param args Command line arguments (not used in this case).
     */
    public static void main(String[] args) {
        // Initialize and display the first page of the application
        SwingUtilities.invokeLater(FirstPage::new);
    }

    /**
     * Creates and returns a JComboBox populated with a list of U.S. states.
     * The first item is a placeholder prompt ("Select State").
     * <ul>
     *     <li>{@code states} list of all the states in the United States</li>
     * </ul>
     *
     * @return A JComboBox containing state names.
     */
    static JComboBox<String> getStates() {
        // Array of U.S. states to populate the combo box
        String[] states = {"Select State", "Alabama", "Alaska", "Arizona", "Arkansas", "California",
                "Colorado", "Connecticut", "Delaware", "Florida", "Georgia",
                "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas",
                "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts",
                "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana",
                "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico",
                "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma",
                "Oregon", "Pennsylvania", "Rhode Island", "South Carolina",
                "South Dakota", "Tennessee", "Texas", "Utah", "Vermont",
                "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"};

        // Return a new JComboBox initialized with the list of states
        return new JComboBox<>(states);
    }

    /**
     * Retrieves the icon image used for the application window.
     *
     * @return The Image object of the application icon.
     */
    public static Image getIcon() {
        // Return the image contained in the ImageIcon
        return icon.getImage();
    }
}
