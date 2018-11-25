package stargazing.lowkey.api.photos;

import stargazing.lowkey.api.wrapper.OnSuccessHandler;

public interface IS3Uploader {
    String BUCKET = "lowkey-userfiles-mobilehub-1217601830";

    void upload(String path, Callback successCallback, Callback failCallback);
    void download(String path, Callback successCallback, Callback failCallback);
}

