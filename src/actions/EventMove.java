package actions;

import primary.Point;

public class EventMove extends Event {
	public String creatureName;
	Point destination;

	public EventMove(String name, Point newDestination) {
		creatureName = new String(name);
		destination = new Point(newDestination);
	}
	
	@Override
	public void processEvent() {
		return;
	}

}
