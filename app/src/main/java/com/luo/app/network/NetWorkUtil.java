package com.luo.app.network;

import com.google.gson.Gson;
import com.luo.app.network.resultBean.FolderInfo;
import com.luo.app.network.resultBean.Password;

import org.json.JSONObject;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * desc :
 * create by 公子赓
 * on 2023/2/18 22:22
 */
public class NetWorkUtil {

    private static final String IPAddress = "";

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

    /**
     * get
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
                if (null != response.body()) {
                    String result = response.body().string();
                    password = gson.fromJson(result, Password.class);
                }
            }
            return password;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

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
                if (null != response.body()) {
                    String result = response.body().string();
                    folderInfo = gson.fromJson(result, FolderInfo.class);
                }
            }
            return folderInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

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
                if (null != response.body()) {
                    result = Objects.requireNonNull(response.body()).string();
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
