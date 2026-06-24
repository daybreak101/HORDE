package project.game.horde.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import project.game.horde.entities.creatures.Player;
import project.game.horde.main.Handler;
import project.game.horde.states.State;
import project.game.horde.ui.UIManager;

public class GameMouseManager implements MouseListener, MouseMotionListener, MouseWheelListener{
	
	private boolean leftPressed, rightPressed, mouseScrolled,
					wheelUp, wheelDown;
	public boolean attack;
	private int mouseX, mouseY;
	private Handler handler;
	
	private UIManager uiManager;
	private Player player;

	public GameMouseManager(Handler handler, Player player) {
		this.handler = handler;
		this.player = player;
	}
	
	public void reset() {
		leftPressed = false;
		rightPressed = false;
	}
	
	public void setUIManager(UIManager uiManager) {
		this.uiManager = uiManager;
	}
	
	
	//getters
	public boolean isLeftPressed() {
		return leftPressed;
	}
	
	public boolean isRightPressed() {
		return rightPressed;
	}
	
	public boolean isMouseScrolled() {
		return mouseScrolled;
	}
	
	
	public int getMouseX() {
		return mouseX;
	}
	
	public int getMouseY() {
		return mouseY;
	}
	
	//implemented methods
	
	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		
		if(uiManager != null)
			uiManager.onMouseMove(e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			leftPressed = true;
			//return;
		}
			
		if(e.getButton() == MouseEvent.BUTTON3) {
			rightPressed = true;
			//return;
		}
			
	}
	

	@Override
	public void mouseReleased(MouseEvent e) {
		if(uiManager != null)
			uiManager.onMouseRelease(e);
		
		if(State.getState() == handler.getGame().gameState && e.getButton() == MouseEvent.BUTTON1 && player.getInv().getGun() != null) {
			player.getInv().getGun().setReadyToFire(false);
			leftPressed = false;
		}
		if(State.getState() == handler.getGame().gameState && e.getButton() == MouseEvent.BUTTON3 && player.getInv().getGun() != null) {
			player.getInv().getGun().setAltReadyToFire(false);
			rightPressed = false;
		}
	}
		


	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		mouseScrolled = true;
		if(e.isControlDown()) {
			if(e.getWheelRotation() < 0) {
				//mouse wheel up
				wheelUp = true;
				
				//to get value of how much rotation
				//e.getPreciseWheelRotation();
			}
			else {
				//mouse wheel down
				wheelDown = true;
			}
		}
	}

	public void setMouseScrolled(boolean mouseScrolled) {
		this.mouseScrolled = mouseScrolled;
	}
	
	

}