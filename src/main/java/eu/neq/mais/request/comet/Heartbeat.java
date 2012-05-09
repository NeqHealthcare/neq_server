package eu.neq.mais.request.comet;

public class Heartbeat {
	
	private int x;
	
	private double[] y;
	
	public Heartbeat() {
		initializeY();
	}
	
	public double getNextY() {
		if (x < y.length-1) {
			return y[x++];
		}
		else {
			x = 0;
			return y[x++];
		}
	}

	private void initializeY() {
		int i = 0;
		y = new double[26];
		
		y[i++] = 0; //0
		y[i++] = 0; //1
		y[i++] = 0.1;
		y[i++] = -0.1;
		y[i++] = 1;
		y[i++] = 0;
		y[i++] = -1;
		y[i++] = 0;
		y[i++] = 0.2;
		y[i++] = 0.3;
		y[i++] = 0.4;
		y[i++] = 0.5;
		y[i++] = 0.0;
		y[i++] = -0.5;
		y[i++] = -0.25;
		y[i++] = 0.1;
		y[i++] = -0.1;
		y[i++] = 1;
		y[i++] = 0;
		y[i++] = -1;
		y[i++] = 0;
		y[i++] = 0;
		y[i++] = 0;
		y[i++] = 0;
		y[i++] = 0;
		y[i++] = 0;
			
		x = 0;
		
	}

}
