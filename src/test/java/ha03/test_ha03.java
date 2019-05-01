package ha03;

import ha03.src.LoadValueHandler;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class test_ha03 {
    @Test
    public void test(){


        /**
         * Test 1
         * Check Init
         * **/
        Assembler as = new Assembler(null);
        boolean succ = as.assemble(new File(""));
        Assert.assertEquals(false, succ);


        /**
         * Test 2
         * Check Exercise
         * **/
        Assembler assembler;
        File file = new File("src/main/java/ha03/src/programm.asm");
        ArrayList<String> commands = new ArrayList<>();
        HashMap<String, CommandHandler> handlers = new HashMap<>();
        handlers.put("Ldc", new LoadHandler());
        handlers.put("Ld", new LoadValueHandler());
        handlers.put("Mult", new MultHandler());
        handlers.put("Store", new StoreHandler());
        handlers.put("Print", new PrintHandler());
        assembler= new Assembler(handlers);

        boolean success = assembler.assemble(file);

        Assert.assertEquals(true, success);
    }
}
