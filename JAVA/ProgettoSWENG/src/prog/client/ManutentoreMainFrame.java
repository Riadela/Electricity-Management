package prog.client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import prog.shared.GeneralInt;
import prog.shared.exception.InesistenteException;

import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class ManutentoreMainFrame extends JFrame {

	private static final long serialVersionUID = -6880600686159045304L;
	private JPanel contentPane;
	private JButton btnLogout;
	private JButton AggiungiComponenteBtn;
	private JButton btnModificaComponente;
	private JButton btnCercaComponente;
	private JButton btnEliminaComponente;
	private JButton btnAggiungiGuasto;
	private JButton btnPrendiInCaricoGuasto;
	private JButton btnRegistraRiparazione;
	private JButton btnVisualizzaElencoGuasti;
	private static String id;
	private static GeneralInt genInt;

	public GeneralInt getManInt() {
		return genInt;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManutentoreMainFrame frame = new ManutentoreMainFrame(id,genInt);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * 
	 */
	public ManutentoreMainFrame(String id, GeneralInt genInt) throws RemoteException, NotBoundException {
		ManutentoreMainFrame.genInt = genInt;
		ManutentoreMainFrame.id=id;
		initComponents();
		createEvents();
	}

	private void initComponents() throws RemoteException, NotBoundException{
		setResizable(false);
		setTitle("Manutentore");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ManutentoreMainFrame.class.getResource("/resource/technology.png")));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 243);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("ID Manutentore :");
		lblNewLabel.setBounds(10, 10, 119, 14);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel(id);  
		lblNewLabel_1.setBounds(141, 10, 152, 14);
		contentPane.add(lblNewLabel_1);

		AggiungiComponenteBtn = new JButton("Aggiungi Componente");
		AggiungiComponenteBtn.setBounds(20, 34, 158, 23);
		contentPane.add(AggiungiComponenteBtn);

		btnModificaComponente = new JButton("Modifica Componente");
		btnModificaComponente.setBounds(20, 68, 158, 23);
		contentPane.add(btnModificaComponente);

		btnCercaComponente = new JButton("Cerca Componente");
		btnCercaComponente.setBounds(20, 102, 158, 23);
		contentPane.add(btnCercaComponente);

		btnEliminaComponente = new JButton("Elimina Componente");
		btnEliminaComponente.setBounds(20, 136, 158, 23);
		contentPane.add(btnEliminaComponente);

		btnAggiungiGuasto = new JButton("Aggiungi Guasto");
		btnAggiungiGuasto.setBounds(20, 170, 158, 23);
		contentPane.add(btnAggiungiGuasto);

		btnVisualizzaElencoGuasti = new JButton("Visualizza Elenco Guasti");
		btnVisualizzaElencoGuasti.setBounds(224, 35, 171, 23);
		contentPane.add(btnVisualizzaElencoGuasti);

		btnLogout = new JButton("LOGOUT");
		btnLogout.setBounds(306, 170, 89, 23);
		contentPane.add(btnLogout);

		btnPrendiInCaricoGuasto = new JButton("Prendi In Carico Guasto");
		btnPrendiInCaricoGuasto.setBounds(224, 68, 171, 23);
		contentPane.add(btnPrendiInCaricoGuasto);

		btnRegistraRiparazione = new JButton("Registra Riparazione");
		btnRegistraRiparazione.setBounds(224, 102, 171, 23);
		contentPane.add(btnRegistraRiparazione);
	}

	private void createEvents(){
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

		//Finestra AggiungiComponente
		AggiungiComponenteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ManutentoreAggiungiComponente aggiungiComponente = new ManutentoreAggiungiComponente(genInt);
				aggiungiComponente.setVisible(true);
				setEnabled(false);
				aggiungiComponente.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						setVisible(true);
						setEnabled(true);
					}
				});
			}
		});

		//Finestra Modifica Componente
		btnModificaComponente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManutentoreModificaComponente modificaComponente = new ManutentoreModificaComponente(genInt);
				modificaComponente.setVisible(true);
				setEnabled(false);
				modificaComponente.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e){
						setVisible(true);
						setEnabled(true);
					}
				});
			}
		});

		//Finestra Cerca Componente
		btnCercaComponente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ManutentoreCercaComponente cercaComponente = new ManutentoreCercaComponente(genInt);
				cercaComponente.setVisible(true);
				setEnabled(false);
				cercaComponente.addWindowListener(new WindowAdapter(){
					@Override
					public void windowClosed(WindowEvent e){
						setVisible(true);
						setEnabled(true);
					}
				});
			}
		});

		//Finestra Elimina Componente
		btnEliminaComponente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManutentoreEliminaComponente eliminaComponente = new ManutentoreEliminaComponente(genInt);
				eliminaComponente.setVisible(true);
				setEnabled(false);
				eliminaComponente.addWindowListener(new WindowAdapter(){
					@Override
					public void windowClosed(WindowEvent e){
						setVisible(true);
						setEnabled(true);
					}
				});
			}
		});

		//Finestra Aggiungi Guasto
		btnAggiungiGuasto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ManutentoreAggiungiGuasto aggiungiGuasto = new ManutentoreAggiungiGuasto(genInt);
				aggiungiGuasto.setVisible(true);
				setEnabled(false);
				aggiungiGuasto.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e){
						setVisible(true);
						setEnabled(true);
					}
				});
			}
		});

		//FInetsra Visualizza Guasti
		btnVisualizzaElencoGuasti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ManutentoreVisualizzaElencoGuasti visualizzaElencoGuasti = new ManutentoreVisualizzaElencoGuasti(genInt,id);
				visualizzaElencoGuasti.setVisible(true);
				setEnabled(false);
				visualizzaElencoGuasti.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e){
						setVisible(true);
						setEnabled(true);
					}
				});
			}
		});

		//Finestra Prendi In Carico Guasto
		btnPrendiInCaricoGuasto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManutentorePrendiInCaricoGuasto prendiInCaricoGuasto = new ManutentorePrendiInCaricoGuasto(genInt,id);
				prendiInCaricoGuasto.setVisible(true);
				setEnabled(false);
				prendiInCaricoGuasto.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e){
						setVisible(true);
						setEnabled(true);
					}
				});
			}
		});

		//Finestra Registra Riparazione Guasto
		btnRegistraRiparazione.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManutentoreRiparazioneGuasto riparazioneGuasto = new ManutentoreRiparazioneGuasto(genInt);
				riparazioneGuasto.setVisible(true);
				setEnabled(false);
				riparazioneGuasto.addWindowListener(new WindowAdapter() {
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
