package prog.client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import prog.shared.GeneralInt;
import prog.shared.Privilegio;
import prog.shared.exception.GiaConnessoException;
import prog.shared.exception.LoginErratoException;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class Login extends JFrame {

	private static final long serialVersionUID = -5458621159589685795L;
	private JPanel contentPane;
	private JTextField textField;
	private JButton btnLogin;
	private Privilegio privilegio;
	private static String id;
	private JPasswordField passwordField;

	public static String getId() {
		return id;
	}

	private void setId(String text) {
		Login.id=text;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		initComponents();
		createEvents();
	}

	private void initComponents(){

		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/resource/technology.png")));
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 390, 254);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblUsername = new JLabel("Username :");
		lblUsername.setBounds(50, 55, 72, 24);
		contentPane.add(lblUsername);

		JLabel lblPassword = new JLabel("Password :");
		lblPassword.setBounds(50, 109, 72, 14);
		contentPane.add(lblPassword);

		textField = new JTextField();
		textField.setBounds(143, 57, 136, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		btnLogin = new JButton("LOGIN");
		btnLogin.setBounds(170, 162, 89, 23);
		contentPane.add(btnLogin);

		passwordField = new JPasswordField();
		passwordField.setBounds(143, 106, 136, 17);
		contentPane.add(passwordField);
	}

	////////This method contain all of code for creating events
	private void createEvents() {

		Registry reg;
		try {
			reg = LocateRegistry.getRegistry("127.0.0.1", 1099);
			GeneralInt genInt = (GeneralInt)reg.lookup("interfaceServer");
			System.out.println("Server trovato.");

			btnLogin.addActionListener(new ActionListener() {
				private ClienteMainFrame clienteMainFrame;
				private ImpiegatoMainFrame impiegatoMainFrame;
				private ManutentoreMainFrame manutentoreMainFrame;

				public void actionPerformed(ActionEvent arg0) {
					setId(textField.getText());
					char[] pass = passwordField.getPassword();
					String pw = new String(pass);
					try {
						privilegio =genInt.login(getId(), pw);

						if(privilegio == Privilegio.CL){
							setVisible(false);
							dispose();
							clienteMainFrame = new ClienteMainFrame(id, genInt);
							clienteMainFrame.setVisible(true);
						}else if(privilegio == Privilegio.IMP){
							setVisible(false);
							dispose();
							impiegatoMainFrame = new ImpiegatoMainFrame(id, genInt);
							impiegatoMainFrame.setVisible(true);
						}else if(privilegio == Privilegio.MAN){
							setVisible(false);
							dispose();
							manutentoreMainFrame = new ManutentoreMainFrame(id, genInt);
							manutentoreMainFrame.setVisible(true);
						}

					} catch (RemoteException e) {
						JOptionPane.showMessageDialog(btnLogin,"Errore Connessione Server");
					} catch (NotBoundException e) {
						JOptionPane.showMessageDialog(btnLogin,"Errore Connessione Server");
					} catch (LoginErratoException e) {							
						JOptionPane.showMessageDialog(btnLogin, e.getMessage());
					} catch (GiaConnessoException e) {
						JOptionPane.showMessageDialog(btnLogin, e.getMessage());
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(btnLogin,"Errore Connessione Server");
					}

				}

			});
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(btnLogin,"Errore Connessione Server");
		} catch (NotBoundException e) {
			JOptionPane.showMessageDialog(btnLogin,"Errore Connessione Server");
		}
	}
}
