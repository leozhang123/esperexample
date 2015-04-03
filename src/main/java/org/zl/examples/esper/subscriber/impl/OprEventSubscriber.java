/**
 * Created on 2015年3月31日
 */
package org.zl.examples.esper.subscriber.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zl.examples.esper.subscriber.StatementSubscriber;

/**
 * <br>
 * change log:<br>
 * 0.01 2015年3月31日 Created <br>
 * @version 0.01
 * @author Ray
 */
public class OprEventSubscriber implements StatementSubscriber {

	/** Logger */
    private static Logger LOG = LoggerFactory.getLogger(OprEventSubscriber.class);
    
	/* (non-Javadoc)
	 * @see org.zl.examples.esper.subscriber.StatementSubscriber#getStatement()
	 */
	@Override
	public String getStatement() {
		return "select doorId,operation,x,y from DoorEvent";
	}

	/**
     * Listener method called when Esper has detected a pattern match.
     */
    public void update(Map<String, String> eventMap) {

        LOG.debug(eventMap.get("doorId")+"门被"+eventMap.get("operation"));
    }
}
