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
/**
 * Retrieves information from XML files designed for CA simulations
 * Stores the information in relevant data structures
 * 
 * @author Chase Malik
 * @author Greg Lyons
 * @author Kevin Rhine
 */
public class XMLParser {
	private Document myDoc;
	private String myModel;
	private int[][] cellsArray;
	private double[][] patchesArray;

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
			cellsArray = null;
			myModel = null;					
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * Gets the model type of the simulation
	 * Initializes the arrays of cells and patches
	 *
	 */
	public String getModelAndInitialize(){
		NodeList modelNodes = myDoc.getElementsByTagName("animation");
		Node modelNode = modelNodes.item(0);
		if(modelNode instanceof Element) {
			myModel = getAttribute(modelNode, "model");
			int r = Integer.parseInt(getAttribute(modelNode, "rows"));
			int c = Integer.parseInt(getAttribute(modelNode, "columns"));
			cellsArray = new int[r][c];
			patchesArray = new double[r][c];
			for(int i = 1; i<patchesArray.length-1; i++){
				for(int j=1; j<patchesArray[0].length-1; j++){
					patchesArray[i][j]=1;
				}
			}
		}
		return myModel;
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
	 *@return int[][] representing the array of cell states
	 */
	public int[][] makeCells(){
		constructArray("cell");
		return cellsArray;
	}
	/**
	 * Using the xml input, creates an array of states for the patches
	 *
	 *@return double[][] representing the array of patch states
	 */
	public double[][] makePatches(){
		constructArray("patch");
		return patchesArray;
	}
	/**
	 * Using the xml input, creates an array of states for either cells or patches
	 * 
	 * @param s String that defines whether to create array for cells or patches
	 */
	private void constructArray(String s) {
		NodeList nodes = myDoc.getElementsByTagName(s);
		for(int i = 0; i<nodes.getLength(); i++){
			Node node = nodes.item(i);
			if(node instanceof Element){
				int r = Integer.parseInt(getAttribute(node,"row"));
				int c = Integer.parseInt(getAttribute(node,"column"));
				if(s.equals("cell")) cellsArray[r][c] = Integer.parseInt(getAttribute(node,"state"));
				else patchesArray[r][c] = Double.parseDouble(getAttribute(node,"state"));				
			}
		}
	}
	/**
	 * Prints the cellsArray to the console
	 * Used for testing purposes
	 */
	public void printCellsArray(){
		for(int i=0; i<cellsArray.length; i++){
			for(int j=0; j<cellsArray[0].length;j++){
				System.out.print(cellsArray[i][j] + " ");
			}
			System.out.print("\n");
		}
	}
}
