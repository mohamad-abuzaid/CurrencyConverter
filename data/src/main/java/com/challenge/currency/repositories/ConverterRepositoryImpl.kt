package com.challenge.currency.repositories

import com.challenge.currency.remote.api.ConverterApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ConverterRepositoryImpl @Inject constructor(
    private val converterApi: ConverterApi
    ) : ConverterRepository {

    override fun getRockets(): Flow<List<Rocket>> {
        return rocketDao
            .getRockets()
            .map { rocketsCached ->
                rocketsCached.map { it.toDomainModel() }
            }
            .onEach { rockets ->
                if (rockets.isEmpty()) {
                    refreshRockets()
                }
            }
    }

    override suspend fun refreshRockets() {
        rocketApi
            .getRockets()
            .map {
                it.toDomainModel().toEntityModel()
            }
            .also {
                rocketDao.saveRockets(it)
            }
    }
}
