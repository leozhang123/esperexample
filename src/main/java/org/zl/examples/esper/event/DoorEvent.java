/**
 * Created on 2015年3月31日
 */
package org.zl.examples.esper.event;

/**
 * <br>
 * change log:<br>
 * 0.01 2015年3月31日 Created <br>
 * @version 0.01
 * @author Ray
 */
public class DoorEvent extends BaseEvent {

	private String doorId;
	private String operation;
	
	public DoorEvent(String doorId, String operation) {
		super();
		this.doorId = doorId;
		this.operation = operation;
		setX(100);
		setY(200);
	}
	
	public DoorEvent(int doorId, int operation) {
		switch(doorId){
		case 0:
			this.doorId = "A1";
			setX(207);
			setY(301);
			break;
		case 1:
			this.doorId = "A2";
			setX(100);
			setY(200);
			break;
		case 2:
			this.doorId = "A3";
			setX(100);
			setY(300);
			break;
		case 3:
			this.doorId = "A4";
			setX(100);
			setY(400);
			break;
		case 4:
			this.doorId = "A5";
			setX(100);
			setY(500);
			break;
		}
		
		switch(operation){
		case 1:
			this.operation = "open";
			break;
		default:
			this.operation = "close";
		}
		
	}

	@Override
	public String toString() {
		return "DoorEvent [doorId=" + doorId + ", operation=" + operation + "]";
	}

	/**
	 * @return the doorId
	 */
	public String getDoorId() {
		return doorId;
	}

	/**
	 * @return the operation
	 */
	public String getOperation() {
		return operation;
	}
	
}
