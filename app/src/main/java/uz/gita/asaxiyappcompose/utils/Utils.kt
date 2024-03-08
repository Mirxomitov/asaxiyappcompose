package uz.gita.asaxiyappcompose.utils

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


fun <T> Flow<T>.launchLifecycle(lifecycle: Lifecycle, scope: LifecycleCoroutineScope, block: (T) -> Unit) {
    onEach { block(it) }
        .flowWithLifecycle(lifecycle)
        .launchIn(scope)
}

fun logger(msg : String, tag : String = "TTT") {
    Log.d(tag, msg)
}