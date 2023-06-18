package swingUI;

import javax.swing.*;
import java.awt.*;

public class MatrixSizeMenu extends JFrame {
    private MainFrame mainFrame;
    private InputPanel inputPanel;
    public MatrixSizeMenu(MainFrame frame) {
        mainFrame = frame;
        setTitle("Choose table size");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        JLabel rowsLabel = new JLabel("Number of equations:");
        JLabel columnsLabel = new JLabel("Number of variables:");

        inputPanel = new InputPanel();

        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.LINE_END;
        mainPanel.add(rowsLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.LINE_START;
        mainPanel.add(inputPanel.getRowsSpinner(), constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.LINE_END;
        mainPanel.add(columnsLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.LINE_START;
        mainPanel.add(inputPanel.getColumnsSpinner(), constraints);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        mainPanel.add(buttonsPanel, constraints);

        add(mainPanel);

        okButton.addActionListener(e -> {
            int rows = inputPanel.getRowValue();
            int columns = inputPanel.getColumnValue();

            InputInfo.setEquations(rows);
            InputInfo.setVariables(columns);

            mainFrame.updateTables();
            dispose();
        });

        cancelButton.addActionListener(e -> dispose());

        setVisible(true);
    }
    class InputPanel {
        private JSpinner rowsSpinner, columnsSpinner;

        public InputPanel() {
            rowsSpinner = new JSpinner();
            columnsSpinner = new JSpinner();

            SpinnerNumberModel rowsModel = new SpinnerNumberModel(InputInfo.getEquations(), 1, 20, 1);
            SpinnerNumberModel columnsModel = new SpinnerNumberModel(InputInfo.getVariables(), 1, 20, 1);
            rowsSpinner.setModel(rowsModel);
            columnsSpinner.setModel(columnsModel);
        }

        public int getRowValue() {
            return (int) rowsSpinner.getValue();
        }

        public int getColumnValue() {
            return (int) columnsSpinner.getValue();
        }

        public JSpinner getRowsSpinner() {
            return rowsSpinner;
        }

        public JSpinner getColumnsSpinner() {
            return columnsSpinner;
        }
    }
}