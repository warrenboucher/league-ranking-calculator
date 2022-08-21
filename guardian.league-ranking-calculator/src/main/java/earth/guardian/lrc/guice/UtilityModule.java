package earth.guardian.lrc.guice;

import com.google.inject.Binder;
import com.google.inject.Module;

import earth.guardian.lrc.utils.StartupParameters;
import earth.guardian.lrc.utils.StartupParametersImpl;
import earth.guardian.lrc.utils.simple.SimpleBufferedReaderMaker;
import earth.guardian.lrc.utils.simple.SimpleBufferedReaderMakerImpl;
import earth.guardian.lrc.utils.simple.SimpleBufferedWriterMaker;
import earth.guardian.lrc.utils.simple.SimpleBufferedWriterMakerImpl;
import earth.guardian.lrc.utils.simple.SimpleFileMaker;
import earth.guardian.lrc.utils.simple.SimpleFileMakerImpl;
import earth.guardian.lrc.utils.simple.SimpleLoggerMaker;
import earth.guardian.lrc.utils.simple.SimpleLoggerMakerImpl;
import earth.guardian.lrc.utils.simple.SimpleSystemExit;
import earth.guardian.lrc.utils.simple.SimpleSystemExitImpl;

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
		binder.bind(SimpleSystemExit.class).to(SimpleSystemExitImpl.class);
		binder.bind(SimpleBufferedReaderMaker.class).to(SimpleBufferedReaderMakerImpl.class);
		binder.bind(SimpleBufferedWriterMaker.class).to(SimpleBufferedWriterMakerImpl.class);		
		binder.bind(StartupParameters.class).to(StartupParametersImpl.class);
		binder.bind(SimpleFileMaker.class).to(SimpleFileMakerImpl.class);
	}

}
