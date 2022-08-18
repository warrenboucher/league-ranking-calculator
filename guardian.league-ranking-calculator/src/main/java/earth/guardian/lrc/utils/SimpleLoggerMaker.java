package earth.guardian.lrc.utils;

import org.apache.logging.log4j.Logger;

public interface SimpleLoggerMaker {
	Logger make(Class<?> clazz);
}
