
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class BazaPacjentow {

	static Connection			connection=null;	//obiekt reprezentujacy polaczenie z baza
	static DatabaseMetaData		dbmd = null;		//obiekt przechowujacy informacje o bazie danych        	
	static Statement			statement=null;		//obiekt wykorzystywany do zapytan do bazy danych
	static ResultSet			result=null;		//obiekt zawierajacy wyniki zapytania do bazy danych
	private static final String JDBC_URL = "jdbc:derby:OSM2;create=true"; 	



	// utworzenie po��czenia z baz� danych
	public void createConnection() {
		try {
			this.connection = DriverManager.getConnection(JDBC_URL);
			if (this.connection != null) {
				System.out.print("Utworzono polaczenie z baza danych");
			}
		}
		catch(SQLException e){
			System.out.print("Nie uda�o si� po��czy�!");
		}
	}


	// pobranie informacji o bazie danych i utworzenie obiektu zapytania
	public void checkDB() {
		try{
			dbmd = connection.getMetaData();
			statement = connection.createStatement();
			System.out.println("\nSprawdzono baz� danych");
		}
		catch(SQLException e){
			e.getStackTrace();
		}
	}

	// stworzenie tabeli pacjentow
	public void createTable() {
		try {
			result=dbmd.getTables(null,null,"PACJENCI",null);
			if (!result.next())
			{
				statement.execute(	"CREATE TABLE pacjenci" +
						"(" +
						"PESEL 			BIGINT NOT NULL PRIMARY KEY,"+
						"imie			VARCHAR(256) NOT NULL," +
						"nazwisko		VARCHAR(256) NOT NULL" +

						")");

				System.out.println("Utworzono tabele pacjenci");
			}
			else
				System.out.println("Tabela pacjenci juz istnieje");
		} catch(SQLException e) { e.getStackTrace();}

	}

	// stworzenie tabeli badanie
	public void createTableExamine() {
		try {
			result = dbmd.getTables(null, null, "BADANIE", null);
			if(!result.next()) {

				statement.execute("CREATE TABLE badanie" + "(" + 
						"id_badania    	INT NOT NULL PRIMARY KEY," + 
						"cisnienie_skurczowe	INT," +
						"cisnienie_rozkurczowe		INT," +
						"tetno					INT,"	+ 
						"PESEL        BIGINT NOT NULL"+ ""
						+ ")");
				System.out.println("Utworzono tabele badanie");
			}else
				System.out.println("Tabela badanie juz istnieje");
		} catch(SQLException e) { e.getStackTrace();}

	}

	//dodawanie wpisow do tabeli pacjenci
	public static void addPatient(String imie, String nazwisko, long PESEL) {
		try{
			statement.execute("insert into pacjenci values ("+PESEL+",'"+ imie + "', '"+ nazwisko + "')");

		}catch(SQLException e) {
			System.out.println("Nie uda�o si� wpisa� u�ytkownika!");
		}
	}

	// dodawanie badania do tabeli
	public static void addBadanie(int id, int skurcz, int rozkurcz, int tetno, long pesel) {
		try {
			statement.execute("insert into badanie values (" + id + ", "+ skurcz + ", "+ rozkurcz+" , " + tetno +", " + pesel +" )");
		}catch(SQLException e) {System.out.println("Nie uda�o si� wpisa� badania!");}
	}

	//wypisanie zawartosci tabeli pacjenci(uporzadkowanej po nazwisku)
	public void display() {
		try {
			System.out.println("\nRekordy tabeli pacjenci:");
			result = statement.executeQuery("SELECT * FROM pacjenci ORDER BY nazwisko");
			while (result.next())
				System.out.println( result.getString("imie") + "\t" + result.getString("nazwisko")+ "\t" + result.getLong("PESEL"));
		}catch(SQLException e) {
			System.out.println("Nie uda�o si� wypisa� danych!");
			e.getStackTrace();}
	}

	// wypisanie zawarto�ci tabeli badanie
	public void displayExam() {
		try {
			System.out.println("\nRekordy tabeli badanie:");
			result = statement.executeQuery("SELECT * FROM badanie ORDER BY id_badania");
			while (result.next())
				System.out.println(result.getLong("PESEL")+ "\t"+result.getInt("id_badania") + "\t"+ result.getInt("cisnienie_skurczowe") + "\t" + result.getInt("cisnienie_rozkurczowe")+ "\t" + result.getInt("tetno"));
		}catch(SQLException e) {
			System.out.println("Nie uda�o si� wypisa� danych!");
			e.getStackTrace();}
	}

	// usuwanie wpisu z tabeli pacjenci
	public void deletePatient(long PESEL) {
		try {
			statement.execute("DELETE FROM pacjenci WHERE PESEL=" + PESEL + "");
			statement.execute("DELETE FROM badanie WHERE PESEL=" + PESEL + "");
			System.out.println("\nUsunieto pacjenta");
			System.out.println();
		}
		catch(SQLException e) {
			System.out.println("Nie uda�o si� usun�� wpisu z tabeli pacjent�w");
		}
	}

	//usuwanie tabel
	public void deleteTables() {
		try {
			statement.execute("DROP TABLE pacjenci");
			statement.execute("DROP TABLE badanie");
		}catch(SQLException e) {
			System.out.println("Nie uda�o si� usun�� tabeli!");
		}
	}

	//zamykanie polaczenia
	public void shutDown() {
		try
		{
			connection.close();
			System.out.println("\nZamknieto polaczenie z baza danych Pacjenci");

			DriverManager.getConnection("jdbc:derby:;shutdown=true");
		}
		catch (SQLException se)
		{
			se.getMessage();
		}}

	// zwraca false, gdy pesel ju� istnieje
	public boolean checkPesel(long pesel) {

		try { 
			ResultSet rezultaty = statement.executeQuery("SELECT PESEL FROM pacjenci WHERE PESEL="  + pesel + "");           
			if (rezultaty.next()) {
				return false;}
		} catch(SQLException e) {
			System.out.println("B��d przy sprawdzaniu peselu  " + e.getMessage());
		}
		return true;
	}

	// pobiera liste pacjent�w z bazy danych
	public ArrayList<String []> pobierzDanePacjentow() {
		ArrayList<String []> lista  = new ArrayList();

		try {

			statement.execute("SELECT * FROM pacjenci");
			ResultSet rezultaty = statement.getResultSet();
			while(rezultaty.next()) {
				String [] pacjent= {rezultaty.getString("imie"),rezultaty.getString("nazwisko"),rezultaty.getString("PESEL")};
				lista.add(pacjent);
			}

		}
		catch(SQLException e) {
			System.out.println("Nie uda�o si� pobra� danych " + e.getMessage());
		}
		return lista;
	}

	// pobiera liste z parametrem t�tno, ci�nienie skurczowe lub ci�nienie rozkurczowe dla wybranej osoby
	public ArrayList<Integer> pobierzDaneBadanie(long pesel, String wybor_box) {
		ArrayList<Integer> dane = new ArrayList();
		try {
			String wybor ="";
			if(wybor_box.equals("T�tno")) {
				wybor = "tetno";
			}
			if(wybor_box.equals("Ci�nienie skurczowe")) {
				wybor = "cisnienie_skurczowe";
			}
			if(wybor_box.equals("Ci�nienie rozkurczowe")) {
				wybor="cisnienie_rozkurczowe";
			}

			statement.execute("SELECT "+ wybor +" FROM badanie WHERE PESEL = "  + pesel + "");
			ResultSet rezultaty = statement.getResultSet();
			while(rezultaty.next()){
				dane.add(rezultaty.getInt(1));
			}

		}
		catch(SQLException e) {
			System.out.println("Nie uda�o si� pobra� danych! " + e.getMessage());
		}
		return dane;
	}
}

