import swingUI.InputInfo;
import swingUI.MatrixSizeMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

public class MainFrame extends JFrame {
    private JPanel panel;
    private JTable matrixTable;
    private JScrollPane scrollPane;
    private int rows = 5, columns = 5;
    private DefaultTableModel model;

    public MainFrame() {
        setTitle("Matrix Input");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 500);
      //  setLocationRelativeTo(null);

        //Menu Bar


        //Panel
        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        //Table
        model = new DefaultTableModel(InputInfo.getRows(),   InputInfo.getCols());
        matrixTable = new JTable(model);

        matrixTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        matrixTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        matrixTable.getColumnModel().getColumn(1).setPreferredWidth(100);

        scrollPane = new JScrollPane(matrixTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);

        setJMenuBar(createMenuBar());
        setVisible(true);
    }

    private JMenuBar createMenuBar(){
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
        helpMenu.add(openReadMe);
        helpMenu.add(about);

        menuBar.add(fileMenu);
        menuBar.add(settingsMenu);
        menuBar.add(helpMenu);

        exitItem.addActionListener(arg0 -> System.exit(0));
        setJMenuBar(menuBar);

        matrixSize.addActionListener(arg0 ->{
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    new MatrixSizeMenu();
                }
            });
        } );

        return menuBar;
    }

}