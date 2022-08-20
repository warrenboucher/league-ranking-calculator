package earth.guardian.lrc.utils.simple;

import com.google.inject.Singleton;

@Singleton
public class SystemExitImpl implements SystemExit {

	/**
	 * @param code
	 */
	@Override
	public void exit(int code) {
		System.exit(code);
	}

}
