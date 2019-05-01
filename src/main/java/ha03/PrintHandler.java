package ha03;

import java.util.Stack;

public class PrintHandler implements CommandHandler {
    @Override
    public boolean command(String command, int number, String variable) {

        Assembler assembler = Assembler.getInstance();
        Stack valueStack = assembler.getValueStack();

        if(valueStack.empty()){
            System.out.println("Stack contains no values.");
            return false;
        }

        System.out.println(valueStack.pop());
        return true;
    }
}
