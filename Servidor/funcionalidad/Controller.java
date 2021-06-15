package funcionalidad;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;

import basededatos.BBDD;
import server.Servidor;


public class Controller {
	private BBDD bbdd;
	private Servidor server;
	private ActualizarBBDD actualizarBBDD;
	
	
	public Controller() throws IOException {
		bbdd = new BBDD();
		server = new Servidor(this);
		actualizarBBDD = new ActualizarBBDD(this); 
		
        
	}
	public void start() throws SQLException, IOException {
		String comando = null;
		bbdd.getTablas(); //carga las tablas de la base de datos
		
		server.start();
        BufferedReader reader =  new BufferedReader(new InputStreamReader(System.in));
        Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 3);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND,0);
		Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(actualizarBBDD, today.getTime(), ActualizarBBDD.UNA_VEZ_AL_DIA);
        
     
     
		
        
		while(true) {
		comando = reader.readLine();
		System.out.println(Main.FechayHora()+"Su comando es: " + comando);
		if(comando.replaceAll("\\s","").equalsIgnoreCase("actualizar")) {
			actualizarBBDD();
		}
		}
		
	}
	public void actualizarBBDD() throws SQLException {
		System.out.println(Main.FechayHora()+"Subiendo apps..");
		bbdd.subirAppsBBDD();
		bbdd.actualizarTablasDesdeCero();
		bbdd.getTablas();
		System.out.println(Main.FechayHora()+"Apps subidas!");
	}
	
	public List<String> sendFreqTabla() {
		return bbdd.sendFreqTable();
	}
	public List<String> sendTcriterios(){
		return bbdd.sendTcriterios();
	}
	
	public void comprobarApps(List<String> aplicaciones) {
		
	
			
			Iterator<String> it = aplicaciones.iterator();
		while(it.hasNext()) {
			 
			String aplicacion= it.next();
			bbdd.comprobarApp(aplicacion);
			
			}
		
		
		
		
	}
	
	 
	
}
