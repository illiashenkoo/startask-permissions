package net.codecision.startask.permissions

import android.app.Activity
import androidx.fragment.app.Fragment
import net.codecision.startask.permissions.model.PermissionCheckResult
import net.codecision.startask.permissions.model.PermissionRequestResult


class Permission private constructor(
        private val requestCode: Int,
        private val shouldShowRationale: Boolean,
        private val shouldRequestAutomatically: Boolean,
        private val permissions: Array<out String>
) {

    /**
     * Checks whether your app has a given permission and whether the app op
     * that corresponds to this permission is allowed.
     *
     * @param activity: Activity for accessing resources.
     * @return The permission check result {@link PermissionCheckResult}
     */
    fun check(activity: Activity): PermissionCheckResult {
        return PermissionUtils.checkPermissions(
                activity,
                permissions,
                requestCode,
                shouldShowRationale,
                shouldRequestAutomatically
        )
    }

    /**
     * Checks whether your app has a given permission and whether the app op
     * that corresponds to this permission is allowed.
     *
     * @param fragment: Fragment for accessing resources.
     * @return The permission check result {@link PermissionCheckResult}
     */
    fun check(fragment: Fragment): PermissionCheckResult {
        return PermissionUtils.checkPermissions(
                fragment,
                permissions,
                requestCode,
                shouldShowRationale,
                shouldRequestAutomatically
        )
    }

    /**
     * Requests permissions to be granted to this application.
     *
     * @param activity: Activity for accessing resources.
     */
    fun request(activity: Activity) {
        return PermissionUtils.requestPermissions(activity, permissions, requestCode)
    }

    /**
     * Requests permissions to be granted to this application.
     *
     * @param fragment: Fragment for accessing resources.
     */
    fun request(fragment: Fragment) {
        return PermissionUtils.requestPermissions(fragment, permissions, requestCode)
    }

    /**
     * Checks whether your app has a given permission
     *
     * @param activity: Activity for accessing resources.
     * @return The permission check result which is either {true or false}.
     */
    fun isGranted(activity: Activity): Boolean {
        return PermissionUtils.isGranted(activity, permissions)
    }

    /**
     * Checks whether your app has a given permission
     *
     * @param fragment: Fragment for accessing resources.
     * @return The permission check result which is either {true or false}.
     */
    fun isGranted(fragment: Fragment): Boolean {
        return PermissionUtils.isGranted(fragment, permissions)
    }

    /**
     * Forwarding the result from requesting permissions.
     *
     * @param fragment: Fragment for accessing resources.
     * @param requestCode
     * @param grantResults
     */
    fun onRequestPermissionsResult(
            fragment: Fragment,
            requestCode: Int,
            grantResults: IntArray
    ): PermissionRequestResult {
        return if (requestCode == this.requestCode) {
            PermissionUtils.onRequestPermissionsResult(fragment, grantResults, permissions)
        } else {
            PermissionRequestResult.getIncorrectCode()
        }
    }

    /**
     * Forwarding the result from requesting permissions.
     *
     * @param activity: Activity for accessing resources.
     * @param requestCode
     * @param grantResults
     */
    fun onRequestPermissionsResult(
            activity: Activity,
            requestCode: Int,
            grantResults: IntArray
    ): PermissionRequestResult {
        return if (requestCode == this.requestCode) {
            PermissionUtils.onRequestPermissionsResult(activity, grantResults, permissions)
        } else {
            PermissionRequestResult.getIncorrectCode()
        }
    }

    class Builder(private vararg val permissions: String) {

        private var requestCode: Int = PERMISSIONS_REQUEST_CODE

        private var shouldShowRationale = true

        private var shouldRequestAutomatically = true

        fun setRequestCode(requestCode: Int): Builder {
            this.requestCode = requestCode
            return this
        }

        fun setShouldShowRationale(shouldShowRationale: Boolean): Builder {
            this.shouldShowRationale = shouldShowRationale
            return this
        }

        fun setShouldRequestAutomatically(shouldRequestAutomatically: Boolean): Builder {
            this.shouldRequestAutomatically = shouldRequestAutomatically
            return this
        }

        fun build(): Permission {
            return if (permissions.isNotEmpty()) {
                Permission(requestCode, shouldShowRationale, shouldRequestAutomatically, permissions)
            } else {
                throw  IllegalArgumentException("Require one or more permission!")
            }
        }

    }

    companion object {

        const val PERMISSIONS_REQUEST_CODE = 43

    }

}