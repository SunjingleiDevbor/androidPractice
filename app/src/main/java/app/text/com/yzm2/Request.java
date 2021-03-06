package app.text.com.yzm2;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by SJL on 2016/8/1.
 */
public class Request {
    private Context mContext;
    private Handler handler;

    public Request(Context context,Handler handler){
        this.mContext=context;
        this.handler=handler;
    }

    //发送验证码
    public void SendMobileCode(final String phone){
        new Thread(){
            @Override
            public void run() {
                Message msg = Message.obtain();
                super.run();
                String data="&phone"+phone;
                Log.e("phone=", phone);
                String resultDate= NetUtil.getResponse("http://hq.xiaocool.net/index.php?" +
                        "g=apps&m=index&a=SendMobileCode",data);
                Log.e("result_data=",resultDate);

                try {
                    JSONObject json = new JSONObject(resultDate);
                    msg.what=0x001;
                    msg.obj=json;
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();

    }

    //注册
    public void Register(final String name,final String phoNum, final String password, final String verifyCode){
        new Thread(){
            Message msg=Message.obtain();

            @Override
            public void run() {
                String data = "&name=" + name + "&avatar=1234.jpg" + "&phone=" + phoNum + "&password=" + password +
                        "&code=" + verifyCode + "&devicestate=2";
                String resultData=NetUtil.getResponse("http://hq.xiaocool.net/index.php?"+"g=apps&m=index&a=AppRegister",data);
                Log.e("successful", resultData);
                try {
                    JSONObject obj = new JSONObject(resultData);
                    msg.what = 0x002;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //登录
    public void Login(final String phone, final String password){
        new Thread(){
            Message msg = Message.obtain();
            @Override
            public void run() {
                super.run();
                String data = "&phone"+phone+"&password"+password;
                String resultData=NetUtil.getResponse("http://hq.xiaocool.net/index.php?g=apps&m=index&a=applogin"
                        ,data);
                try {
                    JSONObject obj = new JSONObject(resultData);
                    msg.what = 0x003;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                    msg.obj="";
                } finally {
                    handler.sendMessage(msg);
                }

            }
        }.start();
    }
}
