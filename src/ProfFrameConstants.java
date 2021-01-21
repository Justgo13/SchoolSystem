public enum ProfFrameConstants {
    STUDENT_INFORMATION_BORDER_LABEL("Student Information"), STUDENT_ID_BORDER_LABEL("Student ID"),
    COURSE_TAUGHT_BORDER_LABEL("Courses Taught"), FIRST_NAME_FIELD_LABEL("First Name"),
    LAST_NAME_FIELD_LABEL("Last Name"), TUITION_FEE_FIELD_LABEL("Tuition Fee"),
    COURSES_BORDER_LABEL("Courses"), EDIT_BUTTON_LABEL("Edit Student Grade"), ADD_COURSE_LABEL("Add Course"),
    REMOVE_COURSE_LABEL("Remove Course"), COURSE_LIST_NAME("Student Course List"), STUDENT_LIST_NAME("Student List"),
    EDIT_BUTTON_COMMAND("Edit"), COURSE_TAUGHT_LIST_NAME("Professor Taught List");
    private String value;
    ProfFrameConstants(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
