package org.itmo.itmoevent

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import org.itmo.itmoevent.network.repository.AuthRepository
import org.itmo.itmoevent.network.repository.ProfileRepository
import org.itmo.itmoevent.network.api.AuthControllerApi
import org.itmo.itmoevent.network.api.ProfileControllerApi

@Module
@InstallIn(ViewModelComponent::class)
class HiltModule {
    @Provides
    fun provideAuthRepository(authApiService: AuthControllerApi) = AuthRepository(authApiService)

    @Provides
    fun provideProfileRepository(profileApiService: ProfileControllerApi) = ProfileRepository(profileApiService)
}