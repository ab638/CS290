package CH.ifa.draw.figures;

import CH.ifa.draw.standard.BoxHandleKit;
import CH.ifa.draw.util.StorableInput;
import CH.ifa.draw.util.StorableOutput;

import java.awt.*;
import java.io.IOException;
import java.util.Vector;

// Edited the drawFrame to draw the lines on the rectangle
// Everything else is required by the extension of AttributeFigure because
// it is abstract and yells at me
public class XboxFigure extends RectangleFigure{

    public XboxFigure(){
        this(new Point(0,0), new Point(0,0));
    }
    public XboxFigure(Point origin, Point corner){
        super(origin, corner);
    }

    public void drawBackground(Graphics g) {
        Rectangle r = displayBox();
        g.fillRect(r.x,r.y,r.width,r.height);
    }

    public void drawFrame(Graphics g) {

        Rectangle r = displayBox();
        g.drawRect(r.x, r.y, r.width-1, r.height-1);
        g.drawLine(r.x, r.y, r.width+r.x, r.height+r.y);
        g.drawLine(r.x, r.y+r.height, r.width+r.x, r.y);
    }



}
