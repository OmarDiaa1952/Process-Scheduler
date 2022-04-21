import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class GUI
{
    private JFrame frame;
    private JPanel mainPanel;
    private CustomPanel chartPanel;
    private JScrollPane tablePane;
    private JScrollPane chartPane;
    private JTable table;
    private JButton addBtn;
    private JButton removeBtn;
    private JButton computeBtn;
    private JLabel wtLabel;
    private JLabel wtResultLabel;
    private JLabel tatLabel;
    private JLabel tatResultLabel;
    private JComboBox option;
    private DefaultTableModel model;
    
    public GUI()
    {
        model = new DefaultTableModel(new String[]{"Process", "Arrival Time", "Burst Time", "Priority", "Waiting Time", "Turnaround Time"}, 0);
        
        table = new JTable(model);
        table.setFillsViewportHeight(true);
        tablePane = new JScrollPane(table);
        tablePane.setBounds(25, 25, 650, 250);
        
        addBtn = new JButton("Add");
        addBtn.setBounds(460, 280, 85, 25);
        addBtn.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        addBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                model.addRow(new String[]{"", "", "", "", "", ""});
//                model.addRow(new String[]{"P1", "0", "10", "3", "", ""});
//                model.addRow(new String[]{"P2", "2", "2", "1", "", ""});
//                model.addRow(new String[]{"P3", "3.5", "1", "1", "", ""});
//                model.addRow(new String[]{"P4", "4", "1", "4", "", ""});
//                model.addRow(new String[]{"P5", "1", "5", "2", "", ""});
//                model.addRow(new String[]{"P6", "22", "2", "1", "", ""});
            } 
        });
        
        removeBtn = new JButton("Remove");
        removeBtn.setBounds(550, 280, 85, 25);
        removeBtn.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        removeBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                
                if (row > -1) {
                    model.removeRow(row);
                }
            }
        });
        
        chartPanel = new CustomPanel();
//        chartPanel.setPreferredSize(new Dimension(700, 10));
        chartPanel.setBackground(Color.WHITE);
        chartPane = new JScrollPane(chartPanel);
        chartPane.setBounds(25, 310, 650, 100);
        
        wtLabel = new JLabel("Average Waiting Time:");
        wtLabel.setBounds(25, 425, 180, 25);
        tatLabel = new JLabel("Average Turn Around Time:");
        tatLabel.setBounds(25, 460, 180, 25);
        wtResultLabel = new JLabel();
        wtResultLabel.setBounds(215, 425, 180, 25);
        tatResultLabel = new JLabel();
        tatResultLabel.setBounds(215, 460, 180, 25);
        
        option = new JComboBox(new String[]{"FCFS", "SJF", "SRT", "PSN", "PSP", "RR"});
        option.setBounds(550, 420, 85, 20);
        
        computeBtn = new JButton("Compute");
        computeBtn.setBounds(550, 450, 85, 25);
        computeBtn.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        computeBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = (String) option.getSelectedItem();
                CPUScheduler scheduler;
                
                switch (selected) {
                    case "FCFS":
                        scheduler = new FirstComeFirstServe();
                        break;
                    case "SJF":
                        scheduler = new ShortestJobFirst();
                        break;
                    case "SRT":
                        scheduler = new ShortestRemainingTime();
                        break;
                    case "PSN":
                        scheduler = new PriorityNonPreemptive();
                        break;
                    case "PSP":
                        scheduler = new PriorityPreemptive();
                        break;
                    case "RR":
                        String tq = JOptionPane.showInputDialog("Time Quantum");
                        if (tq == null) {
                            return;
                        }
                        scheduler = new RoundRobin();
                        scheduler.setTimeQuantum(Integer.parseInt(tq)); 
                        break;
                    default:
                        return;
                }
                
                for (int i = 0; i < model.getRowCount(); i++)
                {
                    
                    String process = (String) model.getValueAt(i, 0);
                    float at = Float.parseFloat(((String) model.getValueAt(i, 1)).trim());
                    float bt = Float.parseFloat(((String) model.getValueAt(i, 2)).trim());
                    float pl;
                    
                    if(at < 0 || bt < 0) {
                        JOptionPane.showMessageDialog(null, "Enter valid input", "Invalid input", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    if (selected.equals("PSN") || selected.equals("PSP"))
                    {
                        if (!model.getValueAt(i, 3).equals(""))
                        {
                            pl = Float.parseFloat((String) model.getValueAt(i, 3));
                        }
                        else
                        {
                            pl = 1;
                        }
                    }
                    else
                    {
                        pl = 1;
                    }
                                        
                    scheduler.add(new Row(process, at, bt, pl));
                }
                
                scheduler.process();
                
                for (int i = 0; i < model.getRowCount(); i++)
                {
                    String process = (String) model.getValueAt(i, 0);
                    Row row = scheduler.getRow(process);
                    model.setValueAt(row.getWaitingTime(), i, 4);
                    model.setValueAt(row.getTurnaroundTime(), i, 5);
                }
                
                wtResultLabel.setText(Double.toString(scheduler.getAverageWaitingTime()));
                tatResultLabel.setText(Double.toString(scheduler.getAverageTurnAroundTime()));
                
                chartPanel.setTimeline(scheduler.getTimeline());
            }
        });
        
        mainPanel = new JPanel(null);
        mainPanel.setPreferredSize(new Dimension(700, 550));
        mainPanel.add(tablePane);
        mainPanel.add(addBtn);
        mainPanel.add(removeBtn);
        mainPanel.add(chartPane);
        mainPanel.add(wtLabel);
        mainPanel.add(tatLabel);
        mainPanel.add(wtResultLabel);
        mainPanel.add(tatResultLabel);
        mainPanel.add(option);
        mainPanel.add(computeBtn);
        
        frame = new JFrame("CPU Scheduler Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.add(mainPanel);
        frame.pack();
    }
    
    public static void main(String[] args)
    {
        new GUI();
    }
    
    class CustomPanel extends JPanel
    {   
        private List<Event> timeline;
        
        @Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            
            if (timeline != null)
            {
//                int width = 30;
                
                for (int i = 0; i < timeline.size(); i++)
                {
                    Event event = timeline.get(i);
                    int x = 30 * (i + 1);
                    int y = 20;
                    
                    g.drawRect(x, y, 30, 30);
                    g.setFont(new Font("Segoe UI", Font.BOLD, 13));
                    g.drawString(event.getProcessName(), x + 10, y + 20);
                    g.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                    g.drawString(Float.toString(event.getStartTime()), x - 5, y + 45);
                    
                    if (i == timeline.size() - 1)
                    {
                        g.drawString(Float.toString(event.getFinishTime()), x + 27, y + 45);
                    }
                    
//                    width += 30;
                }
                
//                this.setPreferredSize(new Dimension(width, 75));
            }
        }
        
        public void setTimeline(List<Event> timeline)
        {
            this.timeline = timeline;
            repaint();
        }
    }
}
