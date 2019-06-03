package net.codecision.startask.permissions

import net.codecision.startask.permissions.model.PermissionRequestResult
import org.junit.Assert
import org.junit.Test

class PermissionRequestResultTest : BaseLibTest() {


    @Test
    fun getGranted_Result_Granted() {
        val permissionCheckResult = PermissionRequestResult.getGranted()
        Assert.assertEquals(PermissionRequestResult.GRANTED_RESULT, permissionCheckResult.result)
    }

    @Test
    fun getGranted_Result_Denied() {
        val permissionCheckResult = PermissionRequestResult.getDenied()
        Assert.assertEquals(PermissionRequestResult.DENIED_RESULT, permissionCheckResult.result)
    }

    @Test
    fun getGranted_Result_NeverAskAgain() {
        val permissionCheckResult = PermissionRequestResult.getNeverAskAgain()
        Assert.assertEquals(PermissionRequestResult.NEVER_ASK_AGAIN_RESULT, permissionCheckResult.result)
    }

    @Test
    fun getGranted_Result_IncorrectCode() {
        val permissionCheckResult = PermissionRequestResult.getIncorrectCode()
        Assert.assertEquals(-1, permissionCheckResult.result)
    }

    @Test
    fun onGranted_Granted_True() {
        PermissionRequestResult.getGranted()
                .onGranted {
                    assert(true)
                }.onDenied {
                    assert(false)
                }.onNeverAskAgain {
                    assert(false)
                }
    }

    @Test
    fun onGranted_Denied_True() {
        PermissionRequestResult.getDenied()
                .onGranted {
                    assert(false)
                }.onDenied {
                    assert(true)
                }.onNeverAskAgain {
                    assert(false)
                }
    }

    @Test
    fun onGranted_NeverAskAgain_True() {
        PermissionRequestResult.getNeverAskAgain()
                .onGranted {
                    assert(false)
                }.onDenied {
                    assert(false)
                }.onNeverAskAgain {
                    assert(true)
                }
    }


}