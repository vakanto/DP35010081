package ha03;

import java.util.HashMap;
import java.util.Stack;

public class StoreHandler implements CommandHandler {

    @Override
    public boolean command(String command, int number, String variable){

        try {
            Assembler assembler = Assembler.getInstance();
            Stack valueStack = assembler.getValueStack();
            HashMap<String, Integer> variables = assembler.getVariables();

            variables.replace("x", (Integer) valueStack.pop());

            return true;
        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }
}
