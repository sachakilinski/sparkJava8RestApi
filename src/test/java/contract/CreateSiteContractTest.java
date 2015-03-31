package contract;

import java8restapi.Main;
import org.junit.*;
import spark.Spark;


import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CreateSiteContractTest {

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
    public void createSite() {
        TestSimpleRequest request= new TestSimpleRequest("POST","/sites?name=teste");
        TestResponse res = request.send();
        assertEquals(200, res.status);
        assertEquals("site teste created sucessifully.\n", res.body);
    }

    @Test
    public void createAlreadyExistingSite() {
        TestSimpleRequest request= new TestSimpleRequest("POST","/sites?name=from");
        TestResponse res = request.send();
        assertEquals(0, (res.status-400)/400);
        assertEquals(true, res.body.contains("site from could not"));
    }
}