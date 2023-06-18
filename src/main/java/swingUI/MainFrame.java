package swingUI;

import org.apache.commons.math.optimization.GoalType;
import org.example.Simplex.*;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class MainFrame extends JFrame {
    private JPanel panel;
    private JTable matrixTable;
    private JTable functionTable;
    private JScrollPane matrixSP;
    private JScrollPane functionSP;
private JComboBox<String> functionComboBox;
    private JCheckBox negativeSolutionCheckbox;

    public MainFrame() {
        setTitle("Matrix Input");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 600);

        panel = new JPanel();
        panel.setLayout(new GridBagLayout());


        GridBagConstraints gbc = new GridBagConstraints();

        addHeaderLabelToPanel("Enter elements of a matrix",gbc,0,0);

        matrixSP = new JScrollPane();
        matrixSP.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        matrixSP.setMaximumSize(new Dimension(400, 300)); // Set maximum size
        matrixSP.setPreferredSize(new Dimension(200, 200));

        updateMatrixTable();

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 10, 10, 10); // Adjust padding as needed
        panel.add(matrixSP, gbc);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 0, 20));
        JButton calculatePrimalButton = new JButton("Calculate primal");
        JButton calculateDualButton = new JButton("Calculate dual");
        calculatePrimalButton.addActionListener(e -> {
            try{
            LinearModel model = createPrimalModel();
            SimplexSolver solver = createSolver(model);
            clearFunctionTable();
            clearMatrixTable();
            SwingUtilities.invokeLater(() -> new CalculationFrame(solver,model.getObjectiveFunction().getGoalType()));
            }catch (Exception e1){
                SwingUtilities.invokeLater(IncorrectInputFrame::new);
            }
        });
        calculateDualButton.addActionListener(e -> {
            try{
            LinearModel model = createDualModel();
            SimplexSolver solver = createSolver(model);
            clearMatrixTable();
            clearFunctionTable();
            SwingUtilities.invokeLater(() -> new CalculationFrame(solver,model.getObjectiveFunction().getGoalType()));
            }catch (Exception e2){
                SwingUtilities.invokeLater(IncorrectInputFrame::new);
            }
        });
        buttonPanel.add(calculatePrimalButton);
        buttonPanel.add(calculateDualButton);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.NORTHWEST;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(40, 10, 10, 60); // Adjust padding as needed
        panel.add(buttonPanel, gbc);

// Add a horizontal line
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // Adjust padding as needed
        panel.add(new JSeparator(), gbc);


// Add the functionPanel to the main panel using GridBagLayout constraints
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(0, 20, 10, 20); // Adjust padding as needed
        panel.add(createFunctionPanel(),gbc);

        add(panel);

        setJMenuBar(createMenuBar());
        setVisible(true);
    }


    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(exitItem);

        JMenu settingsMenu = new JMenu("Settings");
        JMenuItem matrixSize = new JMenuItem("Matrix Size");
        settingsMenu.add(matrixSize);

        JMenu helpMenu = new JMenu("Help");
        JMenuItem openReadMe = new JMenuItem("Open ReadMe");
        JMenuItem about = new JMenuItem("About");
        openReadMe.addActionListener(e->new ReadMe());
        about.addActionListener(e -> new AboutFrame());
        helpMenu.add(openReadMe);
        helpMenu.add(about);

        menuBar.add(fileMenu);
        menuBar.add(settingsMenu);
        menuBar.add(helpMenu);

        exitItem.addActionListener(arg0 -> System.exit(0));
        setJMenuBar(menuBar);

        matrixSize.addActionListener(arg0 -> {
            SwingUtilities.invokeLater(() -> new MatrixSizeMenu(this));
        });

        return menuBar;
    }
    private JPanel createFunctionPanel(){
        GridBagConstraints gbcFun = new GridBagConstraints();
       // Create functionPanel
        JPanel functionPanel = new JPanel(new GridBagLayout());
        functionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        addHeaderLabelToPanel("Enter elements of a function",gbcFun,0,2);

        // Create the JComboBox with options "Min" and "Max"
        String[] functionOptions = {"Min", "Max"};
        functionComboBox = new JComboBox<>(functionOptions);
        functionComboBox.setSelectedItem("Min"); // Select "Min" by default
        gbcFun.gridx = 0;
        gbcFun.gridy = 1;
        gbcFun.weightx = 0.1;
        gbcFun.weighty = 1.0;
        gbcFun.anchor = GridBagConstraints.WEST;
        gbcFun.fill = GridBagConstraints.NONE;
        gbcFun.insets = new Insets(5, 20, 5, 20); // Add spacing
        functionPanel.add(functionComboBox, gbcFun);

        functionSP = new JScrollPane();
        functionSP.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        functionSP.setMaximumSize(new Dimension(400, 30)); // Set maximum size
        updateFunctionTable();
        gbcFun.insets = new Insets(20, 20, 20, 20); // Add spacing
        gbcFun.gridx = 2;
        gbcFun.gridy = 1;
        gbcFun.gridwidth = 1;
        gbcFun.weightx = 0.8;
        gbcFun.weighty = 0.7;
        gbcFun.anchor = GridBagConstraints.EAST;
        gbcFun.fill = GridBagConstraints.BOTH;
        functionPanel.add(functionSP, gbcFun);

        // Add the checkbox
        negativeSolutionCheckbox = new JCheckBox("Negative solution");
        negativeSolutionCheckbox.setSelected(false); // Not selected by default
        gbcFun.gridx = 0;
        gbcFun.gridy = 3;
        gbcFun.gridwidth = 3;
        gbcFun.anchor = GridBagConstraints.WEST;
        gbcFun.insets = new Insets(5, 20, 5, 20); // Add spacing
        functionPanel.add(negativeSolutionCheckbox, gbcFun);

        return functionPanel;
    }

    public void setPreferredWidthToAllColumns(int width, JTable table) {
        TableColumnModel columnModel = table.getColumnModel();
        for (int columnIndex = 0; columnIndex < columnModel.getColumnCount(); columnIndex++) {
            TableColumn column = columnModel.getColumn(columnIndex);
            column.setPreferredWidth(width);
        }
    }
private void updateFunctionTable(){
    functionTable = new JTable(new DefaultTableModel(1, InputInfo.getVariables()+1) {
        @Override
        public String getColumnName(int column) {
            if (column < getColumnCount() - 1) {
                return "x" + (column + 1);
            } else {
                return "c";
            }
        }
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return JTextField.class; // Set the column class to JTextField
        }
        @Override
        public Object getValueAt(int row, int column) {
            Object value = super.getValueAt(row, column);
            if (value instanceof JTextField) {
                return ((JTextField) value).getText(); // Return the text value of JTextField cells
            }
            return value;
        }
    });
    setPreferredWidthToAllColumns(50, functionTable);
    functionTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    functionTable.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    functionSP.setViewportView(functionTable);
    setPreferredWidthToAllColumns(50,functionTable);
}


    private void updateMatrixTable() {
        matrixTable = new JTable(new DefaultTableModel(InputInfo.getEquations(), InputInfo.getVariables()+2) {
            @Override
            public String getColumnName(int column) {
                if (column < getColumnCount() - 2) {
                    return "x" + (column + 1);
                } else if (column == getColumnCount() - 2) {
                    return "sign";
                } else {
                    return "b";
                }
            }
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == getColumnCount() - 2) {
                    return JComboBox.class;
                }
                return JTextField.class; // Set the column class to JTextField
            }

            @Override
            public Object getValueAt(int row, int column) {
                if (column == getColumnCount() - 2) {
                    return super.getValueAt(row, column); // Return JComboBox for "sign" column
                }
                Object value = super.getValueAt(row, column);
                if (value instanceof JTextField) {
                    return ((JTextField) value).getText(); // Return the text value of JTextField cells
                }
                return value;
            }

        });
        matrixTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Create the JComboBox with the elements ">=", "=", and "<="
        JComboBox<String> comboBox = new JComboBox<>(new String[]{">=", "=", "<="});

        // Set the JComboBox as the cell editor for the "sign" column
        TableColumn signColumn = matrixTable.getColumnModel().getColumn(matrixTable.getColumnCount() - 2);
        signColumn.setCellEditor(new DefaultCellEditor(comboBox));
        setPreferredWidthToAllColumns(50,matrixTable);
        matrixTable.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add black thin borders to the matrixTable
        matrixSP.setViewportView(matrixTable);
    }

    public void updateTables(){
        updateFunctionTable();
        updateMatrixTable();
        panel.revalidate();
        panel.repaint();
    }
    void addHeaderLabelToPanel(String text, GridBagConstraints gbc, int x, int y){
        JLabel label = new JLabel(text);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 16));
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding
        label.setOpaque(true); // Enable background color
        label.setBackground(new Color(173, 216, 230)); // Set light blue background color
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(label, gbc);
    }
    private void clearMatrixTable() {
        int rowCount = matrixTable.getRowCount();
        int columnCount = matrixTable.getColumnCount();
        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                matrixTable.setValueAt("", row, column);
            }
        }
    }
    private void clearFunctionTable(){
        int rowCount = functionTable.getRowCount();
        int columnCount = functionTable.getColumnCount();
        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                functionTable.setValueAt("", row, column);
            }
        }
    }

 private LinearModel createPrimalModel(){
     int rowCount=0;
     int columnCount = functionTable.getColumnCount();

     int constTerm = columnCount-1;
     double[] coefs = new double[constTerm];
     for (int i = 0; i < constTerm; i++) {
         coefs[i] = Double.parseDouble(functionTable.getValueAt(rowCount,i).toString());
     }

     LinearModel model = new LinearModel(new LinearObjectiveFunction(coefs,Double.parseDouble(functionTable.getValueAt(rowCount,constTerm).toString()),getFuncGoal()));

     rowCount = matrixTable.getRowCount();
     columnCount = matrixTable.getColumnCount();
     int signNum = columnCount - 2;
     coefs = new double[signNum];
     for (int row = 0; row < rowCount; row++) {
         for (int column = 0; column < columnCount-2; column++) {
             coefs[column] = Double.parseDouble(matrixTable.getValueAt(row, column).toString());
         }
         Relationship rel = Relationship.getByString(matrixTable.getValueAt(row, columnCount-2).toString());
         double rightSide = Double.parseDouble(matrixTable.getValueAt(row, columnCount-1).toString());
         model.addConstraint(new LinearEquation(coefs,rel,rightSide));
     }
     return model;
 }
 private LinearModel createDualModel(){
       return createPrimalModel().dualForm();
 }
    private SimplexSolver createSolver(LinearModel model){
        SimplexSolver solver = new SimplexSolver();
        solver.setNonNegative(!negativeSolutionCheckbox.isSelected());
        solver.createTableau(model);
        return solver;
    }
    private GoalType getFuncGoal(){
        String goal =  Objects.requireNonNull(functionComboBox.getSelectedItem()).toString();
        if(goal.equals("Min")){
            return GoalType.MINIMIZE;
        }else {
            return GoalType.MAXIMIZE;
        }
    }
}