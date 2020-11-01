package housingapp.errors;

import housingapp.SysConst;

public class InvalidLoginException extends Exception {

    public InvalidLoginException() {
        super(SysConst.ERR_INVALID_LOGIN);
    }

    public InvalidLoginException(String msg) {
        super(SysConst.ERR_INVALID_LOGIN + " " + msg);
    }
}
