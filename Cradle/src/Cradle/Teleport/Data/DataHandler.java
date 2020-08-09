package Cradle.Teleport.Data;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

public class DataHandler implements Serializable {
	
	private static final long serialVersionUID = 2019346553888332169L;
	
	// Load one global spawn point.
	
	private String filePath = "";
	public String worldName = "";
	
    // Can be used for saving
    public DataHandler(String _filePath, DataGlobalSpawn data) {
    	filePath = _filePath;
        
        saveData(data);
    }
    
    // ?
    // Can be used for loading
    public DataHandler(DataHandler loadedData) {
        
    }
	
	public boolean saveData(Object data) {
        try {
        	BukkitObjectOutputStream out = new BukkitObjectOutputStream(new GZIPOutputStream(new FileOutputStream(filePath)));
            out.writeObject(data);
            out.close();
            return true;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }
	
	public static DataGlobalSpawn loadData(String filePath) {
        try {
            BukkitObjectInputStream in = new BukkitObjectInputStream(new GZIPInputStream(new FileInputStream(filePath)));
            DataGlobalSpawn data = (DataGlobalSpawn) in.readObject();
            in.close();
            
            return data;
        } catch (ClassNotFoundException | IOException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            return null;
        }
    }
}
