package ha10;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.fulib.yaml.EventSource;
import org.junit.Assert;
import org.junit.Test;
import java.io.*;
import java.util.function.Predicate;

import static java.lang.Thread.sleep;

public class test_ha10 {

    @Test
    public void testContainer() throws IOException, InterruptedException {
        Runtime runtime = Runtime.getRuntime();
        Process commandExecutor;
        BufferedReader stdIn;
        String dockerComposeUp =  "docker-compose -f src/main/java/ha10/docker-compose.yml up";
        String dockerComposeDown =  "docker-compose -f src/main/java/ha10/docker-compose.yml down";


        Thread composeUp = new Thread(){
            public void run(){
                CommandLine composeUp = CommandLine.parse(dockerComposeUp);
                DefaultExecutor executor = new DefaultExecutor();
                try {
                    executor.execute(composeUp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread composeDown = new Thread(){
            public void run(){
                CommandLine composeDown = CommandLine.parse(dockerComposeDown);
                DefaultExecutor executor = new DefaultExecutor();
                try {
                    executor.execute(composeDown);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        composeUp.start();

        //Wait until services have started
        sleep(20000);

        String dockerInspect = "docker container inspect shopserver";
        commandExecutor = runtime.exec(dockerInspect);
        stdIn = new BufferedReader(new InputStreamReader(commandExecutor.getInputStream()));
        Predicate<String> volumeMatch = e -> e.contains("shopVolume");
        Assert.assertTrue(stdIn.lines().anyMatch(volumeMatch));

        dockerInspect = "docker container inspect warehouseserver";
        commandExecutor = runtime.exec(dockerInspect);
        stdIn = new BufferedReader(new InputStreamReader(commandExecutor.getInputStream()));
        volumeMatch = e -> e.contains("warehouseVolume");
        Assert.assertTrue(stdIn.lines().anyMatch(volumeMatch));

        composeDown.start();

        //Wait until services have stopped
        sleep(2000);
    }
}
