package com.caavo.recipeapp.viewmodels

import androidx.lifecycle.*
import com.caavo.recipeapp.data.FirebaseHelper
import com.caavo.recipeapp.data.RoomDB
import com.caavo.recipeapp.models.ResponseBody
import com.caavo.recipeapp.models.STATE_FAILED
import com.caavo.recipeapp.models.STATE_SUCCESSFUL
import com.caavo.recipeapp.models.UserObject
import kotlinx.coroutines.launch
import java.lang.Exception

public class AuthViewModel(

    val firebase: FirebaseHelper,
    val roomDB: RoomDB
) : ViewModel() {



    private val authStatus = MutableLiveData<Boolean>()
    private val signInWithEmailAndPassword = MutableLiveData<ResponseBody<UserObject>>()
    private val createWithEmailAndPassword = MutableLiveData<ResponseBody<UserObject>>()



    // Room

    private fun saveProfileToRoom(account: UserObject) = viewModelScope.launch { roomDB.insert(account) }


    fun fetchAuthStatus() {
        authStatus.postValue(firebase.isAuthenticated())
    }


    fun signInWithEmailPassword(email: String, password: String) {

        try {

            signInWithEmailAndPassword.postValue(ResponseBody<UserObject>().loading())

            firebase.signInWithEmailAndPassword(
                email,
                password,
                {
                    fetchUserData()
                },
                {
                    logout()
                    signInWithEmailAndPassword.postValue(ResponseBody(STATE_FAILED, it.message.toString(), null))
                })
        }
        catch (e: Exception) {
            logout()
            signInWithEmailAndPassword.postValue(ResponseBody(STATE_FAILED, e.message.toString(), null))
        }
    }


    private fun fetchUserData() {

        try {

            signInWithEmailAndPassword.postValue(ResponseBody<UserObject>().loading())

            firebase.fetchUserData(
                {
                    saveProfileToRoom(it.toObject(UserObject::class.java) as UserObject)
                    authStatus.postValue((firebase.isAuthenticated()))
                    signInWithEmailAndPassword.postValue(ResponseBody(STATE_SUCCESSFUL, "", it.toObject(UserObject::class.java)))

                },
                {
                    logout()
                    signInWithEmailAndPassword.postValue(ResponseBody(STATE_FAILED, it.message.toString(), null))
                })
        }
        catch (e: Exception) {
            logout()
            signInWithEmailAndPassword.postValue(ResponseBody(STATE_FAILED, e.message.toString(), null))
        }
    }


    fun createAccountWithEmailPassword(profile: UserObject, password : String) {

        try {

            createWithEmailAndPassword.postValue(ResponseBody<UserObject>().loading())

            firebase.createUserWithEmailAndPassword(
                profile.email,
                password,
                {
                    postUserData(UserObject(name = profile.name, email = profile.email))
                },
                {
                    logout()
                    createWithEmailAndPassword.postValue(ResponseBody(STATE_FAILED, it.message.toString(), null))
                })
        }
        catch (e: Exception) {
            logout()
            createWithEmailAndPassword.postValue(ResponseBody(STATE_FAILED, e.message.toString(), null))
        }
    }


    private fun postUserData(userProfile: UserObject) {

        try {

            createWithEmailAndPassword.postValue(ResponseBody<UserObject>().loading())

            firebase.uploadUserData(
                userProfile,
                {
                    saveProfileToRoom(userProfile)
                    authStatus.postValue((firebase.isAuthenticated()))
                    createWithEmailAndPassword.postValue(ResponseBody(STATE_SUCCESSFUL, "", null))
                },
                {
                    firebase.signOut()
                    createWithEmailAndPassword.postValue(ResponseBody(STATE_FAILED, it.message.toString(), null))
                })
        }
        catch (e: Exception) {
            logout()
            createWithEmailAndPassword.postValue(ResponseBody(STATE_FAILED, e.message.toString(), null))
        }
    }


    fun logout() {
        firebase.signOut()
//        deleteProfileFromRoom()
    }


    fun getAuthStatusLiveData() = authStatus
    fun getLoginInWithEmailLiveData() = signInWithEmailAndPassword
    fun getCreateAccountWithEmailLiveData() = createWithEmailAndPassword



}
