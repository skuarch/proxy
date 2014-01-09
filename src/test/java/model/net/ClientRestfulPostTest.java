package model.net;

import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author skuarch
 */
public class ClientRestfulPostTest {

    public ClientRestfulPostTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of send method, of class ClientRestfulPost.
     */
    @Test
    public void testSend() throws Exception {

        /*JSONObject json = new JSONObject("{\"request\":\"getConfiguration\", \"timeout\":\"1000\", \"hostDestination\":\"192.168.208.15\",\"portDestination\":\"8084\",\"expectedReturn\":true}");
        json.accumulate("request", "connectivity");

        //ClientRestfulPost instance = new ClientRestfulPost("http://192.168.208.9:8080/sam5/webservices/restful/v1/notifications");
        ClientRestfulPost instance = new ClientRestfulPost("http://192.168.208.9:8080/proxy/restful/v1/socketRemoteConnection");

        System.out.println("send");
        instance.send(json);

        System.out.println("receive");
        System.out.println(instance.receive());

        System.out.println("close");
        instance.closeClient();*/

    }

}
