package walmart.util.listener;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import walmart.labs.util.annotation.TestData;
import walmart.labs.util.dataprovider.TestDataProvider;
import walmart.util.listener.TestAnnotationListener;

public class TestAnnotationListener implements IAnnotationTransformer {

	/** Logger. */
	static Logger logger = LogManager.getLogger(TestAnnotationListener.class.getName());
	
	/** Constant TESTGROUPS: list of valid groups that test must use */
	private static final Set<String> TESTGROUPS = new HashSet<String>(Arrays.asList(new String[] {	"datamodel",
																									"LoginPage",
																									"AddCart",
																									"RemoveCart",
																									"AddandRemoveCart",
																									"Payment",
																									"Shipment",
																									"OrderConfirmation"
																									}));

	/* Adds data providers to all the tests
	 * @see org.testng.IAnnotationTransformer#transform(org.testng.annotations.ITestAnnotation, java.lang.Class, java.lang.reflect.Constructor, java.lang.reflect.Method)
	 */
	public void transform(ITestAnnotation anAnnotation, @SuppressWarnings("rawtypes") Class aClass, @SuppressWarnings("rawtypes") Constructor aConstructor, Method aMethod) {
		if (anAnnotation != null && anAnnotation instanceof ITestAnnotation){
			if(aMethod.getAnnotation(TestData.class) != null){
				anAnnotation.setDataProvider("getTestData");
				anAnnotation.setDataProviderClass(TestDataProvider.class);
			}
			String methodName = aMethod.getName();
			String[] groups = anAnnotation.getGroups();
			String[] updatedGroups = new String[groups.length + 1];
			for(int i = 0; i < groups.length; i++){
				// If a group found in @Test annotation does not exist in TESTGROUPS, log the group name and exit.
				if(TESTGROUPS.contains(groups[i]) == false) {
					logger.error("The group: " + groups[i] + " is not an approved group name. Please update the groups in " + methodName + ".");
					System.exit(1);
				}
				updatedGroups[i] = groups[i];
			}
			updatedGroups[groups.length] = "allTests";
			anAnnotation.setGroups(updatedGroups);
		}
	}	
}