import java.awt.*;
import java.awt.image.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class View extends JFrame
{
	public static final int CANVAS_W = 640;
	public static final int CANVAS_H = 400;
	
	private Model model;
	private Controller controller;
	
	private Canvas canvas;
	
	public View()
	{
		model = new Model();
		controller = new Controller(this, model);
		setTitle("Gravity Ambient Simulation");
		setSize(CANVAS_W + 6, CANVAS_H + 102);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		canvas = new Canvas();
		canvas.setLayout(null);
		canvas.setBounds(0, 30, CANVAS_W, CANVAS_H);
		canvas.setPicture(new BufferedImage(CANVAS_W, CANVAS_W, BufferedImage.TYPE_INT_RGB));
		
		canvas.addActionListener(controller);
		
		add(canvas);
		add(new JLabel());
		new UpdateThread().start();
	}
	
	class UpdateThread extends Thread
	{
		public void run()
		{
			while (true)
			{
				updateModel();
				updateView();
			}
		}
	}
	
	public void start() { setVisible(true); }
	public void updateModel()
	{
		model.calculateInteractions();
		model.updateEntities();
		model.checkCollisions();
	}
	public void updateView()
	{
		BufferedImage picture;
		Graphics2D gd;
		Point location;
		
		picture = new BufferedImage(canvas.getPicture().getWidth(), canvas.getPicture().getHeight(), BufferedImage.TYPE_INT_RGB);
		gd = picture.createGraphics();
		gd.setColor(Color.BLACK);
		gd.clearRect(0, 0, picture.getWidth(), picture.getHeight());
		gd.setColor(Color.WHITE);
		for (Entity entity : model.getEntities())
		{
			location = entity.getLocation();
			gd.setColor(new Color(255, (int)(255 - (entity.getMass() / Double.MAX_VALUE) * 255), (int)(255 - (entity.getMass() / Double.MAX_VALUE) * 255)));
			gd.drawOval((int)(location.x - entity.getRadius()), (int)(entity.getLocation().y - entity.getRadius()), (int)entity.getDiameter(), (int)entity.getDiameter());
		}
		canvas.setPicture(picture);
	}
}