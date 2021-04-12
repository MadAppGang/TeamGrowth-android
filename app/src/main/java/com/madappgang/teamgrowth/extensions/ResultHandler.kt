package com.madappgang.teamgrowth.extensions

import android.security.keystore.UserNotAuthenticatedException
import com.madappgang.identifolib.entities.Error
import com.madappgang.identifolib.entities.ErrorResponse
import com.madappgang.identifolib.extensions.Result
import retrofit2.HttpException
import retrofit2.Response


/*
 * Created by Eugene Prytula on 4/9/21.
 * Copyright (c) 2021 MadAppGang. All rights reserved.
 */

suspend fun <T> suspendApiCall(requestFunc: suspend () -> T): Result<T, ErrorResponse> {
    // TODO: Improve error handling
    return try {
        val request = requestFunc.invoke()
        Result.Success(request)
    } catch (e: Throwable) {
        val error = ErrorResponse(
            Error(
                message = e.localizedMessage.toString(),
                detailedMessage = e.message.toString()
            )
        )
        return Result.Error(error)
    }
}