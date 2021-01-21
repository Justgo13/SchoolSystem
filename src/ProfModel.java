import java.util.ArrayList;
import java.util.List;

public class ProfModel {
    private List<ProfView> profViews;
    private List<String> studentListData;
    private List<String> courseTaughtData;
    public ProfModel() {
        profViews = new ArrayList<>();
        studentListData = new ArrayList<>();
        courseTaughtData = new ArrayList<>();
    }

    public void addProfView(ProfView profView) {
        profViews.add(profView);
    }

    public void setStudentListData(List<String> studentListData) {
        this.studentListData = studentListData;
    }

    public void setCourseTaughtData(List<String> courseTaughtData) {
        this.courseTaughtData = courseTaughtData;
    }

    public List<String> getStudentListData() {
        return studentListData;
    }

    public List<String> getCourseTaughtData() {
        return courseTaughtData;
    }

    public void showStudentInfo(Object selectedValue) {
        for (ProfView pv : profViews) {
            pv.handleShowStudentInfo(selectedValue);
        }
    }

    public void editCourseGrade() {
        for (ProfView pv : profViews) {
            pv.handleUpdateCourseGrade();
        }
    }

    public void enableEditButton() {
        for (ProfView pv : profViews) {
            pv.handleEnableEditCourse();
        }
    }
}
