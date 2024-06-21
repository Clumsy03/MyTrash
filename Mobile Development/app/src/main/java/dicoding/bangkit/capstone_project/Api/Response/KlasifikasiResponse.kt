package dicoding.bangkit.capstone_project.Api.Response

import com.google.gson.annotations.SerializedName

data class KlasifikasiResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Data(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("data")
	val data: String? = null,

	@field:SerializedName("suggestion")
	val suggestion: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)
