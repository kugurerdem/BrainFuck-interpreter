public class Token{
    
    public String value;
    public String type;
    
    /*
     * Constructor 1 for Token class
     * @param type - type of the token
     * @param value - value of the token
     */
    public Token(String type, String value){
        this.type = type;
        this.value = value;
    }
    
    /*
     * Constructor 2 for Token class
     * @param type - type of the token
     */
    public Token(String type){
        this.type = type;
        this.value = null;
    }
    
}