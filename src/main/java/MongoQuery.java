import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
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
}