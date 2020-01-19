/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xquery;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class Xquery {

    public static void main(String[] args) {
        ArrayList<String> arr = new ArrayList<String>();
        /* arr.add("id");arr.add("name");arr.add("email");arr.add("email_verified_at");
       arr.add("password");arr.add("remember_token");arr.add("created_at");arr.add("updated_at");
       displayXML("users.xml", "/DATA/USER",arr);
       arr.clear();
       
       arr.add("id");arr.add("generated_id");arr.add("created_at");arr.add("updated_at");
       arr.add("drawn_by");arr.add("date");arr.add("title");arr.add("type");arr.add("description");arr.add("status");
       displayXML("paintings.xml", "/DATA/PAINTING",arr);
       arr.clear();
       
       arr.add("id");arr.add("created_at");arr.add("updated_at");arr.add("generated_id");
       arr.add("main_artist");arr.add("done_date");arr.add("title");arr.add("description");arr.add("sellable");
       displayXML("mosiacs.xml", "/DATA/MOSIAC",arr);
       arr.clear();
       
       arr.add("id");arr.add("created_at");arr.add("updated_at");arr.add("f_name");arr.add("l_name");arr.add("username");
       arr.add("phone_number");arr.add("linked_in");arr.add("facebook");arr.add("twitter");
       arr.add("instagram");arr.add("description");arr.add("bried_description");
       displayXML("artists.xml", "/DATA/ARTIST",arr);
       arr.clear();
       
       arr.add("id");arr.add("created_at");arr.add("updated_at");arr.add("country");
       arr.add("city");arr.add("subcity");arr.add("landmark");arr.add("place");
       displayXML("addresses.xml", "/DATA/ADDRESS",arr);
       arr.clear();*/
        arr.add("password");
        arr.add("password");
        arr.add("remember_token");
        arr.add("created_at");
        arr.add("updated_at");
        arr.add("drawn_by");
        arr.add("date");
        arr.add("title");
        arr.add("type");
        arr.add("description");
        arr.add("status");

        add("users.xml", "/DATA/", "USER", arr);
        arr.clear();

    }

    public static void displayXML(String filename, String exp, ArrayList<String> arr) {
        System.out.println("\n************************" + filename + "************************\n");
        try {
            File inputFile = new File(filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            XPath xPath = XPathFactory.newInstance().newXPath();

            String expression = exp;
            NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node nNode = nodeList.item(i);
                System.out.println("\n" + nNode.getNodeName() + " number " + String.valueOf(i + 1));

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    for (int l = 0; l < arr.size(); l++) {

                        System.out.println(arr.get(l) + " : "
                                + eElement
                                        .getElementsByTagName(arr.get(l))
                                        .item(0)
                                        .getTextContent());

                    }
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
    }

    public static void add(String filename, String exp, String tag, ArrayList<String> arr) {
        try {
            Scanner scan = new Scanner(System.in);
            String input = "";
            Document doc;
            Element root;
            Element elm;
            File inputFile = new File(filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            if (!inputFile.exists()) {
                doc = dBuilder.newDocument();
                root = doc.createElement("DATA");
                doc.appendChild(root);
                trans(filename, doc);
            } else {
                doc = dBuilder.parse(inputFile);
            }

            Element innerTag = doc.createElement(tag);
            doc.getElementsByTagName("DATA").item(0).appendChild(innerTag);
            XPath xPath = XPathFactory.newInstance().newXPath();

            NodeList nodeList = (NodeList) xPath.compile(exp + tag).evaluate(doc, XPathConstants.NODESET);

            for (int i = 0; i < arr.size(); i++) {
                System.out.println("Enter the value for " + arr.get(i));
                input = scan.next();
                elm = doc.createElement(arr.get(i));
                elm.appendChild(doc.createTextNode(input));
                doc.getElementsByTagName(innerTag.getNodeName()).item(nodeList.getLength() - 1).appendChild(elm);
            }
            trans(filename, doc);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XPathExpressionException ex) {
            Logger.getLogger(Xquery.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void trans(String filename, Document doc) {
        try {
            TransformerFactory ttf = TransformerFactory.newInstance();
            Transformer tr = ttf.newTransformer();
            DOMSource xmlSource = new DOMSource(doc);
            StreamResult outputTarget = new StreamResult(filename);
            tr.transform(xmlSource, outputTarget);
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(Xquery.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(Xquery.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
