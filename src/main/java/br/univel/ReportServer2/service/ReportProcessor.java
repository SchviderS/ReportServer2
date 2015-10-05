package br.univel.ReportServer2.service;

import java.io.Serializable;
import java.sql.Date;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.univel.ReportServer2.dao.ReportParamsDao;
import br.univel.ReportServer2.model.ReportParams;
import br.univel.ReportServer2.rest.ReportParamsEndpoint;

@MessageDriven(name = "ReportJMSQueue", activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "queue/ReportJMSQueue"),
		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") })
public class ReportProcessor implements Serializable, MessageListener {
	@PersistenceContext(unitName = "ReportServer2-persistence-unit")
	private EntityManager em;

	private static final long serialVersionUID = -1L;

	public void onMessage(Message message) {
		TextMessage msg = null;
		ReportParams params = null;

		try {
			if (message instanceof TextMessage) {
				msg = (TextMessage) message;

				// criar o novo objeto para gravar no h2 com os parametros que
				// vieram
				params = new ReportParams();
				params.setNome(msg.getStringProperty("nome"));
				params.setDataInicio(new java.sql.Date(msg
						.getLongProperty("dataInicio")));
				params.setDataFim(new java.sql.Date(msg
						.getLongProperty("dataFim")));

				int senha = msg.getIntProperty("senha");
				params.setSenha(senha);

				System.out.println("Mensagem com a senha " + senha
						+ " recebida!");
			} else {
				System.out.println("Message of wrong type: "
						+ message.getClass().getName());
			}

		} catch (JMSException e) {
			e.printStackTrace();
		}
		System.out.println("Gravando no H2...");
		
		// gravar no h2 para usar no jax ws depois
		try{
			em.persist(params);
			System.out.println("Gravado com sucesso!");
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Erro ao gravar...");
		}
		
		System.out.println("Registros gravados podem ser vistos em:");
		System.out.println("http://localhost:8080/ReportServer2/rest/reportparams");
		System.out.println("-------------------");
		System.out.println("Registro inserido agora pode ser visto em:");
		System.out.println("http://localhost:8080/ReportServer2/rest/reportparams/senha/"+params.getSenha());
	
	}
}