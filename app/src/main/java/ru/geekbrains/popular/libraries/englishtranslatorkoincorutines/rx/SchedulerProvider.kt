package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.rx

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

//In the sake of testing
class SchedulerProvider : ISchedulerProvider {

    // Для корректной работы AndroidSchedulers.mainThread() нужно добавить следующие библиотеки:
    // implement "io.reactivex.rxjava2:rxandroid:2.1.1"
    // implement "io.reactivex.rxjava3:rxjava:3.0.0-RC3"
    override fun ui(): Scheduler = AndroidSchedulers.mainThread()
    override fun io(): Scheduler = Schedulers.io()
}