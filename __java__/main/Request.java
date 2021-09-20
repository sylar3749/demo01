package __java__.main;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodySubscribers;

public class Request {
    public static Response GET(String uri, String... headers) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri)).headers(headers).build();
        try {
            System.out.println("Sending HTTP Request:");
            System.out.println("Method: GET");
            System.out.println("URL: " + uri);
            HttpResponse<byte[]> httprep = client.send(request, responseInfo -> BodySubscribers.ofByteArray());
            String responseCode = httprep.statusCode() + "";
            String responseBody = responseBodyToString(httprep);
            Response response = new Response(responseCode, responseBody);
            System.out.println("Response Code: " + response.code);
            System.out.println("Response Body: " + response.body + "\n");
            return response;
        } catch (IOException | InterruptedException e) {
            System.out.println("HTTP Request failed to sent with: " + e.getCause().toString() + "\n");
            return null;
        }
    }

    public static Response POST(String uri, String body, String... headers) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri)).headers(headers)
                .POST(BodyPublishers.ofString(body)).build();
        try {
            System.out.println("Sending HTTP Request:");
            System.out.println("Method: POST");
            System.out.println("URL: " + uri);
            HttpResponse<byte[]> httprep = client.send(request, responseInfo -> BodySubscribers.ofByteArray());
            String responseCode = httprep.statusCode() + "";
            String responseBody = responseBodyToString(httprep);
            Response response = new Response(responseCode, responseBody);
            System.out.println("Response Code: " + response.code);
            System.out.println("Response Body: " + response.body + "\n");
            return response;
        } catch (IOException | InterruptedException e) {
            System.out.println("HTTP Request failed to sent with: " + e.getCause().toString() + "\n");
            return null;
        }
    }

    private static String responseBodyToString(HttpResponse<byte[]> response) {
        byte[] body = response.body();
        return new String(body);
    }
}
