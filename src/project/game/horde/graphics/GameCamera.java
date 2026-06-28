package project.game.horde.graphics;

import project.game.horde.entities.Entity;
import project.game.horde.main.Handler;

public class GameCamera {

    private Handler handler;
    private float xOffset, yOffset;

    public GameCamera(Handler handler, float xOffset, float yOffset) {
        this.handler = handler;
        this.xOffset = xOffset;
        this.yOffset = yOffset;

    }

    // public void centerOnEntity(Entity e) {
    //     double zoom = handler.getSettings().getZoomLevel(false);
    //     // xOffset = (float) (e.getX() - zoom
    //     // 		+ e.getWidth()/2  / zoom);
    //     // yOffset = (float) (e.getY() - zoom
    //     // 		+ e.getHeight()/2 / zoom);
    //     xOffset = (float) (e.getX() + e.getWidth() / 2.0
    //             - (handler.getWidth() / 2.0) / zoom);
    //     yOffset = (float) (e.getY() + e.getHeight() / 2.0
    //             - (handler.getHeight() / 2.0) / zoom);
    // }
    public void centerOnEntity(Entity e) {
        double zoom = handler.getSettings().getZoomLevel(false);

        double viewWidth = handler.getGame().getDisplay().getCanvas().getWidth() / zoom;
        double viewHeight = handler.getGame().getDisplay().getCanvas().getHeight() / zoom;

        xOffset = (float) (e.getX() - e.getWidth() - viewWidth / 2f);
        yOffset = (float) (e.getY()- viewHeight / 2f);
    }

    public void move(float xAmt, float yAmt) {
        xOffset += xAmt * handler.getSettings().getZoomLevel(false);
        yOffset += yAmt * handler.getSettings().getZoomLevel(false);
    }

    public float getxOffset() {
        return xOffset;
    }

    public void setxOffset(float xOffset) {
        this.xOffset = xOffset;
    }

    public float getyOffset() {
        return yOffset;
    }

    public void setyOffset(float yOffset) {
        this.yOffset = yOffset;
    }
}
