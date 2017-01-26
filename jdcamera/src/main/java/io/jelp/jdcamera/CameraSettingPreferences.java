package io.jelp.jdcamera;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Camera;
import android.support.annotation.NonNull;

/**
 * Created by angel on 1/20/17.
 */

public class CameraSettingPreferences {

    private static final String FLASH_MODE = "flash_mode";

    private static SharedPreferences getCameraSettingPreferences(@NonNull final Context context) {
        return context.getSharedPreferences("io.jelp.jdcamera", Context.MODE_PRIVATE);
    }

    public static void saveCameraFlashMode(@NonNull final Context context, @NonNull final String cameraFlashMode) {
        final SharedPreferences preferences = getCameraSettingPreferences(context);

        if (preferences != null) {
            final SharedPreferences.Editor editor = preferences.edit();
            editor.putString(FLASH_MODE, cameraFlashMode);
            editor.apply();
        }
    }

    public static String getCameraFlashMode(@NonNull final Context context) {
        final SharedPreferences preferences = getCameraSettingPreferences(context);

        if (preferences != null) {
            return preferences.getString(FLASH_MODE, Camera.Parameters.FLASH_MODE_AUTO);
        }

        return Camera.Parameters.FLASH_MODE_AUTO;
    }
}