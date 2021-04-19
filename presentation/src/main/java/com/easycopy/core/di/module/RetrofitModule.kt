package com.easycopy.core.di.module

import android.content.Context
import com.easycopy.BuildConfig
import com.easycopy.constants.AppConstants
import com.easycopy.core.di.qualifier.ApplicationContext
import com.easycopy.core.di.qualifier.MooLiteBaseUrl
import com.easycopy.data.Constant
import com.easycopy.data.device.WSConnectorImpl
import com.easycopy.use_case.WSConnector
import com.fasterxml.jackson.databind.ObjectMapper
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by pankaj on 27,June,2019
 * pankaj.sharma@stellapps.com
 * Stellapps Technologies Private Limited
 * Bangalore, India
 */
@Module
class RetrofitModule {
    @Provides
    @MooLiteBaseUrl
    fun provideBaseUrl(): String {
        return if (BuildConfig.DEBUG) AppConstants.BASE_DOMAIN_DEBUG else AppConstants.BASE_DOMAIN_RELEASE
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        /*try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socketCommon factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }*/
        /* builder.callTimeout(0, TimeUnit.MILLISECONDS)   //0 means infinite
                .connectTimeout(6, TimeUnit.MINUTES)
                .readTimeout(6, TimeUnit.MINUTES)
                .writeTimeout(6, TimeUnit.MINUTES);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        builder.addInterceptor(logging);*/return builder.build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(@MooLiteBaseUrl baseUrl: String, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client) //.addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    @Provides
    @Singleton
    fun providesWSConnector(@ApplicationContext context: Context): WSConnector {
        return WSConnectorImpl(Constant.BASE_URL, context)
    }

    @Provides
    @Singleton
    fun providesObjectMapper():ObjectMapper {
        return ObjectMapper();
    }
}