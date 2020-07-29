package com.ankang.acme;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by XdaTk on 2014/12/21.
 * <p/>
 * HTTP请求工具类
 */
public class HTTPSend {
    /**
     * 发送get请求
     *
     * @param url  请求地址
     * @param list 请求参数
     * @return 请求结果
     *
     * @throws IOException
     */
    public static String sendGet(String url, List<HTTPParam> list) throws IOException {
        StringBuffer buffer = new StringBuffer(); //用来拼接参数
        StringBuffer result = new StringBuffer(); //用来接受返回值
        URL httpUrl = null; //HTTP URL类 用这个类来创建连接
        URLConnection connection = null; //创建的http连接
        BufferedReader bufferedReader = null; //接受连接受的参数
        //如果存在参数，我们才需要拼接参数 类似于 localhost/index.html?a=a&b=b
        if (list!=null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                buffer.append(list.get(i).getKey()).append("=").append(URLEncoder.encode(list.get(i).getValue(), "utf-8"));
                //如果不是最后一个参数，不需要添加&
                if ((i + 1) < list.size()) {
                    buffer.append("&");
                }
            }
            url = url + "?" + buffer.toString();
        }
        //创建URL
        httpUrl = new URL(url);
        //建立连接
        connection = httpUrl.openConnection();
        connection.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        connection.setRequestProperty("connection", "keep-alive");
        connection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:34.0) Gecko/20100101 Firefox/34.0");
        connection.connect();
        //接受连接返回参数
        bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            result.append(line);
        }
        bufferedReader.close();
        return result.toString();
    }
    /**
     * 发送Post请求
     *
     * @param url  请求地址
     * @param list 请求参数
     *
     * @return 请求结果
     *
     * @throws IOException
     */
    public static String sendPost(String url, List<HTTPParam> list) throws IOException {
        StringBuffer buffer = new StringBuffer(); //用来拼接参数
        StringBuffer result = new StringBuffer(); //用来接受返回值
        URL httpUrl = null; //HTTP URL类 用这个类来创建连接
        URLConnection connection = null; //创建的http连接
        PrintWriter printWriter = null;
        BufferedReader bufferedReader; //接受连接受的参数
        //创建URL
        httpUrl = new URL(url);
        //建立连接
        connection = httpUrl.openConnection();
        connection.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        connection.setRequestProperty("connection", "keep-alive");
        connection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.87 Safari/537.36");
        connection.setDoOutput(true);
        connection.setDoInput(true);
        printWriter = new PrintWriter(connection.getOutputStream());
        if (list!=null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                buffer.append(list.get(i).getKey()).append("=").append(URLEncoder.encode(list.get(i).getValue(), "utf-8"));
                //如果不是最后一个参数，不需要添加&
                if ((i + 1) < list.size()) {
                    buffer.append("&");
                }
            }
        }
        printWriter.print(buffer.toString());
        printWriter.flush();
        connection.connect();
        //接受连接返回参数
        /*bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            result.append(line);
        }*/
        //bufferedReader.close();
        return result.toString();
    }

    /**
     * Version:
     * Random: 1E 4A 3D 2E 25 90 0A F3 92 11 AB 6F 58 0A 94 8C 52 C7 99 96 7D F0 FF CF 02 BF 3C 2B C0 E7 56 95
     * "Time": 1994/8/2 星期二 3:16:46
     * SessionID: 7F 3B 14 1A 82 F6 1C 11 A4 E4 E1 97 0E 88 94 9D 9D CB C2 41 B3 51 CE EA F0 C4 92 0B C1 77 76 C4
     * Extensions:
     * 	grease (0x5a5a)	empty
     * 	server_name	content-autofill.googleapis.com
     * 	extended_master_secret	empty
     * 	renegotiation_info	00
     * 	supported_groups	grease [0x3a3a], x25519 [0x1d], secp256r1 [0x17], secp384r1 [0x18]
     * 	ec_point_formats	uncompressed [0x0]
     * 	SessionTicket	empty
     * 	ALPN		h2, http/1.1
     * 	status_request	OCSP - Implicit Responder
     * 	signature_algs	ecdsa_secp256r1_sha256, rsa_pss_rsae_sha256, rsa_pkcs1_sha256, ecdsa_secp384r1_sha384, rsa_pss_rsae_sha384, rsa_pkcs1_sha384, rsa_pss_rsae_sha512, rsa_pkcs1_sha512, rsa_pkcs1_sha1
     * 	SignedCertTimestamp (RFC6962)	empty
     * 	key_share	00 29 3A 3A 00 01 00 00 1D 00 20 FA 51 8E 9A 83 50 42 F7 F7 71 14 59 4C 75 E6 9B 0F C1 F9 A0 5C 29 3C F8 8E AD FA 30 13 98 62 63
     * 	psk_key_exchange_modes	01 01
     * 	supported_versions	grease [0x4a4a], Tls1.3, Tls1.2, Tls1.1
     * 	0x001b		02 00 02
     * 	grease (0xa0a)	00
     * 	padding		185 null bytes
     * Ciphers:
     * 	[FAFA]	Unrecognized cipher - See https://www.iana.org/assignments/tls-parameters/
     * 	[1301]	TLS_AES_128_GCM_SHA256
     * 	[1302]	TLS_AES_256_GCM_SHA384
     * 	[1303]	TLS_CHACHA20_POLY1305_SHA256
     * 	[C02B]	TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256
     * 	[C02F]	TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256
     * 	[C02C]	TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384
     * 	[C030]	TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384
     * 	[CCA9]	TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256
     * 	[CCA8]	TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256
     * 	[C013]	TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA
     * 	[C014]	TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA
     * 	[009C]	TLS_RSA_WITH_AES_128_GCM_SHA256
     * 	[009D]	TLS_RSA_WITH_AES_256_GCM_SHA384
     * 	[002F]	TLS_RSA_WITH_AES_128_CBC_SHA
     * 	[0035]	TLS_RSA_WITH_AES_256_CBC_SHA
     * 	[000A]	SSL_RSA_WITH_3DES_EDE_SHA
     *
     * Compression:
     * 	[00]	NO_COMPRESSION
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        String url = "http://content-autofill.googleapis.com:443";
        List<HTTPParam> ls = new ArrayList<>();
        HTTPParam p1 = new HTTPParam("Version","3.3 (TLS/1.2)");
        HTTPParam p2 = new HTTPParam("Random","1E 4A 3D 2E 25 90 0A F3 92 11 AB 6F 58 0A 94 8C 52 C7 99 96 7D F0 FF CF 02 BF 3C 2B C0 E7 56 "+Math.random()*100);
        HTTPParam p3 = new HTTPParam("Time","1994/8/2 星期二 3:16:46");
        HTTPParam p6 = new HTTPParam("Ciphers","[FAFA]\tUnrecognized cipher - See https://www.iana.org/assignments/tls-parameters/\n" +
                "\t[1301]\tTLS_AES_128_GCM_SHA256\n" +
                "\t[1302]\tTLS_AES_256_GCM_SHA384\n" +
                "\t[1303]\tTLS_CHACHA20_POLY1305_SHA256\n" +
                "\t[C02B]\tTLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256\n" +
                "\t[C02F]\tTLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256\n" +
                "\t[C02C]\tTLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384\n" +
                "\t[C030]\tTLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384\n" +
                "\t[CCA9]\tTLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256\n" +
                "\t[CCA8]\tTLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256\n" +
                "\t[C013]\tTLS_ECDHE_RSA_WITH_AES_128_CBC_SHA\n" +
                "\t[C014]\tTLS_ECDHE_RSA_WITH_AES_256_CBC_SHA\n" +
                "\t[009C]\tTLS_RSA_WITH_AES_128_GCM_SHA256\n" +
                "\t[009D]\tTLS_RSA_WITH_AES_256_GCM_SHA384\n" +
                "\t[002F]\tTLS_RSA_WITH_AES_128_CBC_SHA\n" +
                "\t[0035]\tTLS_RSA_WITH_AES_256_CBC_SHA\n" +
                "\t[000A]\tSSL_RSA_WITH_3DES_EDE_SHA\n");
        HTTPParam p8 = new HTTPParam("Compression","[00]\tNO_COMPRESSION\n");
        HTTPParam p5 = new HTTPParam("Extensions","\tgrease (0x5a5a)\tempty\n" +
                "\tserver_name\tcontent-autofill.googleapis.com\n" +
                "\textended_master_secret\tempty\n" +
                "\trenegotiation_info\t00\n" +
                "\tsupported_groups\tgrease [0x3a3a], x25519 [0x1d], secp256r1 [0x17], secp384r1 [0x18]\n" +
                "\tec_point_formats\tuncompressed [0x0]\n" +
                "\tSessionTicket\tempty\n" +
                "\tALPN\t\th2, http/1.1\n" +
                "\tstatus_request\tOCSP - Implicit Responder\n" +
                "\tsignature_algs\tecdsa_secp256r1_sha256, rsa_pss_rsae_sha256, rsa_pkcs1_sha256, ecdsa_secp384r1_sha384, rsa_pss_rsae_sha384, rsa_pkcs1_sha384, rsa_pss_rsae_sha512, rsa_pkcs1_sha512, rsa_pkcs1_sha1\n" +
                "\tSignedCertTimestamp (RFC6962)\tempty\n" +
                "\tkey_share\t00 29 3A 3A 00 01 00 00 1D 00 20 FA 51 8E 9A 83 50 42 F7 F7 71 14 59 4C 75 E6 9B 0F C1 F9 A0 5C 29 3C F8 8E AD FA 30 13 98 62 63\n" +
                "\tpsk_key_exchange_modes\t01 01\n" +
                "\tsupported_versions\tgrease [0x4a4a], Tls1.3, Tls1.2, Tls1.1\n" +
                "\t0x001b\t\t02 00 02\n" +
                "\tgrease (0xa0a)\t00\n" +
                "\tpadding\t\t185 null bytes\n" +
                "Ciphers: \n" +
                "\t[FAFA]\tUnrecognized cipher - See https://www.iana.org/assignments/tls-parameters/\n" +
                "\t[1301]\tTLS_AES_128_GCM_SHA256\n" +
                "\t[1302]\tTLS_AES_256_GCM_SHA384\n" +
                "\t[1303]\tTLS_CHACHA20_POLY1305_SHA256\n" +
                "\t[C02B]\tTLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256\n" +
                "\t[C02F]\tTLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256\n" +
                "\t[C02C]\tTLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384\n" +
                "\t[C030]\tTLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384\n" +
                "\t[CCA9]\tTLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256\n" +
                "\t[CCA8]\tTLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256\n" +
                "\t[C013]\tTLS_ECDHE_RSA_WITH_AES_128_CBC_SHA\n" +
                "\t[C014]\tTLS_ECDHE_RSA_WITH_AES_256_CBC_SHA\n" +
                "\t[009C]\tTLS_RSA_WITH_AES_128_GCM_SHA256\n" +
                "\t[009D]\tTLS_RSA_WITH_AES_256_GCM_SHA384\n" +
                "\t[002F]\tTLS_RSA_WITH_AES_128_CBC_SHA\n" +
                "\t[0035]\tTLS_RSA_WITH_AES_256_CBC_SHA\n" +
                "\t[000A]\tSSL_RSA_WITH_3DES_EDE_SHA\n");

        ls.add(p1);
        ls.add(p2);
        ls.add(p3);
        ls.add(p5);
        ls.add(p6);
        ls.add(p8);
        String s = sendPost(url, ls);
        System.out.println(s);
    }
}