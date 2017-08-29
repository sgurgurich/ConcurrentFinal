import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
   
        ArrayList<String> file_list = new ArrayList<String>();
        
        //Populate the file list
		file_list.add("EclipseSvalbard_Santikunaporn_960.jpg");
		file_list.add("GS_20150401_SolarHalo_8814_DayNight.jpg");
		file_list.add("HokusaiOblique_2015h800c.jpg");
		file_list.add("M97_M108_LRGB_60p_APF_ckaltseis2015_1024.jpg");
		file_list.add("Messier46DenisPRIOU1024.jpg");
		file_list.add("Mooooonwalk_rjn_960.jpg");
		file_list.add("MysticMountain_HubbleForteza_960.jpg");
		file_list.add("N2903JewelofLeo_hallas_c1024.jpg");
		file_list.add("Ring0644_hubble_960.jpg");
		file_list.add("TLE2015_1024x821olsen.jpg");
		file_list.add("TLEGoldenGate_rba_d.jpg");
		file_list.add("TethysRingShadow_cassini_1080.jpg");
		file_list.add("Thumbs.db");
		file_list.add("VirgoCentral_Subaru_960.jpg");
		file_list.add("VolcanoWay_montufar_960.jpg");
		file_list.add("barnard344_vanderHoeven_960.jpg");
		file_list.add("hs-2015-13-a-web_voorwerpjes600h.jpg");
		file_list.add("ngc3293_eso_960.jpg");
		file_list.add("rNGC-4725-HaL(AOX)RGBpugh1024.jpg");
		file_list.add("snowtrees_bonfadini_960.jpg");
		file_list.add("tafreshi_MG_3456Pc2s.jpg");
    
        ThreadManager tm = new ThreadManager(file_list);
        tm.spawnThreads();

	}

}
