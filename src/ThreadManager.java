import java.awt.image.BufferedImage;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import Threads.DownloadThread;
import Threads.GrayscaleThread;
import Threads.ImageInfo;
import Threads.SaveThread;

public class ThreadManager {
	
	private int max_threads;
	private ExecutorService fixedExecutorService;
	private ArrayBlockingQueue<ImageInfo> converter_queue;
	private ArrayBlockingQueue<ImageInfo> save_queue;
	
	
	
	public ThreadManager(){
		// sets the threadpool to the best size for your System
		this.max_threads = Runtime.getRuntime().availableProcessors();
		fixedExecutorService = Executors.newFixedThreadPool(this.max_threads);
		converter_queue = new ArrayBlockingQueue<ImageInfo>(50);
		save_queue = new ArrayBlockingQueue<ImageInfo>(50);
	}

	public void spawnThreads(){
		//TODO: this logic is temporary
		String img1_name = "GS_20150401_SolarHalo_8814_DayNight.jpg";
		String img2_name = "Thumbs.db";
		
		
		GrayscaleThread gst = new GrayscaleThread(converter_queue, save_queue);
		SaveThread st = new SaveThread(save_queue);

		fixedExecutorService.execute(gst);
		fixedExecutorService.execute(st);
		
		// add a for loop to loop through all of the images
		DownloadThread dt = new DownloadThread(img1_name, converter_queue);
		DownloadThread dt2 = new DownloadThread(img2_name, converter_queue);
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
