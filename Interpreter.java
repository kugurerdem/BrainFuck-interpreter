import java.util.*;

public class Interpreter{
    
    
    public String text;
    public int pos;
    public Token current_token;
    
    public final int CELLS_LENGTH;
    public int[] cells;
    public int cells_index;
    public List<Token> tokens = new ArrayList<Token>();
    
    /*
     * Constructor for Interpreter class
     * @param text - text that is going to be interpreted
     */
    
    public Interpreter(String text, int CELLS_LENGTH){
        
        this.text = text;
        this.pos = 0;
        this.current_token = null;
        
        this.CELLS_LENGTH = CELLS_LENGTH;
        this.cells = new int[ this.CELLS_LENGTH ];
        this.cells_index = 0;
        
        // Set every element of cells' value to 0
        for(int i = 0; i < this.CELLS_LENGTH; i++){
            this.cells[i] = 0;
        }
    }
    
    /* 
     * Function that raises an error
     */
    public Error error(){
        return new Error("Error parsing input");
    }
    
    /*
     * Function that returns the next token
     * @return - returns the next token
     */
    public Token getNextToken(){
        char current_char;
        
        // Return "END" Token if read all things
        if( this.pos > text.length() - 1 ){
            return new Token("END");
        }
        
        // Ignore all space characters
        do{
            current_char = text.charAt(this.pos);
            this.pos = this.pos+1;
        }while(current_char == ' ' && this.pos <= text.length() - 1);
         
        
        // If current_char is KEYWORD then return Token type as KEYWORD
        if(current_char == '>' || current_char == '<' || 
           current_char == '[' || current_char == ']' || 
           current_char == '+' || current_char == '-' ||
           current_char == '.'){
            return new Token("KEYWORD", "" + current_char);
        }
        
        // Throw error if there is an unrecognized character in text
        throw this.error();
        
        
    }
    
    /*
     * Function that splits tokens to a list
     */ 
    public void lexer(){
        int i = 0; //current indexOf tokens
        Token token = getNextToken();
        
        while( !token.type.equals("END") ){
            tokens.add(i, token);
            i++;
            
            token = getNextToken();
        }   
    }
    
    /*
     *  Function that runs the interpreter
     */ 
    public void run(){
        lexer();
        // System.out.println(tokens);
        
        for(int i = 0; i < tokens.size(); i++){
            Token token = tokens.get(i);
            // System.out.println(token.value + "  " + i);
            
            // if '+' or '-' increase or decrement the value of current cell
            if( token.value.equals("+") || token.value.equals("-") ){
                int addWith = token.value.equals("+")?1:-1;
                this.cells[this.cells_index] = this.cells[this.cells_index] + addWith;
            } 
            // if '>' or '<' move pointer to the left or right 
            else if( token.value.equals(">") || token.value.equals("<") ){
                int move;
                move = token.value.equals(">")?1:-1;
                if( cells_index + move < cells.length && cells_index + move >= 0 ){
                    cells_index = cells_index + move;
                } else{
                    throw this.error();
                }
            }
            // if '.' print out the current cell's ASCII value
            else if( token.value.equals(".") ){
                System.out.print( (char) cells[cells_index] );
            }
            // if '[' then: skip to the ']' if current cell's value is 0, otherwise do the next operation 
            else if( token.value.equals("[") ){
                if( cells[cells_index] == 0 ){
                    int count_in_iterator = 1; // count for nested '['s
                    
                    while( !token.value.equals("]") || count_in_iterator != 0){
                        
                        i++;
                        token = tokens.get(i);
                        if( token.value.equals("[") ){
                            count_in_iterator++;
                        } else if( token.value.equals("]") ){
                            count_in_iterator--;
                        }
 
                    }
                    // i--;
                } 
            } 
            
            // if ']' then: go back to the '[' if current cell's value is not 0, otherwise do the next operation
            else if ( token.value.equals("]") ){
                if( cells[cells_index] != 0 ){
                    int count_in_iterator = 1; // count for nested ']'s
                    while( !token.value.equals("[") || count_in_iterator != 0){
                        i--;
                        token = tokens.get(i);
                        if( token.value.equals("]") ){
                            count_in_iterator = count_in_iterator + 1;
                        } else if( token.value.equals("[") ){
                            count_in_iterator = count_in_iterator - 1;
                        }    
                    }
                    // i--;
                } 
            }
            
            // System.out.println("cell_index : " + this.cells_index + " , " + "value : " + this.cells[this.cells_index] );
        }
    }
    
    /*
     *  Function that shows the cells
     *  @return result - returns the cells in the interpreter
     */
    public String showCells(){
        String result;
        
        result = "";
        for(int i = 0; i < cells.length; i++){
            result = result + "[" + cells[i] + "]";
        }
        
        return result;
    }
}