package prog.client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import prog.shared.GeneralInt;
import prog.shared.Posizione;
import prog.shared.Tipo;

import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.awt.event.ActionEvent;

public class ManutentoreAggiungiComponente extends JFrame {

	private static final long serialVersionUID = -8603091171326648029L;
	private JPanel contentPane;
	private JComboBox<Object> TipoComponenteBox;
	private JSpinner LatiGradiSpinner;
	private JSpinner LatiPrimiSpinner;
	private JSpinner LatiSecondiSpinner;
	private JSpinner LongiGradiSpinner;
	private JSpinner LongiPrimiSpinner;
	private JSpinner LongiSecondiSpinner;
	private JButton AggiungiComponentBtn;
	private static GeneralInt genInt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				ManutentoreAggiungiComponente frame = new ManutentoreAggiungiComponente(genInt);
				frame.setVisible(true);
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ManutentoreAggiungiComponente(GeneralInt genInt) {
		ManutentoreAggiungiComponente.genInt = genInt;
		initComponents();
		createEvents();
	}

	private void initComponents(){
		setTitle("Aggiungi Componente");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ManutentoreAggiungiComponente.class.getResource("/resource/technology.png")));
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 197);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Tipo Componente :");
		lblNewLabel.setBounds(10, 25, 105, 14);
		contentPane.add(lblNewLabel);

		TipoComponenteBox = new JComboBox<Object>();
		TipoComponenteBox.setModel(new DefaultComboBoxModel<Object>(Tipo.values()));
		TipoComponenteBox.setBounds(127, 22, 143, 20);
		contentPane.add(TipoComponenteBox);

		JLabel lblLatitudine = new JLabel("Latitudine :");
		lblLatitudine.setBounds(10, 56, 91, 14);
		contentPane.add(lblLatitudine);

		LatiGradiSpinner = new JSpinner();
		LatiGradiSpinner.setModel(new SpinnerNumberModel(0, -90, 90, 1));
		LatiGradiSpinner.setBounds(111, 53, 47, 20);
		contentPane.add(LatiGradiSpinner);

		JLabel label = new JLabel("\u00B0");
		label.setBounds(160, 53, 11, 14);
		contentPane.add(label);

		LatiPrimiSpinner = new JSpinner();
		LatiPrimiSpinner.setModel(new SpinnerNumberModel(0, 0, 60, 1));
		LatiPrimiSpinner.setBounds(178, 53, 47, 20);
		contentPane.add(LatiPrimiSpinner);

		JLabel label_1 = new JLabel("'");
		label_1.setBounds(227, 56, 11, 14);
		contentPane.add(label_1);

		LatiSecondiSpinner = new JSpinner();
		LatiSecondiSpinner.setModel(new SpinnerNumberModel(0, 0, 60, 1));
		LatiSecondiSpinner.setBounds(242, 53, 47, 20);
		contentPane.add(LatiSecondiSpinner);

		JLabel label_2 = new JLabel("''");
		label_2.setBounds(293, 56, 46, 14);
		contentPane.add(label_2);

		JLabel lblLongitudine = new JLabel("Longitudine :");
		lblLongitudine.setBounds(10, 81, 91, 14);
		contentPane.add(lblLongitudine);

		LongiGradiSpinner = new JSpinner();
		LongiGradiSpinner.setModel(new SpinnerNumberModel(0, -90, 90, 1));
		LongiGradiSpinner.setBounds(111, 84, 47, 20);
		contentPane.add(LongiGradiSpinner);

		JLabel label_3 = new JLabel("\u00B0");
		label_3.setBounds(160, 84, 11, 14);
		contentPane.add(label_3);

		LongiPrimiSpinner = new JSpinner();
		LongiPrimiSpinner.setModel(new SpinnerNumberModel(0, 0, 60, 1));
		LongiPrimiSpinner.setBounds(178, 84, 47, 20);
		contentPane.add(LongiPrimiSpinner);

		JLabel label_4 = new JLabel("'");
		label_4.setBounds(227, 84, 11, 14);
		contentPane.add(label_4);

		LongiSecondiSpinner = new JSpinner();
		LongiSecondiSpinner.setModel(new SpinnerNumberModel(0, 0, 60, 1));
		LongiSecondiSpinner.setBounds(242, 84, 47, 20);
		contentPane.add(LongiSecondiSpinner);

		JLabel label_5 = new JLabel("''");
		label_5.setBounds(293, 84, 46, 14);
		contentPane.add(label_5);

		AggiungiComponentBtn = new JButton("AGGIUNGI COMPONENTE");
		AggiungiComponentBtn.setBounds(235, 125, 172, 23);
		contentPane.add(AggiungiComponentBtn);
	}

	private void createEvents(){

		AggiungiComponentBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				try {
					Tipo tipo = Tipo.valueOf(TipoComponenteBox.getSelectedItem().toString());
					String latiGradi =  (LatiGradiSpinner.getValue()).toString();
					String latiPrimi = (LatiPrimiSpinner.getValue()).toString();
					String latiSecondi = (LatiSecondiSpinner.getValue()).toString();
					String lati = new String(latiGradi+"°"+latiPrimi+"'"+latiSecondi+"''");
					String longiGradi = (LongiGradiSpinner.getValue()).toString();
					String longiPrimi = (LongiPrimiSpinner.getValue()).toString();
					String longiSecondi = (LongiSecondiSpinner.getValue()).toString();
					String longi = new String(longiGradi+"°"+longiPrimi+"'"+longiSecondi+"''");

					Posizione posizione = new Posizione(lati,longi);

					genInt.addComponente(tipo, posizione);

					setVisible(false);
					dispose();
				} catch (RemoteException e1) {
					JOptionPane.showMessageDialog(AggiungiComponentBtn,"Errore Connessione Server");
				}
			}
		});
	}
}
