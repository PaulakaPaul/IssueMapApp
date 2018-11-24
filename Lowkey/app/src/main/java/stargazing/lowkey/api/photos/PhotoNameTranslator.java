package stargazing.lowkey.api.photos;

public class PhotoNameTranslator {
    public static String getPhotoNameFromEmail(String email) {
        return email.replace("@", "").replace(".", "");
    }
}
