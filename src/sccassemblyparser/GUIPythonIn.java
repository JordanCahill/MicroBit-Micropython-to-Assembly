package sccassemblyparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;



/**
 *
 * @author Jorda
 */
public class GUIPythonIn extends javax.swing.JFrame {

    /* Global variabales are necessary due to the dynamic nature of the program's input */
    private static ArrayList<String> ISRTimerSetUp = new ArrayList<>();
    private static ArrayList<String> ISR0 = new ArrayList<>();
    private static ArrayList<String> ISR1 = new ArrayList<>();
    private static boolean buttonDetected;
    private static boolean buttonA; 
    private static boolean buttonB;
    private static boolean ISRA;
    private static boolean ISRB;
    private static boolean interruptsNeeded;
    private static ArrayList<String> displayImage = new ArrayList<>();
    
    /**
     * Creates new form GUIJavaScriptIn
     */
    public GUIPythonIn() {
        initComponents();
        this.setVisible(true);
        
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | 
                 IllegalAccessException | 
                 InstantiationException | 
                 UnsupportedLookAndFeelException ex) {}
        //</editor-fold>
        //</editor-fold>
        
    }

    /**
     * This method is called from within the constructor to initialize the form
     * Code generated using the NetBeans JFrame Form Editor
     * Creates the GUI Components
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        SubmitDirectoryButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextField1.setToolTipText("");

        jButton1.setText("Open File");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GetDirectoryButtonActionPerformed(evt);
            }
        });

        SubmitDirectoryButton.setText("Convert");
        SubmitDirectoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubmitDirectoryButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Lucida Sans", 0, 11)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 51));
        jLabel1.setText("Please select the Python file you wish to convert..");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton1))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(125, 125, 125)
                        .addComponent(SubmitDirectoryButton, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addComponent(SubmitDirectoryButton)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * On button press, this method allows the user to search for a file with the file extension ".py" and
     * saves the directory of the file as a String, the String is then saved to the text field to be retrieved
     * later
     * @param evt Button press
     */
    private void GetDirectoryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GetDirectoryButtonActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Python Files (*.py)", "py");
        fileChooser.setFileFilter(filter);
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String directory = fileChooser.getSelectedFile().toString();
            jTextField1.setText(directory);      
        }
    }//GEN-LAST:event_GetDirectoryButtonActionPerformed

    /**
     * On press, takes the directory found in the text field and saves it as a new String.
     * Reads in the file associated with the string line by line and calls the Format() method on it
     * Contains additional functionality to accommodate interrupts for the final output
     * Also calls a method to format the master output array "AssemText" and another method to write 
     * the array as a ".asm" file
     * @param evt Button press
     */
    private void SubmitDirectoryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubmitDirectoryButtonActionPerformed
        // Read in the file based on the directory in the text field
        String directory = jTextField1.getText();
        FileReader in = null;
        try {
            in = new FileReader(directory);
        } catch (FileNotFoundException ex) {}
        BufferedReader br = new BufferedReader(in);
        System.out.println("File found..");
     
        String[] assemText = new String[100]; // Text to be formatted and added to .asm file
        assemText[0] = "; File created using a python to assembly converter";
        int L=1; // Counter for the index of the output commands
        String line;
        System.out.println("Reading python file and converting..");
 
        try {
            while ((line = br.readLine()) != null) { // Loop through the text file
                
                assemText[L] = Format(line); // Format each line one by one, returns output assembly command
                
                // Support for display.show() (Python)
                if (assemText[L].contains("Displaying image for line")){
                    for (String s: displayImage){
                        assemText[L] = s; L++; // Add to master array and increment index
                    }
                }
                L++; // Increment index
            }
        } catch (IOException ex) {}
        
        assemText[L] = "END"; L++; // All programs must finish with "END"
        try {in.close();} catch (IOException ex) {} // Close the buffer
             
        buttonDetected = false; // Temporary disable to allow formatting
        /* If button A or button B was pressed, flags ISRA or ISRB will be asserted and
           the ISR0 or ISR1 arrays will contain commands to be added to the respective
           interrupt service routines
        */
        if (ISRA == true){ // Button A detected
            assemText[L] = ";"; L++;
            assemText[L] = "ISR0:   ORG 92"; L++;
            for (String s: ISR0){
                String f = Format(s); // Format each line
                assemText[L] = f; // Add formatted line to master array
                if (assemText[L].contains("Displaying image for line")){ // Support for display.show()
                    for (String t: displayImage){
                        assemText[L] = t; L++; // Add to master array and increment index
                    }
                } L++; // Increment index
            }
            assemText[L] = "RETI"; L++; // ISR must end with "RETI", increment index
        }
        if (ISRB == true){ // Button B detected
            assemText[L] = ";"; L++;
            assemText[L] = "ISR1:   ORG 104"; L++;
            for (String s: ISR1){
                String f = Format(s); // FOrmat each line
                assemText[L] = f; // Add formatted line to master array
                if (assemText[L].contains("Displaying image for line")){ // Support for display.show()
                    for (String t: displayImage){ 
                        assemText[L] = t; L++; // Add to master array and increment index
                    }
                } L++; // Increment index
            }
            assemText[L] = "RETI"; L++; // ISR must end with "RETI", increment index
        }
        buttonDetected = true; // Re-enable buttonDetected
        
        // Sets up the timer for a user-defined length of time
        assemText[L] = ";"; L++;
        for (String s: ISRTimerSetUp){
            assemText[L] = s; L++;
        }
        // Add enableInterrupts() to asm if flag set to true
        if (interruptsNeeded){
            assemText[L] = ";"; L++;
            assemText[L] = "enableInterrupts:"; L++;
            assemText[L] = "SETBSFR SFR0, 0"; L++;
            assemText[L] = "SETBSFR SFR0, 1"; L++;
            assemText[L] = "SETBSFR SFR0, 2"; L++;
            assemText[L] = "RET"; L++;
        }
     
        // Format the master output array by removing "nulls" and blank lines 
        Formatter formatOut = new Formatter();
        String[] outputText = formatOut.formatOutputText(assemText,L);
              
        try { // Write the final text to an .asm file
            formatOut.CreateAsmFile(this, outputText); 
        } catch (FileNotFoundException ex) {}
    }//GEN-LAST:event_SubmitDirectoryButtonActionPerformed
    

    /**
     * Parses a Python command to an appropriate assembly command(s)
     * 
     * @param Python command
     * @return Assembly command
     */
    private static String Format(String line) {
        
        String formatted = ""; // Assembly command to be returned
     
        // Sleep command functionality
        if(line.contains("sleep") && buttonDetected == false){
            Sleep sleep = new Sleep(line,true); // Sleep object to invoke methods
            String[] TmrVals = sleep.getOutputVals(); // Get values to pass into TMRL and TMRH registers
            formatted = "CALL settingUpTimer"; // Call assembly method to set up timer
            Timer setupNoReload = new Timer(); // Timer object to invoke set up method
            ISRTimerSetUp = setupNoReload.setUpTimerNoReload(TmrVals); // Add assembly commands to array
        }
        // Support for LED functionality
        if(line.contains("display") && (buttonDetected == false)){
            LED led = new LED(line); // LED object to invoke methods
            formatted = led.getOutputLine(); 
        }
        // Additional LED support to display pre-programmed images (Hardcoded in LED class)
        if(line.contains("display.show") && (buttonDetected == false)){
            LED led = new LED(line); // LED object to invoke displayImage()
            displayImage = led.displayImage();
            formatted = "; Displaying image for line: " + line;
        }
        // Convert loops after "onButtonPressed" calls
        // This if loop determines which button (A or B) press loop the current line falls under, if any
        if((line.contains("button")) && (line.contains("is_pressed"))){
            if(line.contains("button_a")){ // Button A detected
                buttonA = true; buttonB = false;
                ISRA = true;
            }else if(line.contains("button_b")){ // Button B detected
                buttonB = true; buttonA = false;
                ISRB = true;
            }
            buttonDetected = true;
            if(ISRA ^ ISRB){ // Enable interrupts if A or B detected
                formatted = "CALL enableInterrupts";
                interruptsNeeded = true;
            }
            line = "\t"; // So the line is not added to loop array
        }
        // Need to detect Python indent (Indicates a loop) and replace with a random string ("foobar")
        line = line.replace("\t", "foobar"); // Detect tab 
        line = line.replace("    ", "foobar"); // Detect four spaces
        if(buttonDetected==true){
            if(line.contains("foobar")){ // Current line falls in a loop
                if(!line.equals("foobar")){ // Ensures "blank" lines aren't manipulated
                    line = line.replace("foobar", ""); // Get rid of the random string
                    if (buttonA == true){ // Current line falls under a button A function
                        ISR0.add(line);
                    }
                    if(buttonB == true){ // Current line falls under a button B function
                        ISR1.add(line);
                    }
                }
            }else{
                buttonDetected = false; // Finished button loop
            }
        }
        return formatted;
    }
    /**
     * Auto-generated, not used
     * 
     * @param args the command line arguments
     */
    public static void main(String args[]) {
   
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUIPythonIn().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton SubmitDirectoryButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
