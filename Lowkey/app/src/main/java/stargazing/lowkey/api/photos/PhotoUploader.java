package stargazing.lowkey.api.photos;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import stargazing.lowkey.LowkeyApplication;


public class PhotoUploader implements IS3Uploader{
    private final String PHOTO_NAME_FILE = "photo";
    public final String PHOTOS_NAME_FOLDER = "photos" + File.separator;
    private static int fileID;
    private AmazonS3Client s3Client;
    private File fileTO;
    private Bitmap photo;

    public PhotoUploader() {
        BasicAWSCredentials credentials =
                new BasicAWSCredentials(
                        AwsAccessKeys.ACCESS_KEY_ID,
                        AwsAccessKeys.ACCESS_SECRET_KEY
                );
        s3Client = new AmazonS3Client(credentials);
    }

    public PhotoUploader(Bitmap photo) {
        this();
        this.photo = photo;
    }

    @Override
    public void upload(String path, final Callback successCallback, final Callback failCallback) {
        File file = LowkeyApplication.instance.getApplicationContext().getFilesDir();
        file = new File(file, PHOTO_NAME_FILE);


        BitMapOperator bitMapOperator = new BitMapOperator(photo);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitMapOperator.serialize());
            fos.close();

            TransferUtility transferUtility =
                    TransferUtility.builder()
                            .context(LowkeyApplication.instance.getApplicationContext())
                            .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                            .defaultBucket(BUCKET)
                            .s3Client(s3Client)
                            .build();

            TransferObserver uploadObserver =
                    transferUtility.upload(PHOTOS_NAME_FOLDER + path, file);

            uploadObserver.setTransferListener(new TransferListener() {
                @Override
                public void onStateChanged(int id, TransferState state) {
                    if (TransferState.COMPLETED == state) {
                        if(successCallback != null)
                            successCallback.handle();
                    }
                }
                @Override
                public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                }
                @Override
                public void onError(int id, Exception ex) {
                    Log.e("uploadError", ex.getMessage());
                    if(failCallback != null)
                        failCallback.handle();
                }
            });

        } catch (IOException e) {
            Log.e("photoUpload", e.getMessage());
        }
    }

    @Override
    public void download(String path,final Callback successCallback,
                         final Callback failCallback) {
        fileID++;
        final File file = new File(LowkeyApplication.instance.getApplicationContext().getFilesDir(), "photoffs"+fileID);
        Log.e("PHOTO STATUS",file.getPath());
        TransferUtility transferUtility =
                TransferUtility.builder()
                        .context(LowkeyApplication.instance.getApplicationContext())
                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                        .defaultBucket(BUCKET)
                        .s3Client(s3Client)
                        .build();

        TransferObserver downloadObserver =
                transferUtility.download(PHOTOS_NAME_FOLDER + path, file);

        downloadObserver.setTransferListener(new TransferListener() {

            @Override
            public void onStateChanged(int id, TransferState state) {
                if (TransferState.COMPLETED == state) {
                    try {
                        BufferedInputStream stream = new BufferedInputStream(new FileInputStream(file));

                        byte[] bytes = new byte[(int) file.length()];
                        stream.read(bytes, 0 , bytes.length);
                        stream.close();

                        photo = new BitMapOperator().unserialize(bytes);
                        fileTO = file;
                        if (successCallback != null)
                            successCallback.handle();
                    } catch (IOException e) {
                        Log.e("downloadPhoto",
                                "Couldn't read downloaded file: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
            }

            @Override
            public void onError(int id, Exception ex) {
                Log.e("downloadError", ex.getMessage());
                if(failCallback != null)
                    failCallback.handle();
            }
        });
    }

    public File getFileTO(){
        return fileTO;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public static class BitMapOperator {
        private Bitmap photo;

        public BitMapOperator(Bitmap photo) {
            this.photo = photo;
        }

        public BitMapOperator() {}

        public byte[] serialize() {
            if(photo == null)
                throw new RuntimeException("There is no image to serialize");

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, stream);

            return stream.toByteArray();
        }

        public String serializeToString() {
            return Base64.encodeToString(serialize(), Base64.DEFAULT);
        }

        public Bitmap unserialize(byte[] bytes) {
            if(bytes == null)
                throw new RuntimeException("No photo to unserialize");

            photo = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            return photo;
        }

        public Bitmap unserialize(String photo) {
            if(photo == null)
                throw new RuntimeException("No photo to unserialize");

            return this.unserialize(Base64.decode(photo, Base64.DEFAULT));
        }

        public Bitmap getPhoto() {
            return photo;
        }

        public void setPhoto(Bitmap photo) {
            this.photo = photo;
        }
    }
    public static void deleteFolder()
    {
        String folder = LowkeyApplication.instance.getApplicationContext().getFilesDir().getPath();
        recursiveDelete(new File(folder));
    }
    private static void recursiveDelete(File file){
        if(!file.exists())
            Log.e("Delete f/d Error : ","path doesn't exist");
        if(file.isDirectory()){
            for(File f : file.listFiles()){
                recursiveDelete(f);
            }
        }
        file.delete();
        Log.e("Deleted file or folder:",file.getAbsolutePath());
    }

}
