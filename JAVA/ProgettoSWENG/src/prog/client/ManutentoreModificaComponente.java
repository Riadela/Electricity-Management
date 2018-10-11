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

public class ManutentoreModificaComponente extends JFrame {

	private static final long serialVersionUID = -136098232338205975L;
	private JPanel contentPane;
	private JTextField idComponenteField;
	private JTextField valoreEnergeticoField;
	private JButton modificaComponenteBtn;
	private static GeneralInt genInt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				ManutentoreModificaComponente frame = new ManutentoreModificaComponente(genInt);
				frame.setVisible(true);
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ManutentoreModificaComponente(GeneralInt genInt) {
		ManutentoreModificaComponente.genInt = genInt;
		initComponents();
		createEvents();
	}

	private void initComponents(){
		setTitle("Modifica Componente");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ManutentoreModificaComponente.class.getResource("/resource/technology.png")));
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 152);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblIdcomponente = new JLabel("IDComponente :");
		lblIdcomponente.setBounds(10, 26, 91, 14);
		contentPane.add(lblIdcomponente);

		JLabel lblValoreEnergetico = new JLabel("Valore Energetico :");
		lblValoreEnergetico.setBounds(10, 51, 108, 14);
		contentPane.add(lblValoreEnergetico);

		idComponenteField = new JTextField();
		idComponenteField.setBounds(130, 23, 255, 20);
		contentPane.add(idComponenteField);
		idComponenteField.setColumns(10);

		valoreEnergeticoField = new JTextField();
		valoreEnergeticoField.setColumns(10);
		valoreEnergeticoField.setBounds(130, 48, 255, 20);
		contentPane.add(valoreEnergeticoField);

		modificaComponenteBtn = new JButton("MODIFICA COMPONENTE");
		modificaComponenteBtn.setBounds(211, 79, 174, 23);
		contentPane.add(modificaComponenteBtn);
	}

	private void createEvents(){

		modificaComponenteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					int idComp = Integer.parseInt(idComponenteField.getText());
					float valEn = Float.parseFloat(valoreEnergeticoField.getText());
					genInt.modComponente(idComp, valEn);
					setVisible(false);
					dispose();
				} catch (RemoteException e1) {
					JOptionPane.showMessageDialog(modificaComponenteBtn,"Errore Connessione Server");
				} catch (InesistenteException e1) {
					JOptionPane.showMessageDialog(modificaComponenteBtn, e1.getMessage());
				} catch (NumberFormatException e1){
					JOptionPane.showMessageDialog(modificaComponenteBtn,"IDComponente e Valore Energetico devono essere valori numerici");
				}
			}
		});
	}
}
