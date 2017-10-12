package com.example.kikaaarias.basicaffdex;

import android.app.Activity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.affectiva.android.affdex.sdk.Frame;
import com.affectiva.android.affdex.sdk.detector.CameraDetector;
import com.affectiva.android.affdex.sdk.detector.Detector;
import com.affectiva.android.affdex.sdk.detector.Face;

import java.util.List;

/**
 * This is a very bare sample app to demonstrate the usage of the CameraDetector object from Affectiva.
 * It displays statistics on frames per second, percentage of time a face was detected, and the user's  score.
 *
 * The app shows off the maneuverability of the SDK by allowing the user to start and stop the SDK and also hide the camera SurfaceView.
 *
 * For use with SDK 2.02
 */
public class MainActivity extends Activity implements Detector.ImageListener, CameraDetector.CameraEventListener {

    final String LOG_TAG = "CameraDetectorDemo";


    SurfaceView cameraPreview;
    RelativeLayout mainLayout;
    TextView smileTextView;
    TextView lippressTextView;
    TextView lippuckerTextView;
    TextView lipstretchTextView;
    TextView lipsuckTextView;
    TextView mouthopenTextView;
    TextView nosewrinkleTextView;
    TextView smirkTextView;
    TextView upperlipraiseTextView;
    TextView chinraiseTextView;
    TextView dimplerTextView;
    TextView eyeclosureTextView;
    TextView eyewidenTextView;
    TextView innerbrowraiseTextView;
    TextView jawdropTextView;
    TextView lidtightenTextView;
    TextView lipcornerdepressorTextView;
    TextView browfurrowTextView;
    TextView browraiseTextView;
    TextView cheekraiseTextView;
    TextView attentionTextView;
    TextView ageTextView;
    TextView ethnicityTextView;
    CameraDetector detector;

    int previewWidth = 0;
    int previewHeight = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        smileTextView = findViewById(R.id.smile_textview);
        lippressTextView = findViewById(R.id.lippress_textview);
        lippuckerTextView = findViewById(R.id.lippucker_textview);
        lipstretchTextView = findViewById(R.id.lipstretch_textview);
        lipsuckTextView = findViewById(R.id.lipsuck_textview);
        mouthopenTextView = findViewById(R.id.mouthopen_textview);
        nosewrinkleTextView = findViewById(R.id.nosewrinkle_textview);
        smirkTextView = findViewById(R.id.smirk_textview);
        upperlipraiseTextView = findViewById(R.id.upperlipraise_textview);
        chinraiseTextView = findViewById(R.id.chinraise_textview);
        dimplerTextView = findViewById(R.id.dimpler_textview);
        eyeclosureTextView = findViewById(R.id.eyeclosure_textview);
        eyewidenTextView = findViewById(R.id.eyewiden_textview);
        innerbrowraiseTextView = findViewById(R.id.innerbrowraise_textview);
        jawdropTextView = findViewById(R.id.jawdrop_textview);
        lidtightenTextView = findViewById(R.id.lidtighten_textview);
        lipcornerdepressorTextView = findViewById(R.id.lipcornerdepressor_textview);
        browfurrowTextView = findViewById(R.id.browfurrow_textview);
        browraiseTextView = findViewById(R.id.browraise_textview);
        cheekraiseTextView = findViewById(R.id.cheekraise_textview);
        attentionTextView = findViewById(R.id.attention_textview);
        ageTextView = findViewById(R.id.age_textview);
        ethnicityTextView = findViewById(R.id.ethnicity_textview);

        //We create a custom SurfaceView that resizes itself to match the aspect ratio of the incoming camera frames
        mainLayout = (RelativeLayout) findViewById(R.id.main_layout);
        cameraPreview = new SurfaceView(this) {
            @Override
            public void onMeasure(int widthSpec, int heightSpec) {
                int measureWidth = MeasureSpec.getSize(widthSpec);
                int measureHeight = MeasureSpec.getSize(heightSpec);
                int width;
                int height;
                if (previewHeight == 0 || previewWidth == 0) {
                    width = measureWidth;
                    height = measureHeight;
                } else {
                    float viewAspectRatio = (float)measureWidth/measureHeight;
                    float cameraPreviewAspectRatio = (float) previewWidth/previewHeight;

                    if (cameraPreviewAspectRatio > viewAspectRatio) {
                        width = measureWidth;
                        height =(int) (measureWidth / cameraPreviewAspectRatio);
                    } else {
                        width = (int) (measureHeight * cameraPreviewAspectRatio);
                        height = measureHeight;
                    }
                }
                setMeasuredDimension(width,height);
            }
        };
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT,RelativeLayout.TRUE);
        cameraPreview.setLayoutParams(params);
        mainLayout.addView(cameraPreview,0);


        detector = new CameraDetector(this, CameraDetector.CameraType.CAMERA_FRONT, cameraPreview);
        detector.setDetectSmile(true);
        detector.setDetectLipPress(true);
        detector.setDetectLipPucker(true);
        detector.setDetectLipStretch(true);
        detector.setDetectLipSuck(true);
        detector.setDetectMouthOpen(true);
        detector.setDetectNoseWrinkle(true);
        detector.setDetectSmirk(true);
        detector.setDetectUpperLipRaise(true);
        detector.setDetectChinRaise(true);
        detector.setDetectDimpler(true);
        detector.setDetectEyeClosure(true);
        detector.setDetectEyeWiden(true);
        detector.setDetectInnerBrowRaise(true);
        detector.setDetectJawDrop(true);
        detector.setDetectLidTighten(true);
        detector.setDetectLipCornerDepressor(true);
        detector.setDetectBrowFurrow(true);
        detector.setDetectBrowRaise(true);
        detector.setDetectCheekRaise(true);
        detector.setDetectAttention(true);
        detector.setDetectAge(true);
        detector.setDetectEthnicity(true);
        detector.setImageListener(this);
        detector.setOnCameraEventListener(this);
        detector.start();

    }

    @Override
    public void onImageResults(List<Face> list, Frame frame, float v) {
        if (list == null)
            return;
        if (list.size() == 0) {
            smileTextView.setText("NO FACE");
            lippressTextView.setText("");
            lippuckerTextView.setText("");
            lipstretchTextView.setText("");
            lipsuckTextView.setText("");
            mouthopenTextView.setText("");
            nosewrinkleTextView.setText("");
            smirkTextView.setText("");
            upperlipraiseTextView.setText("");
            chinraiseTextView.setText("");
            dimplerTextView.setText("");
            eyeclosureTextView.setText("");
            eyewidenTextView.setText("");
            innerbrowraiseTextView.setText("");
            jawdropTextView.setText("");
            lidtightenTextView.setText("");
            lipcornerdepressorTextView.setText("");
            browfurrowTextView.setText("");
            browraiseTextView.setText("");
            cheekraiseTextView.setText("");
            attentionTextView.setText("");
            ageTextView.setText("");
            ethnicityTextView.setText("");
        } else {
            Face face = list.get(0);
            smileTextView.setText(String.format("SMILE: %.2f",face.expressions.getSmile()));
            lippressTextView.setText(String.format("LIP_PRESS: %.2f",face.expressions.getLipPress()));
            lippuckerTextView.setText(String.format("LIP_PUCKER: %.2f",face.expressions.getLipPucker()));
            lipstretchTextView.setText(String.format("LIP_STRETCH: %.2f",face.expressions.getLipStretch()));
            lipsuckTextView.setText(String.format("LIP_SUCK: %.2f",face.expressions.getLipSuck()));
            mouthopenTextView.setText(String.format("MOUTH_OPEN: %.2f",face.expressions.getMouthOpen()));
            nosewrinkleTextView.setText(String.format("NOSE_WRINKLE: %.2f",face.expressions.getNoseWrinkle()));
            smirkTextView.setText(String.format("SMIRK: %.2f",face.expressions.getSmirk()));
            upperlipraiseTextView.setText(String.format("UPPER_LIP_RAISE: %.2f",face.expressions.getUpperLipRaise()));

            chinraiseTextView.setText(String.format("CHIN_RAISE: %.2f",face.expressions.getChinRaise()));
            dimplerTextView.setText(String.format("DIMPLER: %.2f",face.expressions.getDimpler()));
            eyeclosureTextView.setText(String.format("EYE_CLOSURE: %.2f",face.expressions.getEyeClosure()));
            eyewidenTextView.setText(String.format("EYE_WIDEN: %.2f",face.expressions.getEyeWiden()));
            innerbrowraiseTextView.setText(String.format("INNER_BROW_RAISE: %.2f",face.expressions.getInnerBrowRaise()));
            jawdropTextView.setText(String.format("JAW_DROP: %.2f",face.expressions.getJawDrop()));
            lidtightenTextView.setText(String.format("LID_TIGHTEN: %.2f",face.expressions.getLidTighten()));
            lipcornerdepressorTextView.setText(String.format("LIP_CORNER_DEPRESSOR: %.2f",face.expressions.getLipCornerDepressor()));
            browfurrowTextView.setText(String.format("BROW_FURROW: %.2f",face.expressions.getBrowFurrow()));
            browraiseTextView.setText(String.format("BROW_RAISE: %.2f",face.expressions.getBrowRaise()));
            cheekraiseTextView.setText(String.format("CHEEK_RAISE: %.2f",face.expressions.getCheekRaise()));
            attentionTextView.setText(String.format("ATTENTION: %.2f",face.expressions.getAttention()));
            switch (face.appearance.getAge()) {
                case AGE_UNKNOWN:
                    ageTextView.setText("");
                    break;
                case AGE_UNDER_18:
                    ageTextView.setText(R.string.age_under_18);
                    break;
                case AGE_18_24:
                    ageTextView.setText(R.string.age_18_24);
                    break;
                case AGE_25_34:
                    ageTextView.setText(R.string.age_25_34);
                    break;
                case AGE_35_44:
                    ageTextView.setText(R.string.age_35_44);
                    break;
                case AGE_45_54:
                    ageTextView.setText(R.string.age_45_54);
                    break;
                case AGE_55_64:
                    ageTextView.setText(R.string.age_55_64);
                    break;
                case AGE_65_PLUS:
                    ageTextView.setText(R.string.age_over_64);
                    break;
            }

            switch (face.appearance.getEthnicity()) {
                case UNKNOWN:
                    ethnicityTextView.setText("");
                    break;
                case CAUCASIAN:
                    ethnicityTextView.setText(R.string.ethnicity_caucasian);
                    break;
                case BLACK_AFRICAN:
                    ethnicityTextView.setText(R.string.ethnicity_black_african);
                    break;
                case EAST_ASIAN:
                    ethnicityTextView.setText(R.string.ethnicity_east_asian);
                    break;
                case SOUTH_ASIAN:
                    ethnicityTextView.setText(R.string.ethnicity_south_asian);
                    break;
                case HISPANIC:
                    ethnicityTextView.setText(R.string.ethnicity_hispanic);
                    break;
            }

        }
    }

    @SuppressWarnings("SuspiciousNameCombination")
    @Override
    public void onCameraSizeSelected(int width, int height, Frame.ROTATE rotate) {
        if (rotate == Frame.ROTATE.BY_90_CCW || rotate == Frame.ROTATE.BY_90_CW) {
            previewWidth = height;
            previewHeight = width;
        } else {
            previewHeight = height;
            previewWidth = width;
        }
        cameraPreview.requestLayout();
    }
}