
package pythontoassembly;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Jordan Cahill
 * @date 06-Feb-2018
 */
public class PythonToAssembly {
    
    private static String[] ISRTimer = new String[30];
    private static ArrayList<String> ISR0 = new ArrayList<>();
    private static ArrayList<String> ISR1 = new ArrayList<>();
    private static boolean buttonDetected;
    private static boolean buttonA; 
    private static boolean buttonB;
    private static boolean ISRA;
    private static boolean ISRB;
    
    /**
     * ISR0 is reserved for the A button
     * ISR1 is reserved for the B button
     * R1 is reserved for the Timer ISR; Can be used elsewhere but runs the risk of being overwritten
     * R0 is reserved for the LED display
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
        
        String directory = null;
        GUI gui = new GUI();
        while (directory==null){
            directory = gui.getDirectory();
            TimeUnit.SECONDS.sleep(1);
        }
        
        // Read in the file
        //FileReader in = new FileReader("C:/Users/Jorda/Documents/College/SampleFile.py");
        FileReader in = new FileReader(directory);
        BufferedReader br = new BufferedReader(in);
        System.out.println("File found..");
     
        String[] assemText = new String[100]; // Text to be formatted and added to .asm file
        assemText[0] = "; File created using a python to assembly converter";
        int L=1; // Counter for the length of the assembly
        String line;
        System.out.println("Reading python file and converting..");
        //String[] ISR = new String[30];
        
        boolean buttonDetected = false; // Boolean to keep track of button presses
        boolean enableInts = false; // Boolean to check if interrupts should be enabled

        while ((line = br.readLine()) != null) { // Loop through the text file       
            assemText[L] = Format(line);
            L++; // Increment size counter      
        }
        
        assemText[L] = "END"; L++; // All programs must finish with "END"
        in.close(); // Close the buffer
             
        // Add setUpTimer functionality
        assemText[L] = ";"; L++;
        for (String s: ISRTimer){
            assemText[L] = s;
            L++;
        }
        // Enable Interrupts
        assemText[L] = ";"; L++;
        assemText[L] = "enableInterrupts:"; L++;
        assemText[L] = "SETBSFR SFR0, 0"; L++;
        assemText[L] = "SETBSFR SFR0, 1"; L++;
        assemText[L] = "SETBSFR SFR0, 2"; L++;
        assemText[L] = "RET"; L++;
        
        // Add button functionality
        if (ISRA == true){
            assemText[L] = ";"; L++;
            assemText[L] = "ISR0:   ORG 92"; L++;
            for (String s: ISR0){
                String f = Format(s);
                if(s != null){
                    assemText[L] = f;
                    L++;
                }
            }
            assemText[L] = "RETI"; L++;
        }
        if (ISRB == true){
            assemText[L] = ";"; L++;
            assemText[L] = "ISR1:   ORG 104"; L++;
            for (String s: ISR1){
                String f = Format(s);
                if(s != null){
                    assemText[L] = f;
                    L++;
                }
            }
            assemText[L] = "RETI"; L++;
        }
      
        // Remove empty and null elements from the array
        String[] outputText = formatOutputText(assemText, L);
        
        CreateAsmFile(outputText); // Output final text to a .asm file
        
        if (gui.isFinished()){ // Finish program and display confirmation
            
            JOptionPane.showConfirmDialog(null,
                "Assembly file created in root package folder",
                "Conversion Successful",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE);
            
            System.exit(0); // Finish the program
        }
    }

    /**
     * Method used to remove nulls and empties from a String array and resize the array
     * 
     * @param outputList the list to be resized with nulls and empties removed
     * @param L the size of the allocated space
     * @return the formatted String array
     */
    private static String[] formatOutputText(String[] outputList, int L) {
        
        String[] out = new String[L];
        int j = 0;
        
        // Remove blank spaces
        for(int i = 0; i<outputList.length; i++) {
            if(outputList[i]!=null){
                if(!outputList[i].equals("")){
                    out[j] = outputList[i];
                    j++;
                }
            }
        }
        // Convert ";" to blank lines
        int k = 0;
        for(String s: out){
            if (s!= null && s.equals(";")){
                out[k] = "";
            }
            k++;
        }  
        // Remove nulls
        int count = 0;
        for (String s: out){
            if(s != null){
                count++;
            }
        }
        // Move to a new resized array
        String[] finalOut = new String[count];
        for (int i=0;i<count;i++){ // Assign to finalOut
            finalOut[i] = out[i];
        }
        
        return finalOut;
    }

    /**
     * Method used to output the formatted text to .asm file located in the src folder
     * 
     * @param outputText the String array to output
     * @throws FileNotFoundException 
     */
    private static void CreateAsmFile(String[] outputText) throws FileNotFoundException {
        PrintWriter out = new PrintWriter("output.asm");
        for (String s: outputText){
            out.println(s);
        }
        out.close();
        System.out.println("\nFinal text output:");
        System.out.println("____________________________________________\n");
        for (String s: outputText){
            System.out.println(s);
        }
        System.out.println("\n____________________________________________\n");
        System.out.println("File written to 'PythonToAssembly' folder.");
    }

    private static String[] setUpTimerNoReload(String[] TmrVals) {
        
        String[] setUpText = new String[30];      
        String left = TmrVals[0];
        String right = TmrVals[1];
         
        setUpText[0] = "settingUpTimer:";
        setUpText[1] = "XOR R1,R1,R1";
        
        int index = 2;
        
        int j = left.length()-1; // Index used to set bit
        for(int i = 0; i<left.length(); i++){
            if ((Character.getNumericValue(left.charAt(i))) == 1){
                setUpText[index] = "SETBR R1, " + j; index++;
            }
            j--;
        }

        setUpText[index] = "MOVRSFR SFR1, R1"; index++;
        setUpText[index] = "XOR R1,R1,R1"; index++;

        j = right.length()-1;
        for(int i = 0; i<right.length(); i++){
            if ((Character.getNumericValue(right.charAt(i))) == 1){
                setUpText[index] = "SETBR R1, " + j; index++;
            }
            j--;
        }

        // Set up global flags
        setUpText[index] = "SETBSFR SFR0, 0 ; enable global interrupts"; index++;
        setUpText[index] = "SETBSFR SFR0, 3 ; enable timer interrupt"; index++;
        setUpText[index] = "SETBSFR SFR0, 6 ; down timer"; index++;
        setUpText[index] = "SETBSFR SFR0, 4 ; enable timer. Include as last bit set"; index++;
        setUpText[index] = "RET"; 
        
        return setUpText;
    }

    private static String Format(String line) {
        String formatted = "";
     
        // Sleep functionality
        if(line.contains("sleep") && buttonDetected == false){
            Sleep sleep = new Sleep(line);
            String[] TmrVals = sleep.getOutputVals();
            formatted = "CALL settingUpTimer";
            ISRTimer = setUpTimerNoReload(TmrVals);
        }
        // Display functions
        if(line.contains("microbit.display") && buttonDetected == false){
            LED led = new LED(line);
            formatted = led.getOutputLine();
        }
        
        
        // Button functions
        if((line.contains("button")) && (line.contains("is_pressed"))){
            if(line.contains("button_a")){
                buttonA = true; buttonB = false;
                ISRA = true;
            }else if(line.contains("button_b")){
                buttonB = true; buttonA = false;
                ISRB = true;
            }
            buttonDetected = true;
            if(ISRA ^ ISRB){formatted = "CALL enableInterrupts";}
            line = "\t"; // So the line is not added to loop array
        } // Count number of lines following the function call
        line = line.replace("\t", "foobar");
        if(buttonDetected==true){
            if(line.contains("foobar")){
                if(!line.equals("foobar")){
                    line = line.replace("foobar", "");
                    if (buttonA == true){;
                        ISR0.add(line);
                    }
                    if(buttonB == true){
                        ISR1.add(line);
                    }
                }
            }else{ // Reset loop variables
                buttonDetected = false; // Finished button loop
            }

            // NOTE: WILL NEED TO ADD buttonDetected check to every other loop
        }

        return formatted;
    }
}
