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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import prog.shared.Guasto;
import prog.shared.GeneralInt;
import prog.shared.StatoGuasto;
import prog.shared.exception.ListaException;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class ManutentoreVisualizzaElencoGuasti extends JFrame {

	private static final long serialVersionUID = 6293340631529911422L;
	private JPanel contentPane;
	private JComboBox<Object> TipoGuastoBox;
	private JLabel IDManutentoreLabel;
	private JButton CercaGuastiPerTipoBtn;
	private JButton CercaGuastiPerIDBtn;
	public static GeneralInt genInt;
	private static String id;
	private DefaultTableModel dtm;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				ManutentoreVisualizzaElencoGuasti frame = new ManutentoreVisualizzaElencoGuasti(genInt,id);
				frame.setVisible(true);
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ManutentoreVisualizzaElencoGuasti(GeneralInt genInt,String id) {
		ManutentoreVisualizzaElencoGuasti.genInt = genInt;
		ManutentoreVisualizzaElencoGuasti.id=id;
		initComponents();
		createEvents();
	}

	private void initComponents(){
		setTitle("Visualizza Elenco Guasti");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ManutentoreVisualizzaElencoGuasti.class.getResource("/resource/technology.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 588);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTipoGuasto = new JLabel("Tipo Guasto :");
		lblTipoGuasto.setBounds(10, 11, 80, 14);
		contentPane.add(lblTipoGuasto);

		TipoGuastoBox = new JComboBox<Object>();
		TipoGuastoBox.setModel(new DefaultComboBoxModel<Object>(StatoGuasto.values()));
		TipoGuastoBox.setBounds(100, 8, 109, 20);
		contentPane.add(TipoGuastoBox);

		CercaGuastiPerTipoBtn = new JButton("CERCA GUASTI PER TIPO");
		CercaGuastiPerTipoBtn.setBounds(245, 7, 180, 23);
		contentPane.add(CercaGuastiPerTipoBtn);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 71, 764, 467);
		contentPane.add(scrollPane);


		// create object of table and table model
		JTable tbl = new JTable(){

			private static final long serialVersionUID = 6791896884730530211L;

			public boolean isCellEditable(int row, int column){
				return false;
			}
		};
		scrollPane.setViewportView(tbl);
		dtm = new DefaultTableModel(0, 8);

		// add header of the table
		String header[] = new String[] { "IDGuasto", "IDUtente", "IDComponente", "IDManutentore", 
				"Dettagli", "Data Segnalazione", "Data Incarico", "Data Riparazione"};

		// add header in table model     
		dtm.setColumnIdentifiers(header);
		//set model into the table object
		tbl.setModel(dtm);

		JLabel lblIdmanutentore = new JLabel("IDManutentore :");
		lblIdmanutentore.setBounds(10, 40, 95, 14);
		contentPane.add(lblIdmanutentore);

		IDManutentoreLabel = new JLabel(id);
		IDManutentoreLabel.setBounds(145, 40, 64, 14);
		contentPane.add(IDManutentoreLabel);

		CercaGuastiPerIDBtn = new JButton("CERCA GUASTI PER ID");
		CercaGuastiPerIDBtn.setBounds(245, 35, 180, 23);
		contentPane.add(CercaGuastiPerIDBtn);
	}

	private void createEvents(){

		CercaGuastiPerTipoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					dtm.setRowCount(0);
					StatoGuasto stato = StatoGuasto.valueOf(TipoGuastoBox.getSelectedItem().toString());
					ArrayList<Guasto> listaGuasti = new ArrayList<Guasto>();
					listaGuasti = genInt.getElencoGuasti(stato);
					for (Guasto guasto : listaGuasti) 
						dtm.addRow(new Object[]{guasto.getIDGuasto(), guasto.getIDCliente(), guasto.getIDComponente(), guasto.getIDManutentore(),
								guasto.getDettagli(), guasto.getDataSegnalazione(), guasto.getDataIncarico(), guasto.getDataRiparazione()});
				} catch (RemoteException e1) {
					JOptionPane.showMessageDialog(CercaGuastiPerTipoBtn,"Errore Connessione Server");
				} catch (ListaException e1) {
					JOptionPane.showMessageDialog(CercaGuastiPerTipoBtn, e1.getMessage());
				}		
			}
		});

		CercaGuastiPerIDBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					dtm.setRowCount(0);
					ArrayList<Guasto> listaGuasti = new ArrayList<Guasto>();
					listaGuasti = genInt.getElencoGuastiMan(id);
					for (Guasto guasto : listaGuasti) 
						dtm.addRow(new Object[]{guasto.getIDGuasto(), guasto.getIDCliente(), guasto.getIDComponente(), guasto.getIDManutentore(),
								guasto.getDettagli(), guasto.getDataSegnalazione(), guasto.getDataIncarico(), guasto.getDataRiparazione()});
				} catch (RemoteException e1) {
					JOptionPane.showMessageDialog(CercaGuastiPerIDBtn,"Errore Connessione Server");
				} catch (ListaException e1) {
					JOptionPane.showMessageDialog(CercaGuastiPerIDBtn, e1.getMessage());
				}
			}
		});		
	}
}
