package project.game.horde.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import project.game.horde.main.Handler;
import project.game.horde.ui.UIManager;

public class KeyManager implements KeyListener {

	private boolean[] keys;
	public boolean w, a, s, d;
	public boolean reload, switchWeapon1, switchWeapon2, use, grenade, sprint, melee;
	public boolean eAlreadyPressed = false;
	public boolean b,c,e,f,g,h,i,j,k,l,m,n,o,p,q,r,t,u,v,x,y,z;
	public boolean enter, backspace, escape, capslock;
	
	private UIManager uiManager;
	
	public KeyManager(Handler handler) {
		keys = new boolean[256];
		handler.getGame().getDisplay().getCanvas().addKeyListener(this);
		handler.getGame().getDisplay().getCanvas().setFocusable(true);
		handler.getGame().getDisplay().getCanvas().requestFocus();
	}
	
	public void setUIManager(UIManager uiManager) {
		this.uiManager = uiManager;
	}
	
	public void tick() {
		w = keys[KeyEvent.VK_W];
		a = keys[KeyEvent.VK_A];
		s = keys[KeyEvent.VK_S];
		d = keys[KeyEvent.VK_D];
		reload = keys[KeyEvent.VK_R];
		switchWeapon1 = keys[KeyEvent.VK_Q];
		switchWeapon2 = keys[KeyEvent.VK_E];
		use = keys[KeyEvent.VK_F];
		grenade = keys[KeyEvent.VK_G];
		sprint = keys[KeyEvent.VK_SHIFT];
		melee = keys[KeyEvent.VK_V];
		
		b = keys[KeyEvent.VK_B];
		c = keys[KeyEvent.VK_C];
		e = keys[KeyEvent.VK_E];
		f = keys[KeyEvent.VK_F];
		g = keys[KeyEvent.VK_G];
		h = keys[KeyEvent.VK_H];
		i = keys[KeyEvent.VK_I];
		j = keys[KeyEvent.VK_J];
		k = keys[KeyEvent.VK_K];
		l = keys[KeyEvent.VK_L];
		m = keys[KeyEvent.VK_M];
		n = keys[KeyEvent.VK_N];
		o = keys[KeyEvent.VK_O];
		p = keys[KeyEvent.VK_P];
		q = keys[KeyEvent.VK_Q];
		r = keys[KeyEvent.VK_R];
		t = keys[KeyEvent.VK_T];
		u = keys[KeyEvent.VK_U];
		v = keys[KeyEvent.VK_V];
		x = keys[KeyEvent.VK_X];
		y = keys[KeyEvent.VK_Y];
		z = keys[KeyEvent.VK_Z];
		
		enter = keys[KeyEvent.VK_ENTER];
		backspace = keys[KeyEvent.VK_BACK_SPACE];
		escape = keys[KeyEvent.VK_ESCAPE];
		capslock = keys[KeyEvent.VK_CAPS_LOCK];
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
		
	}
	
}
