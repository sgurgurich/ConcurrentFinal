import java.util.ArrayList;
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
	private ExecutorService download_pool;
	private ExecutorService converter_pool;
	private ExecutorService save_pool;
	private ArrayBlockingQueue<ImageInfo> converter_queue;
	private ArrayBlockingQueue<ImageInfo> save_queue;
	private ArrayList<String> file_list;
	
	
	public ThreadManager(ArrayList<String> file_list){
		// sets the threadpool to the best size for your System
		this.max_threads = Runtime.getRuntime().availableProcessors();
		download_pool = Executors.newFixedThreadPool(this.max_threads);
		converter_pool = Executors.newSingleThreadExecutor();
		save_pool = Executors.newSingleThreadExecutor();
		converter_queue = new ArrayBlockingQueue<ImageInfo>(50);
		save_queue = new ArrayBlockingQueue<ImageInfo>(50);
		this.file_list = file_list;
	}

	public void spawnThreads(){

		GrayscaleThread gst = new GrayscaleThread(converter_queue, save_queue);
		SaveThread st = new SaveThread(save_queue);

		converter_pool.execute(gst);
		save_pool.execute(st);
		
		for (int i = 0; i < file_list.size(); i++){
			DownloadThread dt = new DownloadThread(file_list.get(i), converter_queue);
			download_pool.execute(dt);
		}
		
		
		//Shutdowns

		try {
			download_pool.shutdown();
			download_pool.awaitTermination(300, TimeUnit.SECONDS);
			
			converter_pool.shutdown();
			converter_pool.awaitTermination(15, TimeUnit.SECONDS);
			
			save_pool.shutdown();
			save_pool.awaitTermination(15, TimeUnit.SECONDS);
			
		} catch (InterruptedException e) {

		}
		
		
	}
	
	
}
