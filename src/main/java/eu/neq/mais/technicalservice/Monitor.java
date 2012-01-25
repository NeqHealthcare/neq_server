package eu.neq.mais.technicalservice;

import java.io.File;
import java.io.IOException;

/**
 * Currently, this thread checks every 60 seconds if changes on the backendConfig file occured. 
 * If changes occured, the backendConfig is automatically reloaded
 * 
 * @author Jan Gansen
 *
 */
public class Monitor implements Runnable {

	private long backendConfigLastModified = 0;
	
	public void run() {
		while(true){
			File file = new File(Settings.BACKEND_CONFIG_FILE);
			if(file.lastModified()>backendConfigLastModified){
				try {
					FileHandler.reloadBackendMap();
					backendConfigLastModified = file.lastModified();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				 Thread.sleep(60000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
