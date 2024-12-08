import javax.swing.*;
import java.awt.*;
import org.example.SourceCode.*;

import org.example.SourceCode.Process;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class Processform extends JFrame {
    private JPanel processpanel;
    private JButton button1;
    private JTextField color;


    private JTextField arriveTime;
    public static boolean state  = false;
    private JTextField burstTime;
    private JTextField name;

    public JLabel header;
    private JTextField PriorityNumber;


    public Processform(Process p , int index, int max, List<Process> li,GUIScheduler g){
// write here the code

        setContentPane(processpanel);
        setTitle("CPU Scheduler");
        setSize(500,500);
        header.setText("Enter Process "+(index) + " Information");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Color c = JColorChooser.showDialog(null, "Choose a Color", Color.WHITE);
                    p.setname(name.getText());
                    System.out.println(name.getText());

                    p.color =c;
                    g.mapOfColors.put(p.name,p.color);
                    p.setArrivalTime(Integer.parseInt(arriveTime.getText()));
                    p.setBurstTime(Integer.parseInt(burstTime.getText()));
                    p.setPriority(Integer.parseInt(PriorityNumber.getText()));
                    li.add(p);
                    if (index != max) {
                        Processform pr1 = new Processform(new Process(), index + 1, max, li,g);
                    dispose();
                    }
                    else{
                        dispose();
                        g.run(li);
                    }


                }
                catch (Exception ep){
                    JOptionPane.showMessageDialog(processpanel , "Enter valid data",
                            "ERROR", JOptionPane.ERROR_MESSAGE);
                }




            }
        });

    }

    public static void main(String[] args) {
     //   new Processform();
    }


}
