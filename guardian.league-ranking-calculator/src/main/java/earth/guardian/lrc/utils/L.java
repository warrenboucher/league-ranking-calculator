package earth.guardian.lrc.utils;

import org.apache.logging.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import earth.guardian.lrc.LrcRuntime;
import earth.guardian.lrc.Main;
import earth.guardian.lrc.utils.simple.SimpleLoggerMaker;

/**
 * Using short class name because we will be using logging everywhere.  L is also a convention in some languages.
 */
@Singleton
public class L {

	/**
	 * 0 = Thread.  
	 * 1 = getCallingClass
	 * 2 = This method.  
	 * 3 = Who called this method.
	 */
	private static final int INDEX_CALLING_CLASS_BASIC = 3;
	
	/**
	 * 0 = Thread.  
	 * 1 = getCallingClass
	 * 2 = logging method  
	 * 3 = Who called logging method.
	 *  */
	private static final int INDEX_CALLING_CLASS_LOG_METHOD = 4; 
	
	/**
	 * 
	 */
	@Inject
	public L() {
		// Inject constructor. 
	}
	
	/**
	 * Calling this method is the same as calling</br>
	 * <code>
	 * L.getLogger(getClass())
	 * </code>
	 * @return
	 */
	public static Logger getLogger() {		
		return getLogger(getCallingClass(INDEX_CALLING_CLASS_BASIC));	
	}
	
	/**
	 * 
	 * @return
	 */
	private static Class<?> getCallingClass(int callingClassIndex) {
		try {
			final StackTraceElement element = Thread.currentThread().getStackTrace()[callingClassIndex];
			return Class.forName(element.getClassName());
		} catch (ClassNotFoundException e) {
			return Main.class; // Log against main class instead of having nothing logged.
		}	
	}
	
	/**
	 * 
	 * @param clazz
	 * @return
	 */
	public static Logger getLogger(Class<?> clazz) {
		return LrcRuntime.getInjector().getInstance(SimpleLoggerMaker.class).make(clazz);
	}
	
	/**
	 * 
	 * @param teamName
	 * @param matchScore
	 * @param seasonScore
	 */
	public void logMatchResult(String teamName, int matchScore, long seasonScore) {
		final Logger logger = getLogger(getCallingClass(INDEX_CALLING_CLASS_LOG_METHOD));
		logger.debug("{} {} recorded match score {}.  Season total is {}.", LrcRuntime.getCorrelationId(), teamName, matchScore, seasonScore);
	}
	
}
