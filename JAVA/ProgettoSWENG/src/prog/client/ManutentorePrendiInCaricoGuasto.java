package prog.client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import prog.shared.GeneralInt;
import prog.shared.exception.InesistenteException;
import prog.shared.exception.PrivilegiInsufficientiException;

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

public class ManutentorePrendiInCaricoGuasto extends JFrame {

	private static final long serialVersionUID = 2143051367017039648L;
	private JPanel contentPane;
	private JTextField idGuastoField;
	private JComboBox<String> DayBox;
	private JComboBox<String> monthBox;
	private JComboBox<String> yearBox;
	private JTextArea dettagliArea;
	private JButton PrendiInCaricoBtn;
	public static GeneralInt genInt;
	private static String id;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				ManutentorePrendiInCaricoGuasto frame = new ManutentorePrendiInCaricoGuasto(genInt,id);
				frame.setVisible(true);
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ManutentorePrendiInCaricoGuasto(GeneralInt genInt, String id) {
		ManutentorePrendiInCaricoGuasto.id=id;
		ManutentorePrendiInCaricoGuasto.genInt = genInt;
		initComponents();
		createEvents();
	}

	private void initComponents(){
		setIconImage(Toolkit.getDefaultToolkit().getImage(ManutentorePrendiInCaricoGuasto.class.getResource("/resource/technology.png")));
		setTitle("Prendi In Carico Guasto");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblIdguasto = new JLabel("IDGuasto :");
		lblIdguasto.setBounds(10, 21, 80, 14);
		contentPane.add(lblIdguasto);

		JLabel lblData = new JLabel("Data :");
		lblData.setBounds(10, 48, 80, 14);
		contentPane.add(lblData);

		JLabel lblDettagli = new JLabel("Dettagli :");
		lblDettagli.setBounds(10, 75, 80, 14);
		contentPane.add(lblDettagli);

		idGuastoField = new JTextField();
		idGuastoField.setBounds(100, 18, 255, 20);
		contentPane.add(idGuastoField);
		idGuastoField.setColumns(10);

		DayBox = new JComboBox<String>();
		DayBox.setModel(new DefaultComboBoxModel<String>(new String[] {""}));
		for(int i=1;i<32;i++){
			DayBox.addItem(new Integer(i).toString());
		}
		DayBox.removeItemAt(0);
		DayBox.setSelectedIndex(0);
		DayBox.setBounds(102, 45, 46, 20);
		contentPane.add(DayBox);

		monthBox = new JComboBox<String>();
		monthBox.setModel(new DefaultComboBoxModel<String>(new String[] {""}));
		for(int i=1;i<13;i++){
			monthBox.addItem(new Integer(i).toString());
		}
		monthBox.removeItemAt(0);
		monthBox.setSelectedIndex(0);
		monthBox.setBounds(160, 45, 53, 20);
		contentPane.add(monthBox);

		yearBox = new JComboBox<String>();
		yearBox.setModel(new DefaultComboBoxModel<String>(new String[] {""}));
		for(int i=1900;i<2018;i++){
			yearBox.addItem(new Integer(i).toString());
		}
		yearBox.removeItemAt(0);
		yearBox.setSelectedIndex(0);
		yearBox.setBounds(225, 45, 53, 20);
		contentPane.add(yearBox);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(100, 75, 255, 146);
		contentPane.add(scrollPane);

		dettagliArea = new JTextArea();
		scrollPane.setViewportView(dettagliArea);

		PrendiInCaricoBtn = new JButton("PRENDI IN CARICO GUASTO");
		PrendiInCaricoBtn.setBounds(167, 237, 188, 23);
		contentPane.add(PrendiInCaricoBtn);
	}

	private void createEvents(){

		PrendiInCaricoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					int idGuasto = Integer.parseInt(idGuastoField.getText());
					String dettagli = dettagliArea.getText();
					String year = (String)yearBox.getSelectedItem();
					String month = (String)monthBox.getSelectedItem();
					String day = (String)DayBox.getSelectedItem();
					String data = new String(year+"-"+month+"-"+day);
					Date datap = java.sql.Date.valueOf(data);

					genInt.workOnGuasto(idGuasto, id, dettagli, datap);
					setVisible(false);
					dispose();
				} catch (RemoteException e1) {
					JOptionPane.showMessageDialog(PrendiInCaricoBtn,"Errore Connessione Server");
				} catch (InesistenteException e1) {
					JOptionPane.showMessageDialog(PrendiInCaricoBtn, e1.getMessage());
				} catch (PrivilegiInsufficientiException e1) {
					JOptionPane.showMessageDialog(PrendiInCaricoBtn, e1.getMessage());
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(PrendiInCaricoBtn, "IDGuasto deve essere un valore numerico");
				}
			}
		});
	}
}
