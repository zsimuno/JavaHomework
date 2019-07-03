package hr.fer.zemris.java.hw17.jvdraw.shapes;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw17.jvdraw.tools.Tool;

/**
 * Represents the geometrical object (not filled) circle.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class Circle extends GeometricalObject implements Tool {

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void paint(Graphics2D g2d) {
		// TODO Auto-generated method stub
		
	}

}
