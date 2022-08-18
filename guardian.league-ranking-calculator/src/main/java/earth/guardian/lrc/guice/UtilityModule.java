package earth.guardian.lrc.guice;

import com.google.inject.Binder;
import com.google.inject.Module;

import earth.guardian.lrc.utils.SimpleLoggerMaker;
import earth.guardian.lrc.utils.SimpleLoggerMakerImpl;

public class UtilityModule implements Module {

	/**
	 * @param binder
	 */
	@Override
	public void configure(Binder binder) {
		binder.bind(SimpleLoggerMaker.class).to(SimpleLoggerMakerImpl.class);
	}

}
