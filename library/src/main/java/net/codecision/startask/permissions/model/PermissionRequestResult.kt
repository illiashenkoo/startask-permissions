package net.codecision.startask.permissions.model

class PermissionRequestResult private constructor(
        val result: Int
) {

    inline fun onGranted(action: () -> Unit): PermissionRequestResult {
        if (result == GRANTED_RESULT) {
            action()
        }
        return this
    }

    inline fun onDenied(action: () -> Unit): PermissionRequestResult {
        if (result == DENIED_RESULT) {
            action()
        }
        return this
    }

    inline fun onNeverAskAgain(action: () -> Unit): PermissionRequestResult {
        if (result == NEVER_ASK_AGAIN_RESULT) {
            action()
        }
        return this
    }

    companion object {

        /** Result: The permission is granted. */
        const val GRANTED_RESULT = 0

        /** Result: The permission is denied. */
        const val DENIED_RESULT = 1

        /** Result: The permission is never ask again. */
        const val NEVER_ASK_AGAIN_RESULT = 2

        private const val INCORRECT_CODE_RESULT = -1

        fun getGranted() = PermissionRequestResult(GRANTED_RESULT)

        fun getDenied() = PermissionRequestResult(DENIED_RESULT)

        fun getNeverAskAgain() = PermissionRequestResult(NEVER_ASK_AGAIN_RESULT)

        fun getIncorrectCode() = PermissionRequestResult(INCORRECT_CODE_RESULT)

    }

}