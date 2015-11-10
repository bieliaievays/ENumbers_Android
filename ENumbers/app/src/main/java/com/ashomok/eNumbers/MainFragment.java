package com.ashomok.eNumbers;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.*;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.util.List;

/**
 * Created by Iuliia on 29.08.2015.
 */
public class MainFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int SPEECH_REQUEST_CODE = 0;
    private ENumberListAdapter scAdapter;
    private ImageButton voiceInputBtn;
    private ImageButton closeBtn;
    private EditText inputEditText;
    private ListView listView;
    private String startChar;
    private ENumbersSQLiteAssetHelper db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.start_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Prepare the loader
        db = new ENumbersSQLiteAssetHelper(getActivity());

        // create Loader for data reading
        getLoaderManager().initLoader(0, null, this);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        try {
            super.onActivityCreated(savedInstanceState);
            startChar = getString(R.string.startChar);
            inputEditText = (EditText) view.findViewById(R.id.inputE);
            inputEditText.setSelection(inputEditText.getText().length()); //starts type after "E"

            voiceInputBtn = (ImageButton) view.findViewById(R.id.ic_mic);

            closeBtn = (ImageButton) view.findViewById(R.id.ic_close);

//            searchBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
////                    try {
////                        TessBaseAPI baseApi = new TessBaseAPI();
////                    }
////                    catch (Exception e)
////                    {
////
////                    }
//
//                    if (v != null) {
//
//                        outputWarning.setText("");
//
//                        String inputing = inputEditText.getText().toString();
//
//                        if (inputing.length() >= 3) {
//
//                            GetInfoByENumber(inputing);
//
//                                //to hide the soft keyboard
//                                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
//                                        Context.INPUT_METHOD_SERVICE);
//                                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//
//                        } else if (inputing.contentEquals(startChar)) {
//                            //empty enter
//                            showAllData(v);
//                        } else {
//                            listView.setAdapter(null);
//                            outputWarning.setText(getActivity().getApplicationContext().getString(R.string.notFoundMessage));
//                        }
//                    }
//                }
//            });

            inputEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    if (charSequence.toString().startsWith(startChar)) {

                    } else {

                        inputEditText.setText(startChar);
                    }
                    inputEditText.setSelection(inputEditText.getText().length());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }

            });

            inputEditText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {

                    if (actionId == EditorInfo.IME_ACTION_DONE) {

                        GetInfoByENumber(textView.getText().toString());

                        //to hide the soft keyboard
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                                Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);

                        return true;
                    }

                    return false;
                }
            });

            voiceInputBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    displaySpeechRecognizer();
                }
            });

            closeBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    inputEditText.setText("");

                    showAllData(view);
                }
            });

            listView = (ListView) view.findViewById(R.id.ENumberList);

            TextView outputWarning = (TextView) view.findViewById(R.id.warning);
            listView.setEmptyView(outputWarning);

            listView.setAdapter(scAdapter);

        } catch (Exception e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    private void GetInfoByENumber(String inputing) {
        //TODO give checkings before
        Bundle b = new Bundle();
        b.putStringArray("codes", new String[]{inputing});
        try {

            getLoaderManager().restartLoader(0, b, this);

        } catch (Exception e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent data) {
        //voice inputing method
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == getActivity().RESULT_OK) {

            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);


            inputEditText.setText(
                    getActivity().getApplicationContext().getString(R.string.startChar));

            String spokenText = results.get(0);
            inputEditText.append(spokenText);

            GetInfoByENumber(inputEditText.getText().toString());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void displaySpeechRecognizer() {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        // Start the activity, the intent will be populated with the speech text
        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }

    private void showAllData(View view) {
        getLoaderManager().restartLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new ENCursorLoader(getActivity(), db, bundle);
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        try {
                scAdapter = new ENumberListAdapter(getActivity(), cursor, 0);

                listView.setAdapter(scAdapter);
                scAdapter.changeCursor(cursor);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {

                        Cursor cursor = (Cursor) parent.getAdapter().getItem(position);

                        EN enumb = new EN(cursor);

                        Intent intent = new Intent(getActivity(), ENDetailsActivity.class);

                        intent.putExtra("en", enumb);
                        startActivity(intent);
                    }
                });
        } catch (Exception e) {
            Log.e(this.getClass().getCanonicalName(), e.getMessage() + e.getStackTrace().toString());
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        scAdapter.changeCursor(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        db.close();
    }
}
