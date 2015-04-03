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
public class MonitorEventSubscriber implements StatementSubscriber {

	/** Logger */
    private static Logger LOG = LoggerFactory.getLogger(MonitorEventSubscriber.class);
    
	/* (non-Javadoc)
	 * @see org.zl.examples.esper.subscriber.StatementSubscriber#getStatement()
	 */
	@Override
	public String getStatement() {
		// Example of simple EPL with a Time Window
		String e = "select a.doorId, count(*) from pattern [every a=DoorEvent -> (timer:interval(10 sec) and not DoorEvent(doorId=a.doorId))] group by doorId";

        e="select doorId,operation,x,y from DoorEvent(operation='open').win:time_batch(5 sec)";
		return e;
	}

	/**
     * Listener method called when Esper has detected a pattern match.
     */
    public void update(Map<String, String> eventMap) {

        LOG.debug("MonitorEventSubscriber:"+eventMap);
    }
}
