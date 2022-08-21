package com.example.gatitbogi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.gatitbogi.databinding.FragmentChatBinding
import com.example.gatitbogi.model.ChatModel
import com.google.firebase.database.FirebaseDatabase

class ChatFragment : Fragment() {

    lateinit var binding: FragmentChatBinding

    lateinit var destinationUid : String
    lateinit var button : Button
    lateinit var editText : EditText

    lateinit var uid : String
    lateinit var chatRoomUid : String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentChatBinding.inflate(inflater, container, false)
        destinationUid = "created_sample_chatroom"
        uid = "sample"
        button = binding.chatSend
        editText = binding.message

        createchatroom()

        button.setOnClickListener(View.OnClickListener(){
            @Override
            fun onClick(view: View) {
                var comment = ChatModel.Comment(uid,"")
                comment.message = editText.getText().toString()
                FirebaseDatabase.getInstance().getReference().child("chatrooms").child(chatRoomUid).child("comments").push().setValue(comment)
            }
        })

        return binding.root
    }


    //일시적 채팅방 구현을 위한 코드
    //구해요 단계 페이지에서 UID 받아오면 없어질 코드
    fun createchatroom() {
        var chatModel = ChatModel(mutableMapOf(uid to true), mutableMapOf("s" to ChatModel.Comment("first", "채팅방이 시작되었습니다")))
        chatModel.users.put(destinationUid, true)
        FirebaseDatabase.getInstance().reference.child("chatrooms").push().setValue(chatModel)
        //chatRoomUid = it
    }

//    fun onclicklistener() {
//
//    }
}