public enum Command {
    NO_COMMAND ((byte)0),
    ERROR_SERVER((byte)99),
    AUTHORIZATION((byte)20),
    SUCCESS_AUTH((byte)21),
    GET_FILE_LIST((byte)30),
    RETURN_FILE_LIST((byte)31),
    DOWNLOAD_FILE((byte)40),
    DOWNLOAD_FILE_PROCESS((byte)41),
    DOWNLOAD_SUCCESS((byte)42),
    DOWNLOAD_ERROR((byte)43),
    UPLOAD_FILE((byte)50),
    UPLOAD_FILE_PROCESS((byte)51),
    UPLOAD_SUCCESS((byte)52),
    DELETE_FILE((byte)60),
    DELETE_SUCCESS((byte)61);

    private byte commandCode;

    Command(byte commandCode) {
        this.commandCode = commandCode;
    }

    public byte getCommandCode() {
        return commandCode;
    }
}
