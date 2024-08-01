package com.unewexp.lessons

import com.unewexp.lessons.model.Client
import com.unewexp.lessons.model.RestAPIService
import com.unewexp.lessons.presenter.Presenter
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Component(modules = [AppModule::class])
interface AppGraph {

    fun inject(activity: MainActivity)
}
@Module
class AppModule(){

    @Provides
    fun providePresenter(restAPIService: RestAPIService): Presenter{
        return Presenter(restAPIService)
    }

    @Provides
    fun provideApi(client: Client): RestAPIService{
        return RestAPIService(client = client)
    }

    @Provides
    fun provideClient(): Client{
        return Client()
    }
}