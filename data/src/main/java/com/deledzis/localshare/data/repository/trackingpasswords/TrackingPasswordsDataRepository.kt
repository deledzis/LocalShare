package com.deledzis.localshare.data.repository.trackingpasswords

import com.deledzis.localshare.common.BaseNetworkManager
import com.deledzis.localshare.common.usecase.Error
import com.deledzis.localshare.common.usecase.Response
import com.deledzis.localshare.data.mapper.TrackingPasswordMapper
import com.deledzis.localshare.data.source.trackingpasswords.TrackingPasswordsDataStoreFactory
import com.deledzis.localshare.domain.model.TrackingPassword
import com.deledzis.localshare.domain.model.response.trackingpasswords.*
import com.deledzis.localshare.domain.repository.TrackingPasswordsRepository
import javax.inject.Inject

/**
 * Provides an implementation of the [TrackingPasswordsRepository] interface for communicating to and from
 * data sources
 */
class TrackingPasswordsDataRepository @Inject constructor(
    private val factory: TrackingPasswordsDataStoreFactory,
    private val trackingPasswordMapper: TrackingPasswordMapper,
    private val networkManager: BaseNetworkManager
) : TrackingPasswordsRepository {

    override suspend fun getTrackingPasswords(
        userId: Int,
        refresh: Boolean
    ): Response<GetTrackingPasswordsResponse, Error> {
        val dataStore = factory.retrieveCacheDataStore()
        val trackingPasswords = dataStore
            .getTrackingPasswords(userId = userId)
        var response: Response<GetTrackingPasswordsResponse, Error> = Response.Failure(
            Error.ResponseError()
        )
        trackingPasswords.handleResult(
            stateBlock = {
                response = it
            },
            successBlock = { list ->
                if (refresh) {
                    clearTrackingPasswords()
                    saveTrackingPasswords(trackingPasswords = list.map {
                        trackingPasswordMapper.mapFromEntity(it)
                    })
                }
                response = Response.Success(
                    GetTrackingPasswordsResponse(
                        items = list.map {
                            trackingPasswordMapper.mapFromEntity(it)
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
        val trackingPasswords = dataStore
            .getTrackingPasswords(userId = userId)
        var response: Response<GetTrackingPasswordsResponse, Error> = Response.Failure(
            Error.ResponseError()
        )
        trackingPasswords.handleResult(
            stateBlock = {
                response = it
            },
            successBlock = { list ->
                if (dataStore is TrackingPasswordsRemoteDataStore) {
                    clearTrackingPasswords()
                    saveTrackingPasswords(trackingPasswords = list.map {
                        trackingPasswordMapper.mapFromEntity(it)
                    })
                }
                response = Response.Success(
                    GetTrackingPasswordsResponse(
                        items = list.map {
                            trackingPasswordMapper.mapFromEntity(it)
                        })
                )
            },
            failureBlock = {
                response = Response.Failure(it)
            }
        )
        return response*/
    }

    override suspend fun addTrackingPassword(
        password: String,
        description: String
    ): Response<AddTrackingPasswordResponse, Error> {
        val dataStore = factory.retrieveCacheDataStore()
        val result = dataStore
            .addTrackingPassword(
                password = password,
                description = description
            )
        var response: Response<AddTrackingPasswordResponse, Error> = Response.Failure(
            Error.ResponseError()
        )
        result.handleResult(
            stateBlock = {
                response = it
            },
            successBlock = {
                response = Response.Success(
                    AddTrackingPasswordResponse(
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
                .addTrackingPassword(
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
    ): Response<AddTrackingPasswordResponse, Error> {
        var response: Response<AddTrackingPasswordResponse, Error> = Response.Failure(
            Error.ResponseError()
        )
        result.handleResult(
            stateBlock = {
                response = it
            },
            successBlock = {
                response = if (it) {
                    val addedCache =
                        factory.retrieveCacheDataStore().addTrackingPassword(
                            password = password,
                            description = description
                        )

                    handleAddPasswordCache(result = addedCache)
                } else {
                    Response.Success(
                        AddTrackingPasswordResponse(
                            result = it
                        )
                    )
                }
            },
            failureBlock = {
                /*val addedCache =
                    factory.retrieveCacheDataStore().addTrackingPassword(
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
    ): Response<AddTrackingPasswordResponse, Error> {
        var response: Response<AddTrackingPasswordResponse, Error> = Response.Failure(
            Error.ResponseError()
        )
        result.handleResult(
            stateBlock = {
                response = it
            },
            successBlock = {
                response = Response.Success(
                    AddTrackingPasswordResponse(
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

    override suspend fun updateTrackingPassword(trackingPassword: TrackingPassword)
            : Response<UpdateTrackingPasswordResponse, Error> {
        val dataStore = factory.retrieveCacheDataStore()
        val result = dataStore.updateTrackingPassword(
                trackingPassword = trackingPasswordMapper.mapToEntity(trackingPassword)
            )
        var response: Response<UpdateTrackingPasswordResponse, Error> = Response.Failure(
            Error.ResponseError()
        )
        result.handleResult(
            stateBlock = {
                response = it
            },
            successBlock = {
                response = Response.Success(
                    UpdateTrackingPasswordResponse(
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
                .updateTrackingPassword(
                    trackingPassword = trackingPasswordMapper.mapToEntity(trackingPassword)
                )
            handleUpdatePasswordRemote(trackingPassword = trackingPassword, result = updatedRemote)
        } else {
            Response.Failure(Error.NetworkConnectionError())
        }*/
    }

    private suspend fun handleUpdatePasswordRemote(
        trackingPassword: TrackingPassword,
        result: Response<Boolean, Error>
    ): Response<UpdateTrackingPasswordResponse, Error> {
        var response: Response<UpdateTrackingPasswordResponse, Error> = Response.Failure(
            Error.ResponseError()
        )
        result.handleResult(
            stateBlock = {
                response = it
            },
            successBlock = {
                response = if (it) {
                    val updatedCache =
                        factory.retrieveCacheDataStore().updateTrackingPassword(
                            trackingPassword = trackingPasswordMapper.mapToEntity(trackingPassword)
                        )

                    handleUpdatePasswordCache(result = updatedCache)
                } else {
                    Response.Success(
                        UpdateTrackingPasswordResponse(
                            result = it
                        )
                    )
                }
            },
            failureBlock = {
                /*val updatedCache =
                    factory.retrieveCacheDataStore().updateTrackingPassword(
                        trackingPassword = trackingPasswordMapper.mapToEntity(trackingPassword)
                    )

                handleUpdatePasswordCache(result = updatedCache)*/
                response = Response.Failure(it)
            }
        )

        return response
    }

    private suspend fun handleUpdatePasswordCache(
        result: Response<Boolean, Error>
    ): Response<UpdateTrackingPasswordResponse, Error> {
        var response: Response<UpdateTrackingPasswordResponse, Error> = Response.Failure(
            Error.ResponseError()
        )
        result.handleResult(
            stateBlock = {
                response = it
            },
            successBlock = {
                response = Response.Success(
                    UpdateTrackingPasswordResponse(
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

    override suspend fun deleteTrackingPassword(password: String)
            : Response<DeleteTrackingPasswordResponse, Error> {
        val dataStore = factory.retrieveCacheDataStore()
        val result = dataStore
            .deleteTrackingPassword(password = password)
        var response: Response<DeleteTrackingPasswordResponse, Error> = Response.Failure(
            Error.ResponseError()
        )
        result.handleResult(
            stateBlock = {
                response = it
            },
            successBlock = {
                response = Response.Success(
                    DeleteTrackingPasswordResponse(
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
                .deleteTrackingPassword(password = password)
            handleDeletePasswordRemote(password = password, result = deletedRemote)
        } else {
            Response.Failure(Error.NetworkConnectionError())
        }*/
    }

    private suspend fun handleDeletePasswordRemote(
        password: String,
        result: Response<Boolean, Error>
    ): Response<DeleteTrackingPasswordResponse, Error> {
        var response: Response<DeleteTrackingPasswordResponse, Error> = Response.Failure(
            Error.ResponseError()
        )
        result.handleResult(
            stateBlock = {
                response = it
            },
            successBlock = {
                response = if (it) {
                    val deletedCache = factory.retrieveCacheDataStore()
                        .deleteTrackingPassword(password = password)

                    handleDeletePasswordCache(result = deletedCache)
                } else {
                    Response.Success(
                        DeleteTrackingPasswordResponse(
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
    ): Response<DeleteTrackingPasswordResponse, Error> {
        var response: Response<DeleteTrackingPasswordResponse, Error> = Response.Failure(
            Error.ResponseError()
        )
        result.handleResult(
            stateBlock = {
                response = it
            },
            successBlock = {
                response = Response.Success(
                    DeleteTrackingPasswordResponse(
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

    override suspend fun clearTrackingPasswords(): Response<ClearTrackingPasswordsResponse, Error> {
        val result = factory.retrieveCacheDataStore()
            .clearTrackingPasswords()
        var response: Response<ClearTrackingPasswordsResponse, Error> = Response.Failure(
            Error.ResponseError()
        )
        result.run {
            handleResult(
                stateBlock = {
                    response = it
                },
                successBlock = {
                    response = Response.Success(ClearTrackingPasswordsResponse())
                },
                failureBlock = {
                    response = Response.Failure(it)
                }
            )
        }

        return response
    }

    override suspend fun saveTrackingPasswords(trackingPasswords: List<TrackingPassword>)
            : Response<SaveTrackingPasswordsResponse, Error> {
        val result = factory.retrieveCacheDataStore().saveTrackingPasswords(
            trackingPasswords = trackingPasswords.map {
                trackingPasswordMapper.mapToEntity(it)
            }
        )
        var response: Response<SaveTrackingPasswordsResponse, Error> = Response.Failure(
            Error.ResponseError()
        )
        result.handleResult(
            stateBlock = {
                response = it
            },
            successBlock = {
                response = Response.Success(SaveTrackingPasswordsResponse())
            },
            failureBlock = {
                response = Response.Failure(it)
            }
        )

        return response
    }

}