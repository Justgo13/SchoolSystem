import com.mongodb.Block;
import com.mongodb.client.*;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;

public class MongoQuery {
    public static final String DATABASE_NAME = "University";
    private String connectionString;
    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    public MongoQuery() {
        connectionString = System.getProperty("mongodb.uri");
        mongoClient = MongoClients.create(connectionString);
        mongoDatabase = mongoClient.getDatabase(DATABASE_NAME);
    }

    public MongoCollection getCollection(String collectionName) {
        return mongoDatabase.getCollection(collectionName);
    }

    public void closeConnection() {
        mongoClient.close();
    }

    public ArrayList<Document> getAllDocuments() {
        ArrayList<Document> allDocuments = new ArrayList<>();
        MongoIterable<String> collectionNames = mongoDatabase.listCollectionNames();
        for (String collectionName : collectionNames) {
            MongoCollection collection = mongoDatabase.getCollection(collectionName);
            collection.find().forEach((Block<Document>) document -> allDocuments.add(document));
        }
        return allDocuments;
    }

    public ArrayList<Document> getUserAccountDocuments() {
        ArrayList<Document> userAccountDocuments = new ArrayList<>();
        MongoIterable<String> collectionNames = mongoDatabase.listCollectionNames();
        for (String collectionName : collectionNames) {
            if (collectionName.equals("Userpass")) {
                continue;
            }
            MongoCollection collection = mongoDatabase.getCollection(collectionName);
            collection.find().forEach((Block<Document>) document -> userAccountDocuments.add(document));
        }
        return userAccountDocuments;
    }

    public String getDocumentCollectionName(Document document) {
        String collectionName = "";
        MongoIterable<String> collectionNames = mongoDatabase.listCollectionNames();
        for (String collectName : collectionNames) {
            MongoCollection collection = mongoDatabase.getCollection(collectName);
            FindIterable<Document> iterable = collection.find(document);
            if (iterable.first() != null) {
                collectionName = collectName;
            }
        }
        return collectionName;
    }
}