package com.gornostaevas.pushify.di

import android.content.Context
import com.gornostaevas.pushify.ResultObserver
import com.gornostaevas.pushify.ResultObserverImpl
import com.gornostaevas.pushify.authorized_list.AuthorizedListViewModel
import com.gornostaevas.pushify.new_authorization.AuthorizationManager
import com.gornostaevas.pushify.social_nets.SupportedNetworks
import com.gornostaevas.pushify.social_nets.VKManager
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
    fun viewModel(): AuthorizedListViewModel = AuthorizedListViewModel(context)

    @Provides
    @ApplicationScope
    fun resObserver(): ResultObserver {
        return ResultObserverImpl()
    }
}

@Module
class SocialNetworkModule(val net: SupportedNetworks) {
    @Provides
    @AuthorizationScope
    fun getAuthenticationManager(): AuthorizationManager {
        return when (net) {
            SupportedNetworks.VK -> VKManager()
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