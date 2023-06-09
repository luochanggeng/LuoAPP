package com.luo.app.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.luo.app.network.resultBean.ContentList;
import com.luo.app.network.resultBean.FolderInfo;
import com.luo.app.network.resultBean.Password;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * desc :
 * create by 公子赓
 * on 2023/2/18 22:22
 */
public class NetWorkUtil {

    private static String IPAddress ;

    /**
     * 该类的实列
     */
    private static NetWorkUtil instance;

    protected static OkHttpClient okHttpClient;

    private final Gson gson;

    /**
     * 指定传输的数据类型
     */
    private final MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

    /**
     * 私有化无参构造方法
     */
    private NetWorkUtil() {
        gson = new Gson();
    }

    public static NetWorkUtil getInstance() {
        if (instance == null) {
            synchronized (NetWorkUtil.class) {
                if (instance == null) {
                    instance = new NetWorkUtil();
                }
            }
        }
        return instance;
    }

    static {
        okHttpClient = new OkHttpClient.Builder()
                .readTimeout(10000, TimeUnit.MILLISECONDS)
                .connectTimeout(10000, TimeUnit.MILLISECONDS)
                .writeTimeout(3000, TimeUnit.MILLISECONDS)
//                .addInterceptor(new Interceptor() {
//                    @NotNull
//                    @Override
//                    public Response intercept(@NotNull Chain chain) throws IOException {
//                        Request.Builder builder = chain.request().newBuilder()
//                                .header("", "");
//                        Request build = builder.build();
//                        return chain.proceed(build);
//                    }
//                })
                .build();
    }

    public String getIPAddress(){
        return IPAddress;
    }

    public void setIpAddress(String ip){
        IPAddress = ip;
    }

    /**
     *  查询登陆密码
     */
    public Password queryPassWord() {
        try {
            String url = IPAddress + "password.json";
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
            final Call call = okHttpClient.newCall(request);
            Response response = call.execute();
            Password password = null;
            if (response.isSuccessful()) {
                ResponseBody body = response.body();
                if (null != body) {
                    String result = body.string();
                    password = gson.fromJson(result, Password.class);
                }
            }
            return password;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询栏目信息
     * @return FolderInfo
     */
    public FolderInfo queryFolderInfo() {
        try {
            String url = IPAddress + "folderList.json";
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
            final Call call = okHttpClient.newCall(request);
            Response response = call.execute();
            FolderInfo folderInfo = null;
            if (response.isSuccessful()) {
                ResponseBody body = response.body();
                if (null != body) {
                    String result = body.string();
                    folderInfo = gson.fromJson(result, FolderInfo.class);
                }
            }
            return folderInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询栏目下内容列表信息
     * @param folderCode 栏目码
     * @return ContentList
     */
    public ContentList queryContentList(String folderCode){
        try {
            String url = IPAddress + folderCode + "/detailsList.json";
            Log.i("zhang", "queryContentList url = " + url);
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
            final Call call = okHttpClient.newCall(request);
            Response response = call.execute();
            ContentList contentList = null;
            if (response.isSuccessful()) {
                ResponseBody body = response.body();
                if (null != body) {
                    String result = body.string();
                    contentList = gson.fromJson(result, ContentList.class);
                }
            }
            return contentList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * post 请求
     */
    public String post() {
        try {
            String url = IPAddress + "";
            JSONObject object = new JSONObject();
            object.put("", "");
            RequestBody body = RequestBody.create(mediaType, object.toString());
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            final Call call = okHttpClient.newCall(request);
            Response response = call.execute();
            String result = null;
            if (response.isSuccessful()) {
                ResponseBody responseBody = response.body();
                if (null != responseBody) {
                    result = responseBody.string();
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
