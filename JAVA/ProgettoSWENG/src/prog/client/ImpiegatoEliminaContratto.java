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

public class ImpiegatoEliminaContratto extends JFrame {

	private static final long serialVersionUID = -4355711279166046006L;
	private JPanel contentPane;
	private JTextField IDContrattoField;
	private static GeneralInt genInt;
	private JButton EliminaContrattoBtn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				ImpiegatoEliminaContratto frame = new ImpiegatoEliminaContratto(genInt);
				frame.setVisible(true);
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 */
	public ImpiegatoEliminaContratto(GeneralInt genInt) {
		ImpiegatoEliminaContratto.genInt = genInt;
		initComponents();
		createEvents();
	}

	private void initComponents(){
		setTitle("Elimina Contratto");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 151);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblIdcontratto = new JLabel("IDContratto :");
		lblIdcontratto.setBounds(10, 28, 77, 14);
		contentPane.add(lblIdcontratto);

		IDContrattoField = new JTextField();
		IDContrattoField.setBounds(99, 25, 255, 20);
		contentPane.add(IDContrattoField);
		IDContrattoField.setColumns(10);

		EliminaContrattoBtn = new JButton("ELIMINA CONTRATTO");
		EliminaContrattoBtn.setBounds(200, 65, 154, 23);
		contentPane.add(EliminaContrattoBtn);
	}

	private void createEvents(){

		EliminaContrattoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					genInt.delContratto(Integer.parseInt(IDContrattoField.getText()));
					setVisible(false);
					dispose();
				} catch (InesistenteException e1) {
					JOptionPane.showMessageDialog(EliminaContrattoBtn, e1.getMessage());
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(EliminaContrattoBtn,"IDUtente invalido");
				} catch (RemoteException e1) {
					JOptionPane.showMessageDialog(EliminaContrattoBtn, "Errore Connessione Server");
				}
			}
		});
	}
}
