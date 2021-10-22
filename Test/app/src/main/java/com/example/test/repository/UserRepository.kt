package com.example.test.repository

import com.example.test.UserDetails
import com.example.test.networking.API
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepository(val api: API) {

    fun userDetails(id: Int): Flow<Result<UserDetails>>{
        return flow {
            val users = api.userDetails(id)
            emit(Result.success(users))
        }
    }


}

val mapOfUsers = HashMap<String, UserDetails>().apply {
    put("John", UserDetails())
    put("John", UserDetails())
    put("John", UserDetails())
    put("Irwan", UserDetails())
}

val users = listOf(UserDetails(), UserDetails(), UserDetails(), UserDetails("Irvwa"))


fun main() {

    val usersCount = users.groupBy { it.name }
    println(usersCount)
    println(usersCount.mapValues { (_, users) -> users.size })

}