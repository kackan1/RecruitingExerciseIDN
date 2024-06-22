package org.example.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.example.Person.Person;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLHandler {

  //Simple validation checks if file is empty
  //and if file begins with <Employee> tag
  public static boolean validateFile(File file){
    try {
      Scanner scanner = new Scanner(file);
      if (scanner.hasNext()) {
        return scanner.nextLine().contains("<Employee>");
      } else {
        return false;
      }
    } catch (FileNotFoundException e) {
      throw new RuntimeException("File not found");
    }
  }

  //Converts xml file into Document with elements with tags
  //returns them as Person object
  public static Person parseFile(File file){
    Person person = new Person();
    if(validateFile(file)) {
      try {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(file);
        document.getDocumentElement().normalize();
        NodeList nList = document.getElementsByTagName("Employee");

        for (int temp = 0; temp < nList.getLength(); temp++) {
          Node nNode = nList.item(temp);
          if (nNode.getNodeType() == Node.ELEMENT_NODE) {
            Element eElement = (Element) nNode;
            person.setPersonId(eElement.getElementsByTagName("PersonId").item(0).getTextContent());
            person.setFirstName(eElement.getElementsByTagName("FirstName").item(0).getTextContent());
            person.setLastName(eElement.getElementsByTagName("LastName").item(0).getTextContent());
            person.setMobile(eElement.getElementsByTagName("Mobile").item(0).getTextContent());
            person.setEmail(eElement.getElementsByTagName("Email").item(0).getTextContent());
            person.setPesel(eElement.getElementsByTagName("PESEL").item(0).getTextContent());
          }
        }
      } catch (ParserConfigurationException ex) {
        throw new RuntimeException("Error: parser configuration");
      } catch (IOException ex) {
        throw new RuntimeException("Error: finding xml file");
      } catch (SAXException ex) {
        throw new RuntimeException("Error: parsing xml file");
      }
      return person;
    } else {
      return null;
    }
  }

  public static void createFile(Person person, String path){
    try {
      File xml = new File(path + "/" + person.getPersonId());
      DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
      Document document = documentBuilder.newDocument();
      document.createElement("<Employee>");
    } catch (ParserConfigurationException e) {
      throw new RuntimeException("Error: parser configuration");
    }
  }
}
