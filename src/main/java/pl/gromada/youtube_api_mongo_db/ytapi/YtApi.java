package pl.gromada.youtube_api_mongo_db.ytapi;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;

import java.io.IOException;

public class YtApi {

    public static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    public static final JsonFactory JSON_FACTORY     = new JacksonFactory();
    private static final String YOUTUBE_API_APPLICATION = "YouTube Channels";

    public static YouTube getService() {
        return new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY,
                new HttpRequestInitializer() {
                    public void initialize(HttpRequest request) throws IOException {
                        // intentionally left empty
                    }
                }).setApplicationName(YOUTUBE_API_APPLICATION).build();
    }
}
