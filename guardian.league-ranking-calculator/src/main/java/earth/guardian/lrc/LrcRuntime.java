package earth.guardian.lrc;

import java.util.UUID;

import com.google.inject.Injector;

public class LrcRuntime {

	/**
	 * 
	 */
	private LrcRuntime() {
		// Do not allow instantiation.
	}
	
	private static Injector injector;
	
	/**
	 * If multiple instances of the application get started at the same time we want logs to be able to distinguish
	 * between them.
	 */
	private static final String CORRELATION_ID = UUID.randomUUID().toString();

	public static Injector getInjector() {
		return injector;
	}

	public static void setInjector(Injector injector) {
		LrcRuntime.injector = injector;
	}

	public static String getCorrelationId() {
		return CORRELATION_ID;
	}
	
}
