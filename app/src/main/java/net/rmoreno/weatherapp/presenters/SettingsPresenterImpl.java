package net.rmoreno.weatherapp.presenters;

import net.rmoreno.weatherapp.SettingsInteractor;

public class SettingsPresenterImpl implements SettingsPresenter {

    private SettingsInteractor interactor;

    public SettingsPresenterImpl(SettingsInteractor interactor) {
        this.interactor = interactor;
    }
    @Override
    public void resume() {}

    @Override
    public void destroy() {}

    @Override
    public void pause() {}

    @Override
    public void updateSweather(int temperature) {
        interactor.updateSweather(temperature);
    }
}
