package frontend;

import backend.TestClassBackend;

/**
 * Created by robert.ruja on 07-Apr-17.
 */
public class TestClassFrontend {

    public static void main(String ... args){
        TestClassBackend backend = new TestClassBackend();
        backend.callBackend();
    }
}
