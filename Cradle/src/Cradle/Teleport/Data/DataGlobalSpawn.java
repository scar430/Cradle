package Cradle.Teleport.Data;

import java.io.Serializable;

public class DataGlobalSpawn implements Serializable {

	private static final long serialVersionUID = -144127186283471036L;
	
	public String worldname = "";
	public double x = 0.0;
	public double y = 0.0;
	public double z = 0.0;
	
	public DataGlobalSpawn(String _worldname, double _x, double _y, double _z) {
		worldname = _worldname;
		x = _x;
		y = _y;
		z = _z;
	}
}
