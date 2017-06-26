package net.rmoreno.weatherapp;


public class WeatherInteractor {

    WeatherRepository repo = new WeatherRepository();
    WeatherCallback callback;

    public WeatherInteractor(WeatherRepository repo) {
        this.repo = repo;
    }

    public void getWeatherData() {
        repo.getCurrentWeather()
    }
    public interface WeatherCallback {
        public void onWeatherRetrieved();
    }
}
