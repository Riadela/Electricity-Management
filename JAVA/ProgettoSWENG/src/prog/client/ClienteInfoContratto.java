package prog.client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import prog.shared.Contratto;
import prog.shared.GeneralInt;
import prog.shared.exception.ListaException;

public class ClienteInfoContratto extends JFrame {

	private static final long serialVersionUID = -5133397674163060217L;
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
				ClienteInfoContratto frame = new ClienteInfoContratto(genInt,id);
				frame.setVisible(true);
			}
		});
	}

	/**
	 * Create the frame.
	 * @param id 
	 */
	public ClienteInfoContratto(GeneralInt genInt, String id) {
		ClienteInfoContratto.id=id;
		ClienteInfoContratto.genInt = genInt;
		initComponents();
		createEvents();
	}

	private void initComponents(){
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClienteInfoContratto.class.getResource("/resource/technology.png")));
		setTitle("Info Contratto");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 424, 249);
		contentPane.add(scrollPane);

		// create object of table and table model
		JTable tbl = new JTable(){

			private static final long serialVersionUID = -86864953526437159L;

			public boolean isCellEditable(int row, int column){
				return false;
			}
		};
		scrollPane.setViewportView(tbl);
		dtm = new DefaultTableModel(0, 3);

		// add header of the table
		String header[] = new String[] { "IDUtente", "IDContratto", "Data Inizio"};

		// add header in table model     
		dtm.setColumnIdentifiers(header);
		//set model into the table object
		tbl.setModel(dtm);

		try {
			ArrayList<Contratto> listaContratti;
			listaContratti = genInt.cercaContrattiCliente(id);
			dtm.setRowCount(0);
			for (Contratto contratto : listaContratti) {
				dtm.addRow(new Object[]{ contratto.getIdutente(), contratto.getIDContratto(), contratto.getDataInizio()});
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
