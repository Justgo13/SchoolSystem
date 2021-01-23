public enum SQLConstants {
    DEAN_TABLE_NAME("dean"), PROFESSOR_TABLE_NAME("professor"), STUDENT_TABLE_NAME("student"),
    USER_PASS_TABLE_NAME("userpass"), DEAN_ID("deanID"), FIRST_NAME("firstName"), LAST_NAME("lastName"),
    SALARY("salary"), PROFESSOR_ID("professorID"), PROFESSOR_COURSE_TAUGHT("courseTaught"),
    USERNAME("username"), PASSWORD("password");
    private String value;
    SQLConstants(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
