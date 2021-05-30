import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface MongoQueryInterface {
    static ArrayList<Document> getUserAccountDocuments() {
        return MongoQueryImpl.getUserAccountDocuments();
    }
    static String getDocumentCollectionName(Document document) {
        return MongoQueryImpl.getDocumentCollectionName(document);
    }
    static List<String> getProfessorCourses(ObjectId profID) {
        return MongoQueryImpl.getProfessorCourses(profID);
    }
    static Map<ObjectId, List<String>> getAllStudentCourses() {
        return MongoQueryImpl.getAllStudentCourses();
    }
    static Long getStudentCourseGrade(ObjectId studentID, String studentCourseName) {
        return MongoQueryImpl.getStudentCourseGrade(studentID, studentCourseName);
    }
    static String getStudentFirstName(ObjectId studentID) {
        return MongoQueryImpl.getStudentFirstName(studentID);
    }
    static String getStudentLastName(ObjectId studentID) {
        return MongoQueryImpl.getStudentLastName(studentID);
    }
    static Long getStudentTuitionFee(ObjectId studentID) {
        return MongoQueryImpl.getStudentTuitionFee(studentID);
    }
    static void updateStudentGrade(ObjectId studentID, String courseName, Long grade) {
        MongoQueryImpl.updateStudentGrade(studentID, courseName, grade);
    }
    static ObjectId getAccountId(String username, String password) {
        return MongoQueryImpl.getAccountId(username, password);
    }
    static void addProfessorCourse(ObjectId profID, String courseName) {
        MongoQueryImpl.addProfessorCourse(profID, courseName);
    }
    static void removeProfessorCourse(ObjectId profID, String courseName) {
        MongoQueryImpl.removeProfessorCourse(profID, courseName);
    }
    static boolean usernameExists() {
        return MongoQueryImpl.usernameExists();
    }
    static ObjectId createAccountDocument(String username, String password) {
        return MongoQueryImpl.createAccountDocument(username, password);
    }
    static void createProfessorDocument(ObjectId accountObjectID, String username, String firstName, String lastName) {
        MongoQueryImpl.createProfessorDocument(accountObjectID, username, firstName, lastName);
    }
    static void closeConnection() {
        MongoQueryImpl.closeConnection();
    }
}
