package StudentMVC;

import StudentMVC.StudentView;

import java.util.ArrayList;
import java.util.List;

public class StudentModel {
    private List<StudentView> studentViews;
    public StudentModel() {
        studentViews = new ArrayList<>();
    }

    public void addStudentView(StudentView studentView) {
        studentViews.add(studentView);
    }

    public void addCourseTaken() {
        for (StudentView sv : studentViews) {
            sv.handleAddCourseTaken();
        }
    }

    public void removeCourseTaken() {
        for (StudentView sv : studentViews) {
            sv.handleRemoveCourseTaken();
        }
    }
}
