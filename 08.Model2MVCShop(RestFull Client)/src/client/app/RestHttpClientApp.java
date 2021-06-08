package client.app;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.model2.mvc.service.domain.User;



public class RestHttpClientApp {
	
	// main Method
	public static void main(String[] args) throws Exception{
		
		////////////////////////////////////////////////////////////////////////////////////////////
		// 주석을 하나씩 처리해가며 실습
		////////////////////////////////////////////////////////////////////////////////////////////
//		// 1.1 Http Get 방식 Request : JsonSimple lib 사용		
//		// 1.2 Http Get 방식 Request : CodeHaus lib 사용
//		RestHttpClientApp.getUserTest_JsonSimple();
//		RestHttpClientApp.getUserTest_Codehaus();
		

//		// 2.1 Http Post 방식 Request : JsonSimple lib 사용
//		// 1.2 Http Post 방식 Request : CodeHaus lib 사용
//		RestHttpClientApp.LoginTest_JsonSimple();
//		RestHttpClientApp.LoginTest_Codehaus();		
		
		
//		RestHttpClientApp.addUserTest_JsonSimple();
//		RestHttpClientApp.addUserTest_Codehaus();
		
//		RestHttpClientApp.updateUserTest_JsonSimple();
		RestHttpClientApp.updateUserTest_Codehaus();
		
//		RestHttpClientApp.getListUserTest_JsonSimple();
//		RestHttpClientApp.getListUserTest_Codehaus();
	
		
		
		
		
	}
	
	
//================================================================//
	//1.1 Http Protocol GET Request : JsonSimple 3rd party lib 사용
	public static void getUserTest_JsonSimple() throws Exception{
		
		// HttpClient : Http Protocol 의 client 추상화 
		HttpClient httpClient = new DefaultHttpClient();
		
		String url= 	"http://127.0.0.1:8080/user/json/getUser/admin";
				
		// HttpGet : Http Protocol 의 GET 방식 Request
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Accept", "application/json");
		httpGet.setHeader("Content-Type", "application/json");
		
		// HttpResponse : Http Protocol 응답 Message 추상화
		HttpResponse httpResponse = httpClient.execute(httpGet);
		
		//==> Response 확인
		System.out.println(httpResponse);
		System.out.println();

		//==> Response 중 entity(DATA) 확인
		HttpEntity httpEntity = httpResponse.getEntity();
		
		//==> InputStream 생성
		InputStream is = httpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
		
		System.out.println("[ Server 에서 받은 Data 확인 ] ");
		String serverData = br.readLine();
		System.out.println(serverData);
		
		//==> 내용읽기(JSON Value 확인)
		JSONObject jsonobj = (JSONObject)JSONValue.parse(serverData);
		System.out.println(jsonobj);
	}
	
	
	//1.2 Http Protocol GET Request : JsonSimple + codehaus 3rd party lib 사용
	public static void getUserTest_Codehaus() throws Exception{
		
		// HttpClient : Http Protocol 의 client 추상화 
		HttpClient httpClient = new DefaultHttpClient();
		
		String url= 	"http://127.0.0.1:8080/user/json/getUser/admin";

		// HttpGet : Http Protocol 의 GET 방식 Request
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Accept", "application/json");
		httpGet.setHeader("Content-Type", "application/json");
		
		// HttpResponse : Http Protocol 응답 Message 추상화
		HttpResponse httpResponse = httpClient.execute(httpGet);
		
		//==> Response 확인
		System.out.println(httpResponse);
		System.out.println();

		//==> Response 중 entity(DATA) 확인
		HttpEntity httpEntity = httpResponse.getEntity();
		
		//==> InputStream 생성
		InputStream is = httpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
		
		//==> 다른 방법으로 serverData 처리 
		//System.out.println("[ Server 에서 받은 Data 확인 ] ");
		//String serverData = br.readLine();
		//System.out.println(serverData);
		
		//==> API 확인 : Stream 객체를 직접 전달 
		JSONObject jsonobj = (JSONObject)JSONValue.parse(br);
		System.out.println(jsonobj);
	
		ObjectMapper objectMapper = new ObjectMapper();
		 User user = objectMapper.readValue(jsonobj.toString(), User.class);
		 System.out.println(user);
	}
//================================================================//	
	
//================================================================//
	//2.1 Http Protocol POST Request : FromData 전달 / JsonSimple 3rd party lib 사용
	public static void LoginTest_JsonSimple() throws Exception{
		
		// HttpClient : Http Protocol 의 client 추상화 
		HttpClient httpClient = new DefaultHttpClient();
		
		String url = "http://127.0.0.1:8080/user/json/login";
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");
		
		//[ 방법 1 : String 사용]
//			String data =  "{\"userId\":\"admin\",\"password\":\"1234\"}";
//			HttpEntity httpEntity01 = new StringEntity(data,"utf-8");
		
		//[ 방법 2 : JSONObject 사용]
		JSONObject json = new JSONObject();
		json.put("userId", "admin");
		json.put("password", "1234");
		HttpEntity httpEntity01 = new StringEntity(json.toString(),"utf-8");

		httpPost.setEntity(httpEntity01);
		HttpResponse httpResponse = httpClient.execute(httpPost);
		
		//==> Response 확인
		System.out.println(httpResponse);
		System.out.println();

		//==> Response 중 entity(DATA) 확인
		HttpEntity httpEntity = httpResponse.getEntity();
		
		//==> InputStream 생성
		InputStream is = httpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
		
		System.out.println("[ Server 에서 받은 Data 확인 ] ");
		String serverData = br.readLine();
		System.out.println(serverData);
		
		//==> 내용읽기(JSON Value 확인)
		JSONObject jsonobj = (JSONObject)JSONValue.parse(serverData);
		System.out.println(jsonobj);
	
	}
	
	
	//2.2 Http Protocol POST 방식 Request : FromData전달 
	//==> JsonSimple + codehaus 3rd party lib 사용
	public static void LoginTest_Codehaus() throws Exception{
		
		// HttpClient : Http Protocol 의 client 추상화 
		HttpClient httpClient = new DefaultHttpClient();
		
		String url = "http://127.0.0.1:8080/user/json/login";
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");
		
//		//[ 방법 1 : String 사용]
//		String data =  "{\"userId\":\"admin\",\"password\":\"1234\"}";
//		HttpEntity httpEntity01 = new StringEntity(data,"utf-8");
	
//		//[ 방법 2 : JSONObject 사용]
//		JSONObject json = new JSONObject();
//		json.put("userId", "admin");
//		json.put("password", "1234");
//		HttpEntity httpEntity01 = new StringEntity(json.toString(),"utf-8");
		
		//[ 방법 3 : codehaus 사용]
		User user01 =  new User();
		user01.setUserId("admin");
		user01.setPassword("1234");
		ObjectMapper objectMapper01 = new ObjectMapper();
		//Object ==> JSON Value 로 변환
		String jsonValue = objectMapper01.writeValueAsString(user01);
		HttpEntity httpEntity01 = new StringEntity(jsonValue,"utf-8");
		
		httpPost.setEntity(httpEntity01);
		HttpResponse httpResponse = httpClient.execute(httpPost);
		
		//==> Response 확인
		System.out.println(httpResponse);
		System.out.println();

		//==> Response 중 entity(DATA) 확인
		HttpEntity httpEntity = httpResponse.getEntity();
		
		//==> InputStream 생성
		InputStream is = httpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
		
		//==> 다른 방법으로 serverData 처리 
		//System.out.println("[ Server 에서 받은 Data 확인 ] ");
		//String serverData = br.readLine();
		//System.out.println(serverData);
		
		//==> API 확인 : Stream 객체를 직접 전달 
		JSONObject jsonobj = (JSONObject)JSONValue.parse(br);
		System.out.println(jsonobj);
	
		ObjectMapper objectMapper = new ObjectMapper();
		 User user = objectMapper.readValue(jsonobj.toString(), User.class);
		 System.out.println(user);
		 
		 
	}	
	
	
	public static void addUserTest_JsonSimple() throws Exception{
		
		
		HttpClient httpClient  = new DefaultHttpClient();
		
		String url = "http://127.0.0.1:8080/user/json/addUser";
		
		HttpGet httpGet = new HttpGet(url);
		
		httpGet.setHeader("Accept", "application/json");
		httpGet.setHeader("Content-Type", "application/json");
		
		HttpResponse httpResponse = httpClient.execute(httpGet);
		
		System.out.println(httpResponse);
		System.out.println();
		
		HttpEntity httpEntity = httpResponse.getEntity();
		
		InputStream is = httpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		
		String serverData = br.readLine();
		System.out.println(serverData);
		
		//JSONObject json = (JSONObject)JSONValue.parse(br);
		//System.out.println(json);
	}
	
	public static void addUserTest_Codehaus() throws Exception{
		
		
		HttpClient httpClient = new DefaultHttpClient();
		
		String url = "http://127.0.0.1:8080/user/json/addUser";
		
		HttpPost httpPost = new HttpPost(url);
		
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");
		
		User user01 = new User();
		
		user01.setUserId("jsonTest");
		user01.setUserName("jsonTest");
		user01.setPassword("1234");
		user01.setSsn("1234561234567");
		user01.setEmail("abc@def.com");
		user01.setPhone("010-1234-5678");
		
		ObjectMapper om = new ObjectMapper();
		String jsonValue = om.writeValueAsString(user01);
		
		HttpEntity httpEntity01 = new StringEntity(jsonValue, "UTF-8");
		httpPost.setEntity(httpEntity01);
		HttpResponse httpResponse = httpClient.execute(httpPost);
		
		System.out.println(httpResponse);
		System.out.println();
		
		HttpEntity httpEntity = httpResponse.getEntity();
		InputStream is = httpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
		
//		System.out.println("br.readLine()");
//		String serverData = br.readLine();
//		System.out.println(serverData);
		
		System.out.println("JSONValue.parse(br)");
		JSONObject json = (JSONObject)JSONValue.parse(br);
		System.out.println(json);
		
		
		ObjectMapper objectMapper = new ObjectMapper();
		 User user = objectMapper.readValue(json.toString(), User.class);
		 System.out.println(user);
	}
	
	
	
	public static void updateUserTest_JsonSimple() throws Exception{
		
		HttpClient httpClient = new DefaultHttpClient();
		
		String url = "http://127.0.0.1:8080/user/json/updateUser/jsonTest";
		
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Accept", "application/json");
		httpGet.setHeader("Content-Type", "application/json");
		
		HttpResponse httpResponse = httpClient.execute(httpGet);
		
		System.out.println(httpResponse);
		System.out.println();
		
		HttpEntity httpEntity = httpResponse.getEntity();
		
		InputStream is = httpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		
		JSONObject json = (JSONObject)JSONValue.parse(br);
		System.out.println("JSONObject:"+json);		
	}
	//==>오류있음.
	public static void updateUserTest_Codehaus() throws Exception{
		
		String url = "http://127.0.0.1:8080/user/json/updateUser";
		
		HttpClient httpClient = new DefaultHttpClient();
		
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");
		
		User user01 = new User();
		
		user01.setUserId("jsonTest");
		user01.setUserName("jsonTEST");
		user01.setEmail("abc@def.com");
		user01.setPhone("010-1234-5678");
		user01.setAddr("서울");
		
		ObjectMapper om = new ObjectMapper();
		String jsonValue = om.writeValueAsString(user01);
		
		HttpEntity httpEntity01 = new StringEntity(jsonValue, "UTF-8");
		httpPost.setEntity(httpEntity01);
		HttpResponse httpResponse = httpClient.execute(httpPost);
		
		System.out.println(httpResponse);
		System.out.println();
		
		HttpEntity httpEntity = httpResponse.getEntity();
		InputStream is = httpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
		
		System.out.println("JSONValue.parse(br)");
		JSONObject json = (JSONObject)JSONValue.parse(br);
		System.out.println(json);
		
		 User user = om.readValue(json.toString(), User.class);
		 System.out.println(user);
	}
	
	
	public static void getListUserTest_JsonSimple() throws Exception{

		HttpClient httpClient = new DefaultHttpClient();

		String url = "http://127.0.0.1:8080/user/json/listUser";

		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");

		JSONObject json = new JSONObject();
		json.put("currentPage", 1);
		json.put("pageSize", 3);
		json.put("searchCondition", "1");
		json.put("searchKeyword", "");
		json.put("startRowNum", 1);
		json.put("endRowNum", 3);
		

		HttpEntity  httpEntity01 = new StringEntity(json.toString(), "utf-8");

		httpPost.setEntity(httpEntity01);

		HttpResponse httpResponse = httpClient.execute(httpPost);

		System.out.println(httpResponse);
		System.out.println();

		HttpEntity httpEntity  = httpResponse.getEntity();

		InputStream is = httpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		
		String serverData = br.readLine();
		
		System.out.println(serverData);
		
		JSONObject jsonobj = (JSONObject)JSONValue.parse(serverData);
		System.out.println(jsonobj);
		
		
	}
	
	public static void getListUserTest_Codehaus() throws Exception{

		HttpClient httpClient = new DefaultHttpClient();

		String url = "http://127.0.0.1:8080/user/json/listUser";

		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");

		JSONObject json = new JSONObject();
		json.put("currentPage", 1);
		json.put("pageSize", 3);
		json.put("searchCondition", "1");
		json.put("searchKeyword", "");
		json.put("startRowNum", 1);
		json.put("endRowNum", 3);
		

		HttpEntity  httpEntity01 = new StringEntity(json.toString(), "utf-8");

		httpPost.setEntity(httpEntity01);

		HttpResponse httpResponse = httpClient.execute(httpPost);

		System.out.println(httpResponse);
		System.out.println();

		HttpEntity httpEntity  = httpResponse.getEntity();

		InputStream is = httpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		
		String serverData = br.readLine();
		
		System.out.println(serverData);
		
		//JSONObject jsonobj = (JSONObject)JSONValue.parse(serverData);
		//System.out.println(jsonobj);
		
		ObjectMapper objectMapper = new ObjectMapper();
		 Map<String, Object> map = objectMapper.readValue(serverData, new TypeReference<Map<String, Object>>(){});
		 System.out.println(map);
		
		
	}
	
	
}