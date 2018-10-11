package prog.client;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import prog.shared.*;
import prog.shared.exception.InesistenteException;

public class ClienteMainFrame extends Login {

	private static final long serialVersionUID = 473462328033789200L;
	private JPanel contentPane;
	private JButton btnLogout;
	private JButton btnInfoContratto;
	private JButton btnInfoConsumi;
	private JButton btnCercaFattura;
	private JButton btnSegnalaGuasto;
	private JButton btnVisualizzaGuasti;
	private static String id;
	private static GeneralInt genInt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				ClienteMainFrame frame = new ClienteMainFrame(id, genInt);
				frame.setVisible(true);
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 */
	public ClienteMainFrame(String id, GeneralInt genInt) {
		ClienteMainFrame.genInt=genInt; 
		ClienteMainFrame.id=id;
		initComponents();
		createEvents();
	}

	private void initComponents(){
		setResizable(false);
		setTitle("Cliente");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClienteMainFrame.class.getResource("/resource/technology.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 208);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnInfoContratto = new JButton("Info Contratto");
		btnInfoContratto.setBounds(22, 34, 123, 23);
		contentPane.add(btnInfoContratto);

		btnInfoConsumi = new JButton("Info Consumi");
		btnInfoConsumi.setBounds(22, 68, 123, 23);
		contentPane.add(btnInfoConsumi);

		btnCercaFattura = new JButton("Cerca Fattura");
		btnCercaFattura.setBounds(22, 102, 123, 23);
		contentPane.add(btnCercaFattura);

		btnSegnalaGuasto = new JButton("Segnala Guasto");
		btnSegnalaGuasto.setBounds(247, 34, 130, 23);
		contentPane.add(btnSegnalaGuasto);

		btnVisualizzaGuasti = new JButton("Visualizza Guasti");
		btnVisualizzaGuasti.setBounds(247, 68, 130, 23);
		contentPane.add(btnVisualizzaGuasti);

		btnLogout = new JButton("LOGOUT");
		btnLogout.setBounds(288, 137, 89, 23);
		contentPane.add(btnLogout);

		JLabel lblNewLabel = new JLabel("ID Cliente :");
		lblNewLabel.setBounds(10, 10, 67, 14);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel(id);
		lblNewLabel_1.setBounds(89, 10, 255, 14);
		contentPane.add(lblNewLabel_1);
	}

	private void createEvents(){

		//Finestra conferma logout
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ret= JOptionPane.showConfirmDialog(null, "Are you sure to LOGOUT ?");
				if(ret == JOptionPane.YES_OPTION){
					try {
						genInt.logout(id);
						System.exit(0);
					} catch (RemoteException e1) {
						JOptionPane.showMessageDialog(btnLogout,"Errore Connessione Server");
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(btnLogout,"Errore Connessione Server");
					} catch (InesistenteException e1) {
						JOptionPane.showMessageDialog(btnLogout, e1.getMessage());
					}
				}
			}
		});

		//Finestra Info Contratto
		btnInfoContratto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ClienteInfoContratto infoContratto = new ClienteInfoContratto(genInt,id);
				infoContratto.setVisible(true);
				setEnabled(false);
				infoContratto.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						setVisible(true);
						setEnabled(true);
					}
				});
			}
		});

		//Finestra Info Consumi
		btnInfoConsumi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClienteInfoConsumi infoConsumi = new ClienteInfoConsumi(genInt,id);
				infoConsumi.setVisible(true);
				setEnabled(false);
				infoConsumi.addWindowListener(new WindowAdapter(){
					@Override
					public void windowClosed(WindowEvent e){
						setVisible(true);
						setEnabled(true);
					}
				});
			}
		});

		//Finestra Cerca Fattura
		btnCercaFattura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClienteCercaFattura cercaFattura = new ClienteCercaFattura(genInt,id);
				cercaFattura.setVisible(true);
				setEnabled(false);
				cercaFattura.addWindowListener(new WindowAdapter(){
					@Override
					public void windowClosed(WindowEvent e){
						setVisible(true);
						setEnabled(true);
					}
				});

			}
		});

		//Finestra Segnala Guasto
		btnSegnalaGuasto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ClienteSegnalaGuasto segnalaGuasto = new ClienteSegnalaGuasto(genInt,id);
				segnalaGuasto.setVisible(true);
				setEnabled(false);
				segnalaGuasto.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e){
						setVisible(true);
						setEnabled(true);
					}
				});
			}
		});

		//Finestra Visualizza Guasti
		btnVisualizzaGuasti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ClienteVisualizzaGuasti visualizzaGuasti = new ClienteVisualizzaGuasti(genInt,id);
				visualizzaGuasti.setVisible(true);
				setEnabled(false);
				visualizzaGuasti.addWindowListener(new WindowAdapter(){
					@Override
					public void windowClosed(WindowEvent e){
						setVisible(true);
						setEnabled(true);
					}
				});
			}
		});

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int ret= JOptionPane.showConfirmDialog(null, "Are you sure to LOGOUT ?");
				if(ret == JOptionPane.YES_OPTION){
					try {
						genInt.logout(id);
						dispose();
					} catch (RemoteException e1) {
						JOptionPane.showMessageDialog(btnLogout,"Errore Connessione Server");
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(btnLogout,"Errore Connessione Server");
					} catch (InesistenteException e1) {
						JOptionPane.showMessageDialog(btnLogout, e1.getMessage());
					}
				}
			}
		});

	}
}
