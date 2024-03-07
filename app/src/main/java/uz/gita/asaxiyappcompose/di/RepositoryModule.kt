package uz.gita.asaxiyappcompose.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.asaxiyappcompose.domain.AppRepository
import uz.gita.asaxiyappcompose.domain.AppRepositoryImp

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindRepository(imp: AppRepositoryImp) : AppRepository
}