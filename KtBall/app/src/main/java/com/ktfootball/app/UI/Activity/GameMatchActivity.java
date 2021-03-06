package com.ktfootball.app.UI.Activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Message;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.frame.app.base.activity.BaseActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kt.ktball.adapter.GameMatchAdapter;
import com.kt.ktball.entity.GamePlace;
import com.kt.ktball.entity.Games;
import com.kt.ktball.myclass.VolleyUtil;
import com.ktfootball.app.Constants;
import com.ktfootball.app.R;
import com.ktfootball.app.UI.Activity.BlockBook.MyOrderActivity;
import com.ktfootball.app.Utils.MD5;

import org.json.JSONObject;

import java.util.ArrayList;

public class GameMatchActivity extends BaseActivity {

    public static final String EXTRA_GAME_ID = "game_id";
    private double latitude;//纬度
    private double longitude;//经度
    long userId;
    ListView listView1;
    ListView listView2;
    GameMatchAdapter gameMatchAdapter1;
    GameMatchAdapter gameMatchAdapter2;
    private TextView baocahng;

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_game_match);
        listView1 = (ListView) findViewById(R.id.listView9);
        listView2 = (ListView) findViewById(R.id.listView11);
        baocahng = (TextView) findViewById(R.id.activity_game_match_to_baochang);
    }

    @Override
    protected void setListener() {
        baocahng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MyOrderActivity.class);
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        openGPSSettings();//打开GPS定位
    }

    private void openGPSSettings() {
        LocationManager alm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            getLocation();//获取经纬度
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.open_gps));
            builder.setPositiveButton(getString(R.string.right), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
                    startActivityForResult(intent, 0);
                }
            });
            builder.setNegativeButton(getString(R.string.cancle), null);
            builder.setCancelable(false);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    public void doBack(View view) {//退出
        finish();
    }

    private void getLocation() {
        // 获取位置管理服务
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        // 查找到服务信息
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE); // 高精度
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW); // 低功耗

        String provider = locationManager.getBestProvider(criteria, true); // 获取GPS信息
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);
        updateToNewLocation(location);
        // 设置监听器，自动更新的最小时间为间隔N秒(1秒为1*1000，这样写主要为了方便)或最小位移变化超过N米
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100 * 1000, 500, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });
    }

    private void updateToNewLocation(Location location) {
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        initView();
    }

    private void initView() {
        userId = PreferenceManager.getDefaultSharedPreferences(this).getLong(LoginActivity.PRE_CURRENT_USER_ID, 0);
        String url = Constants.HOST + "games/list?user_id=" +
                userId + "&lon=" + longitude + "&lat=" + latitude +
                "&authenticity_token=" + MD5.getToken(Constants.HOST + "games/list");
        showLoadingDiaglog();
        JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        closeLoadingDialog();
                        Log.d("=========", jsonObject.toString());
                        GamePlace gamePlace = new Gson().fromJson(jsonObject.toString(),
                                new TypeToken<GamePlace>() {
                                }.getType());
                        ArrayList<Games> data = gamePlace.games;
                        final ArrayList<Games> data1 = new ArrayList<>();
                        data1.add(data.get(0));
                        ArrayList<Games> data2 = new ArrayList<>();
                        for (int i = data.size() - 1; i > 0; i--) {
                            data2.add(data.get(i));
                        }
                        gameMatchAdapter1 = new GameMatchAdapter(data1, GameMatchActivity.this);
                        listView1.setAdapter(gameMatchAdapter1);
                        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                long gameId = data1.get(0).id;
                                Intent intent = new Intent(GameMatchActivity.this, GamesDetailsActivity.class);
                                intent.putExtra(EXTRA_GAME_ID, gameId);
                                startActivity(intent);
                            }
                        });
                        gameMatchAdapter2 = new GameMatchAdapter(data2, GameMatchActivity.this);
                        listView2.setAdapter(gameMatchAdapter2);
                        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Games games = gameMatchAdapter2.getItem(position);
                                long gameId = games.id;
                                Intent intent = new Intent(GameMatchActivity.this, GamesDetailsActivity.class);
                                intent.putExtra(EXTRA_GAME_ID, gameId);
                                startActivity(intent);
                            }
                        });
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                closeLoadingDialog();
                Log.d("=========", volleyError.toString());
            }
        }
        );
        VolleyUtil.getInstance(this).addRequest(jsonRequest);
    }
}
