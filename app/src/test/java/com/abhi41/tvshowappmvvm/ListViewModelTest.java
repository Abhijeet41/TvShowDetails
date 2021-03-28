package com.abhi41.tvshowappmvvm;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.abhi41.tvshowappmvvm.Repositary.TVShowDetailsRepository;
import com.abhi41.tvshowappmvvm.network.ApiInterface;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.Executor;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.annotations.NonNull;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

public class ListViewModelTest {

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @Mock
    ApiInterface apiService;

    @InjectMocks
    TVShowDetailsRepository tvShowDetailsRepository = new TVShowDetailsRepository();

    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getCountriesSuccess()
    {

    }

    @Before
    public void setupRxSchedulers()
    {
        Scheduler immediate = new Scheduler() {
            @NonNull
            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(runnable -> runnable.run(),true);
            }
        };

        RxJavaPlugins.setInitNewThreadSchedulerHandler(scheduler -> immediate);
        RxAndroidPlugins.setMainThreadSchedulerHandler(scheduler -> immediate);
    }
}
