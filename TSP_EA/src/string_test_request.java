

public class string_test_request {
	double[][] xundy;
	 double[][] ergs;
	
	



public string_test_request(double[][] coordinates) {
		 this.xundy=coordinates;
	}



public String call_me() throws Exception {
	
	String urlAnfang = "https://api.openrouteservice.org/matrix?api_key=%0958d904a497c67e00015b45fce60fe6750d3e4061a1e3178c1db4f08e&profile=driving-car&locations=";
	 String zwischenerg="";
	 for(int i=0; i<xundy.length;i++)
	 {
		 for (int j=0; j<2;j++)
		 {
			 zwischenerg += Double.toString(xundy[i][j]);
			 if (j==0)
				 zwischenerg+="%2C";
			 if ((i+1)==xundy.length & j==1)
			 {}
			 if (j==1 & (i+1)<xundy.length)
				 zwischenerg+="%7C";
		 }
	 }
	 String gesamt=urlAnfang+zwischenerg;
	 return gesamt;
}}
