package com.example.softskillapp.util

import android.content.Context
import com.example.softskillapp.R
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime

object SupabaseClientProvider {
    private var supabaseClient: SupabaseClient? = null

    fun getClient(context: Context): SupabaseClient {
        if (supabaseClient == null) {
            supabaseClient = createSupabaseClient(
                context.getString(R.string.supabase_url),
                context.getString(R.string.supabase_key)
            ) {
                install(Postgrest)
                install(Realtime)
            }
        }
        return supabaseClient!!
    }
}