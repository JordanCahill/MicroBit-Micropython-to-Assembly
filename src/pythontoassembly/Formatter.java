
package pythontoassembly;

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
    
    
}
