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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.Date;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class ClienteSegnalaGuasto extends JFrame {

	private static final long serialVersionUID = 936900634936701570L;
	private JPanel contentPane;
	private static GeneralInt genInt;
	private JTextArea DettagliArea;
	private JButton segnalaGuastoBtn;
	private JTextField IDComponenteField;
	private JComboBox<String> dayBox;
	private JComboBox<String> monthBox;
	private JComboBox<String> yearBox;
	private static String id;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				ClienteSegnalaGuasto frame = new ClienteSegnalaGuasto(genInt, id);
				frame.setVisible(true);
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ClienteSegnalaGuasto(GeneralInt genInt, String id) {
		ClienteSegnalaGuasto.genInt = genInt;
		ClienteSegnalaGuasto.id = id;
		initComponents();
		createEvents();
	}

	private void initComponents(){
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClienteSegnalaGuasto.class.getResource("/resource/technology.png")));
		setTitle("Segnala Guasto");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 402, 256);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Dettagli :");
		lblNewLabel.setBounds(10, 70, 60, 14);
		contentPane.add(lblNewLabel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(105, 70, 255, 106);
		contentPane.add(scrollPane);

		DettagliArea = new JTextArea();
		scrollPane.setViewportView(DettagliArea);

		segnalaGuastoBtn = new JButton("SEGNALA GUASTO");
		segnalaGuastoBtn.setBounds(221, 187, 139, 23);
		contentPane.add(segnalaGuastoBtn);

		JLabel lblIdcomponente = new JLabel("IDComponente :");
		lblIdcomponente.setBounds(12, 14, 95, 14);
		contentPane.add(lblIdcomponente);

		IDComponenteField = new JTextField();
		IDComponenteField.setColumns(10);
		IDComponenteField.setBounds(105, 11, 255, 20);
		contentPane.add(IDComponenteField);

		JLabel lblData = new JLabel("Data :");
		lblData.setBounds(10, 45, 46, 14);
		contentPane.add(lblData);

		dayBox = new JComboBox<String>();
		dayBox.setModel(new DefaultComboBoxModel<String>(new String[] {""}));
		for(int i=1;i<32;i++){
			dayBox.addItem(new Integer(i).toString());
		}
		dayBox.removeItemAt(0);
		dayBox.setSelectedIndex(0);
		dayBox.setBounds(105, 42, 46, 20);
		contentPane.add(dayBox);

		monthBox = new JComboBox<String>();
		monthBox.setModel(new DefaultComboBoxModel<String>(new String[] {""}));
		for(int i=1;i<13;i++){
			monthBox.addItem(new Integer(i).toString());
		}
		monthBox.setBounds(169, 42, 46, 20);
		monthBox.removeItemAt(0);
		monthBox.setSelectedIndex(0);
		contentPane.add(monthBox);

		yearBox = new JComboBox<String>();
		yearBox.setModel(new DefaultComboBoxModel<String>(new String[] {""}));
		for(int i=1900;i<2018;i++){
			yearBox.addItem(new Integer(i).toString());
		}
		yearBox.removeItemAt(0);
		yearBox.setSelectedIndex(0);
		yearBox.setBounds(229, 42, 53, 20);
		contentPane.add(yearBox);
	}

	private void createEvents(){

		segnalaGuastoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					int idComponente = Integer.parseInt(IDComponenteField.getText());
					String dettagli = DettagliArea.getText();

					String year = (String)yearBox.getSelectedItem();
					String month = (String)monthBox.getSelectedItem();
					String day = (String)dayBox.getSelectedItem();
					String data = new String(year+"-"+month+"-"+day);
					Date datas = java.sql.Date.valueOf(data);
					genInt.addGuasto(dettagli, idComponente, id, datas);

					setVisible(false);
					dispose();
				} catch (RemoteException e1) {
					JOptionPane.showMessageDialog(segnalaGuastoBtn,"Errore Connessione Server");
				} catch (InesistenteException e1) {
					JOptionPane.showMessageDialog(segnalaGuastoBtn, e1.getMessage());
				} catch (CercaException e1) {
					JOptionPane.showMessageDialog(segnalaGuastoBtn, e1.getMessage());		
				}
			}
		});

	}
}
