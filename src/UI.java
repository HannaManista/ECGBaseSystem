
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import javax.swing.table.DefaultTableModel;

public class UI implements ActionListener, MouseListener{
	
	// zmienne wystêpuj¹ce w ca³ej klasie
	private static JFrame frame;
	private JFrame frame2;
	private static JTextField textPesel;
	private static JTextField textSurname;
	private static JTextField textName;
	private static JButton btnZapisz;
	private static JButton btnAnuluj;
	private static JComboBox<String> comboBox ;
	private static JButton btnOblicz;
	private static JButton btnZapisz_1;
	private static JButton btnAnuluj2;
	private static JButton btnDodajPacjenta;
	static int id = 0, id_b =1;
	static BazaPacjentow bazaPacjentow = new BazaPacjentow();
	static UI window;
	private static JTextField textMinimum;
	private static JTextField textMaksimum;
	private static JTextField textSrednia;
	private static JTextField txtCisnienieskurcz;
	private static JTextField txtCisnienie_rozkurcz;
	private static JTextField txtTetno;
	private JLabel lblTtno;
	private JLabel lblCinienieSkurczowe;
	private JLabel lblCinienieRozkurczowe;
	private static ArrayList<String []> danePacjentow;
	private static ArrayList<Integer> daneBadania;
	private static JButton btnUsunPacjenta;
	private static JTable table;
	private static DefaultTableModel model ;

	/**
	 * Uruchomienie aplikacji, po³¹czenie z baz¹ danych i ustawienie pocz¹tkowych ustawieñ w panelu g³ównym
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new UI();
					UI.frame.setVisible(true);
					Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
					System.out.println("Sterownik bazy danych zostal uruchominy\n");
					bazaPacjentow.createConnection();
					bazaPacjentow.checkDB();
					bazaPacjentow.deleteTables(); // usuniêcie tabeli, jeœli istnieje, tworzenie nowych tabeli pacjetnów i badania
					bazaPacjentow.createTable();
					bazaPacjentow.createTableExamine();
					btnUsunPacjenta.setEnabled(false);
					zezwolenieBadanie(false);
					zezwoleniePacjent(false);
					zezwolenieOblicz(false);

				} catch (Exception e) { // w przypadku b³êdu w uruchamianiu zamkniêcie po³¹czenia z baz¹ danych
					e.printStackTrace();
					bazaPacjentow.shutDown();
				}
			}
		});
	}

	/**
	 * Tworzenie interfejsu graficznego
	 */
	public UI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 589, 371);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		btnZapisz = new JButton("ZAPISZ");
		btnZapisz.addActionListener(this);

		btnZapisz.setBounds(24, 147, 89, 23);
		frame.getContentPane().add(btnZapisz);
		textPesel = new JTextField();
		textPesel.setBounds(24, 116, 86, 20);
		frame.getContentPane().add(textPesel);
		textPesel.setColumns(10);

		textSurname = new JTextField();
		textSurname.setBounds(24, 71, 86, 20);
		frame.getContentPane().add(textSurname);
		textSurname.setColumns(10);

		textName = new JTextField();
		textName.setBounds(24, 25, 86, 20);
		frame.getContentPane().add(textName);
		textName.setColumns(10);

		JLabel lblImi = new JLabel("Imiê");
		lblImi.setBounds(24, 11, 46, 14);
		frame.getContentPane().add(lblImi);

		JLabel lblNazwisko = new JLabel("Nazwisko");
		lblNazwisko.setBounds(24, 56, 89, 14);
		frame.getContentPane().add(lblNazwisko);

		JLabel lblPesel = new JLabel("PESEL");
		lblPesel.setBounds(24, 102, 46, 14);
		frame.getContentPane().add(lblPesel);

		btnAnuluj = new JButton("ANULUJ");
		btnAnuluj.setBounds(123, 147, 89, 23);
		frame.getContentPane().add(btnAnuluj);
		btnAnuluj.addActionListener(this);

		comboBox = new JComboBox<String>();
		comboBox.setBounds(298, 214, 188, 20);
		comboBox.addItem("Ciœnienie skurczowe");
		comboBox.addItem("Ciœnienie rozkurczowe");
		comboBox.addItem("Têtno");
		frame.getContentPane().add(comboBox);

		JLabel lblRodzajParametru = new JLabel("Rodzaj parametru");
		lblRodzajParametru.setBounds(334, 195, 119, 14);
		frame.getContentPane().add(lblRodzajParametru);

		btnZapisz_1 = new JButton("ZAPISZ");
		btnZapisz_1.setBounds(24, 303, 89, 23);
		frame.getContentPane().add(btnZapisz_1);
		btnZapisz_1.addActionListener(this);

		btnAnuluj2 = new JButton("ANULUJ");
		btnAnuluj2.setBounds(123, 303, 89, 23);
		frame.getContentPane().add(btnAnuluj2);
		btnAnuluj2.addActionListener(this);

		textMinimum = new JTextField();
		textMinimum.setBounds(242, 260, 86, 20);
		frame.getContentPane().add(textMinimum);
		textMinimum.setColumns(10);

		textMaksimum = new JTextField();
		textMaksimum.setBounds(356, 260, 86, 20);
		frame.getContentPane().add(textMaksimum);
		textMaksimum.setColumns(10);

		textSrednia = new JTextField();
		textSrednia.setBounds(466, 260, 86, 20);
		frame.getContentPane().add(textSrednia);
		textSrednia.setColumns(10);

		JLabel lblMinimum = new JLabel("Minimum");
		lblMinimum.setBounds(242, 245, 86, 14);
		frame.getContentPane().add(lblMinimum);

		JLabel lblMaksiumum = new JLabel("Maksiumum");
		lblMaksiumum.setBounds(356, 245, 100, 14);
		frame.getContentPane().add(lblMaksiumum);

		JLabel lblWartorednia = new JLabel("Warto\u015B\u0107 \u015Brednia");
		lblWartorednia.setBounds(466, 245, 97, 14);
		frame.getContentPane().add(lblWartorednia);

		btnOblicz = new JButton("OBLICZ");
		btnOblicz.setBounds(353, 292, 89, 23);
		frame.getContentPane().add(btnOblicz);
		btnOblicz.addActionListener(this);

		JLabel lblBadanie = new JLabel("BADANIE");
		lblBadanie.setBounds(93, 181, 76, 14);
		frame.getContentPane().add(lblBadanie);

		txtCisnienieskurcz = new JTextField();
		txtCisnienieskurcz.setBounds(24, 272, 100, 20);
		frame.getContentPane().add(txtCisnienieskurcz);
		txtCisnienieskurcz.setColumns(10);

		txtCisnienie_rozkurcz = new JTextField();
		txtCisnienie_rozkurcz.setBounds(24, 229, 100, 20);
		frame.getContentPane().add(txtCisnienie_rozkurcz);
		txtCisnienie_rozkurcz.setColumns(10);

		txtTetno = new JTextField();
		txtTetno.setBounds(126, 272, 86, 20);
		frame.getContentPane().add(txtTetno);
		txtTetno.setColumns(10);

		lblTtno = new JLabel("Têtno");
		lblTtno.setBounds(149, 254, 46, 23);
		frame.getContentPane().add(lblTtno);

		lblCinienieSkurczowe = new JLabel("Ci\u015Bnienie skurczowe");
		lblCinienieSkurczowe.setBounds(24, 254, 145, 23);
		frame.getContentPane().add(lblCinienieSkurczowe);

		lblCinienieRozkurczowe = new JLabel("Ci\u015Bnienie rozkurczowe");
		lblCinienieRozkurczowe.setBounds(24, 214, 145, 14);
		frame.getContentPane().add(lblCinienieRozkurczowe);

		btnDodajPacjenta = new JButton("Dodaj pacjenta");
		btnDodajPacjenta.setBounds(414, 147, 127, 23);
		frame.getContentPane().add(btnDodajPacjenta);
		btnDodajPacjenta.addActionListener(this);

		btnUsunPacjenta = new JButton("Usu\u0144 pacjenta");
		btnUsunPacjenta.setBounds(252, 147, 119, 23);
		frame.getContentPane().add(btnUsunPacjenta);
		btnUsunPacjenta.addActionListener(this);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(242, 11, 308, 131);
		frame.getContentPane().add(scrollPane);

		// tworzenie tabeli do wyœwietlania danych pacjenta
		table = new JTable();
		table.addMouseListener(this);
		model = new DefaultTableModel() {

			@Override
			public boolean isCellEditable(int row, int column) {
				//all cells false
				return false;
			}
		};
		table.setModel(model);
		model.addColumn("Imiê");
		model.addColumn("Nazwisko");
		model.addColumn("PESEL");


		scrollPane.setViewportView(table);
	}

	// obs³uga przysicków
	@Override
	public void actionPerformed(ActionEvent e) {
		Object z= e.getSource();

		if(z == btnDodajPacjenta) { // przycisk dodaj pacjenta powoduje odblokowanie mo¿liwoœci dodawania pacjenta do bazy danych

			zezwoleniePacjent(true);
			zezwolenieBadanie(false);
			zezwolenieOblicz(false);
			txtTetno.setText("");
			txtCisnienie_rozkurcz.setText("");
			txtCisnienieskurcz.setText("");
			textMinimum.setText("");
			textMaksimum.setText("");
			textSrednia.setText("");

		}
		if(z == btnZapisz) { // sprawdzenie poprawnoœci danych zapisywanych, wpisanie pacjenta do bazy i uaktualnienie tabeli wyœwietlaj¹cej pacjentów
			String imie = textName.getText();
			String nazwisko = textSurname.getText();
			long pesel;

			if(sprawdzDane()) {
				pesel = Long.parseLong(textPesel.getText());
				if(bazaPacjentow.checkPesel(pesel)) {
					BazaPacjentow.addPatient(imie, nazwisko, pesel);
					pokazTabele();
					id++;
					//bazaPacjentow.display();
					textName.setText("");
					textPesel.setText("");
					textSurname.setText("");
					zezwoleniePacjent(false);
					btnUsunPacjenta.setEnabled(true);

				}
				else{
					JOptionPane.showMessageDialog(frame, "Taki PESEL ju¿ istnieje!");
				}

			}
		}
		// zapisywanie badania do bazy danych, sprawdzanie jego poprawnoœci, przypisywanie go u¿ytkownikowi z wybranej listy (pesel)
		if(z == btnZapisz_1) {

			if(sprawdzBadanie()) {
				int cisSkurcz = Integer.parseInt(txtCisnienieskurcz.getText());
				int cisRozkurcz = Integer.parseInt(txtCisnienie_rozkurcz.getText());
				int row = table.getSelectedRow();
				String pesel_slowo = table.getModel().getValueAt(row, 2).toString();
				int tetno = Integer.parseInt(txtTetno.getText());
				long pesel = Long.parseLong(pesel_slowo);

				BazaPacjentow.addBadanie(id_b, cisSkurcz, cisRozkurcz, tetno, pesel);
				//bazaPacjentow.displayExam();
				id_b++;
				txtTetno.setText("");
				txtCisnienie_rozkurcz.setText("");
				txtCisnienieskurcz.setText("");
				zezwolenieBadanie(false);
				zezwolenieOblicz(false);

			}

		}
		//usuniêcie wybranego pacjenta wraz z jego badaniami, jeœli nie ma ¿adnych pacjentów w tabeli uniemo¿liwienie usuniêcia
		if(z == btnUsunPacjenta) {
			int row = table.getSelectedRow();
			String pesel_slowo = table.getModel().getValueAt(row, 2).toString();
			long pesel_pacjenta = Long.parseLong(pesel_slowo);
			bazaPacjentow.deletePatient(pesel_pacjenta);
			pokazTabele();
			zezwolenieOblicz(false);
			zezwolenieBadanie(false);
			txtTetno.setText("");
			txtCisnienie_rozkurcz.setText("");
			txtCisnienieskurcz.setText("");
			textMinimum.setText("");
			textMaksimum.setText("");
			textSrednia.setText("");
			if(table.getRowCount() == 0) {
				btnUsunPacjenta.setEnabled(false);
			}
		}

		// anulowanie wpisu pacjenta powoduje zablokowanie mo¿liwoœci dodania wpisu, aby umo¿liwiæ dodanie wpisu nale¿y u¿yæ przycisku dodaj pacjenta
		if(z == btnAnuluj) {
			zezwoleniePacjent(false);
			textName.setText("");
			textSurname.setText("");
			textPesel.setText("");
		}

		// obliczenie wartoœci wybranego parametru dla osoby wybranej z listy, wyœwietlany jest wykres danego parametru
		if(z == btnOblicz) {

			String wybrane;
			wybrane = comboBox.getSelectedItem().toString();
			int row = table.getSelectedRow();
			String pesel_slowo = table.getModel().getValueAt(row, 2).toString();
			long pesel_pacjenta = Long.parseLong(pesel_slowo);
			int sr;
			int max;
			int min;
			try {

				if(wybrane.equals("Têtno")) {
					sr = obliczSrednia(pesel_pacjenta, wybrane);
					min = znajdzMinimum(pesel_pacjenta, wybrane);
					max = znajdzMaksimum(pesel_pacjenta, wybrane);
					textMinimum.setText(Integer.toString(min));
					textMaksimum.setText(Integer.toString(max));
					textSrednia.setText(Integer.toString(sr));

				}

				if(wybrane.equals("Ciœnienie skurczowe")) {
					sr = obliczSrednia(pesel_pacjenta, wybrane);
					min = znajdzMinimum(pesel_pacjenta, wybrane);
					max = znajdzMaksimum(pesel_pacjenta, wybrane);
					textMinimum.setText(Integer.toString(min));
					textMaksimum.setText(Integer.toString(max));
					textSrednia.setText(Integer.toString(sr));

				}

				if(wybrane.equals("Ciœnienie rozkurczowe")) {
					sr = obliczSrednia(pesel_pacjenta, wybrane);
					min = znajdzMinimum(pesel_pacjenta, wybrane);
					max = znajdzMaksimum(pesel_pacjenta, wybrane);
					textMinimum.setText(Integer.toString(min));
					textMaksimum.setText(Integer.toString(max));
					textSrednia.setText(Integer.toString(sr));

				}

				stworzWykres(wybrane, pesel_pacjenta); 
				zezwolenieBadanie(false);
			} catch(Exception es) { // w przypadku braku danych z bazy dla danego pacjenta, wartoœci ustawiane s¹ na zero, wykres siê nie rysuje
				JOptionPane.showMessageDialog(frame, "Nie mo¿na obliczyæ parametrów");
				textMinimum.setText(Integer.toString(0));
				textMaksimum.setText(Integer.toString(0));
				textSrednia.setText(Integer.toString(0));
				zezwolenieOblicz(false);
				zezwolenieBadanie(false);
			}


		}
		// anulowanie wpisywania powoduje usuniêcie wartoœci z pól i zablokowanie mo¿liwoœci dodania wpisu
		if(z == btnAnuluj2) {
			zezwolenieOblicz(false);
			zezwolenieBadanie(false);
			txtTetno.setText("");
			txtCisnienie_rozkurcz.setText("");
			txtCisnienieskurcz.setText("");
		}

	}

	// sprawdzenie czy s³owo zawiera same litery, jeœli zawiera coœ poza liter¹ zwraca true
	public boolean nieZawieraLiter(String slowo) {

		for(int i=0; i <= (slowo.length()-1); i++) {
			if(!Character.isLetter(slowo.charAt(i)))
				return true;}

		return false;

	}

	// sprawdzenie czy s³owo zawiera same cyfry, jeœli zawiera coœ poza cyfr¹ zwraca true
	public boolean nieZawieraCyfr(String slowo) {

		for(int i = 0  ; i <= (slowo.length()-1) ; i++) {
			if(!Character.isDigit(slowo.charAt(i)))
				return true;}

		return false;

	}

	// sprawdzanie poprawnoœci wprowadzanych danych pacjenta
	public boolean sprawdzDane() {
		String imie = textName.getText();
		String nazwisko = textSurname.getText();
		String PESEL = textPesel.getText();

		if(imie.isEmpty() || imie.trim().isEmpty() || nieZawieraLiter(imie)) {
			JOptionPane.showMessageDialog(frame, "Niepoprawne imiê");
			return false;
		}
		if(nazwisko.isEmpty() || nazwisko.trim().isEmpty() || nieZawieraLiter(nazwisko)) {
			JOptionPane.showMessageDialog(frame, "Niepoprawne nazwisko");
			return false;
		};
		if(PESEL.length()!=11 || PESEL.trim().isEmpty() ||  PESEL.isEmpty() || nieZawieraCyfr(PESEL)) {
			JOptionPane.showMessageDialog(frame, "Niepoprawne niepoprawny pesel");
			return false;
		}
		return true;
	}

	// sprawdzanie poprawnoœci wprowadzanych danych badania
	public boolean sprawdzBadanie() {
		int cisSkurcz;
		int cisRozkurcz;
		int tetno;

		if(txtTetno.getText().isEmpty() || txtTetno.getText().trim().isEmpty() || nieZawieraCyfr(txtTetno.getText())) {
			JOptionPane.showMessageDialog(frame, "Niepoprawnie wpisane têtno");
			return false;
		}
		if(txtCisnienieskurcz.getText().isEmpty() || txtCisnienieskurcz.getText().trim().isEmpty() || nieZawieraCyfr(txtCisnienieskurcz.getText())) {
			JOptionPane.showMessageDialog(frame, "Niepoprawnie wpisane ciœnienie skurczowe");
			return false;
		}
		if(txtCisnienie_rozkurcz.getText().isEmpty() || txtCisnienie_rozkurcz.getText().trim().isEmpty() || nieZawieraCyfr(txtCisnienie_rozkurcz.getText())) {
			JOptionPane.showMessageDialog(frame, "Niepoprawnie wpisane ciœnienie rozkurczowe");
			return false;
		}

		cisSkurcz = Integer.parseInt(txtCisnienieskurcz.getText());
		cisRozkurcz = Integer.parseInt(txtCisnienie_rozkurcz.getText());
		tetno = Integer.parseInt(txtTetno.getText());

		if(tetno<20 || tetno>200) {
			JOptionPane.showMessageDialog(frame, "Têtno spoza zakresu!"); 
			return false;
		}
		if(cisSkurcz<60 || cisSkurcz > 250) {
			JOptionPane.showMessageDialog(frame, "Ciœnienie skurczowe spoza zakresu!");
			return false;
		}
		if(cisRozkurcz<30 || cisRozkurcz>150) {
			JOptionPane.showMessageDialog(frame, "Ciœnienie rozkurczowe spoza zakresu!");
			return false;
		}
		return true;
	}

	// pokazywanie tabeli z pacjentami
	public void pokazTabele() {
		danePacjentow= bazaPacjentow.pobierzDanePacjentow();
		model.setRowCount(0);
		for( int i = 0 ; i < danePacjentow.size() ; i++) {
			model.addRow(danePacjentow.get(i));
		}

	}

	// odblokowanie pól dotycz¹cych dodawnania badania
	public static void  zezwolenieBadanie(boolean zezwol) {
		txtTetno.setEditable(zezwol);
		txtCisnienieskurcz.setEditable(zezwol);
		txtCisnienie_rozkurcz.setEditable(zezwol);
		btnZapisz_1.setEnabled(zezwol);;
		btnAnuluj2.setEnabled(zezwol);

	}

	// odblokowanie pól dotycz¹cych dodawania pacjenta
	public static void zezwoleniePacjent(boolean zezwol) {

		textName.setEditable(zezwol);
		textPesel.setEditable(zezwol);
		textSurname.setEditable(zezwol);
		btnZapisz.setEnabled(zezwol);
		btnAnuluj.setEnabled(zezwol);

	}

	// odblokowanie pól dotycz¹cych obliczania parametrów
	public static void zezwolenieOblicz(boolean zezwol) {
		comboBox.setEnabled(zezwol);
		btnOblicz.setEnabled(zezwol);
		textMinimum.setEditable(false);
		textMaksimum.setEditable(false);
		textSrednia.setEditable(false);	

	}

	// szukanie minimum w liœcie 
	public int znajdzMinimum(long pesel, String wybrane) {	
		daneBadania = bazaPacjentow.pobierzDaneBadanie(pesel, wybrane);
		int minimum = Collections.min(daneBadania);
		return minimum;
	}

	// obliczanie œrednie z wartoœci z listy
	public int obliczSrednia(long pesel, String wybrane) {
		daneBadania = bazaPacjentow.pobierzDaneBadanie(pesel, wybrane);
		int ilosc = daneBadania.size();
		int suma = 0;
		for(int i = 0; i < ilosc; i++) {
			suma += daneBadania.get(i); }
		int srednia = suma/ilosc;
		return srednia;
	}

	// szukanie maksimum w liœcie
	public int znajdzMaksimum(long pesel, String wybrane) {
		daneBadania = bazaPacjentow.pobierzDaneBadanie(pesel, wybrane);
		int maksimum = Collections.max(daneBadania);
		return maksimum;
	}

	// tworzenie wykresu w nowym oknie
	public void stworzWykres(String wybrane, long pesel) {

		frame2 = new JFrame ("MyPanel");
		frame2.setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);
		JPanel contentPane = new JPanel();
		frame2.setContentPane(contentPane);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(1, 2));

		XYSeries seria= new XYSeries("Wykres parametru: " + wybrane);
		daneBadania = bazaPacjentow.pobierzDaneBadanie(pesel, wybrane);
		int liczba = daneBadania.size();

		for( int i = 0 ; i < liczba ; i++) {
			seria.add((double)i+1, (double)daneBadania.get(i));
		}

		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(seria);

		JFreeChart chart = ChartFactory.createXYLineChart(
				"Wykres parametru: " + wybrane , // tytu³
				"Kolejne pomiary", // oœ x - nazwa
				wybrane, // oœ y - nazwa
				dataset, // zestaw danych
				PlotOrientation.VERTICAL, // orientacja wykresu
				false, // pokazywanie legendy
				true, // u¿ywanie tootips
				false // 
				);

		final XYPlot plot = chart.getXYPlot( );
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
		renderer.setSeriesPaint( 0 , Color.RED );
		plot.setRenderer( renderer ); 

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(800, 500));
		contentPane.add(chartPanel);

		frame2.setTitle("Wykres przedstawiaj¹cy dane: " + wybrane);
		frame2.pack();
		frame2.setVisible (true);

	}

	// wybranie u¿ytkownika odblokowuje mo¿liwoœæ dodania badania lu obliczenia wartoœci
	@Override
	public void mouseClicked(MouseEvent e) {

		zezwolenieBadanie(true);
		zezwoleniePacjent(false);
		zezwolenieOblicz(true);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
