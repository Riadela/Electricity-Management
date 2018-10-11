package prog.client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import prog.shared.GeneralInt;
import prog.shared.exception.CercaException;
import prog.shared.exception.GiaPagataException;

import javax.swing.UIManager;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.awt.event.ActionEvent;

public class ClientePagaFattura extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField IDFatturaField;
	private static GeneralInt genInt;
	private JButton PagaFatturaBtn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientePagaFattura frame = new ClientePagaFattura(genInt);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ClientePagaFattura(GeneralInt genInt) {
		ClientePagaFattura.genInt = genInt;
		initComponents();
		createEvents();
	}
	
	private void initComponents(){
		setTitle("Paga Fattura");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClientePagaFattura.class.getResource("/resource/technology.png")));
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 379, 152);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblIdfattura = new JLabel("IDFattura :");
		lblIdfattura.setBounds(10, 32, 54, 14);
		contentPane.add(lblIdfattura);
		
		IDFatturaField = new JTextField();
		IDFatturaField.setBounds(74, 29, 255, 20);
		contentPane.add(IDFatturaField);
		IDFatturaField.setColumns(10);
		
		PagaFatturaBtn = new JButton("PAGA FATTURA");
		PagaFatturaBtn.setBounds(220, 71, 109, 23);
		contentPane.add(PagaFatturaBtn);
	}
	
	private void createEvents(){
		
		PagaFatturaBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					genInt.pagaFtt(Integer.parseInt(IDFatturaField.getText()));
					//
					setVisible(false);
					dispose();
				} catch (NumberFormatException | RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (CercaException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(PagaFatturaBtn,e1.getMessage());
					//e1.printStackTrace();
				} catch (GiaPagataException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(PagaFatturaBtn,e1.getMessage());
					//e1.printStackTrace();
				}
			}
		});
		
	}

}
