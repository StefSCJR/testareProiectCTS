package stef.testareProiectCTS;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestFacade.class, TestFactory.class, TestMemento.class, TestObserver.class })
public class AllTests {

}
