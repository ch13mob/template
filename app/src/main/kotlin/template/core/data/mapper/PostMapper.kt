package template.core.data.mapper

import template.core.database.model.PostEntity
import template.core.model.Post
import template.core.network.model.NetworkPost

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
