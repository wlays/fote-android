import java.lang.reflect.Method;

import org.junit.runners.model.InitializationError;

import shadow.ShadowSherlockListActivity;
import android.app.Application;

import com.lays.fote.FoteApplication;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;

public class CustomTestRunner extends RobolectricTestRunner {

    public CustomTestRunner(Class<?> testClass) throws InitializationError {
	super(testClass);
	// addClassOrPackageToInstrument("com.actionbarsherlock.app.SherlockListActivity");
    }
    
    @Override public void beforeTest(Method method) {
	Robolectric.bindShadowClass(ShadowSherlockListActivity.class);
    }
    
    @Override protected Application createApplication() {
        return new FoteApplication();
    }
}