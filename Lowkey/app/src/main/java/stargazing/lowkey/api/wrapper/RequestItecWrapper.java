package stargazing.lowkey.api.wrapper;

public abstract class RequestItecWrapper extends RequestWrapper {
    private static final String ITEC_API_BASE_URL = "http://itec-api.deventure.co";

    public RequestItecWrapper(String tag) {
        super(ITEC_API_BASE_URL, tag);
    }
}
