package GUI;

import Model.Server;
import Model.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class SimulationFrame extends JFrame {
    public JTextArea queuesPane = new JTextArea(10, 100);
    private JScrollPane scroll;
    private JTextField textFieldC = new JTextField();
    private JTextField textFieldQ = new JTextField();
    private JTextField textFieldTMax = new JTextField();
    private JTextField textFieldMinArrTime = new JTextField();
    private JTextField textFieldMaxArrTime = new JTextField();
    private JTextField textFieldMinSerTime = new JTextField();
    private JTextField textFieldMaxSerTime = new JTextField();
    private JButton simulationButton = new JButton("Start");

    public void addSimBtnListener(ActionListener listener) {
        simulationButton.addActionListener(listener);
    }

    public Integer getNoClients() {
        String s = textFieldC.getText();
        return Integer.parseInt(s);
    }

    public Integer getNoQ() {
        String s = textFieldQ.getText();
        return Integer.parseInt(s);
    }

    public Integer getSimInterval() {
        String s = textFieldTMax.getText();
        return Integer.parseInt(s);
    }

    public Integer getArrMin() {
        String s = textFieldMinArrTime.getText();
        return Integer.parseInt(s);
    }

    public Integer getArrMax() {
        String s = textFieldMaxArrTime.getText();
        return Integer.parseInt(s);
    }

    public Integer getExMin() {
        String s = textFieldMinSerTime.getText();
        return Integer.parseInt(s);
    }

    public Integer getExMax() {
        String s = textFieldMaxSerTime.getText();
        return Integer.parseInt(s);
    }

    public SimulationFrame() {
        queuesPane.setPreferredSize(new Dimension(600, 100));
        textFieldC.setPreferredSize(new Dimension(100, 20));
        textFieldQ.setPreferredSize(new Dimension(100, 20));
        textFieldTMax.setPreferredSize(new Dimension(100, 20));
        textFieldMinArrTime.setPreferredSize(new Dimension(100, 20));
        textFieldMaxArrTime.setPreferredSize(new Dimension(100, 20));
        textFieldMaxSerTime.setPreferredSize(new Dimension(100, 20));
        textFieldMaxSerTime.setPreferredSize(new Dimension(100, 20));

        JLabel labelC = new JLabel("Clienti: ");
        JLabel labelQ = new JLabel("Cozi: ");
        JLabel labeltmax = new JLabel("Timp simulare: ");
        JLabel labelArrTime = new JLabel("Timp minim sosire: ");
        JLabel labelArrTime2 = new JLabel("Timp maxim sosire: ");
        JLabel labelSerTime = new JLabel("Timp minim servire: ");
        JLabel labelSerTime2 = new JLabel("Timp maxim servire: ");

        JPanel labelsPanel = new JPanel(new GridLayout(7, 2, 5, 5));
        labelsPanel.add(labelC);
        labelsPanel.add(textFieldC);
        labelsPanel.add(labelQ);
        labelsPanel.add(textFieldQ);
        labelsPanel.add(labeltmax);
        labelsPanel.add(textFieldTMax);
        labelsPanel.add(labelArrTime);
        labelsPanel.add(textFieldMinArrTime);
        labelsPanel.add(labelArrTime2);
        labelsPanel.add(textFieldMaxArrTime);
        labelsPanel.add(labelSerTime);
        labelsPanel.add(textFieldMinSerTime);
        labelsPanel.add(labelSerTime2);
        labelsPanel.add(textFieldMaxSerTime);

        JScrollPane scrollPanelQ = new JScrollPane(queuesPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanelQ.setViewportView(queuesPane);

        JPanel finalPanel = new JPanel(new BorderLayout());
        finalPanel.add(labelsPanel, BorderLayout.NORTH);
        finalPanel.add(scrollPanelQ, BorderLayout.CENTER);
        finalPanel.add(simulationButton, BorderLayout.SOUTH);

        this.setContentPane(finalPanel);
        this.setResizable(true);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
