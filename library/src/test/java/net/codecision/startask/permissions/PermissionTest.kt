package net.codecision.startask.permissions

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.PermissionChecker
import net.codecision.startask.permissions.model.PermissionCheckResult
import net.codecision.startask.permissions.model.PermissionRequestResult
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.M])
class PermissionTest : BaseLibTest() {

    lateinit var activity: Activity

    @Before
    fun setUp() {
        val activityController = Robolectric.buildActivity(Activity::class.java)
        activity = Mockito.spy(activityController.setup().get())
    }

    @Test
    @Throws(Exception::class)
    fun checkActivity_NotNull_True() {
        Assert.assertNotNull(activity)
    }

    @Test
    fun checkActivity_Granted_GrantedResult() {
        Mockito.`when`(PermissionUtils.checkSelfPermission(getContext(), CAMERA_PERMISSION))
                .thenReturn(PermissionChecker.PERMISSION_GRANTED)

        val permissionCheckResult = Permission.Builder(CAMERA_PERMISSION)
                .build()
                .check(activity)
        assertEquals(PermissionCheckResult.GRANTED_RESULT, permissionCheckResult.result)
    }

    @Test
    fun isGranted_Granted_True() {
        Mockito.`when`(PermissionUtils.checkSelfPermission(getContext(), CAMERA_PERMISSION))
                .thenReturn(PermissionChecker.PERMISSION_GRANTED)

        assert(Permission.Builder(CAMERA_PERMISSION).build().isGranted(activity))
    }

    @Test
    fun onRequestPermissionsResult_Granted() {
        val permissionRequestResult = Permission.Builder(CAMERA_PERMISSION)
                .build()
                .onRequestPermissionsResult(
                        activity,
                        Permission.PERMISSIONS_REQUEST_CODE,
                        IntArray(1) { PackageManager.PERMISSION_GRANTED }
                )
        assertEquals(PermissionRequestResult.GRANTED_RESULT, permissionRequestResult.result)
    }

    private fun getContext() = activity as Context

}