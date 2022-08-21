package earth.guardian.lrc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.cli.CommandLine;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.google.inject.Binder;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;

import earth.guardian.lrc.LeagueRankingCalculatorTest.LeagueRankingCalculatorTestModule;
import earth.guardian.lrc.constants.CommandLineConstants;
import earth.guardian.lrc.utils.L;
import earth.guardian.lrc.utils.StartupParameters;
import earth.guardian.lrc.utils.simple.SimpleBufferedReaderMaker;
import earth.guardian.lrc.utils.simple.SimpleBufferedWriterMaker;
import earth.guardian.lrc.utils.simple.SimpleFileMaker;
import earth.guardian.lrc.utils.simple.SimpleLoggerMaker;
import earth.guardian.lrc.utils.simple.SimpleSystemExit;

@Guice(modules = {LeagueRankingCalculatorTestModule.class})
public class LeagueRankingCalculatorTest {

	private static final boolean RAISE_FILE_NOT_FOUND = true;
	private static final boolean DO_NOT_RAISE_FILE_NOT_FOUND = false;
	
	private static final boolean RAISE_IO = true;
	private static final boolean DO_NOT_RAISE_IO = false;
	
	private static final boolean FILE_FOUND = true;
	private static final boolean FILE_NOT_FOUND = false;
	
	private static final String INPUT_FILE_NAME = "input.txt";
	private static final String OUTPUT_FILE_NAME = "output.txt";
	private static final String[] TEST_ARGUMENTS = new String[] {"-i", INPUT_FILE_NAME, "-o", OUTPUT_FILE_NAME};
	private static final String MOCK_LEAGUE_RESULT = "MOCK_LEAGUE_RESULT";
	
	/**
	 * Injector that has been configured specifically with this units module. 
	 */
	@Inject private Injector injector;
	
	@Inject private SimpleLoggerMaker simpleLoggerMaker;
	
	@Inject private StartupParameters mockStartupParameters;
	
	@Inject private SimpleBufferedReaderMaker mockSimpleBufferedReader;
	
	@Inject private SimpleBufferedWriterMaker mockSimpleBufferedWriterMaker;
	
	@Inject private SimpleFileMaker mockSimpleFileMaker;
	
	@Inject private SimpleSystemExit mockSimpleSystemExit;	
	
	@Inject private LeagueMatchRecorder mockLeagueMatchRecorder;
	
	@Inject private L mockLogger;
	
	/**
	 * Run before each test to setup mocks and any other required setup.
	 */
	@BeforeMethod(groups = TestConstants.UNITTEST)
	public void init() {
		MockitoAnnotations.initMocks(this);
		LrcRuntime.setInjector(injector);
		Mockito.reset(simpleLoggerMaker);
		Mockito.reset(mockStartupParameters);
		Mockito.reset(mockSimpleBufferedReader);
		Mockito.reset(mockSimpleBufferedWriterMaker);
		Mockito.reset(mockSimpleFileMaker);
		Mockito.reset(mockSimpleSystemExit);
		Mockito.reset(mockLeagueMatchRecorder);
		Mockito.reset(mockLogger);
	}
	
	/**
	 * Basic test to see if we can instantiate model.
	 */
	@Test(groups = TestConstants.UNITTEST)
	public void testConstructor() {
		Assert.assertNotNull(LrcRuntime.getInjector().getInstance(LeagueRankingCalculator.class), "Failed to create fixture instance.");				
	}	
	
	/**
	 * Basic test assuming everything is working fine.
	 * @throws IOException 
	 */
	@Test(groups = TestConstants.UNITTEST)
	public void testSimpleUseCase() throws IOException {		
		final LeagueRankingCalculator fixture = LrcRuntime.getInjector().getInstance(LeagueRankingCalculator.class);
				
		setupCommandLineResponse();
		setupInputFile(Arrays.asList(FILE_FOUND));
		setupOutputFile(DO_NOT_RAISE_IO, Arrays.asList(FILE_NOT_FOUND));
		setupInputFileReadResults(DO_NOT_RAISE_FILE_NOT_FOUND, DO_NOT_RAISE_IO);
		BufferedWriter mockBufferedWriter = setupLeagueMatchRecorder();
		
		// 1. Exercise fixture.
		fixture.run(TEST_ARGUMENTS);
		
		// 2. Assert.
		Mockito.verify(mockStartupParameters).interpret(TEST_ARGUMENTS);
		Mockito.verify(mockBufferedWriter).write(MOCK_LEAGUE_RESULT);
	}
	
	/**
	 * Test input file not found.  Should log and exit.
	 * @throws IOException 
	 */
	@Test(groups = TestConstants.UNITTEST)
	public void testInputFileNotFound() throws IOException {		
		final LeagueRankingCalculator fixture = LrcRuntime.getInjector().getInstance(LeagueRankingCalculator.class);
				
		setupCommandLineResponse();
		File inputFile = setupInputFile(Arrays.asList(FILE_NOT_FOUND));
		setupOutputFile(DO_NOT_RAISE_IO, Arrays.asList(FILE_NOT_FOUND));
		setupInputFileReadResults(DO_NOT_RAISE_FILE_NOT_FOUND, DO_NOT_RAISE_IO);
		setupLeagueMatchRecorder();
		
		// 1. Exercise fixture.
		fixture.run(TEST_ARGUMENTS);
		
		// 2. Assert.
		Mockito.verify(mockLogger).logInputFileDoesNotExist(inputFile);
		Mockito.verify(mockSimpleSystemExit, Mockito.atLeast(1)).exit(1);
	}	
	
	/**
	 * Test input file not found while reading.  Should log and exit.
	 * @throws IOException 
	 */
	@Test(groups = TestConstants.UNITTEST)
	public void testInputFileDeletedWhileReading() throws IOException {		
		final LeagueRankingCalculator fixture = LrcRuntime.getInjector().getInstance(LeagueRankingCalculator.class);
				
		setupCommandLineResponse();
		setupInputFile(Arrays.asList(FILE_NOT_FOUND));
		setupOutputFile(DO_NOT_RAISE_IO, Arrays.asList(FILE_NOT_FOUND));
		setupInputFileReadResults(RAISE_FILE_NOT_FOUND, DO_NOT_RAISE_IO);
		setupLeagueMatchRecorder();
		
		// 1. Exercise fixture.
		fixture.run(TEST_ARGUMENTS);
		
		// 2. Assert.
		Mockito.verify(mockLogger).logInputFileDoesNotExist(Mockito.any(), Mockito.any());
		Mockito.verify(mockSimpleSystemExit, Mockito.atLeast(1)).exit(1);
	}		
	
	/**
	 * Test error while reading file.  Should log and exit.
	 * @throws IOException 
	 */
	@Test(groups = TestConstants.UNITTEST)
	public void testErrorWhileReadingFile() throws IOException {		
		final LeagueRankingCalculator fixture = LrcRuntime.getInjector().getInstance(LeagueRankingCalculator.class);
				
		setupCommandLineResponse();
		setupInputFile(Arrays.asList(FILE_NOT_FOUND));
		setupOutputFile(DO_NOT_RAISE_IO, Arrays.asList(FILE_NOT_FOUND));
		setupInputFileReadResults(DO_NOT_RAISE_FILE_NOT_FOUND, RAISE_IO);
		setupLeagueMatchRecorder();
		
		// 1. Exercise fixture.
		fixture.run(TEST_ARGUMENTS);
		
		// 2. Assert.
		Mockito.verify(mockLogger).logFailedToReadLineFromInputFile(Mockito.any(), Mockito.any());
		Mockito.verify(mockSimpleSystemExit, Mockito.atLeast(1)).exit(1);
	}
	
	/**
	 * Test output file already exists.  Should log and exit.
	 * @throws IOException 
	 */
	@Test(groups = TestConstants.UNITTEST)
	public void testOutputFileAlreadyExists() throws IOException {		
		final LeagueRankingCalculator fixture = LrcRuntime.getInjector().getInstance(LeagueRankingCalculator.class);
				
		setupCommandLineResponse();
		setupInputFile(Arrays.asList(FILE_NOT_FOUND));
		File outputFile = setupOutputFile(DO_NOT_RAISE_IO, Arrays.asList(FILE_FOUND));
		setupInputFileReadResults(RAISE_FILE_NOT_FOUND, DO_NOT_RAISE_IO);
		setupLeagueMatchRecorder();
		
		// 1. Exercise fixture.
		fixture.run(TEST_ARGUMENTS);
		
		// 2. Assert.
		Mockito.verify(mockLogger).logOutputFileAlreadyExists(outputFile);
		Mockito.verify(mockSimpleSystemExit, Mockito.atLeast(1)).exit(1);
	}
	
	/**
	 * Test failed to create output file.  Should log and exit.
	 * @throws IOException 
	 */
	@Test(groups = TestConstants.UNITTEST)
	public void testFailedToCreateOutputFile() throws IOException {		
		final LeagueRankingCalculator fixture = LrcRuntime.getInjector().getInstance(LeagueRankingCalculator.class);
				
		setupCommandLineResponse();
		setupInputFile(Arrays.asList(FILE_NOT_FOUND));
		setupOutputFile(RAISE_IO, Arrays.asList(FILE_FOUND));
		setupInputFileReadResults(RAISE_FILE_NOT_FOUND, DO_NOT_RAISE_IO);
		setupLeagueMatchRecorder();
		
		// 1. Exercise fixture.
		fixture.run(TEST_ARGUMENTS);
		
		// 2. Assert.
		Mockito.verify(mockLogger).logCouldNotCreateOutputFile(Mockito.any(), Mockito.any());
		Mockito.verify(mockSimpleSystemExit, Mockito.atLeast(1)).exit(1);
	}		
	
	/**
	 * @throws IOException 
	 * 
	 */
	private BufferedWriter setupLeagueMatchRecorder() throws IOException {
		BufferedWriter mockBufferedWriter = Mockito.mock(BufferedWriter.class);
		Mockito.when(mockSimpleBufferedWriterMaker.make(Mockito.any())).thenReturn(mockBufferedWriter);		
		Mockito.when(mockLeagueMatchRecorder.getLeagueResults()).thenReturn(MOCK_LEAGUE_RESULT);
		return mockBufferedWriter;
	}
	
	/**
	 * @param inputFileFound Can queue up results.  For example initially found then deleted.
	 */
	private File setupInputFile(Collection<Boolean> inputFileFound) {
		File mockInputFile = Mockito.mock(File.class);
		
		for (Boolean found : inputFileFound) {
			Mockito.when(mockInputFile.exists()).thenReturn(found);	
		}
		
		Mockito.when(mockInputFile.isDirectory()).thenReturn(false);
		Mockito.when(mockSimpleFileMaker.make(INPUT_FILE_NAME)).thenReturn(mockInputFile);
		return mockInputFile;
	}
	
	/**
	 * @param raiseIOException
	 * @param outputFileFound Can queue up results.  For example initially found then deleted.
	 * @throws IOException 
	 */
	private File setupOutputFile(boolean raiseIOException, Collection<Boolean> outputFileFound) throws IOException {
		File mockOutputFile = Mockito.mock(File.class);
		
		if (raiseIOException) {
			Mockito.when(mockOutputFile.createNewFile()).thenThrow(new IOException("MOCK_IO_EXCEPTION"));	
		} 
			
		Mockito.when(mockSimpleFileMaker.make(OUTPUT_FILE_NAME)).thenReturn(mockOutputFile);
		for (Boolean found : outputFileFound) {
			Mockito.when(mockOutputFile.exists()).thenReturn(found);	
		}

		Mockito.when(mockOutputFile.isDirectory()).thenReturn(false);
		return mockOutputFile;
	}
	
	/**
	 * 
	 */
	private void setupCommandLineResponse() {
		final CommandLine commandLine = Mockito.mock(CommandLine.class);
		Mockito.when(commandLine.getOptionValue(CommandLineConstants.getArgumentInput())).thenReturn(INPUT_FILE_NAME);
		Mockito.when(commandLine.getOptionValue(CommandLineConstants.getArgumentOutput())).thenReturn(OUTPUT_FILE_NAME);
		Mockito.when(mockStartupParameters.interpret(TEST_ARGUMENTS)).thenReturn(commandLine);		
	}
	
	/**
	 * @param raiseFileNotFoundException
	 * @param raiseIOException
	 * @throws IOException
	 */
	private void setupInputFileReadResults(boolean raiseFileNotFoundException, boolean raiseIOException) throws IOException {
		final BufferedReader mockBufferedReader = Mockito.mock(BufferedReader.class);
		if (raiseIOException) {
			Mockito.when(mockBufferedReader.readLine()).thenThrow(new IOException("MOCK_IO_EXCEPTION"));
		} else {
			Mockito.when(mockBufferedReader.readLine())
			.thenReturn("Lions 3, Snakes 3")
			.thenReturn("Tarantulas 1, FC Awesome 0")
			.thenReturn("Lions 1, FC Awesome 1")
			.thenReturn("Tarantulas 3, Snakes 1")
			.thenReturn("Lions 4, Grouches 0")
			.thenReturn(null); // End of file.	
		}
						
		if (raiseFileNotFoundException) {
			Mockito.when(mockSimpleBufferedReader.make(Mockito.any())).thenThrow(new FileNotFoundException("MOCK_FILE_NOT_FOUND"));
		} else {
			Mockito.when(mockSimpleBufferedReader.make(Mockito.any())).thenReturn(mockBufferedReader);
		}
		
	}
	
	/**
	 * Injection module that is specific to this unit.
	 */
	public static class LeagueRankingCalculatorTestModule implements Module {
		@Override
		public void configure(Binder binder) {
			binder.bind(SimpleLoggerMaker.class).toInstance(Mockito.mock(SimpleLoggerMaker.class));
			binder.bind(SimpleSystemExit.class).toInstance(Mockito.mock(SimpleSystemExit.class));
			binder.bind(SimpleBufferedReaderMaker.class).toInstance(Mockito.mock(SimpleBufferedReaderMaker.class));
			binder.bind(SimpleBufferedWriterMaker.class).toInstance(Mockito.mock(SimpleBufferedWriterMaker.class));
			binder.bind(SimpleFileMaker.class).toInstance(Mockito.mock(SimpleFileMaker.class));
			binder.bind(StartupParameters.class).toInstance(Mockito.mock(StartupParameters.class));
			binder.bind(LeagueMatchRecorder.class).toInstance(Mockito.mock(LeagueMatchRecorder.class));
			binder.bind(L.class).toInstance(Mockito.mock(L.class));
		}
	}
}
