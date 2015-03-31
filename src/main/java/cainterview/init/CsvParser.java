package cainterview.init;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CsvParser {

    private BufferedReader reader;
    private Function<String, List<String>> lineFunction;

    public CsvParser(BufferedReader reader, Function<String, List<String>> lineFunction) {
        this.reader = reader;
        this.lineFunction = lineFunction;
    }

    public CsvParser(BufferedReader reader) {
        this(reader, line -> Arrays.asList(line.split(",")));
    }


    public List<List<String>> parseAndCloseReader()  {
        List<List<String>> parsed =  reader.lines()
                .map(lineFunction)
                .collect(Collectors.toList());
        closeReader();
        return parsed;
    }

    private void closeReader(){
        try{
            reader.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}