package MongoQuery;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.client.*;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mongodb.client.model.Filters.eq;

public class MongoQueryImpl implements MongoQueryInterface {
    public static final String DATABASE_NAME = "University";
    public static final String connectionString = System.getProperty("mongodb.uri");
    public static final MongoClient mongoClient = MongoClients.create(connectionString);
    public static final MongoDatabase mongoDatabase = mongoClient.getDatabase(DATABASE_NAME);
    public static final MongoCollection<Document> studentCollection = getCollection("Student");
    public static final MongoCollection<Document> accountCollection = getCollection("Userpass");
    public static final MongoCollection<Document> profCollection = getCollection("Professor");

    public static String getDocumentCollectionName(ObjectId documentObjectID) {
        ArrayList<Document> accountDocuments = getUserAccountDocuments();
        String collectionName = "";
        for (Document doc : accountDocuments) {
            if (doc.getObjectId(MongoQueryEnum.ID.toString()).equals(documentObjectID)) {
                MongoIterable<String> collectionNames = mongoDatabase.listCollectionNames();
                for (String collectName : collectionNames) {
                    MongoCollection<Document> collection = mongoDatabase.getCollection(collectName);
                    FindIterable<Document> iterable = collection.find(doc);
                    if (iterable.first() != null) {
                        collectionName = collectName;
                    }
                }
            }
        }
        return collectionName;
    }

    public static List<String> getProfessorCourses(ObjectId profID) {
        MongoCollection<Document> profCollection = getCollection("Professor");
        Document profDocument = profCollection.find(eq(MongoQueryEnum.ID.toString(), profID)).first();
        return profDocument.getList(MongoQueryEnum.profCourses.toString(), String.class);
    }

    public static Map<ObjectId, List<String>> getAllStudentCourses() {
        MongoCollection<Document> studentCollection = getCollection("Student");
        FindIterable<Document> studentDocuments = studentCollection.find();
        Map<ObjectId, List<String>> allStudentCourses = new HashMap<>();
        for (Document studentInformation : studentDocuments) {
            ObjectId studentID = studentInformation.getObjectId(MongoQueryEnum.ID.toString());
            List<Document> studentCourseTaken = studentInformation.getList(MongoQueryEnum.studentCourses.toString(), Document.class);
            List<String> studentCourses = new ArrayList<>();
            for (Document course : studentCourseTaken) {
                studentCourses.add(course.getString(MongoQueryEnum.studentCourseName.toString()));
            }
            allStudentCourses.put(studentID, studentCourses);
        }
        return allStudentCourses;
    }

    public static Long getStudentCourseGrade(ObjectId studentID, String studentCourseName) {
        Document studentInformation = getStudentDocument(studentID);
        List<Document> studentCourses = studentInformation.getList(MongoQueryEnum.studentCourses.toString(), Document.class);
        Long studentCourseGrade = 0L;
        for (Document studentCourse : studentCourses) {
            String currCourseName = studentCourse.getString(MongoQueryEnum.studentCourseName.toString());
            Long currCourseGrade = studentCourse.getLong(MongoQueryEnum.studentCourseGrade.toString());
            if (currCourseName.equals(studentCourseName)) {
                studentCourseGrade = currCourseGrade;
                break;
            }
        }
        return studentCourseGrade;
    }

    public static String getStudentFirstName(ObjectId studentID) {
        Document studentInformation = getStudentDocument(studentID);
        return studentInformation.getString(MongoQueryEnum.userFirstName.toString());
    }

    public static String getStudentLastName(ObjectId studentID) {
        Document studentInformation = getStudentDocument(studentID);
        return studentInformation.getString(MongoQueryEnum.userLastName.toString());
    }

    public static Long getStudentTuitionFee(ObjectId studentID) {
        Document studentInformation = getStudentDocument(studentID);
        return studentInformation.getLong(MongoQueryEnum.studentTuitionFee.toString());
    }

    public static void updateStudentGrade(ObjectId studentID, String courseName, Long grade) {
        Document studentInformation = getStudentDocument(studentID);
        BasicDBObject query = new BasicDBObject();
        query.put(MongoQueryEnum.ID.toString(), studentInformation.getObjectId(MongoQueryEnum.ID.toString()));
        query.put(MongoQueryEnum.studentCourses.toString() + "." + MongoQueryEnum.studentCourseName.toString(), courseName);

        BasicDBObject data = new BasicDBObject();
        data.put(MongoQueryEnum.studentCourses.toString() + ".$." + MongoQueryEnum.studentCourseGrade.toString(), Long.valueOf(grade));

        BasicDBObject command = new BasicDBObject();
        command.put("$set", data);
        studentCollection.updateOne(query, command);
    }

    public static ObjectId getAccountId(String username, String password) {
        ObjectId accountObjectId = null;
        FindIterable<Document> userPassPair = accountCollection.find();
        for (Document doc : userPassPair) {
            String mongoUsername = doc.getString(MongoQueryEnum.userUsername.toString());
            String mongoPassword = doc.getString(MongoQueryEnum.userPassword.toString());
            if (username.equals(mongoUsername) && password.equals(mongoPassword)) { // correct user found
                accountObjectId = doc.getObjectId(MongoQueryEnum.ID.toString());
            }
        }
        return accountObjectId;
    }

    public static void addProfessorCourse(ObjectId profID, String courseName) {
        profCollection.updateOne(eq(MongoQueryEnum.ID.toString(), profID), Updates.addToSet(MongoQueryEnum.profCourses.toString(), courseName));
    }

    public static void removeProfessorCourse(ObjectId profID, String courseName) {
        profCollection.updateOne(eq(MongoQueryEnum.ID.toString(), profID), Updates.pull(MongoQueryEnum.profCourses.toString(), courseName));
    }

    public static boolean usernameExists() {
        return accountCollection.countDocuments() == 0;
    }

    public static ObjectId createAccountDocument(String username, String password) {
        ObjectId accountObjectID = new ObjectId();
        Document userPassPair = new Document(MongoQueryEnum.ID.toString(), accountObjectID);
        userPassPair.append(MongoQueryEnum.userUsername.toString(), username);
        userPassPair.append(MongoQueryEnum.userPassword.toString(), password);
        accountCollection.insertOne(userPassPair);
        return accountObjectID;
    }

    public static void createProfessorDocument(ObjectId accountObjectID, String username, String firstName, String lastName) {
        Document userDocument = new Document(MongoQueryEnum.ID.toString(), accountObjectID);
        List<String> coursesTaught = new ArrayList<>();
        userDocument.append(MongoQueryEnum.userUsername.toString(), username);
        userDocument.append(MongoQueryEnum.userFirstName.toString(), firstName);
        userDocument.append(MongoQueryEnum.userLastName.toString(), lastName);
        userDocument.append(MongoQueryEnum.profCourses.toString(), coursesTaught);
        profCollection.insertOne(userDocument);
    }

    public static Map<String, Long> getStudentCourses(ObjectId studentID) {
        Document studentDocument = getStudentDocument(studentID);
        Map<String, Long> studentCourses = new HashMap<>();
        List<Document> studentCourseList = studentDocument.getList(MongoQueryEnum.studentCourses.toString(), Document.class);

        for (Document course : studentCourseList) {
            String courseName = course.getString(MongoQueryEnum.studentCourseName.toString());
            Long grade = course.getLong(MongoQueryEnum.studentCourseGrade.toString());
            studentCourses.put(courseName, grade);
        }

        return studentCourses;
    }

    public static void addStudentCourse(ObjectId studentID, String courseToAdd) {
        Document courseDocument = new Document();
        courseDocument.append(MongoQueryEnum.studentCourseName.toString(), courseToAdd);
        courseDocument.append(MongoQueryEnum.studentCourseGrade.toString(), 0L);
        studentCollection.updateOne(eq(MongoQueryEnum.ID.toString(), studentID), Updates.addToSet(MongoQueryEnum.studentCourses.toString(), courseDocument));
    }

    public static void removeStudentCourse(ObjectId studentID, String courseToRemove, Long grade) {
        Document courseToRemoveDocument = new Document();
        courseToRemoveDocument.append(MongoQueryEnum.studentCourseName.toString(), courseToRemove);
        courseToRemoveDocument.append(MongoQueryEnum.studentCourseGrade.toString(), grade);
        // findCourseDocument(studentID, courseToRemove);
        studentCollection.updateOne(eq(MongoQueryEnum.ID.toString(), studentID), Updates.pull(MongoQueryEnum.studentCourses.toString(), courseToRemoveDocument));
    }

    private static ArrayList<Document> getUserAccountDocuments() {
        ArrayList<Document> userAccountDocuments = new ArrayList<>();
        MongoIterable<String> collectionNames = mongoDatabase.listCollectionNames();
        for (String collectionName : collectionNames) {
            if (!collectionName.equals("Userpass")) {
                MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);
                collection.find().forEach((Block<Document>) document -> userAccountDocuments.add(document));
            }
        }
        return userAccountDocuments;
    }

    private static Document getStudentDocument(ObjectId studentID) {
        return studentCollection.find(eq(MongoQueryEnum.ID.toString(), studentID)).first();
    }

    private static Document getProfessorInformation(ObjectId profID) {
        return profCollection.find(eq(MongoQueryEnum.ID.toString(), profID)).first();
    }

    private static MongoCollection<Document> getCollection(String collectionName) {
        return mongoDatabase.getCollection(collectionName);
    }
}