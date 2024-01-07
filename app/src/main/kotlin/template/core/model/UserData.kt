package template.core.model

data class UserData(
    val email: String?
) {
    val isLoggedIn: Boolean
        get() = email.isNullOrEmpty().not()
}
