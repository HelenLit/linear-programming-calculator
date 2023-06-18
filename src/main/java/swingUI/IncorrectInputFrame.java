package swingUI;

import javax.swing.*;
import java.awt.*;

public class IncorrectInputFrame extends JFrame {
    public IncorrectInputFrame(){
    setTitle("Incorrect input exception");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(300, 200);
    setLocationRelativeTo(null);

    StringBuilder sb = new StringBuilder();
        sb.append("Found exceptional situation:\n\nIncorrect input, try again.");

    JTextArea solutionText = new JTextArea(sb.toString());
        solutionText.setEditable(false);
        solutionText.setLineWrap(true);
        solutionText.setWrapStyleWord(true);
        solutionText.setForeground(Color.RED);
        solutionText.setFont(solutionText.getFont().deriveFont(Font.ITALIC, 14));
    JScrollPane scrollPane = new JScrollPane(solutionText);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding
    add(scrollPane);
    JButton okButton = new JButton("OK");
        okButton.addActionListener( (e) -> dispose());
    JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
    add(buttonPanel, BorderLayout.SOUTH);
    setVisible(true);}
}
