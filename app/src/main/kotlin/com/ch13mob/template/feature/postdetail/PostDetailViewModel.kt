package com.ch13mob.template.feature.postdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.ch13mob.template.feature.postdetail.navigation.PostIdArg
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val postId: Int = savedStateHandle[PostIdArg] ?: -1
}
