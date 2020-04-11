package com.gornostaevas.pushify.di

import com.gornostaevas.pushify.MainActivity
import com.gornostaevas.pushify.authorized_list.AuthorizedListFragment
import com.gornostaevas.pushify.network.NetworkModule
import com.gornostaevas.pushify.new_authorization.AuthorizationManager
import com.gornostaevas.pushify.new_authorization.AuthorizeFragment
import com.gornostaevas.pushify.social_nets.SupportedNetworks
import dagger.Component
import dagger.Module
import javax.inject.Provider

@ApplicationScope
@Component(modules = [NetworkModule::class, ModelModule::class])
interface ApplicationComponent {
    fun inject(mainActivity: MainActivity)

    // TODO its better to use subcomponent for this fragment in future
    fun inject(fragment : AuthorizedListFragment)

    fun inject(fragment : AuthorizeFragment)
}