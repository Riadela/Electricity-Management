package prog.client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import prog.shared.GeneralInt;
import prog.shared.Privilegio;
import prog.shared.Utente;
import prog.shared.exception.CercaException;
import prog.shared.exception.ListaException;

import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class ImpiegatoCercaUtente extends JFrame {

	private static final long serialVersionUID = 4691842250865508662L;
	private JPanel contentPane;
	private JTextField IDField;
	private JTextField EmailField;
	private JTextField TelefonoField;
	private static GeneralInt genInt;
	private JButton CercaIDUtentebtn;
	private DefaultTableModel dtm;
	private JButton CercaEmailbtn;
	private JButton CercaTelefonobtn;
	private JButton Privilegiobtn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				ImpiegatoCercaUtente frame = new ImpiegatoCercaUtente(genInt);
				frame.setVisible(true);
			}
		});
	}

	/**
	 * Create the frame.
	 *  
	 */
	public ImpiegatoCercaUtente(GeneralInt genInt) {
		ImpiegatoCercaUtente.genInt=genInt;
		initComponents();
		createEvents();
	}

	private void initComponents(){
		setIconImage(Toolkit.getDefaultToolkit().getImage(ImpiegatoCercaUtente.class.getResource("/resource/technology.png")));
		setTitle("Cerca Utente");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 890, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblIdutente = new JLabel("IDUtente :");
		lblIdutente.setBounds(10, 31, 68, 14);
		contentPane.add(lblIdutente);

		IDField = new JTextField();
		IDField.setBounds(88, 28, 255, 20);
		contentPane.add(IDField);
		IDField.setColumns(10);

		JLabel lblNewLabel = new JLabel("E-Mail :");
		lblNewLabel.setBounds(390, 31, 46, 14);
		contentPane.add(lblNewLabel);

		EmailField = new JTextField();
		EmailField.setColumns(10);
		EmailField.setBounds(446, 28, 255, 20);
		contentPane.add(EmailField);

		CercaIDUtentebtn = new JButton("CERCA IDUTENTE");
		CercaIDUtentebtn.setBounds(201, 59, 142, 23);
		contentPane.add(CercaIDUtentebtn);

		CercaEmailbtn = new JButton("CERCA E-MAIL");
		CercaEmailbtn.setBounds(571, 59, 130, 23);
		contentPane.add(CercaEmailbtn);

		TelefonoField = new JTextField();
		TelefonoField.setColumns(10);
		TelefonoField.setBounds(88, 93, 255, 20);
		contentPane.add(TelefonoField);

		JLabel lblNewLabel_1 = new JLabel("Telefono :");
		lblNewLabel_1.setBounds(10, 96, 60, 14);
		contentPane.add(lblNewLabel_1);

		CercaTelefonobtn = new JButton("CERCA TELEFONO");
		CercaTelefonobtn.setBounds(201, 124, 142, 23);
		contentPane.add(CercaTelefonobtn);

		Privilegiobtn = new JButton("MOSTRA CLIENTI");
		Privilegiobtn.setBounds(571, 95, 130, 23);
		contentPane.add(Privilegiobtn);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 158, 864, 402);
		contentPane.add(scrollPane);

		// create object of table and table model
		JTable tbl = new JTable(){

			private static final long serialVersionUID = 4117367299536925382L;

			public boolean isCellEditable(int row, int column){
				return false;
			}
		};
		scrollPane.setViewportView(tbl);
		dtm = new DefaultTableModel(0, 8);

		// add header of the table
		String header[] = new String[] { "IDUtente", "Cognome", "Nome",
				"Data Di Nascita", "Indirizzo", "E-Mail", "Telefono", "Privilegio" };

		// add header in table model     
		dtm.setColumnIdentifiers(header);
		//set model into the table object
		tbl.setModel(dtm);

	}

	private void createEvents(){

		CercaIDUtentebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					dtm.setRowCount(0);
					Utente utente = genInt.cercaUtenteId(IDField.getText());
					dtm.addRow(new Object[] { utente.getIDUtente(), utente.getCognome(), utente.getNome(),
							utente.getDataDiNascita(), utente.getIndirizzo(), utente.getEmail(), utente.getNumTelefono(), utente.getPrivilegio() });					
				} catch (RemoteException e1) {
					JOptionPane.showMessageDialog(CercaIDUtentebtn,"Errore Connessione Server");
				} catch (CercaException e1) {
					JOptionPane.showMessageDialog(CercaIDUtentebtn, e1.getMessage());
				}
			}
		});

		CercaEmailbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					dtm.setRowCount(0);
					ArrayList<Utente> listaUtenti = genInt.cercaUtenteMail(EmailField.getText());
					for (Utente utente : listaUtenti) {
						dtm.addRow(new Object[]{ utente.getIDUtente(), utente.getCognome(), utente.getNome(), utente.getDataDiNascita(),
								utente.getIndirizzo(), utente.getEmail(), utente.getNumTelefono(), utente.getPrivilegio()});
					}			
				} catch (RemoteException e1) {
					JOptionPane.showMessageDialog(CercaEmailbtn,"Errore Connessione Server");
				} catch (CercaException e1) {
					JOptionPane.showMessageDialog(CercaEmailbtn, e1.getMessage());
				}
			}
		});

		CercaTelefonobtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					dtm.setRowCount(0);
					ArrayList<Utente> listaUtenti = genInt.cercaUtenteTel(TelefonoField.getText());
					for (Utente utente : listaUtenti) {
						dtm.addRow(new Object[]{ utente.getIDUtente(), utente.getCognome(), utente.getNome(), utente.getDataDiNascita(),
								utente.getIndirizzo(), utente.getEmail(), utente.getNumTelefono(), utente.getPrivilegio()});
					}			
				} catch (RemoteException e1) {
					JOptionPane.showMessageDialog(CercaTelefonobtn,"Errore Connessione Server");
				} catch (ListaException e1) {
					JOptionPane.showMessageDialog(CercaTelefonobtn, e1.getMessage());
				}
			}
		});

		Privilegiobtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					dtm.setRowCount(0);
					ArrayList<Utente> listaUtenti = genInt.listaClienti(Privilegio.CL);
					for (Utente utente : listaUtenti) {
						dtm.addRow(new Object[]{ utente.getIDUtente(), utente.getCognome(), utente.getNome(), utente.getDataDiNascita(),
								utente.getIndirizzo(), utente.getEmail(), utente.getNumTelefono(), utente.getPrivilegio()});
					}			
				} catch (RemoteException e1) {
					JOptionPane.showMessageDialog(Privilegiobtn,"Errore Connessione Server");
				} catch (ListaException e1) {
					JOptionPane.showMessageDialog(Privilegiobtn, e1.getMessage());
				}
			}
		});	
	}
}
