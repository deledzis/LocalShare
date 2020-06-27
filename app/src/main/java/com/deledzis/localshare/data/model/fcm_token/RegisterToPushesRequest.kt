package com.deledzis.localshare.data.model.fcm_token

import com.google.gson.annotations.SerializedName

data class RegisterToPushesRequest(
    val name: String? = null,
    val active: String? = "true",
    @SerializedName("device_id")
    val deviceId: String? = null,
    @SerializedName("registration_id")
    val registrationId: String? = null,
    @SerializedName("cloud_message_type")
    val cloudMessageType: String? = "FCM"
)