package com.automic.constants;

/**
 * Exception constants used in the application
 *
 */
public class ExceptionConstants {

	
    public static final String INVALID_FILE_TYPE = "Invalid file type provided [%s], required PNG file type.";
    public static final String INVALID_CLIENT_TYPE = "Invalid Client value provided [%s], required integer type.";
    public static final String INVALID_JSON = "File doesn't have correct Json";
		
    public static final String UNABLE_TO_FLUSH_STREAM = "Error while flushing stream";
    public static final String INVALID_ARGS = "Improper Args. Possible cause : %s";

    public static final String INVALID_INPUT_PARAMETER = "Invalid value for parameter [%s] : [%s]";

    public static final String GENERIC_ERROR_MSG = "System Error occured.";

 // Certificate errors
    public static final String ERROR_SKIPPING_CERT = "Error skipping the certificate validation";
    public static final String PAGE_TIMEOUT = "Timeout happened. Unable to get ready login page in provided timeframe [%s seconds].";
    public static final String PAGE_INPUTS_TIMEOUT = "Timeout happened. Unable to load login page inputs in provided timeframe [%s seconds].";
    
    
    private ExceptionConstants() {
    }
}
