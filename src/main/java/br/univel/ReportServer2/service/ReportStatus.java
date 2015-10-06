package br.univel.ReportServer2.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService
public class ReportStatus {

	@WebMethod(operationName = "getUrl")
	@WebResult(name = "result")
	public String status(@WebParam(name = "senha") int a) {

		String sURL = "http://localhost:8080/ReportServer2/rest/reportparams/senha/"+ a;
		String retorno = "";
		
		try {
			InputStream is = new URL(sURL).openStream();
//			BufferedReader rd = new BufferedReader(new InputStreamReader(is,Charset.forName("UTF-8")));
//			String jsonText = readAll(rd);
//			JSONObject json = new JSONObject(jsonText);
//			
//			if (json.getInt("senha") <= 0)
			if(is.read() == -1)
				retorno = "Erro ao procurar o report";
			else
				retorno = sURL;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return retorno;
	}
	
	private static String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	  }
}
