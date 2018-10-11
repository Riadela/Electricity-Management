package prog.client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.Toolkit;
import javax.swing.JOptionPane;
import java.rmi.RemoteException;
import java.util.ArrayList;
import prog.shared.*;
import prog.shared.exception.ListaException;

public class ClienteInfoConsumi extends JFrame {

	private static final long serialVersionUID = -4948196865421397895L;
	private JPanel contentPane;
	private static GeneralInt genInt;
	private static String id;
	private DefaultTableModel dtm;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				ClienteInfoConsumi frame = new ClienteInfoConsumi(genInt,id);
				frame.setVisible(true);
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ClienteInfoConsumi(GeneralInt genInt,String id) {
		ClienteInfoConsumi.id = id;
		ClienteInfoConsumi.genInt = genInt;
		initComponents();
		createEvents();
	}

	private void initComponents(){
		setTitle("Info Consumi");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClienteInfoConsumi.class.getResource("/resource/technology.png")));
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 414, 249);
		contentPane.add(scrollPane);

		// create object of table and table model
		JTable tbl = new JTable(){

			private static final long serialVersionUID = 1650344105624801803L;

			public boolean isCellEditable(int row, int column){
				return false;
			}
		};
		scrollPane.setViewportView(tbl);
		dtm = new DefaultTableModel(0, 4);

		// add header of the table
		String header[] = new String[] { "IDContratto", "IDCompPos", "IDComNeg", "Bilancio"};

		// add header in table model     
		dtm.setColumnIdentifiers(header);
		//set model into the table object
		tbl.setModel(dtm);

		try {
			ArrayList<Contratto> listaConsumi;
			listaConsumi = genInt.cercaConsumo(id);
			dtm.setRowCount(0);
			for (Contratto consumo : listaConsumi) {
				dtm.addRow(new Object[]{ consumo.getIDContratto(), consumo.getIDCompPos(),  consumo.getIDCompNeg(), consumo.getBilancio()});
			}
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(tbl, "Errore Connessione Server");
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
