package com.edbrix.enterprise.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.edbrix.enterprise.Application;
import com.edbrix.enterprise.BuildConfig;
import com.edbrix.enterprise.MainActivity;
import com.edbrix.enterprise.Models.ResponseData;
import com.edbrix.enterprise.R;
import com.edbrix.enterprise.Utils.Conditions;
import com.edbrix.enterprise.Utils.Constants;
import com.edbrix.enterprise.Volley.GsonRequest;
import com.edbrix.enterprise.Volley.SettingsMy;
import com.edbrix.enterprise.baseclass.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import timber.log.Timber;

public class ForgotPasswordActivity extends BaseActivity {

    Context context;

    RelativeLayout layout;
    TextInputEditText _forgot_password_edit_text_email;
    Button _forgot_password_button_submit;
    ProgressBar _forgot_password_progress_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        context = this;

        _forgot_password_edit_text_email = findViewById(R.id.forgot_password_edit_text_email);
        _forgot_password_button_submit = findViewById(R.id.forgot_password_button_submit);
        _forgot_password_progress_bar = findViewById(R.id.forgot_password_progress_bar);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        _forgot_password_button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValidations();
            }
        });

        _forgot_password_edit_text_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!charSequence.toString().isEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(charSequence.toString().trim()).matches()) {
                    _forgot_password_button_submit.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
                } else {
                    _forgot_password_button_submit.setBackgroundColor(context.getResources().getColor(R.color.colorDisableBtn));
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void checkValidations() {

        Conditions.hideKeyboard(ForgotPasswordActivity.this);

        String email = _forgot_password_edit_text_email.getText().toString().trim();

        if (email.isEmpty()) {
            _forgot_password_edit_text_email.setError(getString(R.string.error_edit_text));
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _forgot_password_edit_text_email.setError(getString(R.string.error_email_not_valid));
        } else {
            _forgot_password_edit_text_email.setError(null);

            if (Conditions.isNetworkConnected(ForgotPasswordActivity.this)) {
                forgotPassword(email);
            } else {
                try {
                    Snackbar.make(layout, getString(R.string.error_network), Snackbar.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(ForgotPasswordActivity.this, getString(R.string.error_network), Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    private void forgotPassword(final String emailId) {

        _forgot_password_progress_bar.setVisibility(View.VISIBLE);

        JSONObject jo = new JSONObject();
        try {

            jo.put("Email", emailId);

        } catch (JSONException e) {
            Timber.e(e, "Parse logInWithEmail exception");
            return;
        }
        if (BuildConfig.DEBUG) Timber.d("Forgot user: %s", jo.toString());

        GsonRequest<ResponseData> userForgotPasswordRequest = new GsonRequest<>(Request.Method.POST, Constants.forgotPassword, jo.toString(), ResponseData.class,
                new Response.Listener<ResponseData>() {
                    @Override
                    public void onResponse(@NonNull ResponseData response) {
                        _forgot_password_progress_bar.setVisibility(View.INVISIBLE);
                        Timber.d("response: %s", response.toString());
                        if (response.getErrorCode() == null) {

                            if (response.getIsOrganizationListShow().equals("0")) {
                                Toast.makeText(context, "Success, Please login with new password ", Toast.LENGTH_SHORT).show();
                                SettingsMy.setActiveUser(null);

                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            } else {
                                Intent intent = new Intent(getApplicationContext(), OrganizationListActivity.class);
                                intent.putExtra("key", true);
                                intent.putExtra("email", emailId);
                                startActivity(intent);

                            }

                        } else {

                            try {
                                Timber.d("Error: %s", response.getErrorMessage());
                                Snackbar.make(layout, response.getErrorMessage(), Snackbar.LENGTH_LONG).show();

                            } catch (Exception e2) {
                                e2.printStackTrace();
                                Timber.d("Error: %s", response.getErrorMessage());
                                Toast.makeText(context, response.getErrorMessage(), Toast.LENGTH_LONG).show();
                            }
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                _forgot_password_progress_bar.setVisibility(View.INVISIBLE);
                try {
                    Snackbar.make(layout, getString(R.string.error_something_wrong), Snackbar.LENGTH_LONG).show();
                } catch (Exception e2) {
                    e2.printStackTrace();
                    Toast.makeText(context, getString(R.string.error_something_wrong), Toast.LENGTH_LONG).show();
                }
            }
        });
        userForgotPasswordRequest.setRetryPolicy(Application.getDefaultRetryPolice());
        userForgotPasswordRequest.setShouldCache(false);
        Application.getInstance().addToRequestQueue(userForgotPasswordRequest, "forgot_password_requests");

    }

}
