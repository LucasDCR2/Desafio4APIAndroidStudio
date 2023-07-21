package devandroid.lucas.desafio4;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import devandroid.lucas.desafio4.getandset.Question;
import devandroid.lucas.desafio4.getandset.Quiz;

import java.util.List;

public class ResultadoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        // Recupere o objeto Quiz e a pontuação (score) da Intent
        String quizJson = getIntent().getStringExtra("quizJson");
        int score = getIntent().getIntExtra("score", 0);

        // Crie um objeto Gson para converter o JSON de volta para um objeto Quiz
        Gson gson = new Gson();
        Quiz quiz = gson.fromJson(quizJson, Quiz.class);

        if (quiz != null) {
            mostrarResumo(quiz.getQuestions(), score);
        } else {
            finish();
        }
    }

    private void mostrarResumo(List<Question> questions, int score) {
        LinearLayout linearLayoutResumo = findViewById(R.id.linearLayoutResumo2);

        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            String selectedOption = question.getSelectedOption();
            String correctOption = question.getOptions().get(question.getRightOption());

            // Crie um novo Button para exibir "Correta" ou "Incorreta" com base na comparação entre a resposta selecionada e a resposta correta
            Button buttonResultado = new Button(this);
            buttonResultado.setText(selectedOption.equals(correctOption) ? "Correta" : "Incorreta");
            buttonResultado.setEnabled(false); // Desabilita o botão para que não seja clicável
            buttonResultado.setBackgroundResource(R.drawable.custom_buttom_resumo);
            buttonResultado.setTextColor(getResources().getColor(android.R.color.white));
            linearLayoutResumo.addView(buttonResultado);

            // Definir a cor do texto de acordo com a resposta (verde para correta, vermelho para incorreta)
            if (selectedOption.equals(correctOption)) {
                buttonResultado.setTextColor(getResources().getColor(R.color.green)); // Cor verde para "Correta"
            } else {
                buttonResultado.setTextColor(getResources().getColor(R.color.red)); // Cor vermelha para "Incorreta"
            }

            // Crie um novo Button para exibir "Sua resposta: " + selectedOption
            Button buttonSuaResposta = new Button(this);
            buttonSuaResposta.setText("Sua resposta: " + selectedOption);
            buttonSuaResposta.setEnabled(false); // Desabilita o botão para que não seja clicável
            buttonSuaResposta.setBackgroundResource(R.drawable.custom_buttom_resumo);
            buttonSuaResposta.setTextColor(getResources().getColor(android.R.color.white));
            linearLayoutResumo.addView(buttonSuaResposta);

            // Crie um novo Button para exibir "Resposta correta: " + correctOption
            Button buttonRespostaCorreta = new Button(this);
            buttonRespostaCorreta.setText("Resposta correta: " + correctOption);
            buttonRespostaCorreta.setEnabled(false); // Desabilita o botão para que não seja clicável
            buttonRespostaCorreta.setBackgroundResource(R.drawable.custom_buttom_resumo);
            buttonRespostaCorreta.setTextColor(getResources().getColor(android.R.color.white));
            linearLayoutResumo.addView(buttonRespostaCorreta);

            // Adicione um espaço entre cada resumo de pergunta
            TextView textViewSpace = new TextView(this);
            textViewSpace.setText(""); // Texto vazio para criar espaço
            linearLayoutResumo.addView(textViewSpace);
        }
    }

}
