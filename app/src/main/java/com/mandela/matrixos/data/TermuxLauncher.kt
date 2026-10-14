package com.mandela.matrixos.data

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager

object TermuxLauncher {
    const val DEFAULT_COMMAND = "cd ~/mandela-builder && ./start.sh"
    private const val TERMUX_PACKAGE = "com.termux"

    data class LaunchResult(val launched: Boolean, val message: String)

    fun isInstalled(context: Context): Boolean = try {
        context.packageManager.getPackageInfo(TERMUX_PACKAGE, 0)
        true
    } catch (_: PackageManager.NameNotFoundException) { false }

    fun launch(context: Context, command: String = DEFAULT_COMMAND): LaunchResult {
        if (!isInstalled(context)) return LaunchResult(false, "Termux is not installed.")
        val service = Intent().apply {
            action = "com.termux.RUN_COMMAND"
            setClassName(TERMUX_PACKAGE, "com.termux.app.RunCommandService")
            putExtra("com.termux.RUN_COMMAND_PATH", "/data/data/com.termux/files/usr/bin/bash")
            putExtra("com.termux.RUN_COMMAND_ARGUMENTS", arrayOf("-lc", command))
            putExtra("com.termux.RUN_COMMAND_BACKGROUND", true)
            putExtra("com.termux.RUN_COMMAND_WORKDIR", "/data/data/com.termux/files/home")
        }
        if (context.checkSelfPermission("com.termux.permission.RUN_COMMAND") != PackageManager.PERMISSION_GRANTED) {
            return LaunchResult(
                false,
                "Termux Run commands permission is not granted. Grant com.termux.permission.RUN_COMMAND in Android app settings."
            )
        }

        return runCatching {
            val started = context.startService(service)
            if (started != null) {
                LaunchResult(true, "Command delivered to Termux. This does not prove the builder server started; waiting for /health is required.")
            } else {
                val launch = context.packageManager.getLaunchIntentForPackage(TERMUX_PACKAGE)
                if (launch != null) context.startActivity(launch)
                LaunchResult(
                    false,
                    "Termux did not confirm command delivery. " +
                        (if (launch != null) "Termux was opened; run the configured command manually. " else "") +
                        "Check allow-external-apps=true in ~/.termux/termux.properties."
                )
            }
        }.getOrElse {
            LaunchResult(
                false,
                "Termux command was refused: ${it.message ?: "permission denied"}. Check allow-external-apps=true in ~/.termux/termux.properties."
            )
        }
    }
}
