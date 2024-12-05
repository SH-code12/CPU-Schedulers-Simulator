import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;

public class GUIScheduler extends JFrame {
    private JLabel jsFirstName;
    private JTextArea textArea1;
    private JPanel Jpanel;
    private JComboBox comboBox1;
    private JButton startButton;

    public GUIScheduler(){
        setContentPane(Jpanel);
        setTitle("CPU Scheduler");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500,500);

        setVisible(true);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String noOfProcesses = textArea1.toString();
                int noOfP = Integer.parseInt(noOfProcesses);


            }
        });
    }

    public static void main(String[]args){
        new GUIScheduler();

    }


}
