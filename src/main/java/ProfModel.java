import java.util.ArrayList;
import java.util.List;

public class ProfModel {
    private List<ProfView> profViews;
    public ProfModel() {
        profViews = new ArrayList<>();
    }

    public void addProfView(ProfView profView) {
        profViews.add(profView);
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

    public void addCourseTaught() {
        for (ProfView pv : profViews) {
            pv.handleAddCourseTaught();
        }
    }

    public void removeCourseTaught() {
        for (ProfView pv : profViews) {
            pv.handleRemoveCourseTaught();
        }
    }

    public void enableRemoveCourseTaught() {
        for (ProfView pv : profViews) {
            pv.handleEnableRemoveCourseTaught();
        }
    }
}
