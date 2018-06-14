package att;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;

import ats.betting.trading.att.ws.history.HistoryService;
import ats.betting.trading.att.ws.history.HistoryService_Service;
import ats.betting.trading.att.ws.scenario.TestScenarioService;
import ats.betting.trading.att.ws.scenario.TestScenarioService_Service;

public class ATTServiceFactory {

    //private static final QName STATUS_SERVICE_QNAME = new QName("http://test.amelco.co.uk/status", "statusService");
    //private static final QName STATUS_PORT_QNAME = new QName("http://test.amelco.co.uk/status", "statusPort");
    private static final QName STATUS_SERVICE_QNAME = new QName("http://iombenampsatt01.amelco.lan:8088",
            "TestScenarioServicePort");
    private static final QName STATUS_PORT_QNAME = new QName("http://iombenampsatt01.amelco.lan:8088",
            "statusPort");

    public static TestScenarioService getTestScenarioService(String endpointUrl) throws MalformedURLException {
        TestScenarioService_Service serviceFactory = new TestScenarioService_Service(new URL(endpointUrl.concat("/att/ws/scenario?wsdl")));
        return serviceFactory.getTestScenarioServicePort();
    }

    public static HistoryService getHistoryService(String endpointUrl) throws MalformedURLException {
        HistoryService_Service serviceFactory = new HistoryService_Service(new URL(endpointUrl.concat("/att/ws/history?wsdl")));
        return serviceFactory.getHistoryServicePort();
    }

    public static Dispatch<Source> getStatusDispatch(String endpointUrl) throws MalformedURLException {
        Service service = Service.create(new URL(endpointUrl.concat("/att/ws/status?wsdl")), STATUS_SERVICE_QNAME);
        return service.createDispatch(STATUS_PORT_QNAME, Source.class, Service.Mode.PAYLOAD);
    }

}
