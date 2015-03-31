package contract;

import cainterview.Main;
import org.junit.*;
import spark.Spark;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class UpdateConnectionsContractTest {

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
    public void updateConnection() {
        TestSimpleRequest request= new TestSimpleRequest("PUT","/sites/from/connections/to?distance=2.0");
        TestResponse res = request.send();
        assertEquals(200, res.status);
        assertEquals("connection from from to to updated sucessifully.", res.body);
    }

    @Test
    public void siteDoesNotExist() {
        TestSimpleRequest request= new TestSimpleRequest("PUT","/sites/nowhere/connections/other?distance=1.0");
        TestResponse res = request.send();
        assertEquals(0, (res.status-400)/400);
        assertEquals(true, res.body.contains("could not be updated"));
    }

    @Test
    public void otherSiteDoesNotExist() {
        TestSimpleRequest request= new TestSimpleRequest("PUT","/sites/from/connections/nowhere?distance=1.0");
        TestResponse res = request.send();
        assertEquals(0, (res.status-400)/400);
        assertEquals(true, res.body.contains("could not be updated"));
    }

    @Test
    public void connectionDoesNotExist() {
        TestSimpleRequest request= new TestSimpleRequest("PUT","/sites/from/connections/other?distance=1.0");
        TestResponse res = request.send();
        assertEquals(0, (res.status-400)/400);
        assertEquals(true, res.body.contains("could not be updated"));
    }

    @Test
    public void negativeDistance() {
        TestSimpleRequest request= new TestSimpleRequest("PUT","/sites/from/connections/to?distance=-1.0");
        TestResponse res = request.send();
        assertEquals(0, (res.status-400)/400);
        assertEquals(true, res.body.contains("could not be updated"));
    }
}