package contract;

import cainterview.Main;
import org.junit.*;
import spark.Spark;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class DeleteSiteContractTest {

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
    public void deleteSite() {
        TestSimpleRequest request= new TestSimpleRequest("DELETE","/sites/other");
        TestResponse  res = request.send();
        assertEquals(200, res.status);
        assertEquals(true, res.body.contains("site other deleted sucessifully."));
    }

    @Test
    public void siteDoesNotExist() {
        TestSimpleRequest request= new TestSimpleRequest("DELETE","/sites/nowhere");
        TestResponse res = request.send();
        assertEquals(0, (res.status-400)/400);
        assertEquals(true, res.body.contains("could not be deleted"));
    }
}