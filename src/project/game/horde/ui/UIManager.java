package project.game.horde.ui;

import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import project.game.horde.main.Handler;

public class UIManager {
	
	private Handler handler;
	private ArrayList<UIObject> objects;
	

	public UIManager(Handler handler) {
		this.handler = handler;
		objects = new ArrayList<UIObject>();
	}
	
	public void tick() {
		for(UIObject o: objects)
			o.tick();
			
	}
	
	public void render(Graphics g) {
		for(UIObject o: objects)
			if(o.isVisible)
				o.render(g);
	}
	
	public void onMouseMove(MouseEvent e) {
		for(UIObject o: objects)
			o.onMouseMove(e);
	}
	
	public void onMouseRelease(MouseEvent e) {
	    System.out.println("Mouse release event received.");
	    for (UIObject o: objects) {
	        if (o.getIsVisible()) {
	            //System.out.println("Checking UIObject: " + o);
	            o.onMouseRelease(e);
	        }
	    }
	}
	
	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public ArrayList<UIObject> getObjects() {
		return objects;
	}

	public void setObjects(ArrayList<UIObject> objects) {
		this.objects = objects;
	}

	public void addObject(UIObject o) {
		objects.add(o);
	}
	
	public void addObjects(ArrayList<TextButton> objects) {
		for(TextButton o: objects) {
			this.objects.add(o);
		}
	}
	
	public void removeObject(UIObject o) {
		objects.remove(o);
	}
}
