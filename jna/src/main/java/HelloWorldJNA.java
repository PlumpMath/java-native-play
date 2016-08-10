import com.sun.jna.Library;
import com.sun.jna.Native;

public class HelloWorldJNA {
    interface TestLibrary extends Library {
        // Must name the shared lib unless linking standard libraries
        TestLibrary INSTANCE = (TestLibrary) Native.loadLibrary("example", TestLibrary.class);

        // java String == C char[]
        void test(String str);
    }

    public static void main(String[] args) {
        TestLibrary.INSTANCE.test("Hello JNA");
        for (String arg : args) {
            TestLibrary.INSTANCE.test(arg);
        }
    }
}
