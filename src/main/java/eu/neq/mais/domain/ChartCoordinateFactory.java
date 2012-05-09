package eu.neq.mais.domain;

public class ChartCoordinateFactory {
	

	public ChartCoordinateX getChartCoordinate(double x, double y) {
		return new ChartCoordinateX(x, y);
	}
	
	public ChartCoordinateTimeStamp getChartCoordinate(long timestamp, double y) {
		return new ChartCoordinateTimeStamp(timestamp, y);
	}

	
	public class ChartCoordinateX {
		
		double x;
		double y;
		
		public ChartCoordinateX(double x, double y) {
			this.x = x;
			this.y = y;
		}

		public double getY() {
			return y;
		}
		public void setY(double y) {
			this.y = y;
		}
		
		public double getX() {
			return x;
		}
		public void setX(double x) {
			this.x = x;
		}
	}
	
	public class ChartCoordinateTimeStamp {
		
		long timestamp;
		double y;
		
		public ChartCoordinateTimeStamp(long timestamp, double y) {
			this.timestamp = timestamp;
			this.y = y;			
		}
		

		public double getY() {
			return y;
		}
		public void setY(double y) {
			this.y = y;
		}

		public long getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(long timestamp) {
			this.timestamp = timestamp;
		}
		
	}

}
