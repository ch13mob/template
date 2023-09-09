package com.ch13mob.template.core.data.mapper

import com.ch13mob.template.core.database.model.PostEntity
import com.ch13mob.template.core.model.Post
import com.ch13mob.template.core.network.model.NetworkPost

fun NetworkPost.toEntity() = PostEntity(
    id = this.id,
    userId = this.userId,
    title = this.title,
    body = this.body
)

fun PostEntity.toModel() = Post(
    id = this.id,
    userId = this.userId,
    title = this.title,
    body = this.body
)
