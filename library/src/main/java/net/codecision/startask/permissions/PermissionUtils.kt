package net.codecision.startask.permissions

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.VisibleForTesting
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment
import net.codecision.startask.permissions.model.PermissionCheckResult
import net.codecision.startask.permissions.model.PermissionRequestResult

class PermissionUtils {

    companion object {

        fun checkPermissions(
                activity: Activity,
                permissions: Array<out String>,
                requestCode: Int,
                shouldShowRationale: Boolean,
                shouldRequestAutomatically: Boolean
        ): PermissionCheckResult {
            return if (isGranted(activity, permissions)) {
                PermissionCheckResult.getGranted()
            } else {
                doOnDenied(activity, permissions, requestCode, shouldShowRationale, shouldRequestAutomatically)
            }
        }

        fun checkPermissions(
                fragment: Fragment,
                permissions: Array<out String>,
                requestCode: Int,
                shouldShowRationale: Boolean,
                shouldRequestAutomatically: Boolean): PermissionCheckResult {
            return if (isGranted(fragment, permissions)) {
                PermissionCheckResult.getGranted()
            } else {
                doOnDenied(fragment, permissions, requestCode, shouldShowRationale, shouldRequestAutomatically)
            }
        }

        fun isGranted(activity: Activity, permissions: Array<out String>) = isGranted(activity as Context, permissions)

        fun isGranted(fragment: Fragment, permissions: Array<out String>) = isGranted(fragment.requireContext(), permissions)

        fun requestPermissions(activity: Activity, permissions: Array<out String>, requestCode: Int) {
            ActivityCompat.requestPermissions(activity, permissions, requestCode)
        }

        fun requestPermissions(fragment: Fragment, permissions: Array<out String>, requestCode: Int) {
            fragment.requestPermissions(permissions, requestCode)
        }

        fun onRequestPermissionsResult(
                fragment: Fragment,
                grantResults: IntArray,
                permissions: Array<out String>
        ): PermissionRequestResult {
            return if (verifyPermissionsResult(grantResults)) {
                PermissionRequestResult.getGranted()
            } else {
                if (shouldShowRequestPermissionRationale(fragment, permissions)) {
                    PermissionRequestResult.getDenied()
                } else {
                    PermissionRequestResult.getNeverAskAgain()
                }
            }
        }

        fun onRequestPermissionsResult(
                activity: Activity,
                grantResults: IntArray,
                permissions: Array<out String>
        ): PermissionRequestResult {
            return if (verifyPermissionsResult(grantResults)) {
                PermissionRequestResult.getGranted()
            } else {
                if (shouldShowRequestPermissionRationale(activity, permissions)) {
                    PermissionRequestResult.getDenied()
                } else {
                    PermissionRequestResult.getNeverAskAgain()
                }
            }
        }

        private fun doOnDenied(
                activity: Activity,
                permissions: Array<out String>,
                requestCode: Int,
                shouldShowRationale: Boolean,
                shouldRequestAutomatically: Boolean
        ): PermissionCheckResult {
            return if (shouldShowRationale && shouldShowRequestPermissionRationale(activity, permissions)) {
                PermissionCheckResult.getShowRationale()
            } else {
                if (shouldRequestAutomatically) {
                    requestPermissions(activity, permissions, requestCode)
                }
                PermissionCheckResult.getRequiredRequest()
            }
        }

        private fun doOnDenied(
                fragment: Fragment,
                permissions: Array<out String>,
                requestCode: Int,
                shouldShowRationale: Boolean,
                shouldRequestAutomatically: Boolean
        ): PermissionCheckResult {
            return if (shouldShowRationale && shouldShowRequestPermissionRationale(fragment, permissions)) {
                PermissionCheckResult.getShowRationale()
            } else {
                if (shouldRequestAutomatically) {
                    requestPermissions(fragment, permissions, requestCode)
                }
                PermissionCheckResult.getRequiredRequest()
            }
        }

        @VisibleForTesting
        fun verifyPermissionsResult(grantResults: IntArray): Boolean {
            return grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }
        }

        private fun shouldShowRequestPermissionRationale(activity: Activity, permissions: Array<out String>): Boolean {
            return permissions.any { ActivityCompat.shouldShowRequestPermissionRationale(activity, it) }
        }

        private fun shouldShowRequestPermissionRationale(fragment: Fragment, permissions: Array<out String>): Boolean {
            return permissions.any { fragment.shouldShowRequestPermissionRationale(it) }
        }

        private fun isGranted(context: Context, permissions: Array<out String>): Boolean {
            return isLessMarshmallow() || hasSelfPermissions(context, permissions)
        }

        private fun hasSelfPermissions(context: Context, permissions: Array<out String>): Boolean {
            return permissions.all { hasSelfPermission(context, it) }
        }

        private fun hasSelfPermission(context: Context, permission: String): Boolean {
            return try {
                checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
            } catch (exception: RuntimeException) {
                exception.printStackTrace()
                false
            }
        }

        @VisibleForTesting
        @Throws(RuntimeException::class)
        fun checkSelfPermission(context: Context, permission: String): Int {
            return PermissionChecker.checkSelfPermission(context, permission)
        }

        @VisibleForTesting
        fun isLessMarshmallow(): Boolean {
            return Build.VERSION.SDK_INT < Build.VERSION_CODES.M
        }

    }
}

