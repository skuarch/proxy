package model.common;

import java.io.IOException;
import model.net.ClientRestfulPost;
import model.net.ClientSocket;
import model.util.JSONUtilities;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * this class does all the business logic of the Proxy.
 *
 * @author skuarch
 */
public class ProxyProcess {

    private static final Logger logger = Logger.getLogger(ProxyProcess.class);

    //==========================================================================
    /**
     * this class doesn't need a constructor
     */
    private ProxyProcess() {
    } // end ProxyProcess

    //==========================================================================
    /**
     * open a socket with the remote client, the JSON must have at least 5
     * properties. <br/>
     * hostDestination, portDestination, request and timeout<br/>
     * hostDestination: IP address or domain to the remote host <br/>
     * portDestination: port of the remote host<br/>
     * request: is required to know what data do you want<br/>
     * timeout: time to wait a connection response<br/>
     * expectedReturn: wait for a response or not
     *
     * @param json String with JSON format
     * @return
     */
    public static String processSocket(String json) {

        if (!validateJsonSocket(json)) {
            logger.error("processSocket", new Exception("illegal parameter exception, json is incorrect"));
            return JSONUtilities.getJSONError("illegal parameter exception, json is incorrect");
        }

        boolean expectedReturn = false;
        ClientSocket cs = null;
        String jsonReceived = null;
        JSONObject jsono = null;
        String host = null;
        int port = 0;
        int timeout = 0;

        try {

            jsono = new JSONObject(json);
            host = jsono.getString("hostDestination");
            port = jsono.getInt("portDestination");
            timeout = jsono.getInt("timeout");
            expectedReturn = jsono.getBoolean("expectedReturn");

            cs = new ClientSocket(host, port, timeout);
            cs.connect();
            cs.sendText(json);

            if (expectedReturn) {
                jsonReceived = cs.receiveText();
            }
        } catch (IOException ioe) {
            logger.error("processSocket", ioe);
            return JSONUtilities.getJSONError(ioe.getMessage());
        } catch (JSONException jsone) {
            logger.error("processSocket", jsone);
            return JSONUtilities.getJSONError(jsone.getMessage());
        } catch (Exception e) {
            logger.error("processSocket", e);
            return JSONUtilities.getJSONError(e.getMessage());
        } finally {
            cs = null;
        }

        return jsonReceived;

    } // end processSocket

    //==========================================================================
    /**
     * send a post to the remote web service, the JSON must have at least 3
     * properties. <br/>
     * url: url of web service<br/>
     * expectedReturn: wait for a response or not<b/>
     * request: is required to know what data do you want.
     *
     * @param json String in JSON format
     * @return String in JSON format
     */
    public static String processWebService(String json) {

        if (!validateJsonWebService(json)) {
            logger.error("processWebService", new Exception("illegal parameter exception, json is incorrect"));
            return JSONUtilities.getJSONError("illegal parameter exception, json is incorrect");
        }

        boolean expectedReturn = false;
        ClientRestfulPost crp = null;
        String jsonReceived = null;
        JSONObject jsono = null;
        String url = null;

        try {

            jsono = new JSONObject(json);
            url = jsono.getString("url");
            expectedReturn = jsono.getBoolean("expectedReturn");
            crp = new ClientRestfulPost(url);
            crp.send(jsono);

            if (expectedReturn) {
                jsonReceived = crp.receive();
            }

            crp.closeClient();

        } catch (IOException ioe) {
            logger.error("processWebService", ioe);
            return JSONUtilities.getJSONError(ioe.getMessage());
        } catch (JSONException jsone) {
            logger.error("processWebService", jsone);
            return JSONUtilities.getJSONError(jsone.getMessage());
        } catch (Exception ex) {
            logger.error("processWebService", ex);
            return JSONUtilities.getJSONError(ex.getMessage());
        } finally {
            crp = null;
        }

        return jsonReceived;
    }

    //==========================================================================
    /**
     * check if the JSON has a at least 5 properties
     * (hostDestination,portDestination,expectedReturn,timeout and request).
     *
     * @param jsonString
     * @return boolean
     */
    private static boolean validateJsonSocket(String jsonString) {

        boolean flag = false;

        try {

            if (jsonString == null || jsonString.length() < 1) {
                return false;
            }

            JSONObject jsono = new JSONObject(jsonString);

            if (!jsono.has("hostDestination")) {
                return false;
            }

            if (!jsono.has("portDestination")) {
                return false;
            }

            if (!jsono.has("expectedReturn")) {
                return false;
            }

            if (!jsono.has("timeout")) {
                return false;
            }

            if (!jsono.has("request")) {
                return false;
            }

            flag = true;

        } catch (JSONException e) {
            logger.error("validateJsonSocket", e);
            flag = false;
        }

        return flag;

    } // end validateJson

    //==========================================================================
    /**
     * check if the JSON has a at least 3 properties (url,expectedReturn and
     * request).
     *
     * @param jsonString String in JSON format
     * @return boolean
     */
    private static boolean validateJsonWebService(String jsonString) {

        boolean flag = false;

        try {

            if (jsonString == null || jsonString.length() < 1) {
                return false;
            }

            JSONObject jsono = new JSONObject(jsonString);

            if (!jsono.has("url")) {
                return false;
            }

            if (!jsono.has("expectedReturn")) {
                return false;
            }

            if (!jsono.has("request")) {
                return false;
            }

            flag = true;

        } catch (JSONException e) {
            logger.error("validateJsonWebService", e);
            flag = false;
        }

        return flag;
    } // end validateJsonWebService

} // end class
