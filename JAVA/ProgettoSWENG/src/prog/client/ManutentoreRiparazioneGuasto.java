package prog.client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import prog.shared.GeneralInt;
import prog.shared.exception.InesistenteException;

import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.Date;
import java.awt.event.ActionEvent;

public class ManutentoreRiparazioneGuasto extends JFrame {

	private static final long serialVersionUID = 7202950184163624410L;
	private JPanel contentPane;
	private JTextField idGuastoField;
	public static GeneralInt genInt;
	private JComboBox<String> dayBox;
	private JComboBox<String> monthBox;
	private JComboBox<String> yearBox;
	private JTextArea dettagliArea;
	private JButton RegistraRiparazioneBtn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				ManutentoreRiparazioneGuasto frame = new ManutentoreRiparazioneGuasto(genInt);
				frame.setVisible(true);
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ManutentoreRiparazioneGuasto(GeneralInt genInt) {
		ManutentoreRiparazioneGuasto.genInt = genInt;
		initComponents();
		createEvents();
	}

	private void initComponents(){
		setTitle("Registra Riparazione");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ManutentoreRiparazioneGuasto.class.getResource("/resource/technology.png")));
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 399, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblIdguasto = new JLabel("IDGuasto :");
		lblIdguasto.setBounds(10, 21, 63, 14);
		contentPane.add(lblIdguasto);

		JLabel lblNewLabel = new JLabel("Data :");
		lblNewLabel.setBounds(10, 46, 52, 14);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Dettagli :");
		lblNewLabel_1.setBounds(10, 71, 52, 14);
		contentPane.add(lblNewLabel_1);

		idGuastoField = new JTextField();
		idGuastoField.setBounds(72, 18, 255, 20);
		contentPane.add(idGuastoField);
		idGuastoField.setColumns(10);

		dayBox = new JComboBox<String>();
		dayBox.setModel(new DefaultComboBoxModel<String>(new String[] {""}));
		for(int i=1;i<32;i++){
			dayBox.addItem(new Integer(i).toString());
		}
		dayBox.removeItemAt(0);
		dayBox.setSelectedIndex(0);
		dayBox.setBounds(72, 43, 46, 20);
		contentPane.add(dayBox);

		monthBox = new JComboBox<String>();
		monthBox.setModel(new DefaultComboBoxModel<String>(new String[] {""}));
		for(int i=1;i<13;i++){
			monthBox.addItem(new Integer(i).toString());
		}
		monthBox.removeItemAt(0);
		monthBox.setSelectedIndex(0);
		monthBox.setBounds(128, 43, 46, 20);
		contentPane.add(monthBox);

		yearBox = new JComboBox<String>();
		yearBox.setModel(new DefaultComboBoxModel<String>(new String[] {""}));
		for(int i=1900;i<2018;i++){
			yearBox.addItem(new Integer(i).toString());
		}
		yearBox.removeItemAt(0);
		yearBox.setSelectedIndex(0);
		yearBox.setBounds(186, 43, 53, 20);
		contentPane.add(yearBox);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(72, 71, 255, 148);
		contentPane.add(scrollPane);

		dettagliArea = new JTextArea();
		scrollPane.setViewportView(dettagliArea);

		RegistraRiparazioneBtn = new JButton("REGISTRA RIPARAZIONE");
		RegistraRiparazioneBtn.setBounds(156, 237, 171, 23);
		contentPane.add(RegistraRiparazioneBtn);
	}

	private void createEvents(){

		RegistraRiparazioneBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					int idGuasto = Integer.parseInt(idGuastoField.getText());
					String dettagli = dettagliArea.getText();
					String year = (String)yearBox.getSelectedItem();
					String month = (String)monthBox.getSelectedItem();
					String day = (String)dayBox.getSelectedItem();
					String data = new String(year+"-"+month+"-"+day);
					Date datar = java.sql.Date.valueOf(data);
					genInt.ripGuasto(idGuasto, dettagli, datar);

					setVisible(false);
					dispose();
				} catch (RemoteException e1) {
					JOptionPane.showMessageDialog(RegistraRiparazioneBtn,"Errore Connessione Server");
				} catch (InesistenteException e1) {
					JOptionPane.showMessageDialog(RegistraRiparazioneBtn, e1.getMessage());
				} catch ( NumberFormatException e1){
					JOptionPane.showMessageDialog(RegistraRiparazioneBtn,"IDGuasto deve essere un valore numerico");
				}
			}
		});
	}
}
