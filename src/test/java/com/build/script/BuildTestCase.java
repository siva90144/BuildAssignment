package com.build.script;

import org.testng.annotations.*;

import com.build.constants.BuildConstants;
import com.build.flow.BuildFlow;
import com.build.util.Util;
/**
 * This Class is used to execute the test case and verify the results
 * @author siva
 *
 */
public class BuildTestCase extends BuildFlow {
	@BeforeClass
	public void launchAppUrl(){
		launchURL();
	}
	
	@Test
	public void searchForProduct(){
		addSudheKohlerProduct();
		addCashmereKohlerProduct();
		
	}

	@AfterClass
	public void closePageWindow(){
		closeDriver();
	}
}
