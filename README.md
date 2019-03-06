## osm18L_projekt2

**Temat:** System elektronicznych kart badań kardiologicznych. 

**Autorzy:** Hanna Manista, Aleksandra Budzyńska

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


**Uwagi dodatkowe:** Projekt zawiera dwie klasy UI oraz BazaPacjentow. W pierwszej z nich znajduje się obsługa okna
użytkownika, w drugiej zaś wszystkie potrzebne metody do obsługi bazy danych.

Przycisk "Dodaj pacjenta" - aktywuje panel dodawania pacjenta, wszystkie dane zapisywane w oknie są sprawdzane
pod kątem poprawności sprowadzanych danych oraz PESELU (sprawdzanie czy w bazie danych już istnieje osoba z takim PESELEM).

Przycisk "Zapisz" - zapisuje dane pacjenta lub badania, sprawdza ich poprawność

Przycisk "Anuluj" - anuluje zapisywanie danych pacjenta, czyści pola i dezaktywuje możliwość dodania danych

Przycisk "Usuń pacjenta" - usuwa pacjenta z bazy oraz tabeli, dodatkowo usuwa wszystkie badania z nim związane.

Przycisk "Oblicz " - oblicza wartość średnia, minimum, maksimum oraz tworzy wykres danych wybranych za pomocą
rozwijanej listy "Tętno", "Ciśnienie skurczowe", "Ciśnienie rozkurczowe"

Aktywacja pola badania oraz rysowania wykresu dokonuje się w momencie wybrania użytkownika z listy, użytkownikowi 
wybranemu z listy albo przypisywane jest kolejne badanie albo wykonywany jest wykres z danych, które go dotyczą.


