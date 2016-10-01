package com.ktfootball.app.UI.Activity.setting;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.frame.app.base.activity.BaseActivity;
import com.frame.app.utils.LogUtils;
import com.kt.ktball.App;
import com.ktfootball.app.Constants;
import com.kt.ktball.myclass.VolleyUtil;
import com.ktfootball.app.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by jy on 16/6/9.
 */
public class FeedbackAvtivity extends BaseActivity {

    @Bind(R.id.layout_feedback_content)
    EditText content;

    @Bind(R.id.layout_feedback_phone)
    EditText phone;

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.layout_feedback);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @OnClick(R.id.layout_feedback)
    public void doYes(View view){
        String contentText = content.getText().toString();
        String phoneText = phone.getText().toString();
        if("".equals(contentText)){
            showToast("请填写您要反馈的意见或建议，谢谢");
            return;
        }
        showLoadingDiaglog();
        String url = Constants.FEEDBACK;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", App.getUserLogin().user_id);
            jsonObject.put("mobile", phoneText);
            jsonObject.put("content", contentText);
            jsonObject.put("authenticity_token", "K9MpaPMdj0jij2m149sL1a7TcYrWXmg5GLrAJDCNBx8");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject1) {
                        LogUtils.e(jsonObject1.toString());
                        closeLoadingDialog();
                        showToast("反馈成功");
                        finish();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                closeLoadingDialog();
                Log.d("========", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=UTF-8");
                return headers;
            }
        };
        VolleyUtil.getInstance(getApplicationContext()).addRequest(jsonRequest);
    }

    public void doBack(View view) {
        finish();
    }
}
