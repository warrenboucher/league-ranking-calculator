package earth.guardian.lrc;

import java.util.UUID;

import org.apache.commons.cli.CommandLine;

import com.google.inject.Injector;

/**
 * Contains variables that can get used by entire application.  For example everyone uses the Injector that was setup
 * in the Main class.
 */
public class LrcRuntime {
	
	/**
	 * 
	 */
	private static Injector injector;
	
	/**
	 * 
	 */
	private static CommandLine commandLine;
	
	/**
	 * If multiple instances of the application get started at the same time we want logs to be able to distinguish
	 * between them.
	 */
	private static final String CORRELATION_ID = UUID.randomUUID().toString();

	/**
	 * 
	 */
	private LrcRuntime() {
		// Do not allow instantiation.
	}
	
	public static Injector getInjector() {
		return injector;
	}

	public static void setInjector(Injector injector) {
		LrcRuntime.injector = injector;
	}

	public static String getCorrelationId() {
		return CORRELATION_ID;
	}

	public static CommandLine getCommandLine() {
		return commandLine;
	}

	public static void setCommandLine(CommandLine commandLine) {
		LrcRuntime.commandLine = commandLine;
	}
	
}
