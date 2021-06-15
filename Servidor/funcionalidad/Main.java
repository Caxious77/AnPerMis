package funcionalidad;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class Main {
	private static Controller controlador;
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		try {
			
			controlador = new Controller();
			controlador.start();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static String FechayHora() {
		String fechayhora = "";
        LocalDate hoy = LocalDate.now();
        LocalTime ahora = LocalTime.now();
        
        LocalDateTime fecha = LocalDateTime.of(hoy, ahora);
        
        fechayhora = fecha.toString().concat("  ");
		
		return fechayhora;
	}

}
