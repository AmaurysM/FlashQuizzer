import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

data class OpenAIRequest(
    val model: String,
    val messages: List<Message>,
    val max_tokens: Int = 150,
    val temperature: Double = 0.7
)

data class Message(
    val role: String,
    val content: String
)

data class OpenAIResponse(
    val choices: List<Choice>
)

data class Choice(
    val message: MessageContent
)

data class MessageContent(
    val content: String
)

interface OpenAIService {
    @Headers("Authorization: Bearer sk-NglWCxwGwGmI4A2Rn30CvJP27b-LnDTdBdpVVEpXPfT3BlbkFJ94Gpt581r_3_pKMGW3aIvGQO6kzgPI6JzaYPq8r-0A")
    @POST("v1/chat/completions")
    suspend fun generateFlashcards(@Body request: OpenAIRequest): OpenAIResponse
}
