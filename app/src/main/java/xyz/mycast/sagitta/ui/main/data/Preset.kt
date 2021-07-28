package xyz.mycast.sagitta.ui.main.data

data class Preset(val title: String, val body: String) {
    companion object {
        val DEFAULT_LIST = listOf(
            Preset("title1", "body1"),
            Preset("title2", "body2"),
        )
    }
}
