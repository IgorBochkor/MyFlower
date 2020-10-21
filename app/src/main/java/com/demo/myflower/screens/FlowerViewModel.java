package com.demo.myflower.screens;
/* Created by Ihor Bochkor on 19.10.2020.
 */

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.demo.myflower.api.Api;
import com.demo.myflower.api.ApiService;
import com.demo.myflower.database.AppDatabase;
import com.demo.myflower.pojo.Flower;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class FlowerViewModel extends AndroidViewModel {
	private static AppDatabase db;
	private MutableLiveData<List<Flower>> flowers;
	private Api api;
	private CompositeDisposable disposable;
	private MutableLiveData<Throwable> errors;

	public FlowerViewModel(@NonNull Application application) {
		super(application);
		disposable = new CompositeDisposable();
		db = AppDatabase.getInstance(application);
		flowers = new MutableLiveData<>();
		errors = new MutableLiveData<>();
		loadData();
	}

	public MutableLiveData<Throwable> getErrors() {
		return errors;
	}

	public LiveData<List<Flower>> getFlowers() {
		return flowers;
	}

	public void clearErrors() {
		errors.setValue(null);
	}

	@SuppressWarnings("unchecked")
	private void insertFlowers(List<Flower> flowers) {
		clearFlowers();
		if (flowers != null && flowers.size() > 0) {
			db.flowerDao().insertAllFlowers(flowers);
		}
	}

	private void clearFlowers() {
		db.flowerDao().deleteAllFlowers();
	}

	private void loadData() {
		api = Api.getInstance();
		ApiService apiService = api.apiService();
		disposable.add(
			apiService
				.getFlower()
				.doOnNext(this::insertFlowers)
				.onErrorReturn(throwable -> db.flowerDao().getAllFlowers())
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(
					result -> {
						flowers.postValue(result);
					},
					throwable ->
					{
						errors.setValue(throwable);
					}
				)
		);
	}

	@Override
	protected void onCleared() {
		disposable.dispose();
		super.onCleared();
	}
}
