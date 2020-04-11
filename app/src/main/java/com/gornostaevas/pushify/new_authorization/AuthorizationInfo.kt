package com.gornostaevas.pushify.new_authorization

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import com.gornostaevas.pushify.social_nets.SupportedNetworks

@Deprecated("wWas created as part of exploring capabilites of android navigation, now redundant")
class AuthorizationInfo() : Parcelable {
    // used to identify what kind of data we have here
    lateinit var argType : SupportedNetworks
    // used to retrieve the data, implementation-specific
    var bundle: Bundle? = null

    constructor(incom : Parcel) : this() {
        argType = incom.readInt().let { SupportedNetworks.values()[it] }
        bundle = incom.readBundle()
    }

    constructor(argType: SupportedNetworks, addit : Bundle?) : this() {
        this.argType = argType
        this.bundle = addit
    }


    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        out.writeInt(argType.ordinal)
        out.writeBundle(bundle)
    }

    val CREATOR: Parcelable.Creator<AuthorizationInfo?> =
        object : Parcelable.Creator<AuthorizationInfo?> {
            override fun createFromParcel(incom: Parcel): AuthorizationInfo? {
                return AuthorizationInfo(incom)
            }

            override fun newArray(size: Int): Array<AuthorizationInfo?> {
                return arrayOfNulls<AuthorizationInfo>(size)
            }
        }
}