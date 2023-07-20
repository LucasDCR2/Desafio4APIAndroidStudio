package devandroid.lucas.desafio4;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.util.List;

import devandroid.lucas.desafio4.getandset.Quiz;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://ah.we.imply.com/";
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout linearLayoutQuizzes = findViewById(R.id.linearLayoutQuizzes);

        LayoutInflater inflater = LayoutInflater.from(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        QuizService quizService = retrofit.create(QuizService.class);
        Call<JsonObject> call = quizService.getQuizzes();

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();
                    if (jsonObject != null && jsonObject.has("result")) {
                        JsonArray quizArray = jsonObject.getAsJsonArray("result");
                        Gson gson = new Gson();
                        List<Quiz> quizzes = gson.fromJson(quizArray, new TypeToken<List<Quiz>>() {}.getType());

                        // Exibe a lista de quizzes disponíveis
                        if (quizzes != null) {
                            for (Quiz quiz : quizzes) {
                                // Inflar o layout personalizado do botão do quiz
                                View buttonQuiz = inflater.inflate(R.layout.button_quiz_item, linearLayoutQuizzes, false);

                                // Encontrar as views dentro do layout personalizado
                                ImageView imageViewQuizIcon = buttonQuiz.findViewById(R.id.imageViewQuizIcon);
                                TextView textViewQuizName = buttonQuiz.findViewById(R.id.textViewQuizName);

                                // Definir o ícone e o nome do quiz
                                imageViewQuizIcon.setImageResource(R.drawable.java_22523);
                                textViewQuizName.setText(quiz.getName());
                                buttonQuiz.setBackgroundResource(R.drawable.button_quiz_background);

                                // Definir margens para controlar a distância entre os botões
                                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT
                                );

                                int marginInPx = getResources().getDimensionPixelSize(R.dimen.quiz_button_margin);
                                layoutParams.setMargins(0, 0, 0, marginInPx);
                                buttonQuiz.setLayoutParams(layoutParams);

                                // Definir a ação do botão quando clicado
                                buttonQuiz.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        // Crie um objeto Gson
                                        Gson gson = new Gson();

                                        // Converter o objeto Quiz em uma string JSON
                                        String quizJson = gson.toJson(quiz);

                                        // Abre a próxima tela para responder ao quiz selecionado
                                        Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                                        intent.putExtra("quizJson", quizJson);
                                        startActivity(intent);
                                    }
                                });

                                // Adicionar o botão ao layout dos quizzes
                                linearLayoutQuizzes.addView(buttonQuiz);
                            }
                        }
                    }
                } else {
                    Log.e(TAG, "Resposta sem sucesso: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, "Erro na requisição: " + t.getMessage());
            }
        });

        Button btnRespondidos = findViewById(R.id.btnRespondidos);
        btnRespondidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implemente o código para a ação do botão "Respondidos"
            }
        });
    }
}







