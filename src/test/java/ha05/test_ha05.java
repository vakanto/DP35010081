package ha05;

import ha05.customer_client.Customer_Client;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class test_ha05 {


    @Test
    public void checkConfigFile(){
        File file = new File("src/main/resources/mosquitto.conf");
        Assert.assertTrue(file.exists());
    }

    @Test
    public void testServer(){
        try {
            Process p = Runtime.getRuntime().exec("mosquitto -c " + "src/main/resources/mosquitto.conf");
            Process q = Runtime.getRuntime().exec("mosquitto_pub -t toTransport -p 2000 -m test");
            Process r = Runtime.getRuntime().exec("mosquitto_sub -t toTransport -p 2000");

            BufferedReader input = new BufferedReader(new InputStreamReader(r.getInputStream()));
            String line = null;
            String output = null;
            while((line = input.readLine())!=null){
                output +=line;
            }

            System.out.println(output);
            Assert.assertEquals("test", output);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
