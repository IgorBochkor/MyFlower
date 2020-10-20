package com.demo.myflower.screens;
/* Created by Ihor Bochkor on 19.10.2020.
 */

import android.app.Application;
import android.os.AsyncTask;
import android.widget.Toast;

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
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class FlowerViewModel extends AndroidViewModel {
    private static AppDatabase db;
    private LiveData<List<Flower>>  flowers;
    private Api api;
    private Disposable disposable;
    private MutableLiveData<Throwable> errors;

    public MutableLiveData<Throwable> getErrors() {
        return errors;
    }

    public LiveData<List<Flower>> getFlowers() {
        return flowers;
    }

    public void clearErrors(){
        errors.setValue(null);
    }

    public FlowerViewModel(@NonNull Application application) {
        super(application);
        db = AppDatabase.getInstance(application);
        flowers = db.flowerDao().getAllFlowers();
        errors = new MutableLiveData<>();
    }

    @SuppressWarnings("unchecked")
    private void insertFlowers(List<Flower> flowers){
        new InsertFlowers().execute(flowers);
    }

    public static class InsertFlowers extends AsyncTask<List<Flower>,Void,Void>{
        @SafeVarargs
        @Override
        protected final Void doInBackground(List<Flower>... lists) {
            if (lists != null && lists.length > 0){
                db.flowerDao().insertAllFlowers(lists[0]);
            }
            return null;
        }
    }

    private void deleteFlowers(){
        new DeleteFlowers().execute();
    }

    public static class DeleteFlowers extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            db.flowerDao().deleteAllFlowers();
            return null;
        }
    }

    public void loadData(){
        api = Api.getInstance();
        ApiService apiService = api.apiService();
        disposable = apiService.getFlower().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Flower>>() {
                    @Override
                    public void accept(List<Flower> flowers) throws Exception {
                        deleteFlowers();
                        insertFlowers(flowers);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        errors.setValue(throwable);
                    }
                });
    }

    @Override
    protected void onCleared() {
        disposable.dispose();
        super.onCleared();
    }
}
