package earth.guardian.lrc.utils.simple;

/**
 * Allows for better test coverage.  Can't really go System.exit(1) in a unit test. 
 */
public interface SystemExit {
	void exit(int code);
}
