//package sample;
//
//import okhttp3.*;
//
//import java.io.IOException;
//
//public class HttpPost {
//
//    public static final MediaType JSON
//            = MediaType.parse("application/json; charset=utf-8");
//
//    private OkHttpClient client = new OkHttpClient();
//
//    public String post(String url, String json) throws IOException {
//        RequestBody body = RequestBody.create(JSON, json);
//        Request request = new Request.Builder()
//                .url(url)
//                .post(body)
//                .build();
//        try (Response response = client.newCall(request).execute()) {
//            if (response.code() != 200)
//            {
//                return null;
//            }
//            return response.body().string();
//        }
//    }
//}