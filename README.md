# StartaskPermissions
[![JitPack](https://jitpack.io/v/illiashenkoo/startask-permissions.svg)](https://jitpack.io/#illiashenkoo/startask-permissions)

StartaskPermissions is a library that helps to handle runtime permissions on Android, entirely written using Kotlin language.

![](https://raw.githubusercontent.com/illiashenkoo/startask-permissions/master/images/logo.png)
## Using in your projects

### ![Gradle](https://raw.githubusercontent.com/illiashenkoo/startask-permissions/master/images/ic_gradle.png) Gradle

The library is published to [JitPack](https://jitpack.io/#illiashenkoo/startask-permissions) repository.

1. Add the JitPack repository to your root build.gradle at the end of repositories.
```groovy
allprojects {
    repositories {
        //...
        maven { url 'https://jitpack.io' }
    }
}
```

2. Add the dependency

`${latest.version}` is [![](https://jitpack.io/v/illiashenkoo/startask-permissions.svg)](https://jitpack.io/#illiashenkoo/startask-permissions)

```groovy
dependencies {
    implementation "com.github.illiashenkoo:startask-permissions:${latest.version}"
}
```

### ![Kotlin](https://raw.githubusercontent.com/illiashenkoo/startask-permissions/master/images/ic_kotlin.png) Usage with Kotlin

1. Add the following line to AndroidManifest.xml:
```xml
<uses-permission android:name="android.permission.CAMERA" />
```

2. Create a `Permission` object
``` kotlin
private val permission: Permission by lazy {
    Permission.Builder(Manifest.permission.CAMERA)
            .setRequestCode(MY_PERMISSIONS_REQUEST_CODE)
            .build()
}
```

3. Check and request permission if needed

``` kotlin
permission.check(this)
        .onGranted {
            // All requested permissions are granted
        }.onShowRationale {
            // Provide an explanation if the user has already denied that permission request
        }
```

4. Delegate the permission handling to library
``` kotlin
override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    permission.onRequestPermissionsResult(this, requestCode, grantResults)
            .onGranted {
                // All requested permissions are granted
            }.onDenied {
                // Oops, some of the permissions are denied
            }.onNeverAskAgain {
                // Oops, some of the permissions are denied
                // User chose "never ask again" about a permission
            }
}
```

[Look at the examples of using the library](https://github.com/illiashenkoo/startask-permissions/blob/master/sample/src/main/java/net/codecision/startask/permissions/sample/PermissionsActivity.kt)

## License

[Apache License 2.0](https://github.com/illiashenkoo/startask-permissions/blob/master/LICENSE)

## Contacts

[Oleg Illiashenko](mailto:illiashenkoo.dev@gmail.com)

## Contributions and releases

All development (both new features and bug fixes) is performed in `develop` branch. 
This way `master` sources always contain sources of the most recently released version.
Please send PRs with bug fixes to `develop` branch.
Fixes to documentation in markdown files are an exception to this rule. They are updated directly in `master`.
                                                                          
The `develop` branch is pushed to `master` during release.