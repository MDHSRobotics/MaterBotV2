package org.usfirst.frc.team4141.MDRobotBase;

public class TankDriveInterpolator {
	private double a;
	private double b;
	public TankDriveInterpolator() {
		this(0,1);
	}
	public TankDriveInterpolator(double a, double b) {
		this.a=a;
		this.b=b;
	}
	public double getA() {
		return a;
	}
	public void setA(double a) {
		this.a = a;
	}
	public double getB() {
		return b;
	}
	public void setB(double b) {
		this.b = b;
	}
	private double R(double forward, double rotate) {
		return forward - b*rotate + forward*rotate*(b-a-1);
	}

	private double L(double forward, double rotate) {
		return forward+b*rotate*(1-forward);
	}
	
	public double[] calculate(double forward, double rotate){
		if (forward>=0) {
			if (rotate>=0){
				//Q1
				return new double[] {L(forward, rotate),R(forward,rotate)};
			}
			else{
				//Q2
				return new double[] {R(forward, -rotate),L(forward,-rotate)};
			}
		}
		else{
			if (rotate>=0){
				//Q4
				return  new double[] {-R(-forward, rotate),-L(-forward,rotate)};
			}
			else{
				//Q3
				return  new double[] {-L(-forward, -rotate),-R(-forward,-rotate)};
			}
		}
	}
	public static void main(String[] args) {
		test(new TankDriveInterpolator());
		test(new TankDriveInterpolator(0.5,0.5));
		test(new TankDriveInterpolator(0.25,0.4));

	}
	
	private static String formatCoordinates(double[] coordinates){
		if(coordinates!=null && coordinates.length>1){
		 return String.format("(%04.2f,%04.2f)", coordinates[0],coordinates[1]);
		}
		else return "";
	}

	private static String toStringTable(double[] range,String[][] contour){
		StringBuilder sb=new StringBuilder();

		for(int r=0;r<contour.length;r++){
			if(r==0){
				for(int c=0;c<range.length;c++){
					if(c>0){
						sb.append("\t");
					}
					sb.append("\t");
					sb.append(range[c]);
				}
				sb.append("\n");
			}
			for (int c=0;c<contour[r].length;c++){
				if(c==0){
					sb.append(range[range.length-1-r]);
				}
				sb.append("\t");
				sb.append(contour[r][c]);
			}
			sb.append("\n");
		}
		
		return sb.toString();
	}

	private static String toMarkDownTable(double[] range,String[][] contour){
		StringBuilder sb=new StringBuilder();
		for(int r=0;r<contour.length;r++){
			if(r==0){
				for(int c=0;c<range.length;c++){
					if(c==0){
						sb.append("");
					}
					sb.append(" | ");
					sb.append(range[c]);
				}
				sb.append("\n");
				for(int c=0;c<range.length;c++){
					if(c==0){
						sb.append("---");
					}
					sb.append(" | ");
					sb.append("---");
				}
				sb.append("\n");
			}
			for (int c=0;c<contour[r].length;c++){
				if(c==0){
					sb.append("__");
					sb.append(range[range.length-1-r]);
					sb.append("__");
				}
				sb.append(" | ");
				sb.append(contour[r][c]);
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	private static void test(TankDriveInterpolator tankDriveInterpolator) {
		// generate the contour map
		String[][] contour= new String[9][9];
		double[] range = {-1,-0.75,-0.5,-0.25,0,0.25,0.5,0.75,1};
		for(int row = 0;row<range.length;row++){
			for(int col = 0;col<range.length;col++){
				contour[row][col] = formatCoordinates(tankDriveInterpolator.calculate(range[range.length-1-row],range[col]));
			}
		}
		
		System.out.println(toStringTable(range,contour));
//		System.out.println(toMarkDownTable(range,contour));
				
	}

}



