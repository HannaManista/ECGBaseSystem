## osm18L_projekt2

**Przedmiot:** OSM

**Projekt:** 2

**Zadanie:** 9

**Temat:** System elektronicznych kart bada� kardiologicznych. 

**Zespol:** Aleksandra Budzy�ska, Hanna Manista

**Biblioteki:** 
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
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


**Uwagi dodatkowe:** Projekt zawiera dwie klasy UI oraz BazaPacjentow. W pierwszej z nich znajduje si� obs�uga okna
u�ytkownika, w drugiej za� wszystkie potrzebne metody do obs�ugi bazy danych.

Przycisk "Dodaj pacjenta" - aktywuje panel dodawania pacjenta, wszystkie dane zapisywane w oknie s� sprawdzane
pod k�tem poprawno�ci sprowadzanych danych oraz PESELU (sprawdzanie czy w bazie danych ju� istnieje osoba z takim PESELEM).

Przycisk "Zapisz" - zapisuje dane pacjenta lub badania, sprawdza ich poprawno��

Przycisk "Anuluj" - anuluje zapisywanie danych pacjenta, czy�ci pola i dezaktywuje mo�liwo�� dodania danych

Przycisk "Usu� pacjenta" - usuwa pacjenta z bazy oraz tabeli, dodatkowo usuwa wszystkie badania z nim zwi�zane.

Przycisk "Oblicz " - oblicza warto�� �rednia, minimum, maksimum oraz tworzy wykres danych wybranych za pomoc�
rozwijanej listy "T�tno", "Ci�nienie skurczowe", "Ci�nienie rozkurczowe"

Aktywacja pola badania oraz rysowania wykresu dokonuje si� w momencie wybrania u�ytkownika z listy, u�ytkownikowi 
wybranemu z listy albo przypisywane jest kolejne badanie albo wykonywany jest wykres z danych, kt�re go dotycz�.


