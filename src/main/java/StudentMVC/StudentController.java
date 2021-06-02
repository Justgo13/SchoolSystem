package StudentMVC;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentController implements ActionListener, ListSelectionListener {
    private StudentModel studentModel;
    public StudentController(StudentModel studentModel) {
        this.studentModel = studentModel;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(StudentEnum.ADD_COURSE.toString())) {
            studentModel.addCourseTaken();
        }
        else if (e.getActionCommand().equals(StudentEnum.REMOVE_COURSE.toString())) {
            studentModel.removeCourseTaken();
        }
    }

    /**
     * Called whenever the value of the selection changes.
     *
     * @param e the event that characterizes the change.
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {

    }
}
