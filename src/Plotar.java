
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Plotar extends JFrame {

	private static final long serialVersionUID = 1L;

	public Plotar( ArrayList<Pontos> pontos, ArrayList<Antenas> antenas, ArrayList<Antenas> antigas) {

		setSize(new Dimension(250, 250));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.WHITE);
		setVisible(true);

		JPanel p = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				
				for(int i=0; i<antigas.size(); i++) {
					Shape circle = new Ellipse2D.Double(antigas.get(i).x - antigas.get(i).raio, antigas.get(i).y - antigas.get(i).raio, 2.0 * antigas.get(i).raio, 2.0 * antigas.get(i).raio);

					g2.setPaint(Color.RED);
					g2.draw(circle);
				}
				
				for(int i=0; i<antenas.size(); i++) {
					Shape circle = new Ellipse2D.Double(antenas.get(i).x - antenas.get(i).raio, antenas.get(i).y - antenas.get(i).raio, 2.0 * antenas.get(i).raio, 2.0 * antenas.get(i).raio);

					g2.setPaint(Color.BLUE);
					g2.draw(circle);
				}
				
				for(int i=0; i<pontos.size(); i++) {
					Point2D.Double point = new Point2D.Double(pontos.get(i).x ,pontos.get(i).y);
					Rectangle2D.Double rect2 = new Rectangle2D.Double(point.x - 0.5, point.y - 0.5, 1, 1);
					g2.setPaint(Color.BLACK);
					g2.draw(rect2);
				}
				/*
				Point2D.Double point = new Point2D.Double(100, 100);
				Shape line = new Line2D.Double(3, 3, 303, 303);
				Shape rect = new Rectangle(3, 3, 303, 303);
				Shape circle = new Ellipse2D.Double(100, 100, 100, 100);
				Shape roundRect = new RoundRectangle2D.Double(20, 20, 250, 250, 5, 25);
				Rectangle2D.Double rect2 = new Rectangle2D.Double(point.x - 0.5, point.y - 0.5, 1, 1);
				
				g2.setPaint(Color.RED);
				g2.draw(rect2);
				g2.setPaint(Color.BLUE);
				g2.draw(circle);
				*/
				
			}
		};
		setTitle("My Shapes");
		this.getContentPane().add(p);
	}

	/*public static void main(String arg[]) {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				new Plotar();
			}
		});
	}*/

}