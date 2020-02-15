package com.nibm.rwp.gms.utill;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nibm.rwp.gms.R;
import com.nibm.rwp.gms.activity.HomeActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;

public class AppUtill {
    // progress bar handling
//    public static Dialog showProgress(Activity activity) {
//        Dialog dialog = new Dialog(activity);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().setBackgroundDrawable(
//                new ColorDrawable(0));
//        dialog.setContentView(R.layout.fragment_dialog_progress);
//        ProgressBar progressBar = dialog.findViewById(R.id.progressBar);
//        progressBar.getIndeterminateDrawable().setColorFilter(activity.getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
//        dialog.setCancelable(false);
//        dialog.show();
//        return dialog;
//    }

//    // Show Dark dialog curtain
//    public static Dialog showCurtain(Activity activity) {
//        Dialog dialog = new Dialog(activity);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().setBackgroundDrawable(
//                new ColorDrawable(0));
//        dialog.setContentView(R.layout.fragment_dialog_curtain);
//        dialog.setCancelable(false);
//        dialog.show();
//        return dialog;
//    }

    // Round off to two decimal places
    public static double roundOff(double value) {
        DecimalFormat df = new DecimalFormat("###.##");
        return Double.parseDouble(df.format(value));
    }

    // get unique device ID (Android ID)
    public static String getDeviceId(Context mContext) {
        return Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    // land to settings
    public static void showSystemSettingsDialog(Context context) {
        context.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
    }

    // Check network connection availability
    public static boolean checkNetworkConnection(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        }

        NetworkInfo activeNetworks = connectivityManager.getActiveNetworkInfo();
        if (activeNetworks != null && activeNetworks.isConnected()) {
            return activeNetworks.isConnectedOrConnecting();
        }
        return false;
    }

    // hide keyboard
    public static void hideDefaultKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
        }
    }


    // start an activity without intentExtras
    public static void startActivity(Activity activity, Class<? extends Activity> aClass) {
        Intent intent = new Intent(activity, aClass);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    // start Activity with intentExtras
    public static void startActivityWithExtra(Activity activity, Intent intent) {
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    // Start Activity with intentExtras and request code
    public static void startActivityForResult(Activity activity, Intent intent, int status) {
        activity.startActivityForResult(intent, status);
        activity.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    // start an activity using drawer
    public static void startActivityFromDrawer(Activity activity, Class<? extends Activity> aClass) {
        if (!activity.getClass().getSimpleName().equals(HomeActivity.class.getSimpleName()))
            activity.finish();
        Intent intent = new Intent(activity, aClass);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    public static void showCustomConfirmAlert(final AlertDialog dialog, Activity activity, String title, String message,
                                              View.OnClickListener yesClickListener, View.OnClickListener noClickListener,
                                              String yesText, String noText, boolean cancelableState) {

        LayoutInflater inflater = activity.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_custom_confirm_alert, null);
        dialogView.setLayerType(View.LAYER_TYPE_SOFTWARE, null); // This line is for hardware acceleration false
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setView(dialogView);
        dialog.setCancelable(cancelableState);

        TextView tvTitle = dialogView.findViewById(R.id.custom_alert_tv_title);
        TextView tvMessage = dialogView.findViewById(R.id.custom_alert_tv_description);
        Button btnCancel = dialogView.findViewById(R.id.custom_alert_btn_cancel);
        Button btnSettings = dialogView.findViewById(R.id.custom_alert_btn_settings);
        btnCancel.setOnClickListener(noClickListener);
        btnSettings.setOnClickListener(yesClickListener);

        if (noClickListener == null)
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

        if (yesClickListener == null)
            btnSettings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

        if (title.isEmpty()) {
            tvTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setText(title);
        }

        tvMessage.setText(message);
        btnCancel.setText(noText);
        btnSettings.setText(yesText);

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        Log.i("Screen Width: ", "" + width);

        dialog.show();
        dialog.getWindow().setLayout(width - 80, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    // custom alert dialog box (Updated UI)
    public static void showCustomStandardAlert(final AlertDialog dialog, Activity activity, String title, String message, Drawable imageIconResource,
                                               View.OnClickListener buttonClickListener, String buttonText, boolean cancelableState) {

        LayoutInflater inflater = activity.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_custom_standard_alert, null);
        dialogView.setLayerType(View.LAYER_TYPE_SOFTWARE, null); // This line is for hardware acceleration false
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setView(dialogView);
        dialog.setCancelable(cancelableState);

        TextView tvTitle = dialogView.findViewById(R.id.custom_alert_tv_title);
        TextView tvMessage = dialogView.findViewById(R.id.custom_alert_tv_description);
        ImageView ivIcon = dialogView.findViewById(R.id.custom_alert_iv_icon);
        ivIcon.setBackground(imageIconResource);
        Button btnCancel = dialogView.findViewById(R.id.custom_alert_btn_text);
        btnCancel.setOnClickListener(buttonClickListener);

        if (buttonClickListener == null)
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        else {
            btnCancel.setOnClickListener(buttonClickListener);
        }

        if (title.isEmpty()) {
            tvTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setText(title);
        }

        tvMessage.setText(message);
        btnCancel.setText(buttonText);

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        Log.i("Screen Width: ", "" + width);

        dialog.show();
        dialog.getWindow().setLayout(width - 80, WindowManager.LayoutParams.WRAP_CONTENT);

    }


    public static void openPlayStore(Context context, String appPackageName) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    public static boolean checkApplicationExists(Context context, String appPackageName) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(appPackageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getToday() {
        // format date as -> 16/11/2016 (Date/Month/Year)
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String formatDate(Long date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date);
    }

    public static String formatTime(Long date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        return dateFormat.format(date);
    }

    public static String formatDisplayDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date myDate = null;
        try {
            myDate = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DateFormat dateFormat2 = new SimpleDateFormat("EEE, d MMM yyyy");
        return dateFormat2.format(myDate);
    }

    //
    public static String startDateOfWeek() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(cal.getTime());
    }

    public static String endDateOfWeek() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        cal.add(Calendar.DATE, 6);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(cal.getTime());
    }

    public static Long getDateInMilliSeconds(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date mDate = sdf.parse(dateString);
            return mDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Long.valueOf("0.00");
    }

    // Split the string using splitUsing character and send the formatted string
    public static String getFormattedString(String fullStringText, String splitUsing) {
        try {
            String[] splitString = fullStringText.split(splitUsing);
            String formattedString = "";
            for (int i = 0; i < splitString.length; i++) {
                formattedString = formattedString + splitString[i] + "\n";
            }
            return formattedString;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    // Read arrays safely
    public static <T> List<T> safeClient(List<T> other) {
        return other == null ? Collections.EMPTY_LIST : other;
    }

    /*
     * Below method used to remove the folder and folder contains data(Ex: PDF, Images, Etc:)
     * */
    // Remove directory (Folder)
    public static boolean removeDirectory(File directory) {

        if (directory == null)
            return false;
        if (!directory.exists())
            return true;
        if (!directory.isDirectory())
            return false;

        String[] list = directory.list();

        if (list != null) {
            for (int i = 0; i < list.length; i++) {
                File entry = new File(directory, list[i]);

                if (entry.isDirectory()) {
                    if (!removeDirectory(entry))
                        return false;
                } else {
                    if (!entry.delete())
                        return false;
                }
            }
        }

        return directory.delete();
    }

    /*
     * We can use following method to create base64 String using a file (.pdf , .docx Etc:)
     * */
    public static String convertFileToByteArray(File f) {
        byte[] byteArray = null;
        try {
            InputStream inputStream = new FileInputStream(f);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024 * 11];
            int bytesRead = 0;

            while ((bytesRead = inputStream.read(b)) != -1) {
                bos.write(b, 0, bytesRead);
            }

            byteArray = bos.toByteArray();

            Log.e("Byte array", ">" + byteArray);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64.encodeToString(byteArray, Base64.NO_WRAP);
    }

    /*
     * We can use following method to create a file using base64 String
     * */
    public static File createFileUsingString(String stringFile) {
        // Currently we create the File inside this method

        /*File filesDir = new File(Environment.getExternalStorageDirectory(), "M.SMS/" + mSvoItem.getSvoName());

        // Create the storage directory if it does not exist
        if (!filesDir.exists()) {
            if (!filesDir.mkdirs()) {
                Log.e(TAG, "Failed to create directory");
            }
        }
        File downloadedFile = new File(filesDir, mSvoItem.getSvoName() + "_Created_by_Anupa" + ".pdf");*/
        try {
            File filesDir = new File(Environment.getExternalStorageDirectory(), "CreatedFile/");
            // Create the storage directory if it does not exist
            if (!filesDir.exists()) {
                if (!filesDir.mkdirs()) {
                    Log.e(TAG, "Failed to create directory");
                }
            }
            File createdFile = new File(filesDir, "Created_by_Anupa_" + System.currentTimeMillis() + ".pdf"); // File extension can change
            byte[] pdfAsBytes = Base64.decode(stringFile, 0);
            FileOutputStream os;
            os = new FileOutputStream(createdFile, false);
            os.write(pdfAsBytes);
            os.flush();
            os.close();

            return createdFile;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


}
