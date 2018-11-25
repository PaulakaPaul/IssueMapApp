package stargazing.lowkey.main.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import stargazing.lowkey.LowkeyApplication;
import stargazing.lowkey.R;
import stargazing.lowkey.api.photos.Callback;
import stargazing.lowkey.api.photos.PhotoUploader;
import stargazing.lowkey.api.photos.PhotoUtils;
import stargazing.lowkey.api.photos.ProfilePhotoUploader;
import stargazing.lowkey.api.wrapper.OnSuccessHandler;
import stargazing.lowkey.api.wrapper.RequestWrapper;
import stargazing.lowkey.auth.EntryActivity;
import stargazing.lowkey.managers.UserManager;
import stargazing.lowkey.models.UpdateUserModel;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    private final int GALLERY_REQUEST = 1;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Bitmap profilePhoto;

    private View rootView;
    private CircleImageView profileImageView;

    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView username = rootView.findViewById(R.id.username);
        username.setText(LowkeyApplication.currentUserManager.getUserModel().getFullName());
        TextView UID = rootView.findViewById(R.id.textView15);
        UID.setText(LowkeyApplication.currentUserManager.getUserModel().getId().toString());

        profileImageView = rootView.findViewById(R.id.profile);
        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askForImage();
            }
        });

        Button button = rootView.findViewById(R.id.next7);
        Button log_out = rootView.findViewById(R.id.next6);
        if(getActivity().getIntent().getStringExtra("Status")!=null)
        if(getActivity().getIntent().getStringExtra("Status").equals("offline")) {
            username.setText("You are offline");
            button.setVisibility(View.INVISIBLE);
            UID.setText("Log in to have a full application experience !");
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog();
            }
        });
        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LowkeyApplication.instance.logout();
                Intent intent = new Intent(getContext(), EntryActivity.class);
                getActivity().overridePendingTransition(0, 0);

                startActivity(intent);
                getActivity().overridePendingTransition(0, 0);
            }
        });

        final ProfilePhotoUploader photoUploader = new ProfilePhotoUploader();
        photoUploader.download(LowkeyApplication.currentUserManager.getProfilePictureName(),
                new Callback() {
                    @Override
                    public void handle() {
                        profilePhoto = photoUploader.getPhoto();
                        profileImageView.setImageBitmap(profilePhoto);
                    }
                }, null);

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case GALLERY_REQUEST:
                    Uri selectedImage = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), selectedImage);
                        // Resize image before saving it.
                        bitmap = PhotoUtils.resizeBitmap(bitmap);

                        profilePhoto = bitmap;
                        profileImageView.setImageBitmap(profilePhoto);
                        PhotoUploader photoUploader = new PhotoUploader(profilePhoto);
                        photoUploader.upload(LowkeyApplication.currentUserManager.getProfilePictureName(), new Callback() {
                            @Override
                            public void handle() {
                                Toast.makeText(getContext(), "Profile picture updated", Toast.LENGTH_LONG).show();
                            }
                        }, new Callback() {
                            @Override
                            public void handle() {
                                Toast.makeText(getContext(), "Profile picture could not be updated", Toast.LENGTH_LONG).show();
                            }
                        });
                    } catch (IOException e) {
                        Log.i("GalleryRequest", e.getMessage());
                    }
                    break;
            }
    }

    private void showEditDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        final EditText fullname = new EditText(getContext());
        final EditText age = new EditText(getContext());

        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);

        // Add a TextView here for the "Title" label, as noted in the comments
        fullname.setHint("Title");
        fullname.setText(LowkeyApplication.currentUserManager.getUserModel().getFullName());
        layout.addView(fullname); // Notice this is an add method

        age.setHint("Age");
        age.setText(LowkeyApplication.currentUserManager.getUserModel().getAge() + "");
        layout.addView(age); // Another add method

        alert.setView(layout);

        alert.setPositiveButton("Yes Option", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                final int ageParsed = Integer.parseInt(age.getText().toString());
                final String fullName = fullname.getText().toString();

                UserManager userManager = new UserManager();
                UpdateUserModel updateUserModel = new UpdateUserModel(
                        LowkeyApplication.currentUserManager.getUserModel().getId(),
                        fullName,
                        ageParsed,
                        13,
                        0,
                        0,
                        LowkeyApplication.currentUserManager.getUserModel().getGender(),""
                );

                userManager.updateUser(updateUserModel, new OnSuccessHandler() {
                    @Override
                    public void handle(JSONObject response) {
                        if(!response.equals(RequestWrapper.FAIL_JSON_RESPONSE_VALUE)){
                            Toast.makeText(getContext(),"Profile updated",Toast.LENGTH_LONG).show();
                            LowkeyApplication.currentUserManager.getUserModel().setAge(ageParsed);
                            LowkeyApplication.currentUserManager.getUserModel().setFullName(fullName);
                        }
                    }
                });

            }
        });

        alert.setNegativeButton("No Option", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });

        alert.show();
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void askForImage() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
    }
}
