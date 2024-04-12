package org.itmo.itmoevent

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import org.itmo.itmoevent.network.repository.AuthRepository
import org.itmo.itmoevent.network.repository.ProfileRepository
import org.itmo.itmoevent.network.api.AuthControllerApi
import org.itmo.itmoevent.network.api.EventControllerApi
import org.itmo.itmoevent.network.api.NotificationControllerApi
import org.itmo.itmoevent.network.api.PlaceControllerApi
import org.itmo.itmoevent.network.api.ProfileControllerApi
import org.itmo.itmoevent.network.api.RoleControllerApi
import org.itmo.itmoevent.network.api.TaskControllerApi
import org.itmo.itmoevent.network.repository.EventRepository
import org.itmo.itmoevent.network.repository.NotificationRepository
import org.itmo.itmoevent.network.repository.PlaceRepository
import org.itmo.itmoevent.network.repository.RoleRepository
import org.itmo.itmoevent.network.repository.TaskRepository

@Module
@InstallIn(ViewModelComponent::class)
class HiltModule {
    @Provides
    fun provideAuthRepository(authApiService: AuthControllerApi) = AuthRepository(authApiService)

    @Provides
    fun provideProfileRepository(profileApiService: ProfileControllerApi) = ProfileRepository(profileApiService)

    @Provides
    fun providePlaceRepository(placeApiService: PlaceControllerApi) = PlaceRepository(placeApiService)

    @Provides
    fun provideNotificationRepository(notificationApi: NotificationControllerApi) = NotificationRepository(notificationApi)

    @Provides
    fun provideEventRepository(eventApi: EventControllerApi) = EventRepository(eventApi)

    @Provides
    fun provideRoleRepository(roleApi: RoleControllerApi) = RoleRepository(roleApi)

    @Provides
    fun provideTaskRepository(taskApi: TaskControllerApi) = TaskRepository(taskApi)
}