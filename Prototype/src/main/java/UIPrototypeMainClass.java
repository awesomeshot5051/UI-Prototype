import javax.swing.*;
import java.awt.*;

public class UIPrototypeMainClass {
    static ImageIcon icon = new ImageIcon("D:\\UI-Prototype\\Prototype\\Icon\\UNFinshedBusiness.png");

    public static void main(String[] args) {
        new FirstPage();
    }

    static JComboBox<String> getStates() {
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
        return new JComboBox<>(states);
    }

    public static Image getIcon() {
        return icon.getImage();
    }
}
