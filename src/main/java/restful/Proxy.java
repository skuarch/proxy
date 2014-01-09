package restful;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;
import model.common.ProxyProcess;
import model.util.JSONUtilities;
import org.apache.log4j.Logger;

/**
 * this proxy is to open a remote communication with a client either socket or
 * restful web service.
 *
 * @author skuarch
 */
@Path("/v1")
public final class Proxy {

    private static final Logger logger = Logger.getLogger(Proxy.class);

    //==========================================================================
    /**
     * this method delegate the process to <code>ProcessSocket</code>.
     *
     * @param json String with JSON format
     * @return String in JSON format either with data or error property
     */
    @POST
    @Path("/socketRemoteConnection")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String socket(String json) {

        if (json == null || json.length() < 1) {
            logger.error("socket", new IllegalArgumentException("json is incorrect or null"));
            return JSONUtilities.getJSONError("json is incorrect or null");
        }

        return ProxyProcess.processSocket(json);

    } // end socket

    //==========================================================================
    /**
     * this method delegate the process to <code>ProcessWebService</code>.
     *
     * @param json
     * @return String in JSON format.
     */
    @POST
    @Path("/webserviceRemoteConnection")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String webService(String json) {

        if (json == null || json.length() < 1) {
            logger.error("webService", new IllegalArgumentException("json is incorrect or null"));
            return JSONUtilities.getJSONError("json is incorrect or null");
        }

        return ProxyProcess.processWebService(json);

    } // end post

} // end class
