package net.rmoreno.weatherapp.presenters;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public interface  Presenter {

    void resume();

    void pause();

    void destroy();

    public interface WeatherCallback {
        void onWeatherRetrieved(Response response);

        void onFailure(Request request, IOException e);
    }
}
