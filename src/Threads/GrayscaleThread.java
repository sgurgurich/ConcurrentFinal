package Threads;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.util.concurrent.ArrayBlockingQueue;

import net.jcip.annotations.ThreadSafe;

/**
 * 
 * GrayscaleThread is an implementation of
 * Runnable that retrieves images from a queue
 * sent by a DownloadThread, converts the image
 * to grayscale, then places the converted image
 * on a new queue to be used by a SaveThread. In
 * this respect, GrayscaleThread is both a producer
 * and consumer. There should only be one instance
 * of a GrayscaleThread at a time.
 * 
 * @author Zach Adam
 * @author John Cutsavage
 * @author Stefan Gurgurich
 *
 */
@ThreadSafe
public class GrayscaleThread implements Runnable{

	private ArrayBlockingQueue<ImageInfo> converter_queue;
	private ArrayBlockingQueue<ImageInfo> save_queue;
	private boolean exit;
	

	/**
	 * Constructor for GrayscaleThread. Takes two
	 * ArrayBlockingQueues of ImageInfo objects; one
	 * to pull download images, and one to push
	 * converted images onto in order to be saved
	 * to the computer.
	 * 
	 * @param converter_queue the queue used to retrieve
	 * images
	 * @param save_queue the queue used to send images
	 * to the SaveThread
	 */
	public GrayscaleThread(ArrayBlockingQueue<ImageInfo> converter_queue, ArrayBlockingQueue<ImageInfo> save_queue){
		this.converter_queue = converter_queue;
		this.save_queue = save_queue;
		this.exit = false;
	}

	/**
	 * Overrides Runnable.run(). While the converter queue
	 * still contains images, GrayscaleThread will pull
	 * the next image from the queue and convert it to grayscale.
	 * 
	 */
	public void run(){
		
		
		while(!exit){		
			if (!converter_queue.isEmpty()){
				ImageInfo tmp = null;
				synchronized(converter_queue){
					if (!converter_queue.isEmpty()){
						try {
							tmp = converter_queue.take();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
                if (!tmp.equals(null)){
				  processImg(tmp);
                }
                else{
                	this.exit = true;
                }
			}
		}
	}
	
	/**
	 * 
	 * Override of Runnable.stop(). Causes the current
	 * thread to stop, allowing it to finish processing 
	 * the current task before exiting.
	 * 
	 * @throws InterruptedException thrown when
	 * the thread is interrupted. 
	 */
	public void stop() throws InterruptedException{
		this.exit = true;
		while(!converter_queue.isEmpty()){
			ImageInfo tmp = null;
			synchronized(converter_queue){
				if (!converter_queue.isEmpty()){

					tmp = converter_queue.take();

				}
			}
            if (tmp != null){
			  processImg(tmp);
            }
 
		}
	}
	
	/**
	 * 
	 * Converts the passed in image to grayscale. Once
	 * the image has been processed, the grayscale
	 * version is put onto save_queue to be used by
	 * a SaveThread.
	 * 
	 * @param tmp temporary image that will be converted
	 * to grayscale.
	 */
	private  void processImg(ImageInfo tmp){

		try {
			BufferedImage img = tmp.getImg();
			ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
			ColorConvertOp op = new ColorConvertOp(cs, null);
            try{
            	img = op.filter(img, null);
            	tmp.setImg(img);
            	save_queue.put(tmp);
            }
            catch(NullPointerException ne){
            	System.out.println("Attempt to process image file " + tmp.getName() + " unsuccessful. File extension not valid.");
            }

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}


