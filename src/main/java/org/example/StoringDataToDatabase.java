package org.example;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class StoringDataToDatabase {

    /**
     * This method is used to filter and store data into MongoDB.
     * @param keywords Keywords for whom we retrived the data from News API.
     */
    public void storeToMongo(String [] keywords){
        try{
            MongoClient mongoClient = MongoClients.create("mongodb+srv://dalvisahil0900:hdlg6eDstNayOsn4@lab6.owqm9h3.mongodb.net/?retryWrites=true&w=majority");
            MongoDatabase db = mongoClient.getDatabase("myMongoNews");
            int count = 0;
            String [] allValues = new String[keywords.length];
            for (String folderName : keywords) {
                File folder = new File(folderName);
                File[] files = folder.listFiles();
//                System.out.println(Arrays.toString(files));
                String line = new String();
                ArrayList<Document> mongoDoc = new ArrayList<Document>();
                for (File FilesInFolder : files) {
                    try{
                        Scanner myReader = new Scanner(FilesInFolder);
                        String newLine = myReader.nextLine();
                        line += newLine;
                        String[] sp = newLine.split("\"title\":\"");
                        for(int i = 1; i < sp.length; i++ ){
                            String title = newLine.split("\"title\":\"")[i].split("\",\"content\":\"")[0]
                                    .replaceAll("\\<.*?\\>", " ")
                                    .replaceAll("[^a-zA-Z0-9\\s]", "")
                                    .replaceAll("(?i)\\b((?:https?|ftp)://\\S+)", "").replaceAll("[:;]-?[()\\[\\]{}|]?", "");;;;;
                            String content = newLine.split("\"title\":\"")[i].split("\",\"content\":\"")[1]
                                    .replaceAll("\\<.*?\\>", " ")
                                    .replaceAll("[^a-zA-Z0-9\\s]", "")
                                    .replaceAll("(?i)\\b((?:https?|ftp)://\\S+)", "")
                                    .replaceAll("[:;]-?[()\\[\\]{}|]?", "");;;;;
                            Document addValues = new Document()
                                    .append("title", title)
                                    .append("content", content);
                            mongoDoc.add(addValues);
                        }
                    } catch (Exception exception){
                        System.out.println(exception.getMessage());
                        exception.printStackTrace();
                    }
                }
                MongoCollection<Document> collection = db.getCollection(folderName);
                collection.insertMany(mongoDoc);
            }

        } catch (Exception e){
            System.out.println("Error");
        }

    }
}
