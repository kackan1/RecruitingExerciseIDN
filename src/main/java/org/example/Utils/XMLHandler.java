package org.example.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.example.Person.Person;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLHandler {
  //Simple validation checks if file is empty
  //and if file begins with <Employee> tag
  public static boolean validateFile(File file) {
    boolean result;
    Scanner scanner = null;
    try {
      scanner = new Scanner(file);
      if (scanner.hasNext()) {
        result = scanner.nextLine().contains("xml");
      } else {
        result = false;
      }
    } catch (FileNotFoundException e) {
      throw new RuntimeException("File not found");
    }
    scanner.close();
    return result;
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
        Node nNode = nList.item(0);

        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
          Element eElement = (Element) nNode;
          person.setPersonId(eElement.getElementsByTagName("PersonId").item(0).getTextContent());
          person.setFirstName(eElement.getElementsByTagName("FirstName").item(0).getTextContent());
          person.setLastName(eElement.getElementsByTagName("LastName").item(0).getTextContent());
          person.setMobile(eElement.getElementsByTagName("Mobile").item(0).getTextContent());
          person.setEmail(eElement.getElementsByTagName("Email").item(0).getTextContent());
          person.setPesel(eElement.getElementsByTagName("PESEL").item(0).getTextContent());
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

  //Creates document based on provided person
  //and writes it to provided file
  public static void modifyFile(File file, Person person){
    if(validateFile(file)) {
      try {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(file);
        document.getDocumentElement().normalize();
        NodeList nList = document.getElementsByTagName("Employee");
        Node nNode = nList.item(0);

        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
          Element eElement = (Element) nNode;
          if (!person.getPersonId().isEmpty()) {
            eElement.getElementsByTagName("PersonId").item(0).setTextContent(person.getPersonId());
          }
          if (!person.getFirstName().isEmpty()) {
            eElement.getElementsByTagName("FirstName").item(0).setTextContent(person.getFirstName());
          }
          if (!person.getLastName().isEmpty()) {
            eElement.getElementsByTagName("LastName").item(0).setTextContent(person.getLastName());
          }
          if (!person.getMobile().isEmpty()) {
            eElement.getElementsByTagName("Mobile").item(0).setTextContent(person.getMobile());
          }
          if (!person.getEmail().isEmpty()) {
            eElement.getElementsByTagName("Email").item(0).setTextContent(person.getEmail());
          }
          if (!person.getPesel().isEmpty()) {
            eElement.getElementsByTagName("PESEL").item(0).setTextContent(person.getPesel());
          }
        }
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(file);

        transformer.transform(source, result);

      } catch (ParserConfigurationException ex) {
        throw new RuntimeException("Error: parser configuration");
      } catch (IOException ex) {
        throw new RuntimeException("Error: finding xml file");
      } catch (SAXException ex) {
        throw new RuntimeException("Error: parsing xml file");
      } catch (TransformerConfigurationException e) {
        throw new RuntimeException(e);
      } catch (TransformerException e) {
        throw new RuntimeException(e);
      }
    }
  }

  public static void createFile(Person person, String path){
    try {
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.newDocument();

      Element rootElement = doc.createElement("Employee");
      doc.appendChild(rootElement);

      Element personId = doc.createElement("PersonId");
      personId.appendChild(doc.createTextNode(person.getPersonId()));
      rootElement.appendChild(personId);

      Element firstName = doc.createElement("FirstName");
      firstName.appendChild(doc.createTextNode(person.getFirstName()));
      rootElement.appendChild(firstName);

      Element lastName = doc.createElement("LastName");
      lastName.appendChild(doc.createTextNode(person.getLastName()));
      rootElement.appendChild(lastName);

      Element mobile = doc.createElement("Mobile");
      mobile.appendChild(doc.createTextNode(person.getMobile()));
      rootElement.appendChild(mobile);

      Element email = doc.createElement("Email");
      email.appendChild(doc.createTextNode(person.getEmail()));
      rootElement.appendChild(email);

      Element pesel = doc.createElement("PESEL");
      pesel.appendChild(doc.createTextNode(person.getPesel()));
      rootElement.appendChild(pesel);

      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();

      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

      DOMSource source = new DOMSource(doc);
      StreamResult result = new StreamResult(new File(path + "/" + person.getPersonId() + ".xml"));

      transformer.transform(source, result);

      System.out.println("XML file created successfully.");
    } catch (ParserConfigurationException | TransformerException e) {
      throw new RuntimeException(e);
    }
  }
}
