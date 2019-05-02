package ha03;

import java.util.Stack;

public class LoadHandler implements CommandHandler {
    @Override
    public boolean command(String command, int number, String variable){

        Assembler assembler = Assembler.getInstance();
        Stack <Integer> valueStack = assembler.getValueStack();

        if(valueStack==null){
            System.out.println("One or more parameters are empty or contain a null value.");
            return false;
        }

        System.out.println("LoadHandler loads " + number);
        valueStack.push(number);

        return true;
    }
}
