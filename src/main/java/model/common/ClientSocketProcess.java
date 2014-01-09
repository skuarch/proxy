package model.common;

import java.io.IOException;
import model.net.ClientSocket;
import org.json.JSONObject;

/**
 * wrapper of ClientSocket
 *
 * @author skuarch
 */
public final class ClientSocketProcess {

    private ClientSocket clientSocket = null;

    //==========================================================================
    public ClientSocketProcess(String host, int port, int timeout) {
        clientSocket = new ClientSocket(host, port, timeout);
    } // end ClientSocketProcess

    //==========================================================================
    public String sendAndReceive(JSONObject jsono) throws IOException {

        if (jsono == null || jsono.length() < 1) {
            throw new IllegalArgumentException("json is null or empty");
        }

        String returnString = null;

        clientSocket.connect();
        clientSocket.sendText(jsono.toString());
        returnString = clientSocket.receiveText();

        return returnString;

    } // end sendAndReceive

} // end class
