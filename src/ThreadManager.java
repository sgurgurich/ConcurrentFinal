import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import Threads.DownloadThread;
import Threads.GrayscaleThread;
import Threads.ImageInfo;
import Threads.SaveThread;
import net.jcip.annotations.ThreadSafe;

/**
 * 
 * ThreadManager generates thread pools used
 * for the downloading, converting, and saving
 * of a list of images based on their file name.
 * An ExecutorService is used for each different 
 * type of task. 
 * 
 * @author Zach Adam
 * @author John Cutsavage
 * @author Stefan Gurgurich
 *
 */
@ThreadSafe
public class ThreadManager {
	
	private int max_threads;
	private ExecutorService download_pool;
	private ExecutorService converter_pool;
	private ExecutorService save_pool;
	private ArrayBlockingQueue<ImageInfo> converter_queue;
	private ArrayBlockingQueue<ImageInfo> save_queue;
	private ArrayList<String> file_list;
	
	
	/**
	 * Constructor for creating a ThreadManager object.
	 * Upon creation, an ExecutorService is created for
	 * each the downloading service, the grayscale
	 * converting service, and the saving service. The
	 * pool for DownloadThreads is a FixedThreadPool
	 * that accomodates the number of available threads
	 * the user's computer has available.
	 * 
	 * @param file_list a list of file names to pass to
	 * the image downloading threads.
	 */
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

	/**
	 * Creates and runs instances of DownloadThreads and
	 * a single instance of both a GrayscaleThread and
	 * SaveThread. The number of DownloadThreads created
	 * is dependent on how large file_list is. Once all
	 * threads are allocated, each ExecutorService will
	 * try to ensure the threads exit gracefully. In the
	 * event of long runtimes, the thread pools will
	 * timeout and terminate after 300 seconds. 
	 */
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
			
		
			gst.stop();
			converter_pool.shutdown();
			converter_pool.awaitTermination(300, TimeUnit.SECONDS);
			
			
			st.stop();
			save_pool.shutdown();
			save_pool.awaitTermination(300, TimeUnit.SECONDS);
			
		} catch (InterruptedException e) {
			download_pool.shutdownNow();
			converter_pool.shutdownNow();
			save_pool.shutdownNow();
		}
		
		
	}
	
	
}
