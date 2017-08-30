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

public class SingleThreaded {

	private ArrayList<String> file_list;
	
	
	public SingleThreaded(ArrayList<String> file_list){
		this.file_list = file_list;
	}
	
	public void run(){
		for (int i = 0; i < file_list.size(); i++){		
		
			BufferedImage image = downloadImg(file_list.get(i));

			if (image != null){
				image = convertImg(image);
				saveImg(image, file_list.get(i));
			}
		}
	}

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
	
	private BufferedImage convertImg(BufferedImage img){
		ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
		ColorConvertOp op = new ColorConvertOp(cs, null);
     
        img = op.filter(img, null);
        	
        return img;

	}
	
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
