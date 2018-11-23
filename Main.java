import java.util.*;
import java.io.*;
import java.nio.*;

/*
 * Building interpreter for BrainFuck
 * Project start date: 22.11.2018
 * @author Uður Erdem Seyfi
 * @version 23.11.2018
 */

public class Main{
    
    public static void main(String[] args){
        
        String text = "+++++ +++++ [>+++++ ++ >+++++ ++ << -] >++. >+++.";

        System.out.println(text);
        
        Interpreter interpreter = new Interpreter(text, 10);
        interpreter.run();
        System.out.print( "\n" + interpreter.showCells() );
    } 
}