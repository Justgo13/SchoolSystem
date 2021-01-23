public enum RegisterGUIConstants {
    DEFAULT_ACCOUNT_TYPE("Choose account type"), DEAN_ACCOUNT_TYPE("Dean"), PROFESSOR_ACCOUNT_TYPE("Professor"),
    STUDENT_ACCOUNT_TYPE("Student"), FN_LABEL("First Name"), LN_LABEL("Last Name"), USERNAME_LABEL("Username"),
    PASSWORD_LABEL("Password"), CONF_PASSWORD_LABEL("Confirm Password"), REGISTER_BTN_LABEL("Register"),
    CANCEL_BTN_LABEL("Cancel"), REGISTER_BTN_CMD("Register"), CANCEL_BTN_CMD("Cancel");
    private String value;
    RegisterGUIConstants(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
