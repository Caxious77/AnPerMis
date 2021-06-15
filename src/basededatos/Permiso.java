package basededatos;

public class Permiso {
	private String name;
	private int appsconestepermiso = 0;
	private double freqpermiso = 0;
	public Permiso(String n, int nump) {
		name = n;
		appsconestepermiso = nump;
	}
	
	public int getAppsconestepermiso() {
		return appsconestepermiso;
	}


	public void setAppsconestepermiso(int appsconestepermiso) {
		this.appsconestepermiso = appsconestepermiso;
	}


	public double getFreqpermiso() {
		return freqpermiso;
	}


	public void setFreqpermiso(double numerodeApps) {
		if(numerodeApps==0)
			this.freqpermiso = 0;
		else
			this.freqpermiso = appsconestepermiso/numerodeApps;
	}

	public String getName() {
		return name;
	}
	public void incrementarEnUnoEstePermiso() {
		appsconestepermiso++;
	}

}
