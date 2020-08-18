import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 * Panel for adding courses to students
 * @author jgao2
 *
 */
public class UnregisterCourseGUI implements ActionListener, dataStorage{
	private Student student;
	private JFrame frame;
	private Container container;
	private JPanel unregisterPanel;
	private JButton unregisterButton;
	private JButton cancelButton;
	private GridBagConstraints constraints;
	private JComboBox<?> courseComboBox;
	private ArrayList<String> courseNames;
	public UnregisterCourseGUI(Student student) {
		this.student = student;
		initUnregisterCourse();
	}
	private void initUnregisterCourse() {
		frame = new JFrame();
        container = frame.getContentPane();

        unregisterPanel = new JPanel(new GridBagLayout());
        container.add(unregisterPanel);
        
        courseNames = new ArrayList<String>();
        courseNames.add("Choose course");
        courseNames.addAll(student.getCourse().keySet());
        
        courseComboBox = new JComboBox<>(courseNames.toArray());
        
		// create button
		unregisterButton = new JButton("UNREGISTER COURSE");
		cancelButton = new JButton("CANCEL");
		
		constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0.0;
		constraints.weighty = 1.0;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.EAST;
		constraints.insets = new Insets(0,5,0,5);
		
		unregisterPanel.add(courseComboBox, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 1;
		unregisterPanel.add(unregisterButton, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 2;
		unregisterPanel.add(cancelButton, constraints);
		
		// adding actionListener
		unregisterButton.addActionListener(this);
		cancelButton.addActionListener(this);
		
		frame.setPreferredSize(new Dimension(800,600));
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		JButton button = (JButton) o;
		
		if (button.equals(unregisterButton)) {
			if (checkCourseCodeValid()) {
				removeCourse((String) courseComboBox.getSelectedItem());
				frame.dispose();
			} else {
				String errorList = "Valid course selection: " + checkCourseCodeValid();
				JOptionPane.showMessageDialog(unregisterPanel, errorList);
			}
		} else if (button.equals(cancelButton)) {
			frame.dispose();
		}	
	}
	/**
	 * Adds course to the list of student courses
	 */
	private void removeCourse(String courseCode) {
		student.removeCourse(courseCode);
	}
	/**
	 * Checks if course code is empty
	 * @return true if course code is fill, false otherwise
	 */
	private boolean checkCourseCodeValid() {
		if (courseComboBox.getSelectedIndex() == 0) {
			courseComboBox.setBorder(BorderFactory.createLineBorder(Color.RED));
			return false;
		} else {
			courseComboBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		}
		return true;
	}
}
