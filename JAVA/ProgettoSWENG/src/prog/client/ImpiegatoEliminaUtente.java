package prog.client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import prog.shared.GeneralInt;
import prog.shared.exception.InesistenteException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.awt.event.ActionEvent;

public class ImpiegatoEliminaUtente extends JFrame {

	private static final long serialVersionUID = 4782992383736553389L;
	private JPanel contentPane;
	private JTextField IDField;
	private static GeneralInt genInt;
	private JButton EliminaUtentebtn;
	private static String id;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				ImpiegatoEliminaUtente frame = new ImpiegatoEliminaUtente(genInt,id);
				frame.setVisible(true);
			}
		});
	}

	/**
	 * Create the frame.
	 * @param id 
	 * 
	 */
	public ImpiegatoEliminaUtente(GeneralInt genInt, String id) {
		ImpiegatoEliminaUtente.id=id;
		ImpiegatoEliminaUtente.genInt = genInt;
		setResizable(false);
		setTitle("Elimina Utente");
		initComponents();
		createEvents();
	}
	private void initComponents(){
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 512, 158);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblInserisciIdutenteDa = new JLabel("Inserisci IDUtente da eliminare :");
		lblInserisciIdutenteDa.setBounds(10, 35, 186, 14);
		contentPane.add(lblInserisciIdutenteDa);

		IDField = new JTextField();
		IDField.setBounds(208, 32, 255, 20);
		contentPane.add(IDField);
		IDField.setColumns(10);

		EliminaUtentebtn = new JButton("ELIMINA UTENTE");
		EliminaUtentebtn.setBounds(296, 77, 167, 23);
		contentPane.add(EliminaUtentebtn);
	}

	private void createEvents(){
		EliminaUtentebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String idUtente;
					idUtente = IDField.getText();
					if(idUtente.compareTo(id)==0){
						JOptionPane.showMessageDialog(EliminaUtentebtn,"Impossibile eliminare l'utente corrente");
					}else{
						genInt.delUtente(idUtente);
						setVisible(false);
						dispose();
					}
				} catch (RemoteException e1) {
					JOptionPane.showMessageDialog(EliminaUtentebtn,"Errore Connessione Server");
				} catch (InesistenteException e1) {
					JOptionPane.showMessageDialog(EliminaUtentebtn, e1.getMessage());
				}
			}
		});
	}
}
