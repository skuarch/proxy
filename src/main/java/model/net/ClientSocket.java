package model.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.rmi.UnexpectedException;
import model.util.IOUtilities;

/**
 * open a remote connection using sockets.
 *
 * @author skuarch
 */
public final class ClientSocket {

    private String host = null;
    private int port = 0;
    private int timeout = 0;
    private Socket socket = null;
    private ObjectInputStream objectInputStream = null;
    private ObjectOutputStream objectOutputStream = null;
    private InputStream inputStream = null;
    private OutputStream outputStream = null;

    //==========================================================================
    /**
     * create a instance.
     *
     * @param host String remote host
     * @param port int remote port
     * @param timeout int time to wait response
     */
    public ClientSocket(String host, int port, int timeout) {
        this.host = host;
        this.port = port;
        this.timeout = timeout;
    } // end ClientSocket

    //==========================================================================
    /**
     * open a connection.
     *
     * @throws IOException
     */
    public void connect() throws IOException {

        if (host == null || host.length() < 1) {
            throw new NullPointerException("host is null");
        }

        if (port <= 1) {
            throw new NullPointerException("port is less than 0");
        }

        if (timeout < 1) {
            throw new NullPointerException("timeOut is less than 0");
        }

        socket = new Socket(host, port);
        socket.setSoTimeout(timeout);
        socket.setKeepAlive(false);

    } // end connect      

    //==========================================================================
    /**
     * stablish keep alive.
     *
     * @param flag boolean
     * @throws SocketException
     * @throws UnexpectedException if the socket is null this exception going to
     * be throw.
     */
    public void setKeepAlive(boolean flag) throws SocketException, UnexpectedException {

        checkSocket();
        socket.setKeepAlive(flag);

    } // end setKeepAlive

    //==========================================================================
    /**
     * send text to the remote host.
     * @param string Text
     * @throws UnexpectedException
     * @throws IOException 
     */
    public void sendText(String string) throws UnexpectedException, IOException {

        if (string == null || string.length() < 1) {
            throw new IllegalArgumentException("string is null or empty");
        }

        checkSocket();

        outputStream = socket.getOutputStream();
        objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeUTF(string);
        objectOutputStream.flush();

    } // end sendText

    //==========================================================================
    /**
     * validate if is possible to do the connection.
     *
     * @throws UnexpectedException
     */
    private void checkSocket() throws UnexpectedException {
        if (socket == null || socket.isClosed()) {
            throw new UnexpectedException("illegal operation socket is closed or null");
        }
    } // end checkSocket

    //==========================================================================
    /**
     * receive text from the remote host.
     * @return String
     * @throws UnexpectedException
     * @throws IOException 
     */
    public String receiveText() throws UnexpectedException, IOException {

        checkSocket();
        String text = null;

        inputStream = socket.getInputStream();
        objectInputStream = new ObjectInputStream(inputStream);
        text = objectInputStream.readUTF();

        return text;

    } // end receiveText

    //==========================================================================
    /**
     * close all the inputs and outputs.
     */
    public void closeConnection() {
        IOUtilities.closeInputStream(inputStream);
        IOUtilities.closeInputStream(objectInputStream);
        IOUtilities.closeOutputStream(outputStream);
        IOUtilities.closeOutputStream(objectOutputStream);
        IOUtilities.closeSocket(socket);
    } // end closeConnection

} // end class
