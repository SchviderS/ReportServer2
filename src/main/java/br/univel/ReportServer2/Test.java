package br.univel.ReportServer2;

import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import br.univel.ReportServer2.model.ReportParams;

public class Test {

	
	
	public static void main(String[] args) {
		
		testarEjbAndQueue();
		
	}

	private static void testarEjbAndQueue() {
		try {
			//não esquecer de colocar o arquivo jboss-ejb-client.properties na pasta src
	        Properties ejbProperties = new Properties();
	        ejbProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
	        
	        //pega o contexto do ejb das configurações
	        Context context = new InitialContext(ejbProperties);
	        
	        //injeta o ejb na variavel, usando o endereço de lookup
			ReportQueueRemote queue = (ReportQueueRemote) context.lookup("ejb:/ReportServer2/ReportQueue!" + ReportQueueRemote.class.getName());
			
			ReportParams params = new ReportParams();
			params.setNome("nome");
			params.setDataInicio(new java.sql.Date(new Date().getTime()));
			params.setDataFim(new java.sql.Date(new Date().getTime()));
	
			Future<Integer> result = queue.sendToQueue(params);
			
			try {
				System.out.println("Resultado aleatório vindo do EJB: " + result.get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
			
			context.close();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
}
