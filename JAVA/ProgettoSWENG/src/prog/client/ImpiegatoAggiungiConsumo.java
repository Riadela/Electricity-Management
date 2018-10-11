package prog.client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import prog.shared.GeneralInt;
import prog.shared.exception.CercaException;
import prog.shared.exception.InesistenteException;

import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.awt.event.ActionEvent;

public class ImpiegatoAggiungiConsumo extends JFrame {

	private static final long serialVersionUID = -3885944070372673041L;
	private JPanel contentPane;
	private JTextField IDContrattoField;
	private JButton AggiungiConsumoBtn;
	private static GeneralInt genInt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				ImpiegatoAggiungiConsumo frame = new ImpiegatoAggiungiConsumo(genInt);
				frame.setVisible(true);
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ImpiegatoAggiungiConsumo(GeneralInt genInt) {
		ImpiegatoAggiungiConsumo.genInt = genInt;
		initComponents();
		createEvents();
	}

	private void initComponents(){
		setTitle("Aggiungi Consumo");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ImpiegatoAggiungiConsumo.class.getResource("/resource/technology.png")));
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 148);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblIdcontratto = new JLabel("IDContratto :");
		lblIdcontratto.setBounds(10, 22, 82, 14);
		contentPane.add(lblIdcontratto);

		IDContrattoField = new JTextField();
		IDContrattoField.setBounds(104, 19, 255, 20);
		contentPane.add(IDContrattoField);
		IDContrattoField.setColumns(10);

		AggiungiConsumoBtn = new JButton("AGGIUNGI CONSUMO");
		AggiungiConsumoBtn.setBounds(203, 60, 156, 23);
		contentPane.add(AggiungiConsumoBtn);
	}

	private void createEvents(){

		AggiungiConsumoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					int idContratto = Integer.parseInt(IDContrattoField.getText());
					genInt.addConsumo(idContratto);
					setVisible(false);
					dispose();
				} catch (RemoteException e1) {
					JOptionPane.showMessageDialog(AggiungiConsumoBtn,"Errore Connessione Server");
				} catch (CercaException e1) {
					JOptionPane.showMessageDialog(AggiungiConsumoBtn, e1.getMessage());
				} catch (InesistenteException e1) {
					JOptionPane.showMessageDialog(AggiungiConsumoBtn, e1.getMessage());
				} catch (NumberFormatException e1){
					JOptionPane.showMessageDialog(AggiungiConsumoBtn,"IDContratto deve essere un valore numerico");
				}
			}
		});
	}
}
