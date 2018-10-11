package prog.client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import prog.shared.GeneralInt;
import prog.shared.Privilegio;
import prog.shared.exception.UtenteEsistenteException;

import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.Date;
import java.awt.event.ActionEvent;

public class ImpiegatoAggiungiUtente extends JFrame {

	private static final long serialVersionUID = -4978989492121444971L;
	private JPanel contentPane;
	private JTextField CognomeField;
	private JTextField NomeField;
	private JTextField EmailField;
	private JTextField TelefonoField;
	private JTextField IDUtenteField;
	private JTextField PasswordField;
	private JButton RegistraUtentebtn;
	private static GeneralInt genInt;
	private JTextArea IndirizzoText;
	private JComboBox<Object> PrivilegioBox;
	private JComboBox<String> YearBox;
	private JComboBox<String> DayBox;
	private JComboBox<String> MonthBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				ImpiegatoAggiungiUtente frame = new ImpiegatoAggiungiUtente(genInt);
				frame.setVisible(true);
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ImpiegatoAggiungiUtente(GeneralInt genInt) {
		initComponents();
		createEvents();
		ImpiegatoAggiungiUtente.genInt = genInt;
	}

	private void initComponents(){
		setResizable(false);
		setTitle("Aggiungi Utente");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ImpiegatoAggiungiUtente.class.getResource("/resource/technology.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 375);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Cognome :");
		lblNewLabel.setBounds(10, 20, 72, 14);
		contentPane.add(lblNewLabel);

		CognomeField = new JTextField();
		CognomeField.setBounds(130, 17, 255, 20);
		contentPane.add(CognomeField);
		CognomeField.setColumns(10);

		JLabel lblNome = new JLabel("Nome :");
		lblNome.setBounds(10, 45, 46, 14);
		contentPane.add(lblNome);

		NomeField = new JTextField();
		NomeField.setBounds(130, 42, 255, 20);
		contentPane.add(NomeField);
		NomeField.setColumns(10);

		JLabel lblDataDiNascita = new JLabel("Data Di Nascita :");
		lblDataDiNascita.setBounds(10, 70, 98, 14);
		contentPane.add(lblDataDiNascita);

		JLabel lblEmail = new JLabel("E-mail :");
		lblEmail.setBounds(10, 95, 46, 14);
		contentPane.add(lblEmail);

		EmailField = new JTextField();
		EmailField.setBounds(130, 92, 255, 20);
		contentPane.add(EmailField);
		EmailField.setColumns(10);

		JLabel lblTelefono = new JLabel("Telefono :");
		lblTelefono.setBounds(10, 120, 59, 14);
		contentPane.add(lblTelefono);

		TelefonoField = new JTextField();
		TelefonoField.setBounds(130, 117, 255, 20);
		contentPane.add(TelefonoField);
		TelefonoField.setColumns(10);

		JLabel lblIndirizzo = new JLabel("Indirizzo :");
		lblIndirizzo.setBounds(10, 145, 59, 14);
		contentPane.add(lblIndirizzo);

		JLabel lblId = new JLabel("IDUtente :");
		lblId.setBounds(10, 193, 59, 14);
		contentPane.add(lblId);

		IDUtenteField = new JTextField();
		IDUtenteField.setBounds(130, 190, 255, 20);
		contentPane.add(IDUtenteField);
		IDUtenteField.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Password : ");
		lblNewLabel_1.setBounds(10, 224, 72, 14);
		contentPane.add(lblNewLabel_1);

		PasswordField = new JTextField();
		PasswordField.setBounds(130, 222, 255, 20);
		contentPane.add(PasswordField);
		PasswordField.setColumns(10);

		JLabel lblPrivilegio = new JLabel("Privilegio :");
		lblPrivilegio.setBounds(10, 255, 59, 14);
		contentPane.add(lblPrivilegio);

		PrivilegioBox = new JComboBox<Object>();
		PrivilegioBox.setModel(new DefaultComboBoxModel<Object>(Privilegio.values()));
		PrivilegioBox.setBounds(130, 254, 175, 20);
		contentPane.add(PrivilegioBox);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(130, 145, 255, 37);
		contentPane.add(scrollPane);

		IndirizzoText = new JTextArea();
		IndirizzoText.setFont(new Font("Tahoma", Font.PLAIN, 11));
		scrollPane.setViewportView(IndirizzoText);

		RegistraUtentebtn = new JButton("REGISTRA UTENTE");
		RegistraUtentebtn.setBounds(247, 286, 138, 23);
		contentPane.add(RegistraUtentebtn);

		DayBox = new JComboBox<String>();
		DayBox.setModel(new DefaultComboBoxModel<String>(new String[] {""}));
		for(int i=1;i<32;i++){
			DayBox.addItem(new Integer(i).toString());
		}
		DayBox.removeItemAt(0);
		DayBox.setSelectedIndex(0);
		DayBox.setBounds(130, 67, 46, 20);
		contentPane.add(DayBox);

		MonthBox = new JComboBox<String>();
		MonthBox.setModel(new DefaultComboBoxModel<String>(new String[] {""}));
		for(int i=1;i<13;i++){
			MonthBox.addItem(new Integer(i).toString());
		}
		MonthBox.setBounds(193, 67, 53, 20);
		MonthBox.removeItemAt(0);
		MonthBox.setSelectedIndex(0);
		contentPane.add(MonthBox);

		YearBox = new JComboBox<String>();	
		YearBox.setModel(new DefaultComboBoxModel<String>(new String[] {""}));
		for(int i=1900;i<2017;i++){
			YearBox.addItem(new Integer(i).toString());
		}
		YearBox.removeItemAt(0);
		YearBox.setSelectedIndex(0);
		YearBox.setBounds(263, 67, 53, 20);
		contentPane.add(YearBox);
	}

	private void createEvents(){

		//registra utente
		RegistraUtentebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					String idut = IDUtenteField.getText();
					String numtel = TelefonoField.getText();
					String passw = PasswordField.getText();
					String email = EmailField.getText();
					String cogn = CognomeField.getText();
					String nome = NomeField.getText();
					String indi = IndirizzoText.getText();

					Privilegio priv = Privilegio.valueOf(PrivilegioBox.getSelectedItem().toString());

					String year = (String)YearBox.getSelectedItem();
					String month = (String)MonthBox.getSelectedItem();
					String day = (String)DayBox.getSelectedItem();
					String data = new String(year+"-"+month+"-"+day);
					Date datan = java.sql.Date.valueOf(data);
					if((idut.compareTo("")==0)){
						JOptionPane.showMessageDialog(RegistraUtentebtn,"IDUtente non valido");
					}else{
						genInt.addUtente(idut, passw, email, numtel, cogn, nome, datan, indi, priv);
					}

					setVisible(false);
					dispose();
				} catch (RemoteException e1) {
					JOptionPane.showMessageDialog(RegistraUtentebtn,"Errore Connessione Server");
				} catch (UtenteEsistenteException e1) {
					JOptionPane.showMessageDialog(RegistraUtentebtn, e1.getMessage());
				}
			}
		});
	}
}
