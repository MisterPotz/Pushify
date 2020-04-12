package com.gornostaevas.pushify.di

import android.content.Context
import androidx.room.Room
import com.gornostaevas.pushify.ResultObserver
import com.gornostaevas.pushify.ResultObserverImpl
import com.gornostaevas.pushify.authorized_list.AuthorizedListViewModel
import com.gornostaevas.pushify.saved_nets.AppDatabase
import com.gornostaevas.pushify.saved_nets.Repository
import com.gornostaevas.pushify.saved_nets.SocNetDao
import com.gornostaevas.pushify.social_nets.AuthorizationManager
import com.gornostaevas.pushify.social_nets.SupportedNetworks
import com.gornostaevas.pushify.social_nets.facebook.FacebookManager
import com.gornostaevas.pushify.social_nets.vk.VKManager
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import java.lang.IllegalStateException

@Module(subcomponents = [SocialNetworkComponent::class])
class ModelModule(val context: Context) {
    @Provides
    @ApplicationScope
    fun context(): Context = context

    @Provides
    @ApplicationScope
    fun viewModel(repository: Repository): AuthorizedListViewModel =
        AuthorizedListViewModel(repository)

    @Provides
    @ApplicationScope
    fun resObserver(): ResultObserver {
        return ResultObserverImpl()
    }

    @Provides
    @ApplicationScope
    fun database(): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "soc_database"
        ).build()
    }
}

@Module
class SocialNetworkModule(val net: SupportedNetworks) {
    @Provides
    @AuthorizationScope
    fun getAuthenticationManager(): AuthorizationManager {
        return when (net) {
            SupportedNetworks.VK -> VKManager()
            SupportedNetworks.Facebook -> FacebookManager()
            else -> throw IllegalStateException("dont have other networks realized")
        }
    }
}

@Subcomponent(modules = [SocialNetworkModule::class])
@AuthorizationScope
interface SocialNetworkComponent {
    fun getAuthManager(): AuthorizationManager

    @Subcomponent.Builder
    interface Builder {
        fun build(): SocialNetworkComponent
        fun socialNetworkModule(module: SocialNetworkModule): Builder
    }
}