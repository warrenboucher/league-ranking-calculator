package earth.guardian.lrc.guice;

import com.google.inject.Binder;
import com.google.inject.Module;

import earth.guardian.lrc.utils.simple.SimpleLoggerMaker;
import earth.guardian.lrc.utils.simple.SimpleLoggerMakerImpl;
import earth.guardian.lrc.utils.simple.SystemExit;
import earth.guardian.lrc.utils.simple.SystemExitImpl;

/**
 * Bind interfaces to implementations.
 */
public class UtilityModule implements Module {

	/**
	 * @param binder
	 */
	@Override
	public void configure(Binder binder) {
		binder.bind(SimpleLoggerMaker.class).to(SimpleLoggerMakerImpl.class);
		binder.bind(SystemExit.class).to(SystemExitImpl.class);
	}

}
