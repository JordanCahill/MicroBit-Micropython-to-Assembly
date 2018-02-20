
package pythontoassembly;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jordan Cahill
 * @date 06-Feb-2018
 */
public class PythonToAssembly {
    
    /**
     * R1 is reserved for the ISR; Can be used elsewhere but runs the risk of being overwritten
     * R2 is reserved for the LED display
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        ///////// Do not delete, used for user directory input after testing /////////
        /*System.out.println("Please enter file directory:");
        Scanner scanner = new Scanner( System.in );
        String directory = scanner.nextLine();
        System.out.println("Please enter the number of lines in the file (or more) to allocate space:");
        Scanner scanner = new Scanner( System.in );
        String size = scanner.nextInt();
        FileReader in = new FileReader(directory);
        */////////////////////////////////////////////////////////////////////////////
        
        // Read in the file
        FileReader in = new FileReader("C:/Users/Jorda/Documents/College/SampleFile.py");
        BufferedReader br = new BufferedReader(in);
        System.out.println("File found..");
     
        String[] assemText = new String[100]; // Text to be formatted and added to .asm file
        assemText[0] = "; File created using a python to assembly converter";
        int L=1; // Counter for the length of the assembly
        String line;
        System.out.println("Reading python file and converting..");
        String[] ISR = new String[30];
        
        while ((line = br.readLine()) != null) {
            
            // Display functions
            if(line.contains("microbit.display")){
                LED led = new LED(line);
                assemText[L]=led.getOutputLine();
                }
            
            // Sleep functionality
            if(line.contains("sleep")){
                Sleep sleep = new Sleep(line);
                String[] TmrVals = sleep.getOutputVals();
                assemText[L] = "CALL settingUpTimer";
                ISR = setUpTimerNoReload(TmrVals);
                
                 //assemText[L]=sleep.getOutputLine();
            }

            L++; // Increment size counter
            
        }
        assemText[L] = "END"; L++; // All programs must finish with "END"
        in.close(); // Close the buffer
        
        // Add setUpTimer functionality
        assemText[L] = ";"; L++;
        assemText[L] = ";"; L++;
        for (String s: ISR){
            assemText[L] = s;
            L++;
        }
        
        // Remove empty and null elements from the array
        String[] outputText = formatOutputText(assemText, L);

        
        CreateAsmFile(outputText); // Output final text to a .asm file
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
        
        int k =0;
        // Convert ";" to blank lines
        for (String outputList1 : outputList) {
            if (outputList1 != null) {
                if (outputList1.equals(";")) {
                    out[k] = "";
                }
            k++;
            }
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
        for (String s: outputText){
            System.out.println(s);
        }
        System.out.println("\nFile written to 'PythonToAssembly' folder.");
    }

    private static String[] setUpTimerNoReload(String[] TmrVals) {
        
        String[] setUpText = new String[30];      
        String left = TmrVals[0];
        String right = TmrVals[1];
         
        setUpText[0] = "settingUpTimer:";
        setUpText[1] = "XOR R1,R1,R1";
        
        int index = 2;
        
        try{
            for(int i = 0; i<16; i++){
                if ((Character.getNumericValue(left.charAt(i))) == 1){
                    setUpText[index] = "SETBR R1, " + i;
                    index++;
                }
            }
        }catch(StringIndexOutOfBoundsException e){
                    setUpText[index] = ""; index++;
        }
        
        setUpText[index] = "MOVRSFR SFR2, R1"; index++;
        setUpText[index] = "XOR R1,R1,R1"; index++;
        
        try{
            for(int i = 0; i<16; i++){
                if ((Character.getNumericValue(right.charAt(i))) == 1){
                    setUpText[index] = "SETBR R1, " + i;
                    index++;
                }
            }
        }catch(StringIndexOutOfBoundsException e){
                    setUpText[index] = ""; index++;
        }
        
        // Set up global flags
        setUpText[index] = "SETBSFR SFR0, 0 ; enable global interrupts"; index++;
        setUpText[index] = "SETBSFR SFR0, 3 ; enable timer interrupt"; index++;
        setUpText[index] = "SETBSFR SFR0, 6 ; down timer"; index++;
        setUpText[index] = "SETBSFR SFR0, 4 ; enable timer. Include as last bit set"; index++;
        setUpText[index] = "RET"; 
        
        return setUpText;
    }
}
