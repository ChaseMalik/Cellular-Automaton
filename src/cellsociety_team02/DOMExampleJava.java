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

public class DOMExampleJava {
	public static void main(String args[]) {
		try {

			File f = new File("xml files/FireExample.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(f);
			doc.getDocumentElement().normalize();
			NodeList infoNodes = doc.getElementsByTagName("info");

			Map<String, String> pMap = new HashMap<>();
			int[][] Array = null;
			Node infoNode = infoNodes.item(0);
			if(infoNode.getNodeType() == Node.ELEMENT_NODE) {
				Element infoElement = (Element) infoNode;
				String myModel = getValue("model", infoElement);
				int r = Integer.parseInt(getValue("rows", infoElement));
				int c = Integer.parseInt(getValue("cols", infoElement));
				Array = new int[r][c];
			}

			NodeList pNodes = doc.getElementsByTagName("parameters");
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
			

			NodeList cellNodes = doc.getElementsByTagName("cell");
			for(int i = 0; i<cellNodes.getLength(); i++){
				Node cNode = cellNodes.item(i);
				if(cNode.getNodeType() == Node.ELEMENT_NODE){
					Element cElement = (Element) cNode;
					int r = Integer.parseInt(getValue("r",cElement));
					int c = Integer.parseInt(getValue("c",cElement));
					int state = Integer.parseInt(getValue("state",cElement));
					Array[r][c] = state;
				}
			}
			
			for(int i=0; i<Array.length; i++){
				for(int j=0; j<Array[0].length;j++){
					System.out.print(Array[i][j] + " ");
				}
				System.out.print("\n");
			}
			for(String s:pMap.keySet()){
				System.out.print(s + " " + pMap.get(s) + "\n");
			}
			

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static String getValue(String tag, Element element) {
		NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nodes.item(0);
		return node.getNodeValue();
	}
}
