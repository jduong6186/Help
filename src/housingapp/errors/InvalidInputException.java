package housingapp.errors;

import housingapp.SysConst;

/**
 * exception thrown when user provides invalid input
 */

public class InvalidInputException extends Exception {

    public InvalidInputException() {
        super(SysConst.ERR_INVALID_INPUT);
    }

    public InvalidInputException(String msg) {
        super(SysConst.ERR_INVALID_INPUT + " " + msg);
    }
}
