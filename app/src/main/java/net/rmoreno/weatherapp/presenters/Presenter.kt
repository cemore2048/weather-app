package net.rmoreno.weatherapp.presenters

import java.lang.ref.WeakReference

abstract class Presenter<M, V> {

    private var model: M? = null
    private var view: WeakReference<V>? = null

    fun setModel(model: M) {
        this.model = model

        if (setupDone()) {
            updateView()
        }
    }

    fun bindView(view: V) {
        this.view = WeakReference(view)
    }

    abstract fun updateView()

    fun view(): V? {
        return if (view == null) null else view?.get()
    }

    private fun setupDone(): Boolean {
        return this.view != null && this.model != null
    }

    open fun unbindView() {
        this.view = null
    }
}
