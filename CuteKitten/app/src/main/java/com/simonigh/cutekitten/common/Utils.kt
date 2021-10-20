package com.simonigh.cutekitten.common

import android.content.Context
import android.content.res.Resources
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import okio.Okio
import java.io.File

fun saveStringToFile(content: String, file: File): Completable {
    return Completable.create { emitter ->
        val sink = Okio.buffer(Okio.sink(file))
        sink.write(content.toByteArray())
        sink.close()
        emitter.onComplete()
    }
}

fun readStringFromFile(file: File): Single<String> {
    return Single.create { emitter ->
        val buffer = Okio.buffer(Okio.source(file))
        emitter.onSuccess(buffer.readUtf8())
    }
}

fun getStringFromFileAsset(resId: Int, context: Context): String? {
    return try {
        context.resources.openRawResource(resId).reader().use { it.readText() }
    } catch (exception: Resources.NotFoundException) {
        null
    }
}