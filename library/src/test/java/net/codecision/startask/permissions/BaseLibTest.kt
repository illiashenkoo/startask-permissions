package net.codecision.startask.permissions

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import java.lang.reflect.Field
import java.lang.reflect.Modifier

open class BaseLibTest {

    @Throws(NoSuchFieldException::class, IllegalAccessException::class, IllegalArgumentException::class)
    protected fun setFinalStatic(field: Field, newValue: Any) {
        field.isAccessible = true

        val modifiersField = Field::class.java.getDeclaredField("modifiers")
        modifiersField.isAccessible = true
        modifiersField.setInt(field, field.modifiers and Modifier.FINAL.inv())

        field.set(null, newValue)
    }

    protected inline fun d(message: () -> String) {
        println(message())
    }

    protected fun getSdkVersionField(): Field {
        return Build.VERSION::class.java.getField(SDK_VERSION_FIELD)
    }

    companion object {
        const val SDK_VERSION_FIELD = "SDK_INT"

        const val GRANTED = PackageManager.PERMISSION_GRANTED
        const val DENIED = PackageManager.PERMISSION_DENIED

        const val CAMERA_PERMISSION = Manifest.permission.CAMERA

        const val PERMISSIONS_REQUEST_CODE = 99
    }

}