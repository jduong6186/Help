package housingapp.errors;

import housingapp.system.SysConst;

public class InvalidPermissionException extends Exception {

    public InvalidPermissionException() {
        super(SysConst.ERR_INVALID_PERMISSION);
    }

    public InvalidPermissionException(String msg) {
        super(SysConst.ERR_INVALID_PERMISSION + " " + msg);
    }
}