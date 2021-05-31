package MongoQuery;

public enum MongoQueryEnum {
    ID("_id"), profCourses("coursesTaught"), studentCourses("courseGrades"),
    studentCourseName("courseName"), studentCourseGrade("grade"), userFirstName("first name"),
    userLastName("last name"), studentTuitionFee("tuition fee"), userUsername("username"),
    userPassword("password");
    private String value;
    MongoQueryEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
