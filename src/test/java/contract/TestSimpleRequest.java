package contract;

import spark.utils.IOUtils;

import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.Assert.fail;

public class TestSimpleRequest {
    private String host;
    private String method;
    private String path;

    TestSimpleRequest(String host, String method, String path){
        this.host = host;
        this.method = method;
        this.path = path;
    }

    TestSimpleRequest(String method, String path){
        this("http://0.0.0.0:4567",method,path);
    }

    public TestResponse send() {
        try{
            URL url = new URL(host + path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 ( compatible ) ");
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("content-type","www-urlencoded");
            connection.setDoOutput(true);
            connection.connect();
            String body;
            try{
                body = IOUtils.toString(connection.getInputStream());
                return new TestResponse(connection.getResponseCode(), body);
            }catch(Exception e){
                body =  IOUtils.toString(connection.getErrorStream());
                return new TestResponse(connection.getResponseCode(), body);
            }
        }catch(Exception e){
            fail(e.getMessage());
            return null;
        }
    }
}