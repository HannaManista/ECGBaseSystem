## osm18L_projekt2

**Przedmiot:** OSM

**Projekt:** 2

**Zadanie:** 9

**Temat:** System elektronicznych kart badañ kardiologicznych. 

**Zespol:** Aleksandra Budzyñska, Hanna Manista

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


**Uwagi dodatkowe:** Projekt zawiera dwie klasy UI oraz BazaPacjentow. W pierwszej z nich znajduje siê obs³uga okna
u¿ytkownika, w drugiej zaœ wszystkie potrzebne metody do obs³ugi bazy danych.

Przycisk "Dodaj pacjenta" - aktywuje panel dodawania pacjenta, wszystkie dane zapisywane w oknie s¹ sprawdzane
pod k¹tem poprawnoœci sprowadzanych danych oraz PESELU (sprawdzanie czy w bazie danych ju¿ istnieje osoba z takim PESELEM).

Przycisk "Zapisz" - zapisuje dane pacjenta lub badania, sprawdza ich poprawnoœæ

Przycisk "Anuluj" - anuluje zapisywanie danych pacjenta, czyœci pola i dezaktywuje mo¿liwoœæ dodania danych

Przycisk "Usuñ pacjenta" - usuwa pacjenta z bazy oraz tabeli, dodatkowo usuwa wszystkie badania z nim zwi¹zane.

Przycisk "Oblicz " - oblicza wartoœæ œrednia, minimum, maksimum oraz tworzy wykres danych wybranych za pomoc¹
rozwijanej listy "Têtno", "Ciœnienie skurczowe", "Ciœnienie rozkurczowe"

Aktywacja pola badania oraz rysowania wykresu dokonuje siê w momencie wybrania u¿ytkownika z listy, u¿ytkownikowi 
wybranemu z listy albo przypisywane jest kolejne badanie albo wykonywany jest wykres z danych, które go dotycz¹.


