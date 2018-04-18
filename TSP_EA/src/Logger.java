import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
public class Logger {
	File logger;
    BufferedWriter logwrite;
    
    public Logger() throws IOException
    {
    	logger=new File("log.txt");
        logger.createNewFile();
        FileWriter logwritef = new FileWriter(logger);
        logwrite = new BufferedWriter(logwritef, 100000);
    }
   public void print(String s)
    {
              try {
            logwrite.write(s+"\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
              //System.out.println(s);
    }
   public void exit()
   {
       print("Applikation wird beendet! @" + (new Date().toString()));
       try {logwrite.close();}catch(IOException e){e.printStackTrace();}
       System.exit(0);
   }
}
