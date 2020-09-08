package com.deledzis.localshare.remote.di

import com.deledzis.localshare.common.Constants
import com.deledzis.localshare.data.di.TokenInterceptor
import com.deledzis.localshare.data.repository.auth.AuthRemote
import com.deledzis.localshare.data.repository.locationpasswords.LocationPasswordsRemote
import com.deledzis.localshare.data.repository.trackingpasswords.TrackingPasswordsRemote
import com.deledzis.localshare.remote.ApiService
import com.deledzis.localshare.remote.AuthRemoteImpl
import com.deledzis.localshare.remote.LocationPasswordsRemoteImpl
import com.deledzis.localshare.remote.TrackingPasswordsRemoteImpl
import com.deledzis.localshare.remote.security.TLS12SocketFactory
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyStore
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager


@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        gsonBuilder.setLenient()
        return gsonBuilder.create()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        interceptor: Interceptor,
        tokenInterceptor: TokenInterceptor
    ): OkHttpClient.Builder {
        /*try {
            val tlsSocketFactory = TLSSocketFactory()
            okHttpBuilder
                .sslSocketFactory(tlsSocketFactory, tlsSocketFactory.trustManager)
                .build()
        } catch (e: KeyManagementException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: KeyStoreException) {
            e.printStackTrace()
        }*/
        return OkHttpClient.Builder()
            .addInterceptor(tokenInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(interceptor)
            .connectTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
    }

    private fun enableTls12OnPreLollipop(client: OkHttpClient.Builder): OkHttpClient.Builder? {
        try {
            val sc: SSLContext = SSLContext.getInstance("TLSv1.2")
            sc.init(null, null, null)
            val trustManagerFactory: TrustManagerFactory = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm()
            )
            trustManagerFactory.init(null as KeyStore?)
            val trustManagers: Array<TrustManager> = trustManagerFactory.trustManagers
            check(!(trustManagers.size != 1 || trustManagers[0] !is X509TrustManager)) {
                ("Unexpected default trust managers:"
                        + trustManagers.contentToString())
            }
            val trustManager: X509TrustManager = trustManagers[0] as X509TrustManager
            client.sslSocketFactory(
                TLS12SocketFactory(
                    sc.socketFactory
                ), trustManager)
            val cs: ConnectionSpec = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .tlsVersions(TlsVersion.TLS_1_2)
                .build()
            val specs: MutableList<ConnectionSpec> = ArrayList()
            specs.add(cs)
            specs.add(ConnectionSpec.COMPATIBLE_TLS)
            specs.add(ConnectionSpec.CLEARTEXT)
            client.connectionSpecs(specs)
        } catch (exc: Exception) {
        }
        return client
    }

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Singleton
    @Provides
    fun provideRequestHeadersInterceptor(): Interceptor = object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val original: Request = chain.request()
            val request: Request = original.newBuilder()
                .build()
            return chain.proceed(request)
        }
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, okHttpClientBuilder: OkHttpClient.Builder): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(enableTls12OnPreLollipop(okHttpClientBuilder)!!.build())
            .build()
    }

    @Singleton
    @Provides
    fun provideApiInterface(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideLocationPasswordsRemote(apiService: ApiService): LocationPasswordsRemote {
        return LocationPasswordsRemoteImpl(apiService = apiService)
    }

    @Singleton
    @Provides
    fun provideTrackingPasswordsRemote(apiService: ApiService): TrackingPasswordsRemote {
        return TrackingPasswordsRemoteImpl(apiService = apiService)
    }

    @Singleton
    @Provides
    fun provideAuthRemote(apiService: ApiService): AuthRemote {
        return AuthRemoteImpl(apiService = apiService)
    }

    companion object {
        private const val REQUEST_TIMEOUT = 60
    }
}