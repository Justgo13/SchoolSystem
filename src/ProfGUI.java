import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
    private String query;
    private ArrayList<String> queryParams;
    private ResultSet queryResult;
    private SQLQuery SQLInstance;
    /**
     * Create a prof GUI
     * @param profID, professor ID
     */
    public ProfGUI(String profID){
    	this.profID = profID;
        query = "";
        queryParams = new ArrayList<>();
        queryResult = null;
        SQLInstance = null;
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
            SQLInstance = new SQLQuery();
            query = "SELECT courseTaught FROM professor WHERE professorID = ?";
            queryParams.clear();
            queryParams.add(profID);
            queryResult = SQLInstance.runQuery(query, queryParams);
        	try {
	        	if (queryResult.next()) {
	        		if (queryResult.getString("courseTaught") == null) {
	        			JOptionPane.showMessageDialog(container, "Please choose a course to teach");
	        		} else {
	        			new UpdateGradeGUI(profID);
	        		}
	        	}
                SQLInstance.getMyConn().close();
                System.out.println("Connection terminated");
        	} catch (SQLException e1) {
        		e1.getStackTrace();
        	}
        } else if (button.equals(courseToTeach)) {
        	new CourseToTeach(profID);
        }
    } 
}
