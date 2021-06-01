package StudentMVC;

public enum StudentEnum {
    COURSE_LIST_NAME("Courses taken"), ADD_COURSE("Add course"), REMOVE_COURSE("Remove course");
    private String value;
    StudentEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
