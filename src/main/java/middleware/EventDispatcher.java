package middleware;

import java.util.ArrayList;
import java.util.List;

public class EventDispatcher {
	public List<EventListener> listeners = new ArrayList<>();


	public void dispatchEvent(Event event) {
		for(EventListener listener : listeners) {
			listener.onEvent(event);
		}
	}
}
