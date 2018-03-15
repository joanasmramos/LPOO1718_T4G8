package dkeep.gui;

import java.awt.EventQueue;
import dkeep.cli.Interaction;

import javax.swing.JFrame;
import javax.swing.JLabel;
import dkeep.logic.GameState;
import dkeep.logic.Map;

import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;



public class DungeonKeep {

	private JFrame frame;
	private JTextField ogresnr;
	private JComboBox<String> guard;
	JTextArea consoledisp;
	JButton moveleft, moveright, moveup, movedown;
	JLabel statusMsg;

	public static char map1[][] =  {
			{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' },
			{ 'X', ' ', ' ', ' ', 'I', ' ', 'X', ' ', ' ', 'X' },
			{ 'X', 'X', 'X', ' ', 'X', 'X', 'X', ' ', ' ', 'X' },
			{ 'X', ' ', 'I', ' ', 'I', ' ', 'X', ' ', ' ', 'X' },
			{ 'X', 'X', 'X', ' ', 'X', 'X', 'X', ' ', ' ', 'X' },
			{ 'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
			{ 'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
			{ 'X', 'X', 'X', ' ', 'X', 'X', 'X', 'X', ' ', 'X' },
			{ 'X', ' ', 'I', ' ', 'I', ' ', 'X', 'K', ' ', 'X' },
			{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' },
	};

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DungeonKeep window = new DungeonKeep();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DungeonKeep() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel options = new JPanel();
		
		JPanel display = new JPanel();
		
		JPanel controls = new JPanel();
		
		statusMsg = new JLabel("You can start a new game.");
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(options, GroupLayout.PREFERRED_SIZE, 272, GroupLayout.PREFERRED_SIZE)
							.addGap(0, 0, Short.MAX_VALUE))
						.addComponent(display, GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addGap(127)
							.addGap(26)
							.addComponent(statusMsg)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(controls, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(options, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(display, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(statusMsg, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(18))
				.addComponent(controls, GroupLayout.PREFERRED_SIZE, 273, GroupLayout.PREFERRED_SIZE)
		);
		
		consoledisp = new JTextArea();
		consoledisp.setFont(new Font("Courier New", Font.PLAIN, 13));
		consoledisp.setEditable(false);
		display.add(consoledisp);
		controls.setLayout(new GridLayout(3, 0, 0, 0));
		
		JPanel pnlnewgame = new JPanel();
		controls.add(pnlnewgame);
		
		JButton btnNewGame = new JButton("New Game");
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newGamePressed();
			}
		});
		pnlnewgame.add(btnNewGame);
		
		JPanel controlspnl = new JPanel();
		controls.add(controlspnl);
		controlspnl.setLayout(new GridLayout(3, 0, 0, 0));
		
		moveup = new JButton("Up");
		moveup.setEnabled(false);
		controlspnl.add(moveup);
		
		JPanel leftrightpnl = new JPanel();   
		controlspnl.add(leftrightpnl);
		leftrightpnl.setLayout(new GridLayout(0, 2, 0, 0));
		
		moveleft = new JButton("Left");
		moveleft.setEnabled(false);
		moveleft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		leftrightpnl.add(moveleft);
		
		moveright = new JButton("Right");
		moveright.setEnabled(false);
		leftrightpnl.add(moveright);
		
		movedown = new JButton("Down");
		movedown.setEnabled(false);
		controlspnl.add(movedown);
		
		JPanel exitpnl = new JPanel();
		controls.add(exitpnl);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		exitpnl.add(btnExit);
		options.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblnrofogres = new JLabel("Number of Ogres");
		options.add(lblnrofogres);
		
		ogresnr = new JTextField();
		options.add(ogresnr);
		ogresnr.setColumns(10);
		
		JLabel lblguard = new JLabel("Guard Personality");
		options.add(lblguard);
		
		guard = new JComboBox<String>();
		guard.setModel(new DefaultComboBoxModel<String>(new String[] {"Rookie", "Drunken", "Suspicious"}));
		options.add(guard);
		frame.getContentPane().setLayout(groupLayout);
	}
	
	public void newGamePressed() {
		Interaction newGame = new Interaction(ogresnr.getText(), guard.getSelectedIndex());
		GameState game = newGame.Dungeon();
		
		consoledisp.setText(newGame.printToString(game.getMap())); // display map
		statusMsg.setText("You can play now");
		enableMoveKeys();
	}
	
	public void enableMoveKeys() {
		moveleft.setEnabled(true);
		moveright.setEnabled(true);
		moveup.setEnabled(true);
		movedown.setEnabled(true);
	}
}