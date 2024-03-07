package uz.gita.asaxiyappcompose.screens.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    fun onEventDispatcher(intent: MainIntent) {

    }
}

sealed interface MainIntent {

}