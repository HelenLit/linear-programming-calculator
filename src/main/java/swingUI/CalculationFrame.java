package swingUI;

import org.apache.commons.math.optimization.GoalType;
import org.example.Simplex.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CalculationFrame extends JFrame {
    private SimplexSolver solver;
    private int iterationNumber = 1;
    private JLabel iterationLabel;
    private JTable calcTable;
    private JButton cancel;
    private JButton next;
    private JButton toFinal;
    private JPanel tablePanel;
    private JScrollPane tableSP;


    public CalculationFrame(SimplexSolver solv, GoalType goalType) {
        iterationNumber = 1;
        this.solver = solv;
        solver.setPrintTableMethod(this::showTable);

        setTitle("Calculation");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 400);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        tablePanel = new JPanel(new GridBagLayout());
        // Add iteration label
        iterationLabel = new JLabel();
        iterationLabel.setFont(iterationLabel.getFont().deriveFont(Font.BOLD, 14));
        iterationLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding
        iterationLabel.setOpaque(true); // Enable background color
        iterationLabel.setBackground(new Color(173, 216, 230));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(10, 10, 10, 10);
        tablePanel.add(iterationLabel, gbc);

        // Add value table
        tableSP = new JScrollPane(calcTable);
        tableSP.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        tableSP.setMaximumSize(new Dimension(490, 350)); // Set maximum size
        tableSP.setPreferredSize(new Dimension(450, 200));

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 10, 10, 10);
        tablePanel.add(tableSP, gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);

        panel.add(tablePanel);
        // Add buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        cancel = new JButton("Cancel");
        next = new JButton("Next");
        toFinal = new JButton("Get solution");
        cancel.addActionListener(e -> {
            dispose();
        });
        next.addActionListener(e -> {
            LinearEquation sol = null;
            try {
                sol = solver.solveNext();
            } catch (UnboundedSolutionException e1) {
                SwingUtilities.invokeLater(UnboundedSolutionFrame::new);
                dispose();
            } catch (NoFeasibleSolutionException e2) {
                SwingUtilities.invokeLater(NoFeasibleSolutionFrame::new);
                dispose();
            } catch (Exception e4) {
                SwingUtilities.invokeLater(IncorrectInputFrame::new);
            }
            if (sol != null) {
                LinearEquation finalSol = sol;
                SwingUtilities.invokeLater(() -> new SolutionFrame(finalSol, goalType));
                dispose();
            }
        });
        toFinal.addActionListener(e -> {
            LinearEquation sol = null;
            try {
                sol = solver.solveToFinal();
            } catch (UnboundedSolutionException e1) {
                SwingUtilities.invokeLater(UnboundedSolutionFrame::new);
                dispose();
            } catch (NoFeasibleSolutionException e2) {
                SwingUtilities.invokeLater(NoFeasibleSolutionFrame::new);
                dispose();
            } catch (Exception e4) {
                SwingUtilities.invokeLater(IncorrectInputFrame::new);
            }
            if (sol != null) {
                LinearEquation finalSol = sol;
                SwingUtilities.invokeLater(() -> new SolutionFrame(finalSol, goalType));
                dispose();
            }
        });
        buttonPanel.add(cancel);
        buttonPanel.add(next);
        buttonPanel.add(toFinal);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(buttonPanel, gbc);
        add(panel);
        try {
            solver.solveFirst();
        } catch (UnboundedSolutionException e1) {
            SwingUtilities.invokeLater(UnboundedSolutionFrame::new);
            dispose();
        } catch (NoFeasibleSolutionException e2) {
            SwingUtilities.invokeLater(NoFeasibleSolutionFrame::new);
            dispose();
        }
        setVisible(true);
    }

    private void showTable(SimplexTableau tabl) {
        double[][] data = tabl.tableau.getData();

        iterationLabel.setText("Iteration #" + iterationNumber++);

        DefaultTableModel model = new DefaultTableModel(data.length, data[0].length);
        calcTable = new JTable(model);
        calcTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        calcTable.setFillsViewportHeight(true);
        calcTable.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        for (int row = 0; row < data.length; row++) {
            for (int col = 0; col < data[row].length; col++) {
                double value = data[row][col];
                String formattedValue = String.format("%.3f", value);
                calcTable.setValueAt(formattedValue, row, col);
            }
        }
        tablePanel.revalidate();
        tablePanel.repaint();
        tableSP.setViewportView(calcTable);

    }
}
