package kg.geekstudio.core_utils.base

import android.util.Log
import com.example.testzavod.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException

abstract class BaseRepository {
    protected fun <T> doRequest(
        request: suspend () -> T
    ) = flow<Resource<T>> {
        emit(Resource.Success(request()))
    }.flowOn(Dispatchers.IO).catch { e ->
        val errorMessage = when {
            e is HttpException -> {
                when (e.code()) {
                    403 -> "Ошибка доступа: У вас нет разрешения на выполнение этого действия"
                    401 -> "Вы не авторизованы"
                    404 -> "Не найдено: Запрошенный ресурс не найден"
                    500 -> "Внутренняя ошибка сервера: Попробуйте позже"
                    503 -> "Сервис не доступен извините"
                    410 -> "Вы удалили аккаунт"
                    else -> "Произошла ошибка: ${e.code()}"
                }
            }

            else -> "Неизвестная ошибка"
        }
        Log.e("baseRep", "Error in doRequest: $errorMessage", e)
        emit(Resource.Error(errorMessage))
    }

    protected fun <T> listRequest(
        request: suspend () -> List<T>
    ) = flow<Resource<List<T>>> {
        emit(Resource.Success(request()))
    }.flowOn(Dispatchers.IO).catch { e ->
        val errorMessage = when {
            e is HttpException -> {
                when (e.code()) {
                    403 -> "Ошибка доступа: У вас нет разрешения на выполнение этого действия"
                    401 -> "Вы не авторизованы"
                    404 -> "Не найдено: Запрошенный ресурс не найден"
                    500 -> "Внутренняя ошибка сервера: Попробуйте позже"
                    503 -> "Сервис не доступен извините"
                    410 -> "Вы удалили аккаунт"
                    else -> "Произошла ошибка: ${e.code()}"
                }
            }

            else -> "Неизвестная ошибка"
        }
        Log.e("baseRep", "Error in listRequest: $errorMessage", e)
        emit(Resource.Error(errorMessage))
    }
}