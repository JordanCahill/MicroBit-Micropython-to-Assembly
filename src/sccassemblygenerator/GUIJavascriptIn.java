package sccassemblygenerator;

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
 * @author Jordan
 */
public class GUIJavascriptIn extends javax.swing.JFrame {

    /* Large ammount of global variabales are necessary due to the dynamic nature of the program's input */
    
    private static boolean dontFormat = false;
    
    // 'Button' Global variables
    private static ArrayList<String> ISRTimerSetUp = new ArrayList<>();
    private static ArrayList<String> ISR0 = new ArrayList<>();
    private static ArrayList<String> ISR1 = new ArrayList<>();
    private static boolean interruptsNeeded; // Global flag to add enableInterrupts() to final output
    private static boolean buttonA; // If current button loop is button A
    private static boolean buttonB; // If current button loop is button B
    private static boolean ISRA; // Flag to add ISR code to ISR0
    private static boolean ISRB; // Flag to add ISR code to ISR1
    
    // 'Function' Global variables
    private static String[][] functions = new String[1000][10000];
    private static int fi = 0; private static int fj = 0; 
    private static String[] functionNames = new String[1000];
    
    /**
     * Creates new form GUIJavaScriptIn
     */
    public GUIJavascriptIn() {
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
        jLabel1.setText("Please select the Javascript file you wish to convert..");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton1))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(125, 125, 125)
                        .addComponent(SubmitDirectoryButton, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addComponent(SubmitDirectoryButton)
                .addContainerGap(27, Short.MAX_VALUE))
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
     * On button press, this method allows the user to search for a file with the file extension ".js" and
     * saves the directory of the file as a String, the String is then saved to the text field to be retrieved
     * later
     * @param evt Button press
     */
    private void GetDirectoryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GetDirectoryButtonActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JavaScript Files (*.js)", "js");
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
        try {in = new FileReader(directory);} catch (FileNotFoundException ex) {}
        BufferedReader br = new BufferedReader(in);
        System.out.println("File found..");
     
        
        long millis=System.currentTimeMillis();  
        java.sql.Date date=new java.sql.Date(millis);
        
        // TODO: Add dynamic length for the string
        String[] assemText = new String[10000]; // Text to be formatted and added to .asm file
        assemText[0] = "; File created using the SCC Assembly Generator";
        assemText[1] = "; Original language: JavaScript";
        assemText[2] = "; Date: " + date;
        int L=3; // Counter for index of the output commands
        String line;
        System.out.println("Reading python file and converting..");

        try { // Read each line and format individually
            while ((line = br.readLine()) != null) { // Loop until no text is left
                assemText[L] = Parse(line); // Return and store assembly command
                L++; // Increment size counter                    
            }
        } catch (IOException ex) {/*Exception Handling*/}
        assemText[L] = "END"; L++; // All programs must finish with "END"
        
        
        try {in.close();} catch (IOException ex) {} // Close the buffer             
        
        dontFormat = false; // Temporarily disable to allow formatting 
        /* If button A or button B was pressed, flags ISRA or ISRB will be asserted and
           the ISR1 or ISR0 arrays will contain commands to be added to the respective
           interrupt service routines
        */
        if (ISRB == true){ // Button B detected
            assemText[L] = ";"; L++;
            assemText[L] = "ISR0:   ORG 92"; L++;
            for (String s: ISR0){ // Format each string in the array
                String f = Parse(s);
                if(s.contains("change(LedSpriteProperty.X, 1)")){
                    assemText[L] = "ROTR R0, 1 ; Shift Sprite right"; L++;
                }
                if(s != null){
                    assemText[L] = f; L++; // Add to master array and increment count
                }
            }
            assemText[L] = "RETI"; L++; // ISR must end with "RETI"
        }
        if (ISRA == true){ // Button A detected
            assemText[L] = ";"; L++;
            assemText[L] = "ISR1:   ORG 104"; L++;
            for (String s: ISR1){ // Format each string in the array
                String f = Parse(s);
                if(s.contains("change(LedSpriteProperty.Y, 1)")){
                    assemText[L] = "ROTR R0, 4 ; Shift Sprite down"; L++;
                }
                if(s != null){
                    assemText[L] = f; L++; // Add to master array and increment count
                }
            }
            assemText[L] = "RETI"; L++; // ISR must end with "RETI"
        }
        
        /*
        // Add each function by iterating through them and adding line by line
        assemText[L] = ";"; L++;
        for(int i=0;i<functions.length;i++){
            assemText[L] = functionNames[i]; L++;
            for(int j=0;j<functions[i].length;j++){
                if(functions[i][j] != null && !functions[i][j].isEmpty()){
                    System.out.print("before: " + functions[i][j] + ", after: ");
                    String f = Parse(functions[i][j]);
                    System.out.println(f);
                    assemText[L] = f; L++; // Add to master array and increment count                        
                }
            }
        }*/
        
        dontFormat = true; // Re-enable
        
        // Sets up the timer for a user-defined length of time
        assemText[L] = ";"; L++;
        for (String s: ISRTimerSetUp){
            assemText[L] = s; L++;
        }
        // Add enableInterrupts() to asm if flag set to true
        if (interruptsNeeded){
            assemText[L] = ";"; L++;
            assemText[L] = "enableInterrupts:"; L++;
            assemText[L] = "SETBSFR SFR0, 0 ; Enable Global Interrupts"; L++;
            assemText[L] = "SETBSFR SFR0, 1 ; Enable ISR0"; L++;
            assemText[L] = "SETBSFR SFR0, 2 ; Enable ISR1"; L++;
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
     * Parses a JavaScript command to an appropriate assembly command(s)
     * 
     * @param JavaScript command
     * @return Assembly command
     */
    private static String Parse(String line) {
        
        String formatted = ""; // Assembly command to be returned
  
        // Pause command functionality
        if(line.contains("pause") && dontFormat == false){
            Sleep sleep = new Sleep(line,false); // Sleep object to invoke methods
            String[] TmrVals = sleep.getOutputVals(); // Get the values to pass into TMRL and TMRH registers
            formatted = "CALL settingUpTimer"; // Calls the method to set up the timer
            Timer setupNoReload = new Timer(); // Timer object to invoke methods
            ISRTimerSetUp = setupNoReload.setUpTimerNoReload(TmrVals);
        }
        // Convert LED plot/unplot functions
        if((line.contains("plot")||line.contains("clear")||line.contains("createSprite")) && dontFormat == false){
            LED led = new LED(line); // LED object to invoke methods
            formatted = led.getOutputLine(); // Get output ASM instruction
        }        
        // Convert loops after "onButtonPressed" calls
        // This if loop determines which button (A or B) press loop the current line falls under, if any
        if(line.contains("onButtonPressed")){
            if(line.contains("Button.A")){ // Button A detected
                buttonA = true; buttonB = false; // Set current button to A
                ISRA = true; 
            }else if(line.contains("Button.B")){ // Button B detected
                buttonB = true; buttonA = false; // Set current button to B
                ISRB = true;
            }
            dontFormat = true;
            if(ISRA ^ ISRB){ // Enable interrupts if A or B detected
                interruptsNeeded = true;
                formatted = "CALL enableInterrupts ; Button press detected, interrupts required";
            }
            line = "\t"; // Ensures the button press line is not added to loop array
        }
        // If the line falls under a button press, add it to an appropriate array
        if(dontFormat==true){
            if(!line.contains("})")){
                if (buttonA == true){ // Current line falls under a button A function
                    ISR1.add(line);
                }
                if(buttonB == true){ // Current line falls under a button B function
                    ISR0.add(line);
                }
            }else{ // Reset loop variables
                dontFormat = false; // Finished current button loop
            }
        }
        /*if(line.contains("function ")){
            dontFormat = true;
            String funcName = line.replace("function ", ""); // Remove the "function" string
            funcName = funcName.replace("{", "");
            funcName = funcName + ":";
            functionNames[fi] = funcName;
            line = "\t";
        }
        if(dontFormat == true){
            if(!line.contains("}") || !line.equals("Stop")){
                functions[fi][fj] = line;
                fj++; // Move to next line
            }else{
                dontFormat = false;
                fi++; // Move to next function column
                fj = 0; // Current index reset
            }
        }*/
        
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
                new GUIJavascriptIn().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton SubmitDirectoryButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
