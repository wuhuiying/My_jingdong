package com.why.wuhuiying_fjindong.Utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 小慧莹 on 2017/12/26.
 */

public class OkHttpUtil {

    /**
     * 懒汉 安全 加同步
     * 私有的静态成员变量 只声明不创建
     * 私有的构造方法
     * 提供返回实例的静态方法
     */
    private static OkHttpClient okHttpClient = null;


    private OkHttpUtil() {
    }

    public static OkHttpClient getInstance() {
        if (okHttpClient == null) {
            //加同步安全
            synchronized (OkHttpUtil.class) {
                if (okHttpClient == null) {
                    //okhttp可以缓存数据....指定缓存路径
                    File sdcache = new File(Environment.getExternalStorageDirectory(), "cache");
                    //指定缓存大小
                    int cacheSize = 10 * 1024 * 1024;

                    okHttpClient = new OkHttpClient.Builder()//构建器
                            .connectTimeout(15, TimeUnit.SECONDS)//连接超时
                            .writeTimeout(20, TimeUnit.SECONDS)//写入超时
                            .readTimeout(20, TimeUnit.SECONDS)//读取超时

                            .addInterceptor((Interceptor) new CommonParamsInterceptor())//添加的是应用拦截器...公共参数
                            //.addNetworkInterceptor(new CacheInterceptor())//添加的网络拦截器

                            .cache(new Cache(sdcache.getAbsoluteFile(), cacheSize))//设置缓存
                            .build();
                }
            }

        }

        return okHttpClient;
    }

    /**
     * get请求
     * 参数1 url
     * 参数2 回调Callback
     */

    public static void doGet(String oldUrl, Callback callback) {




        //创建OkHttpClient请求对象
        OkHttpClient okHttpClient = getInstance();
        //创建Request
        Request request = new Request.Builder().url(oldUrl).build();
        //得到Call对象
        Call call = okHttpClient.newCall(request);
        //执行异步请求
        call.enqueue(callback);


    }

    /**
     * post请求
     * 参数1 url
     * 参数2 Map<String, String> params post请求的时候给服务器传的数据
     *      add..("","")
     *      add()
     */

    public static void doPost(String url, Map<String, String> params, Callback callback) {



        //创建OkHttpClient请求对象
        OkHttpClient okHttpClient = getInstance();
        //3.x版本post请求换成FormBody 封装键值对参数

        FormBody.Builder builder = new FormBody.Builder();
        //遍历集合
        for (String key : params.keySet()) {
            builder.add(key, params.get(key));

        }


        //创建Request
        Request request = new Request.Builder().url(url).post(builder.build()).build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);

    }

    /**
     * post请求上传文件....包括图片....流的形式传任意文件...
     * 参数1 url
     * file表示上传的文件
     * fileName....文件的名字,,例如aaa.jpg
     * params ....传递除了file文件 其他的参数放到map集合
     *
     */
    public static void uploadFile(String url, File file, String fileName,Map<String,String> params) {
        //创建OkHttpClient请求对象
        OkHttpClient okHttpClient = getInstance();

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        //参数
        if (params != null){
            for (String key : params.keySet()){
                builder.addFormDataPart(key,params.get(key));
            }
        }
        //文件...参数name指的是请求路径中所接受的参数...如果路径接收参数键值是fileeeee,此处应该改变
        builder.addFormDataPart("file",fileName, RequestBody.create(MediaType.parse("application/octet-stream"),file));

        //构建
        MultipartBody multipartBody = builder.build();

        //创建Request
        Request request = new Request.Builder().url(url).post(multipartBody).build();

        //得到Call
        Call call = okHttpClient.newCall(request);
        //执行请求
        call.enqueue(new Callback() {


            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("upload",e.getLocalizedMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //上传成功回调 目前不需要处理
                if (response.isSuccessful()){
                    String s = response.body().string();
                    Log.e("upload","上传--"+s);
                }
            }
        });

    }

    /**
     * Post请求发送JSON数据....{"name":"zhangsan","pwd":"123456"}
     * 参数一：请求Url
     * 参数二：请求的JSON
     * 参数三：请求回调
     */
    public static void doPostJson(String url, String jsonParams, Callback callback) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonParams);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        Call call = getInstance().newCall(request);
        call.enqueue(callback);

    }

    /**
     * 下载文件 以流的形式把apk写入的指定文件 得到file后进行安装
     * 参数er：请求Url
     * 参数san：保存文件的文件夹....download
     */
    public static void download(final Activity context, final String url, final String saveDir) {
        Request request = new Request.Builder().url(url).build();
        Call call = getInstance().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //com.orhanobut.logger.Logger.e(e.getLocalizedMessage());
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    is = response.body().byteStream();//以字节流的形式拿回响应实体内容
                    //apk保存路径
                    final String fileDir = isExistDir(saveDir);
                    //文件
                    File file = new File(fileDir, getNameFromUrl(url));

                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }

                    fos.flush();

                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "下载成功:" + fileDir + "," + getNameFromUrl(url), Toast.LENGTH_SHORT).show();
                        }
                    });

                    //apk下载完成后 调用系统的安装方法
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                    context.startActivity(intent);


                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (is != null) is.close();
                    if (fos != null) fos.close();


                }
            }
        });

    }

    /**
     * 判断下载目录是否存在......并返回绝对路径
     *
     * @param saveDir
     * @return
     * @throws IOException
     */
    public static String isExistDir(String saveDir) throws IOException {
        // 下载位置
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            File downloadFile = new File(Environment.getExternalStorageDirectory(), saveDir);
            if (!downloadFile.mkdirs()) {
                downloadFile.createNewFile();
            }
            String savePath = downloadFile.getAbsolutePath();
            Log.e("savePath", savePath);
            return savePath;
        }
        return null;
    }

    /**
     * @param url
     * @return 从下载连接中解析出文件名
     */
    private static String getNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    /**
     * 公共参数拦截器
     */
    private static class CommonParamsInterceptor implements Interceptor{

        //拦截的方法
        @Override
        public Response intercept(Chain chain) throws IOException {

            //获取到请求
            Request request = chain.request();
            //获取请求的方式
            String method = request.method();
            //获取请求的路径
            String oldUrl = request.url().toString();

            Log.e("---拦截器",request.url()+"---"+request.method()+"--"+request.header("User-agent"));

            //要添加的公共参数...map
            Map<String,String> map = new HashMap<>();
            map.put("source","android");

            if ("GET".equals(method)){
                // 1.http://www.baoidu.com/login                --------？ key=value&key=value
                // 2.http://www.baoidu.com/login?               --------- key=value&key=value
                // 3.http://www.baoidu.com/login?mobile=11111    -----&key=value&key=value

                StringBuilder stringBuilder = new StringBuilder();//创建一个stringBuilder

                stringBuilder.append(oldUrl);

                if (oldUrl.contains("?")){
                    //?在最后面....2类型
                    if (oldUrl.indexOf("?") == oldUrl.length()-1){

                    }else {
                        //3类型...拼接上&
                        stringBuilder.append("&");
                    }
                }else {
                    //不包含? 属于1类型,,,先拼接上?号
                    stringBuilder.append("?");
                }

                //添加公共参数....
                for (Map.Entry<String,String> entry: map.entrySet()) {
                    //拼接
                    stringBuilder.append(entry.getKey())
                            .append("=")
                            .append(entry.getValue())
                            .append("&");
                }

                //删掉最后一个&符号
                if (stringBuilder.indexOf("&") != -1){
                    stringBuilder.deleteCharAt(stringBuilder.lastIndexOf("&"));
                }

                String newUrl = stringBuilder.toString();//新的路径

                //拿着新的路径重新构建请求
                request = request.newBuilder()
                        .url(newUrl)
                        .build();


            }else if ("POST".equals(method)){
                //先获取到老的请求的实体内容
                RequestBody oldRequestBody = request.body();//....之前的请求参数,,,需要放到新的请求实体内容中去

                //如果请求调用的是上面doPost方法
                if (oldRequestBody instanceof FormBody){
                    FormBody oldBody = (FormBody) oldRequestBody;

                    //构建一个新的请求实体内容
                    FormBody.Builder builder = new FormBody.Builder();
                    //1.添加老的参数
                    for (int i=0;i<oldBody.size();i++){
                        builder.add(oldBody.name(i),oldBody.value(i));
                    }
                    //2.添加公共参数
                    for (Map.Entry<String,String> entry:map.entrySet()) {

                        builder.add(entry.getKey(),entry.getValue());
                    }

                    FormBody newBody = builder.build();//新的请求实体内容

                    //构建一个新的请求
                    request = request.newBuilder()
                            .url(oldUrl)
                            .post(newBody)
                            .build();
                }


            }


            Response response = chain.proceed(request);

            return response;
        }
    }

    /**
     * 网络缓存的拦截器......注意在这里更改cache-control头是很危险的,一般客户端不进行更改,,,,服务器端直接指定
     *
     * 没网络取缓存的时候,一般都是在数据库或者sharedPerfernce中取出来的
     *
     *
     *
     */
    /*private static class CacheInterceptor implements Interceptor{

        @Override
        public Response intercept(Chain chain) throws IOException {
            //老的响应
            Response oldResponse = chain.proceed(chain.request());

            *//*if (NetUtils.isNetworkConnected(DashApplication.getAppContext())){
                int maxAge = 120; // 在线缓存在2分钟内可读取

                return oldResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            }else {
                int maxStale = 60 * 60 * 24 * 14; // 离线时缓存保存2周
                return oldResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }*//*

        }
    }*/
}
