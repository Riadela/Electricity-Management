package prog.client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import prog.shared.GeneralInt;
import prog.shared.exception.InesistenteException;

import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.awt.event.ActionEvent;

public class ManutentoreEliminaComponente extends JFrame {

	private static final long serialVersionUID = 875952353340632729L;
	private JPanel contentPane;
	private JTextField IdComponenteField;
	private JButton EliminaComponenteField;
	private static GeneralInt genInt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				ManutentoreEliminaComponente frame = new ManutentoreEliminaComponente(genInt);
				frame.setVisible(true);
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ManutentoreEliminaComponente(GeneralInt genInt) {
		ManutentoreEliminaComponente.genInt= genInt;
		initComponents();
		createEvents();
	}

	private void initComponents(){
		setTitle("Elimina Componente");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ManutentoreEliminaComponente.class.getResource("/resource/technology.png")));
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 128);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblIdcomponente = new JLabel("IDComponente :");
		lblIdcomponente.setBounds(10, 25, 95, 14);
		contentPane.add(lblIdcomponente);

		IdComponenteField = new JTextField();
		IdComponenteField.setBounds(117, 22, 255, 20);
		contentPane.add(IdComponenteField);
		IdComponenteField.setColumns(10);

		EliminaComponenteField = new JButton("ELIMINA COMPONENTE");
		EliminaComponenteField.setBounds(208, 53, 164, 23);
		contentPane.add(EliminaComponenteField);
	}

	private void createEvents(){

		EliminaComponenteField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					int idComponente = Integer.parseInt(IdComponenteField.getText());
					genInt.delComponente(idComponente);
					setVisible(false);
					dispose();
				} catch (RemoteException e1) {
					JOptionPane.showMessageDialog(EliminaComponenteField,"Errore Connessione Server");
				} catch (InesistenteException e1) {
					JOptionPane.showMessageDialog(EliminaComponenteField, e1.getMessage());
				}catch (NumberFormatException e1){
					JOptionPane.showMessageDialog(EliminaComponenteField, "IDComponente deve essere un valore numerico");
				}
			}
		});	
	}
}
