package net.codecision.startask.permissions

import android.app.Activity
import android.content.Context
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import net.codecision.startask.permissions.model.PermissionCheckResult
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.spy
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.M])
class PermissionUtilsTest : BaseLibTest() {

    lateinit var activity: Activity

    @Before
    fun setUp() {
        val activityController = Robolectric.buildActivity(Activity::class.java)
        activity = spy(activityController.setup().get())
    }

    @Test
    @Throws(Exception::class)
    fun checkActivity_NotNull_True() {
        assertNotNull(activity)
    }

    @Test
    fun checkIsLessMarshmallow_22_True() {
        changeSdkVersion(Build.VERSION_CODES.LOLLIPOP_MR1)
        assert(PermissionUtils.isLessMarshmallow())
    }

    @Test
    fun checkIsLessMarshmallow_23_False() {
        changeSdkVersion(Build.VERSION_CODES.M)
        assertFalse(PermissionUtils.isLessMarshmallow())
    }

    @Test
    fun checkIsLessMarshmallow_24_False() {
        changeSdkVersion(Build.VERSION_CODES.N)
        assertFalse(PermissionUtils.isLessMarshmallow())
    }

    @Test
    fun isGranted_Granted_True() {
        `when`(PermissionUtils.checkSelfPermission(getContext(), CAMERA_PERMISSION))
                .thenReturn(PermissionChecker.PERMISSION_GRANTED)
        assert(PermissionUtils.isGranted(activity, arrayOf(CAMERA_PERMISSION)))
    }

    @Test
    fun isGranted_Denied_False() {
        `when`(PermissionUtils.checkSelfPermission(getContext(), CAMERA_PERMISSION))
                .thenReturn(PermissionChecker.PERMISSION_DENIED)
        assertFalse(PermissionUtils.isGranted(activity, arrayOf(CAMERA_PERMISSION)))
    }

    @Test
    fun isGranted_Exception_False() {
        `when`(PermissionUtils.checkSelfPermission(getContext(), CAMERA_PERMISSION))
                .thenThrow(RuntimeException::class.java)
        assertFalse(PermissionUtils.isGranted(activity, arrayOf(CAMERA_PERMISSION)))
    }

    @Test
    fun checkPermissions_GrantedShowRationale_GrantedResult() {
        `when`(PermissionUtils.checkSelfPermission(getContext(), CAMERA_PERMISSION))
                .thenReturn(PermissionChecker.PERMISSION_GRANTED)

        val permissionCheckResult = PermissionUtils.checkPermissions(
                activity,
                arrayOf(CAMERA_PERMISSION),
                PERMISSIONS_REQUEST_CODE,
                true,
                true
        )

        assertEquals(PermissionCheckResult.GRANTED_RESULT, permissionCheckResult.result)
    }

    @Test
    fun checkPermissions_GrantedNotShowRationale_GrantedResult() {
        `when`(PermissionUtils.checkSelfPermission(getContext(), CAMERA_PERMISSION))
                .thenReturn(PermissionChecker.PERMISSION_GRANTED)

        val permissionCheckResult = PermissionUtils.checkPermissions(
                activity,
                arrayOf(CAMERA_PERMISSION),
                PERMISSIONS_REQUEST_CODE,
                false,
                true
        )

        assertEquals(PermissionCheckResult.GRANTED_RESULT, permissionCheckResult.result)
    }

    @Test
    fun checkPermissions_DeniedShowRationale_True() {
        `when`(PermissionUtils.checkSelfPermission(getContext(), CAMERA_PERMISSION))
                .thenReturn(PermissionChecker.PERMISSION_DENIED)

        `when`(ActivityCompat.shouldShowRequestPermissionRationale(activity, CAMERA_PERMISSION))
                .thenReturn(true)

        val permissionCheckResult = PermissionUtils.checkPermissions(
                activity,
                arrayOf(CAMERA_PERMISSION),
                PERMISSIONS_REQUEST_CODE,
                true,
                true
        )

        assertEquals(PermissionCheckResult.SHOW_RATIONALE_RESULT, permissionCheckResult.result)
    }

    @Test
    fun checkPermissions_DeniedNotShowRationale_True() {
        `when`(PermissionUtils.checkSelfPermission(getContext(), CAMERA_PERMISSION))
                .thenReturn(PermissionChecker.PERMISSION_DENIED)

        `when`(ActivityCompat.shouldShowRequestPermissionRationale(activity, CAMERA_PERMISSION))
                .thenReturn(true)

        val permissionCheckResult = PermissionUtils.checkPermissions(
                activity,
                arrayOf(CAMERA_PERMISSION),
                PERMISSIONS_REQUEST_CODE,
                false,
                true
        )

        assertEquals(PermissionCheckResult.REQUIRED_REQUEST_RESULT, permissionCheckResult.result)
    }

    @Test
    fun verifyPermissionsResult_GrantedOne_True() {
        assertTrue(PermissionUtils.verifyPermissionsResult(IntArray(1) { GRANTED }))
    }

    @Test
    fun verifyPermissionsResult_GrantedTwo_True() {
        assertTrue(PermissionUtils.verifyPermissionsResult(IntArray(2) { GRANTED; GRANTED }))
    }

    @Test
    fun verifyPermissionsResult_GrantedZero_False() {
        assertFalse(PermissionUtils.verifyPermissionsResult(IntArray(0)))
    }

    @Test
    fun verifyPermissionsResult_Denied_False() {
        assertFalse(PermissionUtils.verifyPermissionsResult(IntArray(1) { DENIED }))
    }

    @Test
    fun verifyPermissionsResult_GrantedDenied_False() {
        assertFalse(PermissionUtils.verifyPermissionsResult(IntArray(2) { GRANTED; DENIED }))
    }

    private fun getContext() = activity as Context

    private fun changeSdkVersion(sdkVersion: Int) = setFinalStatic(getSdkVersionField(), sdkVersion)

}