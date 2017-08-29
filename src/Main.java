import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
   
        ArrayList<String> file_list = new ArrayList<String>();
        
        //Populate the file list
		file_list.add("GS_20150401_SolarHalo_8814_DayNight.jpg");
		file_list.add("Thumbs.db");
    
        ThreadManager tm = new ThreadManager(file_list);
        tm.spawnThreads();

	}

}
