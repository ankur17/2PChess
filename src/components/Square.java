package components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

import components.pieces.Piece;


public class Square implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final static Square INEXISTANT = new Square(-1, -1, -1, -1);
	public static int SQUARE_SIZE = 10;

	private Color color;
	private int x, y, indexX, indexY;
	private boolean selected = false;
	private boolean highlighted = false, debug = false;
	protected Piece piece;
	
	public void setSelected(){
		color = Color.yellow;
		selected = true;
	}
	
	public void setDebug(){ debug = true; }
	
	public void setHighlighted(){
		//color = new Color(100, 137, 97);
		highlighted = true;
	}
	
	public boolean isSelected(){ return selected; }
	
	public Square(int x, int y, int indexX, int indexY){
		this.x = x;
		this.y = y;
		
		this.indexX = indexX;
		this.indexY = indexY;
		
		setColor();
	}
	
	public void setGraphicalX(int x){ this.x = x; }
	public void setGraphicalY(int y){ this.y = y; }
	
	public void setSize(int s){
		SQUARE_SIZE = s;
	}
	
	public void setColor(){
		if(indexX%2 == 0 ^ indexY%2 == 0)
			color = new Color(2, 136, 209);
		else 
			color = new Color(179, 229, 252);
		selected = false;
		highlighted = false;
		debug = false;
	}
	
	public Color getColor(){ return color; }
	
	public void setPiece(Piece piece) {
		this.piece = piece;
	}
	
	public Piece getPiece() { return piece; }

	public int getGraphicalX(){ return x; }
	public int getGraphicalY(){ return y; }
	
	public int getX(){ return indexX; }
	public int getY(){ return indexY; }
	
	
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Rectangle2D r = new Rectangle2D.Double(x, y, SQUARE_SIZE, SQUARE_SIZE);
		g2.setColor(this.color);
		g2.fill(r);
		
		if(piece != null && piece.getImagePath() != ""){
			Image i = Toolkit.getDefaultToolkit().getImage(piece.getImagePath());
			
			g2.drawImage(i, x+SQUARE_SIZE/2 - i.getWidth(null)/2,
					y+SQUARE_SIZE/2 - i.getHeight(null)/2, null);
			
			
			g.drawImage(i, x+SQUARE_SIZE/2 - i.getWidth(null)/2,
					y+SQUARE_SIZE/2 - i.getHeight(null)/2, null);
			
		}
		if(isHighlighted()){
			g2.setColor(Color.yellow);
			g2.fillOval(x + SQUARE_SIZE/2 - 10, y+SQUARE_SIZE/2 - 10, 20, 20);
		}
		
		if(debug){
			//g2.setColor(Color.red);
			//g2.fillOval(x + SQUARE_SIZE/2 - 10, y+SQUARE_SIZE/2 - 10, 20, 20);
		}
	}

	public boolean isHighlighted() {
		return highlighted;
	}

	public Piece deletePiece() {
		Piece p = this.piece;
		this.piece = null;
		p.nullifySquare();
		return p;
	}

	protected void insertPiece(Piece moved) {
		this.piece = moved;
		piece.setSquare(this);
	}

	@Override
	public String toString() {
		return "("+indexX+", "+indexY+")";
	}
	
	
}