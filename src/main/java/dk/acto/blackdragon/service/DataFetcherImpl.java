package dk.acto.blackdragon.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class DataFetcherImpl implements DataFetcher {
    @Override
    public String fetchData(URL url) {
        try (InputStream input = url.openStream()) { 
            InputStreamReader streamReader = new InputStreamReader(input);
            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder data = new StringBuilder();

            int c;
            while ((c = reader.read()) != -1) {
                data.append((char) c);
            }
            return data.toString();
        } 
        catch (IOException e) {
            System.err.println("Couldn't fetch data: " + e.getMessage());
            return ""; 
        }
    } 
}
