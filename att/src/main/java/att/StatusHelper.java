package att;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.namespace.NamespaceContext;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import decoders.Decoder;
import decoders.DecoderConfigException;
import decoders.DecoderManager;

public class StatusHelper {

    private static final Logger log = Logger.getLogger(StatusHelper.class);
    private SOAPMessage soapResponse;

    /**
     * Starting point for the SAAJ - SOAP Client Testing
     * @param eventRef event ref id
     * @param endPoint soap request end point
     * @throws DecoderConfigException decoder config error
     * @throws SOAPException soap web service error
     */
    public void getStatus(String eventRef, String endPoint) throws DecoderConfigException, SOAPException {
        // Create SOAP Connection
        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection soapConnection = soapConnectionFactory.createConnection();
        // Send SOAP Message to SOAP Server
        Decoder decoder = DecoderManager.getManager().getDecoder();
        String url = decoder.decodeATTURL() + "/att/ws/status?wsdl";
        log.info("URL : " + url + " EventId , Endpoint " + eventRef + ":" + endPoint);
        soapResponse = soapConnection.call(createSOAPRequest(eventRef, endPoint),url);

        soapConnection.close();
    }

    public void printStatus()throws SOAPException{
        try {
            // Print the request message
            log.info("Response received ");
            //soapResponse.writeTo(System.out);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            soapResponse.writeTo(out);
            String strSoapMessage = new String(out.toByteArray());
            log.info(strSoapMessage);
        } catch (IOException e) {
            log.error("Error logging the response");
        }
    }

    private SOAPMessage createSOAPRequest(String eventRef, String endPoint) throws SOAPException {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();
        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        // envelope.addNamespaceDeclaration("soapenv", "http://schemas.xmlsoap.org/soap/envelope/");
        envelope.addNamespaceDeclaration("ws", "http://ws.services.att.trading.betting.ats/");

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement key = soapBody.addChildElement("key");

        SOAPElement event = key.addChildElement("eventRef");
        event.addTextNode(eventRef);

        SOAPElement des = key.addChildElement("destination");
        des.addTextNode(endPoint);

        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", "");

        soapMessage.saveChanges();
        try {
        /* Print the request message */
            log.info("Request SOAP Message = ");
            //soapMessage.writeTo(System.out);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            soapMessage.writeTo(out);
            String strSoapMessage = new String(out.toByteArray());
            log.info(strSoapMessage);
        } catch (IOException e) {
            log.error("Error logging the response");
        }
        return soapMessage;
    }

    /**
     * Method used to read from the SoapResponse
     */
    private NodeList getValueOf(String xpath) throws SOAPException, XPathExpressionException {
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xPath = xPathFactory.newXPath();
        xPath.setNamespaceContext(new NamespaceContext() {
            @Override
            public String getNamespaceURI(String prefix) {
                return "http://ws.services.att.trading.betting.ats/";
            }

            @Override
            public String getPrefix(String namespaceURI) {
                return null;
            }

            @Override
            public Iterator getPrefixes(String namespaceURI) {
                System.out.println(namespaceURI);
                return null;
            }
        });
        SOAPBody body = soapResponse.getSOAPBody();
        Document document = body.extractContentAsDocument();

        //org.w3c.dom.Node node = list.item(0);
        //System.out.println(node.getFirstChild().getNodeName());

        return (NodeList) xPath.compile(xpath).evaluate(document, XPathConstants.NODESET);
    }


    public void printTextContantOfNode(String xpath) throws Exception {
        NodeList nodelist = getValueOf(xpath);
        for (int i = 0; i < nodelist.getLength(); i++) {
            Node node = nodelist.item(i);
            log.info("event id ----- " + node.getTextContent());
        }

    }

    /**
     * Method used to return the value from the SoapResponse
     */
    public List<String> getTextContentOfNode(String xpath) throws XPathExpressionException, SOAPException {
        log.info("xpath-------"+xpath);
        NodeList nodelist = getValueOf(xpath);
        List<String> nodevalue = new ArrayList<>();
        for (int i = 0; i < nodelist.getLength(); i++) {
            Node node = nodelist.item(i);
            log.info("Node text ----- " + node.getTextContent());
            nodevalue.add(node.getTextContent());
        }
        return nodevalue;
    }

}
