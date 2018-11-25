package stargazing.lowkey.api.photos;

import android.graphics.Bitmap;

import java.io.File;

public class IssuePhotoUploader extends PhotoUploader {
    private final String ISSUES_NAME_FOLDER = "issues" + File.separator;

    public IssuePhotoUploader(Bitmap photo) {
        super(photo);
    }

    public IssuePhotoUploader() {}

    @Override
    public void upload(String fileName, Callback successCallback, Callback failCallback) {
        String path = ISSUES_NAME_FOLDER + fileName;
        super.upload(path, successCallback, failCallback);
    }

    @Override
    public void download(String fileName, Callback successCallback, Callback failCallback) {
        String path = ISSUES_NAME_FOLDER + fileName;
        super.download(path, successCallback, failCallback);
    }
}
