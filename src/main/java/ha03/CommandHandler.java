package ha03;

import java.util.Stack;

public interface CommandHandler {
    public boolean command(String command, Stack variableStack);
}
