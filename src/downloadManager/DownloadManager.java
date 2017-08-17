/**
 * 
 */
package downloadManager;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author John Cutsavage
 *
 */
public class DownloadManager {
	
	private URLConnection connection;
	
	/**
	 * 
	 * @param url
	 */
	public DownloadManager(String url) {
		
		URL website;
		
		try {
			website = new URL(url);
			connection = website.openConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
