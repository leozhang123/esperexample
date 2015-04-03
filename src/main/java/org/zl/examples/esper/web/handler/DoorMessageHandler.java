/**
 * Created on 2015年3月31日
 */
package org.zl.examples.esper.web.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * <br>
 * change log:<br>
 * 0.01 2015年3月31日 Created <br>
 * @version 0.01
 * @author Ray
 */
@EnableWebSocket
public class DoorMessageHandler extends TextWebSocketHandler implements
		WebSocketConfigurer {

	private static Logger log = LoggerFactory.getLogger(DoorMessageHandler.class);
	
	/* (non-Javadoc)
	 * @see org.springframework.web.socket.config.annotation.WebSocketConfigurer#registerWebSocketHandlers(org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry)
	 */
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(myMessageHandler(), "/wserver").withSockJS();
	}

	@Bean
    public DoorMessageHandler myMessageHandler() {
        return new DoorMessageHandler();
    }
	
	/* (non-Javadoc)
	 * @see org.springframework.web.socket.handler.AbstractWebSocketHandler#handleTextMessage(org.springframework.web.socket.WebSocketSession, org.springframework.web.socket.TextMessage)
	 */
	@Override
	protected void handleTextMessage(WebSocketSession session,
			TextMessage message) throws Exception {
		super.handleTextMessage(session, message);
		log.debug("handleTextMessage");
		TextMessage returnMessage = new TextMessage(message.getPayload()+" received at server");
		session.sendMessage(returnMessage);

	}
}
