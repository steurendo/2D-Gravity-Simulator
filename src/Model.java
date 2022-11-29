import java.util.*;

public class Model
{
	public static final double  SPACE_EXPANSION_FACTOR = 100;
	public static final double  SPACE_WIDTH = View.CANVAS_W * SPACE_EXPANSION_FACTOR;
	public static final double  SPACE_HEIGHT = View.CANVAS_H * SPACE_EXPANSION_FACTOR;
	public static final double  GRAVITATIONAL_CONSTANT = 6.67408 * Math.pow(10, -11);
	public static final double  TIME_SHIFT = Math.pow(10, 8);
	
	private ArrayList<Entity> entities;
	private Stack<Integer> removeList;
	
	public Model()
	{
		entities = new ArrayList<>();
		removeList = new Stack<>();
		//addEntity(new Entity(70000, 10, new Point(150, 150), new Vector(5, 0)));
		//addEntity(new Entity(20000, 40, new Point(400, 400), new Vector(-3, -0)));
		addEntity(new Entity(Double.MAX_VALUE, 10, new Point(320, 200)));
	}

	public ArrayList<Entity> getEntities() { return entities; }
	public int getEntitiesCount() { return entities.size(); }
	
	public void calculateInteractions()
	{
		int i, j;
		Object[] entities;
		Entity entA, entB;
		double modAB, modBA, th, r;
		
		entities = this.entities.toArray();
		for (i = 0; i < entities.length; i++)
			for (j = 0; j < i; j++)
			{
				entA = (Entity)entities[i];
				entB = (Entity)entities[j];
				modAB = GRAVITATIONAL_CONSTANT * entA.getDensity();
				modBA = GRAVITATIONAL_CONSTANT * entB.getDensity();
				r = Math.pow(entA.getDistanceFromEntity(entB), 2);
				modAB /= r;
				modBA /= r;
				th = Math.atan((entB.getLocation().y - entA.getLocation().y) / (entB.getLocation().x - entA.getLocation().x)) + 
					(entB.getLocation().x - entA.getLocation().x < 0 ? Math.toRadians(180) : 0);
				entA.getCurrentVector().add(new Vector(modBA * Math.cos(th) * TIME_SHIFT, modBA * Math.sin(th) * TIME_SHIFT));
				entB.getCurrentVector().add(new Vector(-modAB * Math.cos(th) * TIME_SHIFT, -modAB * Math.sin(th) * TIME_SHIFT));
			}
	}
	public void updateEntities()
	{
		for (Entity entity : entities)
		{
			entity.update();
		}
	}
	public void checkCollisions()
	{
		int i, j;
		Object[] entities;
		Entity entA, entB;
		
		entities = this.entities.toArray();
		for (i = 0; i < entities.length; i++)
			for (j = 0; j < i; j++)
			{
				entA = (Entity)entities[i];
				entB = (Entity)entities[j];
				if (entA == null || entB == null) continue;
				if (entA.getDistanceFromEntity(entB) <= 0)
				{
					entA.collide(entB);
					entities[j] = null;
					removeList.push(j);
				}
			}
		while (!removeList.isEmpty())
			this.entities.remove((int)removeList.pop());
	}
	public void addEntity(Entity entity)
	{
		entities.add(entity);
	}
}