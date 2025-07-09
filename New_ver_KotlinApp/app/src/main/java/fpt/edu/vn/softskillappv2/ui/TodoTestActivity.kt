package fpt.edu.vn.softskillappv2.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fpt.edu.vn.softskillappv2.R
import fpt.edu.vn.softskillappv2.data.model.User
import fpt.edu.vn.softskillappv2.data.supabase.SupabaseClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TodoTestActivity : AppCompatActivity() {
    private lateinit var rvUsers: RecyclerView
    private lateinit var tvError: TextView
    private lateinit var adapter: UserAdapter
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)
    private val TAG = "TodoTestActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_test)
        rvUsers = findViewById(R.id.rvTodos)
        tvError = findViewById(R.id.tvError)
        adapter = UserAdapter()
        rvUsers.layoutManager = LinearLayoutManager(this)
        rvUsers.adapter = adapter
        loadUsers()
    }

    private fun loadUsers() {
        scope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    SupabaseClient.postgrest.from("users").select()
                }
                val users = response.decodeList<User>()
                adapter.submitList(users)
                tvError.visibility = View.GONE
                Log.d(TAG, "✅ Load users thành công: Số lượng = ${users.size}, Dữ liệu: $users")
            } catch (e: Exception) {
                tvError.text = "❌ Error: ${e.message}"
                tvError.visibility = View.VISIBLE
                adapter.submitList(emptyList())
                Log.e(TAG, "❌ Lỗi khi load users: ${e.message}", e)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}

class UserAdapter : RecyclerView.Adapter<UserViewHolder>() {
    private var items: List<User> = emptyList()

    fun submitList(list: List<User>) {
        items = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): UserViewHolder {
        val view = android.view.LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_2, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}

class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val text1: TextView = itemView.findViewById(android.R.id.text1)
    private val text2: TextView = itemView.findViewById(android.R.id.text2)
    fun bind(item: User) {
        text1.text = item.email
        text2.text = "Points: ${item.points}"
    }
} 