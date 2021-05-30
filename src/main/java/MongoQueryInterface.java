import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;

public interface MongoQueryInterface {
    static ArrayList<Document> getUserAccountDocuments() {
        return MongoQueryImpl.getUserAccountDocuments();
    }
    static String getDocumentCollectionName(Document document) {
        return MongoQueryImpl.getDocumentCollectionName(document);
    }
    static MongoCollection<Document> getCollection(String collectionName) {
        return MongoQueryImpl.getCollection(collectionName);
    }
    static void closeConnection() {
        MongoQueryImpl.closeConnection();
    };
}
