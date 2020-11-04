package housingapp.errors;

import housingapp.SysConst;

/**
 * exception thrown when user attempts to access resources beyond their permissions
 */

public class InvalidPermissionException extends Exception {

    public InvalidPermissionException() {
        super(SysConst.ERR_INVALID_PERMISSION);
    }

    public InvalidPermissionException(String msg) {
        super(SysConst.ERR_INVALID_PERMISSION + " " + msg);
    }
}
