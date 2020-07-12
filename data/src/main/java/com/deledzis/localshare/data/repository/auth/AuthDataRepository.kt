package com.deledzis.localshare.data.repository.auth

import com.deledzis.localshare.common.BaseNetworkManager
import com.deledzis.localshare.common.usecase.Error
import com.deledzis.localshare.common.usecase.Response
import com.deledzis.localshare.data.mapper.AuthMapper
import com.deledzis.localshare.data.mapper.UserMapper
import com.deledzis.localshare.data.source.auth.AuthDataStoreFactory
import com.deledzis.localshare.domain.model.entity.auth.*
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

    override suspend fun auth(email: String, password: String): Response<AuthResponse, Error> {
        return if (networkManager.isConnectedToInternet()) {
            val auth = factory.retrieveRemoteDataStore().authUser(
                email = email,
                password = password
            )
            var response: Response<AuthResponse, Error> = Response.Failure(Error.ResponseError())
            auth.handleResult(
                stateBlock = {
                    response = it
                },
                successBlock = {
                    response = Response.Success(
                        AuthResponse(
                            auth = authMapper.mapFromEntity(it)
                        )
                    )
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

    override suspend fun register(
        email: String,
        password: String
    ): Response<RegisterResponse, Error> {
        return if (networkManager.isConnectedToInternet()) {
            val register = factory.retrieveRemoteDataStore().register(
                email = email,
                password = password
            )
            var response: Response<RegisterResponse, Error> =
                Response.Failure(Error.ResponseError())
            register.handleResult(
                stateBlock = {
                    response = it
                },
                successBlock = {
                    response = Response.Success(
                        RegisterResponse(
                            auth = authMapper.mapFromEntity(it)
                        )
                    )
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

    override suspend fun verifyToken(token: String): Response<VerifyTokenResponse, Error> {
        return if (networkManager.isConnectedToInternet()) {
            val verify = factory.retrieveRemoteDataStore().verifyToken(
                token = token
            )
            var response: Response<VerifyTokenResponse, Error> =
                Response.Failure(Error.ResponseError())
            verify.handleResult(
                stateBlock = {
                    response = it
                },
                successBlock = {
                    response = Response.Success(
                        VerifyTokenResponse(
                            auth = authMapper.mapFromEntity(it)
                        )
                    )
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

    override suspend fun refreshToken(token: String): Response<RefreshTokenResponse, Error> {
        return if (networkManager.isConnectedToInternet()) {
            val refresh = factory.retrieveRemoteDataStore().verifyToken(
                token = token
            )
            var response: Response<RefreshTokenResponse, Error> =
                Response.Failure(Error.ResponseError())
            refresh.handleResult(
                stateBlock = {
                    response = it
                },
                successBlock = {
                    response = Response.Success(
                        RefreshTokenResponse(
                            auth = authMapper.mapFromEntity(it)
                        )
                    )
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

    override suspend fun forgetPassword(email: String): Response<ForgetPasswordResponse, Error> {
        return if (networkManager.isConnectedToInternet()) {
            val recover = factory.retrieveRemoteDataStore().recoverPassword(
                email = email
            )
            var response: Response<ForgetPasswordResponse, Error> =
                Response.Failure(Error.ResponseError())
            recover.handleResult(
                stateBlock = {
                    response = it
                },
                successBlock = {
                    response = Response.Success(
                        ForgetPasswordResponse(
                            result = it
                        )
                    )
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

    override suspend fun getUser(id: Int): Response<GetUserResponse, Error> {
        return if (networkManager.isConnectedToInternet()) {
            val user = factory.retrieveRemoteDataStore().getUser(
                id = id
            )
            var response: Response<GetUserResponse, Error> = Response.Failure(Error.ResponseError())
            user.handleResult(
                stateBlock = {
                    response = it
                },
                successBlock = {
                    response = Response.Success(
                        GetUserResponse(
                            user = userMapper.mapFromEntity(it)
                        )
                    )
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