import org.example.SourceCode.*;
import org.example.SourceCode.Process;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.util.List;

public class GUIScheduler extends JFrame {
    private JLabel jsFirstName;
    private JPanel Jpanel;
    private JComboBox comboBox1;
    Map<String, Color> mapOfColors = new HashMap<>();
    private JButton startButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;

    public GUIScheduler(){
        setContentPane(Jpanel);
        setTitle("CPU Scheduler");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(500,500);
        setLocationRelativeTo(null);

        setVisible(true);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String noOfProcesses = textField1.getText();
                System.out.println();
                System.out.println(noOfProcesses);
                int noOfP = Integer.parseInt(noOfProcesses);
                if(comboBox1.getSelectedIndex()!=3) {
                    List<Process> li = new ArrayList<>();

                    Process member = new Process();
                    Processform pr1 = new Processform(member, 1, noOfP, li, GUIScheduler.this);
                }
                else{
                    List<fcai_process> li = new ArrayList<>();

                    fcai_process member = new fcai_process();
                    fcaiform pr1 = new fcaiform(member, 1, noOfP, li, GUIScheduler.this);
                }

                        dispose();





            }
        });
    }


    public void run(List<Process>li){
        System.out.println("run arrived");
        if(comboBox1.getSelectedIndex()==0){

            NonPreemptivePriorityScheduler proces1 = new NonPreemptivePriorityScheduler(li,Integer.parseInt(textField3.getText()));
            proces1.schedule();

            Presentation Mypresesntation = new Presentation(proces1.executionOrder,this);
            Mypresesntation.setVisible(true);


        }
        else if(comboBox1.getSelectedIndex()==1){
// Shortest job first
            SJFScheduler proces2 = new SJFScheduler(li,Integer.parseInt(textField3.getText()));
            proces2.schedule();


            Presentation Mypresesntation = new Presentation(proces2.executionOrder,this);
            Mypresesntation.setVisible(true);

        }
        else if(comboBox1.getSelectedIndex()==2) {
// shortest remaining time first
            System.out.println("schdule arrived ");


            SRTFScheduler proces3 = new SRTFScheduler(li, Integer.parseInt(textField3.getText()));
            proces3.schedule();

            Presentation Mypresesntation = new Presentation(proces3.executionOrder, this);
            Mypresesntation.setVisible(true);
        }

    }
    public void run(List<fcai_process>prcs,boolean f){
        System.out.println("schdule arrived ");
        double v1 = prcs.get(prcs.size() - 1).arrivalTime / 10.0;
        System.out.println("v1 is " + v1);
        double v2 = 0;
        for (fcai_process x : prcs) {
            v2 = Math.max(v2, (double) x.burstTime);
        }
        v2 /= 10.0;
        System.out.println("v2 is " + v2);

        // Calculate fcai for each process
        for (fcai_process x : prcs) {
            double num = (10 - x.priority) + ((double) x.arrivalTime / v1) + ((double) x.remainingTime / v2);
            x.fcai = (int) Math.ceil(num);
            System.out.println("FCAI Factor for " + x.name + " = " + x.fcai);
        }

        FCAI proces3 = new FCAI(prcs, v1,v2);
        proces3.schedule();

        Presentation Mypresesntation = new Presentation(proces3.resault, this);
        Mypresesntation.setVisible(true);
    }

    public static void main(String[]args){
        new GUIScheduler();
    }


}
