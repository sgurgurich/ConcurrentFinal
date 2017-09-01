package Threads;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * 
 * SingleThreaded is a class designed
 * to perform a single-threaded version
 * of image downloading, converting to
 * grayscale, and saving to a local folder
 * on the user's computer. Specifically
 * downloads files listed on
 * http://elvis.rowan.edu/~mckeep82/ccpsu17/Astronomy/.
 * 
 * @author Zach Adam
 * @author John Cutsavage
 * @author Stefan Gurgurich
 *
 */
public class SingleThreaded {

	private ArrayList<String> file_list;
	
	/**
	 * 
	 * Constructor for SingleThreaded. Takes an 
	 * ArrayList of file names to be downloaded,
	 * converted to grayscale, and saved locally.
	 * 
	 * @param file_list a list of file names to download
	 */
	public SingleThreaded(ArrayList<String> file_list){
		this.file_list = file_list;
	}
	
	/**
	 * Every file in file_list is downloaded,
	 * converted to grayscaled, and saved to a
	 * local folder.
	 */
	public void run(){
		for (int i = 0; i < file_list.size(); i++){		
		
			BufferedImage image = downloadImg(file_list.get(i));

			if (image != null){
				image = convertImg(image);
				saveImg(image, file_list.get(i));
			}
		}
	}

	/**
	 * 
	 * Opens a URL connection to the specified file
	 * on http://elvis.rowan.edu/~mckeep82/ccpsu17/Astronomy/
	 * and downloads the image. Returns a BufferedImage of
	 * the file.
	 * 
	 * @param file_name the image's file name
	 * @return the downloaded image
	 */
	private BufferedImage downloadImg(String file_name){
		
		String url_str = "http://elvis.rowan.edu/~mckeep82/ccpsu17/Astronomy/" + file_name;			
		URL url;
		BufferedImage img = null;
		try {
			url = new URL(url_str);
			img = ImageIO.read(url);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException ie){
			
		}

		return img;	
	}
	
	/**
	 * 
	 * Converts the given BufferedImage to grayscale, then
	 * returns the new converted BufferedImage.
	 * 
	 * @param img the Buffered image to be converted
	 * @return the grayscaled image
	 */
	private BufferedImage convertImg(BufferedImage img){
		ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
		ColorConvertOp op = new ColorConvertOp(cs, null);
     
        img = op.filter(img, null);
        	
        return img;

	}
	
	/**
	 * 
	 * Saved a converted grayscale image to a folder
	 * local to the Java project.
	 * 
	 * @param img the BufferedImage to be saved
	 * @param name the file name to save the BufferedImage
	 * to in a local folder.
	 */
	private void saveImg(BufferedImage img, String name){
		
		try {
			File outputfile = new File("./SingleThreadImgs/" + name);
			ImageIO.write(img, "jpg", outputfile);
			System.out.println("Saved: " + name);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
