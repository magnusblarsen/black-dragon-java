package dk.acto.blackdragon.service;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.net.URL;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;


public class DataFetcherTest {

    @Test(groups = "fetch")
    public void testFetchData(ITestContext context) throws Exception {

        DataFetcher subject = new DataFetcherImpl();

        String result = subject.fetchData(new URL("https://dragon.acto.dk/test.csv"));
        assertNotNull(result);
        assertEquals(result.length(), 508);
        context.setAttribute("data", result);
    }
}
