
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.lays.fote.activities.MainActivity;

@RunWith(CustomTestRunner.class)
public class MainActivityTest {

    @Before
    public void initialize() {
        MainActivity activity = new MainActivity();
        activity.onCreate(null);
    }
    
    @Test
    public void shouldHaveHappySmiles() throws Exception {
        assertThat("false", equalTo("Hello World, MyActivity!"));
    }
}