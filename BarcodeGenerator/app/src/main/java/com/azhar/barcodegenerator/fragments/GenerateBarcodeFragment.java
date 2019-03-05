package com.azhar.barcodegenerator.fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.azhar.barcodegenerator.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;

public class GenerateBarcodeFragment extends Fragment {

    private EditText mInputText;
    private ImageView mImageView;
    private FloatingActionButton mSave;
    private Activity mActivity;
    private Bitmap generatedBitmap;
    private String fileName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_generate_barcode, container, false);
        mActivity = getActivity();

        mInputText = view.findViewById(R.id.inputText);
        mImageView = view.findViewById(R.id.outputBitmap);
        mSave = view.findViewById(R.id.save);


        mInputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    mImageView.setImageResource(R.drawable.ic_placeholder);
                } else {
                    generateBarcode(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveImage(generatedBitmap);
            }
        });

        return view;
    }

    private void saveImage(Bitmap generatedBitmap) {
        FileOutputStream out = null;
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "QRCodeBarcode");
        if (!file.exists()) {
            file.mkdirs();
        }
        if (fileName.contains("/")) {
            fileName = fileName.replace("/", "\\");
        }
        String filePath = (file.getAbsolutePath() + "/" + fileName + ".png");
        try {
            out = new FileOutputStream(filePath);
            generatedBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Toast.makeText(mActivity, "File saved at\n" + filePath, Toast.LENGTH_SHORT).show();
    }

    private void generateBarcode(String s) {
        fileName = s;
        MultiFormatWriter writer = new MultiFormatWriter();
        String finalData = Uri.encode(s);

        // Use 1 as the height of the matrix as this is a 1D Barcode.
        BitMatrix bm = null;
        try {
            bm = writer.encode(finalData, BarcodeFormat.CODE_128, 1080, 1);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        int bmWidth = bm.getWidth();

        Bitmap imageBitmap = Bitmap.createBitmap(bmWidth, 640, Bitmap.Config.ARGB_8888);

        for (int i = 0; i < bmWidth; i++) {
            // Paint columns of width 1
            int[] column = new int[640];
            Arrays.fill(column, bm.get(i, 0) ? Color.BLACK : Color.WHITE);
            imageBitmap.setPixels(column, 0, 1, i, 0, 1, 640);
        }

        generatedBitmap = imageBitmap;

        mImageView.setImageBitmap(imageBitmap);
    }
}
