package project.game.horde.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import project.game.horde.main.Handler;
import project.game.horde.states.State;
import project.game.horde.ui.UIManager;

public class MouseManager implements MouseListener, MouseMotionListener, MouseWheelListener {

	private boolean leftPressed, rightPressed, mouseScrolled, wheelUp, wheelDown;
	public boolean attack;
	private int mouseX, mouseY;
	private Handler handler;

	private UIManager uiManager;

	public MouseManager(Handler handler) {
		this.handler = handler;

	}

	public void reset() {
		leftPressed = false;
		rightPressed = false;
	}

	public void setUIManager(UIManager uiManager) {
		this.uiManager = uiManager;
	}

	// getters
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

	// implemented methods

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		mouseX = e.getX();
		mouseY = e.getY();
		// System.out.println(mouseX);

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		mouseX = e.getX();
		mouseY = e.getY();

		if (uiManager != null)
			uiManager.onMouseMove(e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getButton() == MouseEvent.BUTTON1) {
			leftPressed = true;
			// return;
		}

		else if (e.getButton() == MouseEvent.BUTTON3) {
			rightPressed = true;
			// return;
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if (uiManager != null)
			uiManager.onMouseRelease(e);

		if (e.getButton() == MouseEvent.BUTTON1) {
			leftPressed = false;
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			rightPressed = false;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		mouseScrolled = true;
		if (e.isControlDown()) {
			if (e.getWheelRotation() < 0) {
				// mouse wheel up
				wheelUp = true;

				// to get value of how much rotation
				// e.getPreciseWheelRotation();
			} else {
				// mouse wheel down
				wheelDown = true;
			}
		}
	}

	public void setMouseScrolled(boolean mouseScrolled) {
		this.mouseScrolled = mouseScrolled;
	}

}
