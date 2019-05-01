package ha03;

import ha03.Assembler;
import ha03.CommandHandler;

import java.util.HashMap;
import java.util.Stack;

public class LoadValueHandler implements CommandHandler {
    @Override
    public boolean command(String command, int number, String variable) {

        Assembler assembler = Assembler.getInstance();
        HashMap<String, Integer> variables = assembler.getVariables();
        Stack<Integer> valueStack = assembler.getValueStack();

        valueStack.push(variables.get(variable));

        return false;
    }
}
