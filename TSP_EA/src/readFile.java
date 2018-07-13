import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class readFile {

	File file;
	BufferedReader br;
	Scanner s ;
	String path;
	double[][] erg;
	
	int countlines=0;
	
	public readFile(String f)
	{
		this.path=f;
		this.file= new File(f);
	}
	
	public int getNumberofCities()
	{
		return countlines;
	}
	
	public double[][] getCoordinates()
	{
		return erg;
	}
		
	public void readingFile()
	{
		try
		{
			br= new BufferedReader(new FileReader(file));
			s = new Scanner(file);
		}
		catch(FileNotFoundException e)
		{
			System.out.print("File not found");
		}
	
	
		
		
		String line="";
		
		try
		{
			while((line=br.readLine())!=null)
			{
				countlines+=1;
			}
		}
		catch(IOException ioex)
		{
			System.out.print("Error reading file");
		}
		
		erg= new double[countlines][2];
		
		
	
		for(int j=0;j<countlines;j++)
		{
		
		String b= s.next();
		String c=s.next();
		
		
		erg[j][0]=Double.parseDouble(b);
		erg[j][1]=Double.parseDouble(c);
	


		}
		
		s.close();
		
		
	
	}
}
