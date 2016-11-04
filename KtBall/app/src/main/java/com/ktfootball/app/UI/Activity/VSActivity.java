package com.ktfootball.app.UI.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.frame.app.base.activity.BaseActivity;
import com.frame.app.utils.FileUtil;
import com.kt.ktball.App;
import com.kt.ktball.myclass.MyCircleImageView;
import com.ktfootball.app.Constants;
import com.ktfootball.app.Entity.BattleStart;
import com.ktfootball.app.Entity.GamePlace;
import com.ktfootball.app.Entity.PostLeague;
import com.ktfootball.app.Entity.ScanUser;
import com.ktfootball.app.Manager.BitmapManager;
import com.ktfootball.app.Net.CallServer;
import com.ktfootball.app.Net.HttpListener;
import com.ktfootball.app.R;
import com.ktfootball.app.Request.BattleStartRequest;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Response;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import butterknife.Bind;

public class VSActivity extends BaseActivity implements SurfaceHolder.Callback, OnClickListener {

    @Bind(R.id.vs_imageView)
    ImageView imageViewVs;//开始键，VS
    @Bind(R.id.textView16)
    TextView textViewTime;//倒计时时间
    @Bind(R.id.relativeLayout1000)
    RelativeLayout relativeLayout;//开始录制视频后的所有可见控件的布局
    @Bind(R.id.textView40)
    TextView textViewA;//第1个头像和名称
    @Bind(R.id.textView41)
    TextView textViewB;//第2个头像和名称
    @Bind(R.id.textView42)
    TextView textViewC;//第3个头像和名称
    @Bind(R.id.textView43)
    TextView textViewD;//第4个头像和名称
    @Bind(R.id.textView44)
    TextView textViewE;//第5个头像和名称
    @Bind(R.id.textView45)
    TextView textViewF;//第6个头像和名称

    //左边控件
    @Bind(R.id.imageView20)
    RelativeLayout imageViewBallLeft;
    @Bind(R.id.imageView21)
    ImageView imageViewPassLeft;
    @Bind(R.id.imageView22)
    ImageView imageViewXLeft;
    @Bind(R.id.imageView18)
    ImageView imageViewKtLeft;
    @Bind(R.id.textView17)
    TextView textViewBallLeft;
    @Bind(R.id.textView46)
    TextView textViewPassLeft;
    @Bind(R.id.textView47)
    TextView textViewXLeft;
    @Bind(R.id.textView48)
    TextView textViewAddLeft;

    //右边控件
    @Bind(R.id.imageView23)
    ImageView imageViewBallRight;
    @Bind(R.id.imageView24)
    ImageView imageViewPassRight;
    @Bind(R.id.imageView25)
    ImageView imageViewXRight;
    @Bind(R.id.imageView19)
    ImageView imageViewKtRight;
    @Bind(R.id.textView49)
    TextView textViewBallRight;
    @Bind(R.id.textView50)
    TextView textViewPassRight;
    @Bind(R.id.textView52)
    TextView textViewXRight;
    @Bind(R.id.textView53)
    TextView textViewAddRight;

    @Bind(R.id.imageView27)
    ImageView imageViewContine;//犯规继续和终止
    @Bind(R.id.imageView28)
    ImageView imageViewStop;

    @Bind(R.id.imageView44)
    ImageView imageViewInvalid;//犯规终止后的图片，比赛无效
    @Bind(R.id.imageView46)
    ImageView imageViewLeftGiveUp;//左方弃权
    @Bind(R.id.imageView47)
    ImageView imageViewRightGiveUp;//右方弃权

    @Bind(R.id.imageView48)
    ImageView imageViewKtDialog;
    @Bind(R.id.linearLayout100)
    LinearLayout linearLayout;
    @Bind(R.id.textView2)
    TextView buttonLeft;
    @Bind(R.id.textView7)
    TextView buttinRight;

    @Bind(R.id.kt_imageView)
    ImageView KtImageView;

    @Bind(R.id.imageView2)
    MyCircleImageView imageViewAA;
    @Bind(R.id.imageView81)
    MyCircleImageView imageViewBB;
    @Bind(R.id.imageView82)
    MyCircleImageView imageViewCC;
    @Bind(R.id.imageView85)
    MyCircleImageView imageViewDD;
    @Bind(R.id.imageView84)
    MyCircleImageView imageViewEE;
    @Bind(R.id.imageView83)
    MyCircleImageView imageViewFF;

    private MediaRecorder mediarecorder;// 录制视频的类
    private SurfaceView surfaceview;// 显示视频的控件
    // 用来显示视频的一个接口
    private SurfaceHolder surfaceHolder;

    private ScanUser userA;
    private ScanUser userB;
    private ScanUser userC;
    private ScanUser userD;
    private ScanUser userE;
    private ScanUser userF;
    private PostLeague league1;
    private PostLeague league2;
    private int vedioCode;
    private String battle_id;

    GamePlace.Games games;
    int team;
    private String code;
    private String picturePath;

    @Override
    protected void initHandler(Message msg) {
        switch (msg.what) {
            case Constants.START_MSG:
                handlerStart(msg);
                break;
            case Constants.STOP_MSG:
                handlerStop(msg);
                break;
            case Constants.UPDATA_LEFT_ADD:
                handlerUpdataLeft(msg);
                break;
            case Constants.UPDATA_RIGHT_ADD:
                handlerUpdataRight(msg);
                break;
        }
    }

    private void handlerUpdataRight(Message msg) {
        int addRight = ballRight * 2 + passRight;
        textViewAddRight.setText(String.valueOf(addRight));
    }

    private void handlerUpdataLeft(Message msg) {
        int addLeft = ballLeft * 2 + passLeft;
        textViewAddLeft.setText(String.valueOf(addLeft));
    }

    private void handlerStop(Message msg) {
        getHandler().removeMessages(Constants.START_MSG);
    }

    private void handlerStart(Message msg) {
        int i = msg.arg1;
        //发送消息
        textViewTime.setText(formatTime(i));
        if (i == 9) {
            soundPool.play(soundMap.get(6), 1, 1, 0, 0, 1);
        }

        if (i == 0) {
            getHandler().sendEmptyMessage(Constants.STOP_MSG);
            doStopVCR();
            vSintent.putExtra(Constants.KT_CODE, 0);
            vSintent.putExtra(Constants.LEFT_BALL_GRADE, textViewBallLeft.getText());
            vSintent.putExtra(Constants.LEFT_PASS_GRADE, textViewPassLeft.getText());
            vSintent.putExtra(Constants.LEFT_X_GRADE, textViewXLeft.getText());
            vSintent.putExtra(Constants.RIGHT_BALL_GRADE, textViewBallRight.getText());
            vSintent.putExtra(Constants.RIGHT_PASS_GRADE, textViewPassRight.getText());
            vSintent.putExtra(Constants.RIGHT_X_GRADE, textViewXRight.getText());
            vSintent.putExtra(Constants.VCR_PATH, VCRpath);
            vSintent.putExtra(Constants.BATTLE_ID, battle_id);
            vSintent.putExtra(Constants.LEAGUE1, league1);
            vSintent.putExtra(Constants.LEAGUE2, league2);
            vSintent.putExtra(Constants.GAME, games);
            vSintent.putExtra(Constants.QICHANG_CODE, code);
            vSintent.putExtra(Constants.PICTUREPATH, picturePath);
            startActivity(vSintent);
            finish();
        } else {
            Message m = Message.obtain();
            m.what = Constants.START_MSG;
            m.arg1 = --i;
            getHandler().sendMessageDelayed(m, 1000);
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        // 选择支持半透明模式,在有surfaceview的activity中使用。
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_vs);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        team = intent.getIntExtra(Constants.USER_TEAM, 0);
        games = (GamePlace.Games) intent.getSerializableExtra(Constants.GAME);
        vedioCode = intent.getIntExtra(Constants.VEDIOCODE, 1);
        league1 = (PostLeague) intent.getSerializableExtra(Constants.LEAGUE1);
        league2 = (PostLeague) intent.getSerializableExtra(Constants.LEAGUE2);
        userA = (ScanUser) intent.getSerializableExtra(Constants.USER_A);
        userB = (ScanUser) intent.getSerializableExtra(Constants.USER_B);
        userC = (ScanUser) intent.getSerializableExtra(Constants.USER_C);
        userD = (ScanUser) intent.getSerializableExtra(Constants.USER_D);
        userE = (ScanUser) intent.getSerializableExtra(Constants.USER_E);
        userF = (ScanUser) intent.getSerializableExtra(Constants.USER_F);
        picturePath = intent.getStringExtra(Constants.PICTUREPATH);
        code = intent.getStringExtra(Constants.QICHANG_CODE);
        init();
    }

    // VCR 存放路径
    private String VCRpath;

    @Override
    protected void onStart() {
        super.onStart();
        initView();
    }

    Intent vSintent;

    //初始化视图
    private void initView() {
        String gamePlace = games.name;
        //获取当前时间
        String currentTime = String.valueOf(System.currentTimeMillis());
//        String path = getCacheDir().getAbsolutePath()+"/KT足球/";
        String path = FileUtil.getDir(getThis(),getString(R.string.kt_ball));
//        String path = Environment.getExternalStorageDirectory().toString() + "/KT足球/";
        relativeLayout.setVisibility(View.GONE);
        textViewTime.setVisibility(View.GONE);
        vSintent = new Intent(this, UploadingActivity.class);
        vSintent.putExtra(Constants.USER_TEAM, team);
        textViewA.setText(userA.nickname);
        textViewF.setText(userF.nickname);
        BitmapManager.getInstance().displayUserLogo(imageViewAA, App.getImageUrl(userA.avatar));
        BitmapManager.getInstance().displayUserLogo(imageViewFF, App.getImageUrl(userF.avatar));
        vSintent.putExtra(Constants.USER_A, userA);
        vSintent.putExtra(Constants.USER_F, userF);
        switch (team) {
            case 1:
                VCRpath = path + userA.nickname + " VS " + userF.nickname + " "+getString(R.string.in) + gamePlace + currentTime + ".mp4";
                break;
            case 2:
                textViewB.setText(userB.nickname);
                textViewE.setText(userE.nickname);
                vSintent.putExtra(Constants.USER_B, userB);
                vSintent.putExtra(Constants.USER_E, userE);
                BitmapManager.getInstance().displayUserLogo(imageViewBB, App.getImageUrl(userB.avatar));
                BitmapManager.getInstance().displayUserLogo(imageViewEE, App.getImageUrl(userE.avatar));
                VCRpath = path + userA.nickname + "," + userB.nickname + " VS " + userE.nickname + "," + userF.nickname + " "+getString(R.string.in) + gamePlace + currentTime + ".mp4";
                break;
            case 3:
                textViewC.setText(userC.nickname);
                textViewD.setText(userD.nickname);
                textViewB.setText(userB.nickname);
                textViewE.setText(userE.nickname);
                vSintent.putExtra(Constants.USER_B, userB);
                vSintent.putExtra(Constants.USER_E, userE);
                vSintent.putExtra(Constants.USER_C, userC);
                vSintent.putExtra(Constants.USER_D, userD);
                BitmapManager.getInstance().displayUserLogo(imageViewBB, App.getImageUrl(userB.avatar));
                BitmapManager.getInstance().displayUserLogo(imageViewEE, App.getImageUrl(userE.avatar));
                BitmapManager.getInstance().displayUserLogo(imageViewCC, App.getImageUrl(userC.avatar));
                BitmapManager.getInstance().displayUserLogo(imageViewDD, App.getImageUrl(userD.avatar));
                VCRpath = path + userA.nickname + "," + userB.nickname + "," + userC.nickname + " VS " + userD.nickname + "," + userE.nickname + "," + userF.nickname + " "+getString(R.string.in) + gamePlace + currentTime + ".mp4";
                break;
        }
    }

    private SoundPool soundPool;//音乐池
    HashMap<Integer, Integer> soundMap;

    private void init() {
        imageViewVs.setOnClickListener(this);
        imageViewBallLeft.setOnClickListener(this);
        imageViewPassLeft.setOnClickListener(this);
        imageViewXLeft.setOnClickListener(this);
        imageViewKtLeft.setOnClickListener(this);
        imageViewBallRight.setOnClickListener(this);
        imageViewPassRight.setOnClickListener(this);
        imageViewXRight.setOnClickListener(this);
        imageViewKtRight.setOnClickListener(this);
        imageViewContine.setOnClickListener(this);
        imageViewStop.setOnClickListener(this);
        imageViewInvalid.setOnClickListener(this);
        imageViewLeftGiveUp.setOnClickListener(this);
        imageViewRightGiveUp.setOnClickListener(this);
        buttonLeft.setOnClickListener(this);
        buttinRight.setOnClickListener(this);
        textViewTime.setOnClickListener(this);

        soundMap = new HashMap<>();
        soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        soundMap.put(1, soundPool.load(this, R.raw.start_end, 1));
        soundMap.put(2, soundPool.load(this, R.raw.foul, 1));
        soundMap.put(3, soundPool.load(this, R.raw.resume, 1));
        soundMap.put(4, soundPool.load(this, R.raw.getpoint, 1));
        soundMap.put(5, soundPool.load(this, R.raw.kt, 1));
        soundMap.put(6, soundPool.load(this, R.raw.bf_end, 1));
        soundMap.put(7, soundPool.load(this, R.raw.pause, 1));

        surfaceview = (SurfaceView) this.findViewById(R.id.surfaceview);
        SurfaceHolder holder = surfaceview.getHolder();// 取得holder
        holder.addCallback(this); // holder加入回调接口
        // setType必须设置，要不出错.
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    int ballLeft = 0;
    int passLeft = 0;
    int xLeft = 0;

    int ballRight = 0;
    int passRight = 0;
    int xRight = 0;

    String currentTime;//当前录制的时间
    boolean time = false;

    private void PostResult(String game_type, String gameId, String user1_id, String user2_id, String league1_id, String league2_id, HttpListener<BattleStart> callback) {

        BattleStartRequest request = new BattleStartRequest(Constants.BATTLE_START, RequestMethod.POST);
        request.add("game_type", game_type);
        request.add("game_id", gameId);
        request.add("user1_id", user1_id);
        request.add("user2_id", user2_id);
        request.add("league1_id", league1_id);
        request.add("league2_id", league2_id);
        CallServer.getRequestInstance().add(this, 0, request, callback, false, false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.vs_imageView:
                if (team == 1) {
                    PostResult((team - 1) + "", games.id + "", userA.user_id + "", userF.user_id + "", "", "", httpListener1);
                } else {
                    PostResult((team - 1) + "", games.id + "", "", "", league1.league_id + "", league2.league_id + "", httpListener1);
                }
                textViewTime.setVisibility(View.VISIBLE);
                imageViewVs.setVisibility(View.GONE);
                relativeLayout.setVisibility(View.VISIBLE);
                doCountDown();
                if (null != camera) {
                    camera.setPreviewCallback(null);
                    camera.stopPreview();
                    camera.release();
                    camera = null;
                }
                mediarecorder = new MediaRecorder();// 创建mediarecorder对象
                // 设置录制视频源为Camera(相机)
                mediarecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
                // 设置录制完成后视频的封装格式THREE_GPP为3gp.MPEG_4为mp4
                mediarecorder
                        .setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                // 设置录制的视频编码h263 h264
                mediarecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
                // 设置视频录制的分辨率。必须放在设置编码和格式的后面，否则报错
//                              mediarecorder.setVideoSize(320, 240);
//                if (vedioCode == 1) {
//                    mediarecorder.setVideoSize(720, 480);
//                } else if (vedioCode == 2) {
//                    mediarecorder.setVideoSize(1280, 720);
//                } else {
//                    mediarecorder.setVideoSize(1280, 720);
//                }
//
//                // 设置录制的视频帧率。必须放在设置编码和格式的后面，否则报错
                mediarecorder.setVideoFrameRate(30);
//                mediarecorder.setPreviewDisplay(surfaceHolder.getSurface());
                mediarecorder.setVideoEncodingBitRate(1 * 2048 * 1024);
                mediarecorder.setPreviewDisplay(surfaceHolder.getSurface());
                // 设置视频文件输出的路径
                mediarecorder.setOutputFile(VCRpath);
                Log.d("mediarecorder.setOu", VCRpath);
                try {
                    // 准备录制
                    mediarecorder.prepare();
                    // 开始录制
                    mediarecorder.start();
                    soundPool.play(soundMap.get(1), 1, 1, 0, 0, 1);
                    currentTime = refFormatNowDate();
                    vSintent.putExtra(Constants.CURRENT_VCR_TIME, currentTime);
                } catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            case R.id.imageView20://左足球
                ballLeft += 1;
                textViewBallLeft.setText(String.valueOf(ballLeft));
                Message mball = Message.obtain();
                mball.what = Constants.UPDATA_LEFT_ADD;
                getHandler().sendMessage(mball);
                soundPool.play(soundMap.get(4), 1, 1, 0, 0, 1);
                break;
            case R.id.imageView21://左胯裆
                passLeft += 1;
                textViewPassLeft.setText(String.valueOf(passLeft));
                Message mpass = Message.obtain();
                mpass.what = Constants.UPDATA_LEFT_ADD;
                getHandler().sendMessage(mpass);
                soundPool.play(soundMap.get(4), 1, 1, 0, 0, 1);
                break;
            case R.id.imageView22://左犯规
                xLeft += 1;
                textViewXLeft.setText(String.valueOf(xLeft));
                doContineOrStop();
                break;
            case R.id.imageView18://左KT
                vSintent.putExtra(Constants.KT_CODE, 1);
                doKT();
                break;
            case R.id.imageView23://右足球
                ballRight += 1;
                textViewBallRight.setText(String.valueOf(ballRight));
                Message mb = Message.obtain();
                mb.what = Constants.UPDATA_RIGHT_ADD;
                getHandler().sendMessage(mb);
                soundPool.play(soundMap.get(4), 1, 1, 0, 0, 1);
                break;
            case R.id.imageView24://右胯裆
                passRight += 1;
                textViewPassRight.setText(String.valueOf(passRight));
                Message mp = Message.obtain();
                mp.what = Constants.UPDATA_RIGHT_ADD;
                getHandler().sendMessage(mp);
                soundPool.play(soundMap.get(4), 1, 1, 0, 0, 1);
                break;
            case R.id.imageView25://右犯规
                xRight += 1;
                textViewXRight.setText(String.valueOf(xRight));
                doContineOrStop();
                break;
            case R.id.imageView19://右KT
                vSintent.putExtra(Constants.KT_CODE, 2);
                doKT();
                break;
            case R.id.imageView27://犯规继续
                imageViewContine.setVisibility(View.GONE);
                imageViewStop.setVisibility(View.GONE);
                imageViewBallLeft.setEnabled(true);
                imageViewPassLeft.setEnabled(true);
                imageViewXLeft.setEnabled(true);
                imageViewKtLeft.setEnabled(true);
                imageViewBallRight.setEnabled(true);
                imageViewPassRight.setEnabled(true);
                imageViewXRight.setEnabled(true);
                imageViewKtRight.setEnabled(true);
                textViewTime.setEnabled(true);
                doSendMsg();
                soundPool.play(soundMap.get(3), 1, 1, 0, 0, 1);
                break;
            case R.id.imageView28://犯规终止
                imageViewContine.setVisibility(View.GONE);
                imageViewStop.setVisibility(View.GONE);
                imageViewInvalid.setVisibility(View.VISIBLE);
                imageViewLeftGiveUp.setVisibility(View.VISIBLE);
                imageViewRightGiveUp.setVisibility(View.VISIBLE);
                break;
            case R.id.imageView44://取消比赛
                doStopVCR();
                File file = new File(VCRpath);
                if (file.exists()) {
                    file.delete();
                }
                finish();
                break;
            case R.id.imageView46://左方弃权
                doStopVCR();
                File file1 = new File(VCRpath);
                if (file1.exists()) {
                    file1.delete();
                }
                finish();
                break;
            case R.id.imageView47://右方弃权
                doStopVCR();
                File file2 = new File(VCRpath);
                if (file2.exists()) {
                    file2.delete();
                }
                finish();
                break;
            case R.id.textView2://KT确定
                doStopVCR();
                soundPool.play(soundMap.get(5), 1, 1, 0, 0, 1);
                imageViewKtDialog.setVisibility(View.GONE);
                linearLayout.setVisibility(View.GONE);
                buttonLeft.setVisibility(View.GONE);
                buttinRight.setVisibility(View.GONE);
                vSintent.putExtra(Constants.LEFT_BALL_GRADE, textViewBallLeft.getText());
                vSintent.putExtra(Constants.LEFT_PASS_GRADE, textViewPassLeft.getText());
                vSintent.putExtra(Constants.LEFT_X_GRADE, textViewXLeft.getText());
                vSintent.putExtra(Constants.RIGHT_BALL_GRADE, textViewBallRight.getText());
                vSintent.putExtra(Constants.RIGHT_PASS_GRADE, textViewPassRight.getText());
                vSintent.putExtra(Constants.RIGHT_X_GRADE, textViewXRight.getText());
                vSintent.putExtra(Constants.VCR_PATH, VCRpath);
                vSintent.putExtra(Constants.BATTLE_ID, battle_id);
                vSintent.putExtra(Constants.LEAGUE1, league1);
                vSintent.putExtra(Constants.LEAGUE2, league2);
                vSintent.putExtra(Constants.GAME, games);
                vSintent.putExtra(Constants.QICHANG_CODE, code);
                vSintent.putExtra(Constants.PICTUREPATH, picturePath);
                Log.d("mediarecorder.setOu", VCRpath);
                KtImageView.setVisibility(View.VISIBLE);
                Animation a = AnimationUtils.loadAnimation(this, R.anim.demo_kt);
                KtImageView.startAnimation(a);
//                if (league1 == null || league2 == null || games == null) {
//                    showDialogToast("asd");
//                }else{
                    getHandler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(vSintent);
                            finish();
                        }
                    }, 3000);
//                }

                break;
            case R.id.textView7://KT取消
                imageViewKtDialog.setVisibility(View.GONE);
                linearLayout.setVisibility(View.GONE);
                buttonLeft.setVisibility(View.GONE);
                buttinRight.setVisibility(View.GONE);
                imageViewBallLeft.setEnabled(true);
                imageViewPassLeft.setEnabled(true);
                imageViewXLeft.setEnabled(true);
                imageViewKtLeft.setEnabled(true);
                imageViewBallRight.setEnabled(true);
                imageViewPassRight.setEnabled(true);
                imageViewXRight.setEnabled(true);
                imageViewKtRight.setEnabled(true);
                textViewTime.setEnabled(true);
                doSendMsg();
                break;
            case R.id.textView16://时间
                getHandler().sendEmptyMessage(Constants.STOP_MSG);
                soundPool.play(soundMap.get(7), 1, 1, 0, 0, 1);
                imageViewContine.setVisibility(View.VISIBLE);
                imageViewStop.setVisibility(View.VISIBLE);
                imageViewBallLeft.setEnabled(false);
                imageViewPassLeft.setEnabled(false);
                imageViewXLeft.setEnabled(false);
                imageViewKtLeft.setEnabled(false);
                imageViewBallRight.setEnabled(false);
                imageViewPassRight.setEnabled(false);
                imageViewXRight.setEnabled(false);
                imageViewKtRight.setEnabled(false);
                textViewTime.setEnabled(false);
                break;
        }
    }

    private HttpListener<BattleStart> httpListener1 = new HttpListener<BattleStart>() {
        @Override
        public void onSucceed(int what, Response<BattleStart> response) {
            if (response.get().response.equals("success")) {
                battle_id = response.get().battle_id;
//                showDialogToast(response.get().battle_id);
            } else {
                battle_id = "";
            }
        }

        @Override
        public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
        }
    };

    //获取当前时间，上传比分的数据
    public String refFormatNowDate() {
        Date nowTime = new Date(System.currentTimeMillis());
        SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String retStrFormatNowDate = sdFormatter.format(nowTime);
        return retStrFormatNowDate;
    }

    private void doSendMsg() {//发消息
        String time = textViewTime.getText().toString();
        int i = Integer.parseInt(time.substring(1, time.indexOf(":")));
        int ii = Integer.parseInt(time.substring(time.indexOf(":") + 1, time.length()));
        Message m = Message.obtain();
        m.what = Constants.START_MSG;
        m.arg1 = i * 60 + ii;
        getHandler().sendMessage(m);
    }

    //KT键
    private void doKT() {
        imageViewKtDialog.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.VISIBLE);
        buttonLeft.setVisibility(View.VISIBLE);
        buttinRight.setVisibility(View.VISIBLE);
        imageViewBallLeft.setEnabled(false);
        imageViewPassLeft.setEnabled(false);
        imageViewXLeft.setEnabled(false);
        imageViewKtLeft.setEnabled(false);
        imageViewBallRight.setEnabled(false);
        imageViewPassRight.setEnabled(false);
        imageViewXRight.setEnabled(false);
        imageViewKtRight.setEnabled(false);
        textViewTime.setEnabled(false);
        getHandler().sendEmptyMessage(Constants.STOP_MSG);
    }

    //犯规继续or终止
    private void doContineOrStop() {
        imageViewContine.setVisibility(View.VISIBLE);
        imageViewStop.setVisibility(View.VISIBLE);
        imageViewBallLeft.setEnabled(false);
        imageViewPassLeft.setEnabled(false);
        imageViewXLeft.setEnabled(false);
        imageViewKtLeft.setEnabled(false);
        imageViewBallRight.setEnabled(false);
        imageViewPassRight.setEnabled(false);
        imageViewXRight.setEnabled(false);
        imageViewKtRight.setEnabled(false);
        textViewTime.setEnabled(false);
        soundPool.play(soundMap.get(2), 1, 1, 0, 0, 1);
        getHandler().sendEmptyMessage(Constants.STOP_MSG);
    }

    //倒计时
    private void doCountDown() {
        Message m = Message.obtain();
        m.what = Constants.START_MSG;
        m.arg1 = 180;
        getHandler().sendMessage(m);
    }

    //停止录像
    private void doStopVCR() {
        if (mediarecorder != null) {
            // 停止录制
            mediarecorder.stop();
            // 释放资源
            mediarecorder.release();
            mediarecorder = null;
        }
    }

    //格式时间
    private String formatTime(int i) {
        String time = null;
        if (i == 180) {
            time = "03:00";
        } else if (i >= 120 && i < 180) {
            time = "02:" + String.format("%02d", i - 120);
        } else if (i >= 60 && i < 120) {
            time = "01:" + String.format("%02d", i - 60);
        } else if (i > 0) {
            time = "00:" + String.format("%02d", i);
        } else {
            time = "00:00";
        }
        return time;
    }


    Camera camera = null;

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // 将holder，这个holder为开始在oncreat里面取得的holder，将它赋给surfaceHolder
        //获取camera对象
        surfaceHolder = holder;
        camera = Camera.open();
        try {
            //设置预览监听
            camera.setPreviewDisplay(holder);
            Camera.Parameters parameters = camera.getParameters();

            if (this.getResources().getConfiguration().orientation
                    != Configuration.ORIENTATION_LANDSCAPE) {
                parameters.set("orientation", "portrait");
                camera.setDisplayOrientation(90);
                parameters.setRotation(90);
            } else {
                parameters.set("orientation", "landscape");
                camera.setDisplayOrientation(0);
                parameters.setRotation(0);
            }
            camera.setParameters(parameters);
            //启动摄像头预览
            camera.startPreview();
            System.out.println("camera.startpreview");

        } catch (IOException e) {
            e.printStackTrace();
            camera.release();
            System.out.println("camera.release");
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // 将holder，这个holder为开始在oncreat里面取得的holder，将它赋给surfaceHolder
        surfaceHolder = holder;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //surfaceDestroyed的时候同时对象设置为null

        surfaceview = null;
        surfaceHolder = null;
        mediarecorder = null;

        if (camera != null) {
            camera.stopPreview();
            camera.release();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.release();
    }
}
