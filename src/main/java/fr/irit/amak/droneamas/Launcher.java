package fr.irit.amak.droneamas;

import fr.irit.smac.amak.scheduling.Scheduler;
import fr.irit.smac.amak.ui.MainWindow;
import fr.irit.smac.amak.ui.SchedulerToolbar;
import fr.irit.smac.amak.ui.VectorialGraphicsPanel;

public class Launcher {
	/**
	 * Launch the system
	 *
	 * @param args Arguments of the problem (not used)
	 */
	public static void main(String[] args) {
		var graphicsPanel = new VectorialGraphicsPanel("World");

		var world = new World(graphicsPanel);
		var droneAmas = new DroneAmas(world, graphicsPanel);

		var mainWindow = new MainWindow();
		mainWindow.addMenuItem("Remove 10 drones", l -> {
			for (int i = 0; i < 10 && droneAmas.getAgents().size() > 0; i++) {
				droneAmas.getAgents().get(droneAmas.getEnvironment().getRandom().nextInt(droneAmas.getAgents().size()))
				         .destroy();
			}
		});
		mainWindow.addMenuItem("Add 10 drones", l -> {
			for (int i = 0; i < 10; i++) {
				new Drone(droneAmas, droneAmas.getEnvironment().getRandom().nextInt(World.WIDTH),
				          droneAmas.getEnvironment().getRandom().nextInt(World.HEIGHT));
			}
		});
		mainWindow.setLeftPanel(graphicsPanel);

		var scheduler = new Scheduler(droneAmas, world);
		mainWindow.addToolbar(new SchedulerToolbar("Scheduler", scheduler));
	}

}
