package funcionalidad;

import java.sql.SQLException;
import java.util.TimerTask;

//Queremos actualizar las tablas todos los dias a las 3 AM
public class ActualizarBBDD extends TimerTask{
	public final static long UNA_VEZ_AL_DIA = 1000*60*60*24;
	private Controller ctrl;
	
	public ActualizarBBDD(Controller ctrl) {
		// TODO Auto-generated constructor stub
		this.ctrl = ctrl;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			System.out.println(Main.FechayHora()+"Actualizacion automatica programada!");
			ctrl.actualizarBBDD();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println(Main.FechayHora()+"Error en la clase ActualizarBBDD");
			e.printStackTrace();
		}
		
	}

}
