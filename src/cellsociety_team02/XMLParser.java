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

	public String getModel(){
		NodeList infoNodes = myDoc.getElementsByTagName("info");
		Node infoNode = infoNodes.item(0);
		if(infoNode.getNodeType() == Node.ELEMENT_NODE) {
			Element infoElement = (Element) infoNode;
			myModel = getValue("model", infoElement);
			int r = Integer.parseInt(getValue("rows", infoElement));
			int c = Integer.parseInt(getValue("cols", infoElement));
			if(myModel.equals("Fire")){ r+=2; c+=2;} // Fire needs padding
			cellsArray = new int[r][c];
			patchesArray = new double[r][c];
		}
		return myModel;
	}
	public Map<String,String> makeParameterMap(){		

		Map<String, String> pMap = new HashMap<>();
		NodeList pNodes = myDoc.getElementsByTagName("parameters");
		Node pNode = pNodes.item(0);
		NodeList childNodes = pNode.getChildNodes();
		for(int i = 0; i<childNodes.getLength(); i++){
			Node cNode = childNodes.item(i);
			if(cNode.getNodeType() == Node.ELEMENT_NODE){
				Element cElement = (Element) cNode;
				String content = cNode.getLastChild().getTextContent().trim();
				pMap.put(cNode.getNodeName(),content);
			}
		}
		return pMap;
	}

	public int[][] makeCells(){
		NodeList cellNodes = myDoc.getElementsByTagName("cell");
		for(int i = 0; i<cellNodes.getLength(); i++){
			Node cNode = cellNodes.item(i);
			if(cNode.getNodeType() == Node.ELEMENT_NODE){
				Element cElement = (Element) cNode;
				int r = Integer.parseInt(getValue("r",cElement));
				int c = Integer.parseInt(getValue("c",cElement));
				if(myModel.equals("Fire")){ r+=1; c+=1;}
				int state = Integer.parseInt(getValue("state",cElement));
				cellsArray[r][c] = state;
			}
		}
		return cellsArray;
	}
	
	public double[][] makePatches(){
		return patchesArray;
	}
	
	
	public void printArray(){
		for(int i=0; i<cellsArray.length; i++){
			for(int j=0; j<cellsArray[0].length;j++){
				System.out.print(cellsArray[i][j] + " ");
			}
			System.out.print("\n");
		}
	}
	private static String getValue(String tag, Element element) {
		NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nodes.item(0);
		return node.getNodeValue();
	}
}
