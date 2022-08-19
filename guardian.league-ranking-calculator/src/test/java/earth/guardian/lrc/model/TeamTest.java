package earth.guardian.lrc.model;

import org.mockito.ArgumentCaptor;
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

import earth.guardian.lrc.LrcRuntime;
import earth.guardian.lrc.TestConstants;
import earth.guardian.lrc.model.TeamTest.TeamTestModule;
import earth.guardian.lrc.utils.L;
import earth.guardian.lrc.utils.SimpleLoggerMaker;

@Guice(modules = {TeamTestModule.class})
public class TeamTest {

	private static final String TEST_TEAM_NAME = "Lions";
	
	/**
	 * Mock SimpleLoggerMaker used by L. 
	 */
	@Inject public SimpleLoggerMaker simpleLoggerMaker;
	
	/**
	 * Injector that has been configured specifically with this units module. 
	 */
	@Inject public Injector injector;
	
	@Inject public L mockLogger;
	
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
	@Test(groups = TestConstants.UNITTEST_MODELS)
	public void testConstructor() {
		Assert.assertNotNull(new Team(TEST_TEAM_NAME), "Failed to create Team instance.");				
	}
	
	/**
	 * Test that match results are recorded.
	 */
	@Test(groups = TestConstants.UNITTEST_MODELS)
	public void testMatchRecording() {
		
		final Team fixture = new Team(TEST_TEAM_NAME);
		final ArgumentCaptor<String> teamNameCaptor = ArgumentCaptor.forClass(String.class);
		final ArgumentCaptor<Integer> matchScoreCaptor = ArgumentCaptor.forClass(Integer.class);
		final ArgumentCaptor<Long> seasonScoreCaptor = ArgumentCaptor.forClass(Long.class);
		
		// 1. Exercise fixture:
		fixture.recordMatchScore(10);
		fixture.recordMatchScore(5);
		
		Mockito.verify(mockLogger, Mockito.times(2)).logMatchResult(teamNameCaptor.capture(), matchScoreCaptor.capture(), seasonScoreCaptor.capture());

		// 2. Asserts
		Assert.assertEquals(fixture.getSeasonScore(), 15, "Match scores not correctly accumulated.");
		Assert.assertEquals(teamNameCaptor.getAllValues().get(0), TEST_TEAM_NAME, "Unexpected teams name logged");
		Assert.assertEquals(teamNameCaptor.getAllValues().get(1), TEST_TEAM_NAME, "Unexpected teams name logged");		
		Assert.assertEquals(matchScoreCaptor.getAllValues().get(0).intValue(), 10, "Unexpected match score logged");
		Assert.assertEquals(matchScoreCaptor.getAllValues().get(1).intValue(), 5, "Unexpected match score logged");		
		Assert.assertEquals(seasonScoreCaptor.getAllValues().get(0).intValue(), 10, "Unexpected season score logged");
		Assert.assertEquals(seasonScoreCaptor.getAllValues().get(1).intValue(), 15, "Unexpected season score logged");
	}
	
	/**
	 * Test that match season results are output as expected.
	 */
	@Test(groups = TestConstants.UNITTEST_MODELS)
	public void testOutputSeasonResult() {
		final Team fixture = new Team(TEST_TEAM_NAME);
		fixture.recordMatchScore(10);
		fixture.recordMatchScore(5);		
		Assert.assertEquals(fixture.toString(), "Lions, 15 pts", "Unexpected season result.");				
	}
	
	/**
	 * Injection module that is specific to this unit.
	 */
	public static class TeamTestModule implements Module {
		@Override
		public void configure(Binder binder) {
			binder.bind(SimpleLoggerMaker.class).toInstance(Mockito.mock(SimpleLoggerMaker.class));
			binder.bind(L.class).toInstance(Mockito.mock(L.class));
		}
	}
	
}
