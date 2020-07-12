package com.deledzis.localshare.data.repository.auth

import com.deledzis.localshare.common.BaseNetworkManager
import com.deledzis.localshare.common.usecase.Error
import com.deledzis.localshare.common.usecase.Response
import com.deledzis.localshare.data.mapper.AuthMapper
import com.deledzis.localshare.data.mapper.UserMapper
import com.deledzis.localshare.data.source.auth.AuthDataStoreFactory
import com.deledzis.localshare.domain.model.Auth
import com.deledzis.localshare.domain.model.User
import com.deledzis.localshare.domain.repository.AuthRepository
import com.deledzis.localshare.domain.repository.LocationPasswordsRepository
import javax.inject.Inject

/**
 * Provides an implementation of the [LocationPasswordsRepository] interface for communicating to and from
 * data sources
 */
class AuthDataRepository @Inject constructor(
    private val factory: AuthDataStoreFactory,
    private val authMapper: AuthMapper,
    private val userMapper: UserMapper,
    private val networkManager: BaseNetworkManager
) : AuthRepository {

    override suspend fun auth(email: String, password: String): Response<Auth, Error> {
        return if (networkManager.isConnectedToInternet()) {
            val auth = factory.retrieveRemoteDataStore().authUser(
                email = email,
                password = password
            )
            var response: Response<Auth, Error> = Response.Failure(Error.NetworkError())
            auth.handleResult(
                stateBlock = {
                    response = it
                },
                successBlock = {
                    response = Response.Success(authMapper.mapFromEntity(it))
                },
                failureBlock = {
                    response = Response.Failure(it)
                }
            )
            response
        } else {
            Response.Failure(Error.NetworkConnectionError())
        }
    }

    override suspend fun register(email: String, password: String): Response<Auth, Error> {
        return if (networkManager.isConnectedToInternet()) {
            val register = factory.retrieveRemoteDataStore().register(
                email = email,
                password = password
            )
            var response: Response<Auth, Error> = Response.Failure(Error.NetworkError())
            register.handleResult(
                stateBlock = {
                    response = it
                },
                successBlock = {
                    response = Response.Success(authMapper.mapFromEntity(it))
                },
                failureBlock = {
                    response = Response.Failure(it)
                }
            )
            response
        } else {
            Response.Failure(Error.NetworkConnectionError())
        }
    }

    override suspend fun verifyToken(token: String): Response<Auth, Error> {
        return if (networkManager.isConnectedToInternet()) {
            val verify = factory.retrieveRemoteDataStore().verifyToken(
                token = token
            )
            var response: Response<Auth, Error> = Response.Failure(Error.NetworkError())
            verify.handleResult(
                stateBlock = {
                    response = it
                },
                successBlock = {
                    response = Response.Success(authMapper.mapFromEntity(it))
                },
                failureBlock = {
                    response = Response.Failure(it)
                }
            )
            response
        } else {
            Response.Failure(Error.NetworkConnectionError())
        }
    }

    override suspend fun refreshToken(token: String): Response<Auth, Error> {
        return if (networkManager.isConnectedToInternet()) {
            val refresh = factory.retrieveRemoteDataStore().verifyToken(
                token = token
            )
            var response: Response<Auth, Error> = Response.Failure(Error.NetworkError())
            refresh.handleResult(
                stateBlock = {
                    response = it
                },
                successBlock = {
                    response = Response.Success(authMapper.mapFromEntity(it))
                },
                failureBlock = {
                    response = Response.Failure(it)
                }
            )
            response
        } else {
            Response.Failure(Error.NetworkConnectionError())
        }
    }

    override suspend fun forgetPassword(email: String): Response<Boolean, Error> {
        return if (networkManager.isConnectedToInternet()) {
            val recover = factory.retrieveRemoteDataStore().recoverPassword(
                email = email
            )
            var response: Response<Boolean, Error> = Response.Failure(Error.NetworkError())
            recover.handleResult(
                stateBlock = {
                    response = it
                },
                successBlock = {
                    response = Response.Success(it)
                },
                failureBlock = {
                    response = Response.Failure(it)
                }
            )
            response
        } else {
            Response.Failure(Error.NetworkConnectionError())
        }
    }

    override suspend fun getUser(id: Int): Response<User, Error> {
        return if (networkManager.isConnectedToInternet()) {
            val user = factory.retrieveRemoteDataStore().getUser(
                id = id
            )
            var response: Response<User, Error> = Response.Failure(Error.NetworkError())
            user.handleResult(
                stateBlock = {
                    response = it
                },
                successBlock = {
                    response = Response.Success(userMapper.mapFromEntity(it))
                },
                failureBlock = {
                    response = Response.Failure(it)
                }
            )
            response
        } else {
            Response.Failure(Error.NetworkConnectionError())
        }
    }
}