import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class ProfGUI implements ActionListener {

    /**
     * JButton for teacher
     */
    private JButton updateGrade;
    private JButton courseToTeach;
    
	private JFrame frame;

	private Container container;

	private JPanel profPanel;
	
	private String profID;

    /**
     * Create a prof GUI
     * @param professor, Student student 
     */
    public ProfGUI(String profID){
    	this.profID = profID;
    	initProfGUI();
    }
    
    public void initProfGUI() {
        frame = new JFrame();
        container = frame.getContentPane();

        profPanel = new JPanel(new GridLayout(0,1));
        container.add(profPanel);

        updateGrade = new JButton("UPDATE GRADE");
        courseToTeach = new JButton("SET COURSE TAUGHT");

        updateGrade.setFocusPainted(false);
        courseToTeach.setFocusPainted(false);

        updateGrade.setFont(new Font("Arial", Font.BOLD, 40));
        courseToTeach.setFont(new Font("Arial", Font.BOLD, 40));

        profPanel.add(updateGrade);
        profPanel.add(courseToTeach);

        updateGrade.addActionListener(this);
        courseToTeach.addActionListener(this);

        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(800,600);
        frame.setResizable(true);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        JButton button = (JButton) o;
        
        if (button.equals(updateGrade)) {
        	try {
	        	// check to see if professor has any courses
	        	MainRun.myStmt = MainRun.myConn.prepareStatement("SELECT courseTaught FROM professor WHERE professorID = ?");
	        	MainRun.myStmt.setString(1, profID);
	        	MainRun.myRs = MainRun.myStmt.executeQuery();
	        	
	        	if (MainRun.myRs.next()) {
	        		if (MainRun.myRs.getString("courseTaught") == null) {
	        			JOptionPane.showMessageDialog(container, "Please choose a course to teach");
	        		} else {
	        			new UpdateGradeGUI(profID);
	        		}
	        	} 
        	} catch (SQLException e1) {
        		e1.getStackTrace();
        	}
        } else if (button.equals(courseToTeach)) {
        	new CourseToTeach(profID);
        }
    } 
}
