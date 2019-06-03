package net.codecision.startask.permissions.model

class PermissionCheckResult private constructor(
        val result: Int
) {

    inline fun onGranted(action: () -> Unit): PermissionCheckResult {
        if (result == GRANTED_RESULT) {
            action()
        }
        return this
    }

    inline fun onShowRationale(action: () -> Unit): PermissionCheckResult {
        if (result == SHOW_RATIONALE_RESULT) {
            action()
        }
        return this
    }

    inline fun onRequiredRequest(action: () -> Unit): PermissionCheckResult {
        if (result == REQUIRED_REQUEST_RESULT) {
            action()
        }
        return this
    }

    companion object {

        /** Result: The permission is granted. */
        const val GRANTED_RESULT = 0

        /** Result: The permission is show rationale. */
        const val SHOW_RATIONALE_RESULT = 1

        /** Result: The permission need request. */
        const val REQUIRED_REQUEST_RESULT = 3

        fun getGranted() = PermissionCheckResult(GRANTED_RESULT)

        fun getShowRationale() = PermissionCheckResult(SHOW_RATIONALE_RESULT)

        fun getRequiredRequest() = PermissionCheckResult(REQUIRED_REQUEST_RESULT)

    }

}
