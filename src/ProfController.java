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
        if (e.getActionCommand().equals("Edit")) {
            profModel.editCourseGrade();
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
        if (o.getName().equals("Student List")) {
            profModel.showStudentInfo(o.getSelectedValue());
        } else if (o.getName().equals("Student Course List")) {
            profModel.enableEditButton();
        } else if (o.getName().equals("Professor Taught List")) {

        }
    }
}
