package ru.qa.summer.support.util;

import lombok.experimental.UtilityClass;

import javax.xml.soap.SOAPMessage;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

@UtilityClass
public class SOAPUtil {
    public static String messageToString(SOAPMessage message) throws TransformerException {
        DOMSource source = new DOMSource(message.getSOAPPart());
        StringWriter stringResult = new StringWriter();
        TransformerFactory.newInstance().newTransformer().transform(source, new StreamResult(stringResult));
        return stringResult.toString();
    }
}
