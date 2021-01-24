public enum SignInConstants {
    DEFAULT_ACCOUNT_TYPE("Choose account type"), DEAN_ACCOUNT_TYPE("Dean"), PROFESSOR_ACCOUNT_TYPE("Professor"),
    STUDENT_ACCOUNT_TYPE("Student"), FN_LABEL("First Name"), LN_LABEL("Last Name"), USERNAME_LABEL("Username"),
    PASSWORD_LABEL("Password"), CONF_PASSWORD_LABEL("Confirm Password"), REGISTER_BTN_LABEL("Register"),
    CANCEL_BTN_LABEL("Cancel"), REGISTER_BTN_CMD("Register"), CANCEL_BTN_CMD("Cancel"),
    LOGIN_BTN_LABEL("Login"), LOGIN_BTN_CMD("Login");
    private String value;
    SignInConstants(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
