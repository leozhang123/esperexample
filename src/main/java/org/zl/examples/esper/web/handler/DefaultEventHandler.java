/**
 * Created on 2015年3月31日
 */
package org.zl.examples.esper.web.handler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import org.zl.examples.esper.event.BaseEvent;
import org.zl.examples.esper.subscriber.StatementSubscriber;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

/**
 * <br>
 * change log:<br>
 * 0.01 2015年3月31日 Created <br>
 * @version 0.01
 * @author Ray
 */
@Component
@Scope(value = "singleton")
public class DefaultEventHandler implements InitializingBean, DisposableBean {

	private static Logger log = LoggerFactory.getLogger(DefaultEventHandler.class);
	
	/** Esper service */
    private EPServiceProvider epService;
    
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

	@Autowired
    private List<StatementSubscriber> subscriberList;
    
	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.DisposableBean#destroy()
	 */
	@Override
	public void destroy() throws Exception {
		epService.destroy();
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		initEvent();
	}

	public void initEvent(){
		log.debug("Initializing Servcie ..");
        Configuration config = new Configuration();
        config.addEventTypeAutoName("org.zl.examples.esper.event");
        epService = EPServiceProviderManager.getDefaultProvider(config);
        
        for (StatementSubscriber subscriber : subscriberList) {
        	log.debug("init subscriber:"+subscriber);
             EPStatement monitorEventStatement = epService.getEPAdministrator().createEPL(subscriber.getStatement());
             monitorEventStatement.setSubscriber(subscriber);
		}
       
	}
	
	public void handle(BaseEvent event) {
		log.debug(event.toString());
        epService.getEPRuntime().sendEvent(event);
        messagingTemplate.convertAndSend("/topic/door", event);
	}
}
