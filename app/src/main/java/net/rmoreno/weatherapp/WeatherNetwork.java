package net.rmoreno.weatherapp;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;

public class WeatherNetwork {

    public Observable<Response> getWeather(double lat, double lng, Callback callback) {
        String URL = "https://api.forecast.io/forecast/5530508d3568e57848d53bf10cfade1f/" + lat + "," + lng;
        final OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(URL)
                .build();


        return Observable.create(new ObservableOnSubscribe<Response>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Response> em) throws Exception {
                try {
                    Response response = client.newCall(request).execute();
                    em.onNext(response);
                    em.onComplete();
                } catch (IOException err) {
                    err.printStackTrace();
                    em.onError(err);
                }
            }
        });
    }
}
