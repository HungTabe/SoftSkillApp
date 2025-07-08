package com.example.softskillapp.data.repository

import android.content.Context
import com.example.softskillapp.util.SupabaseClientProvider
import com.example.softskillapp.data.model_kt.User
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserRepository(context: Context) {

    private val supabaseClient: SupabaseClient = SupabaseClientProvider.getClient(context)

    // Callback interface
    interface ConnectionCallback {
        fun onResult(isConnected: Boolean)
    }

    // Kiểm tra kết nối Supabase bằng coroutine
    suspend fun checkConnection(): Boolean {
        return try {
            // Truy vấn bảng "users"
            withContext(Dispatchers.IO) {
                supabaseClient
                    .from("users")
                    .select {
                        limit(1)
                    }
                    .decodeList<User>()
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    // Hàm gọi checkConnection với callback cho Java
    fun checkConnectionWithCallback(callback: ConnectionCallback) {
        CoroutineScope(Dispatchers.Main).launch {
            val isConnected = checkConnection()
            callback.onResult(isConnected)
        }
    }

}