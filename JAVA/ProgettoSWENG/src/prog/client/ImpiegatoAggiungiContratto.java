package prog.client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import prog.shared.GeneralInt;
import prog.shared.exception.CercaException;
import prog.shared.exception.ComponenteNonTuoException;
import prog.shared.exception.InesistenteException;

import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.Date;
import java.awt.event.ActionEvent;

public class ImpiegatoAggiungiContratto extends JFrame {

	private static final long serialVersionUID = -7641761246763109230L;
	private static GeneralInt genInt;
	private JPanel contentPane;
	private JTextField IDUtenteField;
	private JButton AggiungiContrattoBtn;
	private JComboBox<String> DayBox;
	private JComboBox<String> MonthBox;
	private JComboBox<String> YearBox;
	private JTextField IDCompPosField;
	private JTextField IDCompNegField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				ImpiegatoAggiungiContratto frame = new ImpiegatoAggiungiContratto(genInt);
				frame.setVisible(true);
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ImpiegatoAggiungiContratto(GeneralInt genInt) {
		initComponents();
		createEvents();
		ImpiegatoAggiungiContratto.genInt = genInt;
	}

	private void initComponents(){
		setIconImage(Toolkit.getDefaultToolkit().getImage(ImpiegatoAggiungiContratto.class.getResource("/resource/technology.png")));
		setTitle("Aggiungi Contratto");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 222);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblIdutente = new JLabel("IDUtente :");
		lblIdutente.setBounds(10, 23, 90, 14);
		contentPane.add(lblIdutente);

		IDUtenteField = new JTextField();
		IDUtenteField.setBounds(137, 20, 255, 20);
		contentPane.add(IDUtenteField);
		IDUtenteField.setColumns(10);

		JLabel lblDataStipulazione = new JLabel("Data Stipulazione :");
		lblDataStipulazione.setBounds(10, 56, 115, 14);
		contentPane.add(lblDataStipulazione);

		DayBox = new JComboBox<String>();
		DayBox.setModel(new DefaultComboBoxModel<String>(new String[] {""}));
		for(int i=1;i<32;i++){
			DayBox.addItem(new Integer(i).toString());
		}
		DayBox.removeItemAt(0);
		DayBox.setSelectedIndex(0);
		DayBox.setBounds(137, 53, 46, 20);
		contentPane.add(DayBox);

		MonthBox = new JComboBox<String>();
		MonthBox.setModel(new DefaultComboBoxModel<String>(new String[] {""}));
		for(int i=1;i<13;i++){
			MonthBox.addItem(new Integer(i).toString());
		}
		MonthBox.removeItemAt(0);
		MonthBox.setSelectedIndex(0);
		MonthBox.setBounds(195, 53, 53, 20);
		contentPane.add(MonthBox);

		YearBox = new JComboBox<String>();
		YearBox.setModel(new DefaultComboBoxModel<String>(new String[] {""}));
		for(int i=1900;i<2018;i++){
			YearBox.addItem(new Integer(i).toString());
		}
		YearBox.removeItemAt(0);
		YearBox.setSelectedIndex(0);
		YearBox.setBounds(258, 53, 53, 20);
		contentPane.add(YearBox);

		AggiungiContrattoBtn = new JButton("AGGIUNGI CONTRATTO");
		AggiungiContrattoBtn.setBounds(230, 153, 162, 23);
		contentPane.add(AggiungiContrattoBtn);

		JLabel lblIdcomppos = new JLabel("IDCompPos :");
		lblIdcomppos.setBounds(10, 82, 72, 16);
		contentPane.add(lblIdcomppos);

		JLabel lblIdcompneg = new JLabel("IDCompNeg :");
		lblIdcompneg.setBounds(10, 110, 72, 16);
		contentPane.add(lblIdcompneg);

		IDCompPosField = new JTextField();
		IDCompPosField.setBounds(137, 80, 255, 20);
		contentPane.add(IDCompPosField);
		IDCompPosField.setColumns(10);

		IDCompNegField = new JTextField();
		IDCompNegField.setColumns(10);
		IDCompNegField.setBounds(137, 108, 255, 20);
		contentPane.add(IDCompNegField);
	}

	private void createEvents(){

		AggiungiContrattoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String idUtente = IDUtenteField.getText();

					String compPos = IDCompPosField.getText();
					String compNeg = IDCompNegField.getText();
					int idCompPos = Integer.parseInt(compPos);		
					int idCompNeg = Integer.parseInt(compNeg);

					String year = (String)YearBox.getSelectedItem();
					String month = (String)MonthBox.getSelectedItem();
					String day = (String)DayBox.getSelectedItem();
					String data = new String(year+"-"+month+"-"+day);
					Date datan = java.sql.Date.valueOf(data);

					genInt.addContratto(idUtente, datan, idCompPos, idCompNeg);
					setVisible(false);
					dispose();
				} catch (RemoteException e1) {
					JOptionPane.showMessageDialog(AggiungiContrattoBtn,"Errore Connessione Server");
				} catch (InesistenteException e1) {
					JOptionPane.showMessageDialog(AggiungiContrattoBtn, e1.getMessage());
				} catch (CercaException e1) {
					JOptionPane.showMessageDialog(AggiungiContrattoBtn, e1.getMessage());
				} catch (ComponenteNonTuoException e1) {
					JOptionPane.showMessageDialog(AggiungiContrattoBtn, e1.getMessage());
				}catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(AggiungiContrattoBtn, "IDCompPos e IDCompNeg devono essere valori numerici. \n"
							+ "Inserire almeno un componente positivo. Se non si dispone di un componente negativo, inserire '0'.   ");
				}
			}
		});

	}
}
