package template.feature.postdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import template.navigation.Screen
import template.navigation.Screen.PostDetail.Companion.POST_ID_ARG
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val postDetailRoute: Screen.PostDetail = savedStateHandle.toRoute()

    val postId: StateFlow<Int> = savedStateHandle.getStateFlow(
        key = POST_ID_ARG,
        initialValue = postDetailRoute.postId
    )
}
