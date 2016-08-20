package org.usfirst.frc.team4141.MDRobotBase.eventmanager;

import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

public class EventManagerWebSocketCreator implements WebSocketCreator {
	private EventManager eventManager;
	public EventManagerWebSocketCreator(EventManager eventManager) {
		this.eventManager = eventManager;
	}
	public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse res) {
		return new EventManagerWebSocket(eventManager);
	}
}
