package fpt.edu.vn.softskillappv2.ui_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fpt.edu.vn.softskillappv2.R
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import fpt.edu.vn.softskillappv2.data.model.Video
import fpt.edu.vn.softskillappv2.data.repository.VideoRepository
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [create.newInstance] factory method to
 * create an instance of this fragment.
 */
class create : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var etVideoUrl: EditText
    private lateinit var etVideoTitle: EditText
    private lateinit var etVideoCategory: EditText
    private lateinit var btnUploadVideo: Button
    private val videoRepository = VideoRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etVideoUrl = view.findViewById(R.id.et_video_url)
        etVideoTitle = view.findViewById(R.id.et_video_title)
        etVideoCategory = view.findViewById(R.id.et_video_category)
        btnUploadVideo = view.findViewById(R.id.btn_upload_video)

        btnUploadVideo.setOnClickListener {
            val url = etVideoUrl.text.toString().trim()
            val title = etVideoTitle.text.toString().trim()
            val category = etVideoCategory.text.toString().trim()
            if (url.isEmpty() || title.isEmpty() || category.isEmpty()) {
                Toast.makeText(requireContext(), "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val video = Video(title = title, youtube_url = url, category = category, uploader_id = null)
            viewLifecycleOwner.lifecycleScope.launch {
                val result = videoRepository.createVideo(video)
                if (result.isSuccess) {
                    Toast.makeText(requireContext(), "Upload video thành công!", Toast.LENGTH_SHORT).show()
                    etVideoUrl.text.clear()
                    etVideoTitle.text.clear()
                    etVideoCategory.text.clear()
                } else {
                    Toast.makeText(requireContext(), "Upload thất bại: ${result.exceptionOrNull()?.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment create.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            create().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}