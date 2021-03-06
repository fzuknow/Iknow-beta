package http;

/**
 * Created by laixl on 2017/11/12.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by laixl on 2017/11/4.
 */

public class http {
    public  static  String doPostRequest(String path, Object content){
        PrintWriter out = null;
        InputStream in=null;
        StringBuffer buffer=null;
       // BufferedReader in = null;
        String result="";//返回结果

        try {
            System.out.println("要发送的信息是："+content+",路径是:"+path);
            URL url = new URL(NetUtil.PATH_BASE+path);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();

            httpURLConnection.setRequestMethod("POST");
            // 设置请求的超时时间
            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.setConnectTimeout(5000);

            // 设置请求的头
            httpURLConnection.setRequestProperty("Connection", "keep-alive");
            // 设置请求的头
            httpURLConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            // 设置请求的头
           /* httpURLConnection.setRequestProperty("Content-Length",
                    String.valueOf(data.getBytes().length));*/
            // 设置请求的头
            httpURLConnection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");

            //这两个参数必须加
            httpURLConnection.setDoInput(true); // 发送POST请求必须设置允许输出
            httpURLConnection.setDoOutput(true);// 发送POST请求必须设置允许输入

            httpURLConnection.connect();

            out = new PrintWriter(httpURLConnection.getOutputStream());
            //在输出流中写入参数
            out.print(content);
            out.flush();

            if(httpURLConnection.getResponseCode() == 200){
//                in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(),"utf-8"));
//                String line;
                in=httpURLConnection.getInputStream();
                BufferedReader br=new BufferedReader(new InputStreamReader(in));
                String str=null;
                buffer=new StringBuffer();
                while((str=br.readLine())!=null){
                    buffer.append(str);
                }
                in.close();
                br.close();
            }
            System.out.println("服务器返回的结果是："+buffer.toString());
            return buffer.toString();


        } catch (MalformedURLException e) {
            System.out.println("URL异常");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IO异常");
            e.printStackTrace();
        }finally {
            try{
                if(out!=null)
                    out.close();
                if(in!=null)
                    in.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }



}

