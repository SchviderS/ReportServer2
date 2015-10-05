package br.univel.ReportServer2.service;

import java.util.concurrent.Future;

import javax.annotation.Resource;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TextMessage;

import br.univel.ReportServer2.ReportQueueRemote;
import br.univel.ReportServer2.model.ReportParams;


@Stateless
@Remote(ReportQueueRemote.class)
public class ReportQueue implements ReportQueueRemote
{

   private static final long serialVersionUID = -1L;
   
   @Inject
   private JMSContext context;

   @Resource(lookup = "java:/queue/ReportJMSQueue")
   private Queue queue;

	@Asynchronous
	@Override
	public Future<Integer> sendToQueue(ReportParams params) {
		
		int senha = (int) Math.round(Math.random()*100);
		TextMessage msg = context.createTextMessage();
		
		try {
			msg.setStringProperty("nome", params.getNome());
			msg.setLongProperty("dataInicio", params.getDataInicio().getTime());
			msg.setLongProperty("dataFim", params.getDataFim().getTime());
			msg.setIntProperty("senha", senha);
			context.createProducer().send(queue, msg);
		} catch (JMSException e) {
			e.printStackTrace();
		}
		
		System.out.println("Parametros enviados para queue, gerando senha...");
		return new AsyncResult<Integer>(senha);
	}
   
}