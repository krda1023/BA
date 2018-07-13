import java.util.ArrayList;

public class Distanzmatrix {
	
	
	static double[][] matrix;
	static int CreatingnumOfCities;
	static City startCity;
	static ArrayList<double[][]> allMatrix= new ArrayList<double[][]>();	

	
	public static void createAll_Cities (){		
		
			double[][] zwischenmatrix;
			String s= "C:\\Users\\BADai\\git\\BA\\TSP_EA\\src\\TSP-Instanz-Karlsruhe";
			readFile rf= new readFile(s);
			rf.readingFile();
			CreatingnumOfCities=rf.getNumberofCities();		
			zwischenmatrix=rf.getCoordinates();
			for(int a=0;a<CreatingnumOfCities;a++) {			
				City neueStaedte = new City(Integer.toString(a),"City",zwischenmatrix[a][1],zwischenmatrix[a][0]);			//Wechsel von Position Latitude -> Longitude
				All_Cities.addCity(neueStaedte);
			}
			startCity=All_Cities.getCity(0);
		
		
	}
																				//Methode erzeugt Send_Request Object mit erg[][]als Distanzmatrix im zweidim. array
	public static void createDurationMatrix() {
			matrix= new double[CreatingnumOfCities+1][CreatingnumOfCities+1]; 
			try {		
		        
		        matrix=Send_Request.createBasicMatrix();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	public static void createAllMatrixes(){
		
		for(int i=0; i<24;i++) {
			double faktor= Maths.getFaktor(i);
			double newMatrix[][] = new double[matrix.length][matrix.length];
			for(int j=0;j<matrix.length-1;j++) {				//Matrix wird mit +1 Stellen extra erzeugt für Zwischenwerte
				for(int k=0; k<matrix.length-1;k++) {
					newMatrix[j][k]=matrix[j][k]*faktor;
				}
			}
			allMatrix.add(newMatrix);
		}
		allMatrix.add(matrix);
	}
	
	public static void updateAllMatrix() throws Exception {
		double faktor;
		double[]IntersectionMatrix=Send_Request.IntersectionMatrix(All_Cities.getCity(All_Cities.numberOfCities()-1));
		for(int i=0;i<matrix.length-1;i++) {
			matrix[matrix.length-1][i]=IntersectionMatrix[i];
		}
		for(int i=0; i<24;i++) {
			faktor= Maths.getFaktor(i);
			for(int j=0;j<matrix.length-1;j++) {				//Matrix wird in BasicMatrix mit +1 Stellen extra erzeugt für Zwischenwerte
				
					allMatrix.get(i)[matrix.length-1][j]=IntersectionMatrix[j]*faktor;
				}
			
			}
		/*for(int i=0; i<matrix.length;i++) {
		for(int j=0; j<matrix.length;j++) {
			System.out.print(matrix[i][j]+" ");
		}
		System.out.println();
		}*/
		}
	
	public static double [][] getDistanzmatrix(){
		return matrix;
	}
	
	public static ArrayList<double [][]> getAllMatrix(){
		return allMatrix;
	}

}