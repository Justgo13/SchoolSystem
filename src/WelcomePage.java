import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * This page will hold the login and register buttons
 * @author Jason Gao
 *
 */
public class WelcomePage implements ComponentListener, ActionListener{
	private JFrame frame;
	private Container contentPane;
	private JPanel buttonPanel;
	private JButton loginButton;
	private JButton registerButton;

	public WelcomePage() {
		init();
	}
	
	public void init() {
		frame = new JFrame("University Application");
		frame.addWindowListener(new WindowAdapter() {
			@Override
		    public void windowClosing(WindowEvent e)
		    {
		        super.windowClosing(e);
		        try {
					MainRun.myConn.close();
					System.out.println("Server disconnected");
			        System.exit(0);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    }
		});
		contentPane = frame.getContentPane();
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(0,1));
		contentPane.add(buttonPanel);
		
		// creating login and register buttons
		loginButton = new JButton("Login");
		registerButton = new JButton("Register");
		
		// setting button font size
		loginButton.setFont(new Font("Arial", Font.PLAIN, 40));
		registerButton.setFont(new Font("Arial", Font.PLAIN, 40));
		
		// add actionListners
		addActionListners(); 
		
		buttonPanel.add(loginButton);
		buttonPanel.add(registerButton);
		
		frame.setPreferredSize(new Dimension(800,600));
		frame.addComponentListener(this);
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
	}

	private void addActionListners() {
		loginButton.addActionListener(this);
		registerButton.addActionListener(this);
	}
	
	 /**
     * Listens to ActionEvents
     * @param e listen to actionEvent
     */ 
    public void actionPerformed(ActionEvent e) {
    	Object o = e.getSource();
    	JButton button = (JButton) o;
    	
    	if (button.equals(loginButton)) {
    		new Login();
    	} else if (button.equals(registerButton)){
    		new Register();
    	}
    }

	@Override
	public void componentResized(ComponentEvent e) {
		int width = frame.getWidth();
        loginButton.setFont(new Font("Arial", Font.PLAIN, width / 10));
        registerButton.setFont(new Font("Arial", Font.PLAIN, width / 10));
        frame.getContentPane().revalidate();
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
}
