package fr.irit.amak.droneamas;

import fr.irit.smac.amak.Environment;
import fr.irit.smac.amak.ui.VectorialGraphicsPanel;

/**
 * This class represents the environment of the AMAS which is the world (or at
 * least a part of it)
 *
 */
public class World extends Environment {

	private final VectorialGraphicsPanel graphicsPanel;
	/**
	 * Areas in the world
	 */
	private Area[][] areas;
	/**
	 * Number of areas in width
	 */
	public final static int WIDTH = 80;
	/**
	 * Number of areas in height
	 */
	public final static int HEIGHT = 60;

	public World(VectorialGraphicsPanel graphicsPanel) {
		super();
		this.graphicsPanel = graphicsPanel;
		areas = new Area[HEIGHT][WIDTH];
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				areas[y][x] = new Area(graphicsPanel, x, y);
			}
		}
	}

	/**
	 * Inform each area at each cycle
	 */
	@Override
	public void onCycle() {
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				areas[y][x].cycle();
			}
		}
	}

	/**
	 * Getter for the areas
	 * 
	 * @return the areas array
	 */
	public Area[][] getAreas() {
		return areas;
	}

	/**
	 * Get an area at a specific coordinate
	 * 
	 * @param dx
	 *            the x coordinate
	 * @param dy
	 *            the y coordinate
	 * @return the area
	 */
	public Area getAreaByPosition(int dx, int dy) {

		if (dx < 0 || dy < 0 || dx >= WIDTH || dy >= HEIGHT)
			return null;
		return areas[dy][dx];
	}
}
