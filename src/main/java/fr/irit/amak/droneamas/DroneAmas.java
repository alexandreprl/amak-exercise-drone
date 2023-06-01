package fr.irit.amak.droneamas;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.OptionalDouble;

import fr.irit.smac.amak.Agent;
import fr.irit.smac.amak.Amas;
import fr.irit.smac.amak.ui.VectorialGraphicsPanel;
import fr.irit.smac.lxplot.LxPlot;

/**
 * This class represents the AMAS
 *
 */
public class DroneAmas extends Amas<World> {

	/**
	 * Initial drones count on the AMAS creation
	 */
	private static final int INITIAL_DRONE_COUNT = 20;
	private final VectorialGraphicsPanel graphicsPanel;
	/**
	 * Queue used to compute the sliding window
	 */
	private LinkedList<Double> lastSums = new LinkedList<>();

	/**
	 * Constructor of the MAS
	 *
	 * @param environment   Environment of the system
	 * @param graphicsPanel
	 */
	public DroneAmas(World environment, VectorialGraphicsPanel graphicsPanel) {
		super(environment, 1, ExecutionPolicy.ONE_PHASE);
		this.graphicsPanel = graphicsPanel;
	}

	/**
	 * Create the agents at random positions
	 */
	@Override
	protected void onInitialAgentsCreation() {
		for (int i = 0; i < INITIAL_DRONE_COUNT; i++)
			new Drone(this, getEnvironment().getRandom().nextInt(World.WIDTH),
					getEnvironment().getRandom().nextInt(World.HEIGHT));

	}

	/**
	 * At the end of each system cycle, compute the sum and average of area
	 * criticalities and display them
	 */
	@Override
	protected void onSystemCycleEnd() {
		double max = 0;
		double sum = 0;
		for (int x = 0; x < getEnvironment().getAreas()[0].length; x++) {
			for (int y = 0; y < getEnvironment().getAreas().length; y++) {
				double criticality = getEnvironment().getAreaByPosition(x, y).computeCriticality();
				sum += criticality;
				if (criticality > max)
					max = criticality;
			}
		}

		lastSums.add(sum);
		if (lastSums.size() > 10000)
			lastSums.poll();

		LxPlot.getChart("Area criticalities").add("Sum", cyclesCount % 1000, sum);
		LxPlot.getChart("Area criticalities").add("Sliding average", cyclesCount % 1000, average(lastSums));
	}

	/**
	 * Compute the average of a list
	 * 
	 * @param lastSums2
	 *            List on which computing the average
	 * @return the average
	 */
	private double average(LinkedList<Double> lastSums2) {
		OptionalDouble average = lastSums2.stream().mapToDouble(a -> a).average();
		return average.getAsDouble();
	}

	/**
	 * Get agents presents in a specified area
	 * 
	 * @param areaByPosition
	 *            The specified area
	 * @return the list of drones in this area
	 */
	public Drone[] getAgentsInArea(Area areaByPosition) {
		List<Drone> res = new ArrayList<>();
		for (Agent<?, World> agent : agents) {
			if (((Drone) agent).getCurrentArea() == areaByPosition)
				res.add((Drone) agent);
		}
		return res.toArray(new Drone[0]);
	}

	public VectorialGraphicsPanel getGraphicsPanel() {
		return graphicsPanel;
	}
}
