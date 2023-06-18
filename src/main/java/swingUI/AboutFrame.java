package swingUI;

import javax.swing.*;
import java.awt.*;

public class AboutFrame extends JFrame {
    public AboutFrame() throws HeadlessException {
        setTitle("About");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(480, 240);
        setLocationRelativeTo(null);

        StringBuilder sb = new StringBuilder();
        sb.append("This program was created as calculation and graphic work for Operational Research discipline in 2023 by Olena Litovska, student of KN-201 group of LPNU.");

        JTextArea solutionText = new JTextArea(sb.toString());
        solutionText.setEditable(false);
        solutionText.setLineWrap(true);
        solutionText.setWrapStyleWord(true);
        solutionText.setForeground(new Color(0,134,179));
        solutionText.setFont(solutionText.getFont().deriveFont(Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(solutionText);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding
        add(scrollPane);
        JButton okButton = new JButton("OK");
        okButton.addActionListener( (e) -> dispose());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        add(buttonPanel, BorderLayout.SOUTH);
        setVisible(true);
    }
}
