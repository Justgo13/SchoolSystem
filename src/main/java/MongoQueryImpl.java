import com.mongodb.Block;
import com.mongodb.client.*;
import org.bson.Document;
import java.util.ArrayList;

public class MongoQueryImpl {
    public static final String DATABASE_NAME = "University";
    public static final String connectionString = System.getProperty("mongodb.uri");;
    public static final MongoClient mongoClient = MongoClients.create(connectionString);;
    public static final MongoDatabase mongoDatabase = mongoClient.getDatabase(DATABASE_NAME);

    public static ArrayList<Document> getUserAccountDocuments() {
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

    public static String getDocumentCollectionName(Document document) {
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

    public static MongoCollection<Document> getCollection(String collectionName) {
        return mongoDatabase.getCollection(collectionName);
    }

    public static void closeConnection() {
        mongoClient.close();
    }
}