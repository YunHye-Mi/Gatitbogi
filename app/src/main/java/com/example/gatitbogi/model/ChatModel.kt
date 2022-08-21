package com.example.gatitbogi.model

data class ChatModel(
    var users : MutableMap<String,Boolean>,
    var destinationUid : MutableMap<String,Comment>
) {
    data class Comment(
        var uid : String,
        var message : String
    )
}
