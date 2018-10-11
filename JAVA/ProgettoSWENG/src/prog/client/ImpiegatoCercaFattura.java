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
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import prog.shared.Fattura;
import prog.shared.GeneralInt;
import prog.shared.StatoFattura;
import prog.shared.exception.CercaException;
import prog.shared.exception.InesistenteException;
import prog.shared.exception.ListaException;

import javax.swing.JScrollPane;

public class ImpiegatoCercaFattura extends JFrame {

	private static final long serialVersionUID = 7468843637924100348L;
	private JPanel contentPane;
	private JTextField IDContrattoField;
	private JTextField IDFatturaField;
	private static GeneralInt genInt;
	private DefaultTableModel dtm;
	private JButton CercaidfatturaBtn;
	private JButton CercaIDContrattoBtn;
	private JComboBox<Object> StatoFatturaBox;
	private JButton CercaStatoFatturaBtn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				ImpiegatoCercaFattura frame = new ImpiegatoCercaFattura(genInt);
				frame.setVisible(true);
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ImpiegatoCercaFattura(GeneralInt genInt) {
		ImpiegatoCercaFattura.genInt = genInt;
		initComponents();
		createEvents();
	}

	private void initComponents(){
		setResizable(false);
		setTitle("Cerca Fattura");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ImpiegatoCercaFattura.class.getResource("/resource/technology.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 765, 564);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblIdcontratto = new JLabel("IDContratto :");
		lblIdcontratto.setBounds(10, 23, 72, 14);
		contentPane.add(lblIdcontratto);

		IDContrattoField = new JTextField();
		IDContrattoField.setBounds(116, 20, 255, 20);
		contentPane.add(IDContrattoField);
		IDContrattoField.setColumns(10);

		CercaIDContrattoBtn = new JButton("CERCA IDCONTRATTO");
		CercaIDContrattoBtn.setBounds(201, 51, 170, 23);
		contentPane.add(CercaIDContrattoBtn);

		JLabel lblIdfattura = new JLabel("IDFattura :");
		lblIdfattura.setBounds(374, 23, 72, 14);
		contentPane.add(lblIdfattura);

		IDFatturaField = new JTextField();
		IDFatturaField.setColumns(10);
		IDFatturaField.setBounds(456, 20, 255, 20);
		contentPane.add(IDFatturaField);

		CercaidfatturaBtn = new JButton("CERCAIDFATTURA");
		CercaidfatturaBtn.setBounds(541, 51, 170, 23);
		contentPane.add(CercaidfatturaBtn);

		JLabel lblStatoFattura = new JLabel("Stato Fattura :");
		lblStatoFattura.setBounds(10, 85, 94, 14);
		contentPane.add(lblStatoFattura);

		StatoFatturaBox = new JComboBox<Object>();
		StatoFatturaBox.setModel(new DefaultComboBoxModel<Object>(StatoFattura.values()));
		StatoFatturaBox.setBounds(116, 82, 87, 20);
		contentPane.add(StatoFatturaBox);

		CercaStatoFatturaBtn = new JButton("CERCA STATO FATTURA");
		CercaStatoFatturaBtn.setBounds(201, 111, 170, 23);
		contentPane.add(CercaStatoFatturaBtn);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 147, 739, 377);
		contentPane.add(scrollPane);

		// create object of table and table model
		JTable tbl = new JTable(){

			private static final long serialVersionUID = 4578742188027525790L;

			public boolean isCellEditable(int row, int column){
				return false;
			}
		};
		scrollPane.setViewportView(tbl);
		dtm = new DefaultTableModel(0, 6);

		// add header of the table
		String header[] = new String[] { "IDContratto", "IDFattura", "Importo",
				"Stato Fattura", "Dettagli", "Data Emissione"};

		// add header in table model     
		dtm.setColumnIdentifiers(header);
		//set model into the table object
		tbl.setModel(dtm);
	}

	private void createEvents(){

		CercaidfatturaBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					dtm.setRowCount(0);
					int idFattura = Integer.parseInt(IDFatturaField.getText());
					Fattura fattura = genInt.cercaFtt(idFattura);
					dtm.addRow(new Object[] { fattura.getIDContratto(), fattura.getIDFattura(), fattura.getImporto(),
							fattura.getStatoFattura(), fattura.getDett(), fattura.getDataEmissione()});	
				} catch (RemoteException e) {
					JOptionPane.showMessageDialog(CercaidfatturaBtn,"Errore Connessione Server");
				} catch (CercaException e) {
					JOptionPane.showMessageDialog(CercaidfatturaBtn, e.getMessage());
				} catch (NumberFormatException e){
					JOptionPane.showMessageDialog(CercaidfatturaBtn,"IDFattura deve essere un valore numerico");
				}
			}
		});

		CercaIDContrattoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					dtm.setRowCount(0);
					int idContratto = Integer.parseInt(IDContrattoField.getText());
					ArrayList<Fattura> listaFatture = genInt.cercaFttCliente(idContratto);
					for (Fattura fattura : listaFatture) {
						dtm.addRow(new Object[]{ fattura.getIDContratto(), fattura.getIDFattura(), fattura.getImporto(),
								fattura.getStatoFattura(), fattura.getDett(), fattura.getDataEmissione()});
					}	
				} catch (RemoteException e1) {
					JOptionPane.showMessageDialog(CercaIDContrattoBtn,"Errore Connessione Server");
				} catch (ListaException e1) {
					JOptionPane.showMessageDialog(CercaIDContrattoBtn, e1.getMessage());
				} catch (InesistenteException e1) {
					JOptionPane.showMessageDialog(CercaIDContrattoBtn, e1.getMessage());
				}catch (NumberFormatException e1){
					JOptionPane.showMessageDialog(CercaIDContrattoBtn,"IDContratto deve essere un valore numerico");
				}
			}
		});

		CercaStatoFatturaBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					dtm.setRowCount(0);
					StatoFattura statoFattura = StatoFattura.valueOf(StatoFatturaBox.getSelectedItem().toString());
					ArrayList<Fattura> listaFatture;
					listaFatture = genInt.cercaFtt(statoFattura);
					for (Fattura fattura : listaFatture) {
						dtm.addRow(new Object[]{ fattura.getIDContratto(), fattura.getIDFattura(), fattura.getImporto(),
								fattura.getStatoFattura(), fattura.getDett(), fattura.getDataEmissione()});
					}
				} catch (RemoteException e1) {
					JOptionPane.showMessageDialog(CercaStatoFatturaBtn,"Errore Connessione Server");
				} catch (CercaException e1) {
					JOptionPane.showMessageDialog(CercaStatoFatturaBtn, e1.getMessage());
				}	
			}
		});
	}
}
