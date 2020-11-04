package housingapp.errors;

import housingapp.SysConst;

/**
 * exception thrown when user fails login attempt
 */

public class InvalidLoginException extends Exception {

    public InvalidLoginException() {
        super(SysConst.ERR_INVALID_LOGIN);
    }

    public InvalidLoginException(String msg) {
        super(SysConst.ERR_INVALID_LOGIN + " " + msg);
    }
}
