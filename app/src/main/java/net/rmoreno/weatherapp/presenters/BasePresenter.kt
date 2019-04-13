package net.rmoreno.weatherapp.presenters

import java.lang.ref.WeakReference

abstract class BasePresenter<M, V> {
    var model: M? = null
    private var view: WeakReference<V>? = null

    fun setM(model: M?) {
        this.model = model

        if (setupDone()) {
            updateView()
        }
    }

    open fun bindView(view: V) {
        this.view = WeakReference(view)
    }

    open fun unbindView() {
        this.view = null
    }

    abstract fun updateView()

    fun view(): V? = if (view == null) null else view?.get()

    open fun setupDone(): Boolean {
        return view() != null && model != null
    }
}

