package ProfessorMVC;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfController implements ActionListener, ListSelectionListener {
    private ProfModel profModel;
    public ProfController(ProfModel profModel) {
        this.profModel = profModel;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(ProfessorEnum.EDIT_BUTTON_COMMAND.toString())) {
            profModel.editCourseGrade();
        } else if (e.getActionCommand().equals(ProfessorEnum.ADD_COURSE_BUTTON_COMMAND.toString())) {
            profModel.addCourseTaught();
        } else if (e.getActionCommand().equals(ProfessorEnum.REMOVE_COURSE_BUTTON_COMMAND.toString())) {
            profModel.removeCourseTaught();
        }
    }

    /**
     * Called whenever the value of the selection changes.
     *
     * @param e the event that characterizes the change.
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {
        JList o = (JList) e.getSource();
        if (e.getValueIsAdjusting()) {
            if (o.getName().equals(ProfessorEnum.STUDENT_LIST_NAME.toString())) {
                profModel.showStudentInfo(o.getSelectedValue());
            } else if (o.getName().equals(ProfessorEnum.COURSE_LIST_NAME.toString())) {
                profModel.enableEditButton();
            } else if (o.getName().equals(ProfessorEnum.COURSE_TAUGHT_LIST_NAME.toString())) {
                profModel.enableRemoveCourseTaught();
            }
        }

    }
}
