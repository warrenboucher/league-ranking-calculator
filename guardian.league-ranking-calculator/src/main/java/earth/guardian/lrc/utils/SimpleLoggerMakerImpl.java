package earth.guardian.lrc.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.inject.Singleton;

/**
 * Extract simple code block that is tricky to test into its own class so that code that use it is simpler to test. 
 */
@Singleton
public class SimpleLoggerMakerImpl implements SimpleLoggerMaker {

	@Override
	public Logger make(Class<?> clazz) {
		return LogManager.getLogger(clazz);
	}

}
