package prog.client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import prog.shared.Fattura;
import prog.shared.GeneralInt;
import prog.shared.StatoFattura;
import prog.shared.exception.InesistenteException;

import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.Date;
import java.awt.event.ActionEvent;

public class ImpiegatoAggiungiFattura extends JFrame {

	private static final long serialVersionUID = -5967186615061704407L;
	private JPanel contentPane;
	private JTextField IDFatturaField;
	private JTextField ImportoField;
	private JTextField IDContrattoField;
	private JComboBox<String> DayBox;
	private JComboBox<String> MonthBox;
	private JComboBox<String> YearBox;
	private JComboBox<Object> StatoFatturaBox;
	private JTextArea DettagliArea;
	private JButton AggiungiFatturaBtn;
	private static GeneralInt genInt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				ImpiegatoAggiungiFattura frame = new ImpiegatoAggiungiFattura(genInt);
				frame.setVisible(true);
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 */
	public ImpiegatoAggiungiFattura(GeneralInt genInt) {
		ImpiegatoAggiungiFattura.genInt=genInt;
		initComponents();
		createEvents();
	}

	private void initComponents(){
		setResizable(false);
		setTitle("Aggiungi Fattura");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ImpiegatoAggiungiFattura.class.getResource("/resource/technology.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblIdfattura = new JLabel("IDFattura :");
		lblIdfattura.setBounds(10, 22, 67, 14);
		contentPane.add(lblIdfattura);

		IDFatturaField = new JTextField();
		IDFatturaField.setBounds(120, 19, 255, 20);
		contentPane.add(IDFatturaField);
		IDFatturaField.setColumns(10);

		JLabel lblDataEmissione = new JLabel("Data Emissione :");
		lblDataEmissione.setBounds(10, 72, 98, 14);
		contentPane.add(lblDataEmissione);

		DayBox = new JComboBox<String>();
		DayBox.setEditable(true);
		DayBox.setModel(new DefaultComboBoxModel<String>(new String[] {""}));
		for(int i=1;i<32;i++){
			DayBox.addItem(new Integer(i).toString());
		}
		DayBox.removeItemAt(0);
		DayBox.setSelectedIndex(0);
		DayBox.setBounds(120, 69, 46, 20);
		contentPane.add(DayBox);

		MonthBox = new JComboBox<String>();
		MonthBox.setEditable(true);
		MonthBox.setModel(new DefaultComboBoxModel<String>(new String[] {""}));
		for(int i=1;i<13;i++){
			MonthBox.addItem(new Integer(i).toString());
		}
		MonthBox.removeItemAt(0);
		MonthBox.setSelectedIndex(0);
		MonthBox.setBounds(178, 69, 53, 20);
		contentPane.add(MonthBox);

		YearBox = new JComboBox<String>();
		YearBox.setEditable(true);
		YearBox.setModel(new DefaultComboBoxModel<String>(new String[] {""}));
		for(int i=1900;i<2018;i++){
			YearBox.addItem(new Integer(i).toString());
		}
		YearBox.removeItemAt(0);
		YearBox.setSelectedIndex(0);
		YearBox.setBounds(241, 69, 53, 20);
		contentPane.add(YearBox);

		JLabel lblImporto = new JLabel("Importo :");
		lblImporto.setBounds(10, 97, 67, 14);
		contentPane.add(lblImporto);

		ImportoField = new JTextField();
		ImportoField.setBounds(120, 94, 86, 20);
		contentPane.add(ImportoField);
		ImportoField.setColumns(10);

		JLabel lblIdcontratto = new JLabel("IDContratto :");
		lblIdcontratto.setBounds(10, 47, 79, 14);
		contentPane.add(lblIdcontratto);

		IDContrattoField = new JTextField();
		IDContrattoField.setBounds(120, 44, 255, 20);
		contentPane.add(IDContrattoField);
		IDContrattoField.setColumns(10);

		JLabel lblStatoFattura = new JLabel("Stato Fattura :");
		lblStatoFattura.setBounds(10, 122, 82, 14);
		contentPane.add(lblStatoFattura);

		StatoFatturaBox = new JComboBox<Object>();	
		StatoFatturaBox.setModel(new DefaultComboBoxModel<Object>(StatoFattura.values()));
		StatoFatturaBox.setBounds(120, 119, 79, 20);
		contentPane.add(StatoFatturaBox);

		JLabel lblDettagli = new JLabel("Dettagli :");
		lblDettagli.setBounds(10, 154, 67, 14);
		contentPane.add(lblDettagli);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(120, 152, 255, 74);
		contentPane.add(scrollPane);

		DettagliArea = new JTextArea();
		scrollPane.setViewportView(DettagliArea);

		AggiungiFatturaBtn = new JButton("AGGIUNGI FATTURA");
		AggiungiFatturaBtn.setBounds(232, 235, 143, 23);
		contentPane.add(AggiungiFatturaBtn);
	}

	private void createEvents(){

		AggiungiFatturaBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					Fattura fattura = new Fattura();
					int idFattura = Integer.parseInt(IDFatturaField.getText());
					float importo = Float.parseFloat(ImportoField.getText());
					String dettagli = DettagliArea.getText();				
					StatoFattura statoFattura = StatoFattura.valueOf(StatoFatturaBox.getSelectedItem().toString());
					int idContratto = Integer.parseInt(IDContrattoField.getText());
					String year = (String)YearBox.getSelectedItem();
					String month = (String)MonthBox.getSelectedItem();
					String day = (String)DayBox.getSelectedItem();
					String data = new String(year+"-"+month+"-"+day);
					Date datae = java.sql.Date.valueOf(data);

					fattura.setIDFattura(idFattura);
					fattura.setImporto(importo);
					fattura.setDett(dettagli);
					fattura.setStatoFattura(statoFattura);
					fattura.setIDContratto(idContratto);
					fattura.setDataEmissione(datae);

					genInt.addFattura(fattura);
					setVisible(false);
					dispose();
				} catch (RemoteException e) {
					JOptionPane.showMessageDialog(AggiungiFatturaBtn,"Errore Connessione Server");
				} catch (InesistenteException e) {
					JOptionPane.showMessageDialog(AggiungiFatturaBtn, e.getMessage());
				} catch (NumberFormatException e){
					JOptionPane.showMessageDialog(AggiungiFatturaBtn,"IDFattura, Importo e IDcontratti devono essere valori numerici");
				}

			}
		});
	}
}