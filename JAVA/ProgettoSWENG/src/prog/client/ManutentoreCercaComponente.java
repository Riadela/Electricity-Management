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
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import prog.shared.Componente;
import prog.shared.GeneralInt;
import prog.shared.Posizione;
import prog.shared.Tipo;
import prog.shared.exception.CercaException;
import prog.shared.exception.ListaException;

import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class ManutentoreCercaComponente extends JFrame {

	private static final long serialVersionUID = 5211242194954225333L;
	private JPanel contentPane;
	private JTextField IDComponenteField;
	private JButton CercaIDbtn;
	private JButton CercaTipobtn;
	private JComboBox<Object> tipoBox;
	private DefaultTableModel dtm;
	private static GeneralInt genInt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				ManutentoreCercaComponente frame = new ManutentoreCercaComponente(genInt);
				frame.setVisible(true);
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ManutentoreCercaComponente(GeneralInt genInt) {
		ManutentoreCercaComponente.genInt = genInt;
		initComponents();
		createEvents();
	}

	private void initComponents(){
		setResizable(false);
		setTitle("Cerca Componente");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ManutentoreCercaComponente.class.getResource("/resource/technology.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 377);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblIdcomponente = new JLabel("IDComponente :");
		lblIdcomponente.setBounds(10, 24, 91, 14);
		contentPane.add(lblIdcomponente);

		IDComponenteField = new JTextField();
		IDComponenteField.setBounds(137, 21, 255, 20);
		contentPane.add(IDComponenteField);
		IDComponenteField.setColumns(10);

		CercaIDbtn = new JButton("CERCA TRAMITE ID");
		CercaIDbtn.setBounds(225, 47, 167, 23);
		contentPane.add(CercaIDbtn);

		JLabel lblTipoComponente = new JLabel("Tipo Componente :");
		lblTipoComponente.setBounds(10, 86, 115, 14);
		contentPane.add(lblTipoComponente);

		tipoBox = new JComboBox<Object>();
		tipoBox.setModel(new DefaultComboBoxModel<Object>(Tipo.values()));
		tipoBox.setBounds(137, 83, 137, 20);
		contentPane.add(tipoBox);

		CercaTipobtn = new JButton("CERCA TRAMITE TIPO");
		CercaTipobtn.setBounds(225, 114, 167, 23);
		contentPane.add(CercaTipobtn);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 145, 424, 192);
		contentPane.add(scrollPane);

		// create object of table and table model
		JTable tbl = new JTable(){

			private static final long serialVersionUID = 4426307270073470911L;

			public boolean isCellEditable(int row, int column){
				return false;
			}
		};
		scrollPane.setViewportView(tbl);
		dtm = new DefaultTableModel(0, 3);

		// add header of the table
		String header[] = new String[] { "IDComponente", "Valore Energetico", "Posizione"};

		// add header in table model     
		dtm.setColumnIdentifiers(header);
		//set model into the table object
		tbl.setModel(dtm);

	}

	private void createEvents(){

		CercaIDbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					dtm.setRowCount(0);
					Componente componente;
					int idComponente = Integer.parseInt(IDComponenteField.getText());
					componente = genInt.cercaComp(idComponente);
					Posizione posizione =componente.getPosizione();
					String lati = posizione.getLatitudine();
					String longi = posizione.getLongitudine();
					dtm.addRow(new Object[] { componente.getIDComponente(), componente.getValoreEnerg(), lati+longi });

				} catch (CercaException e1) {
					JOptionPane.showMessageDialog(CercaIDbtn, e1.getMessage());
				} catch (RemoteException e1) {
					JOptionPane.showMessageDialog(CercaIDbtn,"Errore Connessione Server");
				} catch (NumberFormatException e1){
					JOptionPane.showMessageDialog(CercaIDbtn, "IDComponente deve essere un valore numerico");
				}
			}
		});

		CercaTipobtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					dtm.setRowCount(0);
					Tipo tipo=Tipo.valueOf(tipoBox.getSelectedItem().toString());
					ArrayList<Componente> listaComponenti = new ArrayList<Componente>();
					listaComponenti = genInt.cercaElencoComp(tipo);
					for (Componente componente : listaComponenti) {
						Posizione posizione =componente.getPosizione();
						String lati = posizione.getLatitudine();
						String longi = posizione.getLongitudine();
						dtm.addRow(new Object[]{ componente.getIDComponente(), componente.getValoreEnerg(), lati+" , "+longi});
					}
				} catch (RemoteException e1) {
					JOptionPane.showMessageDialog(CercaTipobtn,"Errore Connessione Server");
				} catch (ListaException e1) {
					JOptionPane.showMessageDialog(CercaTipobtn, e1.getMessage());
				}
			}
		});
	}	
}
