package contract;

import java8restapi.Main;
import org.junit.*;
import spark.Spark;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CreateConnectionsContractTest {

    @Before
    public  void before() {
        String[] args = {"test"};
        Main.main(args);
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @After
    public  void after() {
        Spark.stop();
    }

    @Test
    public void createConnection() {
        TestSimpleRequest request= new TestSimpleRequest("POST","/sites/from/connections?othersite=other&distance=1.0");
        TestResponse res = request.send();
        assertEquals(200, res.status);
        assertEquals("connection from from to other created sucessifully.\n", res.body);
    }

    @Test
    public void siteDoesNotExist() {
        TestSimpleRequest request= new TestSimpleRequest("POST","/sites/nowhere/connections?othersite=other&distance=1.0");
        TestResponse res = request.send();
        assertEquals(0, (res.status-400)/400);
        assertEquals(true, res.body.contains("could not be created"));
    }

    @Test
    public void otherSiteDoesNotExist() {
        TestSimpleRequest request= new TestSimpleRequest("POST","/sites/from/connections?othersite=nowhere&distance=1.0");
        TestResponse res = request.send();
        assertEquals(0, (res.status-400)/400);
        assertEquals(true, res.body.contains("could not be created"));
    }

    @Test
    public void connectionAlreadyExists() {
        TestSimpleRequest request= new TestSimpleRequest("POST","/sites/from/connections?othersite=to&distance=1.0");
        TestResponse res = request.send();
        assertEquals(0, (res.status-400)/400);
        assertEquals(true, res.body.contains("could not be created"));
    }

    @Test
    public void negativeDistance() {
        TestSimpleRequest request= new TestSimpleRequest("POST","/sites/to/connections?othersite=other&distance=-1.0");
        TestResponse res = request.send();
        assertEquals(0, (res.status-400)/400);
        assertEquals(true, res.body.contains("could not be created"));
    }

    @Test
    public void selfConnection() {
        TestSimpleRequest request= new TestSimpleRequest("POST","/sites/to/connections?othersite=to&distance=1.0");
        TestResponse res = request.send();
        assertEquals(0, (res.status-400)/400);
        assertEquals(true, res.body.contains("could not be created"));
    }
}