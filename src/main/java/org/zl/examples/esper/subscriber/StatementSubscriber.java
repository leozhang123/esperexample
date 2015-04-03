/**
 * Created on 2015年3月31日
 */
package org.zl.examples.esper.subscriber;

/**
 * <br>
 * change log:<br>
 * 0.01 2015年3月31日 Created <br>
 * @version 0.01
 * @author Ray
 */
public interface StatementSubscriber {

	/**
     * Get the EPL Stamement the Subscriber will listen to.
     * @return EPL Statement
     */
    public String getStatement();
}
