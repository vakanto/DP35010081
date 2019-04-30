package ha03;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Assembler {

    private Stack <Integer> variableStack;
    private File file;
    private Map <String, CommandHandler> handlers;

    Assembler(Map <String, CommandHandler> handler){

        //Init stack
        variableStack = new Stack<>();
        this.handlers = handler;

    }

    public boolean executeCommands(ArrayList<String> commands){
        boolean success=false;
        for(String command : commands){
            CommandHandler handler = handlers.get("command");
            success=handler.command(command, variableStack);
        }
        return success;
    }

    public ArrayList<String> readCommands(File file){

        ArrayList <String> commands = new ArrayList<>();

        //Check if file exists
        if(!file.exists())
        {
            System.out.println("Specified file doesnt exist.");
        }
        //No read permissions
        if(!file.canRead()){
            System.out.print("Sorry ive got no read permissions on that file.");
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
                String command = line.split(" ")[0];
                String number = line.split(" ")[1];

                executeCommand()

                System.out.println(command);

                try {
                    line = buff.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return commands;
    }
}
