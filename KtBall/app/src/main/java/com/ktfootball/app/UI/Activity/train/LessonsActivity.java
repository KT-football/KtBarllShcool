package com.ktfootball.app.UI.Activity.train;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.frame.app.base.Adapter.OnRecyclerViewItemClickListener;
import com.frame.app.base.activity.BaseActivity;
import com.frame.app.utils.FileUtil;
import com.frame.app.utils.LogUtils;
import com.frame.app.utils.PhoneUtils;
import com.frame.app.utils.ZipUtils;
import com.frame.app.view.Dialog.SingleDownDialog;
import com.ktfootball.app.Manager.BitmapManager;
import com.ktfootball.app.Manager.bitmap.GlideHelper;
import com.ktfootball.app.Entity.AppCartoon;
import com.ktfootball.app.Entity.LoadFile;
import com.ktfootball.app.Manager.bitmap.PlaceHolder;
import com.ktfootball.app.Manager.bitmap.RotateTransformation;
import com.ktfootball.app.Net.CallServer;
import com.ktfootball.app.R;
import com.ktfootball.app.Views.DownListDialog;
import com.ktfootball.app.Views.LessonsDialog;
import com.ktfootball.app.Views.StartTrainDialog;
import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.Logger;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.download.DownloadListener;
import com.yolanda.nohttp.download.DownloadRequest;
import com.yolanda.nohttp.error.ArgumentError;
import com.yolanda.nohttp.error.NetworkError;
import com.yolanda.nohttp.error.ServerError;
import com.yolanda.nohttp.error.StorageReadWriteError;
import com.yolanda.nohttp.error.StorageSpaceNotEnoughError;
import com.yolanda.nohttp.error.TimeoutError;
import com.yolanda.nohttp.error.URLError;
import com.yolanda.nohttp.error.UnKnownHostError;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by jy on 16/6/22.
 */
public class LessonsActivity extends BaseActivity {

    private String path;
    private AppCartoon appCartoon;
    private String name;
    private List<View> list;
    private ViewPager viewPager;
    private ImageView bt;
    private RelativeLayout close;
    private LessonsDialog dialog;
    private int code;
    private SingleDownDialog singleDownDialog;
    private DownListDialog listDownDialog;
    private DownloadRequest mDownloadRequest;
    private StartTrainDialog startTrainDiialog;
    public static final int STARTTRAIN_1 = 1001;
    public static final int STARTTRAIN_2 = 1002;
    public static final int STARTTRAIN_3 = 1003;
    public static final int MANHUA_1 = 1004;
    public static final int MANHUA_2 = 1005;
    private int x = 0;

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        setContentView(R.layout.layout_lessins);
        viewPager = (ViewPager) findViewById(R.id.layout_lessons_vp);
        bt = (ImageView) findViewById(R.id.layout_imagefragment_bt);
        close = (RelativeLayout) findViewById(R.id.layout_lessons_close);
    }

    @Override
    protected void setListener() {
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                } else {
                    showDialog();
                }
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void showDialog() {
        dialog = new LessonsDialog(this, R.style.transparentFrameWindowStyle, code);
        dialog.show();
        dimActivity(dialog, 0.6f);
        if (code == 0) {
            dialog.setOnItemClickListener(listener1);
        } else {
            dialog.setOnItemClickListener(listener2);
        }
    }

    private OnRecyclerViewItemClickListener listener1 = new OnRecyclerViewItemClickListener() {
        @Override
        public void onItemClick(ViewGroup parent, View view, int position) {
            switch (position) {
                case 0:
                    showDownDialog(MANHUA_2);
                    break;
                case 1:
                    showtartTrainDialog();
                    break;
                case 2:
                    Intent intent = new Intent(getThis(), PersonVedioActivity.class);
                    intent.putExtra("data", appCartoon);
                    startActivity(intent);
                    break;
            }
        }
    };
    private OnRecyclerViewItemClickListener listener2 = new OnRecyclerViewItemClickListener() {
        @Override
        public void onItemClick(ViewGroup parent, View view, int position) {
            switch (position) {
                case 0:
                    showDownDialog(MANHUA_1);
                    break;
                case 1:
                    showtartTrainDialog();
                    break;
                case 2:
                    Intent intent = new Intent(getThis(), PersonVedioActivity.class);
                    intent.putExtra("data", appCartoon);
                    startActivity(intent);
                    break;
            }
        }
    };

    private void showtartTrainDialog() {
        if (startTrainDiialog == null) {
            startTrainDiialog = new StartTrainDialog(this, R.style.transparentFrameWindowStyle);
            startTrainDiialog.setItenClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDownDialog(TrainDetailsActivity.STARTTRAIN_1);
                    startTrainDiialog.dismiss();
                }
            }, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDownDialog(TrainDetailsActivity.STARTTRAIN_2);
                    startTrainDiialog.dismiss();
                }
            }, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDownDialog(TrainDetailsActivity.STARTTRAIN_3);
                    startTrainDiialog.dismiss();
                }
            });
        }
        startTrainDiialog.show();
        dimActivity(startTrainDiialog, 0.6f);
    }

    public void showDownDialog(int code) {
        switch (code) {
            case STARTTRAIN_1:
                AppCartoon.Videos videos1 = selectVideo("0");
                if (videos1 != null) {
                    String str = getString(R.string.download_1_class);
                    downVideos(str, videos1);
                }
                break;
            case STARTTRAIN_2:
                AppCartoon.Videos videos2 = selectVideo("1");
                if (videos2 != null) {
                    String str = getString(R.string.download_2_class);
                    downVideos(str, videos2);
                }
                break;
            case STARTTRAIN_3:
                AppCartoon.Videos videos3 = selectVideo("2");
                if (videos3 != null) {
                    String str = getString(R.string.download_3_class);
                    downVideos(str, videos3);
                }
                break;
            case MANHUA_1:
                List<AppCartoon.Lessons> lit1 = appCartoon.lessons;
                AppCartoon.Lessons lessons1 = lit1.get(1);
                if (lessons1 != null) {
                    String str = getString(R.string.download_manhua);
                    downLessons(str, lessons1, 0);
                }
                break;
            case MANHUA_2:
                List<AppCartoon.Lessons> lit2 = appCartoon.lessons;
                AppCartoon.Lessons lessons2 = lit2.get(0);
                if (lessons2 != null) {
                    String str = getString(R.string.download_gushi);
                    downLessons(str, lessons2, 1);
                }
                break;
        }
    }

    private void downLessons(String str, AppCartoon.Lessons lessons, int code) {
        String path = FileUtil.getDecompressionDir(getThis()) + appCartoon.name + "/" + lessons.name;
        if (FileUtil.fileExists(path)) {
            Intent intent = new Intent(getThis(), LessonsActivity.class);
            intent.putExtra("path", path);
            intent.putExtra("data", appCartoon);
            intent.putExtra("name", lessons.name);
            intent.putExtra("code", code);
            startActivity(intent);
            finish();
        } else {
            selectDialog(str, lessons.zip_size, lessons.download_images_url, str);
        }
    }

    private void selectDialog(String str, String size, String url, final String name) {
        int model = PhoneUtils.isNetworkAvailable(getThis());
        if (model == 1) {//wifi
            showWiFiDialog(str, size, url, name);
        } else {
            showGDialog(size, url, name);
        }
    }

    private AppCartoon.Videos selectVideo(String level) {
        if (appCartoon != null) {
            List<AppCartoon.Videos> list = appCartoon.videos;
            for (int x = 0; x < list.size(); x++) {
                AppCartoon.Videos videos = list.get(x);
                if (videos.video_level.equals(level)) {
                    return videos;
                }
            }
        }
        return null;
    }

    private void showWiFiDialog(String str, String size, final String url, final String name) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getThis());
        builder.setMessage(getString(R.string.is_wifi_download) + size + "KB）");
        builder.setTitle(getString(R.string.prompt));
        builder.setPositiveButton("   " + str, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                doSingleDown(url, name);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(getString(R.string.download_all_content), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                download();
                dialog.dismiss();
            }
        });
        builder.setNeutralButton(getString(R.string.cancle), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void showGDialog(String size, final String url, final String name) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getThis());
        builder.setMessage(getString(R.string.download_liuliang) + size + getString(R.string.donwload_is));
        builder.setTitle(getString(R.string.prompt));
        builder.setPositiveButton(getString(R.string.i_have_money), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                doSingleDown(url, name);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(getString(R.string.cancle), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void downVideos(String str, AppCartoon.Videos videos) {
        String path = FileUtil.getDecompressionDir(getThis()) + appCartoon.name + "/";
        String name = getFileName(videos.download_video_url).replace(".zip", "");
        if (FileUtil.fileExists(path + name)) {
            Intent intent = new Intent(getThis(), VedioViewpagerActiviy.class);
            intent.putExtra("path", path);
            intent.putExtra("name", name);
            intent.putExtra("data", appCartoon);
            intent.putExtra("video", videos);
            startActivity(intent);
        } else {
            selectDialog(str, videos.video_size, videos.download_video_url, str);
        }
    }

    private void doSingleDown(String url, String name) {
        String fileName = getFileName(url);
        LogUtils.e(fileName);
        // url 下载地址。
        // fileFolder 保存的文件夹。
        // fileName 文件名。
        // isRange 是否断点续传下载。
        // isDeleteOld 如果发现存在同名文件，是否删除后重新下载，如果不删除，则直接下载成功。
        LogUtils.e(url);
        url = getReleaUrl(url);
        showsingleDownDialog(name);
        mDownloadRequest = NoHttp.createDownloadRequest(url, FileUtil.getDownloadDir(getThis()), fileName, true, true);
        mDownloadRequest.addHeader("Accept", "application/json");
        mDownloadRequest.setContentType("application/octet-stream; charset=UTF-8");
        // what 区分下载。
        // downloadRequest 下载请求对象。
        // downloadListener 下载监听。
        CallServer.getDownloadInstance().add(0, mDownloadRequest, downloadListener);
    }

    private String getFileName(String url) {
        int index = url.lastIndexOf("/");
        return url.substring(index + 1);
    }

    public static String getReleaUrl(String url) {
        try {
            url = URLEncoder.encode(url, "utf-8");
            url = url.replaceAll("\\+", "%20");
            return url.replaceAll("%3A", ":").replaceAll("%2F", "/");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * 文件列表。
     */
    private List<LoadFile> mFileList;

    /**
     * 下载任务列表。
     */
    private List<DownloadRequest> mDownloadRequests;

    /**
     * 开始下载全部。
     */
    private void download() {
        if (appCartoon == null) {
            return;
        }
        mDownloadRequests = new ArrayList<>();
        mFileList = new ArrayList<>();
        for (int x = 0; x < appCartoon.lessons.size(); x++) {
            AppCartoon.Lessons l = appCartoon.lessons.get(x);
            // 创建四个下载请求并且保存起来。
            DownloadRequest downloadRequest = NoHttp.createDownloadRequest(getReleaUrl(l.download_images_url), FileUtil.getDownloadDir(getThis()), getFileName(l.download_images_url), true, true);
            mDownloadRequests.add(downloadRequest);
            mFileList.add(new LoadFile(l.name, 0));
        }
        for (int y = 0; y < appCartoon.videos.size(); y++) {
            AppCartoon.Videos v = appCartoon.videos.get(y);
            // 创建四个下载请求并且保存起来。
            DownloadRequest downloadRequest = NoHttp.createDownloadRequest(getReleaUrl(v.download_video_url), FileUtil.getDownloadDir(getThis()), getFileName(v.download_video_url), true, true);
            mDownloadRequests.add(downloadRequest);
            String name = "";
            if (v.video_level.equals("0")) {
                name = getString(R.string.download_1);
            }
            if (v.video_level.equals("1")) {
                name = getString(R.string.download_2);
            }
            if (v.video_level.equals("2")) {
                name = getString(R.string.download_3);
            }
            mFileList.add(new LoadFile(name, 0));
        }
        showListDownDialog();
        x = 0;
        for (int i = 0; i < mDownloadRequests.size(); i++) {
            CallServer.getDownloadInstance().add(i, mDownloadRequests.get(i), listDownloadListener);
        }
    }

    private void showListDownDialog() {
        listDownDialog = new DownListDialog(getThis(), R.style.transparentFrameWindowStyle, mFileList);
        listDownDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                for (DownloadRequest downloadRequest : mDownloadRequests) {
                    downloadRequest.cancel();
                }
            }
        });
        listDownDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        listDownDialog.show();
        dimActivity(listDownDialog, 0.6f);
    }

    /**
     * 下载状态监听。
     */
    /**
     * 下载监听
     */
    private DownloadListener listDownloadListener = new DownloadListener() {

        @Override
        public void onStart(int what, boolean isResume, long beforeLength, Headers headers, long allCount) {
            updateProgress(what, 0);
        }

        @Override
        public void onDownloadError(int what, Exception exception) {
            String message = getString(R.string.download_error);
            String messageContent;
            if (exception instanceof ServerError) {
                messageContent = getString(R.string.download_error_server);
            } else if (exception instanceof NetworkError) {
                messageContent = getString(R.string.download_error_network);
            } else if (exception instanceof StorageReadWriteError) {
                messageContent = getString(R.string.download_error_storage);
            } else if (exception instanceof StorageSpaceNotEnoughError) {
                messageContent = getString(R.string.download_error_space);
            } else if (exception instanceof TimeoutError) {
                messageContent = getString(R.string.download_error_timeout);
            } else if (exception instanceof UnKnownHostError) {
                messageContent = getString(R.string.download_error_un_know_host);
            } else if (exception instanceof URLError) {
                messageContent = getString(R.string.download_error_url);
            } else if (exception instanceof ArgumentError) {
                messageContent = getString(R.string.download_error_argument);
            } else {
                messageContent = getString(R.string.download_error_un);
            }
            showDialogToast(messageContent);
        }

        @Override
        public void onProgress(int what, int progress, long fileCount) {
            updateProgress(what, progress);
        }

        @Override
        public void onFinish(int what, String filePath) {
            Logger.d("Download finish");
            try {
                ZipUtils.UnZipFolder(filePath, FileUtil.getDecompressionDir(getThis()) + appCartoon.name);

            } catch (Exception e) {
                showDialogToast(getString(R.string.zevf_error));
            }
            downFinish();
        }

        @Override
        public void onCancel(int what) {
        }


        /**
         * 更新进度。
         * @param what 哪个item。
         * @param progress 进度值。
         */
        private void updateProgress(int what, int progress) {
            mFileList.get(what).setProgress(progress);
            listDownDialog.notifyItemChanged(what);
        }
    };

    private synchronized void downFinish() {
        ++x;
        if (x == mFileList.size() && listDownDialog != null) {
            listDownDialog.dialogDismiss();
        }
    }

    /**
     * 下载监听
     */
    private DownloadListener downloadListener = new DownloadListener() {

        @Override
        public void onStart(int what, boolean isResume, long beforeLength, Headers headers, long allCount) {
            LogUtils.e("onStart" + isResume + "..." + beforeLength + "..." + allCount);
        }

        @Override
        public void onDownloadError(int what, Exception exception) {
            LogUtils.e("onDownloadError = " + exception.toString());
            String message = getString(R.string.download_error);
            String messageContent;
            if (exception instanceof ServerError) {
                messageContent = getString(R.string.download_error_server);
            } else if (exception instanceof NetworkError) {
                messageContent = getString(R.string.download_error_network);
            } else if (exception instanceof StorageReadWriteError) {
                messageContent = getString(R.string.download_error_storage);
            } else if (exception instanceof StorageSpaceNotEnoughError) {
                messageContent = getString(R.string.download_error_space);
            } else if (exception instanceof TimeoutError) {
                messageContent = getString(R.string.download_error_timeout);
            } else if (exception instanceof UnKnownHostError) {
                messageContent = getString(R.string.download_error_un_know_host);
            } else if (exception instanceof URLError) {
                messageContent = getString(R.string.download_error_url);
            } else if (exception instanceof ArgumentError) {
                messageContent = getString(R.string.download_error_argument);
            } else {
                messageContent = getString(R.string.download_error_un);
            }
            message = String.format(Locale.getDefault(), message, messageContent);
            showDialogToast(message);
        }

        @Override
        public void onProgress(int what, int progress, long fileCount) {
            LogUtils.e(progress + "..." + fileCount);
            updateProgress(progress);
        }

        @Override
        public void onFinish(int what, String filePath) {
            singleDownDialog.setProgress(100);
            LogUtils.e("onFinish");
            LogUtils.e("文件路径：" + filePath);
            LogUtils.e(FileUtil.getDecompressionDir(getThis()));
            try {
                ZipUtils.UnZipFolder(filePath, FileUtil.getDecompressionDir(getThis()) + appCartoon.name);

            } catch (Exception e) {
                showDialogToast(getString(R.string.zevf_error));
            }
            getHandler().postAtTime(new Runnable() {
                @Override
                public void run() {
                    singleDownDialog.dialogDismiss();
                }
            }, 500);

        }

        @Override
        public void onCancel(int what) {
        }

        private void updateProgress(int progress) {
            if (singleDownDialog.isShowing()) {
                singleDownDialog.setProgress(progress);
            }
        }
    };

    @Override
    protected void onDestroy() {
        // 暂停下载
        if (mDownloadRequest != null) {
            mDownloadRequest.cancel();
        }
        super.onDestroy();
    }

    private void showsingleDownDialog(String name) {
        if (singleDownDialog == null) {
            singleDownDialog = new SingleDownDialog(this, R.style.transparentFrameWindowStyle, name);
            singleDownDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    mDownloadRequest.cancel();
                }
            });
        }
        singleDownDialog.setProgress(0);
        singleDownDialog.setName(name);
        singleDownDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        singleDownDialog.show();
        dimActivity(singleDownDialog, 0.6f);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        appCartoon = (AppCartoon) intent.getSerializableExtra("data");
        path = intent.getStringExtra("path");
        name = intent.getStringExtra("name");
        code = intent.getIntExtra("code", 0);
        list = new ArrayList<View>();
        for (int x = 0; x < getFile(path); x++) {
            String path = FileUtil.getDecompressionDir(getThis()) +appCartoon.name+"/"+name + "/" + name + (x + 1) + ".jpg";

//            LogUtils.e(FileUtil.getDecompressionDir(getThis()) + name + "/" + name + (x + 1));
            View view = getView();
            ImageView img = (ImageView) view.findViewById(R.id.layout_imagefragment_iv);
            GlideHelper.loadImageRotated(getThis(), img, path,new RotateTransformation(getThis(), 90f));
            list.add(view);
        }
        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter();
        viewPager.setAdapter(myFragmentPagerAdapter);
        viewPager.setCurrentItem(0);
    }

    private int getFile(String path) {
        // get file list where the path has
        LogUtils.e(path);
        File file = new File(path);
        // get the folder list
        File[] array = file.listFiles();
        Log.e("getFile", "开始遍历..." + array.length);
        int size = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i].isFile()) {
                if (!array[i].isHidden()) {
                    LogUtils.e(array[i].getPath());
                    ++size;
                }
            }
        }
        LogUtils.e("结束遍历" + size);
        return size;
    }

    private View getView() {
        return View.inflate(getThis(), R.layout.layout_imagefragment, null);
    }

    class MyFragmentPagerAdapter extends PagerAdapter {

        //viewpager中的组件数量
        @Override
        public int getCount() {
            return list.size();
        }

        //滑动切换的时候销毁当前的组件
        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            ((ViewPager) container).removeView(list.get(position));
        }

        //每次滑动的时候生成的组件
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager) container).addView(list.get(position));
            return list.get(position);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }
    }
}
