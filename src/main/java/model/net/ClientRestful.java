package model.net;

import org.json.JSONObject;

/**
 *
 * @author skuarch
 */
public interface ClientRestful {
    
    public void send(JSONObject json) throws Exception;
    public String receive() throws Exception;
    
} // end interface