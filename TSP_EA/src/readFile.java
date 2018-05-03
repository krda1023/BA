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
	int id;
	int countlines=0;
	
	
	public int getNumberofCities()
	{
		return countlines;
	}
	public double[][] getMatrix()
	{
		return erg;
	}
	
	public readFile(String f)
	{
		this.path=f;
		this.file= new File(f);
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
		
		erg= new double[countlines][3];
		
		
	
		for(int j=0;j<countlines;j++)
		{
		String a=s.next();
		String b= s.next();
		String c=s.next();
		id=Integer.parseInt(a);
		erg[(id-1)][0]=(double)id;
		erg[(id-1)][1]=Double.parseDouble(b);
		erg[(id-1)][2]=Double.parseDouble(c);
	


		}
		
		s.close();
		
		
	
	}
}
