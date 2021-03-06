package java8restapi.init;


import java.io.BufferedReader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BufferedReaderFactory {

    public BufferedReader fromFile(String path){
        try {
            return Files.newBufferedReader(createPath(path));
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public BufferedReader fromRawString(String raw){
        return new BufferedReader(new StringReader(raw));
    }

    private Path createPath(String path) {
        String fullPath = getClass().getResource(path).getFile();

        try{
            return Paths.get(fullPath); //unix
        }catch (Exception e)
        {
            return Paths.get(fullPath.substring(1)); //windows
        }
    }
}
