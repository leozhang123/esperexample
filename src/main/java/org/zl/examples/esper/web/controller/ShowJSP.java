/**
 * Created on 2015年3月31日
 */
package org.zl.examples.esper.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * <br>
 * change log:<br>
 * 0.01 2015年3月31日 Created <br>
 * @version 0.01
 * @author Ray
 */
@Controller
public class ShowJSP {

	private static Logger log = LoggerFactory.getLogger(ShowJSP.class);
	
	private SimpMessageSendingOperations messagingTemplate;
	
	String msg;
	
	@Autowired
	public ShowJSP(SimpMessagingTemplate messagingTemplate) {
		this.messagingTemplate = messagingTemplate;
	}

	@RequestMapping(value="/{jsp}", method = RequestMethod.GET)
	public String showJSP(@PathVariable("jsp") String jspName){
		if(log.isDebugEnabled()){
			log.debug("jsp:"+jspName);
		}
		return jspName;
	}
	
	@MessageMapping("/sendmsg")
	public void sendMessage(String msg){
		if(log.isDebugEnabled()){
			log.debug("send Message:"+msg);
		}
		messagingTemplate.convertAndSend("/queue/pmsg",msg);
	}
	
	@SubscribeMapping("/pmsg")
	public String pMessage(){
		if(log.isDebugEnabled()){
			log.debug("pMessage:");
		}
		return "{\"msg\":\"abcc\"}";
	}
	
	@MessageExceptionHandler
	@SendToUser("/queue/errors")
	public String handleException(Throwable exception) {
		if(log.isDebugEnabled()){
			log.debug("handleException:"+exception);
		}
		return exception.getMessage();
	}
}
