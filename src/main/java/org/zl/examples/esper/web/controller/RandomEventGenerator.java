/**
 * Created on 2015年3月31日
 */
package org.zl.examples.esper.web.controller;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.zl.examples.esper.event.DoorEvent;
import org.zl.examples.esper.web.handler.DefaultEventHandler;

/**
 * <br>
 * change log:<br>
 * 0.01 2015年3月31日 Created <br>
 * @version 0.01
 * @author Ray
 */
@Controller
public class RandomEventGenerator {

	private static Logger log = LoggerFactory.getLogger(RandomEventGenerator.class);

	@Autowired
	//@Qualifier("eventHandler")
	private DefaultEventHandler eventHandler;

	@RequestMapping(value="/door/{jsp}", method = RequestMethod.POST)
	public String ocDoor(@PathVariable("jsp") String jspName,String door,String opr){
		if(log.isDebugEnabled()){
			log.debug("ocDoor jsp:"+jspName);
			log.debug("ocDoor door:"+door);
		}
		DoorEvent event = new DoorEvent(door,opr);
		
		eventHandler.handle(event);
		
		return jspName;
	}
	
	@RequestMapping(value="/randomdoor/{jsp}", method = RequestMethod.POST)
	public String randomDoor(@PathVariable("jsp") String jspName,String num,String sleeptime){
		if(log.isDebugEnabled()){
			log.debug("ocDoor jsp:"+jspName);
			log.debug("ocDoor num:"+num);
			log.debug("ocDoor sleeptime:"+sleeptime);
		}
		
		final int n = NumberUtils.toInt(num, 10);
		
		final int t = NumberUtils.toInt(sleeptime, 100);
		
		ExecutorService executor = Executors.newSingleThreadExecutor();
		
		executor.execute(new Runnable() {

			@Override
			public void run() {
				log.debug("loop["+n+"]");
				for (int i = 0; i < n; i++) {
					int co = new Random().nextInt(2);
					int door = new Random().nextInt(4);
					log.debug("door:"+door+",co:"+co);
					DoorEvent event = new DoorEvent(door,co);
					eventHandler.handle(event);
					
					try {
						Thread.sleep(t);
					} catch (InterruptedException e) {
						log.error("Thread Interrupted", e);
					}
				}
			}
			
		});
		executor.shutdown();
		return jspName;
	}
}
