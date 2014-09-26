Group members: Greg Lyons, Chase Malik, Kevin Rhine

Date Started: Wednesday 9/10/14 Date Completed: Friday 9/26/14

Estimated number of hours: 40

Primary Roles:

- Greg: Predator/Prey simulation, GridModel, GridView, Cell, Patch
- Kevin: Fire simulation, GridModel, GridView, graph, Cell, Patch
- Chase: XML Parser, Factory, Draw, Life simulation, Segregation simulation, Sugar simulation, Cell, Patch

File to start project: Main.java 
Necessary resources and files (XML files) are included in project.

XML files contain the configuration data for running the simulations.  The following attributes and parameters may be specified in our XML files for any simulation:
- Type of simulation ("model")
- Method for determining initial states: Given, Probability, or Random ("config")
- Grid type: Rectangle or Triangle ("gridType")
- Number of possible Cell states ("numStates")
- Number of possible Patch states ("numPatches")
- Dimensions for the grid ("rows" and "columns")

Additionally, the XML files contain parameters based on the given simulation to be implemented.  For example, the Predator-Prey simulation requires values for the breeding time for fish and sharks.


Program is controlled using buttons along bottom of screen.
[Load] is used to load a new simulation.
[Play/Pause] is used to start and stop the simulation.
[Step] is used to step through the simulation.

The speed of the simulation can be controlled with the slider (left increases the simulation speed, right decreases it).

We were able to implement mouse-click functionality for interactively updating the states on the grid, but ran into issues with scope.  We commented out the changes (in the GridView class) to avoid any errors when compiling, although the code runs even without commenting it out.
