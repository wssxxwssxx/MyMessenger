package com.example.messenger.ui.settings

import android.content.Context
import android.preference.EditTextPreference
import android.text.TextUtils
import android.util.AttributeSet
import android.widget.Toast
import com.example.messenger.data.local.AppPreferences
import com.example.messenger.data.remote.request.StatusUpdateRequestObject
import com.example.messenger.service.MessengerApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author Iyanu Adelekan. 01/11/2017.
 */
class ProfileStatusPreference(context: Context, attributeSet: AttributeSet) : EditTextPreference(context, attributeSet) {

    private val service: MessengerApiService = MessengerApiService.getInstance()
    private val preferences: AppPreferences = AppPreferences.create(context)

    override fun onDialogClosed(positiveResult: Boolean) {
        if (positiveResult) {
            // bind ProfileStatusPreference's EditText to etStatus variable
            val etStatus = editText

            if (TextUtils.isEmpty(etStatus.text)) {
                // Display error message when user tries to submit an empty status.
                Toast.makeText(context, "Status cannot be empty.", Toast.LENGTH_LONG).show()

            } else {
                val requestObject = StatusUpdateRequestObject(etStatus.text.toString())

                // use MessengerApiService to update the user's status
                service.updateUserStatus(requestObject,  preferences.accessToken as String)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ res ->
                            // store updated user details if status update is successful
                            preferences.storeUserDetails(res) },
                                { error ->
                                    Toast.makeText(context, "Unable to update status at the " +
                                            "moment. Try again later.", Toast.LENGTH_LONG).show()
                                    error.printStackTrace()})
            }
        }

        super.onDialogClosed(positiveResult)
    }
}