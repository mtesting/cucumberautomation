package att.ws;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import ats.betting.trading.att.ws.history.HistoryService;
import ats.betting.trading.att.ws.history.HistoryService_Service;
import ats.betting.trading.att.ws.history.dto.HistoryEntry;
import decoders.Decoder;
import decoders.DecoderConfigException;
import decoders.DecoderManager;

public class HistoryServiceSupport {
    private final HistoryService service;
    private static final Logger log = Logger.getLogger(HistoryServiceSupport.class);
    private SOAPMessage historyDetailResponse;

    private List<HistoryEntry> list = null;

    public HistoryServiceSupport() {
        HistoryService_Service s = new HistoryService_Service();
        service = s.getHistoryServicePort();
        Client client = ClientProxy.getClient(service);
        Endpoint endpoint = client.getEndpoint();
        // Enable Logging
        // from http://cwiki.apache.org/CXF20DOC/debugging.html
        client.getInInterceptors().add(new LoggingInInterceptor());
        client.getOutInterceptors().add(new LoggingOutInterceptor());
    }

    public SOAPMessage getHistroyDetailResponse() {
        return historyDetailResponse;
    }

    public void getLatestHistorydetail(Long eventid, String destination) throws DecoderConfigException, SOAPException{
        this.getHistory(eventid, destination);
        Long latestnum = list.get(list.size()-1).getNumber();
        getHistoryDetailBynum(eventid, destination, latestnum);

    }
    public void getHistory (Long eventid, String destination) {
        list =service.getHistory(eventid, destination);
        printHistory();
    }

    public void getHistoryDetailBynum(Long eventid, String destination, Long num) throws DecoderConfigException, SOAPException {
        DecoderManager decoderManager;
        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection soapConnection = soapConnectionFactory.createConnection();
        // Send SOAP Message to SOAP Server
        Decoder decoder = DecoderManager.getManager().getDecoder();
        String url = decoder.decodeATTURL() + "/att/ws/history/details?wsdl";
        log.info("URL : " + url + " Eventid , Endpoint " + eventid.toString() + ":" + destination);
        historyDetailResponse = soapConnection.call(createSOAPRequest(eventid.toString(), destination, num.toString()), url);
        try {
        /* Print the request message */
            log.info("Response SOAP Message = ");
            historyDetailResponse.writeTo(System.out);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            historyDetailResponse.writeTo(out);
            String strSoapMessage = new String(out.toByteArray());
            log.info(strSoapMessage);
        } catch (IOException e) {
            log.error("Error logging the response");
        }

        soapConnection.close();

    }

    private SOAPMessage createSOAPRequest(String eventRef, String destination, String number) throws SOAPException {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();
        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        // envelope.addNamespaceDeclaration("soapenv", "http://schemas.xmlsoap.org/soap/envelope/");
        envelope.addNamespaceDeclaration("ws", "http://ws.services.att.trading.betting.ats/");

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement eventNode = soapBody.addChildElement("event");

        //Create Attribute Nodes
        Name nEventID = envelope.createName("eventRef");
        Name nDestination = envelope.createName("destination");
        Name nNum = envelope.createName("number");

        //add Attribute to EventHelperTemplate node
        eventNode.addAttribute(nEventID,eventRef);
        eventNode.addAttribute(nDestination,destination);
        eventNode.addAttribute(nNum, number);

        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", "");

        soapMessage.saveChanges();
        try {
        /* Print the request message */
            log.info("Request SOAP Message = ");
            soapMessage.writeTo(System.out);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            soapMessage.writeTo(out);
            String strSoapMessage = new String(out.toByteArray());
            log.info(strSoapMessage);
        } catch (IOException e) {
            log.error("Error logging the response");
        }
        return soapMessage;
    }

    private void printHistory(){
        for (HistoryEntry historyDetail : list){
            String time = historyDetail.getEntryTime().toString();
            String type = historyDetail.getMessageType();
            Long num = historyDetail.getNumber();

            log.info("At time " + time + " received " +  Long.toString(num) + ":" + type);
        }
    }

}
