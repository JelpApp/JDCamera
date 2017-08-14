package io.jelp.demojdcamera;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import io.jelp.jdcamera.CameraActivity;
import io.jelp.jdcamera.CameraParams;
import io.jelp.jdcamera.tools.ImageUtility;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int REQUEST_CAMERA = 0;
    private static final int REQUEST_CAMERA2 = 2;
    private static final int REQUEST_CAMERA_PERMISSION = 1;
    private Point mSize;
    Button btnCamera1;
    Button btnCamera2;
    Integer CAMERA = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Display display = getWindowManager().getDefaultDisplay();
        mSize = new Point();
        display.getSize(mSize);
        btnCamera1 = (Button)findViewById(R.id.btnCamera1);
        btnCamera2 = (Button)findViewById(R.id.btnCamera2);
        btnCamera1.setOnClickListener(this);
        btnCamera2.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;

        if (requestCode == REQUEST_CAMERA) {
            Uri photoUri = data.getData();
            // Get the bitmap in according to the width of the device
            Bitmap bitmap = ImageUtility.decodeSampledBitmapFromPath(photoUri.getPath(), mSize.x, mSize.x);
            ((ImageView) findViewById(R.id.image)).setImageBitmap(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void requestForCameraPermission(View view) {
        final String permission = Manifest.permission.CAMERA;
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permission)) {
                showPermissionRationaleDialog("Test", permission);
            } else {
                requestForPermission(permission);
            }
        } else {
            launch();
        }
    }

    private void showPermissionRationaleDialog(final String message, final String permission) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.this.requestForPermission(permission);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create()
                .show();
    }

    private void requestForPermission(final String permission) {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, REQUEST_CAMERA_PERMISSION);
    }

    private void launch() {
        switch (CAMERA){
            case REQUEST_CAMERA:
                Intent startCustomCameraIntent = new Intent(this, CameraActivity.class);
                Bundle bundleSquare = new Bundle();
                bundleSquare.putInt(CameraParams.OPTION,CameraParams.SQUARE);
                bundleSquare.putString(CameraParams.MESSAGE,"Mensaje");
                bundleSquare.putBoolean(CameraParams.GALLERY,true);
                startCustomCameraIntent.putExtras(bundleSquare);
                startActivityForResult(startCustomCameraIntent, REQUEST_CAMERA);
                break;

            case REQUEST_CAMERA2:
                Intent startCustomCamera2Intent = new Intent(this, CameraActivity.class);
                Bundle bundleRectangle = new Bundle();
                bundleRectangle.putInt(CameraParams.OPTION,CameraParams.RECTANGLE);
                bundleRectangle.putString(CameraParams.MESSAGE,"Foto del vehículo");
                bundleRectangle.putString(CameraParams.PICTURE_NAME,"15_Front");
                bundleRectangle.putInt(CameraParams.MAX_WIDTH,720);
                bundleRectangle.putInt(CameraParams.QUALITY,80);
                startCustomCamera2Intent.putExtras(bundleRectangle);
                startActivityForResult(startCustomCamera2Intent, REQUEST_CAMERA);
                break;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA_PERMISSION:
                final int numOfRequest = grantResults.length;
                final boolean isGranted = numOfRequest == 1
                        && PackageManager.PERMISSION_GRANTED == grantResults[numOfRequest - 1];
                if (isGranted) {
                    launch();
                }
                break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnCamera1:
                CAMERA = REQUEST_CAMERA;
                requestForCameraPermission(view);
                break;

            case R.id.btnCamera2:
                CAMERA = REQUEST_CAMERA2;
                requestForCameraPermission(view);
                break;
        }
    }
}
