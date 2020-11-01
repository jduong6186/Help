package housingapp.errors;

import housingapp.system.SysConst;

public class InvalidSessionDetailsException extends Exception {

    public InvalidSessionDetailsException() {
        super(SysConst.ERR_INVALID_SESSION_DETAILS);
    }

    public InvalidSessionDetailsException(String msg) {
        super(SysConst.ERR_INVALID_SESSION_DETAILS + " " + msg);
    }
}
