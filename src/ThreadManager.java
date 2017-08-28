import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadManager {
	
	private int max_threads;
	private ExecutorService fixedExecutorService;
	
	
	public ThreadManager(int max_threads){
		this.max_threads = max_threads;
		fixedExecutorService = Executors.newFixedThreadPool(this.max_threads);
	}

	public void spawnThreads(){
		
	}
	
	
	
	
	
}
