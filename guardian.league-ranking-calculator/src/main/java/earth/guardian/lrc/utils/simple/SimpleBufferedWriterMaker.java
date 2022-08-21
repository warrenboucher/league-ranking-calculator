package earth.guardian.lrc.utils.simple;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

public interface SimpleBufferedWriterMaker {
	BufferedWriter make(File file) throws IOException;
}
