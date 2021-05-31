import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.client.*;
import org.bson.Document;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MongoQueryImpl implements MongoQueryInterface{
    public static final String DATABASE_NAME = "University";
    public static final String connectionString = System.getProperty("mongodb.uri");
    public static final MongoClient mongoClient = MongoClients.create(connectionString);
    public static final MongoDatabase mongoDatabase = mongoClient.getDatabase(DATABASE_NAME);
    public static final MongoCollection<Document> studentCollection = getCollection("Student");
    public static final MongoCollection<Document> accountCollection = getCollection("Userpass");
    public static final MongoCollection<Document> profCollection = getCollection("Professor");

    public static ArrayList<Document> getUserAccountDocuments() {
        ArrayList<Document> userAccountDocuments = new ArrayList<>();
        MongoIterable<String> collectionNames = mongoDatabase.listCollectionNames();
        for (String collectionName : collectionNames) {
            if (collectionName.equals("Userpass")) {
                continue;
            }
            MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);
            collection.find().forEach((Block<Document>) document -> userAccountDocuments.add(document));
        }
        return userAccountDocuments;
    }

    public static String getDocumentCollectionName(Document document) {
        String collectionName = "";
        MongoIterable<String> collectionNames = mongoDatabase.listCollectionNames();
        for (String collectName : collectionNames) {
            MongoCollection<Document> collection = mongoDatabase.getCollection(collectName);
            FindIterable<Document> iterable = collection.find(document);
            if (iterable.first() != null) {
                collectionName = collectName;
            }
        }
        return collectionName;
    }

    public static List<String> getProfessorCourses(ObjectId profID) {
        MongoCollection<Document> profCollection = getCollection("Professor");
        Document profDocument = profCollection.find(eq("_id", profID)).first();
        return profDocument.getList("coursesTaught", String.class);
    }

    public static Map<ObjectId, List<String>> getAllStudentCourses() {
        MongoCollection<Document> studentCollection = getCollection("Student");
        FindIterable<Document> studentDocuments = studentCollection.find();
        Map<ObjectId, List<String>> allStudentCourses = new HashMap<>();
        for (Document studentInformation : studentDocuments) {
            ObjectId studentID = studentInformation.getObjectId("_id");
            List<Document> studentCourseTaken = studentInformation.getList("courseGrades", Document.class);
            List<String> studentCourses = new ArrayList<>();
            for (Document course : studentCourseTaken) {
                studentCourses.add(course.getString("courseName"));
            }
            allStudentCourses.put(studentID, studentCourses);
        }
        return allStudentCourses;
    }

    public static Long getStudentCourseGrade(ObjectId studentID, String studentCourseName) {
        Document studentInformation = getStudentInformation(studentID);
        List<Document> studentCourses = studentInformation.getList("courseGrades", Document.class);
        Long studentCourseGrade = 0L;
        for (Document studentCourse : studentCourses) {
            String currCourseName = studentCourse.getString("courseName");
            Long currCourseGrade = studentCourse.getLong("grade");
            if (currCourseName.equals(studentCourseName)) {
                studentCourseGrade = currCourseGrade;
                break;
            }
        }
        return studentCourseGrade;
    }

    public static String getStudentFirstName(ObjectId studentID) {
        Document studentInformation = getStudentInformation(studentID);
        return studentInformation.getString("first name");
    }

    public static String getStudentLastName(ObjectId studentID) {
        Document studentInformation = getStudentInformation(studentID);
        return studentInformation.getString("last name");
    }

    public static Long getStudentTuitionFee(ObjectId studentID) {
        Document studentInformation = getStudentInformation(studentID);
        return studentInformation.getLong("tuition fee");
    }

    public static void updateStudentGrade(ObjectId studentID, String courseName, Long grade) {
        Document studentInformation = getStudentInformation(studentID);
        BasicDBObject query = new BasicDBObject();
        query.put("_id", studentInformation.getObjectId("_id"));
        query.put("courseGrades.courseName", courseName);

        BasicDBObject data = new BasicDBObject();
        data.put("courseGrades.$.grade", Long.valueOf(grade));

        BasicDBObject command = new BasicDBObject();
        command.put("$set", data);
        studentCollection.updateOne(query, command);
    }

    public static ObjectId getAccountId(String username, String password) {
        ObjectId accountObjectId = null;
        FindIterable<Document> userPassPair = accountCollection.find();
        for (Document doc : userPassPair){
            String mongoUsername = doc.getString("username");
            String mongoPassword = doc.getString("password");
            if (username.equals(mongoUsername) && password.equals(mongoPassword)) { // correct user found
                accountObjectId = doc.getObjectId("_id");
            }
        }
        return accountObjectId;
    }

    public static void addProfessorCourse(ObjectId profID, String courseName) {
        Document profDocument = getProfessorInformation(profID);

        BasicDBObject query = new BasicDBObject();
        query.put("_id", profDocument.getObjectId("_id"));

        BasicDBObject fields = new BasicDBObject();
        fields.put("coursesTaught", courseName);

        BasicDBObject command = new BasicDBObject();
        command.put("$push", fields);
        profCollection.updateOne(query, command);
    }

    public static void removeProfessorCourse(ObjectId profID, String courseName) {
        Document profDocument = getProfessorInformation(profID);

        BasicDBObject query = new BasicDBObject();
        query.put("_id", profDocument.getObjectId("_id"));

        BasicDBObject fields = new BasicDBObject();
        fields.put("coursesTaught", courseName);

        BasicDBObject command = new BasicDBObject();
        command.put("$pull", fields);
        profCollection.updateOne(query, command);
    }

    public static boolean usernameExists() {
        return accountCollection.countDocuments() == 0;
    }

    public static ObjectId createAccountDocument(String username, String password) {
        ObjectId accountObjectID = new ObjectId();
        Document userPassPair = new Document("_id", accountObjectID);
        userPassPair.append("username", username);
        userPassPair.append("password", password);
        accountCollection.insertOne(userPassPair);
        return accountObjectID;
    }

    public static void createProfessorDocument(ObjectId accountObjectID, String username, String firstName, String lastName) {
        Document userDocument = new Document("_id", accountObjectID);
        List<String> coursesTaught = new ArrayList<>();
        userDocument.append("username", username);
        userDocument.append("first name", firstName);
        userDocument.append("last name", lastName);
        userDocument.append("coursesTaught", coursesTaught);
        profCollection.insertOne(userDocument);
    }

    private static Document getStudentInformation(ObjectId studentID) {
        return studentCollection.find(eq("_id", studentID)).first();
    }

    private static Document getProfessorInformation(ObjectId profID) {
        return profCollection.find(eq("_id", profID)).first();
    }

    private static MongoCollection<Document> getCollection(String collectionName) {
        return mongoDatabase.getCollection(collectionName);
    }
}