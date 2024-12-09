package project.game.horde.states;

import java.awt.Graphics;
import project.game.horde.main.Handler;

public abstract class State {
	public abstract void tick();
	public abstract void render(Graphics g);
	
	protected static Handler handler;
	
	private static State currentState = null;
	
	public State(Handler handler) {
		State.handler = handler;
	}
	
	
	public static void setState(State state) {
		currentState  = state;
		State.handler.getGame().resetManagers();

	}
	
	public static State getState() {
		return currentState;
	}
	

}
