package ProfessorMVC;

public enum ProfessorEnum {
    COURSE_LIST_NAME("Student Course List"), STUDENT_LIST_NAME("Student List"),
    EDIT_BUTTON_COMMAND("Edit"), COURSE_TAUGHT_LIST_NAME("Course Taught List"),
    ADD_COURSE_BUTTON_COMMAND("Add course"), REMOVE_COURSE_BUTTON_COMMAND("Remove Course");
    private String value;
    ProfessorEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
