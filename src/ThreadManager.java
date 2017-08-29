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
	private ExecutorService fixedExecutorService;
	private ArrayBlockingQueue<ImageInfo> converter_queue;
	private ArrayBlockingQueue<ImageInfo> save_queue;
	private ArrayList<String> file_list;
	
	
	public ThreadManager(ArrayList<String> file_list){
		// sets the threadpool to the best size for your System
		this.max_threads = Runtime.getRuntime().availableProcessors();
		fixedExecutorService = Executors.newFixedThreadPool(this.max_threads);
		converter_queue = new ArrayBlockingQueue<ImageInfo>(50);
		save_queue = new ArrayBlockingQueue<ImageInfo>(50);
		this.file_list = file_list;
	}

	public void spawnThreads(){

		GrayscaleThread gst = new GrayscaleThread(converter_queue, save_queue);
		SaveThread st = new SaveThread(save_queue);

		fixedExecutorService.execute(gst);
		fixedExecutorService.execute(st);
		
		for (int i = 0; i < file_list.size(); i++){
			DownloadThread dt = new DownloadThread(file_list.get(i), converter_queue);
			fixedExecutorService.execute(dt);
		}
		
		
		//Shutdowns
		fixedExecutorService.shutdown();
		try {
			fixedExecutorService.awaitTermination(10, TimeUnit.SECONDS);
			gst.stop();
			st.stop();
			
		} catch (InterruptedException e) {

		}
		
		
	}
	
	
}
