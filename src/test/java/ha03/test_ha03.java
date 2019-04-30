package ha03;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

public class test_ha03 {
    @Test
    public void test(){

        Assembler assembler = new Assembler();
        File file = new File("src/main/java/ha03/src/programm.asm");
        ArrayList<String> commands = new ArrayList<>();

        commands = assembler.readCommands(file);
        Assert.assertEquals(commands.get(0),"Ldc");
        Assert.assertEquals(commands.get(1),"Ldc");
        Assert.assertEquals(commands.get(2),"Mult");
        Assert.assertEquals(commands.get(3),"Store");
        Assert.assertEquals(commands.get(4),"Ld");
        Assert.assertEquals(commands.get(5),"Print");
    }
}
