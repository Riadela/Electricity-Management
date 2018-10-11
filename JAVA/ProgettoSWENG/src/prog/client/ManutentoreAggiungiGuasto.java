package prog.client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import prog.shared.GeneralInt;
import prog.shared.exception.CercaException;
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

public class ManutentoreAggiungiGuasto extends JFrame {

	private static final long serialVersionUID = 4744283890590357950L;
	private JPanel contentPane;
	private JTextField IDComponenteField;
	private JTextField IDUtenteField;
	public static GeneralInt genInt;
	private JComboBox<String> DayBox;
	private JComboBox<String> MonthBox;
	private JComboBox<String> YearBox;
	private JTextArea DettagliArea;
	private JButton AggiungiGuastoBtn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				ManutentoreAggiungiGuasto frame = new ManutentoreAggiungiGuasto(genInt);
				frame.setVisible(true);
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ManutentoreAggiungiGuasto(GeneralInt genInt) {
		ManutentoreAggiungiGuasto.genInt = genInt;
		initComponents();
		createEvents();
	}

	private void initComponents(){
		setIconImage(Toolkit.getDefaultToolkit().getImage(ManutentoreAggiungiGuasto.class.getResource("/resource/technology.png")));
		setTitle("Aggiungi Guasto");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblIdUtente = new JLabel("IDUtente :");
		lblIdUtente.setBounds(10, 25, 58, 14);
		contentPane.add(lblIdUtente);

		JLabel lblIdcomponente = new JLabel("IDComponente :");
		lblIdcomponente.setBounds(10, 50, 89, 14);
		contentPane.add(lblIdcomponente);

		JLabel lblData = new JLabel("Data :");
		lblData.setBounds(10, 75, 46, 14);
		contentPane.add(lblData);

		JLabel lblDettagli = new JLabel("Dettagli :");
		lblDettagli.setBounds(10, 100, 58, 14);
		contentPane.add(lblDettagli);

		IDComponenteField = new JTextField();
		IDComponenteField.setBounds(111, 47, 255, 20);
		contentPane.add(IDComponenteField);
		IDComponenteField.setColumns(10);

		IDUtenteField = new JTextField();
		IDUtenteField.setColumns(10);
		IDUtenteField.setBounds(111, 22, 255, 20);
		contentPane.add(IDUtenteField);

		DayBox = new JComboBox<String>();
		DayBox.setModel(new DefaultComboBoxModel<String>(new String[] {""}));
		for(int i=1;i<32;i++){
			DayBox.addItem(new Integer(i).toString());
		}
		DayBox.removeItemAt(0);
		DayBox.setSelectedIndex(0);
		DayBox.setBounds(111, 72, 46, 20);
		contentPane.add(DayBox);

		MonthBox = new JComboBox<String>();
		MonthBox.setModel(new DefaultComboBoxModel<String>(new String[] {""}));
		for(int i=1;i<13;i++){
			MonthBox.addItem(new Integer(i).toString());
		}
		MonthBox.removeItemAt(0);
		MonthBox.setSelectedIndex(0);
		MonthBox.setBounds(169, 72, 53, 20);
		contentPane.add(MonthBox);

		YearBox = new JComboBox<String>();
		YearBox.setModel(new DefaultComboBoxModel<String>(new String[] {""}));
		for(int i=1900;i<2018;i++){
			YearBox.addItem(new Integer(i).toString());
		}
		YearBox.removeItemAt(0);
		YearBox.setSelectedIndex(0);
		YearBox.setBounds(234, 72, 53, 20);
		contentPane.add(YearBox);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(111, 100, 255, 119);
		contentPane.add(scrollPane);

		DettagliArea = new JTextArea();
		scrollPane.setViewportView(DettagliArea);

		AggiungiGuastoBtn = new JButton("AGGIUNGI GUASTO");
		AggiungiGuastoBtn.setBounds(227, 230, 139, 23);
		contentPane.add(AggiungiGuastoBtn);
	}

	private void createEvents(){

		AggiungiGuastoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					String idUtente = IDUtenteField.getText();
					int idComponente = Integer.parseInt(IDComponenteField.getText());
					String dettagli = DettagliArea.getText();

					String year = (String)YearBox.getSelectedItem();
					String month = (String)MonthBox.getSelectedItem();
					String day = (String)DayBox.getSelectedItem();
					String data = new String(year+"-"+month+"-"+day);
					Date datas = java.sql.Date.valueOf(data);
					genInt.addGuasto(dettagli, idComponente, idUtente, datas);

					setVisible(false);
					dispose();
				} catch (RemoteException e1) {
					JOptionPane.showMessageDialog(AggiungiGuastoBtn,"Errore Connessione Server");
				} catch (InesistenteException e1) {
					JOptionPane.showMessageDialog(AggiungiGuastoBtn, e1.getMessage());
				} catch (CercaException e1) {
					JOptionPane.showMessageDialog(AggiungiGuastoBtn, e1.getMessage());
				}catch (NumberFormatException e1){
					JOptionPane.showMessageDialog(AggiungiGuastoBtn, "IDComponente deve essere un valore numerico");
				}
			}
		});	
	}
}
