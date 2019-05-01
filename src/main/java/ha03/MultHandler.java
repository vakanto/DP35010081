package ha03;

import java.util.Stack;

public class MultHandler implements CommandHandler {

    @Override
    public boolean command(String command, int number, String variable) {

        Assembler assembler = Assembler.getInstance();
        Stack valueStack = assembler.getValueStack();
        valueStack.push(multiplication((Integer)valueStack.pop(), (Integer)valueStack.pop()));

        return true;
    }

    private int multiplication(Integer a, Integer b){
        return a*b;
    }
}
