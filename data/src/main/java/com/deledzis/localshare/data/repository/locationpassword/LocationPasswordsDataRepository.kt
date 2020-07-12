package com.deledzis.localshare.data.repository.locationpassword

import com.deledzis.localshare.common.BaseNetworkManager
import com.deledzis.localshare.common.usecase.Error
import com.deledzis.localshare.common.usecase.Response
import com.deledzis.localshare.data.mapper.LocationPasswordMapper
import com.deledzis.localshare.data.source.locationpassword.LocationPasswordsDataStoreFactory
import com.deledzis.localshare.domain.model.LocationPassword
import com.deledzis.localshare.domain.repository.LocationPasswordsRepository
import com.deledzis.localshare.domain.usecase.None
import javax.inject.Inject

/**
 * Provides an implementation of the [LocationPasswordsRepository] interface for communicating to and from
 * data sources
 */
class LocationPasswordsDataRepository @Inject constructor(
    private val factory: LocationPasswordsDataStoreFactory,
    private val locationPasswordMapper: LocationPasswordMapper,
    private val networkManager: BaseNetworkManager
) : LocationPasswordsRepository {

    override suspend fun getLocationPasswords(userId: Int): Response<List<LocationPassword>, Error> {
        val dataStore = factory.retrieveDataStore(userId = userId)
        val locationPasswords = dataStore.getLocationPasswords(userId = userId)
        var response: Response<List<LocationPassword>, Error> = Response.Success(emptyList())
        locationPasswords.handleResult(
            stateBlock = {
                response = it
            },
            successBlock = { list ->
                response = Response.Success(list.map { locationPasswordMapper.mapFromEntity(it) })
            },
            failureBlock = {
                response = Response.Failure(it)
            }
        )
        return response
    }

    override suspend fun addLocationPassword(
        password: String,
        description: String
    ): Response<Boolean, Error> {
        return if (networkManager.isConnectedToInternet()) {
            factory.retrieveRemoteDataStore().addLocationPassword(
                password = password,
                description = description
            )
        } else {
            Response.Failure(Error.NetworkConnectionError())
        }
    }

    override suspend fun updateLocationPassword(locationPassword: LocationPassword): Response<Boolean, Error> {
        return if (networkManager.isConnectedToInternet()) {
            val updatedRemote = factory.retrieveRemoteDataStore().updateLocationPassword(
                locationPassword = locationPasswordMapper.mapToEntity(locationPassword)
            )
            handleUpdatePasswordRemote(locationPassword = locationPassword, result = updatedRemote)
        } else {
            Response.Failure(Error.NetworkConnectionError())
        }
    }

    private suspend fun handleUpdatePasswordRemote(
        locationPassword: LocationPassword,
        result: Response<Boolean, Error>
    ): Response<Boolean, Error> {
        var response: Response<Boolean, Error> = result
        result.handleResult(
            stateBlock = {
                response = it
            },
            successBlock = {
                response = if (it) {
                    val updatedCache =
                        factory.retrieveCacheDataStore().updateLocationPassword(
                            locationPassword = locationPasswordMapper.mapToEntity(locationPassword)
                        )

                    handleUpdatePasswordCache(result = updatedCache)
                } else {
                    Response.Success(it)
                }
            },
            failureBlock = {
                response = Response.Failure(it)
            }
        )

        return response
    }

    private suspend fun handleUpdatePasswordCache(
        result: Response<Boolean, Error>
    ): Response<Boolean, Error> {
        var response: Response<Boolean, Error> = result
        result.handleResult(
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

        return response
    }

    override suspend fun deleteLocationPassword(id: Int): Response<Boolean, Error> {
        return if (networkManager.isConnectedToInternet()) {
            val deletedRemote = factory.retrieveRemoteDataStore().deleteLocationPassword(id = id)
            handleDeletePasswordRemote(id = id, result = deletedRemote)
        } else {
            Response.Failure(Error.NetworkConnectionError())
        }
    }

    private suspend fun handleDeletePasswordRemote(
        id: Int,
        result: Response<Boolean, Error>
    ): Response<Boolean, Error> {
        var response: Response<Boolean, Error> = result
        result.handleResult(
            stateBlock = {
                response = it
            },
            successBlock = {
                response = if (it) {
                    val deletedCache =
                        factory.retrieveCacheDataStore().deleteLocationPassword(id = id)

                    handleDeletePasswordCache(result = deletedCache)
                } else {
                    Response.Success(it)
                }
            },
            failureBlock = {
                response = Response.Failure(it)
            }
        )

        return response
    }

    private suspend fun handleDeletePasswordCache(
        result: Response<Boolean, Error>
    ): Response<Boolean, Error> {
        var response: Response<Boolean, Error> = result
        result.handleResult(
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

        return response
    }

    override suspend fun clearLocationPasswords(): Response<None, Error> {
        return factory.retrieveCacheDataStore().clearLocationPasswords()
    }

    override suspend fun saveLocationPasswords(locationPasswords: List<LocationPassword>): Response<None, Error> {
        return factory.retrieveCacheDataStore()
            .saveLocationPasswords(locationPasswords = locationPasswords.map {
                locationPasswordMapper.mapToEntity(it)
            })
    }

}