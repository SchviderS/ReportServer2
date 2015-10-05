package br.univel.ReportServer2;

import java.util.concurrent.Future;

import javax.ejb.Remote;

import br.univel.ReportServer2.model.ReportParams;

@Remote
public interface ReportQueueRemote
{
	
	Future<Integer> sendToQueue(ReportParams params);
	
}