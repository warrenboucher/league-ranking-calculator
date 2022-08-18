package earth.guardian.lrc.utils;

import org.apache.logging.log4j.Logger;
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
import earth.guardian.lrc.Main;
import earth.guardian.lrc.TestConstants;
import earth.guardian.lrc.utils.LTest.LTestModule;

/**
 * Unit tests for {@link L}
 */
@Guice(modules = {LTestModule.class})
public class LTest {
	
	/**
	 * Mock SimpleLoggerMaker used by L. 
	 */
	@Inject public SimpleLoggerMaker simpleLoggerMaker;
	
	/**
	 * Injector that has been configured specifically with this units module. 
	 */
	@Inject public Injector injector;
	
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
	 * Basic test to see that <code>L.getLogger()</code> returns a logger.
	 */
	@Test(groups = TestConstants.UNITTEST_UTILS)
	public void testBasicGetLogger() {
		
		@SuppressWarnings("rawtypes")
		final ArgumentCaptor<Class> classArgumentCaptor = ArgumentCaptor.forClass(Class.class);
		
		// Setup test.
		Mockito.when(simpleLoggerMaker.make(Mockito.any())).thenReturn(Mockito.mock(Logger.class));
		
		// 1. Exercise fixture:
		final Logger fixture = L.getLogger();

		// 2. Asserts
		Assert.assertNotNull(fixture, "L did not return a logger");
		
		Mockito.verify(simpleLoggerMaker).make(classArgumentCaptor.capture());
		Assert.assertNotNull(classArgumentCaptor.getValue(), "L did not pass a class to simpleLoggerMaker");
		Assert.assertEquals(classArgumentCaptor.getValue(), getClass(), "L passed wrong class to simpleLoggerMaker");				
	}
	
	/**
	 * Basic test to see that <code>L.getLogger(class)</code> returns a logger.
	 */
	@Test(groups = TestConstants.UNITTEST_UTILS)
	public void testClassProvidedGetLogger() {
		
		@SuppressWarnings("rawtypes")
		final ArgumentCaptor<Class> classArgumentCaptor = ArgumentCaptor.forClass(Class.class);
		
		// Setup test.
		Mockito.when(simpleLoggerMaker.make(Mockito.any())).thenReturn(Mockito.mock(Logger.class));
		
		// 1. Exercise fixture:
		final Logger fixture = L.getLogger(Main.class);

		// 2. Asserts
		Assert.assertNotNull(fixture, "L did not return a logger");
		
		Mockito.verify(simpleLoggerMaker).make(classArgumentCaptor.capture());
		Assert.assertNotNull(classArgumentCaptor.getValue(), "L did not pass a class to simpleLoggerMaker");
		Assert.assertEquals(classArgumentCaptor.getValue(), Main.class, "L passed wrong class to simpleLoggerMaker");				
	}	
	
	/**
	 * Injection module that is specific to this unit.
	 */
	public static class LTestModule implements Module {
		@Override
		public void configure(Binder binder) {
			binder.bind(SimpleLoggerMaker.class).toInstance(Mockito.mock(SimpleLoggerMaker.class));
		}
	}
}
