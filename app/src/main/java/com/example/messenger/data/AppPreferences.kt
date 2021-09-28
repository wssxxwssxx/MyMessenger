package com.example.messenger.data

import android.content.Context
import android.content.SharedPreferences
import com.example.messenger.data.vo.UserVo

//Конструктор приватный, чтоб каждый класс который применяет данный класс использовал метод create()
class AppPreferences private constructor(){
    private lateinit var preferences: SharedPreferences

    companion object{
        private val PREFERENCES_FILE_NAME = "APP_PREFERENCES"
        fun create(context: Context): AppPreferences{
            val appPreferences = AppPreferences()
            appPreferences.preferences = context.getSharedPreferences(PREFERENCES_FILE_NAME,0)
            return appPreferences
        }
    }

    //Сохраняем токен доступа полученный с удаленного сервера
    val accessToken: String?
    get() = preferences.getString("ACCESS_TOKEN",null)
    fun storeAccessToken(accessToken: String){
        preferences.edit().putString("ACCESS_TOKEN",accessToken).apply()
    }

    val userDetails: UserVo
    get(): UserVo{
        return UserVo (
            preferences.getString("CREATED_AT",null),
            preferences.getString("STATUS",null),
            preferences.getString("PHONE_NUMBER",null),
            preferences.getString("USERNAME",null),
            preferences.getLong("ID",0)
            )
    }

    //Сохраняем в файле настроек объект
    fun  storeUserDetails(userVO: UserVo){
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putLong("ID",userVO.id).apply()
        editor.putString("USERNAME",userVO.username).apply()
        editor.putString("PHONE_NUMBER",userVO.phoneNumber).apply()
        editor.putString("STATUS",userVO.status).apply()
        editor.putString("CREATED_AT",userVO.createdAt).apply()
    }

    fun clear(){
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.clear()
        editor.apply()
    }
}