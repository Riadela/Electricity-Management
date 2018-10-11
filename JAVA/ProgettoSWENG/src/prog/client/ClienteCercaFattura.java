package prog.client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import prog.shared.Fattura;
import prog.shared.GeneralInt;
import prog.shared.StatoFattura;
import prog.shared.exception.CercaException;
import prog.shared.exception.GiaPagataException;
import prog.shared.exception.InesistenteException;

import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class ClienteCercaFattura extends JFrame {

	private static final long serialVersionUID = 6031804112218327923L;
	private JPanel contentPane;
	private JButton CercaBtn;
	private static GeneralInt genInt;
	private DefaultTableModel dtm;
	private JComboBox<Object> StatoFattureBox;
	private JTable tbl;
	private static String id;
	private ArrayList<Fattura> listaFatturaCopia;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				ClienteCercaFattura frame = new ClienteCercaFattura(genInt,id);
				frame.setVisible(true);
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ClienteCercaFattura(GeneralInt genInt,String idUtente) {
		ClienteCercaFattura.id=idUtente;
		ClienteCercaFattura.genInt=genInt;
		initComponents();
		createEvents();
	}

	private void initComponents(){
		setResizable(false);
		setTitle("Cerca Fattura");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClienteCercaFattura.class.getResource("/resource/technology.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 504, 347);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblStatoFattureCercate = new JLabel("Stato Fatture Cercate :");
		lblStatoFattureCercate.setBounds(10, 11, 132, 14);
		contentPane.add(lblStatoFattureCercate);

		StatoFattureBox = new JComboBox<Object>();
		StatoFattureBox.setModel(new DefaultComboBoxModel<Object>(StatoFattura.values()));
		StatoFattureBox.setBounds(154, 8, 89, 20);
		contentPane.add(StatoFattureBox);

		CercaBtn = new JButton("CERCA");
		CercaBtn.setBounds(271, 7, 89, 23);
		contentPane.add(CercaBtn);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 61, 476, 245);
		contentPane.add(scrollPane);

		// create object of table and table model
		tbl = new JTable(){

			private static final long serialVersionUID = 3152209258193165107L;

			public boolean isCellEditable(int row, int column){
				return false;
			}
		};
		scrollPane.setViewportView(tbl);
		dtm = new DefaultTableModel(0, 5);

		// add header of the table
		String header[] = new String[] { "IDFattura", "IDContratto", "Data Emissione", "Importo", "Dettagli"};

		// add header in table model     
		dtm.setColumnIdentifiers(header);
		//set model into the table object
		tbl.setModel(dtm);

		JLabel lblNewLabel = new JLabel("Per effettuare il pagamento di una fattura selezionare la riga corrispondente");
		lblNewLabel.setBounds(10, 37, 476, 16);
		contentPane.add(lblNewLabel);

	}

	private void createEvents(){

		listaFatturaCopia= new ArrayList<Fattura>();

		//Cerca Fattura
		CercaBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					dtm.setRowCount(0);
					StatoFattura stato = StatoFattura.valueOf(StatoFattureBox.getSelectedItem().toString());
					ArrayList<Fattura> listaFattura;
					listaFattura = genInt.cercaFtt(stato,id);
					//Creo una copia dell'arraylist per poter pagare selezionando la linea
					listaFatturaCopia=listaFattura;				
					for (Fattura fattura : listaFattura) {
						dtm.addRow(new Object[]{ fattura.getIDFattura(), fattura.getIDContratto(), fattura.getDataEmissione(),
								fattura.getImporto(), fattura.getDett()});
					}
				} catch (RemoteException e) {
					JOptionPane.showMessageDialog(CercaBtn, "Errore Connessione Server");
				} catch (CercaException e) {
					JOptionPane.showMessageDialog(tbl, e.getMessage());
				} catch (InesistenteException e) {	
					JOptionPane.showMessageDialog(tbl, e.getMessage());
				}
			}
		});

		//Paga Fattura
		tbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tbl.getSelectedRow();
				Fattura fattura = listaFatturaCopia.get((row));
				String mess = "Pagare fattura : "+ fattura.getIDFattura()+"?";
				int ret= JOptionPane.showConfirmDialog(null, mess);
				if(ret == JOptionPane.YES_OPTION){
					try {
						genInt.pagaFtt(fattura.getIDFattura());
						JOptionPane.showMessageDialog(tbl, "Fattura "+fattura.getIDFattura()+" pagata");
					} catch (RemoteException e1) {
						JOptionPane.showMessageDialog(tbl, "Errore Connessione Server");
					} catch (CercaException e1) {
						JOptionPane.showMessageDialog(tbl, e1.getMessage());
					} catch (GiaPagataException e1) {
						JOptionPane.showMessageDialog(tbl, e1.getMessage());
					}
				}
			}
		});

	}
}
