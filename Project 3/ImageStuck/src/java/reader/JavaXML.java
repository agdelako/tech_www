package reader;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import java.lang.String;
/**
 *
 * @author AD
 */
public class JavaXML extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
   
    public static String[] readerXML( String header ) throws  IOException{
        
    
    int LastIndexOf = header.lastIndexOf("."); 
    int FirstIndexOf= header.indexOf("8080/");
    String dir = header.substring(FirstIndexOf + 5,LastIndexOf);
    dir = "/usr/local/tomcat/webapps/" + dir + ".xml";
    File file = new File(dir);
    DocumentBuilder builder = null;
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(JavaXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    Document doc = null;
        try {
            doc = builder.parse(file);
        } catch (SAXException ex) {
            Logger.getLogger(JavaXML.class.getName()).log(Level.SEVERE, null, ex);
        }

    
    String imageSrc, imageCapt, imageBorder, imageHeight, imageWidth, imageRotate;
    
    NodeList nodes = doc.getElementsByTagName("picture");
    String[] eikona = new String[nodes.getLength()*2];
    
    for (int i = 0; i < nodes.getLength(); i=i+2) {
      Element element = (Element) nodes.item(i);
      NodeList pic = element.getElementsByTagName("image"); 
      Element img = (Element) pic.item(0);
      imageSrc = getCharacterDataFromElement(img);
      
      NodeList title = element.getElementsByTagName("caption");
      Element text = (Element) title.item(0);
      imageCapt = getCharacterDataFromElement(text);
      
      NodeList border = element.getElementsByTagName("border-style");
      Element line = (Element) border.item(0);
      imageBorder = getCharacterDataFromElement(line);
      
      NodeList height = element.getElementsByTagName("height");
      Element heig = (Element) height.item(0);
      imageHeight = getCharacterDataFromElement(heig);
      
      NodeList width = element.getElementsByTagName("width");
      Element wid = (Element) width.item(0);
      imageWidth = getCharacterDataFromElement(wid);
      
      NodeList rotate = element.getElementsByTagName("rotate");
      Element rot = (Element) rotate.item(0);
      imageRotate = getCharacterDataFromElement(rot);
      
      
      eikona[i] = "<img src=\"" + imageSrc + "\"style=\"border:" + imageBorder + ";-moz-transform:rotate(" + imageRotate +"deg);-webkit-transform:rotate(" + imageRotate + "deg);\"height=\"" + imageHeight + "\"width=\"" + imageWidth + "\"/>";
      eikona[i+1] = "<label id=\"caption\" style=\"position:relative;font-family:Arial Rounded MT Bold;left:20%;white-space:nowrap;\">" + imageCapt + "</label>";
        }
        return eikona;
    
  }
  
  public static String getCharacterDataFromElement(Element e) {
    Node child = e.getFirstChild();
    if (child instanceof CharacterData) {
      CharacterData cd = (CharacterData) child;
      return cd.getData();
    }
    return "";
  }
  

}