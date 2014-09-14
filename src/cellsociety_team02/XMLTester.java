package cellsociety_team02;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLTester {
	public static void main(String[] args) throws Exception {

		File empDoc = new File("xml files/XML_Employees.xml");
		//Get the DOM Builder Factory
		DocumentBuilderFactory factory =
				DocumentBuilderFactory.newInstance();
		//Get the DOM Builder
		DocumentBuilder builder = factory.newDocumentBuilder();
		//Load and Parse the XML document
		//document contains the complete XML as a Tree.
		Document document =
				builder.parse(empDoc);

		List<Employee> empList = new ArrayList<>();
		//Iterating through the nodes and extracting the data.
		NodeList nodeList = document.getDocumentElement().getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			//We have encountered an <employee> tag.
			Node node = nodeList.item(i);
			if (node instanceof Element) {
				Employee emp = new Employee();
				emp.id = node.getAttributes().
						getNamedItem("id").getNodeValue();
				NodeList childNodes = node.getChildNodes();
				for (int j = 0; j < childNodes.getLength(); j++) {
					Node cNode = childNodes.item(j);
					//Identifying the child tag of employee encountered.
					if (cNode instanceof Element) {
						String content = cNode.getLastChild().
								getTextContent().trim();
						switch (cNode.getNodeName()) {
						case "firstName":
							emp.firstName = content;
							break;
						case "lastName":
							emp.lastName = content;
							break;
						case "location":
							emp.location = content;
							break;
						}
					}
				}
				empList.add(emp);
			}
		}
		//Printing the Employee list populated.
		for (Employee emp : empList) {
			System.out.println(emp);
		}
	}
}

class Employee{
	String id;
	String firstName;
	String lastName;
	String location;

	@Override
	public String toString() {
		return firstName+" "+lastName+"("+id+")"+location;
	}
}
