package com.deledzis.localshare.data.repository.locationpasswords

import com.deledzis.localshare.common.BaseNetworkManager
import com.deledzis.localshare.common.usecase.Error
import com.deledzis.localshare.common.usecase.Response
import com.deledzis.localshare.data.mapper.LocationPasswordMapper
import com.deledzis.localshare.data.source.locationpasswords.LocationPasswordsDataStoreFactory
import com.deledzis.localshare.domain.model.LocationPassword
import com.deledzis.localshare.domain.model.response.locationpasswords.*
import com.deledzis.localshare.domain.repository.LocationPasswordsRepository
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

    override suspend fun getLocationPasswords(
        userId: Int,
        refresh: Boolean
    ): Response<GetLocationPasswordsResponse, Error> {
        val dataStore = factory.retrieveCacheDataStore()
        val locationPasswords = dataStore
            .getLocationPasswords(userId = userId)
        var response: Response<GetLocationPasswordsResponse, Error> = Response.Failure(
            Error.ResponseError()
        )
        locationPasswords.handleResult(
            stateBlock = {
                response = it
            },
            successBlock = { list ->
                if (refresh) {
                    clearLocationPasswords()
                    saveLocationPasswords(locationPasswords = list.map {
                        locationPasswordMapper.mapFromEntity(it)
                    })
                }
                response = Response.Success(
                    GetLocationPasswordsResponse(
                        items = list.map {
                            locationPasswordMapper.mapFromEntity(it)
                        }
                    )
                )
            },
            failureBlock = {
                response = Response.Failure(it)
            }
        )
        return response
        // TODO: uncomment when backend works
        /*val dataStore = if (refresh) {
            factory.retrieveRemoteDataStore()
        } else {
            factory.retrieveDataStore(userId = userId)
        }
        val locationPasswords = dataStore
            .getLocationPasswords(userId = userId)
        var response: Response<GetLocationPasswordsResponse, Error> = Response.Failure(
            Error.ResponseError()
        )
        locationPasswords.handleResult(
            stateBlock = {
                response = it
            },
            successBlock = { list ->
                if (dataStore is LocationPasswordsRemoteDataStore) {
                    clearLocationPasswords()
                    saveLocationPasswords(locationPasswords = list.map {
                        locationPasswordMapper.mapFromEntity(it)
                    })
                }
                response = Response.Success(
                    GetLocationPasswordsResponse(
                        items = list.map {
                            locationPasswordMapper.mapFromEntity(it)
                        })
                )
            },
            failureBlock = {
                response = Response.Failure(it)
            }
        )
        return response*/
    }

    override suspend fun addLocationPassword(
        password: String,
        description: String
    ): Response<AddLocationPasswordResponse, Error> {
        val dataStore = factory.retrieveCacheDataStore()
        val result = dataStore
            .addLocationPassword(
                password = password,
                description = description
            )
        var response: Response<AddLocationPasswordResponse, Error> = Response.Failure(
            Error.ResponseError()
        )
        result.handleResult(
            stateBlock = {
                response = it
            },
            successBlock = {
                response = Response.Success(
                    AddLocationPasswordResponse(
                        result = it
                    )
                )
            },
            failureBlock = {
                response = Response.Failure(it)
            }
        )
        return response
        // TODO: uncomment when backend works
        /*return if (networkManager.isConnectedToInternet()) {
            val result = factory.retrieveRemoteDataStore()
                .addLocationPassword(
                    password = password,
                    description = description
                )
            handleAddPasswordRemote(
                password = password,
                description = description,
                result = result
            )
        } else {
            Response.Failure(Error.NetworkConnectionError())
        }*/
    }

    private suspend fun handleAddPasswordRemote(
        password: String,
        description: String,
        result: Response<Boolean, Error>
    ): Response<AddLocationPasswordResponse, Error> {
        var response: Response<AddLocationPasswordResponse, Error> = Response.Failure(
            Error.ResponseError()
        )
        result.handleResult(
            stateBlock = {
                response = it
            },
            successBlock = {
                response = if (it) {
                    val addedCache =
                        factory.retrieveCacheDataStore().addLocationPassword(
                            password = password,
                            description = description
                        )

                    handleAddPasswordCache(result = addedCache)
                } else {
                    Response.Success(
                        AddLocationPasswordResponse(
                            result = it
                        )
                    )
                }
            },
            failureBlock = {
                /*val addedCache =
                    factory.retrieveCacheDataStore().addLocationPassword(
                        password = password,
                        description = description
                    )

                handleAddPasswordCache(result = addedCache)*/
                response = Response.Failure(it)
            }
        )

        return response
    }

    private suspend fun handleAddPasswordCache(
        result: Response<Boolean, Error>
    ): Response<AddLocationPasswordResponse, Error> {
        var response: Response<AddLocationPasswordResponse, Error> = Response.Failure(
            Error.ResponseError()
        )
        result.handleResult(
            stateBlock = {
                response = it
            },
            successBlock = {
                response = Response.Success(
                    AddLocationPasswordResponse(
                        result = it
                    )
                )
            },
            failureBlock = {
                response = Response.Failure(it)
            }
        )

        return response
    }

    override suspend fun updateLocationPassword(locationPassword: LocationPassword)
            : Response<UpdateLocationPasswordResponse, Error> {
        val dataStore = factory.retrieveCacheDataStore()
        val result = dataStore.updateLocationPassword(
                locationPassword = locationPasswordMapper.mapToEntity(locationPassword)
            )
        var response: Response<UpdateLocationPasswordResponse, Error> = Response.Failure(
            Error.ResponseError()
        )
        result.handleResult(
            stateBlock = {
                response = it
            },
            successBlock = {
                response = Response.Success(
                    UpdateLocationPasswordResponse(
                        result = it
                    )
                )
            },
            failureBlock = {
                response = Response.Failure(it)
            }
        )
        return response
        // TODO: uncomment when backend works
        /*return if (networkManager.isConnectedToInternet()) {
            val updatedRemote = factory.retrieveRemoteDataStore()
                .updateLocationPassword(
                    locationPassword = locationPasswordMapper.mapToEntity(locationPassword)
                )
            handleUpdatePasswordRemote(locationPassword = locationPassword, result = updatedRemote)
        } else {
            Response.Failure(Error.NetworkConnectionError())
        }*/
    }

    private suspend fun handleUpdatePasswordRemote(
        locationPassword: LocationPassword,
        result: Response<Boolean, Error>
    ): Response<UpdateLocationPasswordResponse, Error> {
        var response: Response<UpdateLocationPasswordResponse, Error> = Response.Failure(
            Error.ResponseError()
        )
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
                    Response.Success(
                        UpdateLocationPasswordResponse(
                            result = it
                        )
                    )
                }
            },
            failureBlock = {
                /*val updatedCache =
                    factory.retrieveCacheDataStore().updateLocationPassword(
                        locationPassword = locationPasswordMapper.mapToEntity(locationPassword)
                    )

                handleUpdatePasswordCache(result = updatedCache)*/
                response = Response.Failure(it)
            }
        )

        return response
    }

    private suspend fun handleUpdatePasswordCache(
        result: Response<Boolean, Error>
    ): Response<UpdateLocationPasswordResponse, Error> {
        var response: Response<UpdateLocationPasswordResponse, Error> = Response.Failure(
            Error.ResponseError()
        )
        result.handleResult(
            stateBlock = {
                response = it
            },
            successBlock = {
                response = Response.Success(
                    UpdateLocationPasswordResponse(
                        result = it
                    )
                )
            },
            failureBlock = {
                response = Response.Failure(it)
            }
        )

        return response
    }

    override suspend fun deleteLocationPassword(password: String)
            : Response<DeleteLocationPasswordResponse, Error> {
        val dataStore = factory.retrieveCacheDataStore()
        val result = dataStore
            .deleteLocationPassword(password = password)
        var response: Response<DeleteLocationPasswordResponse, Error> = Response.Failure(
            Error.ResponseError()
        )
        result.handleResult(
            stateBlock = {
                response = it
            },
            successBlock = {
                response = Response.Success(
                    DeleteLocationPasswordResponse(
                        result = it
                    )
                )
            },
            failureBlock = {
                response = Response.Failure(it)
            }
        )
        return response
        // TODO: uncomment when backend works
        /*return if (networkManager.isConnectedToInternet()) {
            val deletedRemote = factory.retrieveRemoteDataStore()
                .deleteLocationPassword(password = password)
            handleDeletePasswordRemote(password = password, result = deletedRemote)
        } else {
            Response.Failure(Error.NetworkConnectionError())
        }*/
    }

    private suspend fun handleDeletePasswordRemote(
        password: String,
        result: Response<Boolean, Error>
    ): Response<DeleteLocationPasswordResponse, Error> {
        var response: Response<DeleteLocationPasswordResponse, Error> = Response.Failure(
            Error.ResponseError()
        )
        result.handleResult(
            stateBlock = {
                response = it
            },
            successBlock = {
                response = if (it) {
                    val deletedCache = factory.retrieveCacheDataStore()
                        .deleteLocationPassword(password = password)

                    handleDeletePasswordCache(result = deletedCache)
                } else {
                    Response.Success(
                        DeleteLocationPasswordResponse(
                            result = it
                        )
                    )
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
    ): Response<DeleteLocationPasswordResponse, Error> {
        var response: Response<DeleteLocationPasswordResponse, Error> = Response.Failure(
            Error.ResponseError()
        )
        result.handleResult(
            stateBlock = {
                response = it
            },
            successBlock = {
                response = Response.Success(
                    DeleteLocationPasswordResponse(
                        result = it
                    )
                )
            },
            failureBlock = {
                response = Response.Failure(it)
            }
        )

        return response
    }

    override suspend fun clearLocationPasswords(): Response<ClearLocationPasswordsResponse, Error> {
        val result = factory.retrieveCacheDataStore()
            .clearLocationPasswords()
        var response: Response<ClearLocationPasswordsResponse, Error> = Response.Failure(
            Error.ResponseError()
        )
        result.handleResult(
            stateBlock = {
                response = it
            },
            successBlock = {
                response = Response.Success(ClearLocationPasswordsResponse())
            },
            failureBlock = {
                response = Response.Failure(it)
            }
        )

        return response
    }

    override suspend fun saveLocationPasswords(locationPasswords: List<LocationPassword>)
            : Response<SaveLocationPasswordsResponse, Error> {
        val result = factory.retrieveCacheDataStore().saveLocationPasswords(
            locationPasswords = locationPasswords.map {
                locationPasswordMapper.mapToEntity(it)
            }
        )
        var response: Response<SaveLocationPasswordsResponse, Error> = Response.Failure(
            Error.ResponseError()
        )
        result.handleResult(
            stateBlock = {
                response = it
            },
            successBlock = {
                response = Response.Success(SaveLocationPasswordsResponse())
            },
            failureBlock = {
                response = Response.Failure(it)
            }
        )

        return response
    }

}