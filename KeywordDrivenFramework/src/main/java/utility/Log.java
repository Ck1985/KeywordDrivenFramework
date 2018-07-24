package utility;

import org.apache.log4j.Logger;

public class Log {
	private static Logger logger = Logger.getLogger(Log.class.getName());
	
	public static void startTestCase(String testCaseName) {
		info("********* Start TestCase **********");
		info("$$$$$$$$$$$ " + testCaseName + " $$$$$$$$$$$$");
		info("***********************************");
	}
	
	public static void endTestCase(String testCaseName) {
		info("********** " + "-E-N-D-" + " ****************");
		info("********** " + "TestCase" + " ***************");
		info("#");
		info("#");
		info("#");
	}
	
	public static void info(String message) {
		logger.info(message);
	}
	
	public static void warn(String message) {
		logger.warn(message);
	}
	
	public static void error(String message) {
		logger.error(message);
	}
	
	public static void fatal(String message) {
		logger.fatal(message);
	}
	
	public static void debug(String message) {
		logger.debug(message);
	}
}
