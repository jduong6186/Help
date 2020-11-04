package housingapp.errors;

import housingapp.SysConst;

/**
 * exception thrown when user's session has expired or contains invalid details
 */

public class InvalidSessionDetailsException extends Exception {

    public InvalidSessionDetailsException() {
        super(SysConst.ERR_INVALID_SESSION_DETAILS);
    }

    public InvalidSessionDetailsException(String msg) {
        super(SysConst.ERR_INVALID_SESSION_DETAILS + " " + msg);
    }
}
