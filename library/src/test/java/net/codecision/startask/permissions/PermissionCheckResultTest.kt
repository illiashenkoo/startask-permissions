package net.codecision.startask.permissions

import net.codecision.startask.permissions.model.PermissionCheckResult
import org.junit.Assert.assertEquals
import org.junit.Test

class PermissionCheckResultTest : BaseLibTest() {

    @Test
    fun getGranted_Result_Granted() {
        val permissionCheckResult = PermissionCheckResult.getGranted()
        assertEquals(PermissionCheckResult.GRANTED_RESULT, permissionCheckResult.result)
    }

    @Test
    fun getGranted_Result_ShowRationale() {
        val permissionCheckResult = PermissionCheckResult.getShowRationale()
        assertEquals(PermissionCheckResult.SHOW_RATIONALE_RESULT, permissionCheckResult.result)
    }

    @Test
    fun getGranted_Result_RequiredRequest() {
        val permissionCheckResult = PermissionCheckResult.getRequiredRequest()
        assertEquals(PermissionCheckResult.REQUIRED_REQUEST_RESULT, permissionCheckResult.result)
    }

    @Test
    fun onGranted_Granted_True() {
        PermissionCheckResult.getGranted()
                .onGranted {
                    assert(true)
                }
                .onShowRationale {
                    assert(false)
                }
                .onRequiredRequest {
                    assert(false)
                }
    }

    @Test
    fun onGranted_ShowRationale_True() {
        PermissionCheckResult.getShowRationale()
                .onGranted {
                    assert(false)
                }
                .onShowRationale {
                    assert(true)
                }
                .onRequiredRequest {
                    assert(false)
                }
    }

    @Test
    fun onGranted_RequiredRequest_True() {
        PermissionCheckResult.getRequiredRequest()
                .onGranted {
                    assert(false)
                }
                .onShowRationale {
                    assert(false)
                }
                .onRequiredRequest {
                    assert(true)
                }
    }

}