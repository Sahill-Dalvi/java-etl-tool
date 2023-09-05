package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ReadDataFromApi {

    /**
     * This method fetches data from the News API.
     * @throws IOException
     */
    public void GetData() throws IOException {
        String apiKeyOfNewsApi = "4b8474eeef864d3caf32a6c3620b1f85";

        String[] keywords = {"Canada", "University", "Dalhousie", "Halifax", "Canada Education", "Moncton", "Hockey", "Fredericton", "Celebration"};
        String[] dataFromApi = new String[keywords.length];
        Integer count = 0;
        for (String word : keywords) {

            String apiUrl = "https://newsapi.org/v2/everything?q=" + word + "&pageSize=100&apiKey=" + apiKeyOfNewsApi;
            URL News = new URL(apiUrl);
            HttpURLConnection connect = (HttpURLConnection) News.openConnection();
            connect.setRequestMethod("GET");

            BufferedReader read = new BufferedReader(new InputStreamReader(connect.getInputStream()));

            String inputTheValues;
            StringBuilder response = new StringBuilder();

            while ((inputTheValues = read.readLine()) != null) {
                response.append(inputTheValues);
            }
            System.out.println("Fetching Data from the API for the keyword " + word +" "+ response);
            read.close();
            String str = response.toString();
            dataFromApi[count] = str;
            count++;
//
        }
        ProcessingData obj = new ProcessingData();
            obj.processingDataToFiles(dataFromApi,keywords);


    }}
