package prog.client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import prog.shared.ImpiegatoInt;
import prog.shared.Utente;
import prog.shared.exception.CercaException;
import prog.shared.exception.InesistenteException;

import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.Date;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ImpiegatoModificaUtente extends JFrame {

	private static final long serialVersionUID = 3240977065187251000L;
	private JPanel contentPane;
	private JTextField IDField;
	private JTable table;
	private JTextField CognomeField;
	private JTextField NomeField;
	private JTextField TelefonoField;
	private JTextField EmailField;
	private JTextField PasswordField;
	private static ImpiegatoInt impInt;
	private JButton btnCercaUtente;
	private JComboBox<String> DayBox;
	private JComboBox<String> MonthBox;
	private JComboBox<String> YearBox;
	private JTextArea IndirizzoText;
	private JButton btnSalva;
	private Utente utente;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				ImpiegatoModificaUtente frame = new ImpiegatoModificaUtente(impInt);
				frame.setVisible(true);
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 */
	public ImpiegatoModificaUtente(ImpiegatoInt impInt) {
		setResizable(false);
		initComponents();
		createEvents();
		ImpiegatoModificaUtente.impInt = impInt;
	}

	private void initComponents(){
		setTitle("Modifica Utente");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ImpiegatoModificaUtente.class.getResource("/resource/technology.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblIdutente = new JLabel("IDUtente :");
		lblIdutente.setBounds(10, 27, 57, 14);
		contentPane.add(lblIdutente);

		IDField = new JTextField();
		IDField.setBounds(88, 24, 244, 20);
		contentPane.add(IDField);
		IDField.setColumns(10);

		table = new JTable();
		table.setBounds(10, 215, 221, -163);
		contentPane.add(table);

		btnCercaUtente = new JButton("CERCA");
		btnCercaUtente.setBounds(342, 23, 89, 23);
		contentPane.add(btnCercaUtente);

		JLabel lblCognome = new JLabel("Cognome :");
		lblCognome.setBounds(10, 52, 66, 14);
		contentPane.add(lblCognome);

		JLabel lblNome = new JLabel("Nome :");
		lblNome.setBounds(10, 77, 46, 14);
		contentPane.add(lblNome);

		JLabel lblDataDiNascita = new JLabel("Data di Nascita :");
		lblDataDiNascita.setBounds(10, 102, 97, 14);
		contentPane.add(lblDataDiNascita);

		JLabel lblTelefono = new JLabel("Telefono :");
		lblTelefono.setBounds(10, 127, 57, 14);
		contentPane.add(lblTelefono);

		JLabel lblEmail = new JLabel("E-Mail :");
		lblEmail.setBounds(10, 152, 46, 14);
		contentPane.add(lblEmail);

		JLabel lblPassword = new JLabel("Password :");
		lblPassword.setBounds(10, 177, 66, 14);
		contentPane.add(lblPassword);

		btnSalva = new JButton("SALVA");
		btnSalva.setEnabled(false);
		btnSalva.setBounds(342, 227, 89, 23);
		contentPane.add(btnSalva);

		CognomeField = new JTextField();
		CognomeField.setEditable(false);
		CognomeField.setBounds(88, 49, 244, 20);
		contentPane.add(CognomeField);
		CognomeField.setColumns(10);

		NomeField = new JTextField();
		NomeField.setEditable(false);
		NomeField.setColumns(10);
		NomeField.setBounds(88, 74, 244, 20);
		contentPane.add(NomeField);

		TelefonoField = new JTextField();
		TelefonoField.setEditable(false);
		TelefonoField.setColumns(10);
		TelefonoField.setBounds(88, 124, 244, 20);
		contentPane.add(TelefonoField);

		EmailField = new JTextField();
		EmailField.setEditable(false);
		EmailField.setColumns(10);
		EmailField.setBounds(88, 149, 244, 20);
		contentPane.add(EmailField);

		PasswordField = new JTextField();
		PasswordField.setEditable(false);
		PasswordField.setColumns(10);
		PasswordField.setBounds(88, 174, 244, 20);
		contentPane.add(PasswordField);

		DayBox = new JComboBox<String>();
		DayBox.setEditable(true);
		DayBox.setEnabled(false);
		DayBox.setModel(new DefaultComboBoxModel<String>(new String[] {""}));
		for(int i=1;i<32;i++){
			DayBox.addItem(new Integer(i).toString());
		}
		DayBox.removeItemAt(0);
		DayBox.setSelectedIndex(0);
		DayBox.setBounds(127, 99, 46, 20);
		contentPane.add(DayBox);

		MonthBox = new JComboBox<String>();
		MonthBox.setEditable(true);
		MonthBox.setEnabled(false);
		MonthBox.setModel(new DefaultComboBoxModel<String>(new String[] {""}));
		for(int i=1;i<13;i++){
			MonthBox.addItem(new Integer(i).toString());
		}
		MonthBox.removeItemAt(0);
		MonthBox.setSelectedIndex(0);
		MonthBox.setBounds(185, 99, 46, 20);
		contentPane.add(MonthBox);

		YearBox = new JComboBox<String>();
		YearBox.setEditable(true);
		YearBox.setEnabled(false);
		YearBox.setModel(new DefaultComboBoxModel<String>(new String[] {""}));
		for(int i=1900;i<2017;i++){
			YearBox.addItem(new Integer(i).toString());
		}
		YearBox.removeItemAt(0);
		YearBox.setSelectedIndex(0);
		YearBox.setBounds(243, 99, 53, 20);
		contentPane.add(YearBox);

		JLabel lblIndirizzo = new JLabel("Indirizzo :");
		lblIndirizzo.setBounds(10, 202, 57, 14);
		contentPane.add(lblIndirizzo);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(88, 205, 244, 42);
		contentPane.add(scrollPane);

		IndirizzoText = new JTextArea();
		IndirizzoText.setEnabled(false);
		scrollPane.setViewportView(IndirizzoText);
	}

	private void createEvents(){

		//Ricerca tramite idUtente
		btnCercaUtente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					utente = impInt.cercaUtenteId(IDField.getText());
					CognomeField.setText(utente.getCognome());
					NomeField.setText(utente.getNome());
					TelefonoField.setText(utente.getNumTelefono());
					EmailField.setText(utente.getEmail());
					PasswordField.setText(utente.getPassword());
					IndirizzoText.setText(utente.getIndirizzo());

					Date datan = utente.getDataDiNascita();
					String datas = datan.toString();
					String dayn="";
					String monthn="";
					String yearn="";
					boolean d=false;
					boolean m=false;
					boolean y=true;
					for(int i=0;i<datas.length();i++){
						char lettera=datas.charAt(i);
						if(lettera!='-'&&y==true){
							yearn=yearn+lettera;
						}else if(lettera=='-'&&y==true){
							y=false;
							m=true;
						}else if(lettera!='-'&&m==true){
							monthn=monthn+lettera;
						}else if(lettera=='-'&&m==true){
							m=false;
							d=true;
						}else if(lettera!='-'&&d==true){
							dayn=dayn+lettera;
						}
					}
					DayBox.setSelectedItem(dayn);
					MonthBox.setSelectedItem(monthn);
					YearBox.setSelectedItem(yearn);

					//Blocco idUtente e sblocco la modifica sugli altri
					IDField.setEditable(false);
					PasswordField.setEditable(true);
					EmailField.setEditable(true);
					TelefonoField.setEditable(true);
					CognomeField.setEditable(true);
					NomeField.setEditable(true);
					DayBox.setEnabled(true);
					MonthBox.setEnabled(true);
					YearBox.setEnabled(true);
					IndirizzoText.setEnabled(true);
					btnSalva.setEnabled(true);
				} catch (RemoteException e1) {
					JOptionPane.showMessageDialog(btnCercaUtente,"Errore Connessione Server");
				} catch (CercaException e1) {
					JOptionPane.showMessageDialog(btnCercaUtente, e1.getMessage());
				}
			}
		});

		//Salva modifiche
		btnSalva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					utente.setPassword(PasswordField.getText());
					utente.setEmail(EmailField.getText());
					utente.setNumTelefono(TelefonoField.getText());
					utente.setCognome(CognomeField.getText());
					utente.setNome(NomeField.getText());
					utente.setIndirizzo(IndirizzoText.getText());

					String year = (String)YearBox.getSelectedItem();
					String month = (String)MonthBox.getSelectedItem();
					String day = (String)DayBox.getSelectedItem();
					String data = new String(year+"-"+month+"-"+day);
					Date datan = java.sql.Date.valueOf(data);
					utente.setDataDiNascita(datan);
					impInt.modificaUtente(utente);

					setVisible(false);
					dispose();
				} catch (RemoteException e1) {
					JOptionPane.showMessageDialog(btnSalva,"Errore Connessione Server");
				} catch (InesistenteException e1) {
					JOptionPane.showMessageDialog(btnSalva, e1.getMessage());
				}
			}
		});	
	}
}
