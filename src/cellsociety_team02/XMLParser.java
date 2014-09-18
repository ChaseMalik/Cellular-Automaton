package cellsociety_team02;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLParser {
	private Document myDoc;
	private String myModel;
	private int[][] cellsArray;
	private double[][] patchesArray;

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

	public String getModelAndInitialize(){
		NodeList modelNodes = myDoc.getElementsByTagName("animation");
		Node modelNode = modelNodes.item(0);
		if(modelNode instanceof Element) {
			myModel = getAttribute(modelNode, "model");
			int r = Integer.parseInt(getAttribute(modelNode, "rows"));
			int c = Integer.parseInt(getAttribute(modelNode, "columns"));
			cellsArray = new int[r][c];
			patchesArray = new double[r][c];
			for(int i = 0; i<patchesArray.length; i++){
				for(int j=0; j<patchesArray[0].length; j++){
					patchesArray[i][j]=1;
				}
			}
		}
		return myModel;
	}

	private String getAttribute(Node n, String s) {
		return n.getAttributes().getNamedItem(s).getNodeValue();
	}

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

	public int[][] makeCells(){
		constructArray("cell");
		return cellsArray;
	}
	public double[][] makePatches(){
		constructArray("patch");
		return patchesArray;
	}

	private void constructArray(String s) {
		boolean isInt = true;
		if(s.equals("patch")) isInt = false;

		NodeList nodes = myDoc.getElementsByTagName(s);
		for(int i = 0; i<nodes.getLength(); i++){
			Node node = nodes.item(i);
			if(node instanceof Element){
				int r = Integer.parseInt(getAttribute(node,"row"));
				int c = Integer.parseInt(getAttribute(node,"column"));
				if(isInt){
					int state = Integer.parseInt(getAttribute(node,"state"));
					cellsArray[r][c] = state;
				}
				else{ 
					double state = Double.parseDouble(getAttribute(node,"state"));
					patchesArray[r][c] = state;
				}
			}
		}
	}

	public void printArray(){
		for(int i=0; i<cellsArray.length; i++){
			for(int j=0; j<cellsArray[0].length;j++){
				System.out.print(cellsArray[i][j] + " ");
			}
			System.out.print("\n");
		}
	}
}
