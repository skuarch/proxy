package model.util;

/**
 *
 * @author skuarch
 */
public final class JSONUtilities {

    //==========================================================================
    private JSONUtilities() {
    } // end JSONUtilities    

    //==========================================================================
    public static String getJSONError(String error) {

        if (error == null || error.length() < 1) {
            error = "unexpected error";
        }

        return "{\"error\":\"" + error + "\"}";
    }

} // end class
