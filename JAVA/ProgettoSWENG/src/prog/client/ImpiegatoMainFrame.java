package prog.client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import prog.shared.GeneralInt;
import prog.shared.exception.InesistenteException;

import java.awt.Toolkit;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class ImpiegatoMainFrame extends JFrame {

	private static final long serialVersionUID = -5425087772576419679L;
	private JPanel contentPane;
	private JButton Logoutbtn;
	private JButton btnAggiungiUtente;
	private JButton btnModificaUtente;
	private JButton btnEliminaUtente;
	private JButton btnAggiungiFattura;
	private JButton btnCercaUtente;
	private JButton btnAggiungiContratto;
	private JButton btnCercaFattura;
	private JButton btnEliminaContratto;
	private JButton btnCercaContratto;
	private JButton btnAggiungiConsumo;
	private static GeneralInt genInt;
	private static String id;

	public GeneralInt getGenInt() {
		return genInt;
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ImpiegatoMainFrame frame = new ImpiegatoMainFrame(id, genInt);
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
	public ImpiegatoMainFrame(String id, GeneralInt genInt) throws RemoteException, NotBoundException {
		ImpiegatoMainFrame.id=id;
		ImpiegatoMainFrame.genInt = genInt;
		initComponents();
		createEvents();
	}

	private void initComponents() throws RemoteException, NotBoundException{

		setResizable(false);	
		setTitle("Impiegato Amministrativo");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ImpiegatoMainFrame.class.getResource("/resource/technology.png")));	
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 282);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnAggiungiUtente = new JButton("Aggiungi Utente");
		btnAggiungiUtente.setBounds(22, 34, 139, 23);
		contentPane.add(btnAggiungiUtente);

		btnModificaUtente = new JButton("Modifica Utente");
		btnModificaUtente.setBounds(22, 68, 139, 23);
		contentPane.add(btnModificaUtente);

		btnCercaUtente = new JButton("Cerca Utente");
		btnCercaUtente.setBounds(22, 102, 139, 23);
		contentPane.add(btnCercaUtente);

		btnEliminaUtente = new JButton("Elimina Utente");
		btnEliminaUtente.setBounds(22, 136, 139, 23);
		contentPane.add(btnEliminaUtente);

		btnAggiungiContratto = new JButton("Aggiungi Contratto");
		btnAggiungiContratto.setBounds(22, 170, 139, 23);
		contentPane.add(btnAggiungiContratto);

		btnAggiungiFattura = new JButton("Aggiungi Fattura");
		btnAggiungiFattura.setBounds(248, 68, 146, 23);
		contentPane.add(btnAggiungiFattura);

		btnCercaFattura = new JButton("Cerca Fattura");
		btnCercaFattura.setBounds(248, 102, 146, 23);
		contentPane.add(btnCercaFattura);

		Logoutbtn = new JButton("LOGOUT");	
		Logoutbtn.setBounds(305, 204, 89, 23);
		contentPane.add(Logoutbtn);

		JLabel lblIdImpiegato = new JLabel("ID Impiegato :");
		lblIdImpiegato.setBounds(10, 10, 81, 14);
		contentPane.add(lblIdImpiegato);

		JLabel lblNewLabel = new JLabel(id); 
		lblNewLabel.setBounds(129, 10, 255, 14);
		contentPane.add(lblNewLabel);

		btnEliminaContratto = new JButton("Elimina Contratto");
		btnEliminaContratto.setBounds(22, 204, 139, 23);
		contentPane.add(btnEliminaContratto);

		btnCercaContratto = new JButton("Cerca Contratto");
		btnCercaContratto.setBounds(248, 34, 146, 23);
		contentPane.add(btnCercaContratto);

		btnAggiungiConsumo = new JButton("Aggiungi Consumo");
		btnAggiungiConsumo.setBounds(248, 136, 146, 23);
		contentPane.add(btnAggiungiConsumo);
	}

	////////This method contain all of code for creating events

	private void createEvents() {

		//Finestra conferma logout
		Logoutbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ret= JOptionPane.showConfirmDialog(null, "Are you sure to LOGOUT ?");
				if(ret == JOptionPane.YES_OPTION){
					try {
						genInt.logout(id);
						System.exit(0);
					} catch (RemoteException e1) {
						JOptionPane.showMessageDialog(Logoutbtn,"Errore Connessione Server");
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(Logoutbtn,"Errore Connessione Server");
					} catch (InesistenteException e1) {
						JOptionPane.showMessageDialog(Logoutbtn, e1.getMessage());
					}
				}
			}
		});

		//Finestra Aggiungi Utente
		btnAggiungiUtente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ImpiegatoAggiungiUtente aggiungiNuovoUtente = new ImpiegatoAggiungiUtente(genInt);
				aggiungiNuovoUtente.setVisible(true);
				setEnabled(false);
				aggiungiNuovoUtente.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						setVisible(true);
						setEnabled(true);
					}
				});
			}
		});
		//Finestra Modifica Utente
		btnModificaUtente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ImpiegatoModificaUtente modificaUtente = new ImpiegatoModificaUtente(genInt);
				modificaUtente.setVisible(true);
				setEnabled(false);
				modificaUtente.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						setVisible(true);
						setEnabled(true);
					}
				});
			}
		});

		//Finestra Elimina Utente
		btnEliminaUtente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ImpiegatoEliminaUtente eliminaUtente = new ImpiegatoEliminaUtente(genInt,id);
				eliminaUtente.setVisible(true);
				setEnabled(false);
				eliminaUtente.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						setVisible(true);
						setEnabled(true);
					}
				});
			}
		});

		//Finestra Aggiungi Fattura
		btnAggiungiFattura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ImpiegatoAggiungiFattura aggiungiFattura = new ImpiegatoAggiungiFattura(genInt);
				aggiungiFattura.setVisible(true);
				setEnabled(false);
				aggiungiFattura.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						setVisible(true);
						setEnabled(true);
					}
				});
			}
		});

		//Finestra Cerca Utente
		btnCercaUtente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ImpiegatoCercaUtente cercaUtente = new ImpiegatoCercaUtente(genInt);
				cercaUtente.setVisible(true);
				setEnabled(false);
				cercaUtente.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						setVisible(true);
						setEnabled(true);
					}
				});
			}
		});

		//Finestra Aggiungi Contratto
		btnAggiungiContratto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ImpiegatoAggiungiContratto aggiungiContratto = new ImpiegatoAggiungiContratto(genInt);
				aggiungiContratto.setVisible(true);
				setEnabled(false);
				aggiungiContratto.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						setVisible(true);
						setEnabled(true);
					}
				});
			}
		});

		//Finestra Cerca Fattura
		btnCercaFattura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ImpiegatoCercaFattura cercaFattura = new ImpiegatoCercaFattura(genInt);
				cercaFattura.setVisible(true);
				setEnabled(false);
				cercaFattura.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						setVisible(true);
						setEnabled(true);
					}
				});
			}
		});

		//Finestra Elimina Contratto
		btnEliminaContratto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ImpiegatoEliminaContratto eliminaContratto = new ImpiegatoEliminaContratto(genInt);
				eliminaContratto.setVisible(true);
				setEnabled(false);
				eliminaContratto.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e){
						setVisible(true);
						setEnabled(true);
					}
				});
			}
		});

		//Finestra Cerca Contratto
		btnCercaContratto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ImpiegatoCercaContratto cercaContratto = new ImpiegatoCercaContratto(genInt);
				cercaContratto.setVisible(true);
				setEnabled(false);
				cercaContratto.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e){
						setVisible(true);
						setEnabled(true);
					}
				});
			}
		});

		//Finestra Aggiungi Consumo
		btnAggiungiConsumo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ImpiegatoAggiungiConsumo aggiungiConsumo = new ImpiegatoAggiungiConsumo(genInt);
				aggiungiConsumo.setVisible(true);
				setEnabled(false);
				aggiungiConsumo.addWindowListener(new WindowAdapter() {
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
						JOptionPane.showMessageDialog(Logoutbtn,"Errore Connessione Server");
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(Logoutbtn,"Errore Connessione Server");
					} catch (InesistenteException e1) {
						JOptionPane.showMessageDialog(Logoutbtn, e1.getMessage());
					}
				}
			}
		});
	}
}
