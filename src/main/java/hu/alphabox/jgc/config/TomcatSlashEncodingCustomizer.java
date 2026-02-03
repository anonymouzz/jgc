package hu.alphabox.jgc.config;

import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.buf.EncodedSolidusHandling;
import org.springframework.boot.tomcat.TomcatConnectorCustomizer;
import org.springframework.stereotype.Component;

@Component
class TomcatSlashEncodingCustomizer implements TomcatConnectorCustomizer {

  @Override
  public void customize(Connector connector) {
    connector.setEncodedSolidusHandling(EncodedSolidusHandling.PASS_THROUGH.getValue());
  }
}
