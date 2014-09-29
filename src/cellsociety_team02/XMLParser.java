package cellsociety_team02;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cell.Cell;
import patch.Patch;
/**
 * Retrieves information from XML files designed for CA simulations
 * Stores the information in relevant data structures
 * 
 * @author Chase Malik
 * @author Greg Lyons
 * @author Kevin Rhine
 */
public class XMLParser {
	private static final int DEFAULT_PATCH_VALUE = 1;
	private static final int DEFAULT_CELL_VALUE = 0;
	private Document myDoc;
	private String myType;
	private String myConfig;
	private Cell[][] cellsList;
	private Patch[][] patchesList;
	private int maxRow;
	private int maxCol;
	private Map<String,String> paramMap;
	private int myNumStates;
	private int myNumPatches;
	private Factory factory;
	private String myGridType;

	/**
	 * Takes an xml file and creates a document that can be parsed
	 * 
	 * @param f XML file with CA data
	 */
	public XMLParser(File f) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			myDoc = dBuilder.parse(f);
			myDoc.getDocumentElement().normalize();		
			paramMap = new HashMap<String,String>();
			factory = new Factory();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * Gets the important parameters for the simulation
	 * Initializes the arrays of cells and patches
	 * Makes the parameter map
	 *
	 */
	public void initialize(){
		NodeList modelNodes = myDoc.getElementsByTagName("animation");
		Node modelNode = modelNodes.item(0);
		if(modelNode instanceof Element) {
			myType = getAttribute(modelNode, "model");
			myConfig = getAttribute(modelNode, "config");
			myNumStates = Integer.parseInt(getAttribute(modelNode, "numStates"));
			try {myNumPatches = Integer.parseInt(getAttribute(modelNode, "numPatches"));}
			catch(Exception ex){myNumPatches = 1;}
			maxRow = Integer.parseInt(getAttribute(modelNode, "rows"));
			maxCol = Integer.parseInt(getAttribute(modelNode, "columns"));
			try{myGridType = getAttribute(modelNode,"gridType");}
			catch(Exception ex){myGridType = "Rectangle";}
			cellsList = new Cell[maxRow][maxCol];
			patchesList = new Patch[maxRow][maxCol];
		}
		paramMap = makeParameterMap();
	}
	/**
	 * Gets the value of the attribute associated with the node and string
	 * 
	 *@param n Node in the xml file
	 *@param s String representing the attribute of interest
	 *@return String representing the value associated with attribute s
	 */
	private String getAttribute(Node n, String s) {
		return n.getAttributes().getNamedItem(s).getNodeValue();
	}
	/**
	 * Makes a map from parameters in the xml file to their value
	 *
	 *@return Map<String,String> with keys defined by the xml parameter name and value equal to its value
	 */
	public Map<String,String> makeParameterMap(){		
		return makeMap("parameter");
	}
	/**
	 * Makes a map from the xml file based on the string it is passed to their value
	 *
	 *@param String s of node name to search for in the xml
	 *@return Map<String,String> with keys defined by the s name and value equal to its value
	 */
	private Map<String, String> makeMap(String s) {
		Map<String, String> pMap = new HashMap<>();
		NodeList parameterNodes = myDoc.getElementsByTagName(s);
		for(int i = 0; i<parameterNodes.getLength(); i++){
			Node parameter = parameterNodes.item(i);
			if(parameter instanceof Element){
				pMap.put(getAttribute(parameter,"name"), getAttribute(parameter,"value"));
			}
		}
		return pMap;
	}
	/**
	 * Using the xml input, creates an array of cells
	 *
	 *@return Cell[][] array of cells
	 */
	public Cell[][] makeCells(){
		constructList("cell");
		return cellsList;
	}
	/**
	 * Using the xml input, creates an array of patches
	 *
	 *@return Patch[][] array of patches
	 */
	public Patch[][] makePatches(){
		constructList("patch");
		return patchesList;
	}
	/**
	 * Using the xml input, creates an array of states for either cells or patches
	 * Based on the model's configuration
	 * 
	 * @param s String that defines whether to create array for cells or patches
	 */
	private void constructList(String s) {
		switch(myConfig){
		case "Given": doGiven(s); break;
		case "Random": doRandom(s); break;
		case "Probability": doProbability(s); break;
		}
	}
	/**
	 * Using the xml input, creates an array of states for either cells or patches
	 * Looks for all the nodes of the type s
	 * Creates the appropriate cell or patch at the location given
	 * Then sets all null entries equal to a cell or patch with its default value
	 * 
	 * @param s String that defines whether to create cells or patches
	 */
	private void doGiven(String s) {
		NodeList nodes = myDoc.getElementsByTagName(s);
		for(int i = 0; i<nodes.getLength(); i++){
			Node node = nodes.item(i);
			if(node instanceof Element){
				int r = Integer.parseInt(getAttribute(node,"row"));	
				int c = Integer.parseInt(getAttribute(node,"column"));	
				double state = Double.parseDouble(getAttribute(node,"state"));
				if(s.equals("cell")) cellsList[r][c] = factory.makeCell(myType, r, c, state, paramMap);
				else patchesList[r][c] = factory.makePatch(cellsList[r][c], myType, r, c, state, paramMap);
			}
		}
	
		setNullState();
	}
	/**
	 * Using the xml input, creates an array of states for either cells or patches
	 * Loops through all possible locations and creates a random cell or patch at the location
	 * 
	 * @param s String that defines whether to create cells or patches
	 */
	private void doRandom(String s){
		for(int i=0;i<cellsList.length;i++){
			for(int j=0;j<cellsList[0].length;j++){
				if(s.equals("cell")) cellsList[i][j] = factory.makeRandomCell(myType, i, j, paramMap,myNumStates);
				else patchesList[i][j] = factory.makeRandomPatch(cellsList[i][j],myType, i, j, paramMap,myNumPatches);
			}
		}
	}
	/**
	 * Using the xml input, creates an array of states for either cells or patches
	 * Creates a map of cell probabilities and patch probabilities
	 * Loops through all possible locations and creates a cell or patch at the location based on the map
	 * 
	 * @param s String that defines whether to create cells or patches
	 */
	private void doProbability(String s) {
		Map<String, String> cellProb = makeMap("cellProb");
		Map<String, String> patchProb;
		if(myNumPatches > 1) {patchProb = makeMap("patchProb");}
		else{
			patchProb = new HashMap<String,String>();
			patchProb.put("0", "1.0");
		}
		for(int i=0;i<cellsList.length;i++){
			for(int j=0;j<cellsList[0].length;j++){
				if(s.equals("cell")) cellsList[i][j] = factory.makeProbCell(myType, i, j, paramMap,myNumStates, cellProb);
				else patchesList[i][j] = factory.makeProbPatch(cellsList[i][j], myType, i, j, paramMap,myNumPatches, patchProb);
			}
		}
	}
	/**
	 * Sets all null entries in cellsList and patchesList
	 * equal to a cell or patch's default state, respectively
	 * 
	 */
	private void setNullState() {
		for(int i=0; i<cellsList.length;i++){
			for(int j =0; j<cellsList[0].length;j++){
				if(cellsList[i][j] == null){
					cellsList[i][j] = factory.makeCell(myType, i, j, DEFAULT_CELL_VALUE, paramMap);
				}
				if(patchesList[i][j]== null)
					patchesList[i][j] = factory.makePatch(cellsList[i][j],myType, i, j, DEFAULT_PATCH_VALUE, paramMap);
			}
		}
	}
	/**
	 * Return the type of grid
	 * 
	 * @return String of grid type
	 */
	public String getGridType() {
		return myGridType;
	}
	/**
	 * Return the maximum number of cell states
	 * 
	 * @return int myNumStates
	 */
	public int getNumStates(){
		return myNumStates;
	}
}
