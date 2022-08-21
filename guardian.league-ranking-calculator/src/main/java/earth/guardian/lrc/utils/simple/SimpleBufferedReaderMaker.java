package earth.guardian.lrc.utils.simple;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

public interface SimpleBufferedReaderMaker {
	BufferedReader make(File file) throws IOException;
}
