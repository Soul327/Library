package Misc;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Graphics {
	public int fontSize = 15, width = 0, height = 0;
	public Graphics2D g;
	final public boolean defaultScaleable = false;
	public boolean scalable = defaultScaleable;
	public BufferedImage[] layers;
	public Graphics[] gl;
	
	public Graphics(java.awt.Graphics g) { this.g = (Graphics2D) g; }
	public Graphics(Graphics2D g) { this.g = g; }
	public void setColor(Color color) { g.setColor(color); }
	public void setDim(int width, int height) { this.width = width; this.height = height; }
	public void initLayers(int num) { initLayers(num, width, height); }
	public void initLayers(int num, int width, int height) {
		layers = new BufferedImage[num];
		gl = new Graphics[num];
		for(int z=0;z<layers.length;z++) {
			layers[z] = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			gl[z] = new Graphics((Graphics2D)layers[z].getGraphics());
		}
	}
	public void renderLayers() {
		for(BufferedImage image:layers)
			drawImage(image);
	}
	public void drawRect(double x, double y, double width, double height) {
		g.drawRect((int)x, (int)y, (int)width, (int)height);
		g.drawRect((int)x, (int)y, (int)width, (int)height);
	}
	public void fillRect(double x, double y, double width, double height) {
		g.fillRect((int)x, (int)y, (int)width, (int)height);
	}
	public void drawOval(double x, double y, double width, double height) {
		g.drawOval((int)x, (int)y, (int)width, (int)height);
	}
	public void fillOval(double x, double y, double width, double height) {
		g.fillOval((int)x, (int)y, (int)width, (int)height); }
	public void drawCircle(double x, double y, double size) {
		g.drawOval((int)(x), (int)(y), (int)size, (int)size);
	}
	public void drawCircle(Point p, double size) {
		g.drawOval((int)(p.getX()), (int)(p.getY()), (int)size, (int)size);
	}
	public void drawCenterCircle(double x, double y, double size) {
		g.drawOval((int)(x-size/2), (int)(y-size/2), (int)size, (int)size); 
	}
	public void fillCircle(double x, double y, double size) {
		g.fillOval((int)x, (int)y, (int)size, (int)size);
	}
	public void fillCenterCircle(double x, double y, double size) {
		g.fillOval((int)(x-size/2), (int)(y-size/2), (int)size, (int)size);
	}
	public void fillCircle(Point p, double size) {
		g.fillOval((int)(p.getX()), (int)(p.getY()), (int)size, (int)size);
	}
	public void drawImage(BufferedImage img,double x, double y, double width, double height) {
		if(img!=null)
			g.drawImage(img, (int)x, (int)y, (int)width, (int)height, null);
	}
	public void drawImage(BufferedImage img) {
		if(img!=null)
			g.drawImage(img, 0, 0, img.getWidth(), img.getHeight(), null);
	}
	public void drawRotatedImage(BufferedImage image,double x, double y, double width, double height, double angle) {
		if(angle%2!=0) {
			BufferedImage rotated = Assets.rotateImageByDegrees(image,angle);
			double tempx = (double)rotated.getWidth()/image.getWidth();
			double tempy = (double)rotated.getHeight()/image.getHeight();
			drawImage(rotated,x-(width*(tempx-1))/2,y-(height*(tempy-1))/2,width*tempx,height*tempy);
		}else
			drawImage(image,x,y,width,height);
	}
	public void drawString(String str, double x, double y) { 
		if(str==null)
			g.drawString("null", (int)x, (int)y);
		else
			g.drawString(str, (int)x, (int)y);
	}
	public void drawOutlinedString(String str, double x, double y) {
		drawOutlinedString(str, x, y, Color.white, Color.black);
	}
	public void drawOutlinedString(String str, double x, double y, Color strColor, Color outlineColor) {
		int borderAmount = 1;
		g.setColor(outlineColor);
		drawString(str, x+borderAmount, y);
		drawString(str, x-borderAmount, y);
		drawString(str, x, y+borderAmount);
		drawString(str, x, y-borderAmount);
		g.setColor(strColor);
		drawString(str, x, y);
	}
	public void drawLine(double x1, double y1, double x2, double y2) { g.drawLine((int)x1, (int)y1, (int)x2, (int)y2); }
	public void drawLine(Point point,Point point2) { g.drawLine((int)point.getX(), (int)point.getY(), (int)point2.getX(), (int)point2.getY()); }
	public void drawLine(DataPoint point,DataPoint point2) { g.drawLine((int)point.x, (int)point.y, (int)point2.x, (int)point2.y); }
	public void drawRLine(double x1, double y1, double x2, double y2) {
		x2 = x2+x1;
		y2 = y2+y1;
		g.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
	}
	public void drawArc(double x, double y, double width, double height, double startAngle, double arcAngle) {
		g.drawArc((int)x, (int)y, (int)width, (int)height, (int)startAngle, (int)arcAngle);
	}
	public void setFont(Font font) { g.setFont(font); }
	public void setFontSize(int size) {
		//Not working
		Font currentFont = g.getFont();
		Font newFont = currentFont.deriveFont(currentFont.getSize() * 1.4F);
		g.setFont(newFont);
		
	}
	public int getStringLength(String s) { return g.getFontMetrics().stringWidth(s); }
	public int getFontHeight() { return g.getFontMetrics().getHeight(); }
	public FontMetrics getFontMetrics() { return g.getFontMetrics(); }
	public void drawShape(Shape shape) { g.draw(shape); }
	public void drawPoly(Polygon shape) {
		g.draw(shape);
	}
	public Polygon rotatePolygon(Polygon p1,double angle,Point center) {
		Polygon newPolygon = new Polygon();
		for(int z=0;z<p1.npoints;z++) {
			Point p=new Point(p1.xpoints[z],p1.ypoints[z]);
			p = rotatePoint(p,center,angle);
			newPolygon.addPoint(p.x,p.y);
		}
		return newPolygon;
	}
	public Polygon rotatePolygon(Polygon p1,double angle) {
		Polygon newPolygon = new Polygon();
		Point center=new Point(0,0);
		double tempSumX = 0,tempSumY=0;
		
		for(int z=0;z<p1.npoints;z++) {
			tempSumX +=p1.xpoints[z];
			tempSumY +=p1.ypoints[z];
		}
		center.setLocation(tempSumX/p1.npoints, tempSumY/p1.npoints);
		for(int z=0;z<p1.npoints;z++) {
			Point p=new Point(p1.xpoints[z],p1.ypoints[z]);
			p = rotatePoint(p,center,angle);
			newPolygon.addPoint(p.x,p.y);
		}
		return newPolygon;
	}
	public Point rotatePoint(Point pt, Point center, double angleDeg) {
		double angleRad = Math.toRadians(angleDeg);
		double cosThetha = Math.cos(angleRad); //The angle COS
		double sinThetha = Math.sin(angleRad); //The angle SIN
		double dx = (pt.x - center.x); //Difference (Point in transformed to origo)
		double dy = (pt.y - center.y); //Difference -- || --

		int ptX = center.x + (int) (dx * cosThetha - dy * sinThetha);
		int ptY = center.y + (int) (dx * sinThetha + dy * cosThetha);

		return new Point(ptX, ptY);
	}
	public void setFont(String font, int style, double size) {
		fontSize = (int)size;
		g.setFont(new Font(font, style, (int)size));
	}
}