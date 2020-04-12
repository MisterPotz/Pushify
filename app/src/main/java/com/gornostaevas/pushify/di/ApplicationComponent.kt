package com.gornostaevas.pushify.di

import com.gornostaevas.pushify.MainActivity
import com.gornostaevas.pushify.authorized_list.AuthorizedListFragment
import com.gornostaevas.pushify.network.NetworkModule
import com.gornostaevas.pushify.new_authorization.AuthorizeFragment
import com.gornostaevas.pushify.send_post.SendPostFragment
import dagger.Component

@ApplicationScope
@Component(modules = [NetworkModule::class, ModelModule::class])
interface ApplicationComponent {
    fun inject(mainActivity: MainActivity)

    // TODO its better to use subcomponent for this fragment in future
    fun inject(fragment : AuthorizedListFragment)

    fun inject(fragment : AuthorizeFragment)

    fun inject(fragment: SendPostFragment)
}