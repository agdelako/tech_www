import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author AD
 */
public class EditImage extends HttpServlet {

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
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String caption = request.getParameter("caption");
        String height = request.getParameter("height");
        String width = request.getParameter("width");
        String gray_shaddow = request.getParameter("gray");
        String rotate = request.getParameter("rotate");
        
        String ref = request.getHeader("referer");
        int LastIndexOf = ref.lastIndexOf("."); 
        int FirstIndexOf= ref.indexOf("8080/");
        String dir = ref.substring(FirstIndexOf + 5,LastIndexOf);
        dir = "/usr/local/tomcat/webapps/" + dir + ".xml";
          
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder docBuilder = null;
        Document doc = null;
        try {
            docBuilder = docFactory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(EditImage.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            doc = docBuilder.parse(dir);
        } catch (SAXException ex) {
            Logger.getLogger(EditImage.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Node fileXML = doc.getFirstChild();
        NodeList picture = fileXML.getChildNodes();
        
        for (int i = 0; i < picture.getLength(); i++) {
            Node node = picture.item(i);
            
            // get the caption element, and update the value
            if (!"".equals(caption) && caption!=null && "caption".equals(node.getNodeName())) {
                node.setTextContent(caption);
	    } 
            // get the height element, and update the value
            if (!"".equals(height) && height!=null && "height".equals(node.getNodeName()) ) {
                node.setTextContent(height);
	    }

            // get the width element, and update the value
            if (!"".equals(width) && width!=null && "width".equals(node.getNodeName())) {
                node.setTextContent(width);
	    }
            // get the gray_bright element, and update the value
            if (!"".equals(gray_shaddow) && gray_shaddow!=null && "gray".equals(node.getNodeName()) ) {
                node.setTextContent(gray_shaddow);
	    }
            // get the rotate element, and update the value
            if (!"".equals(rotate) && rotate!=null && "rotate".equals(node.getNodeName()) ) {
                node.setTextContent(rotate);
	    }
        }
        
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
	Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(EditImage.class.getName()).log(Level.SEVERE, null, ex);
        }
	DOMSource source = new DOMSource(doc);
	StreamResult result = new StreamResult(new File(dir));
        try {
            transformer.transform(source, result);
        } catch (TransformerException ex) {
            Logger.getLogger(EditImage.class.getName()).log(Level.SEVERE, null, ex);
        }
        response.sendRedirect(ref);
     
    }

}
