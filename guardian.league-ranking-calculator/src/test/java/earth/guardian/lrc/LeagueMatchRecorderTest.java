package earth.guardian.lrc;

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

import earth.guardian.lrc.LeagueMatchRecorderTest.LeagueMatchRecorderTestModule;
import earth.guardian.lrc.utils.L;
import earth.guardian.lrc.utils.simple.SimpleLoggerMaker;

/**
 * 
 */
@Guice(modules = {LeagueMatchRecorderTestModule.class})
public class LeagueMatchRecorderTest {

	/**
	 * Injector that has been configured specifically with this units module. 
	 */
	@Inject public Injector injector;
	
	@Inject public SimpleLoggerMaker simpleLoggerMaker;
	
	/**
	 * Run before each test to setup mocks and any other required setup.
	 */
	@BeforeMethod(groups = TestConstants.UNITTEST_UTILS)
	public void init() {
		MockitoAnnotations.initMocks(this);
		LrcRuntime.setInjector(injector);
		Mockito.reset(simpleLoggerMaker);
	}
	
	/**
	 * Basic test to see if we can instantiate model.
	 */
	@Test(groups = TestConstants.UNITTEST_UTILS)
	public void testConstructor() {
		Assert.assertNotNull(LrcRuntime.getInjector().getInstance(LeagueMatchRecorder.class), "Failed to create League instance.");				
	}
	
	/**
	 * Basic test to see if we can instantiate model.
	 */
	@Test(groups = TestConstants.UNITTEST_MODELS)
	public void testBasicSeason() {
		final LeagueMatchRecorder fixture = LrcRuntime.getInjector().getInstance(LeagueMatchRecorder.class);
		fixture.recordMatchResult("Lions 3, Snakes 3");
		fixture.recordMatchResult("Tarantulas 1, FC Awesome 0");
		fixture.recordMatchResult("Lions 1, FC Awesome 1");
		fixture.recordMatchResult("Tarantulas 3, Snakes 1");
		fixture.recordMatchResult("Lions 4, Grouches 0");
		
		final StringBuilder expectedOutput = new StringBuilder();
		expectedOutput.append(String.format("%s %n", "1. Tarantulas, 6 pts")); 
		expectedOutput.append(String.format("%s %n", "2. Lions, 5 pts"));
		expectedOutput.append(String.format("%s %n", "3. FC Awesome, 1 pts")); 
		expectedOutput.append(String.format("%s %n", "4. Snakes, 1 pts")); 
		expectedOutput.append(String.format("%s %n", "5. Grouches, 0 pts")); 
					
		Assert.assertEquals(fixture.getLeagueResults(), expectedOutput.toString(), "Unexpected legue results");		
	}	
	
	/**
	 * Injection module that is specific to this unit.
	 */
	public static class LeagueMatchRecorderTestModule implements Module {
		@Override
		public void configure(Binder binder) {
			binder.bind(SimpleLoggerMaker.class).toInstance(Mockito.mock(SimpleLoggerMaker.class));
			binder.bind(L.class).toInstance(Mockito.mock(L.class));
		}
	}
}
