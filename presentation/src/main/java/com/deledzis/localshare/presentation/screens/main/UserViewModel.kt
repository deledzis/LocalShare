package com.deledzis.localshare.presentation.screens.main

import androidx.lifecycle.MutableLiveData
import com.deledzis.localshare.common.usecase.Error
import com.deledzis.localshare.common.usecase.Response
import com.deledzis.localshare.domain.model.BaseUserData
import com.deledzis.localshare.domain.model.User
import com.deledzis.localshare.domain.model.entity.Entity
import com.deledzis.localshare.presentation.base.BaseViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class UserViewModel @Inject constructor(
    private val userData: BaseUserData
) : BaseViewModel() {

    override val receiveChannel: ReceiveChannel<Response<Entity, Error>>
        get() = Channel()

    var user = MutableLiveData<User>(userData.getUser())

    override suspend fun resolve(value: Response<Entity, Error>) {}

    fun saveUser(user: User?) {
        Timber.d("Saving User $user")
        userData.saveUser(user)
        this.user.postValue(user)
    }
}