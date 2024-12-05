import org.example.SourceCode.*;
import org.example.SourceCode.Process;

import javax.swing.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;

public class GUIScheduler extends JFrame {
    private JLabel jsFirstName;
    private JPanel Jpanel;
    private JComboBox comboBox1;
    private JButton startButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;

    public GUIScheduler(){
        setContentPane(Jpanel);
        setTitle("CPU Scheduler");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500,500);

        setVisible(true);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String noOfProcesses = textField1.getText();
                System.out.println();
                System.out.println(noOfProcesses);
                int noOfP = Integer.parseInt(noOfProcesses);
                List<Process> li = new ArrayList<>();
                for(int i = 0;i<noOfP;i++) {
                    Process member  = new Process("",0,0,0);
                    Processform pr1 = new Processform(member);

                }
            }
        });
    }

    public static void main(String[]args){
        new GUIScheduler();

    }


}
