package devandroid.lucas.desafio4;

import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.GET;

public interface QuizService {
    @GET("quizzes")
    Call<JsonObject> getQuizzes();
}


