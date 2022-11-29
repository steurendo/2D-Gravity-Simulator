import java.awt.*;
import java.awt.event.*;

public class Controller implements ActionListener
{
	private View view;
	private Model model;
	
	public Controller(View view, Model model)
	{
		this.view = view;
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Component src;
		
		src = (Component)e.getSource();
		if (src instanceof Canvas)
		{
			model.addEntity(((Canvas)src).getGeneratedEntity());
			view.updateView();
		}
	}
}