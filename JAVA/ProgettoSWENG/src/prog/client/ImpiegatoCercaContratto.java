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
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import prog.shared.Contratto;
import prog.shared.GeneralInt;
import prog.shared.exception.CercaException;
import prog.shared.exception.ListaException;

import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class ImpiegatoCercaContratto extends JFrame {

	private static final long serialVersionUID = -2884842522853206968L;
	private JPanel contentPane;
	private JTextField IDContrattoField;
	private JTextField IDClienteField;
	private static GeneralInt genInt;
	private DefaultTableModel dtm;
	private JButton CercaIDContrattoBtn;
	private JButton CercaContrattiPerIDClientebtn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				ImpiegatoCercaContratto frame = new ImpiegatoCercaContratto(genInt);
				frame.setVisible(true);
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ImpiegatoCercaContratto(GeneralInt genInt) {
		ImpiegatoCercaContratto.genInt=genInt;
		initComponents();
		createEvents();
	}

	private void initComponents(){
		setIconImage(Toolkit.getDefaultToolkit().getImage(ImpiegatoCercaContratto.class.getResource("/resource/technology.png")));
		setResizable(false);
		setTitle("Cerca Contratto");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 513, 519);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblIdcontratto = new JLabel("IDContratto : ");
		lblIdcontratto.setBounds(10, 26, 81, 14);
		contentPane.add(lblIdcontratto);

		IDContrattoField = new JTextField();
		IDContrattoField.setBounds(88, 23, 255, 20);
		contentPane.add(IDContrattoField);
		IDContrattoField.setColumns(10);

		CercaIDContrattoBtn = new JButton("CERCA PER IDCONTRATTO");
		CercaIDContrattoBtn.setBounds(159, 54, 184, 23);
		contentPane.add(CercaIDContrattoBtn);

		JLabel lblIdcliente = new JLabel("IDCliente :");
		lblIdcliente.setBounds(10, 88, 68, 14);
		contentPane.add(lblIdcliente);

		IDClienteField = new JTextField();
		IDClienteField.setColumns(10);
		IDClienteField.setBounds(88, 85, 255, 20);
		contentPane.add(IDClienteField);

		CercaContrattiPerIDClientebtn = new JButton("CERCA PER IDCLIENTE");
		CercaContrattiPerIDClientebtn.setBounds(159, 116, 184, 23);
		contentPane.add(CercaContrattiPerIDClientebtn);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 150, 487, 329);
		contentPane.add(scrollPane);

		// create object of table and table model
		JTable tbl = new JTable(){

			private static final long serialVersionUID = -5211965502209900806L;

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
	}

	private void createEvents(){

		CercaIDContrattoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					dtm.setRowCount(0);
					int idContratto = Integer.parseInt(IDContrattoField.getText());
					Contratto contratto = genInt.cercaContratto(idContratto);
					dtm.addRow(new Object[] { contratto.getIdutente(),contratto.getIDContratto(),contratto.getDataInizio() });
				} catch (CercaException e1) {
					JOptionPane.showMessageDialog(CercaIDContrattoBtn, e1.getMessage());
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(CercaIDContrattoBtn,"IDContratto invalido.");
				} catch (RemoteException e1) {
					JOptionPane.showMessageDialog(CercaIDContrattoBtn,"Errore Connessione Server");
				}
			}
		});

		CercaContrattiPerIDClientebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					dtm.setRowCount(0);
					ArrayList<Contratto> listaContratti = genInt.cercaContrattiCliente(IDClienteField.getText());
					for (Contratto contratto : listaContratti) {
						dtm.addRow(new Object[]{ contratto.getIdutente(), contratto.getIDContratto(), contratto.getDataInizio()});
					}	
				} catch (RemoteException e) {
					JOptionPane.showMessageDialog(CercaContrattiPerIDClientebtn,"Errore Connessione Server");
				} catch (ListaException e) {
					JOptionPane.showMessageDialog(CercaContrattiPerIDClientebtn, e.getMessage());
				}
			}
		});	
	}
}
