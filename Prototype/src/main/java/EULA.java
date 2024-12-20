import javax.swing.*;
import java.awt.*;

public class EULA {
    private static final int WIDTH = 640;
    private static final int HEIGHT = 420;

    //Displays End User License Agreement
    public EULA() {
        displayEULA();
    }

    private static JTextArea getJTextArea() {
        JTextArea eula = new JTextArea("BY SUBMITTING AN APPLICATION, YOU AGREE TO THESE TERMS\n" +
                "\n" +
                "I ACCEPT MY STATUS MAY CHANGE AT ANY TIME. ACCEPTANCE OF ANY MATERIALS, OR\n" +
                "BENEFITS, DOES NOT GRANT A JOB. ONLY A WRITTEN SIGNATURE FROM THE PRESIDENT OR GENERAL MANAGER CAN CHANGE THE STATUS.\n" +
                "\n" +
                "I ACCEPT MY ANSWERS WILL BE CHECKED. FALSE ANSWERS MAY CHANGE MY STATUS.\n" +
                "SCHOOLS, EMPLOYERS, AND REFERENCES CAN BE CONTACTED. THE COMPANY IS NOT\n" +
                "RESPONSIBLE FOR WHAT FOLLOWS.\n" +
                "\n" +
                "I ACCEPT THE COMPANY’S DRUG AND ALCOHOL POLICY. I ACCEPT I WILL BE TESTED\n" +
                "BEFORE AND DURING MY EMPLOYMENT. RESULTS MAY EFFECT MY JOB.\n" +
                "\n" +
                "I ACCEPT MY CREDIT, AND CHARACTER, WILL BE CHECKED. THE FAIR CREDIT REPORTING ACT ALLOWS ME DETAILS OF THE REPORT.\n" +
                "\n" +
                "I ACCEPT MY JOB WILL HAVE A 60-DAY TRIAL PERIOD. DURING THIS, UNFINISHED BUSINESS OR I MAY OR MAY NOT END EMPLOYMENT FOR ANY REASON.\n");
        eula.setEditable(false);
        eula.setLineWrap(true);
        eula.setWrapStyleWord(true);
        return eula;
    }

    private void displayEULA() {
        JFrame frame = new JFrame("EULA");
        frame.setLayout(new BorderLayout());

        JScrollPane main = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JTextArea eula = getJTextArea();
        main.setViewportView(eula);

        JTextField signature = new JTextField();
        Dimension dimension = new Dimension(200, 5);
        signature.setPreferredSize(dimension);
        frame.add(main, BorderLayout.NORTH);
        frame.add(signature, BorderLayout.CENTER);
        String signatureText = "<html>By e-signing your name and checking the box, you are acknowledging the agreement above.<br>This e-signature is legally-binding</html>";
        JCheckBox signatureCheckBox = new JCheckBox(signatureText);
        frame.add(signatureCheckBox, BorderLayout.SOUTH);
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(_ -> {
            // Show a dialog box with a message indicating the user has finished
            JOptionPane.showMessageDialog(
                    null,                                // Parent component (null centers it on the screen)
                    "You finished!",                    // Message to display
                    "You Finished",                     // Title of the dialog box
                    JOptionPane.INFORMATION_MESSAGE     // Type of message (informational)
            );

            // Terminate the application with a status code of 0 (indicates normal exit)
            System.exit(0);
        });
        frame.add(submitButton, BorderLayout.AFTER_LINE_ENDS);

        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
