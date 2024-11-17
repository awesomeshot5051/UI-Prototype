import javax.swing.*;
import java.awt.*;

public class EULA {
    private static final int WIDTH = 640;
    private static final int HEIGHT = 480;

    public EULA() {
        JFrame frame = new JFrame("EULA");
        frame.setLayout(new BorderLayout());

        JScrollPane main = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JTextArea eula = getJTextArea();
        main.setViewportView(eula);

        JTextField signature = new JTextField();
        JTextArea signatureText = new JTextArea("By e-signing your name and checking the box, \nyou are acknowledging the agreement above.\nThis e-signature is legally-binding");
        signatureText.setEditable(false);
        signatureText.setLineWrap(true);
        signatureText.setWrapStyleWord(true);
        Dimension dimension = new Dimension(200, 25);
        signature.setPreferredSize(dimension);
        frame.add(main, BorderLayout.NORTH);
        frame.add(signature, BorderLayout.CENTER);
        frame.add(signatureText, BorderLayout.SOUTH);

        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
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

    public static void main(String[] args) {
        new EULA();
    }
}
