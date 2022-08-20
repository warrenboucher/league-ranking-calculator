package earth.guardian.lrc.constants;

/**
 * Contains constants specific to application command line parameters.
 */
public class CommandLineConstants {

	private static final String ARGUMENT_INPUT = "input";
	private static final String ARGUMENT_OUTPUT = "output";
	private static final String APPLICATION_NAME = "League-Ranking-Calculator";
	
	private CommandLineConstants() {
		// Do not allow initialization.
	}

	public static String getArgumentInput() {
		return ARGUMENT_INPUT;
	}

	public static String getArgumentOutput() {
		return ARGUMENT_OUTPUT;
	}

	public static String getApplicationName() {
		return APPLICATION_NAME;
	}
	
}
