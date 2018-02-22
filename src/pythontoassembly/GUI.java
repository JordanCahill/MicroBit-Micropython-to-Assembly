
package pythontoassembly;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Jordan Cahill
 * @date 22-Feb-2018
 */
public class GUI extends javax.swing.JFrame{
    
    private String directory;
    private boolean done = false;
    private JTextField directoryField = new JTextField(30);
    JFrame frame = new JFrame("PythonToAssembly");
    
    public GUI(){
        
        // Set up the GUI
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints c1 = new GridBagConstraints(); // row 1
        GridBagConstraints c2 = new GridBagConstraints(); // row 2
        GridBagConstraints c3 = new GridBagConstraints(); // row 3       
        JPanel panel = new JPanel(layout);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setVisible(true);
        frame.setSize(500, 170);
        
        // Add text
        JLabel t1 = new JLabel("Welcome,");
        c1.anchor = GridBagConstraints.SOUTH;
        c1.gridwidth = 2;
        c1.gridx = 0;
        c1.gridy = 0;
        c1.weighty = 0.7;
        panel.add(t1,c1);
        // ----------
        JLabel t2 = new JLabel("Please select the python file you wish to convert");
        c1.anchor = GridBagConstraints.NORTH;
        c1.gridx = 0;
        c1.gridy = 1;
        c1.weighty = 0.1;
        panel.add(t2,c1);
        
        // Add a button used to open to files
        JButton openFile = new JButton("Choose file..");
        c2.gridx = 1;
        c2.gridy = 2;
        c2.weightx = 0.3;
        c2.weighty = 0.2;
        panel.add(openFile,c2);
        
        // Add preview of directory
        c2.gridx = 0;
        c2.weightx = 0.7;
        panel.add(directoryField,c2);
        
             
        // Add listener for functionality for openFile button
        openFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openFileActionPerformed(evt);
            }
        });
    }
    
    private void openFileActionPerformed(java.awt.event.ActionEvent evt) {
        
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Python Files(*.py)", "py");
        fileChooser.setFileFilter(filter);
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            int selectedOption = JOptionPane.showConfirmDialog(null,"Would you like to convert '" + fileChooser.getSelectedFile().getName() + "'?","Choose",JOptionPane.YES_NO_OPTION); 
            if (selectedOption == JOptionPane.YES_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                this.directory = selectedFile.toString();
                directoryField.setText(directory);
                System.out.println(selectedFile.toString());
                JOptionPane.showConfirmDialog(null,
                    "Assembly file created in root package folder",
                    "Conversion Successful",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE);
                frame.dispose();
                
                this.done = true; // Sends a boolean back to main to allow the system to shut down
                
            }
        }
    }
    
    public String getDirectory(){
        return this.directory;
    }
    
    public boolean isFinished(){
        return this.done;
    }

}
