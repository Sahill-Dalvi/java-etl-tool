package org.example;

import java.io.File;
import java.io.FileWriter;

public class ProcessingData {

    /**
     * This method is used to store data into files.
     * @param strArray Articles that are retrived from the News API.
     * @param array List of keywords whose data we retrieved.
     */
    public void processingDataToFiles(String[] strArray, String [] array) {
        try {
            Integer countForArray = 0;

            for( String keyword : array) {
                    String title = "";
                    String content = "";

                    StringBuilder articles = new StringBuilder("[");
                    int count =0;
                File folder = new File(keyword);
                folder.mkdir();
                if (strArray[countForArray].toString().contains("\"articles\":[")) {
                    String[] filterArticles = strArray[countForArray].toString().split("\"articles\":\\[")[1].split("\\],\"totalResults\"")[0].split("\\{\"source\":");
                    for (String rawArticle : filterArticles) {
                        if (rawArticle.contains("\"title\":")) {
                            title = rawArticle.split("\"title\":")[1].split(",\"description\"")[0].replace("\"", "");
                        }
                        if (rawArticle.contains("\"content\":")) {
                            content = rawArticle.split("\"content\":")[1].split(",\"url\"")[0].replace("\"", "");
                        }
                        articles.append("{\"title\":\"").append(title).append("\",\"content\":\"").append(content).append("\"},");
                        if(count%5==0 && count!=0){
                            articles.append("]");
                            String newArticles = articles.toString().replace("{\"title\":\"\",\"content\":\"\"},","");
                            File file = new File(folder+"/" +keyword + count/5 + ".json");
                            FileWriter writer = new FileWriter(file);
                            writer.write(newArticles.toString());
                            writer.flush();
                            writer.close();
                            articles = new StringBuilder("[");
                        }
                        count++;
                    }
                    articles.deleteCharAt(articles.length() - 1);
                }


                countForArray++;

            }
            StoringDataToDatabase obj = new StoringDataToDatabase();
            obj.storeToMongo(array);


        }
        catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}