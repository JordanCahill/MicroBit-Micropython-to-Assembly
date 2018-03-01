
package sccassemblyparser;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Jordan Cahill
 * @date 08-Feb-2018
 */
class Formatter {

    private String input;
    
    public Formatter(String line){
        input=line;
    }

    public Formatter() { // Blank constructor for method calls
        
    }

    String[] delimit() {
                
        String delim1 = "\\(";
        String delim2 = ",";
        String[] tokens = input.split(delim1);
        String input = tokens[1]; //(x,y,str)
        
        String[] variables = input.split(delim2);
        String[] delimitted = null;
        delimitted = variables;
        
        for(int i=0;i<delimitted.length;i++){
            delimitted[i] = delimitted[i].replace(")","");
        }
       
        return delimitted;
    }

    
    /**
     * Method used to remove nulls and empties from a String array and resize the array
     * 
     * @param outputList the list to be resized with nulls and empties removed
     * @param L the size of the allocated space
     * @return the formatted String array
     */
    String[] formatOutputText(String[] outputList, int L) {
        
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
     * @param aThis pass in the JFrame so it can be closed
     * @param outputText the String array to output
     * @throws FileNotFoundException 
     */
    void CreateAsmFile(JFrame aThis, String[] outputText) throws FileNotFoundException {
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
        System.out.println("Assembly File generated successfully.");
        
        String directory = getOutputDirectory() + "\\Output.asm";
        
        JOptionPane.showConfirmDialog(null,
                "Assembly file generated to " + directory,
                "Success!",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        
        // Close the program
        aThis.setVisible(false);
        System.exit(0);
    }
    
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