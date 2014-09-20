package cellsociety_team02;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
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
	private Document myDoc;
	private String myType;
	private List<Cell> cellsList;
	private List<Patch> patchesList;
	private int maxRow;
	private int maxCol;

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
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * Gets the model type of the simulation
	 * Initializes the arrays of cells and patches with default values
	 * Default value of cell is 0
	 * Default value of patch is 1.0
	 *
	 */
	public void initialize(){
		NodeList modelNodes = myDoc.getElementsByTagName("animation");
		Node modelNode = modelNodes.item(0);
		if(modelNode instanceof Element) {
			myType = getAttribute(modelNode, "model");
			maxRow = Integer.parseInt(getAttribute(modelNode, "rows"));
			maxCol = Integer.parseInt(getAttribute(modelNode, "columns"));
			cellsList = new ArrayList<Cell>();
			patchesList = new ArrayList<Patch>();
		}
	}
	/**
	 * Gets the value of the attribute associated with the node and string
	 * 
	 *@param n Node in the xml file
	 *@param s String representing the attribute of interest
	 *@return String representing the value assoicated with attribute s
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
		Map<String, String> pMap = new HashMap<>();
		NodeList parameterNodes = myDoc.getElementsByTagName("parameter");
		for(int i = 0; i<parameterNodes.getLength(); i++){
			Node parameter = parameterNodes.item(i);
			if(parameter instanceof Element){
				pMap.put(getAttribute(parameter,"name"), getAttribute(parameter,"value"));
			}
		}
		return pMap;
	}
	/**
	 * Using the xml input, creates an array of states for the cells
	 *
	 *@return 
	 */
	public List<Cell> makeCells(){
		constructList("cell");
		return cellsList;
	}
	/**
	 * Using the xml input, creates an array of states for the patches
	 *
	 *@return 
	 */
	public List<Patch> makePatches(){
		constructList("patch");
		return patchesList;
	}
	/**
	 * Using the xml input, creates an array of states for either cells or patches
	 * 
	 * @param s String that defines whether to create array for cells or patches
	 */
	private void constructList(String s) {
		NodeList nodes = myDoc.getElementsByTagName(s);
		for(int i = 0; i<nodes.getLength(); i++){
			Node node = nodes.item(i);
			if(node instanceof Element){
				double r = Double.parseDouble(getAttribute(node,"row"));	
				double c = Double.parseDouble(getAttribute(node,"column"));	
				double state = Double.parseDouble(getAttribute(node,"state"));
				
				Cell newCell = null;
				Patch newPatch = null;
				switch(myType){
				case "Fire": 
		//			if (s.equals("cell")) newCell = new FireCell(state, r, c); 
		//			else newPatch = new FirePatch(state, r, c); 
					break;
				case "PredPrey":
	//				if (s.equals("cell")) newCell = new PredPreyCell(state, r, c); 
					break;
				case "Segregation": 
//					if (s.equals("cell")) newCell = new SegCell(state, r, c); 
					break;
				case "Life":
					if (s.equals("cell")) newCell = new LifeCell(state, r, c); 
					break;
				}
				
				cellsList.add(newCell);
				patchesList.add(newPatch);		
			}
		}
	}
	/**
	 * Prints the cellsArray to the console
	 * Used for testing purposes
	 */
	public void printCellsArray(){
		/*for(int i=0; i<cellsArray.length; i++){
			for(int j=0; j<cellsArray[0].length;j++){
				System.out.print(cellsArray[i][j] + " ");
			}
			System.out.print("\n");
		}*/
	}
}
