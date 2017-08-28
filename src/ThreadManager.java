import java.awt.image.BufferedImage;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import Threads.DownloadThread;
import Threads.GrayscaleThread;
import Threads.SaveThread;

public class ThreadManager {
	
	private int max_threads;
	private ExecutorService fixedExecutorService;
	private ArrayBlockingQueue<BufferedImage> converter_queue;
	private ArrayBlockingQueue<BufferedImage> save_queue;
	
	
	
	public ThreadManager(){
		// sets the threadpool to the best size for your System
		this.max_threads = Runtime.getRuntime().availableProcessors();
		fixedExecutorService = Executors.newFixedThreadPool(this.max_threads);
		converter_queue = new ArrayBlockingQueue<BufferedImage>(50);
		save_queue = new ArrayBlockingQueue<BufferedImage>(50);
	}

	public void spawnThreads(){
		//TODO: this logic is temporary
		String full_url = "http://elvis.rowan.edu/~mckeep82/ccpsu17/Astronomy/GS_20150401_SolarHalo_8814_DayNight.jpg";
		String url_2 = "http://elvis.rowan.edu/~mckeep82/ccpsu17/Astronomy/GS_20150401_SolarHalo_8814_DayNight.jpg";
		
		
		
		GrayscaleThread gst = new GrayscaleThread(converter_queue, save_queue);
		SaveThread st = new SaveThread(save_queue);

	
		fixedExecutorService.execute(gst);
		fixedExecutorService.execute(st);
		
		// add a for loop to loop through all of the images
		DownloadThread dt = new DownloadThread(full_url, converter_queue);
		DownloadThread dt2 = new DownloadThread(url_2, converter_queue);
		fixedExecutorService.execute(dt);
		fixedExecutorService.execute(dt2);

		
		fixedExecutorService.shutdown();
		try {
			fixedExecutorService.awaitTermination(3, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
}
