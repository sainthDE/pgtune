package de.sainth.pgtune

class CheckPointCompletionTarget(systemConfiguration: SystemConfiguration) : PgConfigurationParameter("checkpoint_completion_target") {
    private val checkpointCompletionTarget: Double = when (systemConfiguration.dbApplication) {
        DbApplication.WEB -> 0.7
        DbApplication.OLTP -> 0.9
        DbApplication.DATA_WAREHOUSE -> 0.9
        DbApplication.DESKTOP -> 0.5
        DbApplication.MIXED -> 0.9
    }

    override fun getParameterString(): String {
        return "$checkpointCompletionTarget"
    }
}
