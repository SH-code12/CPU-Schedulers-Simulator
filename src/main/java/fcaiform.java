import org.example.SourceCode.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class fcaiform extends JFrame{
    private JPanel fcaiprocess;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField4;
    private JTextField textField3;
    private JLabel header;
    private JButton button1;
    private JTextField textField5;

    public fcaiform( fcai_process p , int index, int max, List<fcai_process> li, GUIScheduler g){
        setContentPane(fcaiprocess);
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
                    p.setname(textField1.getText());
                    System.out.println(textField1.getText());

                    g.mapOfColors.put(p.name,c);
                    p.setArrivalTime(Integer.parseInt(textField2.getText()));
                    p.setBurstTime(Integer.parseInt(textField3.getText()));
                    p.remainingTime = Integer.parseInt(textField3.getText());
                    p.setPriority(Integer.parseInt(textField4.getText()));
                    p.setQuantum(Integer.parseInt(textField5.getText()));
                    li.add(p);
                    if (index != max) {
                        fcaiform pr1 = new fcaiform(new fcai_process(), index + 1, max, li,g);
                        dispose();
                    }
                    else{
                        dispose();
                        g.run(li,false);
                    }


                }
                catch (Exception ep){
                    JOptionPane.showMessageDialog(fcaiprocess , "Enter valid data",
                            "ERROR", JOptionPane.ERROR_MESSAGE);
                }




            }
        });
    }
}
