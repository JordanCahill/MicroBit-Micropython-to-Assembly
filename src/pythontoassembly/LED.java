
package pythontoassembly;

/**
 * Constructor used to call methods that can convert micropython LED commands for an SCC
 * Assumes the SCC is using R2 as a 4x4 16-bit LED array in the following format:
 * 
 * 12 | 13 | 14 | 15
 * 8  | 9  | 10 | 11 
 * 4  | 5  | 6  | 7
 * 0  | 1  | 2  | 3
 * 
 * where bit 0 corresponds to coordinates (0,0) and bit 12 corresponds to (0,4) etc. 
 * 
 * @author Jordan Cahill
 * @date 07-Feb-2018
 */
public final class LED {
    
    private final String inputLine;
    private String outputLine;
    
    public LED(String in){
        this.inputLine = in;
        
        if (inputLine.contains("set_pixel")){
            setPixel(inputLine);
        }else if (inputLine.contains("clear")){
            clearPixels(inputLine);
        }
    }
    
    /**
     * Function used to switch a pixel on or off
     * NOTE: Assumes the LED is being inverted
     * 
     * @param line input line need for coordinates
     * @return output command
     */
    String setPixel(String line) {
  
        String command = "; Unknown problem converting set_pixel() function"; // Default assignment
        String[] coordinates = delimit(line); // Calls the delimit function to retrieve the (x,y) LED coordinate
        
        String x = coordinates[0];
        String y = coordinates[1];
        
        // Coordinates must be between (0,0) and (3,3)
        if (Integer.parseInt(x)>3 || Integer.parseInt(y)>3){
            command = "; Coordinates of set_pixel() too high";
        }
        
        // Must check 4 x-values for each y-value and return an appropriate command
        switch (y){
            case "0":
                switch (x){
                    case "0":
                        outputLine = "INVBR R2, 0";
                        break;
                    case "1":
                        outputLine = "INVBR R2, 1";
                        break;
                    case "2":
                        outputLine = "INVBR R2, 2";
                        break;
                    case "3":
                        outputLine = "INVBR R2, 3";
                        break;
                } break; 
            case "1":
                switch (x){
                    case "0":
                        outputLine = "INVBR R2, 4";
                        break;
                    case "1":
                        outputLine = "INVBR R2, 5";
                        break;
                    case "2":
                        outputLine = "INVBR R2, 6";
                        break;
                    case "3":
                        outputLine = "INVBR R2, 7";
                        break;
                } break;
            case "2":
                switch (x){
                    case "0":
                        outputLine = "INVBR R2, 8";
                        break;
                    case "1":
                        outputLine = "INVBR R2, 9";
                        break;
                    case "2":
                        outputLine = "INVBR R2, 10";
                        break;
                    case "3":
                        outputLine = "INVBR R2, 11";
                        break;
                } break;
            case "3":
                switch (x){
                    case "0":
                        outputLine = "INVBR R2, 12";
                        break;
                    case "1":
                        outputLine = "INVBR R2, 13";
                        break;
                    case "2":
                        outputLine = "INVBR R2, 14";
                        break;
                    case "3":
                        outputLine = "INVBR R2, 15";
                        break;
                } break;
        }   
        return command;
    }

    /**
     * Delimits a python command into its input variables 
     * 
     * @param line input command to be delimited
     * @return String array of variables
     */
    private String[] delimit(String line) {
        String delim1 = "\\(";
        String delim2 = ",";
        String[] tokens = line.split(delim1);
        String input = tokens[1]; //(x,y,str)
        
        String[] variables = input.split(delim2);
        String[] coordinates = new String[2];
        coordinates = variables;
        
        return coordinates;
    }

    String getOutputLine() {
        return outputLine;
    }

    // Clears the LED display by "XORing" the register with itself
    private void clearPixels(String inputLine) {
        outputLine = "XOR R2, R2, R2 ; Clear R2";
    }
}
