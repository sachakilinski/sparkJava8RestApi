package contract;

import java8restapi.Main;
import org.junit.*;
import spark.Spark;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class RenameSiteContractTest {

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
    public void renameSite() {
        TestSimpleRequest request= new TestSimpleRequest("PUT","/sites/from?newName=newName");
        TestResponse res = request.send();
        assertEquals(200, res.status);
        assertEquals("site from sucessifully renamed to newName.", res.body);
    }

    @Test
    public void renamingToAlreadyExistingSite() {
        TestSimpleRequest request= new TestSimpleRequest("PUT","/sites/other?newName=to");
        TestResponse res = request.send();
        assertEquals(0, (res.status-400)/400);
        assertEquals(true, res.body.contains("could not be renamed"));
    }

    @Test
    public void siteDoesNotExist() {
        TestSimpleRequest request= new TestSimpleRequest("PUT","/sites/nowhere?newName=to");
        TestResponse res = request.send();
        assertEquals(0, (res.status-400)/400);
        assertEquals(true, res.body.contains("could not be renamed"));
    }
}