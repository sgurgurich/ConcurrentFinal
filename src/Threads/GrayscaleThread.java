package Threads;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.util.concurrent.ArrayBlockingQueue;

public class GrayscaleThread implements Runnable{

	private ArrayBlockingQueue<BufferedImage> converter_queue;
	private ArrayBlockingQueue<BufferedImage> save_queue;

	// takes an image
	public GrayscaleThread(ArrayBlockingQueue<BufferedImage> converter_queue, ArrayBlockingQueue<BufferedImage> save_queue){
		this.converter_queue = converter_queue;
		this.save_queue = save_queue;
	}

	// if there's something on the converter_queue, take it off, grayscale it, and add it do the save_queue
	public void run(){
		while(true){
			
			if (!converter_queue.isEmpty()){
				long startTime = System.currentTimeMillis();  // Start timing
				try {


					BufferedImage tmp = converter_queue.take();
					ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
					ColorConvertOp op = new ColorConvertOp(cs, null);

					tmp = op.filter(tmp, null);
					save_queue.put(tmp);

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				long endTime = System.currentTimeMillis();  // Start timing

				long final_time = endTime - startTime;
				System.out.println(final_time);
			}
		}
	}
	
	// overloaded to stop this thread if there's an issue
	public void stop(){
		
	}
}


