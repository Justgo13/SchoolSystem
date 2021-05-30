import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * This page will hold the login and register buttons
 * @author Jason Gao
 *
 */
public class WelcomePage extends JFrame implements ComponentListener, ActionListener{

	private Container contentPane;
	private JPanel buttonPanel;
	private JButton loginButton;
	private JButton registerButton;

	public WelcomePage() {
		init();
	}
	
	public void init() {
		setTitle("University Application");
		contentPane = getContentPane();
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
		
		setPreferredSize(new Dimension(800,600));
		addComponentListener(this);
		setResizable(false);
		pack();
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
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
    		new LoginGUI();
    	} else if (button.equals(registerButton)){
    		new RegisterGUI();
    	}
    }

	@Override
	public void componentResized(ComponentEvent e) {
		int width = getWidth();
        loginButton.setFont(new Font("Arial", Font.PLAIN, width / 10));
        registerButton.setFont(new Font("Arial", Font.PLAIN, width / 10));
        getContentPane().revalidate();
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
