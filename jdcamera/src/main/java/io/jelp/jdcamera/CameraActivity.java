package io.jelp.jdcamera;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import io.jelp.jdcamera.fragments.RectangleCameraFragment;
import io.jelp.jdcamera.fragments.SquareCameraFragment;

/**
 * Created by angel on 1/20/17.
 */

public class CameraActivity extends AppCompatActivity {

    public static final String TAG = CameraActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.CameraTheme);
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_camera);
        Integer option = getIntent().getExtras().getInt(CameraParams.OPTION);
        if (savedInstanceState == null&& option !=null) {
            switch (option)
            {
                case CameraParams.SQUARE:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, SquareCameraFragment.newInstance(getIntent().getExtras()), SquareCameraFragment.TAG)
                            .commit();
                     break;

                case CameraParams.RECTANGLE:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, RectangleCameraFragment.newInstance(getIntent().getExtras()), RectangleCameraFragment.TAG)
                            .commit();
                    break;
            }
        }
    }

    public void returnPhotoUri(Uri uri) {
        Intent data = new Intent();
        data.setData(uri);

        if (getParent() == null) {
            setResult(RESULT_OK, data);
        } else {
            getParent().setResult(RESULT_OK, data);
        }

        finish();
    }

    public void onCancel(View view) {
        getSupportFragmentManager().popBackStack();
    }
}