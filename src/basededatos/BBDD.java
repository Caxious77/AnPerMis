package basededatos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
//import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.locks.ReentrantLock;

import funcionalidad.Main;

public class BBDD {
	private Conexion conectarBBDD;// se usa para conectarse a la BBDD
	private Connection ConRealizada = null;// clase connection trata todo lo que tiene que ver con la conexion
	private Statement stm = null;// se usa para ejecutar query
	private ArrayList<String> nombreColumnastablaFreq;
	private ArrayList<String> nombreColumnastablaCriterios;
	private ArrayList<String> nombreColumnastablaGeneral;
	// CACHE DE APPS PARA NO HACER UN USO EXCESIVO DE LA BASE DE DATOS
	private HashMap<String, ArrayList<String>> cache;// Clave = nombre del paquete, Valor = nombre app, idpaquete,
														// categoria, permisos[]
	private HashMap<String, ArrayList<Integer>> tcriterios;// cambiar nombre por tabla de criterios
	private ArrayList<Categoria> categoriasfreq;// tabla de frecuencias de permisos de las categorias
	private static ReentrantLock cierre = new ReentrantLock();

	public BBDD() {
		conectarBBDD = new Conexion();
		categoriasfreq = new ArrayList<Categoria>();
		nombreColumnastablaFreq = new ArrayList<String>();
		nombreColumnastablaCriterios = new ArrayList<String>();
		nombreColumnastablaGeneral = new ArrayList<String>();
		cache = new HashMap<String, ArrayList<String>>();
		tcriterios = new HashMap<String, ArrayList<Integer>>();
		try {
			init();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getTablas() throws SQLException {
		getFreqTable();
		getCriteriosTable();

	}

	private void init() throws SQLException {
		ConRealizada = conectarBBDD.conectar();
		stm = ConRealizada.createStatement();
		actualizarTablasDesdeCero();

	}

	// actualiza la tabla frecuencia a partir de la tabla general, lo hace
	// directamente en la base de datos
	public void actualizarTablasDesdeCero() throws SQLException {
		ejecutarUpdateQuery(queryParaEliminarTablaFreqEnMySQL());
		ejecutarUpdateQuery(queryParaActualizarTablaFreqEnMySQL());
		getNombreColumnasTablaFreq();
		getNombreColumnasTablaCriterios();
		getNombreColumnasTablaGeneral();

	}

	private void getNombreColumnasTablaCriterios() throws SQLException {
		ResultSet columnas = ejecutarSelectQuery("SHOW COLUMNS FROM tcriterios");
		nombreColumnastablaCriterios.clear();
		while (columnas.next()) {
			nombreColumnastablaCriterios.add(columnas.getString("Field"));
		}
	}

	private void getNombreColumnasTablaFreq() throws SQLException {
		ResultSet columnas = ejecutarSelectQuery("SHOW COLUMNS FROM tablafreqabs");
		nombreColumnastablaFreq.clear();
		while (columnas.next()) {
			nombreColumnastablaFreq.add(columnas.getString("Field"));
		}
	}

	public void comprobarApp(String informacion) {// Funcion de la cache
		String[] partes = informacion.replaceAll("'", "").replaceAll("´", "").split(",");
		String idapp = partes[1].replaceAll("\\s", "");
		ArrayList<String> row;
		if (!cache.containsKey(idapp) && !partes[2].equalsIgnoreCase("SININFORMACION") && !partes[2].equalsIgnoreCase("")) {
			row = new ArrayList<String>();
			for (int i = 0; i < partes.length; i++) {
				row.add(partes[i].replaceAll("\\s", ""));
			}
			try {
				cierre.lock();
				if (!cache.containsKey(idapp))
					cache.put(idapp, row);
			} finally {
				cierre.unlock();
			}

		}

	}

	public List<String> sendFreqTable() {
		List<String> headersList = new ArrayList<String>(nombreColumnastablaFreq);
		headersList.remove(1);// quita el elemento en la posicion indicada
		List<String> tabla = new ArrayList<String>();
		for (int fila = 0; fila < categoriasfreq.size(); fila++) {// filas

			// System.out.print(categoriasfreq.get(fila).getNombreCategoria());
			String row = (categoriasfreq.get(fila).getNombreCategoria());
			for (int column = 1; column < headersList.size(); column++) {// columnas
				// System.out.print(";" +
				// String.format("%.2f",categoriasfreq.get(fila).getFreqPermiso(headersList.get(column))));
				row = row.concat(";" + String.format("%.2f", categoriasfreq.get(fila).getFreqPermiso(headersList.get(column))));

			}
			// System.out.println(Main.FechayHora()+"");
			tabla.add(row);
		}

		return tabla;
	}

	public List<String> sendTcriterios() {
		List<String> tabla = new ArrayList<String>();

		for (Entry<String, ArrayList<Integer>> cat : tcriterios.entrySet()) {
			String row = "";
			row = row.concat(cat.getKey());
			ArrayList<Integer> permisos = cat.getValue();
			for (Integer p : permisos) {
				row = row.concat(";" + p);
			}
			tabla.add(row);
		}

		return tabla;
	}

	private void getNombreColumnasTablaGeneral() throws SQLException {
		ResultSet columnas = ejecutarSelectQuery("SHOW COLUMNS FROM permisosapp");
		nombreColumnastablaGeneral.clear();
		while (columnas.next()) {
			nombreColumnastablaGeneral.add(columnas.getString("Field"));
		}
	}

	private void getCriteriosTable() throws SQLException {
		ResultSet FTable = ejecutarSelectQuery("SELECT * FROM tcriterios");
		String categoria;
		tcriterios.clear();
		ArrayList<Integer> linea;
		// int numappscat;// numero de aplicaciones analizadas en esa categoria
		while (FTable.next()) {// itera por fila
			linea = new ArrayList<Integer>();
			categoria = FTable.getString(nombreColumnastablaFreq.get(0)); // Mete la categoria en el array list

			for (int i = 1; i < nombreColumnastablaCriterios.size(); i++) {
				linea.add(FTable.getInt(nombreColumnastablaCriterios.get(i)));

			}
			tcriterios.put(categoria, linea);

		}

	}

	private void getFreqTable() throws SQLException {
		ResultSet FTable = ejecutarSelectQuery("SELECT * FROM tablafreqabs");
		Categoria cat;
		String categoria, nombrenumappscat;
		String nombrecolumnacategoria = nombreColumnastablaFreq.get(0);
		// int numappscat;// numero de aplicaciones analizadas en esa categoria
		while (FTable.next()) {// itera por fila
			categoria = FTable.getString(nombreColumnastablaFreq.get(0));// saca la categoria que es
			nombrenumappscat = nombreColumnastablaFreq.get(1);
			boolean existe = false;
			if ((cat = contieneCategoria(categoria)) != null) {// comparo si la categoria esta ya dentro
				existe = true;
			} else {
				cat = new Categoria(FTable.getString(nombrecolumnacategoria));// busca la columna categoria -> devuelve
																				// el nombre de
				// la categoria
			}
			cat.setNumdeapps(FTable.getInt(nombrenumappscat));
			for (int i = 2; i < nombreColumnastablaFreq.size(); i++) {
				cat.setPermiso(nombreColumnastablaFreq.get(i), FTable.getInt(nombreColumnastablaFreq.get(i)));
			}
			if (!existe) {
				categoriasfreq.add(cat);
			}
		}
	}

	private Categoria contieneCategoria(String categoria) {
		for (int i = 0; i < categoriasfreq.size(); i++) {
			if (categoriasfreq.get(i).getNombreCategoria().equalsIgnoreCase(categoria))
				return categoriasfreq.get(i);
		}

		return null;
	}

	public void cerrarConexion() throws SQLException {
		stm.close();
	}

	public void subirAppsBBDD() {
		/*
		 * Para acutalizar la BBDD primero vamos a comprobar si la app que queremos
		 * subir existe ya en la BBDD y si es igual a la nueva, es decir, no cambia
		 * ninguno de sus campos. Si son iguales no se ejecuta la query INSERT IGNORE,
		 * si algunos de los campos cambia la apps que esta en la tabla permisosapp se
		 * pasa a una tabla de historicos con la fecha del momento del cambio, y se
		 * inserta la nueva version dentro de permisosapp.
		 */

		System.out.println(Main.FechayHora()+"dentro funcion subirappsbbdd, apps en cache: " + cache.size());
		try {
			ArrayList<String> appbbddnoformat = new ArrayList<String>();
			ArrayList<String> appbbddwithformat;
			for (Map.Entry<String, ArrayList<String>> app : cache.entrySet()) {
				System.out.println(Main.FechayHora()+"dentro for app: " + app.getKey());
				

				ResultSet rs = ejecutarSelectQuery(querySelectAppPermisosapp(app.getValue().get(1)));

				if (!rs.next()) {// si no devuelve nada se inserta la app en la tabla permisosapp

					ejecutarUpdateQuery(queryParaActualizarTablaPermisosappsyHistorico("permisosapp", app.getValue()));

				} else {

					for (int i = 1; i <= nombreColumnastablaGeneral.size(); i++)
						appbbddnoformat.add(rs.getString(i));
				
				appbbddwithformat = formatearResultadoSelect(appbbddnoformat);
				
				
				appbbddnoformat.clear();

				if (compararMismaApp(appbbddwithformat, app.getValue())) {

				} else {
					
					ejecutarUpdateQuery(queryParaActualizarTablaPermisosappsyHistorico("historico", appbbddwithformat));// inserta la version anterior de la app en la tabla historico
					ejecutarUpdateQuery(queryDeleteAppPermisosapp(app.getValue().get(1)));//elimina la version anterior de la tabla permisosapp
					ejecutarUpdateQuery(queryParaActualizarTablaPermisosappsyHistorico("permisosapp", app.getValue()));//inserta la nueva version dentro de la tabla permisosapp
					appbbddwithformat.clear();
					
				}
				
				}

			}

			cache.clear();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println(Main.FechayHora()+"Error en la ejecucion del update query en MySQL");
			e.printStackTrace();
		}

	}

	private boolean compararMismaApp(ArrayList<String> appbbdd, ArrayList<String> appcache) {

		if (appbbdd.size() == appcache.size()) {
			for (int i = 0; i < appbbdd.size(); i++) {
				if (!appbbdd.contains(appcache.get(i))) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}

	}

	private ArrayList<String> formatearResultadoSelect(ArrayList<String> appbbdd) {
		ArrayList<String> appformat = new ArrayList<String>();
		for (int i = 0; i < 3; i++)// nombre, id, categoria
			appformat.add(appbbdd.get(i));

		for (int i = 3; i < appbbdd.size(); i++) {// permisos, su formato esta en 0 y 1 por cada permiso, hay que
													// cambiarlo a nombre completo
			int permiso = Integer.parseInt(appbbdd.get(i));
			if (permiso == 1) {
				appformat.add(nombreColumnastablaGeneral.get(i));
			}

		}

		return appformat;
	}

	// ----------------------------------------------------------QUERYS SQL--------------------------------------------------------------------

	public ResultSet ejecutarSelectQuery(String query) {
		ResultSet rs = null;
		try {
			rs = stm.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println(Main.FechayHora()+"Error en la ejecucion del select query en MySQL");
			e.printStackTrace();
		}

		return rs;
	}

	public void ejecutarUpdateQuery(String query) throws SQLException {

		stm.executeUpdate(query);

		// TODO Auto-generated catch block

	}

	private String queryParaEliminarTablaFreqEnMySQL() {
		return "DROP TABLE IF EXISTS tablafreqabs;";
	}

	private String querySelectAppPermisosapp(String app) {
		String query = "SELECT * FROM `permisosapp` WHERE `Id App` = '" + app + "'";

		return query;
	}
	
	private String queryDeleteAppPermisosapp(String app) {
		String query = "DELETE FROM `permisosapp` WHERE `Id App` = '" + app + "'";

		return query;
	}

	private String queryParaActualizarTablaFreqEnMySQL() {

		return "CREATE TABLE tablafreqabs AS SELECT Categoria, COUNT(Categoria) AS 'NumApps',  \r\n"
				+ "SUM(`android.permission.ACCEPT_HANDOVER`) AS 'android.permission.ACCEPT_HANDOVER', \r\n"
				+ "SUM(`android.permission.ACCESS_BACKGROUND_LOCATION`) AS 'android.permission.ACCESS_BACKGROUND_LOCATION', \r\n"
				+ "SUM(`android.permission.ACCESS_COARSE_LOCATION`) AS 'android.permission.ACCESS_COARSE_LOCATION',\r\n"
				+ "SUM(`android.permission.ACCESS_FINE_LOCATION`) AS 'android.permission.ACCESS_FINE_LOCATION',\r\n"
				+ "SUM(`android.permission.ACCESS_MEDIA_LOCATION`) AS 'android.permission.ACCESS_MEDIA_LOCATION', \r\n"
				+ "SUM(`android.permission.ACTIVITY_RECOGNITION`) AS 'android.permission.ACTIVITY_RECOGNITION', \r\n"
				+ "SUM(`android.permission.ADD_VOICEMAIL`) AS 'android.permission.ADD_VOICEMAIL',\r\n"
				+ "SUM(`android.permission.ANSWER_PHONE_CALLS`) AS 'android.permission.ANSWER_PHONE_CALLS',\r\n"
				+ "SUM(`android.permission.BODY_SENSORS`) AS 'android.permission.BODY_SENSORS' ,\r\n"
				+ "SUM(`android.permission.CALL_PHONE`) AS 'android.permission.CALL_PHONE', \r\n"
				+ "SUM(`android.permission.CAMERA`) AS 'android.permission.CAMERA',\r\n"
				+ "SUM(`android.permission.GET_ACCOUNTS`) AS 'android.permission.GET_ACCOUNTS',\r\n"
				+ "SUM(`android.permission.PROCESS_OUTGOING_CALLS`) AS 'android.permission.PROCESS_OUTGOING_CALLS',\r\n"
				+ "SUM(`android.permission.READ_CALENDAR`) AS 'android.permission.READ_CALENDAR', \r\n"
				+ "SUM(`android.permission.READ_CALL_LOG`) AS 'android.permission.READ_CALL_LOG', \r\n"
				+ "SUM(`android.permission.READ_CONTACTS`) AS 'android.permission.READ_CONTACTS', \r\n"
				+ "SUM(`android.permission.READ_EXTERNAL_STORAGE`) AS 'android.permission.READ_EXTERNAL_STORAGE', \r\n"
				+ "SUM(`android.permission.READ_PHONE_NUMBERS`) AS 'android.permission.READ_PHONE_NUMBERS',\r\n"
				+ "SUM(`android.permission.READ_PHONE_STATE`) AS 'android.permission.READ_PHONE_STATE',\r\n"
				+ "SUM(`android.permission.READ_SMS`) AS 'android.permission.READ_SMS', \r\n"
				+ "SUM(`android.permission.RECIEVE_MMS`) AS 'android.permission.RECIEVE_MMS', \r\n"
				+ "SUM(`android.permission.RECIEVE_SMS`) AS 'android.permission.RECIEVE_SMS',\r\n"
				+ "SUM(`android.permission.RECIEVE_WAP_PUSH`) AS 'android.permission.RECIEVE_WAP_PUSH', \r\n"
				+ "SUM(`android.permission.RECORD_AUDIO`) AS 'android.permission.RECORD_AUDIO',\r\n"
				+ "SUM(`android.permission.SEND_SMS`) AS 'android.permission.SEND_SMS', \r\n"
				+ "SUM(`android.permission.USE_SIP`) AS 'android.permission.USE_SIP', \r\n"
				+ "SUM(`android.permission.WRITE_CALL_LOG`) AS 'android.permission.WRITE_CALL_LOG', \r\n"
				+ "SUM(`android.permission.WRITE_CONTACTS`) AS 'android.permission.WRITE_CONTACTS', \r\n"
				+ "SUM(`android.permission.WRITE_EXTERNAL_STORAGE`) AS 'android.permission.WRITE_EXTERNAL_STORAGE'\r\n"
				+ "FROM permisosapp GROUP BY Categoria";
	}

	private String queryParaActualizarTablaPermisosappsyHistorico(String nombretabla, ArrayList<String> cache) {

		int max = cache.size();
		String valorpermisos = "";
		String query = "INSERT IGNORE INTO `" + nombretabla + "`(\r\n" + "    `Nombre App`,\r\n" + "    `Id App`,\r\n"
				+ "    `Categoria`";

		for (int i = 3; i < max; i++) {

			query = query.concat(", `" + cache.get(i) + "`");
			if (i + 1 == max) {
				valorpermisos = valorpermisos.concat("1");
			} else {
				valorpermisos = valorpermisos.concat("1, ");
			}
		}
		query = query.concat(") VALUES (");

		for (int i = 0; i < 3; i++) {// hace un bucle de 3 porque pone nombre, id y categoria, luego el string de
										// valorpermisos

			query = query.concat("'" + cache.get(i) + "',");

		}
		if (max <= 3) {
			query = query.substring(0, query.length() - 1);

		}

		query = query.concat(valorpermisos + " )");

		System.out.println(Main.FechayHora()+query);

		return query;

	}

	/*
	 * 
	 * public String printFreqTable() {
	 * 
	 * List<String> headersList = new ArrayList<String>(nombreColumnastablaFreq);
	 * headersList.remove(1);
	 * 
	 * List<List<String>> rowsList = new ArrayList<List<String>>();
	 * 
	 * for (int fila = 0; fila < categoriasfreq.size(); fila++) {// filas
	 * List<String> row = new ArrayList<String>();
	 * row.add(categoriasfreq.get(fila).getNombreCategoria()); for (int column = 1;
	 * column < headersList.size(); column++) {// columnas
	 * 
	 * row.add(String.format("%.2f",
	 * categoriasfreq.get(fila).getFreqPermiso(headersList.get(column))));
	 * 
	 * } rowsList.add(row); } Board board = new Board(1504); Table table = new
	 * Table(board, 1504, headersList, rowsList); // List<Integer> colWidthsList =
	 * Arrays.asList(14, 14, 13, 14, 14); table.setColWidthsList(47);
	 * 
	 * Block tableBlock = table.tableToBlocks();
	 * 
	 * board.setInitialBlock(tableBlock); board.build(); String preview1 =
	 * board.getPreview(); System.out.println(Main.FechayHora()+"TABLE EXAMPLE 1");
	 * System.out.println(Main.FechayHora()+preview1);
	 * 
	 * return preview1; }
	 */
}
