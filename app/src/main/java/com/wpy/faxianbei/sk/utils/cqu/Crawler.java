package com.wpy.faxianbei.sk.utils.cqu;

import com.google.gson.Gson;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Crawler {

	/*
	 * 模拟登录POST参数 1. __VIEWSTATE=dDw1OTgzNjYzMjM7dDw7bDxpPDE%2BO2k8Mz47aTw1Pjs%
	 * 2BO2w8dDxwPGw8VGV4dDs%2BO2w86YeN5bqG5aSn5a2mOz4%2BOzs%
	 * 2BO3Q8cDxsPFRleHQ7PjtsPFw8c2NyaXB0IHR5cGU9InRleHQvamF2YXNjcmlwdCJcPgpcPCEtLQpmdW5jdGlvbiBvcGVuV2luTG9nKHRoZVVSTCx3LGgpewp2YXIgVGZvcm0scmV0U3RyXDsKZXZhbCgiVGZvcm09J3dpZHRoPSIrdysiLGhlaWdodD0iK2grIixzY3JvbGxiYXJzPW5vLHJlc2l6YWJsZT1ubyciKVw7CnBvcD13aW5kb3cub3Blbih0aGVVUkwsJ3dpbktQVCcsVGZvcm0pXDsgLy9wb3AubW92ZVRvKDAsNzUpXDsKZXZhbCgiVGZvcm09J2RpYWxvZ1dpZHRoOiIrdysicHhcO2RpYWxvZ0hlaWdodDoiK2grInB4XDtzdGF0dXM6bm9cO3Njcm9sbGJhcnM9bm9cO2hlbHA6bm8nIilcOwppZih0eXBlb2YocmV0U3RyKSE9J3VuZGVmaW5lZCcpIGFsZXJ0KHJldFN0cilcOwp9CmZ1bmN0aW9uIHNob3dMYXkoZGl2SWQpewp2YXIgb2JqRGl2ID0gZXZhbChkaXZJZClcOwppZiAob2JqRGl2LnN0eWxlLmRpc3BsYXk9PSJub25lIikKe29iakRpdi5zdHlsZS5kaXNwbGF5PSIiXDt9CmVsc2V7b2JqRGl2LnN0eWxlLmRpc3BsYXk9Im5vbmUiXDt9Cn0KZnVuY3Rpb24gc2VsVHllTmFtZSgpewogIGRvY3VtZW50LmFsbC50eXBlTmFtZS52YWx1ZT1kb2N1bWVudC5hbGwuU2VsX1R5cGUub3B0aW9uc1tkb2N1bWVudC5hbGwuU2VsX1R5cGUuc2VsZWN0ZWRJbmRleF0udGV4dFw7Cn0KZnVuY3Rpb24gd2luZG93Lm9ubG9hZCgpewoJdmFyIHNQQz13aW5kb3cubmF2aWdhdG9yLnVzZXJBZ2VudCt3aW5kb3cubmF2aWdhdG9yLmNwdUNsYXNzK3dpbmRvdy5uYXZpZ2F0b3IuYXBwTWlub3JWZXJzaW9uKycgU046TlVMTCdcOwp0cnl7ZG9jdW1lbnQuYWxsLnBjSW5mby52YWx1ZT1zUENcO31jYXRjaChlcnIpe30KdHJ5e2RvY3VtZW50LmFsbC50eHRfZHNkc2RzZGpramtqYy5mb2N1cygpXDt9Y2F0Y2goZXJyKXt9CnRyeXtkb2N1bWVudC5hbGwudHlwZU5hbWUudmFsdWU9ZG9jdW1lbnQuYWxsLlNlbF9UeXBlLm9wdGlvbnNbZG9jdW1lbnQuYWxsLlNlbF9UeXBlLnNlbGVjdGVkSW5kZXhdLnRleHRcO31jYXRjaChlcnIpe30KfQpmdW5jdGlvbiBvcGVuV2luRGlhbG9nKHVybCxzY3IsdyxoKQp7CnZhciBUZm9ybVw7CmV2YWwoIlRmb3JtPSdkaWFsb2dXaWR0aDoiK3crInB4XDtkaWFsb2dIZWlnaHQ6IitoKyJweFw7c3RhdHVzOiIrc2NyKyJcO3Njcm9sbGJhcnM9bm9cO2hlbHA6bm8nIilcOwp3aW5kb3cuc2hvd01vZGFsRGlhbG9nKHVybCwxLFRmb3JtKVw7Cn0KZnVuY3Rpb24gb3Blbldpbih0aGVVUkwpewp2YXIgVGZvcm0sdyxoXDsKdHJ5ewoJdz13aW5kb3cuc2NyZWVuLndpZHRoLTEwXDsKfWNhdGNoKGUpe30KdHJ5ewpoPXdpbmRvdy5zY3JlZW4uaGVpZ2h0LTMwXDsKfWNhdGNoKGUpe30KdHJ5e2V2YWwoIlRmb3JtPSd3aWR0aD0iK3crIixoZWlnaHQ9IitoKyIsc2Nyb2xsYmFycz1ubyxzdGF0dXM9bm8scmVzaXphYmxlPXllcyciKVw7CnBvcD1wYXJlbnQud2luZG93Lm9wZW4odGhlVVJMLCcnLFRmb3JtKVw7CnBvcC5tb3ZlVG8oMCwwKVw7CnBhcmVudC5vcGVuZXI9bnVsbFw7CnBhcmVudC5jbG9zZSgpXDt9Y2F0Y2goZSl7fQp9CmZ1bmN0aW9uIGNoYW5nZVZhbGlkYXRlQ29kZShPYmopewp2YXIgZHQgPSBuZXcgRGF0ZSgpXDsKT2JqLnNyYz0iLi4vc3lzL1ZhbGlkYXRlQ29kZS5hc3B4P3Q9IitkdC5nZXRNaWxsaXNlY29uZHMoKVw7Cn0KXFwtLVw
	 * %2BClw8L3NjcmlwdFw%2BOz4%2BOzs%2BO3Q8O2w8aTwxPjs%2BO2w8dDw7bDxpPDA%
	 * 2BOz47bDx0PHA8bDxUZXh0Oz47bDxcPG9wdGlvbiB2YWx1ZT0nU1RVJyB1c3JJRD0n5a2m5Y%
	 * 2B3J1w%2B5a2m55SfXDwvb3B0aW9uXD4KXDxvcHRpb24gdmFsdWU9J1RFQScgdXNySUQ9J%
	 * 2BW4kOWPtydcPuaVmeW4iFw8L29wdGlvblw%
	 * 2BClw8b3B0aW9uIHZhbHVlPSdTWVMnIHVzcklEPSfluJDlj7cnXD7nrqHnkIbkurrlkZhcPC9vcHRpb25cPgpcPG9wdGlvbiB2YWx1ZT0nQURNJyB1c3JJRD0n5biQ5Y
	 * %2B3J1w%2B6Zeo5oi357u05oqk5ZGYXDwvb3B0aW9uXD4KOz4%2BOzs%2BOz4%2BOz4%2BOz4
	 * %2BOz7p2B9lkx%2BYq%2Fjf62i%2BiqicmZx%2Fxg%3D%3D 
	 * 2.__VIEWSTATEGENERATOR=CAA0A5A7 
	 * 3. Sel_Type=STU (用户类别) 4.
	 * txt_dsdsdsdjkjkjc=******** (学号) 5. txt_dsdfdfgfouyy=****** (密码) 6.
	 * txt_ysdsdsdskgf= 7. pcInfo= 8. typeName= 9. aerererdsdxcxdfgfg=
	 * 10.efdfdfuuyyuuckjg=2773C8CAA1F93D462B7B9D13E810EA
	 */
	final static String viewState = "dDw1OTgzNjYzMjM7dDw7bDxpPDE%2BO2k8Mz47aTw1Pjs%2BO2w8dDxwPGw8VGV4dDs%2BO2w86YeN5bqG5aSn5a2mOz4%2BOzs%2BO3Q8cDxsPFRleHQ7PjtsPFw8c2NyaXB0IHR5cGU9InRleHQvamF2YXNjcmlwdCJcPgpcPCEtLQpmdW5jdGlvbiBvcGVuV2luTG9nKHRoZVVSTCx3LGgpewp2YXIgVGZvcm0scmV0U3RyXDsKZXZhbCgiVGZvcm09J3dpZHRoPSIrdysiLGhlaWdodD0iK2grIixzY3JvbGxiYXJzPW5vLHJlc2l6YWJsZT1ubyciKVw7CnBvcD13aW5kb3cub3Blbih0aGVVUkwsJ3dpbktQVCcsVGZvcm0pXDsgLy9wb3AubW92ZVRvKDAsNzUpXDsKZXZhbCgiVGZvcm09J2RpYWxvZ1dpZHRoOiIrdysicHhcO2RpYWxvZ0hlaWdodDoiK2grInB4XDtzdGF0dXM6bm9cO3Njcm9sbGJhcnM9bm9cO2hlbHA6bm8nIilcOwppZih0eXBlb2YocmV0U3RyKSE9J3VuZGVmaW5lZCcpIGFsZXJ0KHJldFN0cilcOwp9CmZ1bmN0aW9uIHNob3dMYXkoZGl2SWQpewp2YXIgb2JqRGl2ID0gZXZhbChkaXZJZClcOwppZiAob2JqRGl2LnN0eWxlLmRpc3BsYXk9PSJub25lIikKe29iakRpdi5zdHlsZS5kaXNwbGF5PSIiXDt9CmVsc2V7b2JqRGl2LnN0eWxlLmRpc3BsYXk9Im5vbmUiXDt9Cn0KZnVuY3Rpb24gc2VsVHllTmFtZSgpewogIGRvY3VtZW50LmFsbC50eXBlTmFtZS52YWx1ZT1kb2N1bWVudC5hbGwuU2VsX1R5cGUub3B0aW9uc1tkb2N1bWVudC5hbGwuU2VsX1R5cGUuc2VsZWN0ZWRJbmRleF0udGV4dFw7Cn0KZnVuY3Rpb24gd2luZG93Lm9ubG9hZCgpewoJdmFyIHNQQz13aW5kb3cubmF2aWdhdG9yLnVzZXJBZ2VudCt3aW5kb3cubmF2aWdhdG9yLmNwdUNsYXNzK3dpbmRvdy5uYXZpZ2F0b3IuYXBwTWlub3JWZXJzaW9uKycgU046TlVMTCdcOwp0cnl7ZG9jdW1lbnQuYWxsLnBjSW5mby52YWx1ZT1zUENcO31jYXRjaChlcnIpe30KdHJ5e2RvY3VtZW50LmFsbC50eHRfZHNkc2RzZGpramtqYy5mb2N1cygpXDt9Y2F0Y2goZXJyKXt9CnRyeXtkb2N1bWVudC5hbGwudHlwZU5hbWUudmFsdWU9ZG9jdW1lbnQuYWxsLlNlbF9UeXBlLm9wdGlvbnNbZG9jdW1lbnQuYWxsLlNlbF9UeXBlLnNlbGVjdGVkSW5kZXhdLnRleHRcO31jYXRjaChlcnIpe30KfQpmdW5jdGlvbiBvcGVuV2luRGlhbG9nKHVybCxzY3IsdyxoKQp7CnZhciBUZm9ybVw7CmV2YWwoIlRmb3JtPSdkaWFsb2dXaWR0aDoiK3crInB4XDtkaWFsb2dIZWlnaHQ6IitoKyJweFw7c3RhdHVzOiIrc2NyKyJcO3Njcm9sbGJhcnM9bm9cO2hlbHA6bm8nIilcOwp3aW5kb3cuc2hvd01vZGFsRGlhbG9nKHVybCwxLFRmb3JtKVw7Cn0KZnVuY3Rpb24gb3Blbldpbih0aGVVUkwpewp2YXIgVGZvcm0sdyxoXDsKdHJ5ewoJdz13aW5kb3cuc2NyZWVuLndpZHRoLTEwXDsKfWNhdGNoKGUpe30KdHJ5ewpoPXdpbmRvdy5zY3JlZW4uaGVpZ2h0LTMwXDsKfWNhdGNoKGUpe30KdHJ5e2V2YWwoIlRmb3JtPSd3aWR0aD0iK3crIixoZWlnaHQ9IitoKyIsc2Nyb2xsYmFycz1ubyxzdGF0dXM9bm8scmVzaXphYmxlPXllcyciKVw7CnBvcD1wYXJlbnQud2luZG93Lm9wZW4odGhlVVJMLCcnLFRmb3JtKVw7CnBvcC5tb3ZlVG8oMCwwKVw7CnBhcmVudC5vcGVuZXI9bnVsbFw7CnBhcmVudC5jbG9zZSgpXDt9Y2F0Y2goZSl7fQp9CmZ1bmN0aW9uIGNoYW5nZVZhbGlkYXRlQ29kZShPYmopewp2YXIgZHQgPSBuZXcgRGF0ZSgpXDsKT2JqLnNyYz0iLi4vc3lzL1ZhbGlkYXRlQ29kZS5hc3B4P3Q9IitkdC5nZXRNaWxsaXNlY29uZHMoKVw7Cn0KXFwtLVw%2BClw8L3NjcmlwdFw%2BOz4%2BOzs%2BO3Q8O2w8aTwxPjs%2BO2w8dDw7bDxpPDA%2BOz47bDx0PHA8bDxUZXh0Oz47bDxcPG9wdGlvbiB2YWx1ZT0nU1RVJyB1c3JJRD0n5a2m5Y%2B3J1w%2B5a2m55SfXDwvb3B0aW9uXD4KXDxvcHRpb24gdmFsdWU9J1RFQScgdXNySUQ9J%2BW4kOWPtydcPuaVmeW4iFw8L29wdGlvblw%2BClw8b3B0aW9uIHZhbHVlPSdTWVMnIHVzcklEPSfluJDlj7cnXD7nrqHnkIbkurrlkZhcPC9vcHRpb25cPgpcPG9wdGlvbiB2YWx1ZT0nQURNJyB1c3JJRD0n5biQ5Y%2B3J1w%2B6Zeo5oi357u05oqk5ZGYXDwvb3B0aW9uXD4KOz4%2BOzs%2BOz4%2BOz4%2BOz4%2BOz7p2B9lkx%2BYq%2Fjf62i%2BiqicmZx%2Fxg%3D%3D";
	final static String viewStateGenerator = "CAA0A5A7";
	// String selType = "STU";
	// String username = "";
	// String userPassword = "";
	String txt_ysdsdsdskgf;
	String pcInfo;
	String typeName;
	String aerererdsdxcxdfgfg;
	
	// String md5Encrypted = "";

	static String sURL = "http://202.202.1.176:8080/_data/index_login.aspx";
	static String vURL = "http://202.202.1.176:8080/znpk/Pri_StuSel_rpt.aspx";
	static String nURL = "http://202.202.1.176:8080/znpk/Pri_StuSel_rpt.aspx";

	static String inforURL = "http://202.202.1.176:8080/xsxj/Stu_MyInfo_RPT.aspx";

	static String responseCookie;

	Map<String, Object> mapFinal = new HashMap<String, Object>();
	String md5Path = "./js/md5.js";
	public String setMD5EnEncrypted(String path){
		this.md5Path = path;
		return this.md5Path;
	}

	/*
	 * 学生登录 参数1 selType 登录类型 STU 参数2 username 用户名 参数3 用户密码
	 */

	public String stuLogin(String selType, String username, String userPassword)
			throws NoSuchMethodException, FileNotFoundException {
		StringBuilder stringBuilder = new StringBuilder();
		StringBuffer stringBuffer = new StringBuffer();
		String md5Encrypted = "";
		String schoolId = "10611";
		md5Encrypted = MD5.md5EncryptedUtil(schoolId, username, userPassword, md5Path);
		try {
			URL dstUrl = new URL(sURL);
			HttpURLConnection connection;

			connection = (HttpURLConnection) dstUrl.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");

			stringBuffer.append("__VIEWSTATE=" + this.viewState);
			stringBuffer.append("&__VIEWSTATEGENERATOR=" + this.viewStateGenerator);
			stringBuffer.append("&Sel_Type=" + selType);
			stringBuffer.append("&txt_dsdsdsdjkjkjc=" + username);
			stringBuffer.append("&txt_dsdfdfgfouyy=" + userPassword);
			stringBuffer.append("&txt_ysdsdsdskgf=");
			stringBuffer.append("&pcInfo=");
			stringBuffer.append("&typeName=");
			stringBuffer.append("&aerererdsdxcxdfgfg=");
			stringBuffer.append("&efdfdfuuyyuuckjg=" + md5Encrypted);

			// 提交参数
			connection.setRequestProperty("Content-Length", String.valueOf(stringBuffer.toString().length()));
			connection.connect();

			OutputStream outputStream = connection.getOutputStream();
			outputStream.write(stringBuffer.toString().getBytes());
			outputStream.close();

			// 获取输入流
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(connection.getInputStream(), "gb2312"));
			// 取到所用的Cookie
			responseCookie = connection.getHeaderField("Set-Cookie");

			// 取返回的页面
			String line = bufferedReader.readLine();
			while (line != null) {
				stringBuilder.append(line);
				line = bufferedReader.readLine();
			}

		} catch (IOException e) {
			System.out.println("连接失败");
		}

		return stringBuffer.toString();
	}

	String getLoginCookie() {
		return responseCookie;
	}

	/*
	 * 参数1 academicYear 学年 2016代表2016学年 参数2 学期 0代表第一学期 1代表第二学期
	 *
	 */
	public String getLessonsHtml(String academicYear, String term) throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		StringBuffer stringBuffer = new StringBuffer();
		URL lessonUrl;
		try {
			lessonUrl = new URL(vURL);
			HttpURLConnection resumeConnection = (HttpURLConnection) lessonUrl.openConnection();

			resumeConnection.setDoInput(true);
			resumeConnection.setDoOutput(true);
			resumeConnection.setRequestMethod("POST");

			String parameter = academicYear + term;

			stringBuffer.append("Sel_XNXQ=" + parameter);
			stringBuffer.append("&rad=" + "on");
			stringBuffer.append("&px=" + "0");
			resumeConnection.setRequestProperty("Content-Length", String.valueOf(stringBuffer.toString().length()));

			if (responseCookie != null) {
				// 发送cookie信息上去，以表明自己的身份，否则会被认为没有权限
				resumeConnection.setRequestProperty("Cookie", responseCookie);
			}

			resumeConnection.connect();
			OutputStream outputStream = resumeConnection.getOutputStream();

			outputStream.write(stringBuffer.toString().getBytes());
			outputStream.close();

			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(resumeConnection.getInputStream(), "gb2312"));

			// 取返回的页面
			String line = bufferedReader.readLine();
			while (line != null) {
				stringBuilder.append(line);
				line = bufferedReader.readLine();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		}
		String result = parserLessonHtml(stringBuilder.toString());
		return result;
	}

	/*
	 * 参数1 academicYear 学年 2016代表2016学年 参数2 学期 0代表第一学期 1代表第二学期
	 */
/*	public String getlessonScoresHtml(String academicYear, String term) throws Exception {

		StringBuilder stringBuilder = new StringBuilder();
		StringBuffer stringBuffer = new StringBuffer();
		URL lessonScoresUrl;
		try {
			lessonScoresUrl = new URL(vURL);
			HttpURLConnection resumeConnection = (HttpURLConnection) lessonScoresUrl.openConnection();

			resumeConnection.setDoInput(true);
			resumeConnection.setDoOutput(true);
			resumeConnection.setRequestMethod("POST");

			String parameter = academicYear + term;

			// 学年 学期 原始成绩 检索 按学期 主修/辅修
			stringBuffer.append("sel_xn=" + academicYear);
			stringBuffer.append("&sel_xq=" + term);
			stringBuffer.append("&SJ=" + "0");
			stringBuffer.append("&btn_search=" + "检索");
			stringBuffer.append("&SelXNXQ=" + "2");
			stringBuffer.append("&zfx_flag=" + "0");
			stringBuffer.append("&zxf=" + "0");
			resumeConnection.setRequestProperty("Content-Length", String.valueOf(stringBuffer.toString().length()));

			if (responseCookie != null) {
				// 发送cookie信息上去，以表明自己的身份，否则会被认为没有权限
				resumeConnection.setRequestProperty("Cookie", responseCookie);
			}

			resumeConnection.connect();
			OutputStream outputStream = resumeConnection.getOutputStream();

			outputStream.write(stringBuffer.toString().getBytes());
			outputStream.close();

			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(resumeConnection.getInputStream(), "gb2312"));

			// 取返回的页面
			String line = bufferedReader.readLine();
			while (line != null) {
				stringBuilder.append(line);
				line = bufferedReader.readLine();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		}

		return stringBuilder.toString();
	}
*/
	public String getStuInforHtml() throws Exception {

		StringBuilder stringBuilder = new StringBuilder();
		StringBuffer stringBuffer = new StringBuffer();
		URL lessonScoresUrl;
		try {
			lessonScoresUrl = new URL(inforURL);
			HttpURLConnection resumeConnection = (HttpURLConnection) lessonScoresUrl.openConnection();

			resumeConnection.setDoInput(true);
			resumeConnection.setDoOutput(true);
			resumeConnection.setRequestMethod("POST");

			if (responseCookie != null) {
				// 发送cookie信息上去，以表明自己的身份，否则会被认为没有权限
				resumeConnection.setRequestProperty("Cookie", responseCookie);
			}

			resumeConnection.connect();
			OutputStream outputStream = resumeConnection.getOutputStream();

			outputStream.write(stringBuffer.toString().getBytes());
			outputStream.close();

			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(resumeConnection.getInputStream(), "gb2312"));

			// 取返回的页面
			String line = bufferedReader.readLine();
			while (line != null) {
				stringBuilder.append(line);
				line = bufferedReader.readLine();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		}

		String result = parserStuIforHtml(stringBuilder.toString());
		return result;
	}

	/*
	 * 解析成绩html文档 参数html字符串
	 */
	public String parserLessonHtml(String htmlString) {
		Document docHtml = Jsoup.parse(htmlString);
		String result;

		if (docHtml.select("TABLE").size() > 0) {
			Elements lessons = docHtml.select("TABLE").get(1).select("tbody>tr");
			Map<String, Object> mapLesson = new HashMap<String, Object>();
			List<Map<String, Object>> lessonList = new ArrayList<>();
			String lastTeacherString = "";
			String lastLessonString = "";

			for (int i = 0; i < lessons.size(); i++) {
				Elements lesson = lessons.get(i).getElementsByTag("td");
				Map<String, Object> mapLessons = new HashMap<String, Object>();

				String locationString = lesson.get(12).text();
				String periodString = lesson.get(11).text();
				String weekString = lesson.get(10).text();
				String teacherString = "";
				String lessonString = "";

				if (lesson.get(1).text().length() > 0) {
					lessonString = lesson.get(1).text().split("\\]")[1];

				} else {
					lessonString = lastLessonString;
				}

				if (lesson.get(9).text().length() > 0) {
					teacherString = lesson.get(9).text();

				} else {
					teacherString = lastTeacherString;
				}

				lastLessonString = lessonString;
				lastTeacherString = teacherString;

				mapLessons.put("lesson", lessonString);
				mapLessons.put("location", locationString);
				mapLessons.put("teacher", teacherString);
				mapLessons.put("week", weekString);
				mapLessons.put("day", periodString.substring(0, 1));
				mapLessons.put("time", periodString.split("\\[")[1].split("\\]")[0]);

				lessonList.add(mapLessons);
			}
			mapLesson.put("lessons", lessonList);

			if (docHtml.select("TABLE").size() == 4) {
				Elements lessonsExperiment = docHtml.select("TABLE").get(3).select("tbody>tr");
				List<Map<String, Object>> lessonExperimentList = new ArrayList<>();

				for (int i = 0; i < lessonsExperiment.size(); i++) {
					Elements lessonExperiment = lessonsExperiment.get(i).getElementsByTag("td");
					Map<String, Object> mapLessonsExperiment = new HashMap<String, Object>();

					String locationString = lessonExperiment.get(11).text();
					String periodString = lessonExperiment.get(10).text();
					String weekString = lessonExperiment.get(9).text();
					String teacherString = "";
					String lessonString = "";

					if (lessonExperiment.get(1).text().length() > 0) {
						lessonString = lessonExperiment.get(1).text().split("\\]")[1];

					} else {
						lessonString = lastLessonString;
					}

					if (lessonExperiment.get(7).text().length() > 0) {
						teacherString = lessonExperiment.get(7).text();

					} else {
						teacherString = lastTeacherString;
					}

					lastLessonString = lessonString;
					lastTeacherString = teacherString;

					mapLessonsExperiment.put("lesson", lessonString);
					mapLessonsExperiment.put("location", locationString);
					mapLessonsExperiment.put("teacher", teacherString);
					mapLessonsExperiment.put("week", weekString);
					mapLessonsExperiment.put("day", periodString.substring(0, 1));
					mapLessonsExperiment.put("time", periodString.split("\\[")[1].split("\\]")[0]);
					// mapLessonsExperiment.put("period", periodString);

					lessonExperimentList.add(mapLessonsExperiment);
				}
				mapLesson.put("experiments", lessonExperimentList);
			}
			mapFinal = mapLesson;
			Gson gson = new Gson();
			result = gson.toJson(mapLesson);
		} else {
			result = "No lesson message";
		}

		return result;
	}

	public String parserStuIforHtml(String htmlString) {
		Document docHtml = Jsoup.parse(htmlString);
		Elements stuInfors = docHtml.select("td");

		Map<String, Object> mapInfor = new HashMap<String, Object>();

		String studentId = stuInfors.get(2).text();
		String studentName = stuInfors.get(4).text();
		String studentGender = stuInfors.get(11).text();
		String studentBirthDay = stuInfors.get(15).text();
		String studentNation = stuInfors.get(17).text();
		String studentAcademy = stuInfors.get(109).text();
		String studentMajorAndClass = stuInfors.get(111).text();

		String studentMajor = studentMajorAndClass.substring(2, studentMajorAndClass.length() - 2);
		String studentClass = studentMajorAndClass.substring(studentMajorAndClass.length() - 2,
				studentMajorAndClass.length());

		mapInfor.put("studentId", studentId);
		mapInfor.put("studentName", studentName);
		mapInfor.put("studentGender", studentGender);
		mapInfor.put("studentBirthDay", studentBirthDay);
		mapInfor.put("studentNation", studentNation);
		mapInfor.put("studentAcademy", studentAcademy);
		mapInfor.put("studentMajor", studentMajor);
		mapInfor.put("studentClass", studentClass);

		Gson gson = new Gson();
		String result = gson.toJson(mapInfor);
		
		return result.toString();
	}


	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	public String queryLessons(int week) {
		Map<String, Object> mapLesson = new HashMap<String, Object>();
		Map<String, Object> mapTest = new HashMap<String, Object>();
		List listClassLessons = new ArrayList<>();
		List listClassExperiments = new ArrayList<>();
		ArrayList listItem = new ArrayList();
		mapLesson.putAll(mapFinal);
		
		listClassLessons = (List) mapLesson.get("lessons");
		listClassExperiments = (List) mapLesson.get("experiments");

		for (int i = 0; i < listClassLessons.size(); i++) {
			int[] containCheck = new int[20];
			for (int in = 0; in < containCheck.length; in++) {
				containCheck[in] = 0;
			}

			int containCheckIndex = 0;
			mapTest = (Map<String, Object>) listClassLessons.get(i);
			String weekNum = mapTest.get("week").toString();
			
			// mapTest.remove("week");
			if (weekNum.contains(",") || weekNum.contains("-")) {
				String[] weekNumSpilt = weekNum.split("\\,");
				for (int i1 = 0; i1 < weekNumSpilt.length; i1++) {
					if (weekNumSpilt[i1].contains("-")) {
						String[] single = weekNumSpilt[i1].split("\\-");

						int start = Integer.parseInt(single[0]);
						int end = Integer.parseInt(single[1]);
						for (int index = start; index <= end; index++) {
							containCheck[containCheckIndex] = index;
							containCheckIndex++;
						}

					} else {
						int weeknum = Integer.parseInt(weekNumSpilt[i1]);
						containCheck[containCheckIndex] = weeknum;
						containCheckIndex++;
					}
				}
			} else {
				int weeknum = Integer.parseInt(weekNum);
				containCheck[containCheckIndex] = weeknum;
				containCheckIndex++;
			}
			for (int in = 0; in < containCheck.length; in++) {
				if (containCheck[in] == week) {
					listItem.add(mapTest);
				}
			}

		}

		if (listClassExperiments != null) {
			for (int i = 0; i < listClassExperiments.size(); i++) {
				int[] containCheck = new int[20];
				for (int in = 0; in < containCheck.length; in++) {
					containCheck[in] = 0;
				}
				int containCheckIndex = 0;
				mapTest = (Map<String, Object>) listClassExperiments.get(i);
				String weekNum = mapTest.get("week").toString();
				int weekNumSToI = Integer.parseInt(weekNum);
				if (weekNumSToI == week) {
					listItem.add(mapTest);
				}
			}
		}
		Gson gson = new Gson();
		String result = gson.toJson(listItem);

		return result;
	}

	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	public String queryLessons(int week, int day) {
		Map<String, Object> mapLesson = new HashMap<String, Object>();
		Map<String, Object> mapTest = new HashMap<String, Object>();
		List listClassLessons = new ArrayList<>();
		List listClassExperiments = new ArrayList<>();
		ArrayList listItem = new ArrayList();
		mapLesson.putAll(mapFinal);
		listClassLessons = (List) mapLesson.get("lessons");
		listClassExperiments = (List) mapLesson.get("experiments");
		
		String dayNumCheck = "";
		switch (day) {
		case 1:
			dayNumCheck = "一";
			break;
		case 2:
			dayNumCheck = "二";
			break;
		case 3:
			dayNumCheck = "三";
			break;
		case 4:
			dayNumCheck = "四";
			break;
		case 5:
			dayNumCheck = "五";
			break;
		case 6:
			dayNumCheck = "六";
			break;
		case 7:
			dayNumCheck = "日";
			break;
		default:
			dayNumCheck = "一";
			break;

		}
		

		for (int i = 0; i < listClassLessons.size(); i++) {
			int[] containCheck = new int[20];
			for (int in = 0; in < containCheck.length; in++) {
				containCheck[in] = 0;
			}

			int containCheckIndex = 0;
			mapTest = (Map<String, Object>) listClassLessons.get(i);
			String weekNum = mapTest.get("week").toString();
			String dayNum = mapTest.get("day").toString();
			
			// mapTest.remove("week");
			if (dayNum.equals(dayNumCheck)) {
				if (weekNum.contains(",") || weekNum.contains("-")) {
					String[] weekNumSpilt = weekNum.split("\\,");
					for (int i1 = 0; i1 < weekNumSpilt.length; i1++) {
						if (weekNumSpilt[i1].contains("-")) {
							String[] single = weekNumSpilt[i1].split("\\-");

							int start = Integer.parseInt(single[0]);
							int end = Integer.parseInt(single[1]);
							for (int index = start; index <= end; index++) {
								containCheck[containCheckIndex] = index;
								containCheckIndex++;
							}

						} else {
							int weeknum = Integer.parseInt(weekNumSpilt[i1]);
							containCheck[containCheckIndex] = weeknum;
							containCheckIndex++;
						}
					}
				} else {
					int weeknum = Integer.parseInt(weekNum);
					containCheck[containCheckIndex] = weeknum;
					containCheckIndex++;
				}
				for (int in = 0; in < containCheck.length; in++) {
					if (containCheck[in] == week) {
						listItem.add(mapTest);
					}
				}

			}

		}
		if (listClassExperiments != null) {
			for (int i = 0; i < listClassExperiments.size(); i++) {
				
				int[] containCheck = new int[20];
				for (int in = 0; in < containCheck.length; in++) {
					containCheck[in] = 0;
				}
				int containCheckIndex = 0;
				mapTest = (Map<String, Object>) listClassExperiments.get(i);
				String weekNum = mapTest.get("week").toString();
				String dayNum = mapTest.get("week").toString();
				mapTest.remove("week");
				int weekNumSToI = Integer.parseInt(weekNum);
				if (weekNumSToI == week && dayNum.equals(dayNumCheck)) {
					listItem.add(mapTest);
				}
			}
		}

		Gson gson = new Gson();
		String result = gson.toJson(listItem);
		return result;
	}


/*	public String parserHtml(String htmlString) {
		Document docHtml = Jsoup.parse(htmlString);
		Element lessons = docHtml.select("TABLE").get(1);

		Document doc = Jsoup.parse(lessons.html());

		Elements lesson = doc.select("tbody>tr");

		System.out.println("ddd " + lesson.size());

		return null;
	}
*/
	public Crawler() {
	}
}
