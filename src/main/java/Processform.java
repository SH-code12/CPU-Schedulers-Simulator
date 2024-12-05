import javax.swing.*;
import org.example.SourceCode.*;
import org.example.SourceCode.Process;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Processform extends JFrame {
    private JPanel processpanel;
    private JButton button1;
    private JTextField color;
    private JTextField arriveTime;
    private JTextField burstTime;
    private JTextField name;
    private JTextField PriorityNumber;


    public Processform(Process p ){
// write here the code
        setContentPane(processpanel);
        setTitle("CPU Scheduler");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500,500);

        setVisible(true);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                p.setname( name.getText());
                p.setArrivalTime(Integer.parseInt(arriveTime.getText()));
                p.setBurstTime( Integer.parseInt(burstTime.getText())) ;
                p.setPriority( Integer.parseInt(PriorityNumber.getText()));
                dispose();

            }
        });

    }



    public static void main(String[] args) {
       // new Processform();
    }
}
