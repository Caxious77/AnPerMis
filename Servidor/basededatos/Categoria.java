package basededatos;

import java.util.HashMap;

public class Categoria {
	private HashMap<String, Permiso> permisosCategoria;
	private String NombreCategoria;
	private Integer numdeapps;

	public Categoria(String nombre) {
		NombreCategoria = nombre;
		permisosCategoria = new HashMap<String, Permiso>();
		numdeapps = 0;
	}

	public String getNombreCategoria() {
		String n = NombreCategoria;
		return n;
	}

	public Double getFreqPermiso(String permiso) {
		double f = permisosCategoria.get(permiso).getFreqpermiso();
		return f;
	}

	public void actualizarfreqpermisos() {

	}

	public void setPermiso(String permiso, int numpermisos) {

		if (permisosCategoria.containsKey(permiso)) {
			permisosCategoria.get(permiso).setAppsconestepermiso(numpermisos);
			permisosCategoria.get(permiso).setFreqpermiso(numdeapps);
			
		} else {
			Permiso p = new Permiso(permiso, numpermisos);
			p.setFreqpermiso(numdeapps);
			
			permisosCategoria.put(permiso, p);
		}
	}

	public int getNumdeapps() {
		return numdeapps;
	}
	
	public String getNumdeappstoString() {
		return numdeapps.toString();
	}

	public void incrementarEnUnoNumApps() {
		numdeapps++;
	}

	public void setNumdeapps(int numdeapps) {
		this.numdeapps = numdeapps;
	}
	public String toString() {
		return "";
	}
}
