
package sccassemblygenerator;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * This class contains methods used to manipulate the master array
 *
 * @author Jordan Cahill
 * @date 08-Feb-2018
 */
class Formatter {

    private String input;
   
    // Overloaded contructors
    public Formatter(String line){
        input=line;
    }
    public Formatter() { // Blank constructor for method calls
        
    }

    /**
     * Used to delimit an input command into its parameters
     * 
     * @return Array of parameters
     */
    String[] delimit() {
                
        String delim1 = "\\("; // Commands contain parameters within brackets
        String delim2 = ","; // Parameters will be separated by commas
        String[] tokens = input.split(delim1); // Separate the parameters from the method
        String params = tokens[1]; //(x,y,z) => parameters
        String[] variables = params.split(delim2); // Separate the params from eachother
        variables[variables.length-1] = variables[variables.length-1].replace(")",""); // Remove the closing bracket from the last parameter
       
        return variables;
    }

    
    /**
     * Method used to remove nulls and empties from a String array and resize the array
     * 
     * @param The String array to be formatted
     * @param L the size of the allocated space
     * @return The formatted String array
     */
    String[] formatOutputText(String[] outputList, int L) {
        
        String[] out = new String[L];
        int j = 0;
        
        // Remove blank spaces
        for (String s : outputList) {
            if (s != null) {
                if (!s.equals("")) {
                    out[j] = s;
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
        System.arraycopy(out, 0, finalOut, 0, count); // Assign to finalOut
        
        return finalOut; // Return the formatted array
    }

    /**
     * Method used to output the formatted text to .asm file in a user-defined directory
     * 
     * @param Pass in the JFrame so it can be closed
     * @param The String array to output
     * @throws FileNotFoundException 
     */
    void CreateAsmFile(JFrame aThis, String[] outputText) throws FileNotFoundException {
        
        // Opens a dialog to allow the user to choose an output directory
        String directory = getOutputDirectory() + "\\Output.asm"; 
        try (PrintWriter out = new PrintWriter(directory)) {
            for (String s: outputText){
                out.println(s); // Write the array to the output file
            }
        }
        // Print the final output to the console
        System.out.println("\nFinal text output:");
        System.out.println("____________________________________________\n");
        for (String s: outputText){
            System.out.println(s);
        }
        System.out.println("\n____________________________________________\n");
        System.out.println("Assembly File generated successfully.");
        
        // Open a dialog box to confirm success
        JOptionPane.showConfirmDialog(null,
                "Assembly file generated to " + directory,
                "Success!",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        
        // Close the program
        aThis.setVisible(false);
        System.exit(0);
    }
    
    /**
     * Opens a dialog box allowing the user to choose an output directory
     * 
     * @return The user defined directory 
     */
    public String getOutputDirectory(){
        JFileChooser chooser = new JFileChooser(); 
        String choosertitle = "Please choose where to save file..";
        chooser.setCurrentDirectory(new java.io.File("./.."));
        chooser.setDialogTitle(choosertitle);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        
        String directory = null;
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            return directory = chooser.getSelectedFile().toString();
        }else{
            return null;
        }
    }
}
