package prog.client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import prog.shared.GeneralInt;
import prog.shared.Guasto;
import prog.shared.exception.ListaException;

import javax.swing.JScrollPane;

public class ClienteVisualizzaGuasti extends JFrame {

	private static final long serialVersionUID = 7091791038242427453L;
	private JPanel contentPane;
	private static GeneralInt genInt;
	private DefaultTableModel dtm;
	private static String id;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				ClienteVisualizzaGuasti frame = new ClienteVisualizzaGuasti(genInt,id);
				frame.setVisible(true);
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ClienteVisualizzaGuasti(GeneralInt genInt, String id) {
		ClienteVisualizzaGuasti.id=id;
		ClienteVisualizzaGuasti.genInt = genInt;
		initComponents();
		createEvents();
	}

	private void initComponents(){
		setResizable(false);
		setTitle("Visualizza Guasti");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClienteVisualizzaGuasti.class.getResource("/resource/technology.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 721, 446);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblIdutente = new JLabel("IDUtente :");
		lblIdutente.setBounds(10, 22, 74, 14);
		contentPane.add(lblIdutente);

		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(96, 22, 255, 14);
		contentPane.add(lblNewLabel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 45, 695, 361);
		contentPane.add(scrollPane);

		// create object of table and table model
		JTable tbl = new JTable(){

			private static final long serialVersionUID = -3539974990764495542L;

			public boolean isCellEditable(int row, int column){
				return false;
			}
		};
		scrollPane.setViewportView(tbl);
		dtm = new DefaultTableModel(0, 7);

		// add header of the table
		String header[] = new String[] { "IDGuasto", "Stato Guasto", "Data Segnalazione", "Data Incarico", "Data Riparazione",
				"Dettagli","IDComponente"};

		// add header in table model     
		dtm.setColumnIdentifiers(header);
		//set model into the table object
		tbl.setModel(dtm);

		try {
			ArrayList<Guasto> listaGuasti;
			listaGuasti = genInt.visualGuasti(id);
			dtm.setRowCount(0);
			for (Guasto guasto : listaGuasti) {
				dtm.addRow(new Object[]{ guasto.getIDGuasto(),guasto.getStatoGuasto(),guasto.getDataSegnalazione(),guasto.getDataIncarico(),
						guasto.getDataRiparazione(),guasto.getDettagli(),guasto.getIDComponente()});
			}
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(tbl,"Errore Connessione Server");
			setVisible(false);
			dispose();
		} catch (ListaException e) {
			JOptionPane.showMessageDialog(tbl, e.getMessage());
			setVisible(false);
			dispose();
		}
	}

	private void createEvents(){

	}
}
