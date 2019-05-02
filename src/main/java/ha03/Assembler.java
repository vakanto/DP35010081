package ha03;

import java.io.*;
import java.util.HashMap;
import java.util.Stack;

public class Assembler {

    private Stack <Integer> valueStack;
    private File file;
    private HashMap <String, CommandHandler> handlers;
    private HashMap <String, Integer> variables;
    private static Assembler instance;

    Assembler(HashMap <String, CommandHandler> handler){

        //Init stack
        valueStack = new Stack<>();
        variables = new HashMap<String, Integer>();
        /**variables.put("x",0);
        variables.put("y",0);
        variables.put("z",0);**/
        this.handlers = handler;
        instance=this;

    }

    public void addHandler(String command, CommandHandler handler){
        System.out.println("Adding handler " + handler + "for " + command + " command.");
        handlers.put(command, handler);
    }

    public boolean assemble(File file){

        boolean success=false;

        //Check if file exists
        if(!file.exists())
        {
            System.out.println("Specified file doesnt exist.");
            return false;
        }
        //No read permissions
        if(!file.canRead()){
            System.out.print("Sorry ive got no read permissions on that file.");
            return false;
        }
        //Why do i have do check this anyway?
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedReader buff = new BufferedReader(new InputStreamReader(fileInputStream));

            String line = null;
            try {
                line = buff.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            while(line !=null){
                //extract first word from line
                //escaping from /s with \/s not possible?
                String [] lineSplit =line.split(" ");

                int size=lineSplit.length;
                String command = line.split(" ")[0];

                if(size>1){
                    String value = line.split(" ")[1];

                    if(value.matches("[a-z]")){
                        //value is variable
                        executeCommand(command, -1, value);
                    }
                    else {

                        int number = Integer.parseInt(line.split(" ")[1]);

                        executeCommand(command, number, "");
                    }
                }
                else {
                    //line contains only function name
                    success = executeCommand(command, -1, "");
                }

                try {
                    line = buff.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(success==true) {
                return success;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return  success;
    }

    public boolean executeCommand(String command, int number, String variable){

        boolean success=false;

        if(!handlers.containsKey(command)){
            System.out.println("Command not found.");
            return false;
        }

        CommandHandler handler = handlers.get(command);
        success=handler.command(command, number, variable);
        return success;
    }

    public static Assembler getInstance(){

        if(instance == null){
            instance = new Assembler(new HashMap<String, CommandHandler>());
        }
        return instance;
    }

    public Stack<Integer> getValueStack() {
        return valueStack;
    }

    public HashMap<String, Integer> getVariables(){
        return variables;
    }
}
