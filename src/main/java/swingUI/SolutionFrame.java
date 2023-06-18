package swingUI;

import org.apache.commons.math.optimization.GoalType;
import org.example.Simplex.LinearEquation;

import javax.swing.*;
import java.awt.*;

public class SolutionFrame extends JFrame {
    public SolutionFrame(LinearEquation solution, GoalType goalType) {
        setTitle("Solution");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);

        StringBuilder sb = new StringBuilder();
        sb.append("Optimal solution is found:\n");
        sb.append("X* = (");
        double[] data = solution.getCoefficients().getData();
        for (int i = 0; i < data.length; i++) {
            sb.append(data[i]);
            if (i < data.length - 1) {
                sb.append(", ");
            }
        }
        sb.append(")\n\n"+goalType+"\n\n");
        sb.append("F(X) ");
        sb.append(solution.getRelationship());
        sb.append(" ");
        sb.append(solution.getRightHandSide());

        JTextArea solutionText = new JTextArea(sb.toString());
        solutionText.setEditable(false);
        solutionText.setLineWrap(true);
        solutionText.setWrapStyleWord(true);
        solutionText.setFont(solutionText.getFont().deriveFont(Font.ITALIC, 14));
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

