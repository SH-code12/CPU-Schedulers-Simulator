import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Map;

public class Presentation extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JScrollPane screen;

    public Presentation(List<String> processes, GUIScheduler g) {
        // Initialize components
        contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS)); // Vertical layout for processes

        buttonOK = new JButton("OK");
        buttonCancel = new JButton("Cancel");

        // Create a panel to hold all process components
        JPanel processPanel = new JPanel();
        processPanel.setLayout(new BoxLayout(processPanel, BoxLayout.Y_AXIS));

        // Add each process with its color
        for (String process : processes) {
            JPanel color = new JPanel();
            color.setLayout(new FlowLayout(FlowLayout.LEFT)); // Horizontal layout for label + color box

            JLabel label = new JLabel(process); // Label for the process name
            color.add(label);

            // Color box for the associated color
            Color myColor = g.mapOfColors.get(process);
            JPanel colorBox = new JPanel();
            colorBox.setPreferredSize(new Dimension(20, 20)); // Set size of the color box
            if (myColor != null) {
                colorBox.setBackground(myColor); // Set the background to the associated color
            } else {
                colorBox.setBackground(Color.GRAY); // Default color if not found
            }
            color.add(colorBox);

            processPanel.add(color); // Add this process panel to the parent panel
        }

        // Add the process panel to a scroll pane
        screen = new JScrollPane(processPanel);
        contentPane.add(screen);

        // Add OK and Cancel buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(buttonOK);
        buttonPanel.add(buttonCancel);
        contentPane.add(buttonPanel);

        // Configure dialog
        setContentPane(contentPane);
        setModal(true);
        setTitle("Presentation Dialog");
        setSize(400, 300); // Set a default size
        setLocationRelativeTo(null); // Center the dialog
        getRootPane().setDefaultButton(buttonOK);

        // Add button actions
        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());

        // Add window close behavior
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // Add ESC key behavior
        contentPane.registerKeyboardAction(e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // OK button logic
        System.out.println("OK clicked");
        dispose();
    }

    private void onCancel() {
        // Cancel button logic
        System.out.println("Cancel clicked");
        dispose();
    }

}


